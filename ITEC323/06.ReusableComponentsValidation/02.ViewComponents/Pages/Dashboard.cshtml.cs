using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ViewComponentsDemo.Pages;

/// <summary>
/// Displays a dashboard that renders multiple View Components for one user.
/// </summary>
public class DashboardModel : PageModel
{
    /// <summary>
    /// Gets the sample user identifier shown on the dashboard.
    /// </summary>
    public int UserId => 101;

    /// <summary>
    /// Handles GET requests for the dashboard page.
    /// </summary>
    public void OnGet()
    {
    }
}
