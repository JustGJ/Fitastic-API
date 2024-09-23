package com.fitastic.service;

import com.fitastic.entity.DefaultExercise;
import com.fitastic.entity.UserExercise;
import com.fitastic.repository.UserExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Validated
public class UserExerciseService {

    private UserExerciseRepository userExerciseRepository;

    public List<UserExercise> getAll(){
        return userExerciseRepository.findAll();
    }

    public UserExercise getUserExerciseById(String id) {
        Optional<UserExercise> exercise = userExerciseRepository.findById(id);
        return exercise.orElse(null);
    }

    public UserExercise updateUserExercise(String id, UserExercise exerciseUpdate) {
        UserExercise exercise = this.getUserExerciseById(id);

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

    public void deleteUserExerciseById(String id) {
        if (userExerciseRepository.existsById(id)){
            userExerciseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Exercise not found with id: " + id);
        }
    }
}
