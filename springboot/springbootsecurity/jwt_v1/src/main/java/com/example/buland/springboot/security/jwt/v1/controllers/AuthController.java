package com.example.buland.springboot.security.jwt.v1.controllers;


import javax.validation.Valid;

import com.example.buland.springboot.security.jwt.v1.auth.jwt.JwtTokenUtils;
import com.example.buland.springboot.security.jwt.v1.dtos.AuthRequest;
import com.example.buland.springboot.security.jwt.v1.dtos.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired AuthenticationManager authManager;
    @Autowired
    JwtTokenUtils jwtUtil;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            com.example.buland.springboot.security.jwt.v1.entities.User appUser = new com.example.buland.springboot.security.jwt.v1.entities.User(user.getUsername(), user.getPassword());
            String accessToken = jwtUtil.generateAccessToken(appUser);
            AuthResponse response = new AuthResponse(appUser.getEmail(), accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}