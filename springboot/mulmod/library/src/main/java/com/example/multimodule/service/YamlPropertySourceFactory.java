package com.example.multimodule.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import static org.springframework.beans.factory.config.YamlProcessor.MatchStatus.*;

/**
 * You may be asking why we need this. Because we have renamed it and since it is not automatically read by the spring itself,
 * we need to tell the system how it should read the myservice.yaml.
 *
 * Issue
 * ====
 * If we name our property file as “application.yaml” in the library and the app which will use this library has its own property file
 * named “application.yaml”, they will collide. The property in the parent project will override the one in the library, so it won’t be
 * able to read it in the application. A complete disappointment :(
 *
 * SOLUTION
 * =======
 * The solution to this problem is to name your property file in the library differently. We named it myservice.yaml
 */
@Slf4j
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) {

        String activeProfile = Optional.ofNullable(Optional.ofNullable(System.getenv("SPRING_PROFILES_ACTIVE"))
                .orElse(System.getProperty("spring.profiles.active"))).orElse("default");

        log.info("myservice active profile: " + activeProfile);
        assert activeProfile != null;

        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        yamlFactory.setDocumentMatchers(properties -> {
            String profileProperty = properties.getProperty("spring.profiles");

            if (StringUtils.isEmpty(profileProperty)) {
                return ABSTAIN;
            }

            return profileProperty.contains(activeProfile) ? FOUND : NOT_FOUND;
        });
        yamlFactory.setResources(encodedResource.getResource());

        Properties properties = yamlFactory.getObject();

        assert properties != null;
        return new PropertiesPropertySource(Objects.requireNonNull(encodedResource.getResource().getFilename()), properties);
    }
}