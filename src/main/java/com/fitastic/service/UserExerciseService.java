package com.fitastic.service;

import com.fitastic.entity.DefaultExercise;
import com.fitastic.entity.UserExercise;
import com.fitastic.repository.UserExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

// Service class for handling UserExercise-related business logic
@Service
@AllArgsConstructor
@Validated
public class UserExerciseService {

    // Repository for UserExercise database operations
    private UserExerciseRepository userExerciseRepository;

    // Retrieve all UserExercises from the database
    public List<UserExercise> getAll(){
        return userExerciseRepository.findAll();
    }

    // Create a new UserExercise in the database
    public UserExercise createUserExercise(UserExercise userExercise) {
        return userExerciseRepository.save(userExercise);
    }

    // Retrieve a specific UserExercise by its ID
    // Returns null if the exercise is not found
    public UserExercise getUserExerciseById(String id) {
        Optional<UserExercise> exercise = userExerciseRepository.findById(id);
        return exercise.orElse(null);
    }

    // Update an existing UserExercise
    public UserExercise updateUserExercise(String id, UserExercise exerciseUpdate) {
        UserExercise exercise = this.getUserExerciseById(id);

        // Update all fields of the exercise
        exercise.setName(exerciseUpdate.getName());
        exercise.setDescription(exerciseUpdate.getDescription());
        exercise.setInstructions(exerciseUpdate.getInstructions());
        exercise.setTarget(exerciseUpdate.getTarget());
        exercise.setAdvices(exerciseUpdate.getAdvices());
        exercise.setImage(exerciseUpdate.getImage());
        exercise.setVideo(exerciseUpdate.getVideo());
        exercise.setUserId(exerciseUpdate.getUserId());
        exercise.setSessionId(exerciseUpdate.getSessionId());

        // Save and return the updated exercise
        return userExerciseRepository.save(exercise);
    }

    // Delete a UserExercise by its ID
    // Throws a RuntimeException if the exercise is not found
    public void deleteUserExerciseById(String id) {
        if (userExerciseRepository.existsById(id)){
            userExerciseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Exercise not found with id: " + id);
        }
    }
}