using System.Text.RegularExpressions;

namespace ComprehensiveAppDemo.Services;

/// <summary>
/// Provides simple HTML sanitisation for trusted preview output.
/// </summary>
public partial class HtmlSanitizerService
{
    /// <summary>
    /// Removes dangerous tags and attributes from user-provided HTML.
    /// </summary>
    /// <param name="html">The HTML string to sanitise.</param>
    /// <returns>Sanitised HTML content.</returns>
    public string Sanitize(string html)
    {
        string sanitized = ScriptTagRegex().Replace(html, string.Empty);
        sanitized = EventHandlerRegex().Replace(sanitized, string.Empty);
        sanitized = JavascriptProtocolRegex().Replace(sanitized, string.Empty);

        return sanitized;
    }

    [GeneratedRegex(@"<script[\s\S]*?</script>", RegexOptions.IgnoreCase)]
    private static partial Regex ScriptTagRegex();

    [GeneratedRegex(@"\son\w+\s*=\s*(['""]).*?\1", RegexOptions.IgnoreCase)]
    private static partial Regex EventHandlerRegex();

    [GeneratedRegex(@"javascript\s*:", RegexOptions.IgnoreCase)]
    private static partial Regex JavascriptProtocolRegex();
}
