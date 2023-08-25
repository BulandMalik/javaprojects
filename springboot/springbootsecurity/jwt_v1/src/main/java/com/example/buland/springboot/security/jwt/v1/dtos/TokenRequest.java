package com.example.buland.springboot.security.jwt.v1.dtos;

import lombok.Data;

@Data
public class TokenRequest {

    private String customerId;
    private String sourceSystem;

    private String customerAPIKey; //encryopted + encoded
    private String email; //encryopted + encoded
    private String tenantId;

    // Getters and setters
}
