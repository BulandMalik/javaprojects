package com.baeldung.springbootreact.payment.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private boolean success;
    private String message; // Optional message for more details (e.g., error message)
/*
    public PaymentResponse(boolean success) {
        this.success = success;
    }
*/
    // Getters and setters for success and message
}