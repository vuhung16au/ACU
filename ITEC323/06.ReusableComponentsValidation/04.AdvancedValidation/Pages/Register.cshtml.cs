using AdvancedValidationDemo.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace AdvancedValidationDemo.Pages;

/// <summary>
/// Displays and processes the advanced registration form.
/// </summary>
public class RegisterModel : PageModel
{
    /// <summary>
    /// Gets or sets the registration form data.
    /// </summary>
    [BindProperty]
    public AdvancedUserRegistration Registration { get; set; } = new();

    /// <summary>
    /// Gets the success message shown after a valid submission.
    /// </summary>
    public string? SuccessMessage { get; private set; }

    /// <summary>
    /// Handles GET requests for the registration page.
    /// </summary>
    public void OnGet()
    {
    }

    /// <summary>
    /// Handles POST requests for the registration page.
    /// </summary>
    /// <returns>The current page with validation feedback or success output.</returns>
    public IActionResult OnPost()
    {
        if (!ModelState.IsValid)
        {
            return Page();
        }

        SuccessMessage = $"Registration complete for {Registration.Username}.";
        ModelState.Clear();
        Registration = new AdvancedUserRegistration();
        return Page();
    }
}
