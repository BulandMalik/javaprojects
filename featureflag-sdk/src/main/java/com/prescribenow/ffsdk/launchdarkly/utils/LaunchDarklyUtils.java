package com.prescribenow.ffsdk.launchdarkly.utils;

import com.launchdarkly.sdk.server.Components;
import com.prescribenow.ffsdk.launchdarkly.config.LaunchDarklyConfigProperties;
import com.prescribenow.ffsdk.launchdarkly.config.domain.Http;
import com.prescribenow.ffsdk.launchdarkly.config.domain.Url;
import com.prescribenow.ffsdk.launchdarkly.exception.ApplicationStartupException;
import com.launchdarkly.sdk.server.LDClient;
import com.launchdarkly.sdk.server.LDConfig;
import com.launchdarkly.sdk.server.LDConfig.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

@Component
public class LaunchDarklyUtils {
        private static final Logger LOGGER = LoggerFactory.getLogger(LaunchDarklyUtils.class);

    public LDClient createLdClient(String sdkKey, LaunchDarklyConfigProperties ldConfigProperties, Builder ldConfigBuilder) {
        Http http = ldConfigProperties.getHttp();
        Url url = ldConfigProperties.getUrl();

        LDConfig ldConfig;
        try {
            ldConfigBuilder = ldConfigBuilder
                .offline(ldConfigProperties.isOffline())
                .applicationInfo(
                    Components.applicationInfo()
                        .applicationId("launchdarkly-java-sdk")
                        .applicationVersion("1.0.0")
                )
                .serviceEndpoints(Components.serviceEndpoints()
                    .streaming(new URI(url.getStream()))
                    .polling(new URI(url.getPolling())) //"https://app.launchdarkly.us")
                    .events(new URI(url.getEvents()))
                );
            this.configureHttpAndEventAttributes(ldConfigBuilder, http);
            ldConfigBuilder = ldConfigBuilder.offline(ldConfigProperties.isOffline());
            //ldConfigBuilder = ldConfigBuilder.stream(ldConfigProperties.isStream());
            ldConfig = ldConfigBuilder.build();
        } catch (URISyntaxException e) {
            LOGGER.error("One of the URIs were malformed. Please have a look. base={}, stream={}, polling={}, events={}",
                    url.getBase(), url.getStream(), url.getPolling(), url.getEvents());
            throw new ApplicationStartupException(e.getMessage());
        }

        return new LDClient(sdkKey, ldConfig);
    }

    protected Builder configureHttpAndEventAttributes(Builder ldConfigBuilder, Http http) {

        ldConfigBuilder
            .http(
                Components.httpConfiguration()
                    .connectTimeout(Duration.ofSeconds(Integer.parseInt(http.getConnectTimeout())))
                    .socketTimeout(Duration.ofSeconds(Integer.parseInt(http.getReadTimeout())))
            )
            .events(
                Components.sendEvents()
                    .flushInterval(Duration.ofSeconds(Integer.parseInt(http.getEventsFlushInterval())))
            );

        return ldConfigBuilder;
    }
}

