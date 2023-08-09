package com.example.buland.springboot.security.jwt.v1.controllers;

import com.example.buland.springboot.security.jwt.v1.auth.jwt.JwtTokenUtils;
import com.example.buland.springboot.security.jwt.v1.dtos.AuthRequest;
import com.example.buland.springboot.security.jwt.v1.entities.User;
import com.example.buland.springboot.security.jwt.v1.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class UserController {

    private UserRepository userRepo;

    private JwtTokenUtils jwtTokenUtils;

    public UserController(UserRepository userRepo, JwtTokenUtils jwtTokenUtils) {
        this.userRepo = userRepo;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid AuthRequest request) {
        try {

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode(request.getPassword());


            com.example.buland.springboot.security.jwt.v1.entities.User newUser = new com.example.buland.springboot.security.jwt.v1.entities.User(request.getEmail(), password);
            User savedUser = userRepo.save(newUser);

            String accessToken = jwtTokenUtils.generateAccessToken(newUser);

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            accessToken
                    )
                    .body(savedUser);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
