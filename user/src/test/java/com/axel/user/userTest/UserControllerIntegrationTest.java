package com.axel.user.userTest;

import com.axel.user.application.DTOs.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    @LocalServerPort
    private int port; //injection port from application properties

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebTestClient webTestClient;

    //create a new user into database using endpoint
    @Test
    void registerUser_createsNewUserInDatabase() throws Exception {
        UserRequest request = new UserRequest("integration_test@example.com", "testpass", "TEACHER");

        webTestClient.post()
                .uri("/users/register") //use a port and URL for WebTestClient
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(request))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo("integration_test@example.com")
                .jsonPath("$.role").isEqualTo("TEACHER");
    }
}

