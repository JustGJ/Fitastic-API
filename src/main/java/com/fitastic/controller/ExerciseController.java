package com.fitastic.controller;

import com.fitastic.repository.ExerciseRepository;
import com.fitastic.entity.Exercise;
import com.fitastic.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
public class ExerciseController {


    private ExerciseRepository exerciseRepository;
    private ExerciseService exerciseService;

    @PostMapping("/exercise")
    public ResponseEntity<Exercise> create(@RequestBody Exercise product) {
        product = exerciseRepository.save(product);
        return ResponseEntity.created(URI.create("/exercise/" + product.getId()))
                .body(product);
    }

    @GetMapping("/exercises")
    public List<Exercise> getExercises() {
        return exerciseService.getAll();
    }
}
