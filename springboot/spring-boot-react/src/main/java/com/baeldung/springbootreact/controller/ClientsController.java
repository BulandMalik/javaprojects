package com.baeldung.springbootreact.controller;

import com.baeldung.springbootreact.domain.Client;
import com.baeldung.springbootreact.repository.ClientRepository;
import com.prescribenow.ffsdk.FeatureFlagService;
import com.prescribenow.ffsdk.FeatureFlagServiceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientsController.class);
    private final ClientRepository clientRepository;

    private final FeatureFlagServiceFactory featureFlagServiceFactory;

    public ClientsController(ClientRepository clientRepository, FeatureFlagServiceFactory featureFlagServiceFactory) {
        this.clientRepository = clientRepository;
        this.featureFlagServiceFactory = featureFlagServiceFactory;
    }

    @GetMapping
    public List<Client> getClients(
            @RequestParam String tid,
            //@RequestParam(value = "tid", required = false) String tid,
            @RequestParam(required = false) String fid,
            @RequestParam(required = false) String pid,
            @RequestParam(required = false) String email) {

        FeatureFlagService ffSvc = featureFlagServiceFactory.getFeatureFlagService("launchdarkly");
        String key = "PN_ERX-1023_MULTI_TENANCY_ENABLED";
        Map<String, String> attributes = new HashMap<String,String>();
        attributes.put("tenant_id", tid);
        attributes.put("facility_id", fid);
        attributes.put("provider_id", pid);
        attributes.put("user_email", email);

        LOGGER.info("evaluating FF with key={} and attributes={} is not set", key, attributes);
        if ( !ffSvc.evaluateFeatureFlag(key, attributes, Boolean.TRUE, Boolean.class) )
        {
            LOGGER.warn("key={} with attributes={} is not set", key, attributes);
            return Collections.emptyList();
        }
        return clientRepository.findAll();

    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity createClient(@RequestBody Client client) throws URISyntaxException {
        Client savedClient = clientRepository.save(client);
        return ResponseEntity.created(new URI("/clients/" + savedClient.getId())).body(savedClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody Client client) {
        Client currentClient = clientRepository.findById(id).orElseThrow(RuntimeException::new);
        currentClient.setName(client.getName());
        currentClient.setEmail(client.getEmail());
        currentClient = clientRepository.save(client);

        return ResponseEntity.ok(currentClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
