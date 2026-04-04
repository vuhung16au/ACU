namespace GeminiTextAndRagRazorDemo.Models;

/// <summary>
/// Represents the user input for the Razor chat form.
/// </summary>
public class ChatInput
{
    /// <summary>
    /// Gets or sets the question typed by the user.
    /// </summary>
    public string Question { get; set; } = string.Empty;
}
