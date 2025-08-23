package com.acu.kafka;

import com.acu.kafka.model.Message;
import com.acu.kafka.service.KafkaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@EmbeddedKafka(partitions = 1, topics = {"messages-topic"})
@ActiveProfiles("test")
class EndToEndTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        kafkaService.clearMessages();
        kafkaService.clearStatistics();
    }

    @Test
    void testCompleteMessageFlow() throws Exception {
        // 1. Send a message via REST API
        Message message = new Message("End-to-end test message", "E2ETest", Message.MessageType.INFO);

        mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Message sent successfully"));

        // 2. Simulate message consumption (which would normally happen via Kafka listener)
        kafkaService.consumeMessage(message);

        // 3. Verify message is stored in memory
        List<Message> storedMessages = kafkaService.getReceivedMessages();
        assertEquals(1, storedMessages.size());
        
        Message storedMessage = storedMessages.get(0);
        assertEquals("End-to-end test message", storedMessage.getContent());
        assertEquals("E2ETest", storedMessage.getSender());
        assertEquals(Message.MessageType.INFO, storedMessage.getType());

        // 4. Verify message can be retrieved via REST API
        mockMvc.perform(get("/api/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("End-to-end test message"));
    }

    @Test
    void testMessageFilteringEndToEnd() throws Exception {
        // 1. Create multiple messages with different senders and types
        Message message1 = new Message("Message from Alice", "Alice", Message.MessageType.INFO);
        Message message2 = new Message("Message from Bob", "Bob", Message.MessageType.WARNING);

        kafkaService.consumeMessage(message1);
        kafkaService.consumeMessage(message2);

        // 2. Test filtering by sender
        mockMvc.perform(get("/api/messages/sender/Alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sender").value("Alice"));

        // 3. Test filtering by type
        mockMvc.perform(get("/api/messages/type/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("INFO"));
    }

    @Test
    void testErrorHandling() throws Exception {
        // Test invalid message type
        mockMvc.perform(get("/api/messages/type/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testClearMessages() throws Exception {
        // 1. Add some messages
        Message message = new Message("Test message", "TestSender", Message.MessageType.INFO);
        kafkaService.consumeMessage(message);
        assertEquals(1, kafkaService.getReceivedMessages().size());

        // 2. Clear messages via REST API
        mockMvc.perform(delete("/api/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Messages cleared successfully"));

        // 3. Verify messages are cleared
        assertEquals(0, kafkaService.getReceivedMessages().size());
    }
}
