using BasicFormValidationDemo.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace BasicFormValidationDemo.Pages;

/// <summary>
/// Displays and processes the contact form.
/// </summary>
public class ContactModel : PageModel
{
    /// <summary>
    /// Gets or sets the contact message data.
    /// </summary>
    [BindProperty]
    public ContactMessage Contact { get; set; } = new();

    /// <summary>
    /// Gets the success message displayed after a valid submission.
    /// </summary>
    public string? SuccessMessage { get; private set; }

    /// <summary>
    /// Handles GET requests for the contact page.
    /// </summary>
    public void OnGet()
    {
    }

    /// <summary>
    /// Handles POST requests for the contact page.
    /// </summary>
    /// <returns>The current page with validation feedback or success output.</returns>
    public IActionResult OnPost()
    {
        if (!ModelState.IsValid)
        {
            return Page();
        }

        SuccessMessage = $"Thanks, {Contact.Name}. Your message was sent successfully.";
        ModelState.Clear();
        Contact = new ContactMessage();

        return Page();
    }
}
