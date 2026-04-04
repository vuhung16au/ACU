using Microsoft.SemanticKernel;
using SemanticKernelLMStudio.Models;
using SemanticKernelLMStudio.Services;

var builder = WebApplication.CreateBuilder(args);

var environmentFileLoader = new EnvironmentFileLoader();
environmentFileLoader.Load(Path.Combine(builder.Environment.ContentRootPath, ".env.local"));

var lmStudioEndpoint = builder.Configuration["LMStudio:Endpoint"] ?? "http://localhost:1234/v1";
var chatModelId = builder.Configuration["LMStudio:ChatModelId"] ?? "local-model";
var embeddingModelId = builder.Configuration["LMStudio:EmbeddingModelId"] ?? "text-embedding-model";
var apiKey = builder.Configuration["LMStudio:ApiKey"] ?? "lm-studio";
var lmStudioBaseEndpoint = lmStudioEndpoint.EndsWith("/v1", StringComparison.OrdinalIgnoreCase)
    ? lmStudioEndpoint[..^3]
    : lmStudioEndpoint;

builder.Services.AddSingleton(environmentFileLoader);
builder.Services.AddHttpClient<LmStudioEmbeddingService>(client =>
{
    client.BaseAddress = new Uri(lmStudioEndpoint.EndsWith('/') ? lmStudioEndpoint : $"{lmStudioEndpoint}/");
    client.Timeout = TimeSpan.FromSeconds(15);
});
builder.Services.AddSingleton<Kernel>(_ =>
{
    var endpoint = new Uri(lmStudioEndpoint);

    return Kernel.CreateBuilder()
        .AddOpenAIChatCompletion(
            modelId: chatModelId,
            endpoint: endpoint,
            apiKey: apiKey)
        .Build();
});

builder.Services.AddSingleton<ChatService>();
builder.Services.AddSingleton<RagService>();

var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    var ragService = scope.ServiceProvider.GetRequiredService<RagService>();

    var documentsPath = Path.Combine(app.Environment.ContentRootPath, "KnowledgeBase", "documents.txt");
    if (File.Exists(documentsPath))
    {
        try
        {
            if (await IsLmStudioReachableAsync(lmStudioBaseEndpoint))
            {
                var documents = File.ReadAllLines(documentsPath)
                    .Where(line => !string.IsNullOrWhiteSpace(line))
                    .ToList();

                await ragService.IndexDocumentsAsync(documents);
            }
            else
            {
                app.Logger.LogWarning("Knowledge base indexing was skipped because LM Studio is not reachable at {Endpoint}.", lmStudioBaseEndpoint);
            }
        }
        catch (Exception ex)
        {
            app.Logger.LogWarning(ex, "Knowledge base indexing was skipped during startup.");
        }
    }
}

app.MapGet("/", () =>
    Results.Ok(new AppInfoResponse(
        "Semantic Kernel + LM Studio Demo",
        "A beginner-friendly ASP.NET Core Minimal API that demonstrates local chat completion and RAG with Semantic Kernel.",
        ["GET /", "POST /api/chat", "POST /api/rag"])));

app.MapPost("/api/chat", async (ChatRequest? request, ChatService chatService) =>
{
    if (request is null || string.IsNullOrWhiteSpace(request.Prompt))
    {
        return Results.BadRequest("Prompt cannot be empty.");
    }

    try
    {
        var content = await chatService.GenerateAsync(
            request.Prompt.Trim(),
            request.MaxTokens ?? 500,
            request.Temperature ?? 0.7f);

        return Results.Ok(new ChatResponse(
            Model: chatModelId,
            Content: content,
            PromptTokens: 0,
            CompletionTokens: 0));
    }
    catch (InvalidOperationException ex)
    {
        return Results.Problem(
            title: "Chat request failed",
            detail: ex.Message,
            statusCode: StatusCodes.Status500InternalServerError);
    }
});

app.MapPost("/api/rag", async (RagRequest? request, RagService ragService) =>
{
    if (request is null || string.IsNullOrWhiteSpace(request.Question))
    {
        return Results.BadRequest("Question cannot be empty.");
    }

    try
    {
        var response = await ragService.AskAsync(
            request.Question.Trim(),
            request.TopK,
            request.MaxTokens ?? 500);

        return Results.Ok(response);
    }
    catch (InvalidOperationException ex)
    {
        return Results.Problem(
            title: "RAG request failed",
            detail: ex.Message,
            statusCode: StatusCodes.Status500InternalServerError);
    }
});

app.Run();

static async Task<bool> IsLmStudioReachableAsync(string endpoint)
{
    using var httpClient = new HttpClient
    {
        Timeout = TimeSpan.FromSeconds(2)
    };

    try
    {
        using var response = await httpClient.GetAsync($"{endpoint.TrimEnd('/')}/models");
        return response.IsSuccessStatusCode;
    }
    catch
    {
        return false;
    }
}
