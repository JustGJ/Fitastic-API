package com.fitastic.controller;

import com.fitastic.dto.UserRegisterDTO;
import com.fitastic.entity.AuthResponse;
import com.fitastic.entity.LoginResponse;
import com.fitastic.entity.User;
import com.fitastic.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user authentication.
 * Manages user registration, login, and token refresh operations.
 */
@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registers a new user.
     *
     * @param request The user registration data.
     * @return AuthResponse containing authentication information.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRegisterDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Authenticates a user based on their login information.
     *
     * @param request The user login data.
     * @return AuthResponse containing authentication information.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Refreshes the JWT token for a user.
     *
     * @param request HttpServletRequest containing request details.
     * @param response HttpServletResponse containing response details.
     * @return ResponseEntity with the refreshed token or an error.
     */
    @PostMapping("/refresh_token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.refreshToken(request, response);
    }
}