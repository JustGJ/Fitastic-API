package com.fitastic.service;

import com.fitastic.entity.DefaultExercise;
import com.fitastic.entity.UserExercise;
import com.fitastic.repository.UserExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling UserExercise-related business logic.
 * Manages CRUD operations for user exercises, including retrieval, creation, update, and deletion.
 */
@Service
@AllArgsConstructor
@Validated
public class UserExerciseService {

    private final UserExerciseRepository userExerciseRepository;

    /**
     * Retrieves all UserExercises from the database.
     *
     * @return List of all UserExercise objects.
     */
    public List<UserExercise> getAll(){
        return userExerciseRepository.findAll();
    }

    /**
     * Creates a new UserExercise in the database.
     *
     * @param userExercise The UserExercise object to be created.
     * @return The created UserExercise object.
     */
    public UserExercise createUserExercise(UserExercise userExercise) {
        return userExerciseRepository.save(userExercise);
    }

    /**
     * Retrieves a specific UserExercise by its ID.
     *
     * @param id The ID of the UserExercise to retrieve.
     * @return The requested UserExercise object, or null if not found.
     */
    public UserExercise getUserExerciseById(String id) {
        Optional<UserExercise> exercise = userExerciseRepository.findById(id);
        return exercise.orElse(null);
    }

    /**
     * Updates an existing UserExercise.
     *
     * @param id The ID of the UserExercise to update.
     * @param exerciseUpdate The UserExercise object containing updated information.
     * @return The updated UserExercise object.
     */
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

    /**
     * Deletes a UserExercise by its ID.
     *
     * @param id The ID of the UserExercise to delete.
     * @throws RuntimeException if the exercise is not found.
     */
    public void deleteUserExerciseById(String id) {
        if (userExerciseRepository.existsById(id)){
            userExerciseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Exercise not found with id: " + id);
        }
    }
}