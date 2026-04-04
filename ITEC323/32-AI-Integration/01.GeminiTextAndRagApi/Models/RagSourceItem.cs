namespace GeminiTextAndRagApiDemo.Models;

/// <summary>
/// Represents one source document returned with a RAG answer.
/// </summary>
public class RagSourceItem
{
    /// <summary>
    /// Gets or sets the source document identifier.
    /// </summary>
    public required string DocumentId { get; set; }

    /// <summary>
    /// Gets or sets the source document title.
    /// </summary>
    public required string Title { get; set; }

    /// <summary>
    /// Gets or sets the source document category.
    /// </summary>
    public required string Category { get; set; }

    /// <summary>
    /// Gets or sets the short excerpt shown to the user.
    /// </summary>
    public required string Excerpt { get; set; }
}
