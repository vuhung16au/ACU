// Import necessary namespaces for ASP.NET Core
using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;

// ============================================================================
// ASP.NET Core Razor Pages - Hello World
// ============================================================================
//
// This is the entry point for an ASP.NET Core web application.
// Program.cs configures and starts the web server that listens for HTTP requests.
//
// Key Concepts:
// - WebApplicationBuilder: Sets up the application configuration
// - Services: Dependencies that can be injected throughout the app
// - Middleware: Components that process HTTP requests and responses
// - Razor Pages: Page-based programming model for building web UI
// ============================================================================

// Create a builder instance
// This builder will configure our web application
var builder = WebApplication.CreateBuilder(args);

// ============================================================================
// 1. Configure Services (Dependency Injection Container)
// ============================================================================
// Services are objects that provide functionality to your application.
// Here we're adding Razor Pages support to our application.

builder.Services.AddRazorPages();

// ============================================================================
// 2. Build the Application
// ============================================================================
// The Build() method creates the actual web application instance
// based on the configuration we've set up.

var app = builder.Build();

// ============================================================================
// 3. Configure the HTTP Request Pipeline (Middleware)
// ============================================================================
// Middleware components process HTTP requests in a specific order.
// Each middleware can:
// - Process the request
// - Pass it to the next middleware
// - Short-circuit the pipeline
// - Modify the response

// Check if we're in Development or Production mode
if (!app.Environment.IsDevelopment())
{
    // In production, use the error handler page
    app.UseExceptionHandler("/Error");
    
    // HSTS (HTTP Strict Transport Security)
    // Tells browsers to only access the site via HTTPS
    app.UseHsts();
}

// Redirect HTTP requests to HTTPS for security
app.UseHttpsRedirection();

// Enable serving static files (CSS, JavaScript, images)
// from the wwwroot folder
app.UseStaticFiles();

// Enable routing - matches incoming requests to endpoints
app.UseRouting();

// Enable authorization - checks if users have permission
app.UseAuthorization();

// Map Razor Pages as endpoints
// This tells the application to handle requests using Razor Pages
app.MapRazorPages();

// ============================================================================
// 4. Run the Application
// ============================================================================
// Start the web server and begin listening for HTTP requests
// The application will run until stopped (Ctrl+C)

app.Run();

// ============================================================================
// How This Works:
// ============================================================================
// 1. builder.Services.AddRazorPages() - Registers Razor Pages services
// 2. var app = builder.Build() - Creates the web application
// 3. Middleware configuration - Sets up how requests are processed
// 4. app.Run() - Starts the web server
//
// When a user visits http://localhost:5000 in their browser:
// 1. The web server receives the HTTP request
// 2. Routing middleware determines which page to load (Index.cshtml)
// 3. Razor engine processes the .cshtml file
// 4. HTML is generated and sent back to the browser
// 5. The browser displays the page
// ============================================================================
