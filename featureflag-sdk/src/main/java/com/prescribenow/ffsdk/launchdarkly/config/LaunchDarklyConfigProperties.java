package com.prescribenow.ffsdk.launchdarkly.config;

import com.prescribenow.ffsdk.launchdarkly.config.domain.Http;
import com.prescribenow.ffsdk.launchdarkly.config.domain.Url;
import com.prescribenow.ffsdk.launchdarkly.exception.ApplicationStartupException;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix="pn.launchdarkly")
public class LaunchDarklyConfigProperties {
    private static final Logger LOG = LoggerFactory.getLogger(LaunchDarklyConfigProperties.class);

    private String sdkKey;
    private boolean offline;
    private Http http;
    private Url url;

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }


    public Http getHttp() {
        return this.http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    public Url getUrl() {
        return this.url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public String getSdkKey() {
        return sdkKey;
    }

    public void setSdkKey(String sdkKey) {
        this.sdkKey = sdkKey;
    }

    @PostConstruct
    public void postConstruct() {

        if (this.getHttp() == null)
            LOG.error("HTTP is null");
        if (this.getUrl() == null)
            LOG.error("URL is null");
        if (this.getHttp() == null || this.getUrl() == null) {
            LOG.error("Neither http, or url properties can be null. httpObj=[{}] urlObj=[{}]",
                    this.getHttp(), this.getUrl());
            throw new ApplicationStartupException("Neither sdkKey, http, or url can be null");
        }

        Validate.notBlank(this.getSdkKey(), "LD SDK Key cannot be null/blank");
        Validate.notBlank(this.getUrl().getBase(), "LD Base URL cannot be null/blank");
        Validate.notBlank(this.getUrl().getStream(), "LD Stream URL cannot be null/blank");
        Validate.notBlank(this.getUrl().getPolling(), "LD Polling URL cannot be null/blank");
        Validate.notBlank(this.getUrl().getEvents(), "LD Analytics Events URL cannot be null/blank");

    }

    public String toString() {

        String ffCfgPropsStr = "LaunchDarklyConfigProperties=[ " + "sdkKey=XXX, " +
                //ffCfgPropsStr.append(this.sdkKey.toString());
                this.http.toString() +
                ", " +
                this.url.toString() +
                " ]";
        return ffCfgPropsStr;
    }
}
