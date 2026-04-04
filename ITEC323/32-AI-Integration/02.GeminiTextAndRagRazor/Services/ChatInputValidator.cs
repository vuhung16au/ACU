using GeminiTextAndRagRazorDemo.Models;

namespace GeminiTextAndRagRazorDemo.Services;

/// <summary>
/// Validates chat input before a question is sent to Gemini.
/// </summary>
public class ChatInputValidator
{
    /// <summary>
    /// Validates the supplied chat input.
    /// </summary>
    /// <param name="input">The chat input to validate.</param>
    /// <returns>A validation message when invalid; otherwise an empty string.</returns>
    public string Validate(ChatInput input)
    {
        var question = input.Question?.Trim() ?? string.Empty;

        if (string.IsNullOrWhiteSpace(question))
        {
            return "Please enter a question before asking Gemini.";
        }

        if (question.Length < 3)
        {
            return "Please enter a slightly longer question so the chat can find useful context.";
        }

        return string.Empty;
    }
}
