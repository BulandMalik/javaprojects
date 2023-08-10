package com.example.buland.springboot.security.jwt.v1.controllers;

import com.example.buland.springboot.security.jwt.v1.auth.jwt.EncryptionUtil;
import com.example.buland.springboot.security.jwt.v1.auth.jwt.JwtTokenUtils;
import com.example.buland.springboot.security.jwt.v1.dtos.TokenRequest;
import com.example.buland.springboot.security.jwt.v1.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TokenController {

    @Autowired
    private JwtTokenUtils jwtUtil;

    @Autowired
    private EncryptionUtil encryptUtil;

    List<String> validCustomerIds = Arrays.asList("c1@mail.com", "c2@mail.com", "c3@mail.com");

    @PostMapping("/generate-token")
    public Map<String, String> generateToken(@RequestBody TokenRequest request) {

        String customerId = getCustomerId(request.getCustomerId());

        if (!isValidCustomer(customerId)) {
            throw new UnauthorizedException("Invalid customer");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("customerId", customerId);
        claims.put("sourceSystem", request.getSourceSystem());

        String token = jwtUtil.generateAccessToken(claims);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        //ResponseEntity<?>
        //return ResponseEntity.ok(new AuthenticationResponse(newAccessToken, refreshToken));
        return response;
    }

    private String getCustomerId(String customerId)  {
        try {
            return encryptUtil.decrypt(customerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private boolean isValidCustomer(String customerId) {
        try {
            // Now validate the decrypted customer ID using your logic
            return validCustomerIds.contains(customerId);
        } catch (Exception e) {
            return false; // Decryption or validation error
        }
    }
}