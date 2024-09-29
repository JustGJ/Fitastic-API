package com.fitastic.controller;

import com.fitastic.entity.DefaultExercise;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.fitastic.service.DefaultExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller for handling DefaultExercise-related operations.
 * Manages retrieval of default exercises and individual exercise details.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/defaultExercises")
public class DefaultExerciseController {

    private final DefaultExerciseService defaultExerciseService;

    /**
     * Retrieves all default exercises.
     *
     * @return ResponseEntity containing a list of DefaultExercise objects or NO_CONTENT if empty.
     */
    @GetMapping
    public ResponseEntity<List<DefaultExercise>> getExercises() {
        List<DefaultExercise> exercises = defaultExerciseService.getAll();
        if (exercises.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    /**
     * Retrieves a specific default exercise by its ID.
     *
     * @param id The ID of the default exercise to retrieve.
     * @return ResponseEntity containing the requested DefaultExercise or NOT_FOUND if not exists.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DefaultExercise> getExercise(@PathVariable String id) {
        try {
            DefaultExercise exercise = defaultExerciseService.getDefaultExerciseById(id);
            return ResponseEntity.ok(exercise);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}