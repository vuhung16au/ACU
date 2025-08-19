# Student Client-Server Application

This JavaFX application demonstrates client-server architecture for collecting and storing student information.

## Overview

The application consists of three main components:

1. **StudentAddress.java** - A serializable class that holds student information (name, street, city, state, zip)
2. **StudentClient.java** - A JavaFX GUI application that collects student information from users
3. **StudentServer.java** - A server application that receives student data and stores it to a file

## Features

- **JavaFX GUI**: Modern user interface for data entry
- **Client-Server Architecture**: Demonstrates network communication between client and server
- **Object Serialization**: Sends and receives serialized objects over the network
- **File Storage**: Server saves received student data to a file

## Prerequisites

Before running the application, make sure you have:

- **Java 21** or higher installed
- **Maven** installed
- **JavaFX** (included in the project dependencies)

To check your Java version:
```bash
java -version
```

To check your Maven version:
```bash
mvn -version
```

## How to Run This App

### Method 1: Using Convenience Scripts (Recommended)

#### Step 1: Start the Server
Open a terminal and run:
```bash
./run-server.sh
```

You should see:
```
Starting Student Server...
Server will listen on port 8000
Press Ctrl+C to stop the server

[INFO] Scanning for projects...
[INFO] Building Student-Client-Server-App 1.0
...
Server started on port 8000
```

**Keep this terminal window open** - the server must be running for the client to connect.

#### Step 2: Start the Client
Open a **new terminal window** and run:
```bash
./run-client.sh
```

You should see:
```
Starting Student Client...
Make sure the server is running first!

[INFO] Scanning for projects...
[INFO] Building Student-Client-Server-App 1.0
...
```

A JavaFX window titled "StudentClient" will open.

#### Step 3: Use the Application
1. Fill in the student information:
   - **Name**: Enter the student's full name
   - **Street**: Enter the street address
   - **City**: Enter the city name
   - **State**: Enter the state abbreviation (e.g., GA, CA, NY)
   - **Zip**: Enter the ZIP code
2. Click the **"Register to the Server"** button
3. The data will be sent to the server and saved to `student.dat`
4. The form will clear automatically for the next entry
5. Check the server terminal for confirmation messages

### Method 2: Using Maven Commands

#### Step 1: Build the Project
```bash
mvn clean compile
```

#### Step 2: Start the Server
```bash
mvn exec:java -Dexec.mainClass="com.example.StudentServer"
```

#### Step 3: Start the Client (in a new terminal)
```bash
mvn javafx:run
```

### Method 3: Direct Java Execution

#### Step 1: Build the Project
```bash
mvn clean compile
```

#### Step 2: Start the Server
```bash
cd target/classes
java com.example.StudentServer
```

#### Step 3: Start the Client (in a new terminal)
```bash
cd target/classes
java com.example.StudentClient
```

## Testing the Application

1. **Start the server** using any method above
2. **Start the client** in a separate terminal
3. **Enter test data**:
   - Name: `John Smith`
   - Street: `100 Main Street`
   - City: `Savannah`
   - State: `GA`
   - Zip: `31411`
4. **Click "Register to the Server"**
5. **Check the server terminal** - you should see:
   ```
   Client connected from: 127.0.0.1
   A new student object is stored: StudentAddress{name='John Smith', street='100 Main Street', city='Savannah', state='GA', zip='31411'}
   ```
6. **Check for the data file** - a `student.dat` file should be created in your project directory

## Troubleshooting

### Common Issues:

#### 1. "Connection refused" error
- **Cause**: Server is not running
- **Solution**: Start the server first before the client

#### 2. "Port 8000 already in use" error
- **Cause**: Another application is using port 8000
- **Solution**: 
  - Kill the existing process: `lsof -ti:8000 | xargs kill -9`
  - Or modify the port in `StudentServer.java` and `StudentClient.java`

#### 3. JavaFX window doesn't appear
- **Cause**: JavaFX modules not properly configured
- **Solution**: Use the Maven commands or ensure JavaFX is in your classpath

#### 4. Permission denied on scripts
- **Cause**: Scripts are not executable
- **Solution**: Run `chmod +x run-server.sh run-client.sh`

### Stopping the Application

- **Client**: Close the JavaFX window
- **Server**: Press `Ctrl+C` in the server terminal

## Project Structure

```
src/main/java/com/example/
├── StudentAddress.java    # Serializable student data class
├── StudentClient.java     # JavaFX client application
├── StudentServer.java     # Server application
└── App.java              # Main application launcher

Scripts:
├── run-server.sh         # Server startup script
└── run-client.sh         # Client startup script
```

## Technical Details

- **Network Protocol**: TCP sockets on port 8000
- **Data Format**: Java object serialization
- **Storage**: Binary file (`student.dat`) using ObjectOutputStream
- **GUI Framework**: JavaFX with GridPane layout
- **Java Version**: 21
- **JavaFX Version**: 24.0.2

## Building and Running

### Build the project:
```bash
mvn clean compile
```

### Run the client:
```bash
mvn javafx:run
```

### Run the server:
```bash
mvn exec:java -Dexec.mainClass="com.example.StudentServer"
```

### Create executable JAR:
```bash
mvn clean package
```

## Notes

- The server must be running before the client can connect
- The server runs continuously and accepts multiple client connections
- Student data is appended to the `student.dat` file
- The application demonstrates proper resource management with try-catch-finally blocks
- You can run multiple clients simultaneously to test concurrent connections

