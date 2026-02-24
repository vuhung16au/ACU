using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Week4Lab2Assignment.Pages;

/// <summary>
/// Page model for the main page of the Week 4 Lab 2 assignment.
/// </summary>
public class IndexModel : PageModel
{
    /// <summary>
    /// Gets or sets the student's name entered in the form.
    /// </summary>
    [BindProperty]
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the student's favourite programming language selected in the form.
    /// </summary>
    [BindProperty]
    public string FavouriteLanguage { get; set; } = string.Empty;

    /// <summary>
    /// Gets the message that will be displayed on the page after the form is submitted.
    /// </summary>
    public string ResultMessage { get; private set; } = string.Empty;

    /// <summary>
    /// Handles GET requests to the page.
    /// </summary>
    public void OnGet()
    {
        ResultMessage = string.Empty;
    }

    /// <summary>
    /// Handles POST requests when the form is submitted.
    /// </summary>
    public void OnPost()
    {
        ResultMessage = UserPreferenceFormatter.FormatMessage(Name, FavouriteLanguage);
    }
}

