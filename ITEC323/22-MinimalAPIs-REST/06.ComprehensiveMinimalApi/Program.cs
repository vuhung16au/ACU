using ComprehensiveMinimalApiDemo.Models;
using ComprehensiveMinimalApiDemo.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddProblemDetails();
builder.Services.AddExceptionHandler<GlobalExceptionHandler>();
builder.Services.AddSingleton<ITaskRepository, InMemoryTaskRepository>();
builder.Services.AddSingleton<TaskService>();

var app = builder.Build();

app.UseExceptionHandler();

app.MapGet("/", () => Results.Ok(new
{
    message = "Comprehensive Minimal API demo",
    topic = "Tasks API",
    endpoints = new[]
    {
        "GET /api/tasks",
        "GET /api/tasks/{id}",
        "GET /api/tasks/search?priority=High&completed=false",
        "GET /api/tasks/summary",
        "POST /api/tasks",
        "PUT /api/tasks/{id}",
        "DELETE /api/tasks/{id}"
    }
}));

app.MapGet("/api/tasks", (TaskService taskService) => Results.Ok(taskService.GetAll()));
app.MapGet("/api/tasks/summary", (TaskService taskService) => Results.Ok(taskService.GetSummary()));

app.MapGet(
    "/api/tasks/search",
    (string? priority, bool? completed, TaskService taskService) =>
        Results.Ok(taskService.Search(priority, completed)));

app.MapGet(
    "/api/tasks/{id:int}",
    (int id, TaskService taskService) =>
    {
        try
        {
            return Results.Ok(taskService.GetById(id));
        }
        catch (TaskNotFoundException exception)
        {
            return Results.NotFound(new { error = exception.Message });
        }
    });

app.MapPost(
    "/api/tasks",
    (CreateTaskRequest request, TaskService taskService) =>
    {
        try
        {
            var createdTask = taskService.Create(request);
            return Results.Created($"/api/tasks/{createdTask.Id}", createdTask);
        }
        catch (ArgumentException exception)
        {
            return Results.BadRequest(new { error = exception.Message });
        }
        catch (DuplicateTaskTitleException exception)
        {
            return Results.Conflict(new { error = exception.Message });
        }
    });

app.MapPut(
    "/api/tasks/{id:int}",
    (int id, UpdateTaskRequest request, TaskService taskService) =>
    {
        try
        {
            return Results.Ok(taskService.Update(id, request));
        }
        catch (ArgumentException exception)
        {
            return Results.BadRequest(new { error = exception.Message });
        }
        catch (TaskNotFoundException exception)
        {
            return Results.NotFound(new { error = exception.Message });
        }
        catch (DuplicateTaskTitleException exception)
        {
            return Results.Conflict(new { error = exception.Message });
        }
    });

app.MapDelete(
    "/api/tasks/{id:int}",
    (int id, TaskService taskService) =>
    {
        try
        {
            taskService.Delete(id);
            return Results.NoContent();
        }
        catch (TaskNotFoundException exception)
        {
            return Results.NotFound(new { error = exception.Message });
        }
    });

app.Run();
