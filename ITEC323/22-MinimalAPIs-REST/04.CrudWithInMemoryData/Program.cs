using CrudWithInMemoryDataDemo.Models;
using CrudWithInMemoryDataDemo.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddSingleton<ITaskRepository, InMemoryTaskRepository>();
builder.Services.AddSingleton<TaskValidator>();

var app = builder.Build();

app.MapGet("/", () => Results.Ok(new
{
    message = "CRUD with in-memory data demo",
    endpoints = new[]
    {
        "GET /api/tasks",
        "GET /api/tasks/{id}",
        "POST /api/tasks",
        "PUT /api/tasks/{id}",
        "DELETE /api/tasks/{id}"
    }
}));

app.MapGet("/api/tasks", (ITaskRepository repository) => Results.Ok(repository.GetAll()));

app.MapGet(
    "/api/tasks/{id:int}",
    (int id, ITaskRepository repository) =>
    {
        var task = repository.GetById(id);
        return task is null
            ? Results.NotFound(new { error = $"Task with id {id} was not found." })
            : Results.Ok(task);
    });

app.MapPost(
    "/api/tasks",
    (CreateTaskRequest request, TaskValidator validator, ITaskRepository repository) =>
    {
        var validationError = validator.ValidateCreate(request);
        if (validationError is not null)
        {
            return Results.BadRequest(new { error = validationError });
        }

        var createdTask = repository.Create(request);
        return Results.Created($"/api/tasks/{createdTask.Id}", createdTask);
    });

app.MapPut(
    "/api/tasks/{id:int}",
    (int id, UpdateTaskRequest request, TaskValidator validator, ITaskRepository repository) =>
    {
        var validationError = validator.ValidateUpdate(request);
        if (validationError is not null)
        {
            return Results.BadRequest(new { error = validationError });
        }

        var updatedTask = repository.Update(id, request);
        return updatedTask is null
            ? Results.NotFound(new { error = $"Task with id {id} was not found." })
            : Results.Ok(updatedTask);
    });

app.MapDelete(
    "/api/tasks/{id:int}",
    (int id, ITaskRepository repository) =>
        repository.Delete(id)
            ? Results.NoContent()
            : Results.NotFound(new { error = $"Task with id {id} was not found." }));

app.Run();
