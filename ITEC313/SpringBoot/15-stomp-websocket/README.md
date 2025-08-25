# Spring Boot STOMP WebSocket Demo

This project demonstrates how to create a "Hello, world" application that sends messages back and forth between a browser and a server using Spring Boot and STOMP (Simple Text Oriented Messaging Protocol) over WebSocket.

## Technologies Used

- **Spring Boot 3.2.0**: Main framework for building the application
- **Spring WebSocket**: Provides WebSocket support
- **STOMP**: Simple Text Oriented Messaging Protocol for WebSocket
- **SockJS**: JavaScript library for WebSocket fallback support
- **Maven**: Build tool and dependency management
- **Java 17**: Programming language

## Project Structure

```
src/
├── main/
│   ├── java/com/acu/stomp/
│   │   ├── MessagingStompWebsocketApplication.java  # Main Spring Boot application
│   │   ├── WebSocketConfig.java                     # WebSocket configuration
│   │   ├── GreetingController.java                  # STOMP message handler
│   │   ├── HelloMessage.java                        # Incoming message model
│   │   └── Greeting.java                            # Outgoing message model
│   └── resources/static/
│       ├── index.html                               # Web client interface
│       ├── app.js                                   # JavaScript for WebSocket client
│       └── main.css                                 # Styling for the web interface
└── test/java/com/acu/stomp/
    └── GreetingIntegrationTests.java                # Integration tests
```

## How It Works

1. **WebSocket Connection**: The browser connects to the server using SockJS over WebSocket
2. **STOMP Protocol**: Messages are exchanged using the STOMP protocol
3. **Message Flow**:
   - Client sends a message to `/app/hello` with a name
   - Server processes the message and responds with a greeting
   - Response is sent to `/topic/greetings` for all subscribed clients
   - All connected clients receive the greeting message

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Building the Project

To build the project, run:

```bash
mvn clean compile
```

## Running the Application

To start the application, run:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Testing the Application

### Manual Testing

1. Open your web browser and navigate to `http://localhost:8080`
2. Click the "Connect" button to establish a WebSocket connection
3. Enter your name in the text field
4. Click "Send" to send a message
5. You should see a greeting message appear in the table
6. Click "Disconnect" to close the connection

### Automated Testing

To run the integration tests:

```bash
mvn test
```

The tests verify that:
- The WebSocket connection can be established
- Messages can be sent and received
- The greeting response is correct

## API Endpoints

- **WebSocket Endpoint**: `/gs-guide-websocket` (SockJS fallback enabled)
- **Message Destination**: `/app/hello` (for sending messages)
- **Topic**: `/topic/greetings` (for receiving messages)

## Key Features

- **Real-time Communication**: Messages are sent and received in real-time
- **Fallback Support**: SockJS provides fallback options for browsers that don't support WebSocket
- **Simple Interface**: Clean web interface for testing the functionality
- **Integration Tests**: Automated tests to verify the WebSocket functionality

## Learning Objectives

This project demonstrates:

1. How to set up Spring Boot with WebSocket support
2. How to configure STOMP over WebSocket
3. How to create message handlers in Spring
4. How to build a simple real-time messaging application
5. How to test WebSocket functionality

## Troubleshooting

- **Connection Issues**: Make sure the application is running on port 8080
- **Browser Compatibility**: Ensure your browser supports WebSocket or SockJS
- **Build Issues**: Make sure you have Java 17+ and Maven installed

## References

- [Spring WebSocket Documentation](https://docs.spring.io/spring-framework/reference/web/websocket.html)
- [STOMP Protocol](https://stomp.github.io/)
- [Spring Boot WebSocket Guide](https://spring.io/guides/gs/messaging-stomp-websocket/)
