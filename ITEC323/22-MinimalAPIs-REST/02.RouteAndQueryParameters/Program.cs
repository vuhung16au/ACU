using RouteAndQueryParametersDemo.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddSingleton<TaskCatalogService>();

var app = builder.Build();

app.MapGet("/", () => Results.Ok(new
{
    message = "Route and query parameter demo",
    topic = "Minimal APIs parameter binding",
    endpoints = new[]
    {
        "GET /api/tasks/{id}",
        "GET /api/tasks/search?priority=High",
        "GET /api/tasks/filter?completed=true"
    }
}));

app.MapGet(
    "/api/tasks/{id:int}",
    (int id, TaskCatalogService taskCatalogService) =>
    {
        var task = taskCatalogService.GetById(id);
        return task is null
            ? Results.NotFound(new { error = $"Task with id {id} was not found." })
            : Results.Ok(task);
    });

app.MapGet(
    "/api/tasks/search",
    (string? priority, TaskCatalogService taskCatalogService) =>
        Results.Ok(taskCatalogService.FilterByPriority(priority)));

app.MapGet(
    "/api/tasks/filter",
    (bool? completed, TaskCatalogService taskCatalogService) =>
        Results.Ok(taskCatalogService.FilterByCompletion(completed)));

app.Run();
