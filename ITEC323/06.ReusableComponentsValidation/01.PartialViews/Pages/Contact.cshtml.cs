using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using PartialViews.Models;

namespace PartialViews.Pages;

/// <summary>
/// Displays a contact form that reuses a shared partial view for its inputs.
/// </summary>
public class ContactModel : PageModel
{
    /// <summary>
    /// Gets or sets the contact data bound from the form.
    /// </summary>
    [BindProperty]
    public ContactInfo Contact { get; set; } = new();

    /// <summary>
    /// Gets the submitted contact data shown after posting the form.
    /// </summary>
    public ContactInfo? SubmittedContact { get; private set; }

    /// <summary>
    /// Handles GET requests for the contact page.
    /// </summary>
    public void OnGet()
    {
        Contact = CreateStarterContact();
    }

    /// <summary>
    /// Handles POST requests for the contact page.
    /// </summary>
    /// <returns>The current page with the submitted example data.</returns>
    public IActionResult OnPost()
    {
        SubmittedContact = Contact;
        return Page();
    }

    private static ContactInfo CreateStarterContact()
    {
        return new ContactInfo
        {
            FullName = "Jordan Lee",
            Email = "jordan.lee@example.com",
            Topic = "Project Help",
            Message = "Could you explain when a partial view is a better fit than a full page?"
        };
    }
}
