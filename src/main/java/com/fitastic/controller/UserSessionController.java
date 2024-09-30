package com.fitastic.controller;


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
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
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

}
