package com.fitastic.controller;

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

// REST Controller for handling UserExercise-related HTTP requests
@RestController
@AllArgsConstructor
@RequestMapping("/api/userExercises")
public class UserExerciseController {

    private UserExerciseRepository userExerciseRepository;
    private UserExerciseService userExerciseService;

    // GET endpoint to retrieve all user exercises
    @GetMapping
    public ResponseEntity<List<UserExercise>> getAllUserExercises() {
        List<UserExercise> exercises = userExerciseService.getAll();
        if (exercises.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    // POST endpoint to create a new user exercise
    @PostMapping
    public ResponseEntity<UserExercise> createUserExercise(@Valid @RequestBody UserExercise product) {
        UserExercise savedProduct = userExerciseService.createUserExercise(product);
        return ResponseEntity.created(URI.create("/exercise/" + savedProduct.getId()))
                .body(savedProduct);
    }

    // PATCH endpoint to update an existing user exercise
    @PatchMapping("/{id}")
    public ResponseEntity<UserExercise> updateUserExercise(@PathVariable String id, @RequestBody UserExercise exerciseUpdate) {
        UserExercise exercise = userExerciseService.updateUserExercise(id, exerciseUpdate);
        if (exercise == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(exercise);
    }

    // GET endpoint to retrieve a specific user exercise by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserExercise> getUserExercise(@PathVariable String id) {
        try {
            UserExercise exercise = userExerciseService.getUserExerciseById(id);
            return ResponseEntity.ok(exercise);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE endpoint to remove a user exercise by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<UserExercise> deleteUserExercise(@PathVariable String id) {
        try {
            userExerciseService.deleteUserExerciseById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
