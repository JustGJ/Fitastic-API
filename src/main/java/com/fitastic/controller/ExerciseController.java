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

    @GetMapping("/exercises")
    public List<Exercise> getExercises() {
        return exerciseService.getAll();
    }

    @PostMapping("/exercise")
    public ResponseEntity<Exercise> create(@RequestBody Exercise product) {
        product = exerciseRepository.save(product);
        return ResponseEntity.created(URI.create("/exercise/" + product.getId()))
                .body(product);
    }

    @GetMapping("/exercises/{name}")
    public ResponseEntity<Exercise> getExercise(@PathVariable String name) {
        try{
            Exercise exercise = exerciseService.getExerciseByName(name);
            return ResponseEntity.ok(exercise);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/exercises/{id}")
    public ResponseEntity<Exercise> delete(@PathVariable String id) {
         try{
            exerciseService.deleteExerciseById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
