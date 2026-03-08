using Microsoft.AspNetCore.Mvc.RazorPages;

namespace BasicLayout.Pages;

/// <summary>
/// Page model for the About page.
/// 
/// This is intentionally simple to focus on the layout concepts.
/// </summary>
public class AboutModel : PageModel
{
    private readonly ILogger<AboutModel> _logger;

    public AboutModel(ILogger<AboutModel> logger)
    {
        _logger = logger;
    }

    public void OnGet()
    {
        _logger.LogInformation("About page visited at {Time}", DateTime.Now);
    }
}
