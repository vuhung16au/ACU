package com.acu.kafka.controller;

import com.acu.kafka.model.ScheduledTaskEntity;
import com.acu.kafka.service.SchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.context.annotation.Import;
import com.acu.kafka.config.TestConfig;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
class SchedulerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SchedulerService schedulerService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        // Setup default mock behavior
        doNothing().when(schedulerService).resetCounters();
    }

    @Test
    void testGetStatus_Success() throws Exception {
        // Given
        when(schedulerService.getTaskCount()).thenReturn(5);
        when(schedulerService.getEmailCount()).thenReturn(3);

        // When & Then
        mockMvc.perform(get("/api/scheduler/status"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.taskCount").value(5))
                .andExpect(jsonPath("$.emailCount").value(3))
                .andExpect(jsonPath("$.status").value("running"));
    }

    @Test
    void testTriggerTask_Success() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/scheduler/trigger"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task triggered successfully"));
    }

    @Test
    void testResetCounters_Success() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/scheduler/reset"))
                .andExpect(status().isOk())
                .andExpect(content().string("Counters reset successfully"));
    }

    @Test
    void testGetScheduledTasks_Success() throws Exception {
        // Given
        ScheduledTaskEntity task1 = new ScheduledTaskEntity();
        task1.setId(UUID.randomUUID());
        task1.setTaskName("testTask1");
        task1.setStatus(ScheduledTaskEntity.TaskStatus.COMPLETED);
        task1.setExecutionTime(LocalDateTime.now());
        task1.setDurationMs(100);

        ScheduledTaskEntity task2 = new ScheduledTaskEntity();
        task2.setId(UUID.randomUUID());
        task2.setTaskName("testTask2");
        task2.setStatus(ScheduledTaskEntity.TaskStatus.FAILED);
        task2.setExecutionTime(LocalDateTime.now());
        task2.setDurationMs(200);

        List<ScheduledTaskEntity> tasks = Arrays.asList(task1, task2);

        // Mock the service
        when(schedulerService.getScheduledTasks()).thenReturn(tasks);

        // When & Then
        mockMvc.perform(get("/api/scheduler/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].taskName").value("testTask1"))
                .andExpect(jsonPath("$[0].status").value("COMPLETED"))
                .andExpect(jsonPath("$[1].taskName").value("testTask2"))
                .andExpect(jsonPath("$[1].status").value("FAILED"));
    }

    @Test
    void testGetScheduledTasksByStatus_Success() throws Exception {
        // Given
        ScheduledTaskEntity task = new ScheduledTaskEntity();
        task.setId(UUID.randomUUID());
        task.setTaskName("completedTask");
        task.setStatus(ScheduledTaskEntity.TaskStatus.COMPLETED);
        task.setExecutionTime(LocalDateTime.now());
        task.setDurationMs(150);

        List<ScheduledTaskEntity> tasks = Arrays.asList(task);

        // Mock the service
        when(schedulerService.getScheduledTasksByStatus(ScheduledTaskEntity.TaskStatus.COMPLETED)).thenReturn(tasks);

        // When & Then
        mockMvc.perform(get("/api/scheduler/tasks/status/completed"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].taskName").value("completedTask"))
                .andExpect(jsonPath("$[0].status").value("COMPLETED"));
    }

    @Test
    void testGetScheduledTasksByStatus_InvalidStatus() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/scheduler/tasks/status/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetScheduledTasksByName_Success() throws Exception {
        // Given
        ScheduledTaskEntity task1 = new ScheduledTaskEntity();
        task1.setId(UUID.randomUUID());
        task1.setTaskName("specificTask");
        task1.setStatus(ScheduledTaskEntity.TaskStatus.COMPLETED);
        task1.setExecutionTime(LocalDateTime.now());
        task1.setDurationMs(100);

        ScheduledTaskEntity task2 = new ScheduledTaskEntity();
        task2.setId(UUID.randomUUID());
        task2.setTaskName("specificTask");
        task2.setStatus(ScheduledTaskEntity.TaskStatus.FAILED);
        task2.setExecutionTime(LocalDateTime.now());
        task2.setDurationMs(200);

        List<ScheduledTaskEntity> tasks = Arrays.asList(task1, task2);

        // Mock the service
        when(schedulerService.getScheduledTasksByName("specificTask")).thenReturn(tasks);

        // When & Then
        mockMvc.perform(get("/api/scheduler/tasks/name/specificTask"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].taskName").value("specificTask"))
                .andExpect(jsonPath("$[1].taskName").value("specificTask"));
    }
}
