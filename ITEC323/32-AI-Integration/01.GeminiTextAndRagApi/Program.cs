using GeminiTextAndRagApiDemo.Models;
using GeminiTextAndRagApiDemo.Services;
using Microsoft.Extensions.Options;

var builder = WebApplication.CreateBuilder(args);

var environmentFileLoader = new EnvironmentFileLoader();
environmentFileLoader.Load(Path.Combine(builder.Environment.ContentRootPath, ".env.local"));

builder.Services.Configure<GeminiOptions>(builder.Configuration.GetSection(GeminiOptions.SectionName));
builder.Services.Configure<RagOptions>(builder.Configuration.GetSection(RagOptions.SectionName));

builder.Services.AddSingleton(environmentFileLoader);
builder.Services.AddSingleton<KnowledgeBaseService>();
builder.Services.AddSingleton<KeywordSearchService>();
builder.Services.AddSingleton<ITextGenerationService, GeminiTextService>();
builder.Services.AddSingleton<RagAnswerService>();

var app = builder.Build();

app.MapGet("/", (IOptions<GeminiOptions> geminiOptions, IOptions<RagOptions> ragOptions) =>
    Results.Ok(new AppInfoResponse
    {
        Title = "Gemini Text And RAG API",
        Description = "A beginner-friendly ASP.NET Core Minimal API that demonstrates direct text generation and simple Retrieval-Augmented Generation.",
        Model = geminiOptions.Value.Model,
        MaxDocuments = ragOptions.Value.MaxDocuments,
        Endpoints =
        [
            "GET /",
            "GET /api/documents",
            "POST /api/generate",
            "POST /api/rag-answer"
        ]
    }));

app.MapGet("/api/documents", (KnowledgeBaseService knowledgeBaseService) =>
{
    var documents = knowledgeBaseService.GetDocuments()
        .Select(document => new
        {
            document.Id,
            document.Title,
            document.Category,
            document.Keywords
        });

    return Results.Ok(documents);
});

app.MapPost("/api/generate", async (GenerateTextRequest? request, ITextGenerationService textGenerationService, IOptions<GeminiOptions> geminiOptions) =>
{
    if (request is null || string.IsNullOrWhiteSpace(request.Prompt))
    {
        return CreateBadRequest("Please provide a non-empty prompt.");
    }

    try
    {
        var responseText = await textGenerationService.GenerateTextAsync(request.Prompt.Trim());
        return Results.Ok(new GenerateTextResponse
        {
            Prompt = request.Prompt.Trim(),
            Model = geminiOptions.Value.Model,
            ResponseText = responseText
        });
    }
    catch (InvalidOperationException ex)
    {
        return CreateServerError(ex.Message);
    }
    catch (Exception ex)
    {
        return CreateServerError($"Gemini request failed: {ex.Message}");
    }
});

app.MapPost("/api/rag-answer", async (RagQuestionRequest? request, RagAnswerService ragAnswerService) =>
{
    if (request is null || string.IsNullOrWhiteSpace(request.Question))
    {
        return CreateBadRequest("Please provide a non-empty question.");
    }

    try
    {
        var response = await ragAnswerService.AnswerQuestionAsync(request.Question.Trim());
        return Results.Ok(response);
    }
    catch (InvalidOperationException ex)
    {
        return CreateServerError(ex.Message);
    }
    catch (Exception ex)
    {
        return CreateServerError($"RAG request failed: {ex.Message}");
    }
});

app.Run();

static IResult CreateBadRequest(string message)
{
    return Results.BadRequest(new ApiErrorResponse
    {
        Error = "ValidationError",
        Message = message
    });
}

static IResult CreateServerError(string message)
{
    return Results.Problem(
        title: "Request failed",
        detail: message,
        statusCode: StatusCodes.Status500InternalServerError);
}
