using Microsoft.AspNetCore.Mvc.RazorPages;

namespace HtmxPartialUpdates.Pages;

/// <summary>
/// Landing page for the HTMX Partial Updates module.
/// Displays learning objectives and a quick-reference attribute list.
/// </summary>
public class IndexModel : PageModel
{
    /// <summary>Quick-reference entries shown on the home page card.</summary>
    public IReadOnlyList<(string Name, string Description)> HtmxAttributes { get; } = new[]
    {
        ("hx-get",       "Send a GET request and replace a target element with the response."),
        ("hx-post",      "Send a POST request (create, update, or delete)."),
        ("hx-target",    "CSS selector for the element that receives the response HTML."),
        ("hx-swap",      "How to insert the response: innerHTML, outerHTML, beforeend, etc."),
        ("hx-trigger",   "DOM event that fires the request — default is click or change."),
        ("hx-indicator", "CSS selector for a loading spinner shown while the request is in flight."),
        ("hx-confirm",   "Show a browser confirm dialog before sending the request."),
    };

    public void OnGet() { }
}
