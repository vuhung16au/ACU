# STOMP Protocol Deep Dive

## Overview
STOMP (Streaming Text Oriented Messaging Protocol) is a simple, text-based protocol that provides an interoperable wire format for message-oriented middleware. It's designed to be easy to implement and debug.

## Protocol Fundamentals

### Frame Structure
Every STOMP message is a frame with the following structure:
```
COMMAND
header1:value1
header2:value2
...

Body content here
^@
```

The `^@` represents a null byte (0x00) that terminates the frame.

### Frame Types

#### 1. CONNECT Frame
```
CONNECT
accept-version:1.2
host:localhost
login:user
passcode:password

^@
```

#### 2. SEND Frame
```
SEND
destination:/topic/chat
content-type:application/json
content-length:25

{"message": "Hello World"}
^@
```

#### 3. SUBSCRIBE Frame
```
SUBSCRIBE
id:sub-0
destination:/topic/chat
ack:auto

^@
```

#### 4. MESSAGE Frame
```
MESSAGE
subscription:sub-0
message-id:msg-1
destination:/topic/chat
content-type:application/json
content-length:25

{"message": "Hello World"}
^@
```

## STOMP Commands

### Client Commands

#### CONNECT
- **Purpose**: Establish connection to STOMP broker
- **Required Headers**: `host`
- **Optional Headers**: `login`, `passcode`, `accept-version`

#### SEND
- **Purpose**: Send a message to a destination
- **Required Headers**: `destination`
- **Optional Headers**: `content-type`, `content-length`, `transaction`

#### SUBSCRIBE
- **Purpose**: Subscribe to a destination
- **Required Headers**: `destination`, `id`
- **Optional Headers**: `ack`, `selector`

#### UNSUBSCRIBE
- **Purpose**: Unsubscribe from a destination
- **Required Headers**: `id`

#### ACK
- **Purpose**: Acknowledge message receipt
- **Required Headers**: `id` or `message-id`
- **Optional Headers**: `transaction`

#### NACK
- **Purpose**: Negative acknowledgment
- **Required Headers**: `id` or `message-id`
- **Optional Headers**: `transaction`

#### DISCONNECT
- **Purpose**: Close connection
- **Optional Headers**: `receipt`

### Server Commands

#### CONNECTED
- **Purpose**: Confirm successful connection
- **Headers**: `version`, `server`, `session`, `heart-beat`

#### MESSAGE
- **Purpose**: Deliver message to subscriber
- **Required Headers**: `destination`, `message-id`, `subscription`
- **Optional Headers**: `ack`, `content-type`, `content-length`

#### RECEIPT
- **Purpose**: Confirm receipt of client command
- **Required Headers**: `receipt-id`

#### ERROR
- **Purpose**: Report error to client
- **Required Headers**: `message`
- **Optional Headers**: `content-type`, `content-length`

## STOMP Versions

### Version 1.0
- Basic protocol features
- Limited header support

### Version 1.1
- Added heart-beating
- Improved error handling
- Better header support

### Version 1.2 (Current)
- Full heart-beating support
- Enhanced error handling
- Improved security features
- Better transaction support

## Heart-beating

STOMP 1.1+ supports heart-beating to detect connection failures:

```
CONNECT
accept-version:1.2
host:localhost
heart-beat:10000,10000

^@
```

- First number: Outgoing heart-beat interval (ms)
- Second number: Incoming heart-beat interval (ms)
- `0,0` means no heart-beating

## Transactions

STOMP supports transactions for message grouping:

```
BEGIN
transaction:tx-1

^@

SEND
destination:/topic/chat
transaction:tx-1
content-type:text/plain

Message 1
^@

SEND
destination:/topic/chat
transaction:tx-1
content-type:text/plain

Message 2
^@

COMMIT
transaction:tx-1

^@
```

## Error Handling

### Error Frame Example
```
ERROR
receipt:receipt-1
message:Invalid destination format
content-type:text/plain
content-length:25

Invalid destination format
^@
```

### Common Error Scenarios
1. **Invalid destination**: Malformed destination format
2. **Authentication failed**: Invalid login credentials
3. **Subscription not found**: ACK/NACK for unknown subscription
4. **Transaction not found**: COMMIT/ABORT for unknown transaction

## STOMP vs Other Protocols

| Feature | STOMP | AMQP | MQTT |
|---------|-------|------|------|
| Complexity | Simple | Complex | Simple |
| Text-based | Yes | No | No |
| Header support | Yes | Yes | Limited |
| Transactions | Yes | Yes | No |
| Heart-beating | Yes | Yes | Yes |
| QoS levels | No | Yes | Yes |

## Security Considerations

### Authentication
- Basic authentication via `login` and `passcode` headers
- Can be extended with custom headers
- Should use TLS/SSL for secure transmission

### Authorization
- Destination-based access control
- User role-based permissions
- Message filtering capabilities

## Best Practices

### 1. Connection Management
- Implement proper connection lifecycle
- Handle reconnection scenarios
- Monitor connection health

### 2. Message Handling
- Validate message content
- Implement proper error handling
- Use appropriate content types

### 3. Performance
- Use heart-beating for connection monitoring
- Implement message batching when possible
- Monitor message throughput

### 4. Security
- Always use TLS in production
- Implement proper authentication
- Validate all inputs

## Example Implementations

### JavaScript Client
```javascript
const stompClient = new StompJs.Client({
    webSocketFactory: () => new WebSocket('ws://localhost:8080/ws'),
    connectHeaders: {
        login: 'user',
        passcode: 'password'
    },
    debug: function (str) {
        console.log(str);
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/chat', (message) => {
        console.log('Received: ' + message.body);
    });
};

stompClient.activate();
```

### Java Server
```java
@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
               .setAllowedOrigins("*")
               .withSockJS();
    }
    
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(64 * 1024)     // 64KB
                   .setSendBufferSizeLimit(512 * 1024)  // 512KB
                   .setSendTimeLimit(20000);            // 20 seconds
    }
}
```
