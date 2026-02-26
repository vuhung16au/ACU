/// <summary>
/// Hello World API - A minimal API demonstration
/// This is the simplest form of an ASP.NET Core web application.
/// It demonstrates the minimal hosting model introduced in .NET 6+.
/// </summary>
/// 
// Create a web application builder
// This sets up default configuration, logging, and dependency injection
var builder = WebApplication.CreateBuilder(args);

// Build the application
// This creates an instance of WebApplication with all configured services
var app = builder.Build();

// Map a GET endpoint to the root path "/"
// When a user visits http://localhost:port/, this endpoint responds with "Hello World!"
app.MapGet("/", () => "Hello World!");

// Start the application and begin listening for HTTP requests
// The app will continue running until stopped (Ctrl+C)
app.Run();
