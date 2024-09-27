package com.fitastic.config;

import com.fitastic.entity.Token;
import com.fitastic.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * CustomLogoutHandler is responsible for handling user logout actions.
 * It marks the user's access token as logged out in the database when the user logs out.
 */
@Configuration
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    public CustomLogoutHandler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * Handles the logout process for a user.
     * Checks the Authorization header for a Bearer token and marks the token as logged out.
     *
     * @param request the HttpServletRequest object containing the request made by the user
     * @param response the HttpServletResponse object for the response to be sent to the user
     * @param authentication the Authentication object containing user authentication details
     */
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        boolean isAuthHeaderValid = authHeader != null && authHeader.startsWith("Bearer ");

        if (!isAuthHeaderValid) return;


        String token = authHeader.substring(7);
        Token storedToken = tokenRepository.findByAccessToken(token).orElse(null);

        boolean isTokenStored = storedToken != null;

        if (isTokenStored) {
            storedToken.setLoggedOut(true);
            tokenRepository.save(storedToken);
        }
    }
}
