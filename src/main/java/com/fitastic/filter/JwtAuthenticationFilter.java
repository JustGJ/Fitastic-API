package com.fitastic.filter;

import com.fitastic.service.JwtService;
import com.fitastic.service.UserDetailsImpService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A filter that checks for JWT authentication in incoming requests.
 * This filter extends OncePerRequestFilter, ensuring that it is executed
 * once per request and performs authentication based on JWT tokens.
 */
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsImpService userDetailsImpService;

    /**
     * Performs the filtering of incoming requests to check for JWT authentication.
     * Extracts the token from the Authorization header, validates it, and
     * sets the authentication in the SecurityContext if valid.
     *
     * @param request the HttpServletRequest object containing the request made by the user
     * @param response the HttpServletResponse object for the response to be sent to the user
     * @param filterChain the FilterChain to continue the request-response flow
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs during the request processing
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        boolean isAuthHeaderValid = authHeader != null && authHeader.startsWith("Bearer ");

        if (!isAuthHeaderValid) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUserEmail(token);

        // Check if username is present and if the user is not already authenticated
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication() != null;

        if (username != null && !isAuthenticated) {

            UserDetails userDetails = userDetailsImpService.loadUserByUsername(username);

            boolean isValidToken = jwtService.isValid(token, userDetails);

            if (isValidToken) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
