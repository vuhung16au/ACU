using RequestBodiesAndValidationDemo.Models;
using RequestBodiesAndValidationDemo.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddSingleton<TaskRequestValidator>();
builder.Services.AddSingleton<TaskCreationService>();
builder.Services.AddSingleton<InMemoryTaskStore>();

var app = builder.Build();

app.MapGet("/", () => Results.Ok(new
{
    message = "Request body and validation demo",
    endpoints = new[]
    {
        "GET /api/tasks",
        "GET /api/tasks/{id}",
        "GET /api/priorities",
        "POST /api/tasks",
        "PUT /api/tasks/{id}",
        "DELETE /api/tasks/{id}"
    }
}));

app.MapGet(
    "/api/tasks",
    (InMemoryTaskStore taskStore) =>
        Results.Ok(taskStore.GetAll()));

app.MapGet(
    "/api/tasks/{id:int}",
    (int id, InMemoryTaskStore taskStore) =>
    {
        var task = taskStore.GetById(id);
        return task is null
            ? Results.NotFound(new { error = $"Task with id {id} was not found." })
            : Results.Ok(task);
    });

app.MapGet("/api/priorities", () => Results.Ok(TaskRequestValidator.AllowedPriorities));

app.MapPost(
    "/api/tasks",
    (CreateTaskRequest request, TaskRequestValidator validator, TaskCreationService taskCreationService, InMemoryTaskStore taskStore) =>
    {
        var validationError = validator.Validate(request);
        if (validationError is not null)
        {
            return Results.BadRequest(new { error = validationError });
        }

        var createdTask = taskStore.Add(taskCreationService.Create(request));
        return Results.Created($"/api/tasks/{createdTask.Id}", createdTask);
    });

app.MapPut(
    "/api/tasks/{id:int}",
    (int id, UpdateTaskRequest request, TaskRequestValidator validator, TaskCreationService taskCreationService, InMemoryTaskStore taskStore) =>
    {
        var validationError = validator.Validate(request);
        if (validationError is not null)
        {
            return Results.BadRequest(new { error = validationError });
        }

        var updatedTask = taskStore.Update(id, taskCreationService.CreateUpdatedTask(id, request));
        return updatedTask is null
            ? Results.NotFound(new { error = $"Task with id {id} was not found." })
            : Results.Ok(updatedTask);
    });

app.MapDelete(
    "/api/tasks/{id:int}",
    (int id, InMemoryTaskStore taskStore) =>
        taskStore.Delete(id)
            ? Results.NoContent()
            : Results.NotFound(new { error = $"Task with id {id} was not found." }));

app.Run();
