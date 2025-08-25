# Spring Boot CORS Demo

This project demonstrates how to create a RESTful web service with Spring Boot that includes Cross-Origin Resource Sharing (CORS) headers in the response. The application allows only `http://localhost:9000` to send cross-origin requests.

## Technologies Used

- **Spring Boot 3.2.0** - Main framework for building the REST API
- **Spring Web** - For creating RESTful web services
- **Spring Test** - For integration testing
- **Maven** - Build tool and dependency management
- **Java 17** - Programming language

## Project Structure

```
src/
├── main/java/com/acu/cors/
│   ├── RestServiceCorsApplication.java  # Main Spring Boot application
│   ├── Greeting.java                    # Data model
│   └── GreetingController.java          # REST controller with CORS
└── test/java/com/acu/cors/
    └── GreetingIntegrationTests.java    # Integration tests
script/
├── demo.sh                              # Automated demo script
└── cors-test.html                       # CORS test page
```

## Features

- **RESTful API**: Provides a `/greeting` endpoint that returns a JSON response
- **CORS Support**: Configured to allow cross-origin requests only from `http://localhost:9000`
- **Parameter Support**: Accepts an optional `name` parameter to customize the greeting
- **Auto-incrementing ID**: Each request gets a unique ID

## API Endpoints

### GET /greeting

Returns a greeting message in JSON format.

**Parameters:**
- `name` (optional): The name to greet (defaults to "World")

**Example Request:**
```bash
curl http://localhost:8080/greeting
```

**Example Response:**
```json
{
  "id": 1,
  "content": "Hello, World!"
}
```

**Example Request with Parameter:**
```bash
curl "http://localhost:8080/greeting?name=Spring"
```

**Example Response:**
```json
{
  "id": 2,
  "content": "Hello, Spring!"
}
```

## CORS Configuration

The application is configured to allow cross-origin requests only from `http://localhost:9000`. This is implemented using the `@CrossOrigin` annotation on the controller method:

```java
@CrossOrigin(origins = "http://localhost:9000")
@GetMapping("/greeting")
public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    // ...
}
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Building the Project

### Clean and Compile

```bash
mvn clean
mvn compile
```

### Run Tests

```bash
mvn test
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

## Testing the Application

### 1. Basic Functionality

Test the basic greeting endpoint:

```bash
curl http://localhost:8080/greeting
```

### 2. CORS Functionality

To test CORS, you can use the provided HTML file at `script/cors-test.html` and serve it from `http://localhost:9000`:

```html
<!DOCTYPE html>
<html>
<head>
    <title>CORS Test</title>
</head>
<body>
    <h1>CORS Test</h1>
    <button onclick="testCORS()">Test CORS</button>
    <div id="result"></div>

    <script>
        function testCORS() {
            fetch('http://localhost:8080/greeting?name=Test')
                .then(response => response.json())
                .then(data => {
                    document.getElementById('result').innerHTML = 
                        `ID: ${data.id}, Content: ${data.content}`;
                })
                .catch(error => {
                    document.getElementById('result').innerHTML = 
                        `Error: ${error.message}`;
                });
        }
    </script>
</body>
</html>
```

You can serve this HTML file using Python's built-in server:

```bash
python3 -m http.server 9000
```

Then access the test page at: `http://localhost:9000/script/cors-test.html`

### 3. Testing from Different Origins

If you try to access the API from a different origin (e.g., `http://localhost:3000`), the browser will block the request due to CORS policy.

## Quick Demo

Run the automated demo script to see everything in action:

```bash
./script/demo.sh
```

This script will:
- Check prerequisites (Java, Maven, Python3, curl)
- Build and test the project
- Start the Spring Boot application on port 8080
- Start an HTTP server on port 9000
- Serve the CORS test page
- Test the CORS functionality automatically
- Provide interactive testing instructions

## Verification Commands

Run these commands to verify everything is working:

```bash
# Clean the project
mvn clean

# Compile the project
mvn compile

# Run tests
mvn test

# Start the application
mvn spring-boot:run
```

All commands should execute without errors.

## Troubleshooting

1. **Port Already in Use**: If port 8080 is already in use, you can change it by adding `server.port=8081` to `application.properties` or by setting the `SERVER_PORT` environment variable.

2. **CORS Issues**: Make sure you're testing from `http://localhost:9000` as that's the only origin allowed by the CORS configuration.

3. **Java Version**: Ensure you're using Java 17 or higher. You can check your Java version with `java -version`.

## Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring CORS Documentation](https://docs.spring.io/spring-framework/reference/web/webmvc-cors.html)
- [Maven Documentation](https://maven.apache.org/guides/)
