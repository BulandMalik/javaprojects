package com.example.buland.springboot.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * this is the file where the action is happening in this tutorial.
 *
 * This is where Spring Boot is configured to use basic authentication.
 *
 * This is also where you can configure a hard-coded default user and password (not something I’d do in production,
 * obviously, but great for tutorials).
 *
 * Now same can be done using EnableSecurity annotation and setting SecurityFilters
 */
@Configuration
@EnableWebSecurity //The @EnableWebSecurity helps to configure the Spring security-related beans such as WebSecurityConfigurer or/and SecurityFilterChain . In the following configuration example: We have created this simple security configuration and added two demo in-memory users 'user1' and 'admin'
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfigurationUsingAnnotation {

    @Value("${spring.security.debug:false}")
    boolean securityDebug;

    /**
     * More importantly, if we want to avoid deprecation for HTTP security, we can create a SecurityFilterChain bean.
     *
     * For example, suppose we want to secure the endpoints depending on the roles, and leave an anonymous entry point only for login.
     * We'll also restrict any delete request to an admin role. We'll use Basic Authentication:
     *
     * The HTTP security will build a DefaultSecurityFilterChain object to load request matchers and filters.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE)
                .hasRole("ADMIN")
                .antMatchers("/admin/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/user/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/login/**")
                .anonymous()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }


    /**
     * given our UserDetailService, we can even set an AuthenticationManager:
     *
     * Similarly, this will work if we use JDBC or LDAP authentication.
     * @param http
     * @param bCryptPasswordEncoder
     * @param userDetailsService
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    /**
     * In Spring Security 5.4 we introduced the WebSecurityCustomizer to allow customizing WebSecurity without needing the WebSecurityConfigurerAdapter.
     *
     * Any customizations to WebSecurity should be done by exposing a WebSecurityCustomizer bean.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(securityDebug)
                .ignoring()
                .antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
    }
}



