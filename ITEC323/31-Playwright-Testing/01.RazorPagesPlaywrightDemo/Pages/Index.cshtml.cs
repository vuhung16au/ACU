using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using RazorPagesPlaywrightDemo.Models;

namespace RazorPagesPlaywrightDemo.Pages;

/// <summary>
/// Displays the sample form and shows submitted values.
/// </summary>
public class IndexModel : PageModel
{
    /// <summary>
    /// Gets or sets the user's current form input.
    /// </summary>
    [BindProperty]
    public FormSubmission Submission { get; set; } = new();

    /// <summary>
    /// Gets the submitted values that are shown after the form is posted.
    /// </summary>
    public FormSubmission SubmittedForm { get; private set; } = new();

    /// <summary>
    /// Gets a value indicating whether the page should show the submitted values section.
    /// </summary>
    public bool HasSubmission { get; private set; }

    /// <summary>
    /// Handles GET requests for the page.
    /// </summary>
    public void OnGet()
    {
    }

    /// <summary>
    /// Handles POST requests for the form demo.
    /// </summary>
    /// <returns>The current page with the submitted values displayed.</returns>
    public IActionResult OnPost()
    {
        SubmittedForm = new FormSubmission
        {
            Name = Submission.Name,
            Email = Submission.Email,
            FavoriteLanguage = Submission.FavoriteLanguage
        };

        HasSubmission = true;

        return Page();
    }
}
