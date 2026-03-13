using Microsoft.AspNetCore.Mvc.RazorPages;
using AlpineJsComponents.Services;

namespace AlpineJsComponents.Pages;

/// <summary>
/// Displays the landing page for the Alpine.js module.
/// </summary>
public class IndexModel : PageModel
{
    private readonly AlpineTipsService _alpineTipsService;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="alpineTipsService">Provides beginner-friendly Alpine tips.</param>
    public IndexModel(AlpineTipsService alpineTipsService)
    {
        _alpineTipsService = alpineTipsService;
        Tips = Array.Empty<string>();
    }

    /// <summary>
    /// Gets the tips displayed on the module landing page.
    /// </summary>
    public IReadOnlyList<string> Tips { get; private set; }

    /// <summary>
    /// Handles GET requests for the module landing page.
    /// </summary>
    public void OnGet()
    {
        Tips = _alpineTipsService.GetTips();
    }
}
