using Microsoft.AspNetCore.Mvc.RazorPages;
using VanillaJsBasics.Services;

namespace VanillaJsBasics.Pages;

/// <summary>
/// Displays the landing page for the vanilla JavaScript module.
/// </summary>
public class IndexModel : PageModel
{
    private readonly VanillaTipService _vanillaTipService;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="vanillaTipService">Provides short classroom tips for the home page.</param>
    public IndexModel(VanillaTipService vanillaTipService)
    {
        _vanillaTipService = vanillaTipService;
        Tips = Array.Empty<string>();
    }

    /// <summary>
    /// Gets the list of beginner-friendly tips displayed on the home page.
    /// </summary>
    public IReadOnlyList<string> Tips { get; private set; }

    /// <summary>
    /// Handles GET requests for the module home page.
    /// </summary>
    public void OnGet()
    {
        Tips = _vanillaTipService.GetTips();
    }
}
