package com.example.buland.springboot.security;

import com.example.buland.springboot.security.jwt.v1.auth.jwt.JwtTokenFilter;
import com.example.buland.springboot.security.jwt.v1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

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

    private UserRepository userRepo;

    private JwtTokenFilter jwtTokenFilter;

    public SecurityConfigurationUsingAnnotation(UserRepository userRepo, JwtTokenFilter jwtTokenFilter) {
        this.userRepo = userRepo;
        this.jwtTokenFilter = jwtTokenFilter;
    }

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
/*
        http.csrf().disable();
        http.authorizeRequests().anyRequest().permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
*/
        http.csrf()
                .disable() //WARNING: Never disable CSRF protection while leaving session management enabled! Doing so will open you up to a Cross-Site Request Forgery attack.
                //.authorizeRequests()
                //.anyRequest().permitAll()
                .authorizeRequests()
                .antMatchers("/signup","/auth/login", "/docs/**", "/users").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /**
         * the exception handling code ensures that the server will return HTTP status 401 (Unauthorized) if any error occurs during
         * authentication process.
         */
        http.exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                );

        //And the last line adds our custom filter before the UsernameAndPasswordAuthenticationFilter in Spring Security filters chain.
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /**
     * given our UserDetailService, we can even set an AuthenticationManager:
     *
     * Similarly, this will work if we use JDBC or LDAP authentication.
     * @param authConfig
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

/*
    // functional interface and provide implementation as a lambda expression.
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepo.findByEmail(username)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("User " + username + " not found"));
            }
        };
    }
*/

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}