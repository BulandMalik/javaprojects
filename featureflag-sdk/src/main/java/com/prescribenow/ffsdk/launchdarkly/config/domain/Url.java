package com.prescribenow.ffsdk.launchdarkly.config.domain;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Url {
    private String base;
    private String stream;
    private String events;

    private String polling;

    public String getBase() {
        return base;
    }
    public void setBase(String base) {
        this.base = base;
    }
    public String getStream() {
        return stream;
    }
    public void setStream(String stream) {
        this.stream = stream;
    }
    public String getEvents() {
        return events;
    }
    public void setEvents(String events) {
        this.events = events;
    }

    public String getPolling() { return polling;}

    public void setPolling(String polling) { this.polling = polling; }

    @Override
    public String toString() {
        String urlStr = "url=[ " + "base=" +
                getBase() +
                ", stream=" +
                getStream() +
                ", pooling=" +
                getPolling() +
                ", events=" +
                getEvents() +
                " ]";
        return urlStr;
    }
}
