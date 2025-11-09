package com.tushar.order_service.external.response;

import com.tushar.order_service.dto.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private long paymentId;
    private PaymentMode paymentMode;
    private long amount;
    private Instant paymentDate;
    private String status;
    private long orderId;
}
