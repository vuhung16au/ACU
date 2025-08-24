package com.acu.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldLoginWithValidCredentials() throws Exception {
        // Create test user
        User user = new User();
        user.setUsername("test@acu.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole("ADMIN");
        userRepository.save(user);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        AuthController.LoginRequest loginRequest = new AuthController.LoginRequest();
        loginRequest.setUsername("test@acu.com");
        loginRequest.setPassword("123456");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").value("test@acu.com"))
                .andExpect(jsonPath("$.authorities").exists());

        // Clean up
        userRepository.delete(user);
    }

    @Test
    void shouldFailLoginWithInvalidCredentials() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        AuthController.LoginRequest loginRequest = new AuthController.LoginRequest();
        loginRequest.setUsername("invalid@acu.com");
        loginRequest.setPassword("wrongpassword");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid username or password"));
    }
}
