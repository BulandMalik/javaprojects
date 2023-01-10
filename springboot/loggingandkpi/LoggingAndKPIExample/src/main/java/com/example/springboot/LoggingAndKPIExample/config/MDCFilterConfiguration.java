package com.example.springboot.LoggingAndKPIExample.config;

import com.example.springboot.LoggingAndKPIExample.filters.MDCFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * The class that configures a servlet that adds a key to the Mapped Diagnostic Context (MDC) to each request so you can print a pn_tid
 * (prescribenow transaction id) in the logg messages of each request. It also add the key as a header in the response so the caller of
 * the request can provide you the id to browse the logs.
 * Set the response header name to null/blank if you want the response to NOT include such header.
 *
 * If you provide a request header name, the filter will check first if the request contains a header with that name and will use the ID it
 * provides. This is useful if your application chain has already assigned an id to the "transaction".
 *
 * Here's a configuration sample with the default values:
 *
 * <pre>
 * summer:
 *   pn.mdc.logging:
 *     responseHeader: response_token
 *     tid_key: pn_tid
 *     client_ip_key: client_IP
 *     request_header:
 * </pre>
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "pn.mdc.logging")
public class MDCFilterConfiguration {

    public static final String DEFAULT_RESPONSE_TID_HEADER = "pn_tid";
    public static final String DEFAULT_PN_TID_KEY = "pn_tid";
    public static final String DEFAULT_CLIENT_IP_KEY = "client_ip";

    private String responseHeader = DEFAULT_RESPONSE_TID_HEADER;
    private String pnTidKey = DEFAULT_PN_TID_KEY;
    private String clientIpKey = DEFAULT_CLIENT_IP_KEY;
    private String requestHeader = null;

    @Bean
    public FilterRegistrationBean<MDCFilter> servletRegistrationBean() {
        final FilterRegistrationBean<MDCFilter> registrationBean = new FilterRegistrationBean<>();
        final MDCFilter log4jMDCFilterFilter = new MDCFilter(responseHeader, pnTidKey, clientIpKey, requestHeader);
        registrationBean.setFilter(log4jMDCFilterFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
