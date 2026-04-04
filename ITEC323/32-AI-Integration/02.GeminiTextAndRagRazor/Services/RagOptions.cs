namespace GeminiTextAndRagRazorDemo.Services;

/// <summary>
/// Stores configuration values used by the simple RAG workflow.
/// </summary>
public class RagOptions
{
    /// <summary>
    /// Gets the configuration section name for RAG options.
    /// </summary>
    public const string SectionName = "Rag";

    /// <summary>
    /// Gets or sets the maximum number of documents to include as context.
    /// </summary>
    public int MaxDocuments { get; set; } = 3;

    /// <summary>
    /// Gets or sets the maximum length of a returned source excerpt.
    /// </summary>
    public int MaxExcerptLength { get; set; } = 160;
}
