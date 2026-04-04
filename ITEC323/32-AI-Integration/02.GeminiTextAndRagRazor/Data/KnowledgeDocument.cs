namespace GeminiTextAndRagRazorDemo.Data;

/// <summary>
/// Represents one document in the local knowledge base used for retrieval.
/// </summary>
public class KnowledgeDocument
{
    /// <summary>
    /// Gets or sets the unique identifier for the document.
    /// </summary>
    public required string Id { get; set; }

    /// <summary>
    /// Gets or sets the short title shown to the user.
    /// </summary>
    public required string Title { get; set; }

    /// <summary>
    /// Gets or sets the broad category of the document.
    /// </summary>
    public required string Category { get; set; }

    /// <summary>
    /// Gets or sets the main text content used for retrieval and prompting.
    /// </summary>
    public required string Content { get; set; }

    /// <summary>
    /// Gets or sets the keywords that help simple retrieval find the document.
    /// </summary>
    public required List<string> Keywords { get; set; }
}
