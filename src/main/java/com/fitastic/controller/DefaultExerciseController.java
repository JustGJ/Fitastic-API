package com.fitastic.controller;

import com.fitastic.entity.DefaultExercise;
import com.fitastic.entity.UserExercise;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.fitastic.service.DefaultExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/defaultExercises")
public class DefaultExerciseController {

    private DefaultExerciseService DefaultExerciseService;

    @GetMapping
    public ResponseEntity<List<DefaultExercise>> getAllDefaultExercises() {
        List<DefaultExercise> exercises = DefaultExerciseService.getAll();
        if (exercises.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultExercise> getDefaultExercise(@PathVariable String id) {
        try{
            DefaultExercise exercise = DefaultExerciseService.getDefaultExerciseById(id);
            return ResponseEntity.ok(exercise);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
