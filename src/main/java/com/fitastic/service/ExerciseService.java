package com.fitastic.service;

import com.fitastic.entity.Exercise;
import com.fitastic.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExerciseService {

    private ExerciseRepository exerciseRepository;

    public List<Exercise> getAll(){
        return exerciseRepository.findAll();
    }

    public Exercise getExerciseByName(String name) {
        List<Exercise> exercises = exerciseRepository.findAll();

        return exercises.stream()
                .filter(exercise -> exercise.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Exercise not found with name: " + name));
    }

    public void deleteExerciseById(String id) {
        if (exerciseRepository.existsById(id)){
            exerciseRepository.deleteById(id);
        } else {
        throw new RuntimeException("Exercise not found with id: " + id);
        }
    }
}
