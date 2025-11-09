package com.tushar.payment_service.service;

import com.tushar.payment_service.model.PaymentRequest;
import com.tushar.payment_service.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
