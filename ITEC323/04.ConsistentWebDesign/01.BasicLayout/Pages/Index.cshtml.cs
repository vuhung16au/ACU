using Microsoft.AspNetCore.Mvc.RazorPages;

namespace BasicLayout.Pages;

/// <summary>
/// Page model for the home page (Index.cshtml).
/// 
/// In Razor Pages, the PageModel handles the logic (C# code),
/// while the .cshtml file handles the presentation (HTML).
/// </summary>
public class IndexModel : PageModel
{
    private readonly ILogger<IndexModel> _logger;

    public IndexModel(ILogger<IndexModel> logger)
    {
        _logger = logger;
    }

    /// <summary>
    /// Handles GET requests to the home page.
    /// This runs when a user navigates to the page.
    /// </summary>
    public void OnGet()
    {
        _logger.LogInformation("Home page visited at {Time}", DateTime.Now);
    }
}
