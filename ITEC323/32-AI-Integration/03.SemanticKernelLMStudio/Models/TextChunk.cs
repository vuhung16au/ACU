namespace SemanticKernelLMStudio.Models;

/// <summary>
/// Represents a text chunk stored in the in-memory knowledge index.
/// </summary>
public class TextChunk
{
    /// <summary>
    /// Gets or sets the unique identifier for the chunk.
    /// </summary>
    public string Id { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the chunk text.
    /// </summary>
    public string Text { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the embedding vector for the chunk.
    /// </summary>
    public ReadOnlyMemory<float> Embedding { get; set; }
}
