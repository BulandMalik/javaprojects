package com.example.buland.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

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
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * This is where we cofigure the Basic Auth. http object being used with its fluent interface to tell spring to authenticate all requests and use HTTP basic authentication.
     *
     * This is pretty trivial, but there’s tons of power here in this interface as you’ll see in a minute.
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            /*.authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .antMatchers("/board/*").hasAnyRole("MEMBER", "BOARD")
                    .antMatchers("/members/*").hasRole("MEMBER")
                    .antMatchers("/").permitAll()
            )*/
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            //.realmName("My Org Name"); //These realms allow the protected resources on a server to be partitioned into a set of protection spaces, each with its own authentication scheme and/or authorization database. – RFC 2617
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * The configure() method is mostly a hack for this tutorial to create a user in an in-memory authentication manager.
     *
     * You’re creating a user with username user and password pass. The user has the USER role assigned to it.
     *
     * The following configuration will create an in-memory user using the NoOpPasswordEncoder This is a password
     * encoder that does nothing and is useful for testing but should NOT be used in production.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user")
            .password("{noop}pass") // Spring Security 5 requires specifying the password storage format
            .roles("USER");
    }
    
}



