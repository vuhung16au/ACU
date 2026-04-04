using System.Net.Http.Json;
using System.Text.Json.Serialization;

namespace SemanticKernelLMStudio.Services;

/// <summary>
/// Calls the LM Studio embeddings endpoint directly to request float embeddings.
/// </summary>
public class LmStudioEmbeddingService(HttpClient httpClient, IConfiguration configuration)
{
    private readonly string _modelId = configuration["LMStudio:EmbeddingModelId"] ?? "text-embedding-model";

    /// <summary>
    /// Generates an embedding vector for the supplied text.
    /// </summary>
    /// <param name="text">The text to embed.</param>
    /// <returns>A float embedding vector.</returns>
    public async Task<ReadOnlyMemory<float>> GenerateEmbeddingAsync(string text)
    {
        var response = await httpClient.PostAsJsonAsync(
            "embeddings",
            new EmbeddingRequest(_modelId, text, "float"));

        response.EnsureSuccessStatusCode();

        var embeddingResponse = await response.Content.ReadFromJsonAsync<EmbeddingResponse>();
        var embedding = embeddingResponse?.Data?.FirstOrDefault()?.Embedding;

        if (embedding is null || embedding.Count == 0)
        {
            throw new InvalidOperationException("LM Studio returned an empty embedding response.");
        }

        return new ReadOnlyMemory<float>(embedding.ToArray());
    }

    private sealed record EmbeddingRequest(
        [property: JsonPropertyName("model")] string Model,
        [property: JsonPropertyName("input")] string Input,
        [property: JsonPropertyName("encoding_format")] string EncodingFormat);

    private sealed class EmbeddingResponse
    {
        [JsonPropertyName("data")]
        public List<EmbeddingItem>? Data { get; init; }
    }

    private sealed class EmbeddingItem
    {
        [JsonPropertyName("embedding")]
        public List<float>? Embedding { get; init; }
    }
}
