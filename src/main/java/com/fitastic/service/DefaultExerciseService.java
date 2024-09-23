package com.fitastic.service;

import com.fitastic.entity.DefaultExercise;
import com.fitastic.repository.DefaultExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultExerciseService {

    private DefaultExerciseRepository defaultExerciseRepository;

    public List<DefaultExercise> getAll() {
        return defaultExerciseRepository.findAll();
    }

    public DefaultExercise getDefaultExerciseById(String id) {
        Optional<DefaultExercise> exercise = defaultExerciseRepository.findById(id);
        return exercise.orElse(null);
    }

}