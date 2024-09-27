package com.fitastic.controller;

import com.fitastic.entity.DefaultExercise;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.fitastic.service.DefaultExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// REST Controller for handling DefaultExercise-related HTTP requests
@RestController
@AllArgsConstructor
@RequestMapping("/api/defaultExercises")
public class DefaultExerciseController {

    // Service dependency for DefaultExercise operations
    private DefaultExerciseService defaultExerciseService;

    // GET endpoint to retrieve all default exercises
    @GetMapping
    public ResponseEntity<List<DefaultExercise>> getExercises() {
        List<DefaultExercise> exercises = defaultExerciseService.getAll();
        // If no exercises found, return 204 No Content
        if (exercises.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // Return the list of exercises with 200 OK status
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    // GET endpoint to retrieve a specific default exercise by ID
    @GetMapping("/{id}")
    public ResponseEntity<DefaultExercise> getExercise(@PathVariable String id) {
        try {
            // Attempt to retrieve the exercise by ID
            DefaultExercise exercise = defaultExerciseService.getDefaultExerciseById(id);
            // Return the exercise with 200 OK status
            return ResponseEntity.ok(exercise);
        } catch (RuntimeException e) {
            // If exercise not found, return 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
}