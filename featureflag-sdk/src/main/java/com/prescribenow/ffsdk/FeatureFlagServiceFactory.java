package com.prescribenow.ffsdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FeatureFlagServiceFactory {

    private static final Logger LOG = LoggerFactory.getLogger(FeatureFlagServiceFactory.class);

    private final Map<String, FeatureFlagService> FeatureFlagServiceMap; // Spring automatically get the bean name as key

    @Autowired // inject FeatureFlagService all implementations
    public FeatureFlagServiceFactory(Map<String, FeatureFlagService> FeatureFlagServiceMap) {
        this.FeatureFlagServiceMap = FeatureFlagServiceMap;
    }

    /**
     * factory method to return the right Feature Flag Service Impl
     *
     * @param featureFlagClientName
     *
     * @return
     */
    public FeatureFlagService getFeatureFlagService(String featureFlagClientName)
    {
        if (FeatureFlagConstants.FF_CLIENT_LAUNCHDARKLY.equals(featureFlagClientName)) {
            return FeatureFlagServiceMap.get(FeatureFlagConstants.FF_CLIENT_LAUNCHDARKLY);
        }
        LOG.warn("Returning LD Service as the default");
        return FeatureFlagServiceMap.get(FeatureFlagConstants.FF_CLIENT_LAUNCHDARKLY);
    }
}
