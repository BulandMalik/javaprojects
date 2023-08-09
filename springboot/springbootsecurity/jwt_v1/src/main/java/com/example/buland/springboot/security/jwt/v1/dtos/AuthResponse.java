package com.example.buland.springboot.security.jwt.v1.dtos;

import lombok.Data;

@Data
public class AuthResponse {
    private String email;
    private String accessToken;

    public AuthResponse() { }

    public AuthResponse(String email, String accessToken) {
        this.email = email;
        this.accessToken = accessToken;
    }

    // getters and setters are not shown...
}