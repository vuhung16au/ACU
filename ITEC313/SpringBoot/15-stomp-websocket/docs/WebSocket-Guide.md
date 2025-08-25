# WebSocket Protocol Guide

## Overview
WebSocket is a computer communications protocol that provides full-duplex communication channels over a single TCP connection. It enables real-time, bidirectional communication between web browsers and servers.

## Protocol Fundamentals

### WebSocket Handshake
The WebSocket connection begins with an HTTP handshake that upgrades the connection:

#### Client Request
```
GET /chat HTTP/1.1
Host: server.example.com
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
Sec-WebSocket-Protocol: chat, superchat
Sec-WebSocket-Version: 13
Origin: http://example.com
```

#### Server Response
```
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
Sec-WebSocket-Protocol: chat
```

### WebSocket Frame Format
```
 0                   1                   2                   3
 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
+-+-+-+-+-------+-+-------------+-------------------------------+
|F|R|R|R| opcode|M| Payload len |    Extended payload length   |
|I|S|S|S|  (4)  |A|     (7)     |             (16/64)          |
|N|V|V|V|       |S|             |   (if payload len==126/127)   |
| |1|2|3|       |K|             |                               |
+-+-+-+-+-------+-+-------------+ - - - - - - - - - - - - - - - +
|     Extended payload length continued, if payload len == 127  |
+ - - - - - - - - - - - - - - - +-------------------------------+
|                               |Masking-key, if MASK set to 1  |
+-------------------------------+-------------------------------+
| Masking-key (continued)       |          Payload Data         |
+-------------------------------- - - - - - - - - - - - - - - - +
:                     Payload Data continued ...                 :
+ - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - +
|                     Payload Data continued ...                 |
+---------------------------------------------------------------+
```

### Frame Fields

#### FIN (1 bit)
- `0`: More frames follow
- `1`: Final frame in message

#### RSV1, RSV2, RSV3 (1 bit each)
- Reserved for future use
- Must be `0` unless negotiated

#### Opcode (4 bits)
- `0x0`: Continuation frame
- `0x1`: Text frame
- `0x2`: Binary frame
- `0x8`: Connection close
- `0x9`: Ping
- `0xA`: Pong
- `0x3-0x7`, `0xB-0xF`: Reserved

#### MASK (1 bit)
- `0`: No masking
- `1`: Payload is masked (client to server)

#### Payload Length (7 bits)
- `0-125`: Actual payload length
- `126`: 16-bit extended payload length follows
- `127`: 64-bit extended payload length follows

## WebSocket vs HTTP

### HTTP Limitations
- **Request-Response**: Client must initiate communication
- **Stateless**: No persistent connection
- **Overhead**: Headers sent with every request
- **Latency**: Connection establishment for each request

### WebSocket Advantages
- **Full-duplex**: Bidirectional communication
- **Persistent**: Single connection for entire session
- **Low overhead**: Minimal headers after handshake
- **Real-time**: Immediate message delivery

### Comparison Table
| Aspect | HTTP | WebSocket |
|--------|------|-----------|
| Connection Model | Request-Response | Persistent |
| Communication | Unidirectional | Bidirectional |
| Overhead | High (headers per request) | Low (minimal headers) |
| Real-time | No | Yes |
| State | Stateless | Stateful |
| Use Cases | REST APIs, web pages | Chat, gaming, live data |

## WebSocket Lifecycle

### 1. Connection Establishment
```
Client                    Server
   |                        |
   | HTTP GET (Upgrade)     |
   |----------------------->|
   |                        |
   | 101 Switching Protocols|
   |<-----------------------|
   |                        |
   | WebSocket Connection   |
   |<---------------------->|
```

### 2. Data Exchange
```
Client                    Server
   |                        |
   | WebSocket Frame        |
   |----------------------->|
   |                        |
   | WebSocket Frame        |
   |<-----------------------|
   |                        |
```

### 3. Connection Closure
```
Client                    Server
   |                        |
   | Close Frame            |
   |----------------------->|
   |                        |
   | Close Frame            |
   |<-----------------------|
   |                        |
   | TCP Connection Close   |
   |<---------------------->|
```

## WebSocket Control Frames

### Close Frame
```
Opcode: 0x8
Payload: 2-byte status code + optional reason
```

Common close codes:
- `1000`: Normal closure
- `1001`: Going away
- `1002`: Protocol error
- `1003`: Unsupported data
- `1006`: Abnormal closure
- `1007`: Invalid frame payload data
- `1008`: Policy violation
- `1009`: Message too big
- `1010`: Client termination
- `1011`: Server error

### Ping/Pong Frames
```
Ping:  Opcode 0x9, optional application data
Pong:  Opcode 0xA, echo of ping data
```

Used for:
- Connection health monitoring
- Keep-alive mechanism
- Network latency measurement

## WebSocket Implementation

### JavaScript Client
```javascript
// Basic WebSocket connection
const socket = new WebSocket('ws://localhost:8080/chat');

// Connection events
socket.onopen = function(event) {
    console.log('Connected to WebSocket server');
    socket.send('Hello Server!');
};

socket.onmessage = function(event) {
    console.log('Received:', event.data);
};

socket.onclose = function(event) {
    console.log('Connection closed:', event.code, event.reason);
};

socket.onerror = function(error) {
    console.error('WebSocket error:', error);
};

// Send message
socket.send(JSON.stringify({
    type: 'message',
    content: 'Hello World!'
}));

// Close connection
socket.close(1000, 'Normal closure');
```

### Java Server (Spring Boot)
```java
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(), "/chat")
               .setAllowedOrigins("*");
    }
}

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    
    private final Set<WebSocketSession> sessions = new ConcurrentHashMap.newKeySet();
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("Client connected: " + session.getId());
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        System.out.println("Received: " + payload);
        
        // Broadcast to all connected clients
        for (WebSocketSession clientSession : sessions) {
            try {
                clientSession.sendMessage(new TextMessage(payload));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("Client disconnected: " + session.getId());
    }
}
```

### Python Server
```python
import asyncio
import websockets

async def chat_handler(websocket, path):
    try:
        async for message in websocket:
            print(f"Received: {message}")
            # Echo the message back
            await websocket.send(f"Server received: {message}")
    except websockets.exceptions.ConnectionClosed:
        print("Client disconnected")

async def main():
    async with websockets.serve(chat_handler, "localhost", 8080):
        await asyncio.Future()  # run forever

if __name__ == "__main__":
    asyncio.run(main())
```

## Security Considerations

### 1. Origin Validation
```java
@Override
public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(chatHandler, "/chat")
           .setAllowedOrigins("https://trusted-domain.com");
}
```

### 2. Authentication
```java
@Component
public class AuthenticatedWebSocketHandler extends TextWebSocketHandler {
    
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, 
                                  ServerHttpResponse response,
                                  WebSocketHandler wsHandler,
                                  Map<String, Object> attributes) {
        // Validate authentication token
        String token = request.getHeaders().getFirst("Authorization");
        return validateToken(token);
    }
}
```

### 3. Rate Limiting
```java
@Component
public class RateLimitedWebSocketHandler extends TextWebSocketHandler {
    
    private final RateLimiter rateLimiter = RateLimiter.create(100.0); // 100 requests per second
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        if (rateLimiter.tryAcquire()) {
            // Process message
        } else {
            // Reject message
        }
    }
}
```

## Performance Optimization

### 1. Connection Pooling
```java
@Configuration
public class WebSocketConfig {
    
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new ThreadPoolTaskExecutor() {{
            setCorePoolSize(10);
            setMaxPoolSize(50);
            setQueueCapacity(100);
        }};
    }
}
```

### 2. Message Compression
```java
@Override
public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
    registration.setMessageSizeLimit(64 * 1024)     // 64KB
               .setSendBufferSizeLimit(512 * 1024)  // 512KB
               .setSendTimeLimit(20000);            // 20 seconds
}
```

### 3. Load Balancing
```java
@Configuration
public class WebSocketConfig {
    
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new LoadBalancedWebSocketHandler();
    }
}
```

## Testing WebSocket

### JavaScript Test Client
```javascript
// Test WebSocket connection
function testWebSocket() {
    const socket = new WebSocket('ws://localhost:8080/chat');
    
    socket.onopen = () => {
        console.log('Test: Connection established');
        socket.send('Test message');
    };
    
    socket.onmessage = (event) => {
        console.log('Test: Received:', event.data);
        socket.close();
    };
    
    socket.onerror = (error) => {
        console.error('Test: Error:', error);
    };
}

testWebSocket();
```

### Java Test
```java
@SpringBootTest
@AutoConfigureTestDatabase
class WebSocketTest {
    
    @Test
    void testWebSocketConnection() throws Exception {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(
            List.of(new WebSocketTransport(new StandardWebSocketClient()))));
        
        StompSession session = stompClient.connect(
            "ws://localhost:8080/ws", 
            new StompSessionHandlerAdapter() {}
        ).get(1, TimeUnit.SECONDS);
        
        assertThat(session).isNotNull();
        assertThat(session.isConnected()).isTrue();
    }
}
```

## Common Issues and Solutions

### 1. Connection Refused
- **Cause**: Server not running or wrong port
- **Solution**: Check server status and port configuration

### 2. Handshake Failed
- **Cause**: Invalid upgrade request or CORS issues
- **Solution**: Verify headers and CORS configuration

### 3. Connection Timeout
- **Cause**: Network issues or server overload
- **Solution**: Implement reconnection logic and health checks

### 4. Message Loss
- **Cause**: Connection drops or buffer overflow
- **Solution**: Implement message acknowledgment and retry logic

## Best Practices

### 1. Error Handling
```javascript
const socket = new WebSocket('ws://localhost:8080/chat');

socket.onerror = function(error) {
    console.error('WebSocket error:', error);
    // Implement reconnection logic
    setTimeout(() => {
        // Attempt reconnection
    }, 5000);
};
```

### 2. Connection Management
```javascript
class WebSocketManager {
    constructor(url) {
        this.url = url;
        this.reconnectAttempts = 0;
        this.maxReconnectAttempts = 5;
    }
    
    connect() {
        this.socket = new WebSocket(this.url);
        this.socket.onclose = () => this.handleReconnect();
    }
    
    handleReconnect() {
        if (this.reconnectAttempts < this.maxReconnectAttempts) {
            this.reconnectAttempts++;
            setTimeout(() => this.connect(), 5000);
        }
    }
}
```

### 3. Message Validation
```java
@Override
protected void handleTextMessage(WebSocketSession session, TextMessage message) {
    try {
        // Validate message format
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());
        
        // Process valid message
        processMessage(jsonNode);
        
    } catch (JsonProcessingException e) {
        // Send error response
        session.sendMessage(new TextMessage("Invalid message format"));
    }
}
```

## Resources

- [MDN WebSocket Documentation](https://developer.mozilla.org/en-US/docs/Web/API/WebSocket)
- [Spring WebSocket Documentation](https://docs.spring.io/spring-framework/reference/web/websocket.html)
