package com.acu.quickstart.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for HelloController
 */
@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloShouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from Spring Boot!"));
    }

    @Test
    void helloWithNameShouldReturnCustomizedMessage() throws Exception {
        mockMvc.perform(get("/hello/name").param("name", "Spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Spring!"))
                .andExpect(jsonPath("$.application").value("01-quick-start"));
    }

    @Test
    void helloWithNameShouldUseDefaultWhenNoNameProvided() throws Exception {
        mockMvc.perform(get("/hello/name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello World!"))
                .andExpect(jsonPath("$.application").value("01-quick-start"));
    }

    @Test
    void healthShouldReturnUpStatus() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.application").value("01-quick-start"));
    }
}
