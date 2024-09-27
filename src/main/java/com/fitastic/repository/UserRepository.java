
package com.fitastic.repository;

import com.fitastic.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface for managing User entities in MongoDB.
 * This interface extends MongoRepository, providing CRUD operations
 * and custom query methods for User entities.
 */
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
