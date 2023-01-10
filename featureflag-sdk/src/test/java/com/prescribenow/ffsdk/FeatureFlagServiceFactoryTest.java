package com.prescribenow.ffsdk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class FeatureFlagServiceFactoryTest {

    @InjectMocks
    FeatureFlagServiceFactory featureFlagServiceFactory;

    @Mock
    Map<String, FeatureFlagService> featureFlagServiceMap = new HashMap<>();

    private final FeatureFlagService ffSvc = new FeatureFlagService() {
        @Override
        public <T> T evaluateFeatureFlag(String key, Map<String, String> attributes, Object defaultValue, Class<T> returnType) {
            return returnType.cast(Boolean.TRUE);
        }
    };


    @DisplayName("Returning LD Service Implementation")
    @Test
    public void getFeatureFlagService(){

        Mockito.when(featureFlagServiceMap.get(Mockito.any())).thenReturn(ffSvc);

        //ACT
        FeatureFlagService ldSvc = featureFlagServiceFactory.getFeatureFlagService(FeatureFlagConstants.FF_CLIENT_LAUNCHDARKLY);
        //ASSERT
        assertNotNull("LaunchDarkly Service is not null. ",ldSvc);
    }

    @DisplayName("Returning LD Service Implementation as Default")
    @Test
    public void getFeatureFlagServiceAsDefault(){

        Mockito.when(featureFlagServiceMap.get(Mockito.any())).thenReturn(ffSvc);

        //ACT
        FeatureFlagService ldSvc = featureFlagServiceFactory.getFeatureFlagService("DONOT_EXISTS");
        //ASSERT
        assertNotNull("LaunchDarkly Service is not null. ",ldSvc);
    }
}
