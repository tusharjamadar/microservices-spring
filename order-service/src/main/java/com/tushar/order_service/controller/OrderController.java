package com.tushar.order_service.controller;

import com.tushar.order_service.dto.OrderRequest;
import com.tushar.order_service.dto.OrderResponse;
import com.tushar.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Log4j2
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(path = "/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        Long orderId = orderService.placeOrder(orderRequest);
        log.info("Order Id: {}", orderId);
        return ResponseEntity.ok(orderId);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id){
        OrderResponse orderResponse = orderService.getOrderById(id);

        if(orderResponse == null)throw new RuntimeException("Order with id: " + id + " not found.");
        return ResponseEntity.ok(orderResponse);
    }
}
