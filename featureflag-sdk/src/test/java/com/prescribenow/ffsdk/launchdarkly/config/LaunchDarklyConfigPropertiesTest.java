package com.prescribenow.ffsdk.launchdarkly.config;

import com.prescribenow.ffsdk.launchdarkly.config.domain.Http;
import com.prescribenow.ffsdk.launchdarkly.config.domain.Url;
import com.prescribenow.ffsdk.launchdarkly.exception.ApplicationStartupException;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LaunchDarklyConfigPropertiesTest {

    private static final Logger LOG = LoggerFactory.getLogger(LaunchDarklyConfigPropertiesTest.class);

    private LaunchDarklyConfigProperties launchDarklyConfigProperties;

    @BeforeAll // denotes that the annotated method will be executed before all test methods in the current class (previously @BeforeClass)
    //It's important to note that the method with the @BeforeAll annotation needs to be static, otherwise the code won't compile.
    static void setup() {
        LOG.info("@BeforeAll - executes once before all test methods in this class");
    }

    @BeforeEach //denotes that the annotated method will be executed before each test method (previously @Before)
    void init() {
        launchDarklyConfigProperties = new LaunchDarklyConfigProperties();
    }

    @AfterEach //denotes that the annotated method will be executed after each test method (previously @After)
    void tearDown() {
        LOG.info("@AfterEach - executed after each test method.");
    }

    @AfterAll //denotes that the annotated method will be executed after all test methods in the current class (previously @AfterClass)
    // has to be static method
    static void done() {
        LOG.info("@AfterAll - executed after all test methods.");
    }

    @Tag("NONPRD") //declares tags for filtering tests
    @DisplayName("Test - SDK Key Is Empty") //defines a custom display name for a test class or a test method
    @Test
    public void sdkkeyEmpty() {

        launchDarklyConfigProperties.setHttp(new Http());
        launchDarklyConfigProperties.setUrl(new Url());
        launchDarklyConfigProperties.setSdkKey(null);
        launchDarklyConfigProperties.setOffline(Boolean.TRUE);
        LOG.info("launchDarklyConfigProperties: {}",launchDarklyConfigProperties.toString());

        assertThrows(NullPointerException.class, () -> {
            launchDarklyConfigProperties.postConstruct();
        });
    }

    //@Test(expected= ApplicationStartupException.class)
    @Test
    public void httpEmpty() {

        launchDarklyConfigProperties.setHttp(null);
        launchDarklyConfigProperties.setUrl(new Url());
        launchDarklyConfigProperties.setSdkKey("test");

        assertThrows(ApplicationStartupException.class, () -> {
            launchDarklyConfigProperties.postConstruct();
        });
    }

    @Test
    public void urlEmpty() {

        launchDarklyConfigProperties.setHttp(new Http());
        launchDarklyConfigProperties.setUrl(null);
        launchDarklyConfigProperties.setSdkKey("test");

        assertThrows(ApplicationStartupException.class, () -> {
            launchDarklyConfigProperties.postConstruct();
        });
    }

    @DisplayName("Test URL Success")
    @Test
    public void testUrlSuccess() {

        Url url = new Url();
        url.setBase("https://app.launchdarkly.com");
        url.setEvents("https://events.launchdarkly.com");
        url.setStream("https://stream.launchdarkly.com");
        url.setPolling("https://app.launchdarkly.us");

        launchDarklyConfigProperties.setHttp(new Http());
        launchDarklyConfigProperties.setUrl(url);
        launchDarklyConfigProperties.setSdkKey("test");

        assertDoesNotThrow(() -> {
            launchDarklyConfigProperties.postConstruct();
        });
    }
}