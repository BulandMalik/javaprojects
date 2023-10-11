package com.baeldung.springbootreact.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tenant {
    private String tenantId;
    private String payPalAccount;

    // Constructors, getters, and setters
}
