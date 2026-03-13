using Microsoft.AspNetCore.Mvc.RazorPages;
using VanillaJsBasics.Models;

namespace VanillaJsBasics.Pages;

/// <summary>
/// Displays hands-on vanilla JavaScript exercises.
/// </summary>
public class PlaygroundModel : PageModel
{
    /// <summary>
    /// Gets the example cards rendered for DOM selection exercises.
    /// </summary>
    public IReadOnlyList<VanillaExampleCard> ExampleCards { get; } =
    [
        new("DOM Selection", "Use querySelector and querySelectorAll to target elements precisely."),
        new("Event Handling", "Use addEventListener instead of inline attributes or helper libraries."),
        new("Class Management", "Use classList.add/remove/toggle for predictable UI changes.")
    ];

    /// <summary>
    /// Handles GET requests for the playground page.
    /// </summary>
    public void OnGet()
    {
    }
}
