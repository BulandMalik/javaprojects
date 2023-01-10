package com.prescribenow.ffsdk.launchdarkly.service;

import com.launchdarkly.sdk.server.LDClient;
import com.launchdarkly.sdk.LDUser;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

/**
 * The @ExtendWith annotation is used to load a JUnit 5 extension. JUnit defines an extension API, which allows a third-party vendor
 * like Mockito to hook into the lifecycle of running test classes and add additional functionality. The MockitoExtension looks at the
 * test class, finds member variables annotated with the @Mock annotation, and creates a mock implementation of those variables.
 *
 * It then finds member variables annotated with the @InjectMocks annotation and attempts to inject its mocks into those classes, using
 * either construction injection or setter injection.
 */
@ExtendWith(MockitoExtension.class)
public class FeatureFlagServiceImplTest {

    @InjectMocks
    private LaunchDarklyServiceImpl launchDarklyServiceImpl;

    @Mock
    private LDClient ldClient;

    @Test
    public void isEnabledTestKeyExist() {
        //ARRANGE
        String key = "test";
        Mockito.when(ldClient.boolVariation(Mockito.eq(key), Mockito.any(LDUser.class), Mockito.anyBoolean())).thenReturn(Boolean.TRUE);
        //ACT
        boolean isEnabled = launchDarklyServiceImpl.evaluateFeatureFlag(key, null, Boolean.TRUE, Boolean.class);
        //ASSERT
        assertTrue("The isEnabled must be true. ",isEnabled);
        Mockito.verify(ldClient,Mockito.times(1)).boolVariation(Mockito.eq(key), Mockito.any(LDUser.class), Mockito.anyBoolean());

    }

    @Test
    public void isEnabledTestKeyExistWithAttributes() {

        String key = "test";
        Map<String,String> attrMap = new HashMap<String,String>();
		attrMap.put("Key1","Value1");
		attrMap.put("Key2","Value2");
		attrMap.put("PN-TENANT-FACILITY","Enabled");

        Mockito.when(ldClient.boolVariation(Mockito.eq(key), Mockito.any(LDUser.class), Mockito.anyBoolean())).thenReturn(Boolean.TRUE);

        boolean isEnabled = launchDarklyServiceImpl.evaluateFeatureFlag(key, attrMap, Boolean.FALSE, Boolean.class);

        assertTrue("The isEnabled must be true. ",isEnabled);
        Mockito.verify(ldClient,Mockito.times(1)).boolVariation(Mockito.eq(key), Mockito.any(LDUser.class), Mockito.anyBoolean());

    }

    @Test
    public void isEnabledTest() {

        String key = "test";
        Map<String,String> attrMap = new HashMap<String,String>();
        attrMap.put("Key1","Value1");
        attrMap.put("Key2","Value2");
        attrMap.put("PN-TENANT-FACILITY","Enabled");
        /*Mockito.doThrow(RuntimeException.class)
                .when(ldClient).boolVariation(Mockito.eq(key), Mockito.any(LDUser.class), Mockito.anyBoolean());*/

        Mockito.when(ldClient.boolVariation(Mockito.eq(key), Mockito.any(LDUser.class), Mockito.anyBoolean())).thenReturn(Boolean.TRUE);

        boolean isEnabled = launchDarklyServiceImpl.evaluateFeatureFlag(key, attrMap, Boolean.FALSE, Boolean.class);

        assertTrue("The isEnabled must be false. ",isEnabled);
        Mockito.verify(ldClient,Mockito.times(1)).boolVariation(Mockito.eq(key), Mockito.any(LDUser.class), Mockito.anyBoolean());

    }

    @Test
    public void isEnabledTestException() {

        String key = "test";
        Map<String,String> attrMap = new HashMap<String,String>();
        attrMap.put("Key1","Value1");
        attrMap.put("Key2","Value2");
        attrMap.put("PN-TENANT-FACILITY","Enabled");
        Mockito.doThrow(RuntimeException.class)
                .when(ldClient).boolVariation(Mockito.eq(key), Mockito.any(LDUser.class), Mockito.anyBoolean());

        boolean isEnabled = launchDarklyServiceImpl.evaluateFeatureFlag(key, attrMap, Boolean.FALSE, Boolean.class);

        assertFalse("The isEnabled must be false. ",isEnabled);
        Mockito.verify(ldClient,Mockito.times(1)).boolVariation(Mockito.eq(key), Mockito.any(LDUser.class), Mockito.anyBoolean());

    }

}