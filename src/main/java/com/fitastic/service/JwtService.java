package com.fitastic.service;

import com.fitastic.entity.User;
import com.fitastic.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

/**
 * Service class responsible for handling JWT token creation, validation, and extraction of claims.
 * This service interacts with tokens, ensuring their validity and managing access/refresh tokens for users.
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.access-token-expiration}")
    private long accessTokenExpire;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpire;


    private final TokenRepository tokenRepository;


    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token the JWT token from which the username will be extracted
     * @return the username (subject) contained in the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Checks if the provided JWT token is expired.
     *
     * @param token the JWT token to be checked
     * @return true if the token has expired, false otherwise
     */
    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);

        boolean validToken = tokenRepository
                .findByAccessToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }

    /**
     * Validates whether the provided refresh token is still valid and belongs to the given user.
     * It also checks whether the token is marked as "logged out" in the token repository.
     *
     * @param token the refresh token to be validated
     * @param user  the user to whom the refresh token is associated
     * @return true if the refresh token is valid, false otherwise
     */
    public boolean isValidRefreshToken(String token, User user) {
        String username = extractUsername(token);

        boolean validRefreshToken = tokenRepository
                .findByRefreshToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validRefreshToken;
    }

    /**
     * Checks if the provided JWT token is expired.
     *
     * @param token the JWT token to be checked
     * @return true if the token has expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date of the provided JWT token.
     *
     * @param token the JWT token from which the expiration date will be extracted
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the JWT token using the provided function.
     *
     * @param token   the JWT token from which the claim will be extracted
     * @param resolver a function that resolves the claim from the token's claims
     * @param <T>     the type of the claim to be extracted
     * @return the extracted claim
     */

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Extracts all claims from the provided JWT token.
     *
     * @param token the JWT token from which the claims will be extracted
     * @return the claims contained in the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generates a new access token for the specified user.
     * The access token's expiration time is set using the value from the application properties.
     *
     * @param user the user for whom the access token is being generated
     * @return the newly generated access token
     */
    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpire);
    }

    /**
     * Generates a new refresh token for the specified user.
     * The refresh token's expiration time is set using the value from the application properties.
     *
     * @param user the user for whom the refresh token is being generated
     * @return the newly generated refresh token
     */
    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpire );
    }

    /**
     * Generates a JWT token for the specified user with a custom expiration time.
     *
     * @param user       the user for whom the token is being generated
     * @param expireTime the expiration time in milliseconds for the token
     * @return the generated token
     */
    private String generateToken(User user, long expireTime) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime ))
                .signWith(getSigninKey())
                .compact();
    }

    /**
     * Retrieves the signing key used for token signature verification.
     * The key is decoded from the base64-encoded secret key.
     *
     * @return the secret key used for signing the tokens
     */
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}