package com.prescribenow.ffsdk.launchdarkly.exception;

public class ApplicationStartupException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String message;

    public ApplicationStartupException(String message) {
        this.message = message;
    }
}
