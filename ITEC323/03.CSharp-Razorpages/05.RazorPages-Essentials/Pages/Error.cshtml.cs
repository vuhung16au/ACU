using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using System.Diagnostics;

namespace RazorPagesEssentials.Pages;

/// <summary>
/// Page model for displaying error information.
/// This page is shown when an unhandled exception occurs in the application.
/// </summary>
[ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
[IgnoreAntiforgeryToken]
public class ErrorModel : PageModel
{
    /// <summary>
    /// Gets or sets the request ID that can be used to track the error.
    /// </summary>
    public string? RequestId { get; set; }

    /// <summary>
    /// Determines whether to show the request ID on the error page.
    /// </summary>
    public bool ShowRequestId => !string.IsNullOrEmpty(RequestId);

    /// <summary>
    /// Handles GET requests to display the error page.
    /// Captures the current Activity ID or HTTP Context TraceIdentifier for debugging.
    /// </summary>
    public void OnGet()
    {
        RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier;
    }
}
