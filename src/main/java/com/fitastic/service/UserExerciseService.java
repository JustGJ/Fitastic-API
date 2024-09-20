package com.fitastic.service;

import com.fitastic.entity.UserExercise;
import com.fitastic.repository.UserExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@AllArgsConstructor
@Validated
public class UserExerciseService {

    private UserExerciseRepository userExerciseRepository;

    public List<UserExercise> getAll(){
        return userExerciseRepository.findAll();
    }

    public UserExercise getExerciseById(String id) {
        List<UserExercise> exercises = userExerciseRepository.findAll();
        return exercises.stream()
                .filter(exercise -> exercise.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Exercise not found with id: " + id));
    }

    public UserExercise updateExercise(String id, UserExercise exerciseUpdate) {
        UserExercise exercise = this.getExerciseById(id);

        exercise.setName(exerciseUpdate.getName());
        exercise.setDescription(exerciseUpdate.getDescription());
        exercise.setInstructions(exerciseUpdate.getInstructions());
        exercise.setTarget(exerciseUpdate.getTarget());
        exercise.setAdvices(exerciseUpdate.getAdvices());
        exercise.setImage(exerciseUpdate.getImage());
        exercise.setVideo(exerciseUpdate.getVideo());
        exercise.setUserId(exerciseUpdate.getUserId());
        exercise.setSessionId(exerciseUpdate.getSessionId());
        return userExerciseRepository.save(exercise);
    }

    public void deleteExerciseById(String id) {
        if (userExerciseRepository.existsById(id)){
            userExerciseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Exercise not found with id: " + id);
        }
    }
}
