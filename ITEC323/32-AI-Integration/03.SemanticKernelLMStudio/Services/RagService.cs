using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.ChatCompletion;
using Microsoft.SemanticKernel.Connectors.OpenAI;
using SemanticKernelLMStudio.Models;

namespace SemanticKernelLMStudio.Services;

/// <summary>
/// Provides a beginner-friendly Retrieval-Augmented Generation flow.
/// </summary>
public class RagService(Kernel kernel, LmStudioEmbeddingService embeddingService)
{
    private readonly List<TextChunk> _chunks = [];
    private readonly Lock _chunksLock = new();
    
    /// <summary>
    /// Indexes a collection of documents in memory.
    /// </summary>
    /// <param name="documents">The documents to embed and store.</param>
    /// <returns>A task that completes when indexing finishes.</returns>
    public async Task IndexDocumentsAsync(IEnumerable<string> documents)
    {
        ArgumentNullException.ThrowIfNull(documents);

        var indexedChunks = new List<TextChunk>();
        var chunkId = 0;

        foreach (var document in documents)
        {
            foreach (var sentence in SplitIntoChunks(document))
            {
                var embedding = await embeddingService.GenerateEmbeddingAsync(sentence);
                indexedChunks.Add(new TextChunk
                {
                    Id = chunkId.ToString(),
                    Text = sentence,
                    Embedding = embedding
                });

                chunkId++;
            }
        }

        lock (_chunksLock)
        {
            _chunks.Clear();
            _chunks.AddRange(indexedChunks);
        }
    }

    /// <summary>
    /// Answers a question by retrieving relevant context and sending a grounded prompt to the model.
    /// </summary>
    /// <param name="question">The user's question.</param>
    /// <param name="topK">The number of source chunks to retrieve.</param>
    /// <param name="maxTokens">The maximum number of tokens to generate.</param>
    /// <returns>A grounded answer with source chunks.</returns>
    public async Task<RagResponse> AskAsync(string question, int topK = 3, int maxTokens = 500)
    {
        var questionEmbedding = await embeddingService.GenerateEmbeddingAsync(question);
        List<(TextChunk Chunk, float Similarity)> rankedChunks;

        lock (_chunksLock)
        {
            rankedChunks = _chunks
                .Select(chunk => (Chunk: chunk, Similarity: CalculateCosineSimilarity(questionEmbedding.Span, chunk.Embedding.Span)))
                .OrderByDescending(result => result.Similarity)
                .Take(Math.Max(1, topK))
                .ToList();
        }

        if (rankedChunks.Count == 0)
        {
            return new RagResponse(
                "I could not find any indexed documents to answer that question yet.",
                []);
        }

        var context = string.Join(
            Environment.NewLine + Environment.NewLine,
            rankedChunks.Select(item => $"[Source {item.Chunk.Id}] {item.Chunk.Text}"));

        var history = new ChatHistory();
        history.AddSystemMessage(
            "You are a helpful assistant that answers questions using only the supplied context. " +
            "If the context is not enough, say that clearly. Begin the answer with 'Based on the documents:'.");
        history.AddUserMessage(
            $"""
            Context:
            {context}

            Question:
            {question}
            """);

        try
        {
            var chatCompletionService = kernel.GetRequiredService<IChatCompletionService>();
            var settings = new OpenAIPromptExecutionSettings
            {
                MaxTokens = maxTokens,
                Temperature = 0.2
            };

            var response = await chatCompletionService.GetChatMessageContentAsync(history, settings);
            var answer = response.Content ?? "(empty response)";

            var sources = rankedChunks
                .Select(item => new SourceDocument(item.Chunk.Id, item.Chunk.Text, item.Similarity))
                .ToList();

            return new RagResponse(answer, sources);
        }
        catch (Exception ex)
        {
            throw new InvalidOperationException(
                "Failed to generate a grounded response. Make sure LM Studio is running and both chat and embedding models are available.",
                ex);
        }
    }

    private static IEnumerable<string> SplitIntoChunks(string document)
    {
        return document
            .Split(['.', '!', '?'], StringSplitOptions.RemoveEmptyEntries | StringSplitOptions.TrimEntries)
            .Where(sentence => !string.IsNullOrWhiteSpace(sentence))
            .Select(sentence => $"{sentence}.");
    }

    private static float CalculateCosineSimilarity(ReadOnlySpan<float> left, ReadOnlySpan<float> right)
    {
        if (left.Length == 0 || right.Length == 0 || left.Length != right.Length)
        {
            return 0;
        }

        float dotProduct = 0;
        float leftMagnitude = 0;
        float rightMagnitude = 0;

        for (var index = 0; index < left.Length; index++)
        {
            dotProduct += left[index] * right[index];
            leftMagnitude += left[index] * left[index];
            rightMagnitude += right[index] * right[index];
        }

        if (leftMagnitude == 0 || rightMagnitude == 0)
        {
            return 0;
        }

        return dotProduct / (float)(Math.Sqrt(leftMagnitude) * Math.Sqrt(rightMagnitude));
    }
}
