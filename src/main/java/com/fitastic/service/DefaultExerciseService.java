package com.fitastic.service;

import com.fitastic.entity.DefaultExercise;
import com.fitastic.repository.DefaultExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service class for handling DefaultExercise-related business logic
@Service
@AllArgsConstructor
public class DefaultExerciseService {

    // Repository for DefaultExercise database operations
    private DefaultExerciseRepository defaultExerciseRepository;

    // Retrieve all DefaultExercises from the database
    public List<DefaultExercise> getAll() {
        return defaultExerciseRepository.findAll();
    }

    // Retrieve a specific DefaultExercise by its ID
    // Returns null if the exercise is not found
    public DefaultExercise getDefaultExerciseById(String id) {
        Optional<DefaultExercise> exercise = defaultExerciseRepository.findById(id);
        return exercise.orElse(null);
    }
}