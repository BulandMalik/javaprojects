package com.prescribenow.ffsdk.launchdarkly.config;

import com.prescribenow.ffsdk.launchdarkly.utils.LaunchDarklyUtils;
import com.launchdarkly.sdk.server.LDClient;
import com.launchdarkly.sdk.server.LDConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
@ConfigurationProperties
@Import(LaunchDarklyConfigProperties.class)
public class LaunchDarklyConfig {

    @Autowired
    LaunchDarklyConfigProperties launchDarklyConfigProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(LaunchDarklyConfig.class);

    private final LaunchDarklyUtils launchDarklyUtils = new LaunchDarklyUtils();

    private LDClient launchdarklyClient;

    @Bean(name="ldClient", destroyMethod = "close")
    public LDClient ldClient() {
        launchdarklyClient = this.launchDarklyUtils.createLdClient(this.launchDarklyConfigProperties.getSdkKey(),this.launchDarklyConfigProperties,new LDConfig.Builder());
        return launchdarklyClient;
    }

    @PreDestroy
    public void destroy() throws IOException {
        this.launchdarklyClient.close();
    }

    @PostConstruct
    public void logConfiguration(){
        LOGGER.info(launchDarklyConfigProperties.toString());
    }

}
