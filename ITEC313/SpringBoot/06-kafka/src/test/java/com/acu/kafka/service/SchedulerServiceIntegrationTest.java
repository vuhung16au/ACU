package com.acu.kafka.service;

import com.acu.kafka.model.ScheduledTaskEntity;
import com.acu.kafka.repository.ScheduledTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class SchedulerServiceIntegrationTest {

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private ScheduledTaskRepository scheduledTaskRepository;

    @BeforeEach
    void setUp() {
        scheduledTaskRepository.deleteAll();
    }

    @Test
    void testGetTaskCount() {
        // Given
        int initialCount = schedulerService.getTaskCount();

        // When
        int count = schedulerService.getTaskCount();

        // Then
        assertTrue(count >= 0);
        assertEquals(initialCount, count);
    }

    @Test
    void testGetEmailCount() {
        // Given
        int initialCount = schedulerService.getEmailCount();

        // When
        int count = schedulerService.getEmailCount();

        // Then
        assertTrue(count >= 0);
        assertEquals(initialCount, count);
    }

    @Test
    void testGetScheduledTasks() {
        // Given
        ScheduledTaskEntity task1 = new ScheduledTaskEntity();
        task1.setTaskName("testTask1");
        task1.setStatus(ScheduledTaskEntity.TaskStatus.COMPLETED);
        task1.setExecutionTime(LocalDateTime.now());
        task1.setDurationMs(100);
        scheduledTaskRepository.save(task1);

        ScheduledTaskEntity task2 = new ScheduledTaskEntity();
        task2.setTaskName("testTask2");
        task2.setStatus(ScheduledTaskEntity.TaskStatus.FAILED);
        task2.setExecutionTime(LocalDateTime.now());
        task2.setDurationMs(200);
        scheduledTaskRepository.save(task2);

        // When
        List<ScheduledTaskEntity> tasks = schedulerService.getScheduledTasks();

        // Then
        assertEquals(2, tasks.size());
        assertTrue(tasks.stream().anyMatch(t -> t.getTaskName().equals("testTask1")));
        assertTrue(tasks.stream().anyMatch(t -> t.getTaskName().equals("testTask2")));
    }

    @Test
    void testGetScheduledTasksByStatus() {
        // Given
        ScheduledTaskEntity completedTask = new ScheduledTaskEntity();
        completedTask.setTaskName("completedTask");
        completedTask.setStatus(ScheduledTaskEntity.TaskStatus.COMPLETED);
        completedTask.setExecutionTime(LocalDateTime.now());
        completedTask.setDurationMs(150);
        scheduledTaskRepository.save(completedTask);

        ScheduledTaskEntity failedTask = new ScheduledTaskEntity();
        failedTask.setTaskName("failedTask");
        failedTask.setStatus(ScheduledTaskEntity.TaskStatus.FAILED);
        failedTask.setExecutionTime(LocalDateTime.now());
        failedTask.setDurationMs(300);
        scheduledTaskRepository.save(failedTask);

        // When
        List<ScheduledTaskEntity> completedTasks = schedulerService.getScheduledTasksByStatus(ScheduledTaskEntity.TaskStatus.COMPLETED);

        // Then
        assertEquals(1, completedTasks.size());
        assertEquals("completedTask", completedTasks.get(0).getTaskName());
        assertEquals(ScheduledTaskEntity.TaskStatus.COMPLETED, completedTasks.get(0).getStatus());
    }

    @Test
    void testGetScheduledTasksByName() {
        // Given
        ScheduledTaskEntity task1 = new ScheduledTaskEntity();
        task1.setTaskName("specificTask");
        task1.setStatus(ScheduledTaskEntity.TaskStatus.COMPLETED);
        task1.setExecutionTime(LocalDateTime.now());
        task1.setDurationMs(100);
        scheduledTaskRepository.save(task1);

        ScheduledTaskEntity task2 = new ScheduledTaskEntity();
        task2.setTaskName("specificTask");
        task2.setStatus(ScheduledTaskEntity.TaskStatus.FAILED);
        task2.setExecutionTime(LocalDateTime.now());
        task2.setDurationMs(200);
        scheduledTaskRepository.save(task2);

        ScheduledTaskEntity task3 = new ScheduledTaskEntity();
        task3.setTaskName("otherTask");
        task3.setStatus(ScheduledTaskEntity.TaskStatus.COMPLETED);
        task3.setExecutionTime(LocalDateTime.now());
        task3.setDurationMs(300);
        scheduledTaskRepository.save(task3);

        // When
        List<ScheduledTaskEntity> specificTasks = schedulerService.getScheduledTasksByName("specificTask");

        // Then
        assertEquals(2, specificTasks.size());
        assertTrue(specificTasks.stream().allMatch(t -> t.getTaskName().equals("specificTask")));
    }

    @Test
    void testResetCounters() {
        // Given
        int initialTaskCount = schedulerService.getTaskCount();
        int initialEmailCount = schedulerService.getEmailCount();

        // When
        schedulerService.resetCounters();

        // Then
        assertEquals(0, schedulerService.getTaskCount());
        assertEquals(0, schedulerService.getEmailCount());
    }
}
