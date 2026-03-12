using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ViewComponentsDemo.Pages;

/// <summary>
/// Displays a second page that reuses View Components with different parameter values.
/// </summary>
public class ActivityModel : PageModel
{
    /// <summary>
    /// Gets the second sample user identifier.
    /// </summary>
    public int SecondUserId => 202;

    /// <summary>
    /// Handles GET requests for the activity page.
    /// </summary>
    public void OnGet()
    {
    }
}
