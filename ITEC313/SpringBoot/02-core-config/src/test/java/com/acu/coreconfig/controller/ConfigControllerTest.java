package com.acu.coreconfig.controller;

import com.acu.coreconfig.service.ConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for ConfigController demonstrating @WebMvcTest
 * 
 * This test shows how to:
 * - Use @WebMvcTest for testing web layer only
 * - Mock dependencies using @MockBean
 * - Test REST endpoints using MockMvc
 * - Verify JSON responses and status codes
 */
@WebMvcTest(ConfigController.class)
class ConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfigService configService;

    @Test
    void getConfig_ShouldReturnConfiguration() throws Exception {
        // Given
        Map<String, Object> mockConfig = Map.of(
            "name", "Test App",
            "environment", "test",
            "maxConnections", 10,
            "timestamp", "2024-01-01 12:00:00"
        );
        when(configService.getAppConfig()).thenReturn(mockConfig);

        // When & Then
        mockMvc.perform(get("/api/config"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Test App"))
                .andExpect(jsonPath("$.environment").value("test"))
                .andExpect(jsonPath("$.maxConnections").value(10));
    }

    @Test
    void getTimestamp_ShouldReturnTimestamps() throws Exception {
        // Given
        when(configService.getCurrentTimestamp()).thenReturn("2024-01-01 12:00:00");
        when(configService.getShortTimestamp()).thenReturn("01/01/2024 12:00");

        // When & Then
        mockMvc.perform(get("/api/config/timestamp"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.timestamp").value("2024-01-01 12:00:00"))
                .andExpect(jsonPath("$.shortTimestamp").value("01/01/2024 12:00"));
    }

    @Test
    void getTimestampWithFormat_ShouldReturnFormattedTimestamp() throws Exception {
        // Given
        when(configService.getCurrentTimestamp()).thenReturn("2024-01-01 12:00:00");
        when(configService.getShortTimestamp()).thenReturn("01/01/2024 12:00");

        // When & Then - Test default format
        mockMvc.perform(get("/api/config/timestamp/default"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.format").value("default"))
                .andExpect(jsonPath("$.timestamp").value("2024-01-01 12:00:00"));

        // When & Then - Test short format
        mockMvc.perform(get("/api/config/timestamp/short"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.format").value("short"))
                .andExpect(jsonPath("$.timestamp").value("01/01/2024 12:00"));
    }

    @Test
    void triggerLogging_ShouldReturnSuccessMessage() throws Exception {
        // Given
        when(configService.getCurrentTimestamp()).thenReturn("2024-01-01 12:00:00");

        // When & Then
        mockMvc.perform(post("/api/config/log")
                .param("level", "debug"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Configuration logged successfully"))
                .andExpect(jsonPath("$.level").value("debug"))
                .andExpect(jsonPath("$.timestamp").value("2024-01-01 12:00:00"));
    }

    @Test
    void health_ShouldReturnHealthStatus() throws Exception {
        // Given
        when(configService.getCurrentTimestamp()).thenReturn("2024-01-01 12:00:00");

        // When & Then
        mockMvc.perform(get("/api/config/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("Config Service"))
                .andExpect(jsonPath("$.timestamp").value("2024-01-01 12:00:00"));
    }
}
