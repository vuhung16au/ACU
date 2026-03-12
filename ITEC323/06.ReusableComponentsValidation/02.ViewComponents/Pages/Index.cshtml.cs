using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ViewComponentsDemo.Pages;

/// <summary>
/// Displays the home page for the View Components example.
/// </summary>
public class IndexModel : PageModel
{
    /// <summary>
    /// Gets the sample user identifier used by the page components.
    /// </summary>
    public int UserId => 101;

    /// <summary>
    /// Handles GET requests for the home page.
    /// </summary>
    public void OnGet()
    {
    }
}
