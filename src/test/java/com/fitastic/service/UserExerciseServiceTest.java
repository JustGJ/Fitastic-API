package com.fitastic.service;

import com.fitastic.entity.UserExercise;
import com.fitastic.repository.UserExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for UserExerciseService.
 * Contains unit tests for the UserExerciseService methods.
 */
class UserExerciseServiceTest {

    @Mock
    private UserExerciseRepository userExerciseRepository;

    @InjectMocks
    private UserExerciseService userExerciseService;

    /**
     * Initializes mocks before each test case.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case: Verify that getAll() method returns all user exercises.
     */
    @Test
    void shouldReturnAllUserExercises() {
        // Creating mock UserExercise objects
        UserExercise userExercise1 = new UserExercise();
        userExercise1.setUserId("1");
        userExercise1.setName("Exercise1");
        userExercise1.setInstructions(new String[]{"Upper body"});

        UserExercise userExercise2 = new UserExercise();
        userExercise2.setUserId("2");
        userExercise2.setName("Exercise2");
        userExercise2.setInstructions(new String[]{"Lower body"});

        // Creating a list of mock exercises
        List<UserExercise> mockExercises = new ArrayList<>();
        mockExercises.add(userExercise1);
        mockExercises.add(userExercise2);

        // Mocking the repository's findAll() method
        when(userExerciseRepository.findAll()).thenReturn(mockExercises);

        // Calling the service method and verifying the result
        List<UserExercise> result = userExerciseService.getAll();

        // Verifying the number of exercises and their properties
        assertEquals(2, result.size());
        assertEquals("Exercise1", result.get(0).getName());
        assertEquals("Upper body", result.get(0).getInstructions()[0]);
        assertEquals("Exercise2", result.get(1).getName());
        assertEquals("Lower body", result.get(1).getInstructions()[0]);
    }

    /**
     * Test case: Verify that getUserExerciseById() method returns the correct user exercise by ID.
     */
    @Test
    void shouldReturnUserExerciseById() {
        // Creating a mock UserExercise object
        UserExercise userExercise = new UserExercise();
        userExercise.setUserId("1");
        userExercise.setName("Exercise");
        userExercise.setInstructions(new String[]{"Upper body"});

        // Mocking the repository's findById() method
        when(userExerciseRepository.findById("1")).thenReturn(Optional.of(userExercise));

        // Calling the service method and verifying the result
        UserExercise result = userExerciseService.getUserExerciseById("1");

        // Asserting that the retrieved object matches the mock
        assertNotNull(result);
        assertEquals("Exercise", result.getName());
        assertEquals("Upper body", result.getInstructions()[0]);
    }

    /**
     * Test case: Verify that updateUserExercise() correctly updates a user exercise.
     */
    @Test
    void shouldUpdateUserExercise() {
        // Mock existing UserExercise
        UserExercise userExercise = new UserExercise();
        userExercise.setUserId("1");
        userExercise.setName("Exercise");
        userExercise.setInstructions(new String[]{"Upper body"});

        // Mock updated UserExercise
        UserExercise updatedExercise = new UserExercise();
        updatedExercise.setUserId("2");
        updatedExercise.setName("ExerciseUpdated");
        updatedExercise.setInstructions(new String[]{"Lower body"});

        // Mock repository methods for findById() and save()
        when(userExerciseRepository.findById("1")).thenReturn(Optional.of(userExercise));
        when(userExerciseRepository.save(any(UserExercise.class))).thenReturn(updatedExercise);

        // Calling the service method and verifying the result
        UserExercise result = userExerciseService.updateUserExercise("1", updatedExercise);

        // Asserting that the object contains the updated values
        assertNotNull(result);
        assertEquals("2", result.getUserId());
        assertEquals("ExerciseUpdated", result.getName());
        assertEquals("Lower body", result.getInstructions()[0]);
    }

    /**
     * Test case: Verify that deleteUserExerciseById() deletes a user exercise by ID.
     */
    @Test
    void shouldDeleteUserExerciseById() {
        // Creating a mock UserExercise object
        UserExercise userExercise = new UserExercise();
        userExercise.setUserId("1");
        userExercise.setName("Exercise");
        userExercise.setInstructions(new String[]{"Upper body"});

        // Mocking repository's existsById() method
        when(userExerciseRepository.existsById("1")).thenReturn(true);

        // Mocking deleteById() to do nothing (void method)
        doNothing().when(userExerciseRepository).deleteById("1");

        // Calling the service method to delete the exercise
        userExerciseService.deleteUserExerciseById("1");

        // Verifying that repository methods were called once
        verify(userExerciseRepository, times(1)).deleteById("1");
        verify(userExerciseRepository, times(1)).existsById("1");
    }
}
