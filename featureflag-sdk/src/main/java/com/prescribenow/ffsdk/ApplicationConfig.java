package com.prescribenow.ffsdk;

import com.prescribenow.ffsdk.launchdarkly.config.LaunchDarklyConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



/**
 * ApplicationConfig is an entry to initialize the required Beans and
 * enable the configuration properties for this library.
 */
@Configuration
@EnableConfigurationProperties({LaunchDarklyConfig.class})
@ComponentScan({"com.prescribenow.ffsdk"})
public class ApplicationConfig {
}