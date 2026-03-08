using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using System.Diagnostics;

// ============================================================================
// Error Page Model
// ============================================================================
//
// This page is displayed when an unhandled exception occurs.
// Configured in Program.cs with: app.UseExceptionHandler("/Error");
// ============================================================================

namespace RazorPagesHelloWorld.Pages
{
    /// <summary>
    /// Page model for the Error page.
    /// Displays information about errors that occur in the application.
    /// </summary>
    [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
    public class ErrorModel : PageModel
    {
        /// <summary>
        /// The request ID for tracking this error.
        /// Useful for logs and debugging.
        /// </summary>
        public string? RequestId { get; set; }

        /// <summary>
        /// Whether to show the request ID on the error page.
        /// </summary>
        public bool ShowRequestId => !string.IsNullOrEmpty(RequestId);

        /// <summary>
        /// Called when the error page is loaded.
        /// Sets the RequestId for tracking the error.
        /// </summary>
        public void OnGet()
        {
            // Get the current Activity ID, or use the HTTP TraceIdentifier as fallback
            RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier;
        }
    }
}
