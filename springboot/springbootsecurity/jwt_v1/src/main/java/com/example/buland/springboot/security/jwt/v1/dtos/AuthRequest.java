package com.example.buland.springboot.security.jwt.v1.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AuthRequest {
    @NotNull @Email @Length(min = 5, max = 50)
    private String email;

    @NotNull @Length(min = 5, max = 10)
    private String password;

    // getters and setters are not shown...
}
