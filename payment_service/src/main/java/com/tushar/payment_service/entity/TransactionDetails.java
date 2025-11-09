package com.tushar.payment_service.entity;

import com.tushar.payment_service.model.PaymentMode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "Transaction_Details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long orderId;
    private String referenceNumber;
    private Instant paymentDate;
    private String paymentStatus;
    private PaymentMode paymentMode;
    private long amount;
}
