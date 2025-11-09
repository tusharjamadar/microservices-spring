package com.tushar.order_service.dto;

import com.tushar.order_service.external.response.PaymentResponse;
import com.tushar.order_service.external.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private Long productId;
    private Long totalAmount;
    private Long quantity;
    private Instant orderDate;
    private String orderStatus;
    private ProductResponse productDetails;
    private PaymentResponse paymentDetails;
}
