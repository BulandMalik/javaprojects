package com.example.buland.springboot.security.jwt.v1.auth.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class EncryptionUtil {

    private static final String ALGORITHM = "AES";

    //private static final String KEY = "YourSharedSecretKey"; // Replace with your shared secret key

    @Value("${app.apiSharedKey}") // Load the API key from application properties
    private String apiSharedKey;

    public String encrypt(String value) throws Exception {
        SecretKeySpec key = new SecretKeySpec(apiSharedKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedValue) throws Exception {
        SecretKeySpec key = new SecretKeySpec(apiSharedKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
        return new String(decryptedBytes);
    }
}