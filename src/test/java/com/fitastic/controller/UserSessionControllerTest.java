package com.fitastic.controller;

import com.fitastic.entity.UserSession;
import com.fitastic.service.UserSessionService;
import org.junit.jupiter.api.BeforeEach;
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

/**
 * Test class for UserSessionController.
 * Contains unit tests for the UserSessionController methods.
 */
@SpringBootTest
@AutoConfigureMockMvc
class UserSessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserSessionService userSessionService;


    /**
     * Test case: Verify that GET request to /api/userSessions returns all user sessions.
     *
     * @throws Exception if an error occurs during the mock request
     */
    @Test
    void shouldReturnAllUserSessions() throws Exception {
        UserSession session1 = new UserSession();
        session1.setId("1");
        session1.setName("Session1");
        session1.setUserId("1");

        UserSession session2 = new UserSession();
        session2.setId("2");
        session2.setName("Session2");
        session2.setUserId("2");

        when(userSessionService.getAllUserSessions()).thenReturn(Arrays.asList(session1, session2));

        mockMvc.perform(get("/api/userSessions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Session1"))
                .andExpect(jsonPath("$[0].userId").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Session2"))
                .andExpect(jsonPath("$[1].userId").value("2"));
    }

    /**
     * Test case: Verify that POST request to /api/userSessions creates a new user session.
     *
     * @throws Exception if an error occurs during the mock request
     */
    @Test
    void shouldCreateUserSession() throws Exception {
        UserSession session = new UserSession();
        session.setId("1");
        session.setName("Session");
        session.setUserId("1");

        when(userSessionService.createUserSession(any(UserSession.class))).thenReturn(session);

        String sessionJson = "{\"id\":\"1\",\"name\":\"Session\",\"userId\" : \"1\"}";

        mockMvc.perform(post("/api/userSessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sessionJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/userSessions/1"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Session"))
                .andExpect(jsonPath("$.userId").value("1"));
    }

    /**
     * Test case: Verify that GET request to /api/userSessions/{id} returns a specific user session.
     *
     * @throws Exception if an error occurs during the mock request
     */
    @Test
    void shouldReturnUserSession() throws Exception {
        UserSession session = new UserSession();
        session.setId("1");
        session.setName("Session");
        session.setUserId("1");

        when(userSessionService.getUserSessionById("1")).thenReturn(session);

        mockMvc.perform(get("/api/userSessions/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Session"))
                .andExpect(jsonPath("$.userId").value("1"));
    }

    /**
     * Test case: Verify that PATCH request to /api/userSessions/{id} updates an existing user session.
     *
     * @throws Exception if an error occurs during the mock request
     */
    @Test
    void shouldUpdateUserSession() throws Exception {
        UserSession session = new UserSession();
        session.setId("1");
        session.setName("Session");
        session.setUserId("1");

        UserSession updatedSession = new UserSession();
        updatedSession.setId("1");
        updatedSession.setName("UpdatedSession");
        updatedSession.setUserId("1");

        when(userSessionService.updateUserSession("1", updatedSession)).thenReturn(updatedSession);

        String updatedSessionJson = "{\"id\":\"1\",\"name\":\"UpdatedSession\",\"userId\" : \"1\"}";

        mockMvc.perform(patch("/api/userSessions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSessionJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("UpdatedSession"))
                .andExpect(jsonPath("$.userId").value("1"));
    }

    /**
     * Test case: Verify that DELETE request to /api/userSessions/{id} deletes a specific user session.
     *
     * @throws Exception if an error occurs during the mock request
     */
    @Test
    void shouldDeleteUserSession() throws Exception {
        doNothing().when(userSessionService).deleteUserSessionById("1");

        mockMvc.perform(delete("/api/userSessions/1"))
                .andExpect(status().isNoContent());

        verify(userSessionService, times(1)).deleteUserSessionById("1");
    }
}