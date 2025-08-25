# Spring Boot STOMP WebSocket Implementation

## Overview
Spring Boot provides comprehensive support for WebSocket and STOMP through the `spring-boot-starter-websocket` dependency. This guide covers the complete implementation of a STOMP WebSocket application.

## Project Setup

### Maven Dependencies
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-websocket</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```


## Configuration

### Basic WebSocket Configuration
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable simple broker for topics and queues
        config.enableSimpleBroker("/topic", "/queue");
        
        // Set application destination prefix
        config.setApplicationDestinationPrefixes("/app");
        
        // Set user destination prefix for user-specific messages
        config.setUserDestinationPrefix("/user");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register STOMP endpoints
        registry.addEndpoint("/ws")
               .setAllowedOrigins("*")
               .withSockJS();
        
        // Alternative endpoint without SockJS
        registry.addEndpoint("/ws-native")
               .setAllowedOrigins("*");
    }
}
```


## Message Models

### Basic Message Model
```java
public class ChatMessage {
    private String content;
    private String sender;
    private String timestamp;
    private MessageType type;
    
    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
    
    // Constructors, getters, setters
    public ChatMessage() {}
    
    public ChatMessage(String content, String sender, MessageType type) {
        this.content = content;
        this.sender = sender;
        this.type = type;
        this.timestamp = LocalDateTime.now().toString();
    }
    
    // Getters and setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    
    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }
}
```

### Notification Model
```java
public class Notification {
    private String id;
    private String title;
    private String message;
    private NotificationType type;
    private String userId;
    private LocalDateTime createdAt;
    
    public enum NotificationType {
        INFO, WARNING, ERROR, SUCCESS
    }
    
    // Constructors, getters, setters
}
```

## Controllers

### Basic Message Controller
```java
@Controller
public class ChatController {
    
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
    
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, 
                              SimpMessageHeaderAccessor headerAccessor) {
        // Add username to web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
```


### Notification Controller
```java
@Controller
public class NotificationController {
    
    private final SimpMessagingTemplate messagingTemplate;
    
    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    
    @MessageMapping("/notifications.send")
    public void sendNotification(@Payload Notification notification) {
        // Send to specific user
        messagingTemplate.convertAndSendToUser(
            notification.getUserId(),
            "/queue/notifications",
            notification
        );
    }
    
    @MessageMapping("/notifications.broadcast")
    @SendTo("/topic/notifications")
    public Notification broadcastNotification(@Payload Notification notification) {
        return notification;
    }
}
```

## Interceptors

### Authentication Interceptor
```java
@Component
public class WebSocketAuthenticationInterceptor implements ChannelInterceptor {
    
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                String jwtToken = token.substring(7);
                // Validate JWT token
                if (validateToken(jwtToken)) {
                    // Set user information in session
                    accessor.setUser(new Principal() {
                        @Override
                        public String getName() {
                            return extractUsername(jwtToken);
                        }
                    });
                }
            }
        }
        
        return message;
    }
    
    private boolean validateToken(String token) {
        // Implement JWT validation logic
        return true;
    }
    
    private String extractUsername(String token) {
        // Extract username from JWT token
        return "user";
    }
}
```

### Logging Interceptor
```java
@Component
public class WebSocketLoggingInterceptor implements ChannelInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(WebSocketLoggingInterceptor.class);
    
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
        if (accessor != null) {
            logger.info("WebSocket {} command from session {}", 
                       accessor.getCommand(), accessor.getSessionId());
        }
        
        return message;
    }
    
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, 
                                   boolean sent, Exception ex) {
        if (ex != null) {
            logger.error("Error sending WebSocket message", ex);
        }
    }
}
```

## Security Configuration

### WebSocket Security
```java
@Configuration
@EnableWebSocketSecurity
public class WebSocketSecurityConfig {
    
    @Bean
    public SecurityWebSocketMessageBrokerConfigurer webSocketSecurity() {
        return new SecurityWebSocketMessageBrokerConfigurer() {
            @Override
            protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
                messages
                    .simpDestMatchers("/app/chat.private").authenticated()
                    .simpDestMatchers("/topic/private.*").authenticated()
                    .simpDestMatchers("/user/**").authenticated()
                    .anyMessage().permitAll();
            }
        };
    }
}
```

### CORS Configuration
```java
@Configuration
public class CorsConfig {
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://trusted-domain.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```

## Client-Side Implementation

### JavaScript Client
```javascript
class WebSocketClient {
    constructor() {
        this.stompClient = null;
        this.connected = false;
    }
    
    connect() {
        const socket = new SockJS('/ws');
        this.stompClient = Stomp.over(socket);
        
        this.stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            this.connected = true;
            this.subscribeToTopics();
        }, (error) => {
            console.error('Connection error:', error);
            this.connected = false;
            setTimeout(() => this.connect(), 5000);
        });
    }
    
    subscribeToTopics() {
        // Subscribe to public chat
        this.stompClient.subscribe('/topic/public', (message) => {
            const chatMessage = JSON.parse(message.body);
            this.displayMessage(chatMessage);
        });
        
        // Subscribe to private messages
        this.stompClient.subscribe('/user/queue/private', (message) => {
            const chatMessage = JSON.parse(message.body);
            this.displayPrivateMessage(chatMessage);
        });
        
        // Subscribe to notifications
        this.stompClient.subscribe('/user/queue/notifications', (message) => {
            const notification = JSON.parse(message.body);
            this.displayNotification(notification);
        });
    }
    
    sendMessage(content, sender) {
        if (this.connected) {
            const chatMessage = {
                content: content,
                sender: sender,
                type: 'CHAT',
                timestamp: new Date().toISOString()
            };
            
            this.stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        }
    }
    
    sendPrivateMessage(content, sender, recipient) {
        if (this.connected) {
            const chatMessage = {
                content: content,
                sender: sender,
                recipient: recipient,
                type: 'CHAT',
                timestamp: new Date().toISOString()
            };
            
            this.stompClient.send("/app/chat.private", {}, JSON.stringify(chatMessage));
        }
    }
    
    joinChat(username) {
        if (this.connected) {
            const chatMessage = {
                sender: username,
                type: 'JOIN',
                timestamp: new Date().toISOString()
            };
            
            this.stompClient.send("/app/chat.addUser", {}, JSON.stringify(chatMessage));
        }
    }
    
    disconnect() {
        if (this.stompClient) {
            this.stompClient.disconnect();
        }
    }
    
    displayMessage(message) {
        // Implementation for displaying messages
        console.log('Message:', message);
    }
    
    displayPrivateMessage(message) {
        // Implementation for displaying private messages
        console.log('Private message:', message);
    }
    
    displayNotification(notification) {
        // Implementation for displaying notifications
        console.log('Notification:', notification);
    }
}

// Usage
const client = new WebSocketClient();
client.connect();

// Send a message
client.sendMessage('Hello World!', 'John');

// Join chat
client.joinChat('John');
```

### React Client
```jsx
import React, { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

const ChatComponent = () => {
    const [stompClient, setStompClient] = useState(null);
    const [messages, setMessages] = useState([]);
    const [inputMessage, setInputMessage] = useState('');
    const [username, setUsername] = useState('');
    
    useEffect(() => {
        const socket = new SockJS('/ws');
        const client = Stomp.over(socket);
        
        client.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            setStompClient(client);
            
            // Subscribe to public chat
            client.subscribe('/topic/public', (message) => {
                const chatMessage = JSON.parse(message.body);
                setMessages(prev => [...prev, chatMessage]);
            });
        });
        
        return () => {
            if (client) {
                client.disconnect();
            }
        };
    }, []);
    
    const sendMessage = () => {
        if (stompClient && inputMessage.trim()) {
            const chatMessage = {
                content: inputMessage,
                sender: username,
                type: 'CHAT',
                timestamp: new Date().toISOString()
            };
            
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
            setInputMessage('');
        }
    };
    
    const joinChat = () => {
        if (stompClient && username.trim()) {
            const chatMessage = {
                sender: username,
                type: 'JOIN',
                timestamp: new Date().toISOString()
            };
            
            stompClient.send("/app/chat.addUser", {}, JSON.stringify(chatMessage));
        }
    };
    
    return (
        <div>
            <div>
                <input
                    type="text"
                    placeholder="Enter username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <button onClick={joinChat}>Join Chat</button>
            </div>
            
            <div>
                {messages.map((message, index) => (
                    <div key={index}>
                        <strong>{message.sender}:</strong> {message.content}
                    </div>
                ))}
            </div>
            
            <div>
                <input
                    type="text"
                    placeholder="Type a message"
                    value={inputMessage}
                    onChange={(e) => setInputMessage(e.target.value)}
                    onKeyPress={(e) => e.key === 'Enter' && sendMessage()}
                />
                <button onClick={sendMessage}>Send</button>
            </div>
        </div>
    );
};

export default ChatComponent;
```


### 3. Connection Management
```java
@Component
public class WebSocketEventListener {
    
    private final SimpMessagingTemplate messagingTemplate;
    
    public WebSocketEventListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        // Handle connection events
    }
    
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        // Handle disconnection events
    }
}
```


## Resources

- [Spring WebSocket Documentation](https://docs.spring.io/spring-framework/reference/web/websocket.html)
- [Spring Boot WebSocket Guide](https://spring.io/guides/gs/messaging-stomp-websocket/)
