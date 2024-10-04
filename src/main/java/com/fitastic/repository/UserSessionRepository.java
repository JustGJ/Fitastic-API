package com.fitastic.repository;

import com.fitastic.entity.UserSession;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for managing UserSession entities in MongoDB.
 * This interface extends MongoRepository, providing CRUD operations
 * and custom query methods for UserSession entities.
 */

public interface UserSessionRepository extends MongoRepository<UserSession, String> {
}
