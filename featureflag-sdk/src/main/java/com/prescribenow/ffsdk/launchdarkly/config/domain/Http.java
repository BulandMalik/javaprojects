package com.prescribenow.ffsdk.launchdarkly.config.domain;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Http {
    private String connectTimeout = "3";
    private String readTimeout = "3";

    private String eventsFlushInterval = "3";

    public String getConnectTimeout() {
        return connectTimeout;
    }
    public void setConnectTimeout(String connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
    public String getReadTimeout() {
        return readTimeout;
    }
    public void setReadTimeout(String readTimeout) {
        this.readTimeout = readTimeout;
    }

    public String getEventsFlushInterval() { return eventsFlushInterval; }

    public void setEventsFlushInterval(String eventsFlushInterval) { this.eventsFlushInterval = eventsFlushInterval; }

    @Override
    public String toString() {
        String httpString = "http=[ " + "connectTimeout=" +
                getConnectTimeout() +
                ", readTimeout=" +
                getReadTimeout() +
                ", eventsFlushInterval=" +
                getEventsFlushInterval() +
                " ]";
        return httpString;
    }
}
