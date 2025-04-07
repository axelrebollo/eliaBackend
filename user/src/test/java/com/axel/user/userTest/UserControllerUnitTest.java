package com.axel.user.userTest;

import com.axel.user.API.controllers.UserController;
import com.axel.user.application.DTOs.UserRequest;
import com.axel.user.application.DTOs.UserResponse;
import com.axel.user.application.DTOs.UserResponseToken;
import com.axel.user.application.services.ILoginUserCase;
import com.axel.user.application.services.IRegisterUserCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRegisterUserCase registerUserCase;

    @MockBean
    private ILoginUserCase loginUserCase;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequest sampleRequest;

    @BeforeEach
    void setUp() {
        sampleRequest = new UserRequest("test@example.com", "password123", "TEACHER");
    }

    @Test
    void testRegisterUser() throws Exception {
        UserResponse fakeResponse = new UserResponse("test@example.com", "TEACHER");

        when(registerUserCase.registerUser(any(), any(), any())).thenReturn(fakeResponse);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.role").value("TEACHER"));
    }

    @Test
    void testLoginUser() throws Exception {
        UserResponseToken fakeTokenResponse = new UserResponseToken("token123");

        when(loginUserCase.loginUser(any(), any())).thenReturn(fakeTokenResponse);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.token").value("token123"));
    }
}
