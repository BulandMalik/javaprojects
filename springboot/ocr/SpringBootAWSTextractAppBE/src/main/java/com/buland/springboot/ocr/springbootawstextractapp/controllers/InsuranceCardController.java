package com.buland.springboot.ocr.springbootawstextractapp.controllers;

import com.buland.springboot.ocr.springbootawstextractapp.services.TextractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/ocr")
@Slf4j
public class InsuranceCardController {

    private final TextractService textractService;

    public InsuranceCardController(TextractService textractService) {
        this.textractService = textractService;
    }

    @PostMapping("/extractInsuranceInfo")
    //@PostMapping(value = "/analyze", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Map<String, String>> extractInsuranceInfo(
            @RequestParam("frontImage") MultipartFile frontImage,
            @RequestParam("backImage") MultipartFile backImage) {
        try {
            log.info("inside processInsuranceCard");
            // Process the front and back images
            Map<String, String> extractedData = textractService.extractInsuranceData(frontImage, backImage);

            return ResponseEntity.ok(extractedData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to process insurance card"));
        }
    }
}