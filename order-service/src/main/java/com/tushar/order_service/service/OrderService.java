package com.tushar.order_service.service;

import com.tushar.order_service.dto.OrderRequest;
import com.tushar.order_service.dto.OrderResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);
}
