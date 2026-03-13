using PromisesAsyncAwait.Models;
using PromisesAsyncAwait.Services;

var builder = WebApplication.CreateBuilder(args);

// Register Razor Pages and the in-memory data service used by the API demos.
builder.Services.AddRazorPages();
builder.Services.AddSingleton<AsyncLearningSessionService>();

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

var sessionsApi = app.MapGroup("/api/learningsessions");

sessionsApi.MapGet(
    string.Empty,
    async (AsyncLearningSessionService service, CancellationToken cancellationToken) =>
    {
        await Task.Delay(350, cancellationToken);
        var sessions = await service.GetSessionsAsync(cancellationToken);
        return Results.Ok(sessions);
    });

sessionsApi.MapGet(
    "/summary",
    async (AsyncLearningSessionService service, CancellationToken cancellationToken) =>
    {
        await Task.Delay(250, cancellationToken);
        var summary = await service.GetSummaryAsync(cancellationToken);
        return Results.Ok(summary);
    });

sessionsApi.MapPost(
    string.Empty,
    async (CreateLearningSessionRequest request, AsyncLearningSessionService service, CancellationToken cancellationToken) =>
    {
        if (string.IsNullOrWhiteSpace(request.StudentName) || string.IsNullOrWhiteSpace(request.Topic))
        {
            return Results.BadRequest(new { message = "StudentName and Topic are required." });
        }

        var createdSession = await service.AddSessionAsync(request, cancellationToken);
        return Results.Created($"/api/learningsessions/{createdSession.Id}", createdSession);
    });

sessionsApi.MapGet(
    "/failure-demo",
    () => Results.Problem("Simulated server error for async error handling practice.", statusCode: 500));

app.MapRazorPages();

app.Run();
