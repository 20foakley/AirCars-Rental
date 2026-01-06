package com.example.carrentalproject.security;

import com.example.carrentalproject.service.MyUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

// I created this utility class for creating JWTs,
// whether they be an access or refresh.
// For documentation my access expiration time is 15 minutes, while my refresh expiration time is 7 days
    // (I chose 15 minutes so that if an attacker were to steal jwt, they only have a short window to do damage)


// Note that "parseClaimsJws" will throw exception on invalid token

@Component
public class JWTUtil {

    private final Key key;
    private final long accessExpiration;
    private final long refreshExpiration;
    public MyUserDetailsService myUserDetailsService;   // I might plan on removing this dependency

    public JWTUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.accessExpirationMs}") long accessExpiration,
                   @Value("${jwt.refreshExpirationMs}") long refreshExpiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String generateToken(String username,long durationMs, String type) {
        // create signed token that contains user's identity (username)
        // This app uses for refresh tokens as well as access tokens - specify duration to differentiate
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + durationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .claim("type",type)
                .compact();
    }

    public String generateAccessToken(String username) {
        return generateToken(username, accessExpiration,"access");
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, refreshExpiration,"refresh");
    }

    public boolean validateToken(String token) {
        // check token's signature and expiration
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                // get username from key so we can identify the user that is making a request
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public long getAccessExpiration() {
        return accessExpiration;
    }

    public long getRefreshExpiration() {
        return refreshExpiration;
    }




}
