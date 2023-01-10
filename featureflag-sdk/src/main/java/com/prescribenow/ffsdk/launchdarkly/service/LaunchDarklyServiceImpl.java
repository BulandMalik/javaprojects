package com.prescribenow.ffsdk.launchdarkly.service;

import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.server.LDClient;
import com.prescribenow.ffsdk.FeatureFlagConstants;
import com.prescribenow.ffsdk.FeatureFlagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service(FeatureFlagConstants.FF_CLIENT_LAUNCHDARKLY)
@Primary
public class LaunchDarklyServiceImpl implements FeatureFlagService {

    @Autowired
    @Qualifier("ldClient")
    private LDClient ldClient;

    private static final Logger LOG = LoggerFactory.getLogger(LaunchDarklyServiceImpl.class);

    public <T>T evaluateFeatureFlag(String ffName, Map<String,String> attributes, Object defaultValue, Class<T> returnType) {
        try {

            Object returnValue = null;
            String userID = UUID.randomUUID().toString();

            LDUser user = buildLDUser(userID, attributes);

            switch (returnType.getSimpleName()) {
                case "Boolean":
                    returnValue = ldClient.boolVariation(ffName, user, (Boolean) defaultValue);
                    break;
                case "String":
                    returnValue = ldClient.stringVariation(ffName, user, (String) defaultValue);
                    break;
            }
            LOG.info("Toggle State | generatedUserID={} | toggleKey={} | toggleValue={} | featureFlagDefaultValue={}",
                    userID, ffName, returnValue, defaultValue);
            return returnType.cast(returnValue);
        }
        catch(Exception e) {
            LOG.info("Exception occurred when determining toggle value. Returning false | toggleKey={} | exceptionMessage={}" + ffName, e.getMessage(), e);
            return returnType.cast(defaultValue);
        }
    }

    protected LDUser buildLDUser(String userID, Map<String,String> attributes) {
        LDUser user = new LDUser.Builder(userID)
                .anonymous(true)
                .build();

        if(attributes!= null && !attributes.isEmpty()) {
            LOG.info("Attributes are NOT Empty");
            for (Map.Entry<String,String> entry : attributes.entrySet()) {
                LOG.info("Adding to custom : Key = {} and Value = {}",entry.getKey(),entry.getValue());
                user = new LDUser.Builder(user)
                        .custom(entry.getKey(),entry.getValue())
                        .build();
            }

        }
        return user;
    }

}
