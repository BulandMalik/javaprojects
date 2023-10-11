package com.baeldung.springbootreact.payment.config;

import com.braintreegateway.BraintreeGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BraintreeConfig {

    @Value("${braintree.environment}")
    private String environment;

    @Value("${braintree.merchantId.tenant1}")
    private String merchantIdTenant1;

    @Value("${braintree.publicKey.tenant1}")
    private String publicKeyTenant1;

    @Value("${braintree.privateKey.tenant1}")
    private String privateKeyTenant1;

    @Value("${braintree.merchantId.tenant2}")
    private String merchantIdTenant2;

    @Value("${braintree.publicKey.tenant2}")
    private String publicKeyTenant2;

    @Value("${braintree.privateKey.tenant2}")
    private String privateKeyTenant2;

    @Bean
    public BraintreeGateway braintreeGatewayTenant1() {
        return new BraintreeGateway(environment, merchantIdTenant1, publicKeyTenant1, privateKeyTenant1);
    }

    @Bean
    public BraintreeGateway braintreeGatewayTenant2() {
        return new BraintreeGateway(environment, merchantIdTenant2, publicKeyTenant2, privateKeyTenant2);
    }
}