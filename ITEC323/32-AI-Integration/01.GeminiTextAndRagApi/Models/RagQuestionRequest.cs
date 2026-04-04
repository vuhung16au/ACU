namespace GeminiTextAndRagApiDemo.Models;

/// <summary>
/// Represents a request for a grounded RAG answer.
/// </summary>
public class RagQuestionRequest
{
    /// <summary>
    /// Gets or sets the question that should be answered with retrieved context.
    /// </summary>
    public required string Question { get; set; }
}
