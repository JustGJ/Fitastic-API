package com.fitastic.controller;

import com.fitastic.dto.APIResponse;
import com.fitastic.entity.User;
import com.fitastic.entity.UserExercise;
import com.fitastic.repository.UserExerciseRepository;
import com.fitastic.service.UserExerciseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controller for handling UserExercise-related operations.
 * Manages CRUD operations for user exercises, including retrieval, creation, update, and deletion.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/userExercises")
public class UserExerciseController {

    private UserExerciseRepository userExerciseRepository;
    private UserExerciseService userExerciseService;

    public static final String SUCCESS = "SUCCESS";

    /**
     * Retrieves all user exercises.
     *
     * @return ResponseEntity containing a list of UserExercise objects or NO_CONTENT if empty.
     */
    @GetMapping
    public ResponseEntity<List<UserExercise>> getAllUserExercises() {
        List<UserExercise> exercises = userExerciseService.getAll();
        if (exercises.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    /**
     * Creates a new user exercise.
     *
     * @param product The UserExercise object to be created.
     * @return ResponseEntity containing the created UserExercise and CREATED status.
     */
    @PostMapping
    public ResponseEntity<UserExercise> createUserExercise(@Valid @RequestBody UserExercise product) {
        UserExercise savedProduct = userExerciseService.createUserExercise(product);
        return ResponseEntity.created(URI.create("/exercise/" + savedProduct.getId()))
                .body(savedProduct);
    }

    /**
     * Updates an existing user exercise.
     *
     * @param id The ID of the user exercise to update.
     * @param exerciseUpdate The UserExercise object containing update information.
     * @return ResponseEntity containing the updated UserExercise or NOT_FOUND if not exists.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserExercise> updateUserExercise(@PathVariable String id, @RequestBody UserExercise exerciseUpdate) {
        UserExercise exercise = userExerciseService.updateUserExercise(id, exerciseUpdate);
        if (exercise == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(exercise);
    }


    /**
     * Retrieves a specific user exercise by its ID.
     *
     * @param id The ID of the user exercise.
     * @return ResponseEntity with the UserExercise or 404 NOT_FOUND if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserExercise(@PathVariable String id) {
        UserExercise userExercise = userExerciseService.getUserExerciseById(id);

        APIResponse<UserExercise> response = APIResponse
                .<UserExercise>builder()
                .status(SUCCESS)
                .data(userExercise)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Deletes a specific UserExercise by its ID.
     *
     * @param id The ID of the UserExercise to delete.
     * @return ResponseEntity with APIResponse indicating success or failure.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<?>> deleteUserExercise(@PathVariable String id) {
        userExerciseService.deleteUserExerciseById(id);
        APIResponse<Void> response = APIResponse
                .<Void>builder()
                .status("SUCCESS")
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}