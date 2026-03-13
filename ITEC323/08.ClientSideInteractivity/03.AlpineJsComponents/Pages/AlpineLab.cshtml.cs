using Microsoft.AspNetCore.Mvc.RazorPages;
using AlpineJsComponents.Models;

namespace AlpineJsComponents.Pages;

/// <summary>
/// Displays the Alpine.js practice page with beginner components.
/// </summary>
public class AlpineLabModel : PageModel
{
    /// <summary>
    /// Gets the example cards shown at the top of the page.
    /// </summary>
    public IReadOnlyList<AlpineExampleCard> ExampleCards { get; } =
    [
        new("Reactive State", "x-data", "Create local component state directly in markup."),
        new("Visibility Control", "x-show / x-if", "Show, hide, or conditionally render UI blocks."),
        new("Events and Inputs", "x-on / x-model", "Bind click events and form values with minimal code.")
    ];

    /// <summary>
    /// Handles GET requests for the Alpine lab page.
    /// </summary>
    public void OnGet()
    {
    }
}
