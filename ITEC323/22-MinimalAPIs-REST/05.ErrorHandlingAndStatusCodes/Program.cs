using ErrorHandlingAndStatusCodesDemo.Models;
using ErrorHandlingAndStatusCodesDemo.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddProblemDetails();
builder.Services.AddExceptionHandler<GlobalExceptionHandler>();
builder.Services.AddSingleton<TaskService>();

var app = builder.Build();

app.UseExceptionHandler();

app.MapGet("/", () => Results.Ok(new
{
    message = "Error handling and status code demo",
    endpoints = new[]
    {
        "GET /api/tasks/{id}",
        "POST /api/tasks",
        "POST /api/tasks/fail"
    }
}));

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
            var task = taskService.Create(request);
            return Results.Created($"/api/tasks/{task.Id}", task);
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

app.MapPost(
    "/api/tasks/fail",
    (TaskService taskService) =>
    {
        taskService.ThrowUnexpectedFailure();
        return Results.Ok();
    });

app.Run();
