package com.example.carrentalproject.security;

import io.jsonwebtoken.Jwt;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    private final JWTUtil jwtUtil;

    public CookieUtil(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Reusable method for both types of cookies, or even creating a cookie that invalidates a session cookie.

    private ResponseCookie createCookie(String type, String token, long maxAgeMs, String path) {
        return ResponseCookie.from(type, token)
                .path(path)
                .httpOnly(true)
                .secure(true)
                .maxAge(maxAgeMs / 1000)
                .sameSite("Strict")
                .build();
    }

    // used camel case here, may have to change when using OAuth 2

    public ResponseCookie createAccessCookie(String token) {
        return createCookie("accessToken", token, jwtUtil.getAccessExpiration(), "/");
    }

    public ResponseCookie createRefreshCookie(String token) {
        return createCookie("refreshToken", token, jwtUtil.getRefreshExpiration(), "/auth/refresh");
    }

    public ResponseCookie createDeleteAccessCookie() {
        return createCookie("accessToken", "", 0, "/");
    }

    public ResponseCookie createDeleteRefreshCookie() {
        return createCookie("refreshToken", "", 0, "/auth/refresh");
    }

}
