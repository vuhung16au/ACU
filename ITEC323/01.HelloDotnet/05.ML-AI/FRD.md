# Functional Requirements Document (FRD)
## Hello World AI Chat Application (LM Studio)

**Project Name**: Hello World AI Chat  
**Version**: 1.0  
**Date**: February 2026  
**Course**: ITEC323 - Application Development  
**Institution**: Australian Catholic University (ACU)

---

## 1. Purpose

The purpose of this application is to introduce students to AI-assisted application development in .NET by building a simple command-line chat client that communicates with a local LLM served by LM Studio.

### Educational Goals
- Teach how to call an AI endpoint from a .NET console app
- Demonstrate request/response JSON handling in C#
- Introduce local inference workflow with LM Studio
- Show how user terminal input can drive AI interactions
- Provide a safe local-first setup without cloud keys

---

## 2. Scope

### 2.1 In Scope
This application will:
- Run as a .NET CLI console app
- Accept user input from terminal
- Send prompt to LM Studio endpoint (`http://localhost:1234/v1/chat/completions`)
- Display model response in terminal
- Continue chat loop until user types `exit`
- Include beginner-friendly documentation

### 2.2 Out of Scope
This application will NOT:
- Include cloud-hosted model providers
- Include user accounts or authentication
- Persist chat history to files/databases
- Provide GUI/web interface
- Support advanced features like tools/function calling

---

## 3. Functional Requirements

### FR-1: Application Startup
**Priority**: Critical  
**Description**: The app must run from the .NET CLI.

**Acceptance Criteria**:
- `dotnet run` starts the application
- Startup text explains endpoint and usage
- App remains running awaiting user input

---

### FR-2: User Prompt Input
**Priority**: Critical  
**Description**: The app must accept prompt text from the user.

**Acceptance Criteria**:
- User can type prompt in terminal
- Empty input is ignored safely
- Typing `exit` closes the app cleanly

---

### FR-3: Local LLM Request
**Priority**: Critical  
**Description**: The app must send the prompt to LM Studio local server.

**Acceptance Criteria**:
- Uses HTTP POST to `http://localhost:1234/v1/chat/completions`
- Sends model name and user message in JSON
- Uses OpenAI-compatible request shape (`model`, `messages`)

---

### FR-4: Response Display
**Priority**: Critical  
**Description**: The app must display the LLM response in terminal.

**Acceptance Criteria**:
- Parses JSON response body
- Reads first assistant message from response choices
- Prints assistant text as chat output

---

### FR-5: Error Handling
**Priority**: High  
**Description**: The app must handle common runtime errors clearly.

**Acceptance Criteria**:
- Handles connection failures to localhost endpoint
- Handles timeout scenarios
- Handles non-success HTTP status codes
- Prints readable error messages for beginners

---

### FR-6: Build and Compatibility
**Priority**: Critical  
**Description**: The app must build cleanly as a standard console project.

**Acceptance Criteria**:
- Targets .NET 10.0
- `dotnet restore` succeeds
- `dotnet build` succeeds
- No external API secrets required

---

## 4. Non-Functional Requirements

### NFR-1: Simplicity
- Keep architecture minimal and easy to follow
- Use straightforward `HttpClient` and JSON classes

### NFR-2: Maintainability
- Clear naming and folder structure
- Documentation aligned with code behavior

### NFR-3: Security
- No hardcoded cloud API keys
- Local endpoint only (`localhost`)

### NFR-4: Educational Value
- Code should be readable for first-time .NET learners
- Each step should map clearly to chat app behavior

---

## 5. Technical Requirements

### 5.1 Technology Stack
- **Application Type**: .NET Console Application
- **Framework**: .NET 10.0
- **Language**: C#
- **AI Runtime**: LM Studio local server
- **Protocol**: HTTP/JSON

### 5.2 Endpoint Configuration
- Default endpoint: `http://localhost:1234/v1/chat/completions`
- Model name can be set via `LMSTUDIO_MODEL` environment variable

---

## 6. Success Criteria

The project is successful when:
1. Students can run the app with `dotnet run`
2. Students can send prompts and receive AI responses locally
3. Students can understand request/response flow to LM Studio
4. Students can extend the app for future AI coursework
