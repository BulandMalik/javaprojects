package com.baeldung.springbootreact.payment.dtos;

import lombok.Data;

import java.math.BigDecimal;

//amount=10, tenantId=tenant1, paymentNonce=testt1)]
@Data
public class PaymentRequest {
    private BigDecimal amount;
    private String tenantId;
    private String paymentNonce;

    // Constructors, getters, and setters
}