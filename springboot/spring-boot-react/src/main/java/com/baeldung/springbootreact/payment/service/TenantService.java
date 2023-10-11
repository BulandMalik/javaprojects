package com.baeldung.springbootreact.payment.service;

import com.baeldung.springbootreact.payment.model.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TenantService {

    private final Map<String, Tenant> tenants = new HashMap<>();

    public TenantService() {
        // Initialize tenants (replace with your actual data source)
        tenants.put("tenant1", new Tenant("tenant1", "t1"));
        tenants.put("tenant2", new Tenant("tenant2", "t2"));
    }

    public void addTenantConfiguration(String tenantId, Tenant configuration) {
        tenants.put(tenantId, configuration);
    }

    public Tenant getTenantById(String tenantId) {
        log.info("inside getTenantById with tenantId={}",tenantId);
        log.info("# of tenants={}",tenants.size());
        log.info("tenants={}",tenants);
        return tenants.get(tenantId);
    }
}
