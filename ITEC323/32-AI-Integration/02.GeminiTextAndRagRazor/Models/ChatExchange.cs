namespace GeminiTextAndRagRazorDemo.Models;

/// <summary>
/// Represents one user question and grounded answer in the conversation.
/// </summary>
public class ChatExchange
{
    /// <summary>
    /// Gets or sets the user's question.
    /// </summary>
    public required string Question { get; set; }

    /// <summary>
    /// Gets or sets the grounded answer returned by Gemini.
    /// </summary>
    public required string Answer { get; set; }

    /// <summary>
    /// Gets or sets the sources used to answer the question.
    /// </summary>
    public required List<RagSourceItem> Sources { get; set; }
}
