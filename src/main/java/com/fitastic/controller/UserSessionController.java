package com.fitastic.controller;

import com.fitastic.entity.User;
import com.fitastic.entity.UserSession;
import com.fitastic.service.UserSessionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controller for handling UserSession-related operations.
 * Manages CRUD operations for userSession, including retrieval, creation, update, and deletion.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/userSessions")
public class UserSessionController {

    private UserSessionService userSessionService;

    /**
     * Retrieves all user Sessions.
     *
     * @return ResponseEntity containing a list of UserSession objects or NO_CONTENT if empty.
     */
    @GetMapping
    public ResponseEntity<List<UserSession>> getAllUserSessions(){
        List<UserSession> userSessions = userSessionService.getAllUserSessions();
        if(userSessions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userSessions);
    }

    /**
     * Creates a new user session.
     *
     * @param userSession The UserSession object to be created.
     * @return ResponseEntity containing the created UserSession and CREATED status.
     */
    @PostMapping
    public ResponseEntity<UserSession> createUserSession(@Valid @RequestBody UserSession userSession){
        UserSession newUserSession = userSessionService.createUserSession(userSession);
        return ResponseEntity.created(URI.create("/session/" + newUserSession.getId()))
                .body(newUserSession);
    }

    /**
     * Retrieves a specific user session by its ID.
     *
     * @param id The ID of the user session to retrieve.
     * @return ResponseEntity containing the requested UserSession or NOT_FOUND if not exists.
     */

    @GetMapping("/{id}")
    public ResponseEntity<UserSession> getUserSession(@PathVariable String id) {
        try {
            UserSession userSession = userSessionService.getUserSessionById(id);
            return ResponseEntity.ok(userSession);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing user exercise.
     *
     * @param id The ID of the user session to update.
     * @param updatedUserSession The UserSession object containing update information.
     * @return ResponseEntity containing the updated UserSession or NOT_FOUND if not exists.
     */

    @PatchMapping("/{id}")
    public ResponseEntity<UserSession> updateUserSession(@PathVariable String id, @RequestBody UserSession updatedUserSession) {
        UserSession userSession = userSessionService.updateUserSession(id, updatedUserSession);
        if(userSession == null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userSession);
    }

    /**
     * Deletes a user session by its ID.
     *
     * @param id The ID of the user session to delete.
     * @return ResponseEntity with NO_CONTENT if successful, or NOT_FOUND if the session doesn't exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<UserSession> deleteUserSession(@PathVariable String id){
        try{
            userSessionService.deleteUserSessionById(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
