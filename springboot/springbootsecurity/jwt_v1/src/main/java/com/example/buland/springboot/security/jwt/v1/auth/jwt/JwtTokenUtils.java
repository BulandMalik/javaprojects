package com.example.buland.springboot.security.jwt.v1.auth.jwt;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

import com.example.buland.springboot.security.jwt.v1.entities.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;

@Component
@Slf4j
public class JwtTokenUtils {
    //private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
    private static final long EXPIRE_DURATION = 24 * 60 * 60; // Seconds for 24 hour
    /*1 day = 24 hours
    1 hour = 60 mins
    1 min = 60 sec
    1 sec = 1000 ms*/

    @Value("${app.jwt.secret.signKey}")
    private String secretSignKey;

    // Replace "publicKey" with your actual public key for encryption (RSA) or your shared secret key for symmetric encryption (AES)
    @Value("${app.jwt.secret.encryptKey}")
    private String secretEncryptKey;

    @Value("${app.jwt.expiryInSeconds:"+EXPIRE_DURATION+"}")
    private Long tokenExpiryInSeconds;

    /**
     * Here, the generateAccessToken() method creates a JSON Web Token with the following details:
     *
     * Subject is combination of the user’s ID and email, separated by a comma.
     * Issuer name is CodeJava
     * The token is issued at the current date and time
     * The token should expire after 24 hours
     * The token is signed using a secret key, which you can specify in the application.properties file or from system environment variable. And the signature algorithm is HMAC using SHA-512.
     *
     * @param user
     * @return
     */
    public String generateAccessToken(User user) {

        Map<String, Object> claims = new HashMap<String, Object>();
/*        StringBuffer roles = new StringBuffer("");
        user.getAuthorities().stream().map( authority -> roles.append(authority).append(","));
        claims.put("roles",roles.substring(0, roles.length()-1));*/
        //claims.put("roles","admin,user");
        claims.put("roles", Arrays.asList("admin","user"));

        // // Create JWS (JSON Web Signature) token
        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
                .setIssuer("Buland Malik Inc.")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (tokenExpiryInSeconds * 1000) ))
                .signWith(SignatureAlgorithm.HS512, secretSignKey)
                .compact();

        return jwtToken;
        /*
        // Encrypt the JWS token using JWE (JSON Web Encryption)
        return Jwts.builder()
                .setPayload(jwtToken)
                //.encryptWith(getEncryptionKey()) // Encrypt the signed JWT payload
                .compact();*/
    }


    public String generateAccessToken(Map<String, Object> claims) {

        claims.put("roles", Arrays.asList("admin","user"));

        // // Create JWS (JSON Web Signature) token
        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(String.format("%s,%s", claims.get("sourceSystem"), claims.get("customerId")))
                .setIssuer("Buland Malik Inc.")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (tokenExpiryInSeconds * 1000) ))
                .signWith(SignatureAlgorithm.HS512, secretSignKey)
                .compact();

        return jwtToken;
    }

    private Key getEncryptionKey() {
        // Replace this with your actual public key for encryption (RSA) or shared secret key for symmetric encryption (AES)
        return new SecretKeySpec(secretEncryptKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * retrieve username from jwt token
     * @param token
     *
     * @return
     */
    public String getUsernameFromToken(String token) {
        String jwtSubject = getClaimFromToken(token, Claims::getSubject);
        return jwtSubject.split(",")[1];

    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        log.info("claims[",claims,"]");
        return claimsResolver.apply(claims);
    }

    /**
     * for retrieveing any information from token we will need the secret key
     *
     * @param token
     * @return
     */
    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secretSignKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            log.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            log.error("Signature validation failed");
        }

        return null;
    }

    /**
     * retrieve expiration date from jwt token
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * check if the token has expired
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * validate token
     * @param token
     * @param jwtUserDetailsService
     * @return
     */
    public Boolean validateToken(String token, JwtUserDetailsService jwtUserDetailsService) {
        final String username = getUsernameFromToken(token);
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getSubject(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }
}
