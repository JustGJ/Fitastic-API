package com.fitastic.repository;

import com.fitastic.entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Token entities in MongoDB.
 * This interface extends MongoRepository, providing CRUD operations
 * and custom query methods for Token entities.
 */
public interface TokenRepository extends MongoRepository<Token, String> {
    List<Token> findAllAccessTokensByUser(String userId);
    Optional<Token> findByAccessToken(String token);
    Optional<Token > findByRefreshToken(String token);
}