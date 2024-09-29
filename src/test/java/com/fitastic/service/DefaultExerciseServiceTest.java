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

/**
 * Test class for DefaultExerciseService.
 * Contains unit tests for the DefaultExerciseService methods.
 */
public class DefaultExerciseServiceTest {

    @Mock
    private DefaultExerciseRepository defaultExerciseRepository;

    @InjectMocks
    private DefaultExerciseService defaultExerciseService;

    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case: Verify that getAll() method returns all default exercises.
     */
    @Test
    void shouldReturnAllDefaultExercises(){
        DefaultExercise exercise1 = new DefaultExercise();
        exercise1.setId("1");
        exercise1.setName("Exercise1");
        exercise1.setInstructions(new String[]{"Upper body"});

        DefaultExercise exercise2 = new DefaultExercise();
        exercise2.setId("2");
        exercise2.setName("Exercise2");
        exercise2.setInstructions(new String[]{"Lower body"});

        List<DefaultExercise> mockExercises = new ArrayList<>();
        mockExercises.add(exercise1);
        mockExercises.add(exercise2);

        when(defaultExerciseRepository.findAll()).thenReturn(mockExercises);

        List<DefaultExercise> result = defaultExerciseService.getAll();

        assertEquals(2, result.size());
        assertEquals("Exercise1", result.get(0).getName());
        assertEquals("Upper body", result.get(0).getInstructions()[0]);
        assertEquals("Exercise2", result.get(1).getName());
        assertEquals("Lower body", result.get(1).getInstructions()[0]);
    }

    /**
     * Test case: Verify that getDefaultExerciseById() method returns the correct default exercise.
     */
    @Test
    void shouldReturnDefaultExerciseById() {
        DefaultExercise exersise = new DefaultExercise();
        exersise.setId("1");
        exersise.setName("Exercise");
        exersise.setInstructions(new String[]{"Upper body"});

        when(defaultExerciseRepository.findById("1")).thenReturn(Optional.of(exersise));

        DefaultExercise result = defaultExerciseService.getDefaultExerciseById("1");

        assertNotNull(result);
        assertEquals("Exercise", result.getName());
        assertEquals("Upper body", result.getInstructions()[0]);
    }
}
