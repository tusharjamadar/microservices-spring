package com.tushar.order_service.service;

import com.tushar.order_service.dto.OrderRequest;
import com.tushar.order_service.dto.OrderResponse;
import com.tushar.order_service.external.response.PaymentResponse;
import com.tushar.order_service.external.response.ProductResponse;
import com.tushar.order_service.entity.Order;
import com.tushar.order_service.external.client.PaymentService;
import com.tushar.order_service.external.client.ProductService;
import com.tushar.order_service.external.request.PaymentRequest;
import com.tushar.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService{
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Long placeOrder(OrderRequest orderRequest) {

        log.info("Calling Product service to reduce product quantity");
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        Order order = Order.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .orderDate(Instant.now())
                .build();

        order = orderRepository.save(order);

        log.info("Calling Payment service to complete the payment");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;

        try{
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully. Changing the order status to PLACED");
            orderStatus = "PLACED";
        }catch (Exception e){
            log.error("Error occured in payment. Changing order status to failed");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
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
                    .orderDate(order.getOrderDate())
                    .orderStatus(order.getOrderStatus())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);

        if(order == null)return null;

        ProductResponse productResponse = restTemplate.getForObject(
                "http://product-service/products/" + order.getProductId(),
                ProductResponse.class
        );

        ProductResponse productDetails = ProductResponse.builder()
                .productName(productResponse.getProductName())
                .price(productResponse.getPrice())
                .quantity(productResponse.getQuantity())
                .productId(productResponse.getProductId())
                .build();

        PaymentResponse paymentResponse = restTemplate.getForObject(
                "http://payment-service/payment/order/" + order.getId(),
                PaymentResponse.class
        );

        PaymentResponse paymentDetails = PaymentResponse.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentMode(paymentResponse.getPaymentMode())
                .status(paymentResponse.getStatus())
                .amount(paymentResponse.getAmount())
                .paymentDate(paymentResponse.getPaymentDate())
                .build();

        return OrderResponse.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .totalAmount(order.getAmount())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();
    }
}
