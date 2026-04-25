using Microsoft.AspNetCore.Mvc.RazorPages;

namespace AlpineJsComponents.Pages;

/// <summary>
/// Displays the ITEC Signup Form page demonstrating Alpine.js client-side validation.
/// </summary>
public class ITECSignupModel : PageModel
{
    /// <summary>
    /// Belt color options for the signup form.
    /// </summary>
    public IReadOnlyList<string> BeltColors { get; } =
    [
        "white", "yellow", "orange", "green", "blue", "purple", "brown", "red", "black"
    ];

    /// <summary>
    /// Handles GET requests for the ITEC Signup page.
    /// </summary>
    public void OnGet()
    {
    }
}
