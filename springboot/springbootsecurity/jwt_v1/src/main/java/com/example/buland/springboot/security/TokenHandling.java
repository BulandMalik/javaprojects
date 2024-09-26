package com.example.buland.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TokenHandling {

    private static String apiSharedKey = "3DD2DEBEFDD478E0838C35986352EA7A"; //128 bit key
    private static final String ALGORITHM = "AES";


    public static void main(String[] args) throws Exception {

        String customerEmail = "c1@mail.com";

        SecretKey aesKey = generateAESKey();
        System.out.println("Generated AES Key: " + bytesToHex(aesKey.getEncoded()));

        String encryptedStr = encrypt(customerEmail);
        System.out.println("encryptedStr for "+ customerEmail + " ==> "+encryptedStr);

        String encryptedStr1 = dencrypt(encryptedStr);
        System.out.println("encryptedStr for "+ encryptedStr + " ==> "+encryptedStr1);
    }

    // Helper method to convert bytes to hexadecimal string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

    public static String encrypt(String value) throws Exception {
        SecretKeySpec key = new SecretKeySpec(apiSharedKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String dencrypt(String encryptedVal) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedVal);
        SecretKeySpec key = new SecretKeySpec(apiSharedKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(encryptedTextByte);
        return new String(encryptedBytes);
    }

    public static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // 128-bit key length
        return keyGenerator.generateKey();
    }

}
