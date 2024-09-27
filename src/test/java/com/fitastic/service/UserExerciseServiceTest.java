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

class UserExerciseServiceTest {

    // Mocking the UserExerciseRepository to simulate repository/database interactions
    @Mock
    private UserExerciseRepository userExerciseRepository;

    // Injecting the mocked repository into the UserExerciseService to test its methods
    @InjectMocks
    private UserExerciseService userExerciseService;

    // Setting up the test environment, initializing the mocks before each test case
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test case to verify that getAll() correctly retrieves all user exercises
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

        // Mocking the findAll() method of the repository to return the mock exercises
        when(userExerciseRepository.findAll()).thenReturn(mockExercises);

        // Calling the service method and verifying the result
        List<UserExercise> result = userExerciseService.getAll();

        // Asserting that the list contains the expected number of exercises and that their properties match
        assertEquals(2, result.size());
        assertEquals("Exercise1", result.get(0).getName());
        assertEquals("Upper body", result.get(0).getInstructions()[0]);
        assertEquals("Exercise2", result.get(1).getName());
        assertEquals("Lower body", result.get(1).getInstructions()[0]);

    }

    // Test case to verify that getUserExerciseById() retrieves the correct user exercise by its ID
    @Test
    void shouldReturnUserExerciseById() {
        // Creating a mock UserExercise object
        UserExercise userExercise = new UserExercise();
        userExercise.setUserId("1");
        userExercise.setName("Exercise");
        userExercise.setInstructions(new String[]{"Upper body"});

        // Mocking the repository's findById() method to return the mock UserExercise object
        when(userExerciseRepository.findById("1")).thenReturn(Optional.of(userExercise));

        // Calling the service method and verifying the result
        UserExercise result = userExerciseService.getUserExerciseById("1");

        // Asserting that the retrieved object is not null and its properties match the mock object
        assertNotNull(userExercise);
        assertEquals("Exercise", result.getName());
        assertEquals("Upper body", result.getInstructions()[0]);
    }

    // Test case to verify that updateUserExercise() correctly updates a user exercise
    @Test
    void shouldUpdateUserExercise() {
        // Creating a mock UserExercise object for the existing exercise
        UserExercise userExercise = new UserExercise();
        userExercise.setUserId("1");
        userExercise.setName("Exercise");
        userExercise.setInstructions(new String[]{"Upper body"});

        // Creating a mock UserExercise object for the updated exercise
        UserExercise updatedExercise = new UserExercise();
        updatedExercise.setUserId("2");
        updatedExercise.setName("ExerciseUpdated");
        updatedExercise.setInstructions(new String[]{"Lower body"});

        // Mocking the repository's findById() to return the existing exercise
        when(userExerciseRepository.findById("1")).thenReturn(Optional.of(userExercise));

        // Mocking the repository's save() to return the updated exercise
        when(userExerciseRepository.save(any(UserExercise.class))).thenReturn(updatedExercise);

        // Calling the service method to update the exercise and verifying the result
        UserExercise result = userExerciseService.updateUserExercise("1", updatedExercise);

        // Asserting that the returned object contains the updated values
        assertNotNull(userExercise);
        assertEquals("2", result.getUserId());
        assertEquals("ExerciseUpdated", result.getName());
        assertEquals("Lower body", result.getInstructions()[0]);
    }

    // Test case to verify that deleteUserExerciseById() deletes a user exercise by its ID
    @Test
    void shouldDeleteUserExerciseById() {
        // Creating a mock UserExercise object
        UserExercise userExercise = new UserExercise();
        userExercise.setUserId("1");
        userExercise.setName("Exercise");
        userExercise.setInstructions(new String[]{"Upper body"});

        // Mocking the repository's existsById() method to return true
        when(userExerciseRepository.existsById("1")).thenReturn(true);

        // Mocking the repository's deleteById() to do nothing (void method)
        doNothing().when(userExerciseRepository).deleteById("1");

        // Calling the service method to delete the exercise
        userExerciseService.deleteUserExerciseById("1");

        // Verifying that deleteById() and existsById() were called exactly once
        verify(userExerciseRepository, times(1)).deleteById("1");
        verify(userExerciseRepository, times(1)).existsById("1");
    }
}
