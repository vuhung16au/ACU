using ExceptionTestingDemo.Models;
using ExceptionTestingDemo.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ExceptionTestingDemo.Pages;

/// <summary>
/// Displays and processes the sample form submission page.
/// </summary>
public class IndexModel : PageModel
{
    private readonly FormSubmissionService _submissionService;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="submissionService">The service used to validate and prepare submitted values.</param>
    public IndexModel(FormSubmissionService submissionService)
    {
        _submissionService = submissionService;
    }

    /// <summary>
    /// Gets or sets the current form input.
    /// </summary>
    [BindProperty]
    public FormSubmissionInput Submission { get; set; } = new();

    /// <summary>
    /// Gets the processed submission result displayed after a successful post.
    /// </summary>
    public SubmissionResult Result { get; private set; } = new();

    /// <summary>
    /// Gets the error message shown when a failure path throws an exception.
    /// </summary>
    public string? ErrorMessage { get; private set; }

    /// <summary>
    /// Gets a value indicating whether a successful submission result should be shown.
    /// </summary>
    public bool HasSubmission { get; private set; }

    /// <summary>
    /// Handles GET requests for the page.
    /// </summary>
    public void OnGet()
    {
    }

    /// <summary>
    /// Handles POST requests for the form.
    /// </summary>
    /// <returns>The current page with an error message or submitted values.</returns>
    public async Task<IActionResult> OnPostAsync()
    {
        try
        {
            Result = await _submissionService.SubmitAsync(Submission);
            HasSubmission = true;
        }
        catch (Exception ex) when (ex is ArgumentException or FormatException or InvalidOperationException)
        {
            ErrorMessage = ex.Message;
        }

        return Page();
    }
}
