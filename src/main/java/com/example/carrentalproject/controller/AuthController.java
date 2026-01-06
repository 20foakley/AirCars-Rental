package com.example.carrentalproject.controller;

import com.example.carrentalproject.dto.*;
import com.example.carrentalproject.security.JWTCookieService;
import com.example.carrentalproject.service.AuthService;
import com.example.carrentalproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JWTCookieService JWTCookieService;


    public AuthController(AuthenticationManager authenticationManager, AuthService authService, UserService userService, JWTCookieService JWTCookieService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.userService = userService;
        this.JWTCookieService = JWTCookieService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        System.out.println(registerRequest.getPassword());
        try {
            userService.registerUser(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        System.out.println("loginRequest password and username : " + loginRequest.getPassword() + " " + loginRequest.getUsername());
        try {
            // will do more research to see if I should move this somewhere else, but I do use it to send an error response
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // tokens contains access and refresh token
            TokensDTO tokens = authService.login(loginRequest);

            // add cookies to header
            // maybe should change the toString, but I feel cookieUtil should return Cookie, not String
            System.out.println("access token : " + tokens.getAccessToken());
            System.out.println("refresh token : " + tokens.getRefreshToken());
            System.out.println("access token cookie: " + JWTCookieService.createAccessCookie(tokens.getAccessToken()).toString());
            System.out.println("refresh token cookie: " + JWTCookieService.createRefreshCookie(tokens.getRefreshToken()).toString());
            response.addHeader(HttpHeaders.SET_COOKIE, JWTCookieService.createRefreshCookie(tokens.getRefreshToken()).toString());
            response.addHeader(HttpHeaders.SET_COOKIE, JWTCookieService.createAccessCookie(tokens.getAccessToken()).toString());


            return ResponseEntity.ok(Map.of("message", "Login successful"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }



    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

        // clear the user's active cookies
        response.addHeader(HttpHeaders.SET_COOKIE, JWTCookieService.createDeleteAccessCookie().toString());
        response.addHeader(HttpHeaders.SET_COOKIE, JWTCookieService.createDeleteRefreshCookie().toString());

        // make session invalid, and clear security context
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logged out successfully.");
    }


    // refresh short-term token.
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            TokensDTO tokens = authService.refreshUserTokens(request.getCookies(), response);
            response.addHeader(HttpHeaders.SET_COOKIE, JWTCookieService.createAccessCookie(tokens.getAccessToken()).toString());
            response.addHeader(HttpHeaders.SET_COOKIE, JWTCookieService.createRefreshCookie(tokens.getRefreshToken()).toString());
            return ResponseEntity.ok("Refresh successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }



    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || authentication.isAuthenticated() == false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = authentication.getName();
        UserDTO userDTO =  userService.getUserByUsername(username);
        return ResponseEntity.ok(userDTO);
    }


}
