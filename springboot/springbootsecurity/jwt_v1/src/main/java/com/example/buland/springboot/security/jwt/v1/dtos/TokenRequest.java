package com.example.buland.springboot.security.jwt.v1.dtos;

import lombok.Data;

@Data
public class TokenRequest {

    private String customerId;
    private String sourceSystem;

    // Getters and setters
}
