using System.Text.RegularExpressions;
using AdvancedValidationDemo.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace AdvancedValidationDemo.Pages;

/// <summary>
/// Displays and processes the XSS prevention demo.
/// </summary>
public partial class SecurityModel : PageModel
{
    /// <summary>
    /// Gets or sets the rich text input data.
    /// </summary>
    [BindProperty]
    public RichTextInput Input { get; set; } = CreateStarterInput();

    /// <summary>
    /// Gets the original content used for safe output.
    /// </summary>
    public string OriginalContent { get; private set; } = string.Empty;

    /// <summary>
    /// Gets the unsafe preview content rendered with Html.Raw.
    /// </summary>
    public string UnsafePreview { get; private set; } = string.Empty;

    /// <summary>
    /// Gets the sanitized preview content rendered with Html.Raw.
    /// </summary>
    public string SanitizedPreview { get; private set; } = string.Empty;

    /// <summary>
    /// Gets a value indicating whether preview content is available.
    /// </summary>
    public bool HasPreview { get; private set; }

    /// <summary>
    /// Handles GET requests for the security page.
    /// </summary>
    public void OnGet()
    {
    }

    /// <summary>
    /// Handles POST requests for the security page.
    /// </summary>
    /// <returns>The current page with preview output.</returns>
    public IActionResult OnPost()
    {
        if (!ModelState.IsValid)
        {
            return Page();
        }

        OriginalContent = Input.Content;

        // This keeps the "unsafe" demo visible but neutralises script blocks so the classroom example does not execute them.
        UnsafePreview = ScriptTagRegex().Replace(Input.Content, string.Empty);
        SanitizedPreview = SanitizeHtml(Input.Content);
        HasPreview = true;

        return Page();
    }

    private static RichTextInput CreateStarterInput()
    {
        return new RichTextInput
        {
            Title = "Welcome Post",
            Content = "<strong>Hello class!</strong> Try adding <em>formatting</em> or an attack example like &lt;img src=x onerror=alert('XSS')&gt;."
        };
    }

    private static string SanitizeHtml(string html)
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
