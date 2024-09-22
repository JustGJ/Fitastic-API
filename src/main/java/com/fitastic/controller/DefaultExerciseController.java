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
    public ResponseEntity<List<DefaultExercise>> getExercises() {
        List<DefaultExercise> exercises = DefaultExerciseService.getAll();
        if (exercises.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultExercise> getExercise(@PathVariable String id) {
        try{
            DefaultExercise exercise = DefaultExerciseService.getDefaultExerciseById(id);
            return ResponseEntity.ok(exercise);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
