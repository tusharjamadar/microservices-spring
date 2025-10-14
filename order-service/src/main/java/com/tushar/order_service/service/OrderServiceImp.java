package com.tushar.order_service.service;

import com.tushar.order_service.dto.OrderRequest;
import com.tushar.order_service.dto.OrderResponse;
import com.tushar.order_service.entity.Order;
import com.tushar.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService{
    private final OrderRepository orderRepository;

    @Override
    public Long placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .orderDate(Instant.now())
                .build();

        order = orderRepository.save(order);

        return order.getId();
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(order -> {
            return OrderResponse.builder()
                    .id(order.getId())
                    .productId(order.getProductId())
                    .quantity(order.getQuantity())
                    .totalAmount(order.getAmount())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);

        if(order == null)return null;

        return OrderResponse.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .totalAmount(order.getAmount())
                .build();
    }
}
