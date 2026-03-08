using Microsoft.AspNetCore.Mvc.RazorPages;

namespace BasicLayout.Pages;

/// <summary>
/// Page model for the Contact page.
/// 
/// In a real application, this would handle form submission.
/// For this demo, we're focusing on the layout system.
/// </summary>
public class ContactModel : PageModel
{
    private readonly ILogger<ContactModel> _logger;

    public ContactModel(ILogger<ContactModel> logger)
    {
        _logger = logger;
    }

    public void OnGet()
    {
        _logger.LogInformation("Contact page visited at {Time}", DateTime.Now);
    }

    /// <summary>
    /// Handles form submission (POST request).
    /// In a real app, you'd process the form data here.
    /// </summary>
    public void OnPost()
    {
        _logger.LogInformation("Contact form submitted at {Time}", DateTime.Now);
        // In a real app: validate input, send email, save to database, etc.
    }
}
