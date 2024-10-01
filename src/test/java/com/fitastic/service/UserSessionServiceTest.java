package com.fitastic.service;

import com.fitastic.entity.UserExercise;
import com.fitastic.entity.UserSession;
import com.fitastic.repository.UserSessionRepository;
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
import static org.mockito.Mockito.times;

class UserSessionServiceTest {

    @Mock
    private UserSessionRepository userSessionRepository;

    @InjectMocks
    private UserSessionService userSessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllUserSessions() {
        List <UserSession> userSessions = new ArrayList<>();

        UserSession userSession1 = new UserSession();
        userSession1.setUserId("1");
        userSession1.setUserSessionDateId("1");
        userSession1.setName("Session1");

        UserSession userSession2 = new UserSession();
        userSession2.setUserId("2");
        userSession2.setUserSessionDateId("2");
        userSession2.setName("Session2");

        userSessions.add(userSession1);
        userSessions.add(userSession2);

        when(userSessionRepository.findAll()).thenReturn(userSessions);

        List<UserSession> result =  userSessionService.getAllUserSessions();

        assertEquals(2, result.size());
        assertEquals("Session1", result.get(0).getName());
        assertEquals("Session2", result.get(1).getName());
    }

    @Test
    void shouldCreateUserSession() {
        UserSession userSession = new UserSession();
        userSession.setName("Session");
        userSession.setUserId("1");
        userSession.setUserSessionDateId("1");

        when(userSessionRepository.save(any(UserSession.class))).thenReturn(userSession);

        UserSession result = userSessionService.createUserSession(userSession);

        assertEquals(userSession, result);
    }

    @Test
    void ShouldGetUserSessionById() {

        UserSession userSession = new UserSession();
        userSession.setUserId("1");
        userSession.setUserSessionDateId("1");
        userSession.setName("Session");

        when(userSessionRepository.findById("1")).thenReturn(Optional.of(userSession));

        UserSession result = userSessionService.getUserSessionById("1");

        assertEquals("Session", result.getName());
    }

    @Test
    void ShouldUpdateUserSession() {

        UserSession userSession = new UserSession();
        userSession.setUserId("1");
        userSession.setUserSessionDateId("1");
        userSession.setName("Session");

        UserSession updatedUserSession = new UserSession();
        updatedUserSession.setUserId("2");
        updatedUserSession.setUserSessionDateId("2");
        updatedUserSession.setName("UpdatedSession");

        when(userSessionRepository.findById("1")).thenReturn(Optional.of(userSession));
        when(userSessionRepository.save(any(UserSession.class))).thenReturn(updatedUserSession);

        UserSession result = userSessionService.updateUserSession("1", updatedUserSession);

        assertEquals("UpdatedSession", result.getName());
    }

    @Test
    void ShouldDeleteUserSessionById() {
        UserSession userSession = new UserSession();
        userSession.setId("1");
        userSession.setName("Session");
        userSession.setUserId("1");
        userSession.setUserSessionDateId("1");

        when(userSessionRepository.existsById("1")).thenReturn(true);

        userSessionService.deleteUserSessionById("1");

        verify(userSessionRepository, times(1)).existsById("1");
        verify(userSessionRepository, times(1)).deleteById("1");
    }

}