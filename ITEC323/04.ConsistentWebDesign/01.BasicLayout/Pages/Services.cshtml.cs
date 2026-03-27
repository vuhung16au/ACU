using Microsoft.AspNetCore.Mvc.RazorPages;

namespace BasicLayout.Pages;

/// <summary>
/// Page model for the Services page.
///
/// This currently supports placeholder content and request logging.
/// </summary>
public class ServicesModel : PageModel
{
    private readonly ILogger<ServicesModel> _logger;

    public ServicesModel(ILogger<ServicesModel> logger)
    {
        _logger = logger;
    }

    public void OnGet()
    {
        _logger.LogInformation("Services page visited at {Time}", DateTime.Now);
    }
}
