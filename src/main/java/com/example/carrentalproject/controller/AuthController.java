package com.example.carrentalproject.controller;

import com.example.carrentalproject.dto.UserDTO;
import com.example.carrentalproject.service.UsersService;
import com.example.carrentalproject.dto.RegisterRequest;
import com.example.carrentalproject.exception.UserNotFoundException;
import com.example.carrentalproject.dto.JWTResponse;
import com.example.carrentalproject.dto.LoginRequest;
import com.example.carrentalproject.security.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;
    private final UsersService usersService;

    public AuthController(AuthenticationManager authManager, JWTUtil jwtUtil, UsersService usersService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            usersService.registerUser(
                    registerRequest.getUsername(),
                    registerRequest.getPassword(),
                    registerRequest.getEmail()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            String accessToken = jwtUtil.generateAccessToken(loginRequest.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(loginRequest.getUsername());

            // access token
            ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken",accessToken)
                    .path("/")
                    .httpOnly(true)
                    .secure(true)
                    .maxAge(jwtUtil.getAccessExpiration() / 1000)
                    .sameSite("Strict")
                    .build();

            // refresh token
            ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken",refreshToken)
                    .path("/auth/refresh")
                    .httpOnly(true)
                    .secure(true)
                    .maxAge(jwtUtil.getRefreshExpiration() / 1000)
                    .sameSite("Strict")
                    .build();

            // add cookies to header
            response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
            response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

            return ResponseEntity.ok(Map.of("message", "Login successful"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }



    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // make session invalid, and clear security context
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();


        // create expired cookies to assassinate (clear) client cookies
        // access path is "/" but refresh path is "/auth/refresh/"

        ResponseCookie accessTokenCookieAssassin = ResponseCookie.from("accessToken","")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(false) // only for not https testing
                .sameSite("Strict")
                .build();

        ResponseCookie refreshTokenCookieAssassin = ResponseCookie.from("refreshToken","")
                .path("/auth/refresh")
                .maxAge(0)
                .httpOnly(true)
                .secure(false) // only for not https testing
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookieAssassin.toString());
        response.addHeader(HttpHeaders.SET_COOKIE,refreshTokenCookieAssassin.toString());




        return ResponseEntity.ok("Logged out successfully.");
    }


    // refresh short-term token.
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String refreshToken = null;
        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                refreshToken = cookie.getValue(); // found refreshtoken
                break;
            }
        }

        if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your session has expired. Please log back in");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        String newAccessToken = jwtUtil.generateAccessToken(username); // this is the short lived token

        // create new access token cookie
        ResponseCookie newAccessTokenCookie = ResponseCookie.from("accessToken",newAccessToken)
                .path("/")
                .httpOnly(true)
                .secure(false)  // only for not https testing
                .sameSite("Strict")
                .maxAge(jwtUtil.getAccessExpiration() / 1000)
                .build();




        return ResponseEntity.ok(new JWTResponse(newAccessToken));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()  ||
                authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        String username = authentication.getName();
        return ResponseEntity.ok(Map.of("username", username));
    }


}

