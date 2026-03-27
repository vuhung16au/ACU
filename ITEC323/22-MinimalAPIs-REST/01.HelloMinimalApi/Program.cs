using HelloMinimalApiDemo.Models;
using HelloMinimalApiDemo.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddSingleton<GreetingService>();
builder.Services.AddSingleton<LanguageCatalogService>();
builder.Services.AddSingleton<SubmissionEchoService>();

var app = builder.Build();

app.MapGet("/", () => Results.Ok(new
{
    message = "Welcome to Hello Minimal API.",
    topic = "Minimal APIs and REST basics",
    endpoints = new[]
    {
        "GET /api/message",
        "GET /api/languages",
        "GET /api/greet/{name}",
        "GET /api/submissions/latest",
        "POST /api/submissions"
    }
}));

app.MapGet("/api/message", () => Results.Ok(new
{
    title = "Hello from Minimal APIs",
    description = "This endpoint returns a simple JSON message."
}));

app.MapGet(
    "/api/languages",
    (LanguageCatalogService languageCatalogService) =>
        Results.Ok(languageCatalogService.GetLanguages()));

app.MapGet(
    "/api/greet/{name}",
    (string name, GreetingService greetingService) =>
        Results.Ok(new GreetingResponse
        {
            Greeting = greetingService.CreateGreeting(name)
        }));

app.MapGet(
    "/api/submissions/latest",
    () => Results.Ok(new FormSubmissionResponse
    {
        Name = "Ada Lovelace",
        Email = "ada@example.com",
        FavoriteLanguage = "C#",
        Message = "This is a sample response that shows the shape of a created resource."
    }));

app.MapPost(
    "/api/submissions",
    (FormSubmissionRequest request, SubmissionEchoService submissionEchoService) =>
    {
        var validationError = submissionEchoService.Validate(request);
        if (validationError is not null)
        {
            return Results.BadRequest(new
            {
                error = validationError
            });
        }

        var response = submissionEchoService.CreateResponse(request);
        return Results.Created("/api/submissions/latest", response);
    });

app.Run();
