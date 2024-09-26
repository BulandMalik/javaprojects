package com.baeldung.springbootreact.payment.controllers;

import com.baeldung.springbootreact.payment.dtos.PaymentRequest;
import com.baeldung.springbootreact.payment.dtos.PaymentResponse;
import com.baeldung.springbootreact.payment.model.Tenant;
import com.baeldung.springbootreact.payment.service.PaymentService;
import com.baeldung.springbootreact.payment.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payment")
@Slf4j
public class PaymentController {

    private PaymentService paymentService;

    private TenantService tenantService;

    public PaymentController(PaymentService paymentService, TenantService tenantService) {
        this.paymentService = paymentService;
        this.tenantService = tenantService;
    }

    @PostMapping("/process-payment")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        BigDecimal amount = paymentRequest.getAmount();
        String tenantId = paymentRequest.getTenantId();
/*
        tenantId = "tenant1";
        log.info("tenantId:",tenantId);
        tenantId="tenant2";
        log.info("tenantId:",tenantId);
        amount = new BigDecimal("10");
        String paymentNonce = "testt1";
*/
        log.info("paymentRequest: {}",paymentRequest);
        log.info("tenantId: {}",tenantId);
        log.info("amount: {}",amount.intValue());
        log.info("Payment Nonce: {}",paymentRequest.getPaymentNonce());
/*
        paymentRequest.setTenantId(tenantId);
        paymentRequest.setAmount(amount);
        paymentRequest.setPaymentNonce(paymentNonce);

        log.info("tenantId:",tenantId);
        log.info("amount:",amount);
        log.info("Payment Nonce:",paymentRequest.getPaymentNonce());

 */
        Tenant tenant = tenantService.getTenantById(tenantId);
        if (tenant == null) {

            return ResponseEntity.badRequest().body(
                    PaymentResponse.builder().success(Boolean.FALSE).message("Tenant=" + tenant.getTenantId()+" not found.").build()
                    );
        }

        String nonce = paymentRequest.getPaymentNonce();

        //String result = paymentService.processPayment(amount, tenant.getTenantId(), nonce);
        PaymentResponse paymentRes = paymentService.processPayment(amount, tenant.getTenantId(), nonce);

        if ( !paymentRes.isSuccess() ) {
            return ResponseEntity.badRequest().body(paymentRes);
        }
        return ResponseEntity.ok(paymentRes);
    }
}

