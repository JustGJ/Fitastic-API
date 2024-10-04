package com.fitastic.controller;

import com.fitastic.dto.APIResponse;
import com.fitastic.dto.LoginRequestDTO;
import com.fitastic.dto.RegisterRequestDTO;
import com.fitastic.dto.AuthResponseDTO;
import com.fitastic.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    public static final String SUCCESS = "Success";


    /**
     * Registers a new user.
     *
     * @param registerRequestDTO The user registration data.
     * @return AuthResponse containing authentication information.
     */
    @PostMapping("/register")
    public ResponseEntity<APIResponse<AuthResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {

        AuthResponseDTO registerResponseDTO = authService.register(registerRequestDTO);

        APIResponse<AuthResponseDTO> responseDTO = APIResponse
                .<AuthResponseDTO>builder()
                .status(SUCCESS)
                .data(registerResponseDTO)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Authenticates a user based on their login information.
     *
     * @param loginRequestDTO The user login data.
     * @return AuthResponse containing authentication information.
     */
    @PostMapping("/login")
    public ResponseEntity<APIResponse<AuthResponseDTO>> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        AuthResponseDTO loginResponseDTO = authService.login(loginRequestDTO);

        APIResponse<AuthResponseDTO> responseDTO = APIResponse
                .<AuthResponseDTO>builder()
                .status(SUCCESS)
                .data(loginResponseDTO)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
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