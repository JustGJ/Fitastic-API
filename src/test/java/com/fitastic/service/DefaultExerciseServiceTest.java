package com.fitastic.service;

import com.fitastic.entity.DefaultExercise;
import com.fitastic.repository.DefaultExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class DefaultExerciseServiceTest {

    // Mocks the DefaultExerciseRepository to simulate database operations
    @Mock
    private DefaultExerciseRepository defaultExerciseRepository;

    // Injects the mock repository into the DefaultExerciseService instance to be tested
    @InjectMocks
    private DefaultExerciseService defaultExerciseService;

    // Initializes the mocks before each test case
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Opens the mocks for this test class
    }

    // Tests the getAll() method of DefaultExerciseService
    @Test
    void shouldReturnAllDefaultExercises(){

        // Creates mock data for DefaultExercise entities
        DefaultExercise exercise1 = new DefaultExercise();
        exercise1.setId("1");
        exercise1.setName("Exercise1");
        exercise1.setInstructions(new String[]{"Upper body"});

        DefaultExercise exercise2 = new DefaultExercise();
        exercise2.setId("2");
        exercise2.setName("Exercise2");
        exercise2.setInstructions(new String[]{"Lower body"});

        // Prepares a list of mock exercises
        List<DefaultExercise> mockExercises = new ArrayList<>();
        mockExercises.add(exercise1);
        mockExercises.add(exercise2);

        // Mocks the behavior of defaultExerciseRepository's findAll method to return mock data
        when(defaultExerciseRepository.findAll()).thenReturn(mockExercises);

        // Calls the service method and stores the result
        List<DefaultExercise> result = defaultExerciseService.getAll();

        // Asserts that the returned list contains two elements and verifies their values
        assertEquals(2, result.size());
        assertEquals("Exercise1", result.get(0).getName());
        assertEquals("Upper body", result.get(0).getInstructions()[0]);
        assertEquals("Exercise2", result.get(1).getName());
        assertEquals("Lower body", result.get(1).getInstructions()[0]);
    }

    // Tests the getDefaultExerciseById() method of DefaultExerciseService
    @Test
    void shouldReturnDefaultExerciseById() {
        // Creates a mock DefaultExercise entity
        DefaultExercise exersise = new DefaultExercise();
        exersise.setId("1");
        exersise.setName("Exercise");
        exersise.setInstructions(new String[]{"Upper body"});

        // Mocks the behavior of defaultExerciseRepository's findById method to return a mock entity
        when(defaultExerciseRepository.findById("1")).thenReturn(Optional.of(exersise));

        // Calls the service method and stores the result
        DefaultExercise result = defaultExerciseService.getDefaultExerciseById("1");

        // Verifies that the result is not null and checks the values of the returned entity
        assertNotNull(result);
        assertEquals("Exercise", result.getName());
        assertEquals("Upper body", result.getInstructions()[0]);
    }

}
