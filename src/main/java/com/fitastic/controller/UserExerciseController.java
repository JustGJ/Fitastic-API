package com.fitastic.controller;

import com.fitastic.entity.UserExercise;
import com.fitastic.repository.UserExerciseRepository;
import com.fitastic.service.UserExerciseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/userExercises")
public class UserExerciseController {

    private UserExerciseRepository userExerciseRepository;
    private UserExerciseService userExerciseService;

    @GetMapping
    public List<UserExercise> getAllUserExercises() {
        return userExerciseService.getAll();
    }

    @PostMapping
    public ResponseEntity<UserExercise> createUserExercise(@Valid @RequestBody UserExercise product) {
        product = userExerciseRepository.save(product);
        return ResponseEntity.created(URI.create("/exercise/" + product.getId()))
                .body(product);
    }

    @PatchMapping("{id}")
    public ResponseEntity<UserExercise> updateUserExercise(@PathVariable String id, @RequestBody UserExercise exerciseUpdate) {
        UserExercise exercise = userExerciseService.updateExercise(id, exerciseUpdate);
        if (exercise == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(exercise);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserExercise> getUserExercise(@PathVariable String id) {
        try{
            UserExercise exercise = userExerciseService.getExerciseById(id);
            return ResponseEntity.ok(exercise);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserExercise> deleteUserExercise(@PathVariable String id) {
         try{
            userExerciseService.deleteExerciseById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
