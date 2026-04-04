namespace SemanticKernelLMStudio.Models;

/// <summary>
/// Represents the request body for the chat endpoint.
/// </summary>
/// <param name="Prompt">The user prompt to send to the model.</param>
/// <param name="MaxTokens">The maximum number of tokens to generate.</param>
/// <param name="Temperature">The sampling temperature for generation.</param>
public record ChatRequest(
    string Prompt,
    int? MaxTokens = 500,
    float? Temperature = 0.7f);

/// <summary>
/// Represents the response body for the chat endpoint.
/// </summary>
/// <param name="Model">The model identifier used for the response.</param>
/// <param name="Content">The generated content.</param>
/// <param name="PromptTokens">Prompt token count when available.</param>
/// <param name="CompletionTokens">Completion token count when available.</param>
public record ChatResponse(
    string Model,
    string Content,
    int PromptTokens,
    int CompletionTokens);

/// <summary>
/// Represents the request body for the RAG endpoint.
/// </summary>
/// <param name="Question">The question to answer.</param>
/// <param name="TopK">The number of matching chunks to retrieve.</param>
/// <param name="MaxTokens">The maximum number of tokens to generate.</param>
public record RagRequest(
    string Question,
    int TopK = 3,
    int? MaxTokens = 500);

/// <summary>
/// Represents the response body for the RAG endpoint.
/// </summary>
/// <param name="Answer">The grounded answer returned by the model.</param>
/// <param name="Sources">The source chunks used to answer the question.</param>
public record RagResponse(
    string Answer,
    List<SourceDocument> Sources);

/// <summary>
/// Represents one source chunk returned as part of a RAG response.
/// </summary>
/// <param name="Id">The chunk identifier.</param>
/// <param name="Text">The source text.</param>
/// <param name="Similarity">The similarity score for the match.</param>
public record SourceDocument(
    string Id,
    string Text,
    float Similarity);

/// <summary>
/// Represents basic information about the demo application.
/// </summary>
/// <param name="Title">The display title of the app.</param>
/// <param name="Description">A short description of the app.</param>
/// <param name="Endpoints">The available API endpoints.</param>
public record AppInfoResponse(
    string Title,
    string Description,
    List<string> Endpoints);
