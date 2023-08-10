package com.example.buland.springboot.security.jwt.v1.auth.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.buland.springboot.security.jwt.v1.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * OncePerRequestFilter is a class provided by Spring Security that ensures a filter is only executed once for each request.
 * It's an abstract base class that you can extend to create custom filters that need to be executed only once during the processing
 * of a single HTTP request.
 *
 * This is particularly useful to avoid duplicate filter execution, which can lead to unexpected behavior or performance issues.
 * When you extend OncePerRequestFilter, Spring Security takes care of managing the execution of the filter chain and ensures that your
 * custom filter is invoked exactly once for each incoming request.
 *
 * By extending OncePerRequestFilter, you ensure that your filter is invoked only once per request, regardless of how many times the
 * filter chain is triggered by other components.
 *
 * In the context of Spring Security, OncePerRequestFilter is commonly used for tasks like token validation, authorization, or any other
 * processing that needs to be done for each incoming request.
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtils jwtUtil;

    private JwtUserDetailsService userDetailsService;

    public JwtTokenFilter(JwtTokenUtils jwtUtil,JwtUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getAccessToken(request);

        if (!jwtUtil.validateToken(token, userDetailsService)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }

    /**
     * JWT Token is in the form "Bearer token" so we validate it if it starts with Bearer as part of Authorization header.
     *
     * @param request
     *
     * @return True/False
     */
    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }

        return true;
    }

    /**
     * Get Bearer Token which comes in the form `Authorization: Bearer xZcasdtyp098A` so we try to get only the token.
     *
     * @param request
     *
     * @return token as a string
     */
    private String getAccessToken(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader("Authorization");
        //String token = requestTokenHeader.substring(7);
        String token = requestTokenHeader.split(" ")[1].trim();
        return token;
    }

    /**
     * configure Spring Security to manually set authentication
     *
     * @param token
     * @param request
     */
    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String token) {
        User appUser = new User();
        String[] jwtSubject = jwtUtil.getSubject(token).split(",");

        try {
            appUser.setId(Integer.parseInt(jwtSubject[0]));
        }
        catch(NumberFormatException nfe) {
            appUser.setId(-1);
        }

        appUser.setEmail(jwtSubject[1]);

        List<String> roles = jwtUtil.getRoles(token);

        org.springframework.security.core.userdetails.User userDetails = null;
        if (appUser.getEmail() != null && roles != null) {
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            userDetails = new org.springframework.security.core.userdetails.User(appUser.getEmail(), "admin123111"/*appUser.getPassword()*/, authorities);
        }
        else {
            userDetails = new org.springframework.security.core.userdetails.User(appUser.getEmail(), appUser.getPassword(), null);
        }
        return userDetails;
    }

}