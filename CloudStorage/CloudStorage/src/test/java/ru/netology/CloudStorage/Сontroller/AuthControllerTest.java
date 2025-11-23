package ru.netology.CloudStorage.Сontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.CloudStorage.Controller.AuthController;
import ru.netology.CloudStorage.Login.LoginRequest;
import ru.netology.CloudStorage.Login.LoginResponse;
import ru.netology.CloudStorage.Logout.LogoutRequest;
import ru.netology.CloudStorage.Service.AuthService.AuthService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.ServerResponse.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testLoginSuccess() throws Exception {
        String sessionId = "session123";

        when(authService.login(anyString(), anyString())).thenReturn(sessionId);

        LoginRequest request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("pass");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value(sessionId));
    }

    @Test
    public void testLoginUnauthorized() throws Exception {
        when(authService.login(anyString(), anyString())).thenThrow(new IllegalArgumentException());

        LoginRequest request = new LoginRequest();
        request.setUsername("wrong");
        request.setPassword("wrong");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("")); // тело null в ResponseEntity
    }

    @Test
    public void testLoginServerError() throws Exception {
        when(authService.login(anyString(), anyString())).thenThrow(new RuntimeException());

        LoginRequest request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("pass");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("")); // тело null
    }

    @Test
    public void testLogoutSuccess() throws Exception {
        LogoutRequest request = new LogoutRequest();
        request.setSessionId("session123");

        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogoutServerError() throws Exception {
        Mockito.doThrow(new RuntimeException()).when(authService).logout(anyString());

        LogoutRequest request = new LogoutRequest();
        request.setSessionId("session123");

        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}