# Minimal API Basics - Understanding Your First Web API

This document provides a deep dive into the concepts and technologies used in the Hello World API application. It's designed to help you understand not just *what* the code does, but *why* it works the way it does.

## Table of Contents

1. [What is a Web API?](#what-is-a-web-api)
2. [What are Minimal APIs?](#what-are-minimal-apis)
3. [Understanding the Code](#understanding-the-code)
4. [HTTP Protocol Basics](#http-protocol-basics)
5. [Routing in Minimal APIs](#routing-in-minimal-apis)
6. [The Application Pipeline](#the-application-pipeline)
7. [How Web Servers Work](#how-web-servers-work)
8. [Comparing API Approaches](#comparing-api-approaches)

---

## What is a Web API?

### The Big Picture

A **Web API** (Application Programming Interface) is software that allows different applications to communicate with each other over the internet using HTTP. Think of it as a waiter in a restaurant:

- **You (the client)** make a request (order food)
- **The waiter (the API)** takes your request to the kitchen
- **The kitchen (the server/database)** prepares what you asked for
- **The waiter (the API)** brings back the response (your food)

### Why APIs Matter

APIs power most of the modern internet:
- **Mobile Apps**: Apps on your phone talk to APIs to get data
- **Websites**: Many websites fetch data from APIs instead of databases directly
- **Microservices**: Large applications are broken into smaller APIs
- **Integration**: Different systems communicate through APIs

### Real-World Examples

- **Weather App**: Fetches data from a weather API
- **Social Media**: Your posts are sent to an API that stores them
- **Online Shopping**: Product information comes from an API
- **Maps**: Location data comes from mapping APIs

---

## What are Minimal APIs?

### The Modern Approach

**Minimal APIs** are a streamlined way to build HTTP APIs introduced in .NET 6. They reduce the amount of code you need to write while still being powerful and production-ready.

### Before Minimal APIs (Traditional Approach)

The traditional approach used **controllers**, which required more setup:

```csharp
// Traditional approach - requires multiple files

// Startup.cs or Program.cs
public void ConfigureServices(IServiceCollection services)
{
    services.AddControllers();
}

public void Configure(IApplicationBuilder app)
{
    app.UseRouting();
    app.UseEndpoints(endpoints =>
    {
        endpoints.MapControllers();
    });
}

// HelloController.cs - separate file
[ApiController]
[Route("[controller]")]
public class HelloController : ControllerBase
{
    [HttpGet]
    public string Get()
    {
        return "Hello World!";
    }
}
```

### With Minimal APIs (Modern Approach)

The same functionality in just a few lines:

```csharp
var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

app.MapGet("/", () => "Hello World!");

app.Run();
```

### Benefits of Minimal APIs

1. **Less Code**: Fewer lines to write and maintain
2. **Easier to Learn**: Simpler syntax for beginners
3. **Faster to Develop**: Get running quickly
4. **Still Powerful**: Can grow to handle complex scenarios
5. **Modern**: Uses latest C# features

### When to Use Each Approach

**Use Minimal APIs for:**
- Learning and education
- Small microservices
- Quick prototypes
- Simple APIs with few endpoints

**Use Controllers for:**
- Large APIs with many endpoints
- Complex routing requirements
- When you need more structure
- Existing codebases using controllers

---

## Understanding the Code

Let's break down each line of our minimal API:

### The Complete Code

```csharp
var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

app.MapGet("/", () => "Hello World!");

app.Run();
```

Only 4 meaningful lines! Let's understand each one.

### Line 1: Creating the Builder

```csharp
var builder = WebApplication.CreateBuilder(args);
```

**What it does:**
- Creates a `WebApplicationBuilder` object
- `args` are command-line arguments passed to the program
- Sets up default configuration, logging, and services

**What happens behind the scenes:**
1. Loads configuration from `appsettings.json`
2. Loads environment-specific settings
3. Sets up a dependency injection container
4. Configures logging to the console
5. Configures Kestrel web server
6. Sets up the hosting environment

**Think of it as:**
Setting up the foundation of your house before building.

### Line 2: Building the Application

```csharp
var app = builder.Build();
```

**What it does:**
- Takes the builder configuration and creates a `WebApplication`
- This app object represents your running web server
- All configuration is now locked in

**What happens behind the scenes:**
1. Validates all configuration
2. Creates the web server (Kestrel)
3. Sets up the HTTP pipeline
4. Prepares to handle requests

**Think of it as:**
Actually building the house based on the foundation.

### Line 3: Mapping an Endpoint

```csharp
app.MapGet("/", () => "Hello World!");
```

This is the most important line! Let's break it down:

**`app.MapGet`**
- Tells the API to respond to GET requests
- `MapGet` is one of several methods: `MapPost`, `MapPut`, `MapDelete`, etc.

**`"/"`**
- The route pattern (URL path)
- `/` means the root of the website
- Could be `/hello`, `/api/users`, etc.

**`() => "Hello World!"`**
- A lambda expression (anonymous function)
- `()` means no parameters
- `=>` separates parameters from the return value
- `"Hello World!"` is what gets returned

**Full breakdown:**
```csharp
app.MapGet(
    "/",                    // Route: what URL triggers this?
    () => "Hello World!"    // Handler: what code runs?
);
```

**Think of it as:**
Telling the waiter (API): "When someone asks for '/' (the menu), give them 'Hello World!' (the response)."

### Line 4: Running the Application

```csharp
app.Run();
```

**What it does:**
- Starts the web server
- Begins listening for HTTP requests
- Blocks the application (keeps it running)
- Will continue until stopped (Ctrl+C)

**What happens behind the scenes:**
1. Opens network ports (5050, 7050)
2. Listens for incoming connections
3. For each request, routes it to the appropriate handler
4. Returns responses to clients

**Think of it as:**
Opening the restaurant doors and starting to serve customers.

---

## HTTP Protocol Basics

### What is HTTP?

**HTTP** (Hypertext Transfer Protocol) is the language of the web. It defines how browsers and servers communicate.

### HTTP Request Structure

When you visit `http://localhost:5050/`, your browser sends:

```
GET / HTTP/1.1
Host: localhost:5050
User-Agent: Mozilla/5.0 (...)
Accept: text/html,application/json
```

Breaking it down:
- **`GET`**: The HTTP method (what action to perform)
- **`/`**: The path (what resource to access)
- **`HTTP/1.1`**: The protocol version
- **Headers**: Additional information about the request

### HTTP Response Structure

The server responds with:

```
HTTP/1.1 200 OK
Content-Type: text/plain; charset=utf-8
Content-Length: 12
Date: Wed, 26 Feb 2026 12:00:00 GMT

Hello World!
```

Breaking it down:
- **`HTTP/1.1`**: Protocol version
- **`200 OK`**: Status code (request succeeded)
- **Headers**: Information about the response
- **Blank line**: Separates headers from body
- **`Hello World!`**: The response body (the actual content)

### HTTP Methods (Verbs)

Different methods mean different actions:

| Method | Purpose | Example |
|--------|---------|---------|
| GET | Retrieve data | Get a list of users |
| POST | Create new data | Create a new user |
| PUT | Update existing data | Update a user's email |
| DELETE | Remove data | Delete a user |
| PATCH | Partially update | Update just the user's name |

Our minimal API only handles GET requests.

### HTTP Status Codes

Status codes tell you what happened:

**2xx - Success**
- `200 OK`: Request succeeded
- `201 Created`: New resource created
- `204 No Content`: Success, but no data to return

**3xx - Redirection**
- `301 Moved Permanently`: Resource moved to new URL
- `302 Found`: Temporary redirect

**4xx - Client Errors**
- `400 Bad Request`: Invalid request
- `404 Not Found`: Resource doesn't exist
- `401 Unauthorized`: Authentication required
- `403 Forbidden`: Access denied

**5xx - Server Errors**
- `500 Internal Server Error`: Something went wrong on server
- `503 Service Unavailable`: Server is down or overloaded

---

## Routing in Minimal APIs

### What is Routing?

**Routing** is the process of matching an incoming HTTP request to a specific endpoint handler.

### Simple Routes

```csharp
app.MapGet("/", () => "Home page");
app.MapGet("/about", () => "About page");
app.MapGet("/contact", () => "Contact page");
```

- Request to `/` → returns "Home page"
- Request to `/about` → returns "About page"
- Request to `/contact` → returns "Contact page"

### Route Parameters

You can capture values from the URL:

```csharp
app.MapGet("/hello/{name}", (string name) => $"Hello, {name}!");
```

- Request to `/hello/Alice` → returns "Hello, Alice!"
- Request to `/hello/Bob` → returns "Hello, Bob!"
- `{name}` is a route parameter
- It's passed to the lambda function as `name`

### Multiple Parameters

```csharp
app.MapGet("/greet/{firstName}/{lastName}", 
    (string firstName, string lastName) => 
        $"Hello, {firstName} {lastName}!");
```

- Request to `/greet/John/Doe` → returns "Hello, John Doe!"

### Query Parameters

Different from route parameters:

```csharp
app.MapGet("/search", (string? query) => $"Searching for: {query}");
```

- Request to `/search?query=pizza` → returns "Searching for: pizza"
- `?query=pizza` is a query parameter
- Comes after `?` in the URL
- Can have multiple: `/search?query=pizza&location=Sydney`

### Route Constraints

Ensure parameters match a pattern:

```csharp
// Only accept numeric IDs
app.MapGet("/user/{id:int}", (int id) => $"User ID: {id}");

// Only accept specific formats
app.MapGet("/post/{slug:alpha}", (string slug) => $"Post: {slug}");
```

Common constraints:
- `:int` - Must be an integer
- `:bool` - Must be true/false
- `:datetime` - Must be a valid date
- `:alpha` - Must be alphabetic characters only
- `:length(5,10)` - Must be 5-10 characters long

---

## The Application Pipeline

### What is the Pipeline?

The **HTTP pipeline** is a series of steps that process each request. Think of it as an assembly line for HTTP requests.

### Pipeline Visualization

```
Request → Middleware 1 → Middleware 2 → Endpoint → Response
              ↓             ↓              ↓
          logging      authentication   your code
```

### Our Simple Pipeline

In our minimal API, the pipeline is very simple:

```csharp
var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

// The pipeline is defined here
app.MapGet("/", () => "Hello World!");  // Define endpoint

app.Run();  // Start listening
```

### Adding Middleware

Middleware are components that run before your endpoint:

```csharp
var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

// Add middleware
app.UseHttpsRedirection();  // Redirect HTTP to HTTPS
app.UseRouting();           // Enable routing

// Define endpoints
app.MapGet("/", () => "Hello World!");

app.Run();
```

### Order Matters!

Middleware runs in the order you add it:

```csharp
app.UseHttpsRedirection();  // 1. Runs first
app.UseAuthentication();    // 2. Then authentication
app.UseAuthorization();     // 3. Then authorization

app.MapGet("/", () => "Hello!");  // 4. Finally your endpoint
```

### Common Middleware

- **`UseHttpsRedirection()`**: Redirects HTTP to HTTPS
- **`UseAuthentication()`**: Checks if user is logged in
- **`UseAuthorization()`**: Checks if user has permission
- **`UseCors()`**: Handles cross-origin requests
- **`UseStaticFiles()`**: Serves CSS, JS, images

---

## How Web Servers Work

### The Kestrel Web Server

**Kestrel** is the web server built into ASP.NET Core. It's:
- **Fast**: One of the fastest web servers available
- **Cross-platform**: Works on Windows, macOS, Linux
- **Modern**: Supports HTTP/1.1, HTTP/2, HTTP/3
- **Integrated**: Built into .NET, no separate installation

### The Request/Response Cycle

1. **Client makes request**
   - Browser or API client sends HTTP request
   - Includes method, path, headers, body

2. **Server receives request**
   - Kestrel accepts the TCP connection
   - Parses the HTTP request

3. **Routing matches endpoint**
   - Request URL is matched to a route
   - Appropriate handler is found

4. **Handler executes**
   - Your lambda function runs
   - Returns a result

5. **Response sent**
   - Result is formatted as HTTP response
   - Sent back to the client

6. **Connection closed or kept alive**
   - Connection can close or be reused

### Ports and URLs

When you see:
```
Now listening on: http://localhost:5050
```

Breaking it down:
- **`http://`**: Protocol (HTTP, not HTTPS)
- **`localhost`**: Computer name (your own machine)
- **`5050`**: Port number (like an apartment number)

**Localhost** means "this computer":
- `localhost` = `127.0.0.1` (IPv4 address)
- `localhost` = `::1` (IPv6 address)
- Only accessible from your own computer
- Perfect for development

### Multiple Ports

Your app listens on two ports:
- **5050** (HTTP): Unencrypted
- **7050** (HTTPS): Encrypted with TLS/SSL

Why two?
- Development: Either works
- Production: HTTPS is mandatory for security

---

## Comparing API Approaches

### Minimal APIs vs Controllers

Both can accomplish the same goals. Here's the same API in each style:

#### Minimal APIs

```csharp
// Program.cs
var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

app.MapGet("/users", () => new[] { "Alice", "Bob" });
app.MapGet("/users/{id}", (int id) => $"User {id}");
app.MapPost("/users", (User user) => $"Created {user.Name}");

app.Run();
```

**Pros:**
- ✅ Concise and readable
- ✅ All in one file
- ✅ Quick to write
- ✅ Easy to understand

**Cons:**
- ❌ Can get messy with many endpoints
- ❌ Less structure
- ❌ Harder to test in isolation

#### Controller-Based APIs

```csharp
// Program.cs
var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
var app = builder.Build();

app.MapControllers();
app.Run();

// UsersController.cs
[ApiController]
[Route("[controller]")]
public class UsersController : ControllerBase
{
    [HttpGet]
    public IActionResult GetAll() => Ok(new[] { "Alice", "Bob" });
    
    [HttpGet("{id}")]
    public IActionResult Get(int id) => Ok($"User {id}");
    
    [HttpPost]
    public IActionResult Create(User user) => Ok($"Created {user.Name}");
}
```

**Pros:**
- ✅ Organized structure
- ✅ Easier to test
- ✅ Better for large APIs
- ✅ More features (filters, model binding)

**Cons:**
- ❌ More boilerplate code
- ❌ Steeper learning curve
- ❌ Multiple files needed

### When to Use Each

**Choose Minimal APIs when:**
- You're learning
- Building a small service
- Want fast development
- Have few endpoints (< 10-20)
- Prototyping

**Choose Controllers when:**
- Building a large API
- Need strict organization
- Have complex routing
- Many endpoints (> 20)
- Team prefers structure

### Can You Mix Both?

Yes! You can use both in the same project:

```csharp
var builder = WebApplication.CreateBuilder(args);

// Add controller support
builder.Services.AddControllers();

var app = builder.Build();

// Mix minimal API endpoints
app.MapGet("/health", () => "OK");

// And controller endpoints
app.MapControllers();

app.Run();
```

---

## Advanced Concepts (Preview)

These topics are beyond this introductory project but good to know about:

### Returning Different Types

```csharp
// Return an object (automatically becomes JSON)
app.MapGet("/user", () => new { Name = "Alice", Age = 30 });

// Return status codes
app.MapGet("/not-found", () => Results.NotFound());
app.MapGet("/created", () => Results.Created("/user/1", new { Id = 1 }));

// Return different content types
app.MapGet("/html", () => Results.Content("<h1>Hello</h1>", "text/html"));
```

### Dependency Injection

```csharp
// Register a service
builder.Services.AddSingleton<IUserService, UserService>();

// Use it in an endpoint
app.MapGet("/users", (IUserService userService) => 
    userService.GetAll());
```

### Reading Request Data

```csharp
// From route
app.MapGet("/user/{id}", (int id) => $"User {id}");

// From query string
app.MapGet("/search", (string? q) => $"Searching: {q}");

// From body (POST)
app.MapPost("/user", (User user) => $"Created: {user.Name}");

// From headers
app.MapGet("/info", (HttpContext context) => 
    context.Request.Headers["User-Agent"].ToString());
```

### Error Handling

```csharp
// Try-catch
app.MapGet("/risky", () =>
{
    try
    {
        // Risky code
        return "Success";
    }
    catch (Exception ex)
    {
        return Results.Problem(ex.Message);
    }
});

// Validation
app.MapPost("/user", (User user) =>
{
    if (string.IsNullOrEmpty(user.Name))
        return Results.BadRequest("Name is required");
    
    return Results.Ok(user);
});
```

### Async Operations

```csharp
// For I/O operations (database, files, network)
app.MapGet("/data", async () =>
{
    await Task.Delay(1000);  // Simulated async work
    return "Data loaded";
});
```

---

## Best Practices

### 1. Keep Endpoints Simple

❌ Bad:
```csharp
app.MapGet("/", () =>
{
    var data = LoadFromDatabase();
    var processed = ProcessData(data);
    var formatted = FormatOutput(processed);
    return formatted;
});
```

✅ Good:
```csharp
app.MapGet("/", (IDataService service) => service.GetProcessedData());
```

### 2. Use Proper HTTP Methods

❌ Bad:
```csharp
app.MapGet("/deleteUser", (int id) => DeleteUser(id));
```

✅ Good:
```csharp
app.MapDelete("/users/{id}", (int id) => DeleteUser(id));
```

### 3. Return Appropriate Status Codes

❌ Bad:
```csharp
app.MapGet("/user/{id}", (int id) =>
{
    var user = FindUser(id);
    return user ?? throw new Exception("Not found");
});
```

✅ Good:
```csharp
app.MapGet("/user/{id}", (int id) =>
{
    var user = FindUser(id);
    return user is not null ? Results.Ok(user) : Results.NotFound();
});
```

### 4. Use Meaningful Routes

❌ Bad:
```csharp
app.MapGet("/api/v1/endpoint1", () => "...");
```

✅ Good:
```csharp
app.MapGet("/api/users", () => "...");
```

---

## Common Patterns

### RESTful API Structure

REST (Representational State Transfer) is a common API design pattern:

```csharp
// GET /users - Get all users
app.MapGet("/users", () => GetAllUsers());

// GET /users/5 - Get specific user
app.MapGet("/users/{id}", (int id) => GetUser(id));

// POST /users - Create new user
app.MapPost("/users", (User user) => CreateUser(user));

// PUT /users/5 - Update user
app.MapPut("/users/{id}", (int id, User user) => UpdateUser(id, user));

// DELETE /users/5 - Delete user
app.MapDelete("/users/{id}", (int id) => DeleteUser(id));
```

### API Versioning

```csharp
// Version in URL
app.MapGet("/api/v1/users", () => "...");
app.MapGet("/api/v2/users", () => "...");

// Version in header (more advanced)
app.MapGet("/api/users", (HttpContext ctx) =>
{
    var version = ctx.Request.Headers["Api-Version"].ToString();
    return version == "2.0" ? GetUsersV2() : GetUsersV1();
});
```

---

## Troubleshooting Guide

### Problem: Port Already in Use

**Error:**
```
Unable to bind to http://localhost:5050 on the IPv4 loopback interface: 
'Address already in use'.
```

**Solutions:**
1. Stop other applications using that port
2. Change the port in `launchSettings.json`
3. Use `--urls` parameter: `dotnet run --urls "http://localhost:5051"`

### Problem: Cannot Access from Another Device

**Symptom:** API works on `localhost` but not from other computers

**Solution:** Listen on all interfaces:
```csharp
var builder = WebApplication.CreateBuilder(args);
builder.WebHost.UseUrls("http://*:5050");  // Listen on all IP addresses
var app = builder.Build();
```

### Problem: HTTPS Certificate Error

**Error:** "Your connection is not private" in browser

**Solution:** Trust the development certificate:
```bash
dotnet dev-certs https --trust
```

---

## Learning Resources

### Official Microsoft Documentation
- [Minimal APIs Overview](https://docs.microsoft.com/aspnet/core/fundamentals/minimal-apis)
- [Tutorial: Create a minimal web API](https://docs.microsoft.com/aspnet/core/tutorials/min-web-api)
- [ASP.NET Core fundamentals](https://docs.microsoft.com/aspnet/core/fundamentals/)

### HTTP and Web Basics
- [MDN HTTP Guide](https://developer.mozilla.org/en-US/docs/Web/HTTP)
- [HTTP Status Codes](https://httpstatuses.com/)
- [REST API Tutorial](https://restfulapi.net/)

### Video Tutorials
- [.NET YouTube Channel](https://www.youtube.com/dotnet)
- Microsoft Learn modules on ASP.NET Core

---

## Next Steps

Now that you understand minimal APIs, try:

1. **Add More Endpoints**
   - Create `/hello/{name}` endpoint
   - Add a `/date` endpoint that returns current date

2. **Return JSON**
   - Create an object and return it
   - Observe how it's automatically converted to JSON

3. **Use Query Parameters**
   - Create `/search?q=term` endpoint
   - Handle optional parameters

4. **Add Validation**
   - Check if parameters are valid
   - Return appropriate error messages

5. **Learn Middleware**
   - Add logging middleware
   - Add error handling middleware

6. **Connect to a Database**
   - Learn Entity Framework Core
   - Store and retrieve data

---

**Course**: ITEC323 - Application Development  
**Institution**: Australian Catholic University (ACU)  
**Last Updated**: February 2026

This document is designed to grow with you. As you learn more concepts, refer back to this guide to deepen your understanding.
