using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ComprehensiveApp.Pages;

/// <summary>
/// Landing page for the comprehensive comparison app.
/// </summary>
public class IndexModel : PageModel
{
    /// <summary>
    /// Handles GET requests for the comparison overview page.
    /// </summary>
    public void OnGet()
    {
        // This page currently loads only static comparison content.
        // Additional data or metrics can be added here in future iterations.
    }
}

