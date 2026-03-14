# Functional Requirements Document (FRD)
## Week 7: AJAX and Dynamic Content

---

## 1. Purpose Statement

This module teaches students to create dynamic, responsive web applications using AJAX techniques, enabling asynchronous data exchange without full-page reloads. Students will learn multiple approaches (Fetch API, HTMX, Blazor) and build RESTful APIs with ASP.NET Core.

---

## 2. Scope

### In Scope
- JavaScript Fetch API for asynchronous requests
- Axios library as Fetch API alternative
- HTMX for declarative partial page updates
- Blazor Server for C#-based single-page applications
- ASP.NET Core Web API development
- Loading states and error handling
- CORS configuration
- API documentation with Swagger
- Real-time updates with polling and SignalR
- Mock data for external API simulation

### Out of Scope
- Advanced JavaScript frameworks (React, Vue, Angular)
- Blazor WebAssembly (focus on Blazor Server)
- WebSockets (except through SignalR in Blazor)
- Database integration (use in-memory storage)
- Authentication/authorization
- Production deployment

---

## 3. Learning Objectives

Students will be able to:

1. **LO1:** Make asynchronous HTTP requests using JavaScript Fetch API
2. **LO2:** Build RESTful Web APIs with proper HTTP verbs and status codes
3. **LO3:** Update specific page sections without full reload (partial updates)
4. **LO4:** Implement HTMX for declarative AJAX without custom JavaScript
5. **LO5:** Create basic Blazor components with real-time updates
6. **LO6:** Handle loading states and errors in asynchronous operations
7. **LO7:** Configure CORS for cross-origin requests
8. **LO8:** Document APIs using Swagger/OpenAPI
9. **LO9:** Implement client-side polling for periodic updates
10. **LO10:** Choose appropriate AJAX technique for different scenarios

---

## 4. Functional Requirements

### FR1: Asynchronous Data Fetching
**Priority:** MUST HAVE  
**Learning Objective:** LO1

Students must demonstrate ability to:
- Use JavaScript Fetch API with GET requests
- Handle JSON responses from server
- Parse and display data dynamically
- Implement async/await patterns

**Acceptance Criteria:**
- Button click triggers Fetch request
- Data displays without page reload
- Console shows no errors

---

### FR2: RESTful Web API Development
**Priority:** MUST HAVE  
**Learning Objective:** LO2

Students must demonstrate ability to:
- Create API controllers or PageHandler endpoints
- Use HTTP verb attributes ([HttpGet], [HttpPost], [HttpPut], [HttpDelete])
- Return JSON data with proper status codes
- Accept parameters from query strings and request bodies

**Acceptance Criteria:**
- API returns 200 OK for successful GET
- API returns 201 Created for successful POST
- API returns 404 Not Found for missing resources
- API returns 400 Bad Request for invalid input

---

### FR3: Partial Page Updates
**Priority:** MUST HAVE  
**Learning Objective:** LO3

Students must demonstrate ability to:
- Target specific DOM elements for updates
- Append or replace content dynamically
- Maintain page state during updates
- Update multiple sections independently

**Acceptance Criteria:**
- Only targeted section updates
- Page doesn't scroll to top
- Other content remains unchanged
- No full-page refresh

---

### FR4: HTMX Implementation
**Priority:** MUST HAVE  
**Learning Objective:** LO4

Students must demonstrate ability to:
- Use HTMX attributes (hx-get, hx-post, hx-target, hx-swap)
- Trigger updates on user interactions
- Return HTML fragments from server
- Display loading indicators

**Acceptance Criteria:**
- Update works without custom JavaScript
- Server returns HTML (not JSON)
- Loading indicator appears during request
- Content swaps correctly

---

### FR5: Blazor Components
**Priority:** SHOULD HAVE  
**Learning Objective:** LO5

Students must demonstrate ability to:
- Create Razor components (.razor files)
- Handle events with C# code
- Bind data with @bind directive
- Use SignalR for real-time updates (automatic)

**Acceptance Criteria:**
- Component renders correctly
- Button clicks trigger C# methods
- State updates automatically across clients
- No manual JavaScript required

---

### FR6: Loading State Management
**Priority:** MUST HAVE  
**Learning Objective:** LO6

Students must demonstrate ability to:
- Display loading spinners during requests
- Disable buttons to prevent double-submission
- Show skeleton screens for content placeholders
- Handle errors with user-friendly messages

**Acceptance Criteria:**
- Loading indicator appears immediately
- UI is non-interactive during request
- Error messages are clear and helpful
- Success states are visually distinct

---

### FR7: CORS Configuration
**Priority:** SHOULD HAVE  
**Learning Objective:** LO7

Students must demonstrate ability to:
- Understand CORS purpose and security implications
- Configure CORS policy in Program.cs
- Allow specific origins or all origins (development only)
- Test cross-origin requests

**Acceptance Criteria:**
- API accessible from different ports (dev scenario)
- CORS errors resolved
- Security considerations documented

---

### FR8: API Documentation with Swagger
**Priority:** SHOULD HAVE  
**Learning Objective:** LO8

Students must demonstrate ability to:
- Install Swashbuckle.AspNetCore package
- Enable Swagger UI
- Add XML comments for documentation
- Test endpoints in Swagger UI

**Acceptance Criteria:**
- Swagger UI accessible at /swagger
- All endpoints documented
- Parameters and responses described
- API testable through UI

---

### FR9: Real-Time Updates
**Priority:** SHOULD HAVE  
**Learning Objective:** LO9

Students must demonstrate ability to:
- Use setInterval() for client-side polling
- Fetch fresh data periodically (e.g., every 15 seconds)
- Update UI automatically
- Clean up intervals on page unload

**Acceptance Criteria:**
- Data refreshes automatically
- No memory leaks (intervals cleared)
- Manual refresh option available
- Latest data displayed

---

### FR10: Complete Local Application
**Priority:** MUST HAVE  
**Learning Objective:** LO10

Students must demonstrate ability to:
- Build self-contained app (no internet required)
- Implement CRUD operations with in-memory storage
- Create both frontend and backend
- Run entirely locally

**Acceptance Criteria:**
- App runs offline
- All CRUD operations work
- Data persists during session
- No external API dependencies

---

## 5. Non-Functional Requirements

### NFR1: Performance
- API responses within 200ms for local endpoints
- Loading indicators appear within 100ms
- Page updates complete within 500ms

### NFR2: Usability
- Clear visual feedback for all user actions
- Intuitive UI for API testing (Swagger)
- Helpful error messages
- Consistent loading patterns

### NFR3: Code Quality
- All public methods documented with XML comments
- Async/await used correctly (no blocking calls)
- Error handling on all API calls
- Clean separation of concerns

### NFR4: Browser Compatibility
- Works in Chrome, Firefox, Safari, Edge (latest versions)
- Graceful degradation if JavaScript disabled (where possible)
- Responsive design for mobile/desktop

### NFR5: Educational Value
- Code is readable and well-commented
- Examples demonstrate best practices
- Progressive complexity
- Real-world applicable patterns

---

## 6. Technical Constraints

### TC1: Technology Stack
- ASP.NET Core 10.0
- C# 14
- Razor Pages and Blazor Server
- JavaScript ES6+
- Bootstrap 5.3

### TC2: Development Environment
- Visual Studio Code or Visual Studio 2022
- .NET SDK 8.0 or higher
- Modern web browser
- Local development only (no cloud deployment)

### TC3: Dependencies
- Swashbuckle.AspNetCore (Swagger)
- HTMX (CDN)
- Axios (CDN, optional - for comparison with Fetch API)
- Bootstrap (CDN)
- No external API services required (mock data only)

---

## 7. Project-Specific Requirements

### Project 01: BasicFetchAPI
- Single page with button
- Local API endpoint returning JSON
- Display fetched data in &lt;div&gt;
- Basic loading spinner

### Project 02: PartialPageUpdates
- Product list with pagination
- View Component for product cards
- Load More button appends items
- No full-page reload

### Project 03: HtmxPartialRendering
- Search input with live results
- HTMX attributes only (no custom JS)
- Server returns HTML fragments
- Loading indicators

### Project 04: LocalTodoApi
- Complete CRUD API (GET, POST, PUT, DELETE)
- In-memory storage
- RESTful endpoints
- Offline-capable

### Project 05: WeatherDashboard
- Mock weather data (no external API required)
- Simulates weather data with random updates
- Auto-refresh every 15 seconds (client-side polling)
- Manual refresh button
- Demonstrates setInterval() and data fetching patterns

### Project 06: BlazorCounter
- Simple counter component
- Increment/decrement buttons
- Real-time sync (SignalR automatic)
- Introduction to Blazor

### Project 07: ComprehensiveApp
- Multi-technique integration
- Swagger documentation
- CORS configuration
- Production-ready patterns

---

## 8. Success Criteria

Students successfully complete this module when they can:

1. ✅ Build a working AJAX application using Fetch API
2. ✅ Create a RESTful API with proper HTTP verbs
3. ✅ Implement partial page updates without reload
4. ✅ Use HTMX for declarative updates
5. ✅ Create a basic Blazor component
6. ✅ Handle loading states and errors
7. ✅ Configure and document APIs with Swagger
8. ✅ Explain when to use each AJAX technique
9. ✅ Build a complete local CRUD application
10. ✅ Implement auto-refresh with polling

---

## 9. Out-of-Scope Items

The following are explicitly NOT covered in this module:

- ❌ Advanced SPA frameworks (React, Angular, Vue)
- ❌ Blazor WebAssembly
- ❌ Database integration (use in-memory only)
- ❌ Authentication and authorization
- ❌ Production deployment and hosting
- ❌ GraphQL APIs
- ❌ WebSockets (except SignalR in Blazor)
- ❌ Service Workers and PWAs
- ❌ Server-Sent Events (SSE)

---

## 10. Glossary

**AJAX:** Asynchronous JavaScript and XML (now typically JSON)  
**SPA:** Single Page Application  
**REST:** Representational State Transfer  
**CORS:** Cross-Origin Resource Sharing  
**SignalR:** Real-time communication library for ASP.NET Core  
**Swagger:** API documentation tool (OpenAPI specification)  
**WCF:** Windows Communication Foundation (legacy .NET Framework)  
**Fetch API:** Modern browser API for HTTP requests  
**Axios:** Promise-based HTTP client library (alternative to Fetch)  
**HTMX:** Library for partial page updates using HTML attributes  
**Blazor:** C#-based framework for building interactive web UIs  
**Mock Data:** Simulated data for testing without external dependencies  
