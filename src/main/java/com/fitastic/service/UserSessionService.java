package com.fitastic.service;

import com.fitastic.entity.UserSession;
import com.fitastic.repository.UserSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling UserSession-related business logic.
 * Manages CRUD operations for user Session, including retrieval, creation, update, and deletion.
 */

@Service
@AllArgsConstructor
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;

    /**
     * Retrieves all UserSession from the database.
     *
     * @return List of all UserSession objects.
     */
    public List<UserSession> getAllUserSessions() {
        return userSessionRepository.findAll();
    }

    /**
     * Creates a new UserExercise in the database.
     *
     * @param userSession The UserSession object to be created.
     * @return The created UserSession object.
     */
    public UserSession createUserSession(UserSession userSession) {
        return userSessionRepository.save(userSession);
    }

    public UserSession getUserSessionById(String id) {
        Optional<UserSession>  userSession = userSessionRepository.findById(id);
        return userSession.orElse(null);
    }

    public UserSession updateUserSession(String id, UserSession updatedUserSession) {
        UserSession userSession = this.getUserSessionById(id);
        userSession.setName(updatedUserSession.getName());
        userSession.setUserSessionDateId(updatedUserSession.getUserSessionDateId());
        userSession.setUserId(updatedUserSession.getUserId());
        return userSessionRepository.save(userSession);
    }

    public void deleteUserSessionById(String id){
        if(userSessionRepository.existsById(id)) {
            userSessionRepository.deleteById(id);
        }else {
            throw new RuntimeException("UserSession not found with id: " + id);
        }
    }
}
