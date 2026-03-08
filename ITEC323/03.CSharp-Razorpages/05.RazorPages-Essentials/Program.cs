// ============================================================================
// Program.cs - Application Entry Point
// ============================================================================
//
// This file configures and starts the ASP.NET Core web application.
// It sets up services, middleware, and the request processing pipeline.
//
// Key Concepts:
// - WebApplication.CreateBuilder(): Creates a new web application builder
// - builder.Services: Configures services (dependency injection)
// - app.UseXxx(): Configures middleware (request processing pipeline)
// - app.Run(): Starts the web server and waits for requests
// ============================================================================

var builder = WebApplication.CreateBuilder(args);

// Add services to the container
// AddRazorPages() enables Razor Pages support
builder.Services.AddRazorPages();

var app = builder.Build();

// Configure the HTTP request pipeline
// Middleware runs in the order they are added

// Use exception handler page in production
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    app.UseHsts(); // HTTP Strict Transport Security
}

// Redirect HTTP requests to HTTPS
app.UseHttpsRedirection();

// Serve static files from wwwroot folder (CSS, images, etc.)
app.UseStaticFiles();

// Enable routing (maps URLs to Razor Pages)
app.UseRouting();

// Enable authorization (checking if user has permission)
app.UseAuthorization();

// Map Razor Pages to routes
app.MapRazorPages();

// Start the web server and begin listening for requests
app.Run();
