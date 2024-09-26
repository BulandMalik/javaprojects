package com.baeldung.springbootreact.payment.service;

import com.baeldung.springbootreact.payment.dtos.PaymentResponse;
import com.braintreegateway.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    @Autowired
    private BraintreeGateway braintreeGatewayTenant1;

    @Autowired
    private BraintreeGateway braintreeGatewayTenant2;

    public PaymentResponse processPayment(BigDecimal amount, String tenantId, String nonce) {
        try {
            if ("tenant1".equals(tenantId)) {

                //Result<PaymentMethodNonce> paymentMethodNonce = braintreeGatewayTenant1.paymentMethodNonce().create("A_PAYMENT_METHOD_TOKEN");
                //nonce = paymentMethodNonce.getTarget().getNonce();

                TransactionRequest request = new TransactionRequest()
                        .amount(amount)
                        .paymentMethodNonce(nonce)
                        .options()
                        .submitForSettlement(true)
                        .done();

                //The sale() method returns a transaction result object that contains the transaction and information about the request.
                Result<Transaction> result = braintreeGatewayTenant1.transaction().sale(request);

                //return PaymentResponse.builder().success(result.isSuccess()).build();

                if (result.isSuccess()) {
                    return PaymentResponse.builder().success(result.isSuccess()).message("Payment processed via PayPal to Tenant 1 account.").build();
                } else {
                    return PaymentResponse.builder().success(result.isSuccess()).message("Payment failed: " + result.getMessage()).build();
                }
            } else if ("tenant2".equals(tenantId)) {
                TransactionRequest request = new TransactionRequest()
                        .amount(amount)
                        //.paymentMethodNonce("YOUR_PAYMENT_NONCE_TENANT2")
                        .paymentMethodNonce(nonce)
                        .options()
                        .submitForSettlement(true)
                        //.paypal() // Include this part if you are processing a PayPal transaction
                        .done();

                Result<Transaction> result = braintreeGatewayTenant2.transaction().sale(request);
                //return result.isSuccess();
                if (result.isSuccess()) {
                    return PaymentResponse.builder().success(result.isSuccess()).message("Payment processed via PayPal to Tenant 2 account.").build();
                } else {
                    return PaymentResponse.builder().success(result.isSuccess()).message("Payment failed: " + result.getMessage()).build();
                }

            } else {
                //return "Tenant="+tenantId+" not found.";
                return PaymentResponse.builder().success(Boolean.FALSE).message("Tenant="+tenantId+" not found.").build();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public String processPayment(BigDecimal amount, String tenantId, String nonce) {
        if ("tenant1".equals(tenantId)) {
            TransactionRequest request = new TransactionRequest()
                    .amount(amount)
                    .paymentMethodNonce(nonce)
                    .options()
                    .submitForSettlement(true)
                    .done();

            Result<Transaction> result = braintreeGatewayTenant1.transaction().sale(request);

            if (result.isSuccess()) {
                return "Payment processed via PayPal to Tenant 1's account.";
            } else {
                return "Payment failed: " + result.getMessage();
            }
        } else if ("tenant2".equals(tenantId)) {
            TransactionRequest request = new TransactionRequest()
                    .amount(amount)
                    .paymentMethodNonce("YOUR_PAYMENT_NONCE_TENANT2")
                    .options()
                    .submitForSettlement(true)
                    .done();

            Result<Transaction> result = braintreeGatewayTenant2.transaction().sale(request);

            if (result.isSuccess()) {
                return "Payment processed via PayPal to Tenant 2's account.";
            } else {
                return "Payment failed: " + result.getMessage();
            }
        } else {
            return "Tenant="+tenantId+" not found.";
        }
    }*/
}
