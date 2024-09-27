package com.fitastic.controller;

import com.fitastic.entity.DefaultExercise;
import com.fitastic.service.DefaultExerciseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Test class for DefaultExerciseController
@SpringBootTest
@AutoConfigureMockMvc
class DefaultExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DefaultExerciseService defaultExerciseService;

    // Test case: Verify that the GET request to /api/defaultExercises returns a list of default exercises
    @Test
    void shouldReturnDefaultExercises() throws Exception {
        // Create mock DefaultExercise objects
        DefaultExercise exercise1 = new DefaultExercise();
        exercise1.setId("1");
        exercise1.setName("Push-up");

        DefaultExercise exercise2 = new DefaultExercise();
        exercise2.setId("2");
        exercise2.setName("Squat");

        // Mock the service method to return the list of exercises
        when(defaultExerciseService.getAll()).thenReturn(Arrays.asList(exercise1, exercise2));

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/defaultExercises"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Push-up"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Squat"));
    }

    // Test case: Verify that the GET request to /api/defaultExercises/{id} returns a specific default exercise
    @Test
    void shouldReturnDefaultExercise() throws Exception {
        // Create a mock DefaultExercise object
        DefaultExercise exercise = new DefaultExercise();
        exercise.setId("1");
        exercise.setName("Push-up");

        // Mock the service method to return the specific exercise
        when(defaultExerciseService.getDefaultExerciseById("1")).thenReturn(exercise);

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/defaultExercises/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Push-up"));
    }
}