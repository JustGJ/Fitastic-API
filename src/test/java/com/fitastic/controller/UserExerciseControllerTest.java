package com.fitastic.controller;

import com.fitastic.entity.UserExercise;
import com.fitastic.service.UserExerciseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest // Specifies that the test should run in a Spring Boot context.
@AutoConfigureMockMvc // Enables MockMvc for testing HTTP endpoints without starting a full web server.
class UserExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc; // Used to perform HTTP requests in a test environment.

    @MockBean
    private UserExerciseService userExerciseService; // Mocked service layer that the controller interacts with.

    @Test
    void shouldReturnAllUserExercises() throws Exception {
        // Test for retrieving all user exercises.
        UserExercise exercise1 = new UserExercise(); // Create a mock exercise.
        exercise1.setId("1");
        exercise1.setName("Push-up");
        exercise1.setUserId("1");

        UserExercise exercise2 = new UserExercise(); // Create another mock exercise.
        exercise2.setId("2");
        exercise2.setName("Squat");
        exercise2.setUserId("1");

        // Mock the service call to return a list of exercises.
        when(userExerciseService.getAll()).thenReturn(Arrays.asList(exercise1, exercise2));

        // Simulate a GET request and check that the response is OK and JSON formatted.
        mockMvc.perform(get("/api/userExercises"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate JSON content of the first exercise.
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Push-up"))
                .andExpect(jsonPath("$[0].userId").value("1"))
                // Validate JSON content of the second exercise.
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Squat"))
                .andExpect(jsonPath("$[1].userId").value("1"));
    }

    @Test
    void shouldCreateUserExercise() throws Exception {
        // Test for creating a new user exercise.
        UserExercise exercise = new UserExercise();
        exercise.setId("1");
        exercise.setName("Push-up");
        exercise.setUserId("1");

        // Mock the service call to return the created exercise.
        when(userExerciseService.createUserExercise(any(UserExercise.class))).thenReturn(exercise);

        // JSON representation of the exercise to be sent in the POST request.
        String exerciseJson = "{\"id\":\"1\",\"name\":\"Push-up\",\"userId\" : \"1\"}";

        // Simulate a POST request to create the exercise and validate the response.
        mockMvc.perform(post("/api/userExercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(exerciseJson))
                .andExpect(status().isCreated()) // Expect HTTP 201 Created status.
                .andExpect(header().string("Location", "/exercise/1")) // Check the location header.
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Push-up"))
                .andExpect(jsonPath("$.userId").value("1"));
    }

    @Test
    void shouldUpdateUserExercise() throws Exception {
        // Test for updating an existing exercise.
        UserExercise exercise = new UserExercise();
        exercise.setId("1");
        exercise.setName("Push-up");
        exercise.setUserId("1");

        // Updated exercise data.
        UserExercise exerciseUpdated = new UserExercise();
        exerciseUpdated.setId("1");
        exerciseUpdated.setName("Squat");
        exerciseUpdated.setUserId("1");

        // Mock the service call to return the updated exercise.
        when(userExerciseService.updateUserExercise("1", exerciseUpdated)).thenReturn(exerciseUpdated);

        // Simulate a PATCH request to update the exercise.
        mockMvc.perform(patch("/api/userExercises/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"name\": \"Squat\", \"userId\" : \"1\"}"))
                .andExpect(status().isOk()) // Expect HTTP 200 OK status.
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Squat"))
                .andExpect(jsonPath("$.userId").value("1"));
    }

    @Test
    void shouldReturnUserExercise() throws Exception {
        // Test for retrieving a single exercise by ID.
        UserExercise exercise = new UserExercise();
        exercise.setId("1");
        exercise.setName("Push-up");
        exercise.setUserId("1");

        // Mock the service call to return the specific exercise.
        when(userExerciseService.getUserExerciseById("1")).thenReturn(exercise);

        // Simulate a GET request and validate the returned JSON data.
        mockMvc.perform(get("/api/userExercises/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Push-up"))
                .andExpect(jsonPath("$.userId").value("1"));
    }

    @Test
    void shouldDeleteUserExercise() throws Exception {
        // Test for deleting a user exercise by ID.
        UserExercise exercise = new UserExercise();
        exercise.setId("1");
        exercise.setName("Push-up");
        exercise.setUserId("1");

        // Mock the service call to do nothing when deleting.
        doNothing().when(userExerciseService).deleteUserExerciseById("1");

        // Simulate a DELETE request and check for the No Content status (HTTP 204).
        mockMvc.perform(delete("/api/userExercises/1"))
                .andExpect(status().isNoContent());

        // Verify that the service delete method was called once.
        verify(userExerciseService, times(1)).deleteUserExerciseById("1");
    }
}
