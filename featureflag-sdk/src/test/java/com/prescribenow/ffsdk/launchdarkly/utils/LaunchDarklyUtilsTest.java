package com.prescribenow.ffsdk.launchdarkly.utils;

import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.server.Components;
import com.launchdarkly.sdk.server.LDClient;
import com.launchdarkly.sdk.server.integrations.TestData;
import com.launchdarkly.shaded.com.launchdarkly.eventsource.StreamHttpErrorException;
import com.prescribenow.ffsdk.launchdarkly.config.LaunchDarklyConfigProperties;
import com.prescribenow.ffsdk.launchdarkly.config.domain.Http;
import com.prescribenow.ffsdk.launchdarkly.config.domain.Url;
import com.launchdarkly.sdk.server.LDConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaunchDarklyUtilsTest {

    private static final Logger LOG = LoggerFactory.getLogger(LaunchDarklyUtilsTest.class);

    private LaunchDarklyUtils launchDarklyUtils;

    private LaunchDarklyConfigProperties launchDarklyConfigProperties;

    private TestData td = TestData.dataSource();
    private LDConfig config = new LDConfig.Builder()
            .dataSource(td)
            .events(Components.noEvents())
            .offline(Boolean.TRUE)
            .build();


    @BeforeEach
        //denotes that the annotated method will be executed before each test method (previously @Before)
    void init() {
        launchDarklyConfigProperties = new LaunchDarklyConfigProperties();
        launchDarklyUtils = new LaunchDarklyUtils();
    }


    @DisplayName("Test LD Client Successful Creation")
    @Test
    public void createLdClientTest() {
        Http http = new Http();
        http.setConnectTimeout("500");
        http.setReadTimeout("500");
        //LOG.info("http: {}",http.toString());

        Url url = new Url();
        url.setBase("https://app.launchdarkly.com");
        url.setEvents("https://events.launchdarkly.com");
        url.setStream("https://stream.launchdarkly.com");
        url.setPolling("https://app.launchdarkly.us");
        //LOG.info("url: {}",url.toString());

        launchDarklyConfigProperties.setHttp(http);
        launchDarklyConfigProperties.setUrl(url);
        launchDarklyConfigProperties.setSdkKey("test");

        LDConfig.Builder ldConfigBuilder = new LDConfig.Builder();
        //LDConfig.Builder ldConfigBuilder = Mockito.mock(LDConfig.Builder.class);

        //Mockito.when(ldConfigBuilder.build()).thenReturn(config);
        //Mockito.when(ldConfigBuilder.serviceEndpoints()).thenReturn(Components.noEvents());

        launchDarklyUtils.createLdClient("test-ket,",launchDarklyConfigProperties,ldConfigBuilder);

    }

    @Disabled("Disabled right now") // disables a test class or method (previously @Ignore)
    public void createLdClientTestException() {
        Http http = new Http();
        http.setConnectTimeout("500");
        http.setReadTimeout("500");

        Url url = new Url();
        url.setBase("bad");
        url.setEvents("https://events.launchdarkly.com");
        url.setStream("https://stream.launchdarkly.com");

        launchDarklyConfigProperties.setHttp(http);
        launchDarklyConfigProperties.setUrl(url);
        launchDarklyConfigProperties.setSdkKey("test");

        LDConfig.Builder ldConfigBuilder = new LDConfig.Builder();

        launchDarklyUtils.createLdClient("test-ket,",launchDarklyConfigProperties,ldConfigBuilder);
    }
}