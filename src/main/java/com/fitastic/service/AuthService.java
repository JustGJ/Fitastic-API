package com.fitastic.service;

import com.fitastic.config.UserRegisterDTO;
import com.fitastic.entity.*;
import com.fitastic.repository.TokenRepository;
import com.fitastic.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for handling user authentication and registration.
 * It includes methods for user registration, login, token management, and token refresh.
 */
@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    /**
     * Registers a new user in the datababse.
     * If the email is already registered, it returns an error response.
     * Otherwise, the user is saved in the database, and access/refresh tokens are generated.
     *
     * @param request the user registration request containing email, username, password and confirmPassword
     * @return an AuthResponse indicating whether the registration was successful or if the user already exists
     */
    public AuthResponse register(UserRegisterDTO request) {
        String emailToRegister = request.getEmail();
        boolean isUserAlreadyExist = userRepository.findByEmail(emailToRegister).isPresent();

        if(isUserAlreadyExist) {
            return new AuthResponse("User already exist");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(request.getConfirmPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(accessToken, refreshToken, user);

        return new AuthResponse("User registration was successful");

    }

    /**
     * Authenticates the user with the provided email and password.
     * If authentication is successful, new access and refresh tokens are generated.
     *
     * @param request the user login request containing email and password
     * @return a LoginResponse containing access and refresh tokens along with a success message
     */
    public LoginResponse login(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(accessToken, refreshToken, user);

        return new LoginResponse(accessToken, refreshToken, "User login was successful");

    }

    /**
     * Revokes all valid access tokens associated with a given user.
     * This method marks each valid token as logged out (loggedOut = true)
     * to prevent its future use.
     *
     * @param user the user whose valid tokens are to be revoked
     */
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(validToken -> {
            validToken.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    /**
     * Saves a new access and refresh token for the specified user in the token repository.
     * Marks the tokens as active by setting the "loggedOut" flag to false.
     *
     * @param accessToken the newly generated access token
     * @param refreshToken the newly generated refresh token
     * @param user the user for whom the tokens are being saved
     */
    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    /**
     * Refreshes the access token for a user based on the provided refresh token.
     * This method extracts the refresh token from the authorization header,
     * validates it, and generates new access and refresh tokens if valid.
     *
     * @param request  the HttpServletRequest containing the authorization header
     * @param response the HttpServletResponse to be used for response
     * @return a ResponseEntity containing the new tokens or an unauthorized status
     */
    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        boolean isValidHeader = authHeader == null || !authHeader.startsWith("Bearer ");

        if (isValidHeader) return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        String token = authHeader.substring(7);
        String email = jwtService.extractUserEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No user found"));

        boolean isValidRefreshToken = jwtService.isValidRefreshToken(token, user);

        if(isValidRefreshToken) {
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity(new LoginResponse(accessToken, refreshToken, "New token generated"), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }
}