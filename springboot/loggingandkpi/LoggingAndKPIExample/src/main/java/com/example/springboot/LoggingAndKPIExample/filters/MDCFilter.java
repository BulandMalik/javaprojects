package com.example.springboot.LoggingAndKPIExample.filters;

import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.springboot.LoggingAndKPIExample.config.MDCFilterConfiguration;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A servlet that adds a key to the Mapped Diagnostic Context (MDC) to each request so you can print a unique id in the logg messages of each
 * request.
 *
 * It also add the key as a header in the response so the caller of the request can provide you the id to browse the logs.
 *
 * @see com.example.springboot.LoggingAndKPIExample.config.MDCFilterConfiguration
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class MDCFilter extends OncePerRequestFilter {

    private final String responseHeader;
    private final String pnTidKey;
    private final String clientIpKey;
    private final String requestHeader;

    public MDCFilter() {
        responseHeader = MDCFilterConfiguration.DEFAULT_RESPONSE_TID_HEADER;
        pnTidKey = MDCFilterConfiguration.DEFAULT_PN_TID_KEY;
        clientIpKey = MDCFilterConfiguration.DEFAULT_CLIENT_IP_KEY;
        requestHeader = null;
    }

    public MDCFilter(final String responseHeader, final String pnTidKey, final String clientIpKey, final String requestHeader) {
        this.responseHeader = responseHeader;
        this.pnTidKey = pnTidKey;
        this.clientIpKey = clientIpKey;
        this.requestHeader = requestHeader;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws java.io.IOException, ServletException {
        try {
            final String pnTid = extractPnTid(request);
            final String clientIP = extractClientIP(request);
            MDC.put(clientIpKey, clientIP);
            MDC.put(pnTidKey, pnTid);
            if (StringUtils.hasText(responseHeader)) {
                response.addHeader(responseHeader, pnTid);
            }
            chain.doFilter(request, response);
        } finally {
            MDC.remove(pnTidKey);
            MDC.remove(clientIpKey);
        }
    }

    private String extractPnTid(final HttpServletRequest request) {
        final String tid;
        if (StringUtils.hasText(pnTidKey) && StringUtils.hasText(request.getHeader(pnTidKey))) {
            tid = request.getHeader(pnTidKey);
        } else {
            tid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        }
        return tid;
    }

    private String extractClientIP(final HttpServletRequest request) {
        final String clientIP;
        //if (request.getHeader("X-Forwarded-For") != null) {
        if (StringUtils.hasText(request.getHeader("X-Forwarded-For"))) {
                clientIP = request.getHeader("X-Forwarded-For").split(",")[0];
            } else {
                clientIP = request.getRemoteAddr();
            }
        return clientIP;
    }

    @Override
    protected boolean isAsyncDispatch(final HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}