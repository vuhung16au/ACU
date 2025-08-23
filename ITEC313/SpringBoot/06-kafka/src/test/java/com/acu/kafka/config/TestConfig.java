package com.acu.kafka.config;

import com.acu.kafka.service.SchedulerService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public JavaMailSender mockJavaMailSender() {
        JavaMailSender mockMailSender = mock(JavaMailSender.class);
        
        // Mock MimeMessage creation
        MimeMessage mockMimeMessage = mock(MimeMessage.class);
        when(mockMailSender.createMimeMessage()).thenReturn(mockMimeMessage);
        when(mockMailSender.createMimeMessage(any())).thenReturn(mockMimeMessage);
        
        // Mock send methods to do nothing
        doNothing().when(mockMailSender).send(any(MimeMessage.class));
        
        return mockMailSender;
    }

    @Bean
    @Primary
    public SchedulerService mockSchedulerService() {
        SchedulerService mockService = mock(SchedulerService.class);
        
        // Mock all methods to do nothing
        doNothing().when(mockService).resetCounters();
        doNothing().when(mockService).clearAllTasks();
        when(mockService.getAllTasks()).thenReturn(List.of());
        when(mockService.getScheduledTasks()).thenReturn(List.of());
        when(mockService.getScheduledTasksByStatus(any())).thenReturn(List.of());
        when(mockService.getScheduledTasksByName(any())).thenReturn(List.of());
        
        return mockService;
    }
}
