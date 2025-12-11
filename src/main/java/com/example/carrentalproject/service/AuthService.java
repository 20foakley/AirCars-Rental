package com.example.carrentalproject.service;

import com.example.carrentalproject.dto.LoginRequest;
import com.example.carrentalproject.dto.TokensDTO;
import com.example.carrentalproject.security.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // Any non-HTTP auth logic goes here

    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;

    public AuthService(AuthenticationManager authManager, JWTUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;

    }

    public TokensDTO login(LoginRequest loginRequest) throws AuthenticationException {
        // We have already checked the Authentication object within AuthController
        String accessToken = jwtUtil.generateAccessToken(loginRequest.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(loginRequest.getUsername());
        System.out.println("accessToken : " + accessToken);
        System.out.println("refreshToken : " + refreshToken);
        return new TokensDTO(accessToken,refreshToken);
    }

    public TokensDTO refreshUserTokens(Cookie[] cookies, HttpServletResponse response){
        if(cookies == null || cookies.length ==0){
            throw new IllegalArgumentException("Cookies are empty.");
        }

        String refreshToken = null;

        for (Cookie cookie : cookies) {
            if("refreshToken".equals(cookie.getName())) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        if(refreshToken == null || jwtUtil.validateToken(refreshToken) == false) {
            throw new IllegalArgumentException("Invalid or expired refresh token.");
        }

        String username = jwtUtil.extractUsername(refreshToken);

        String newAccessToken = jwtUtil.generateAccessToken(username);
        String newRefreshToken = jwtUtil.generateRefreshToken(username);
        return new TokensDTO(newAccessToken,newRefreshToken);

    }


}
