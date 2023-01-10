package com.prescribenow.ffsdk;

import org.springframework.stereotype.Component;

import java.util.Map;

public interface FeatureFlagService {

    <T>T evaluateFeatureFlag(String key, Map<String,String> attributes, Object defaultValue, Class<T> returnType);

}
