# STOMP WebSocket Documentation

## Table of Contents
1. [STOMP Protocol](#stomp-protocol)
2. [WebSocket](#websocket)
3. [Spring Boot Implementation](#spring-boot-implementation)
4. [Key Concepts](#key-concepts)
5. [Use Cases](#use-cases)

---

## STOMP Protocol

### What is STOMP?
**STOMP** (Streaming Text Oriented Messaging Protocol) is a simple text-based protocol designed to work with message-oriented middleware. It provides an interoperable wire format that allows STOMP clients to communicate with any STOMP message broker.

### Key Characteristics:
- **Text-based**: Uses simple text commands and headers
- **Frame-based**: Messages are organized in frames with headers and body
- **Protocol-agnostic**: Can run over any reliable transport protocol
- **Simple**: Easy to implement and debug

### STOMP Frame Structure:
```
COMMAND
header1:value1
header2:value2

Body content here
^@
```

### Common STOMP Commands:
- `CONNECT` - Establish connection to broker
- `SEND` - Send a message to a destination
- `SUBSCRIBE` - Subscribe to a destination
- `UNSUBSCRIBE` - Unsubscribe from a destination
- `ACK` - Acknowledge message receipt
- `NACK` - Negative acknowledgment
- `DISCONNECT` - Close connection

---

## WebSocket

### What is WebSocket?
**WebSocket** is a computer communications protocol that provides full-duplex communication channels over a single TCP connection. It enables real-time, bidirectional communication between web browsers and servers.

### Key Features:
- **Full-duplex**: Both client and server can send messages simultaneously
- **Persistent connection**: Connection stays open until explicitly closed
- **Low latency**: Minimal overhead for real-time communication
- **Standard protocol**: RFC 6455 standard

### WebSocket Handshake:
1. Client sends HTTP upgrade request
2. Server responds with upgrade confirmation
3. Connection switches from HTTP to WebSocket protocol
4. Bidirectional communication begins

### WebSocket vs HTTP:
| Feature | HTTP | WebSocket |
|---------|------|-----------|
| Connection | Request-response | Persistent |
| Direction | Unidirectional | Bidirectional |
| Overhead | High (headers per request) | Low (minimal headers) |
| Real-time | No | Yes |

---

## Spring Boot Implementation

### Spring WebSocket Support
Spring Boot provides comprehensive support for WebSocket and STOMP through the `spring-boot-starter-websocket` dependency.

### Key Components:

#### 1. WebSocket Configuration
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
               .setAllowedOrigins("*")
               .withSockJS();
    }
}
```

#### 2. Message Controllers
```java
@Controller
public class MessageController {
    
    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public Message handleMessage(Message message) {
        return message;
    }
    
    @SendToUser("/queue/notifications")
    public Notification sendNotification(Notification notification) {
        return notification;
    }
}
```

#### 3. Client-Side Connection
```javascript
// Connect to WebSocket
const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    
    // Subscribe to topic
    stompClient.subscribe('/topic/messages', function (message) {
        console.log('Received: ' + message.body);
    });
    
    // Send message
    stompClient.send("/app/send", {}, JSON.stringify({
        content: "Hello World!"
    }));
});
```

### Spring Boot Dependencies:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```

---

## Key Concepts

### 1. Message Broker
- **Simple Broker**: In-memory message broker for development
- **External Broker**: RabbitMQ, ActiveMQ for production
- **Destinations**: Topics (broadcast) and Queues (point-to-point)

### 2. Destination Types
- **Topics** (`/topic/*`): Broadcast messages to all subscribers
- **Queues** (`/queue/*`): Point-to-point messages
- **User destinations** (`/user/*`): User-specific messages

### 3. Message Flow
1. Client connects via WebSocket
2. Client subscribes to destinations
3. Client sends messages to `/app/*` endpoints
4. Server processes messages via `@MessageMapping`
5. Server broadcasts responses to subscribed clients

### 4. Security Considerations
- **CORS**: Configure allowed origins
- **Authentication**: Implement user authentication
- **Authorization**: Control access to destinations
- **Rate limiting**: Prevent abuse

---

## Use Cases

### 1. Real-time Chat Applications
- Instant messaging
- Group chat rooms
- Live customer support

### 2. Live Dashboards
- Real-time data visualization
- Stock tickers
- System monitoring

### 3. Collaborative Applications
- Real-time document editing
- Live collaboration tools
- Multiplayer games

### 4. Notifications
- Push notifications
- Live alerts
- Status updates

### 5. IoT Applications
- Device monitoring
- Sensor data streaming
- Remote control systems

---


## Resources

- [Spring WebSocket Documentation](https://docs.spring.io/spring-framework/reference/web/websocket.html)
- [Spring Boot WebSocket Guide](https://spring.io/guides/gs/messaging-stomp-websocket/)
