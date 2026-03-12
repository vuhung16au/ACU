using BasicFormValidationDemo.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace BasicFormValidationDemo.Pages;

/// <summary>
/// Displays and processes the registration form.
/// </summary>
public class RegisterModel : PageModel
{
    /// <summary>
    /// Gets or sets the user registration data.
    /// </summary>
    [BindProperty]
    public UserRegistration Registration { get; set; } = new();

    /// <summary>
    /// Gets the success message displayed after a valid submission.
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

        SuccessMessage = $"Registration complete for {Registration.FullName}.";
        ModelState.Clear();
        Registration = new UserRegistration();

        return Page();
    }
}
