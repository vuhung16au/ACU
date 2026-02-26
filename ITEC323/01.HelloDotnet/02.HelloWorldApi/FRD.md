# Functional Requirements Document (FRD)
## Hello World API Application

**Project Name**: Hello World API  
**Version**: 1.0  
**Date**: February 2026  
**Course**: ITEC323 - Application Development  
**Institution**: Australian Catholic University (ACU)

---

## 1. Purpose

The purpose of this application is to serve as an introductory educational project that demonstrates the fundamental concepts of building web APIs with ASP.NET Core using the minimal API approach. This application provides students with their first hands-on experience creating, running, and understanding a web API using the .NET framework.

### Educational Goals:
- Introduce students to ASP.NET Core and web API development
- Demonstrate the minimal API pattern introduced in .NET 6+
- Show the basic structure of a minimal API project
- Illustrate how HTTP requests map to API endpoints
- Teach the fundamentals of web servers and HTTP
- Provide a foundation for building RESTful services

---

## 2. Scope

### 2.1 In Scope
This application will:
- Respond to HTTP GET requests at the root path (`/`) with "Hello World!"
- Start and run a web server using Kestrel
- Listen on both HTTP and HTTPS ports
- Use the modern minimal hosting model
- Include proper documentation for educational purposes
- Serve as a reference implementation for course assignments
- Demonstrate the simplest possible ASP.NET Core API

### 2.2 Out of Scope
This application will NOT:
- Include database connectivity
- Implement authentication or authorization
- Provide multiple endpoints (deliberately kept minimal)
- Include complex business logic
- Use external NuGet packages beyond the framework
- Implement error handling middleware (uses defaults)
- Provide Swagger/OpenAPI documentation
- Include logging beyond framework defaults
- Handle POST, PUT, DELETE, or other HTTP methods
- Return structured data (JSON, XML) - returns plain text only

---

## 3. Functional Requirements

### FR-1: Application Startup
**Priority**: Critical  
**Description**: The application must start successfully using the .NET CLI.

**Acceptance Criteria**:
- Application can be started with `dotnet run` command
- Application can be started with `dotnet watch run` for hot reload
- Application listens on both HTTP and HTTPS ports
- Application starts without errors or warnings
- Application displays startup messages showing the listening URLs
- Startup time is under 5 seconds

**Technical Details**:
- Uses Kestrel web server
- Default ports: 5050 (HTTP), 7050 (HTTPS) as configured in `launchSettings.json`
- Application continues running until manually stopped (Ctrl+C)
- Uses `WebApplication.CreateBuilder(args)` for configuration

**Testing**:
```bash
dotnet run
# Should display:
# Now listening on: http://localhost:5050
# Now listening on: https://localhost:7050
```

---

### FR-2: Root Endpoint Response
**Priority**: Critical  
**Description**: The application must respond to HTTP GET requests at the root path with "Hello World!".

**Acceptance Criteria**:
- GET request to `/` returns HTTP 200 (OK) status
- Response body contains the exact text "Hello World!"
- Response is plain text (text/plain content type)
- Response is returned within 100ms
- Works on both HTTP and HTTPS protocols
- Accessible from web browsers and command-line tools

**Technical Details**:
- Endpoint defined using `app.MapGet("/", () => "Hello World!");`
- Uses minimal API syntax with lambda expression
- No controller or routing classes needed
- Returns string directly (framework handles serialization)

**Testing Methods**:
1. **Browser**: Navigate to `http://localhost:5050`
2. **curl**: `curl http://localhost:5050`
3. **PowerShell**: `Invoke-WebRequest http://localhost:5050`
4. **VS Code REST Client**: `GET http://localhost:5050`

**Expected Response**:
```
HTTP/1.1 200 OK
Content-Type: text/plain; charset=utf-8
Content-Length: 12
Date: <timestamp>

Hello World!
```

---

### FR-3: HTTP Protocol Handling
**Priority**: Critical  
**Description**: The application must correctly handle HTTP/HTTPS protocols and respond appropriately.

**Acceptance Criteria**:
- Supports HTTP/1.1 protocol
- Supports HTTPS with development certificate
- Properly handles GET requests
- Returns appropriate HTTP status codes
- Sets correct Content-Type headers
- Responds to OPTIONS requests (CORS preflight)

**Technical Details**:
- HTTP on port 5050
- HTTPS on port 7050
- Development certificate managed by .NET SDK
- Status code 200 for successful requests
- Status code 404 for non-existent routes
- Status code 405 for unsupported methods

---

### FR-4: Configuration Management
**Priority**: High  
**Description**: The application must support configuration through appsettings.json files.

**Acceptance Criteria**:
- `appsettings.json` is loaded on startup
- `appsettings.Development.json` overrides settings in Development environment
- Logging configuration is read from settings
- Configuration is accessible to the application
- Environment-specific settings are properly isolated

**Technical Details**:
- Uses `WebApplication.CreateBuilder(args)` which loads configuration automatically
- Configuration hierarchy:
  1. appsettings.json
  2. appsettings.{Environment}.json
  3. Environment variables
  4. Command-line arguments

**Configuration Structure**:
```json
{
  "Logging": {
    "LogLevel": {
      "Default": "Information",
      "Microsoft.AspNetCore": "Warning"
    }
  },
  "AllowedHosts": "*"
}
```

---

### FR-5: Logging
**Priority**: High  
**Description**: The application must log startup, shutdown, and request information.

**Acceptance Criteria**:
- Logs application startup with listening URLs
- Logs hosting environment (Development, Production)
- Logs content root path
- Logs shutdown messages
- Logs HTTP requests (in Development mode)
- Log levels are configurable via appsettings.json

**Technical Details**:
- Uses built-in .NET logging
- Default log level: Information
- ASP.NET Core logs set to Warning to reduce noise
- Logs to console by default
- No custom logging configuration required

**Sample Log Output**:
```
info: Microsoft.Hosting.Lifetime[14]
      Now listening on: http://localhost:5050
info: Microsoft.Hosting.Lifetime[0]
      Application started. Press Ctrl+C to shut down.
```

---

### FR-6: Build and Compilation
**Priority**: Critical  
**Description**: The application must compile successfully without errors.

**Acceptance Criteria**:
- `dotnet build` completes without errors
- `dotnet restore` successfully retrieves all dependencies
- Project file (`.csproj`) is valid
- All C# syntax is correct
- No compiler warnings for the application code
- Compatible with .NET 8.0, 9.0, and 10.0

**Technical Details**:
- Targets .NET 10.0
- Uses `Microsoft.NET.Sdk.Web` SDK
- Implicit usings enabled
- Nullable reference types enabled
- No external NuGet packages required

**Project File Requirements**:
```xml
<Project Sdk="Microsoft.NET.Sdk.Web">
  <PropertyGroup>
    <TargetFramework>net10.0</TargetFramework>
    <Nullable>enable</Nullable>
    <ImplicitUsings>enable</ImplicitUsings>
  </PropertyGroup>
</Project>
```

---

### FR-7: Environment Configuration
**Priority**: High  
**Description**: The application must support different environments (Development, Production).

**Acceptance Criteria**:
- `ASPNETCORE_ENVIRONMENT` variable is respected
- Development environment enables detailed error messages
- Production environment uses secure defaults
- Launch settings define environment for local development
- Environment is displayed in logs on startup

**Technical Details**:
- Default environment: Development (for local testing)
- Configured in `Properties/launchSettings.json`
- Can be overridden with environment variable
- Affects logging, error pages, and other behaviors

**Environment Variables**:
```json
"environmentVariables": {
  "ASPNETCORE_ENVIRONMENT": "Development"
}
```

---

### FR-8: Graceful Shutdown
**Priority**: Medium  
**Description**: The application must shut down gracefully when terminated.

**Acceptance Criteria**:
- Responds to Ctrl+C signal
- Logs shutdown message
- Closes server sockets properly
- Completes in-flight requests if possible
- Returns exit code 0 for normal shutdown
- No error messages during shutdown

**Technical Details**:
- Handled automatically by `app.Run()`
- Kestrel server shutdown grace period: 5 seconds
- No custom shutdown logic required

**Shutdown Sequence**:
1. Signal received (Ctrl+C)
2. Server stops accepting new requests
3. Existing requests are completed
4. Application logs shutdown
5. Process terminates

---

### FR-9: Port Configuration
**Priority**: High  
**Description**: The application must listen on configurable ports.

**Acceptance Criteria**:
- HTTP port is configurable (default: 5050)
- HTTPS port is configurable (default: 7050)
- Ports can be changed via launchSettings.json
- Ports can be overridden via command line
- Application fails gracefully if ports are unavailable

**Technical Details**:
- Configured in `Properties/launchSettings.json`
- Can be overridden with `--urls` parameter
- Format: `http://localhost:port` or `https://localhost:port`

**Configuration**:
```json
"applicationUrl": "https://localhost:7050;http://localhost:5050"
```

**Command-line Override**:
```bash
dotnet run --urls "http://localhost:8080"
```

---

### FR-10: Browser Launch
**Priority**: Low  
**Description**: The application should optionally open a browser on startup.

**Acceptance Criteria**:
- Browser opens automatically when `launchBrowser: true`
- Default browser is used
- Opens to the configured URL
- Works on Windows, macOS, and Linux
- Gracefully handles failure to launch browser

**Technical Details**:
- Configured in `Properties/launchSettings.json`
- Set to `true` for convenience in Development
- Uses system default browser
- Non-blocking (app starts even if browser fails)

---

## 4. Non-Functional Requirements

### NFR-1: Performance
**Description**: The application must be performant and responsive.

**Requirements**:
- Startup time: < 5 seconds
- Response time: < 100ms for root endpoint
- Memory usage: < 50MB at idle
- Can handle 100 concurrent requests
- No memory leaks during extended operation

**Validation**:
- Use `time dotnet run` to measure startup
- Use browser DevTools to measure response time
- Use system monitor for memory usage

---

### NFR-2: Reliability
**Description**: The application must be stable and reliable.

**Requirements**:
- Runs without crashes for extended periods
- Handles malformed requests gracefully
- Does not hang or become unresponsive
- Recovers from transient errors
- Consistent behavior across runs

**Validation**:
- Run continuously for 1 hour without issues
- Send various HTTP methods (POST, PUT, DELETE)
- Send requests to non-existent paths
- Send malformed HTTP requests

---

### NFR-3: Usability
**Description**: The application must be easy to understand and use for beginners.

**Requirements**:
- Code is simple and readable
- Extensive inline comments explain concepts
- Clear console output messages
- Obvious how to start and stop
- Error messages are helpful

**Validation**:
- Student testers can run without assistance
- Code comments explain all parts
- Startup messages are clear
- Error messages guide users to solutions

---

### NFR-4: Maintainability
**Description**: The code must be maintainable and well-documented.

**Requirements**:
- Follows C# naming conventions
- Includes XML documentation comments
- Code is properly formatted
- No unused code or comments
- Clear separation of concerns (though minimal here)

**Validation**:
- Code passes linting
- Documentation is complete
- Follows ITEC323 coding standards

---

### NFR-5: Portability
**Description**: The application must run on multiple platforms.

**Requirements**:
- Runs on Windows, macOS, and Linux
- Compatible with .NET 8.0, 9.0, and 10.0
- No platform-specific code
- Uses standard .NET APIs only
- Paths use platform-agnostic format

**Validation**:
- Test on Windows 10/11
- Test on macOS (Intel and Apple Silicon)
- Test on Linux (Ubuntu, Fedora)
- Test with .NET 8.0 and 10.0

---

### NFR-6: Security
**Description**: The application must follow basic security practices.

**Requirements**:
- HTTPS is available and encouraged
- Development certificate is trusted
- No hardcoded secrets or credentials
- Uses secure defaults from framework
- HTTPS redirection in production (though not enforced in this simple example)

**Validation**:
- HTTPS endpoint works correctly
- No credentials in source code
- Certificate validation works

---

## 5. Technical Constraints

### 5.1 Platform Constraints
- **Operating System**: Windows, macOS, or Linux
- **.NET Version**: .NET 8.0 or later (targets 10.0)
- **Web Server**: Kestrel (included with ASP.NET Core)
- **IDE**: Any code editor (VS Code, Visual Studio, Rider, etc.)

### 5.2 Development Constraints
- Must use .NET CLI for building and running
- Must follow minimal API pattern (no controllers)
- Must be a single-file application (Program.cs only for logic)
- Must not require external packages beyond framework
- Must be compatible with the course curriculum

### 5.3 Educational Constraints
- Code must be simple enough for beginners
- Every line should be explainable
- No advanced patterns or abstractions
- Documentation must be extensive
- Must serve as a learning tool

---

## 6. Dependencies

### 6.1 Runtime Dependencies
- **.NET Runtime**: Version 8.0 or higher
- **ASP.NET Core**: Included with .NET SDK
- **Kestrel Web Server**: Included with ASP.NET Core

### 6.2 Development Dependencies
- **.NET SDK**: Version 8.0 or higher
- **Code Editor**: Any with C# support
- **Web Browser**: For testing (any modern browser)
- **Command Line**: Terminal or command prompt

### 6.3 Optional Tools
- **curl**: For command-line testing
- **Postman**: For API testing
- **VS Code REST Client**: For integrated testing
- **Git**: For version control

---

## 7. Testing Strategy

### 7.1 Manual Testing
Students should manually test:
1. Building the application
2. Running the application
3. Accessing the endpoint via browser
4. Stopping the application
5. Modifying the message
6. Testing different ports

### 7.2 Automated Testing
- Unit tests are not included (kept simple for beginners)
- Integration tests are not included
- Will be introduced in later projects

### 7.3 Validation Checklist
- [ ] Application builds without errors
- [ ] Application runs without errors
- [ ] Browser displays "Hello World!"
- [ ] HTTP and HTTPS both work
- [ ] Can be stopped with Ctrl+C
- [ ] Hot reload works with `dotnet watch`
- [ ] Works on student's development machine

---

## 8. Documentation Requirements

### 8.1 Project Documentation
All projects must include:
- **README.md**: Overview and learning objectives
- **QUICKSTART.md**: Step-by-step instructions
- **FRD.md**: This document
- **docs/MinimalApiBasics.md**: Detailed technical concepts

### 8.2 Code Documentation
All code must include:
- XML documentation comments on Program.cs
- Inline comments explaining each section
- Clear variable names
- Consistent formatting

### 8.3 Educational Content
Documentation must:
- Explain concepts, not just syntax
- Provide context and reasoning
- Include examples and comparisons
- Link to additional resources
- Anticipate common questions

---

## 9. Success Criteria

This project is successful if students can:

1. **Create the Project**
   - Use `dotnet new web` to create a minimal API
   - Understand the generated project structure

2. **Understand the Code**
   - Explain what each line in Program.cs does
   - Understand the request/response cycle
   - Describe how routing works

3. **Run the Application**
   - Start the app with `dotnet run`
   - Test the endpoint in a browser
   - Stop the application properly

4. **Modify the Application**
   - Change the response message
   - Add new endpoints
   - Understand the impact of changes

5. **Troubleshoot Issues**
   - Resolve port conflicts
   - Trust HTTPS certificates
   - Fix simple syntax errors

6. **Build Upon This Foundation**
   - Ready to add more endpoints
   - Ready to return JSON data
   - Ready to learn routing and middleware

---

## 10. Future Enhancements (Out of Scope for v1.0)

These features are intentionally omitted but could be added in future projects:

1. **Multiple Endpoints**: Add `/api/greet/{name}` endpoint
2. **JSON Responses**: Return structured data objects
3. **HTTP Methods**: Add POST, PUT, DELETE handlers
4. **Middleware**: Add logging, error handling, CORS
5. **Swagger/OpenAPI**: Add interactive API documentation
6. **Database**: Connect to a database
7. **Authentication**: Add user authentication
8. **Validation**: Add input validation and error handling
9. **Testing**: Add unit and integration tests
10. **Deployment**: Deploy to Azure or AWS

---

## 11. References

### ASP.NET Core Documentation
- [Minimal APIs Overview](https://docs.microsoft.com/aspnet/core/fundamentals/minimal-apis)
- [Tutorial: Create a minimal API](https://docs.microsoft.com/aspnet/core/tutorials/min-web-api)
- [Routing in ASP.NET Core](https://docs.microsoft.com/aspnet/core/fundamentals/routing)

### Educational Resources
- [HTTP Protocol](https://developer.mozilla.org/en-US/docs/Web/HTTP)
- [REST API Design](https://restfulapi.net/)
- [Web API Best Practices](https://docs.microsoft.com/azure/architecture/best-practices/api-design)

### Course Materials
- ITEC323 Course Handbook
- Week 1 Lecture: Introduction to Web APIs
- Week 1 Lab: Building Your First API

---

## 12. Revision History

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0 | Feb 2026 | ITEC323 Team | Initial version |

---

## 13. Approval

This document will be reviewed and approved by:

- **Course Coordinator**: ITEC323
- **Technical Reviewer**: ACU Development Team
- **Educational Reviewer**: ACU Teaching Team

---

**Document Status**: Approved for Teaching  
**Next Review**: Start of Next Semester  
**Course**: ITEC323 - Application Development  
**Institution**: Australian Catholic University (ACU)
