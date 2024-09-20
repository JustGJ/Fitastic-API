package com.fitastic.service;

import com.fitastic.entity.DefaultExercise;
import com.fitastic.repository.DefaultExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class DefaultExerciseService {

    private DefaultExerciseRepository defaultExerciseRepository;

    public List<DefaultExercise> getAll(){
        return defaultExerciseRepository.findAll();
    }

    public DefaultExercise getExerciseById(String id) {
        List<DefaultExercise> exercises = defaultExerciseRepository.findAll();
        return exercises.stream()
                .filter(exercise -> exercise.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Exercise not found with id: " + id));
    }
}
