using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using RazorPagesUnitTestingDemo.Models;
using RazorPagesUnitTestingDemo.Services;

namespace RazorPagesUnitTestingDemo.Pages;

/// <summary>
/// Displays and processes the sample form submission page.
/// </summary>
public class IndexModel : PageModel
{
    private readonly FormSubmissionValidator _validator;
    private readonly FormSubmissionService _submissionService;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="validator">The validator used to check the input.</param>
    /// <param name="submissionService">The service used to prepare submitted values.</param>
    public IndexModel(FormSubmissionValidator validator, FormSubmissionService submissionService)
    {
        _validator = validator;
        _submissionService = submissionService;
    }

    /// <summary>
    /// Gets or sets the current form input.
    /// </summary>
    [BindProperty]
    public FormSubmissionInput Submission { get; set; } = new();

    /// <summary>
    /// Gets the list of validation errors shown on the page.
    /// </summary>
    public List<string> Errors { get; private set; } = new();

    /// <summary>
    /// Gets the processed submission result displayed after a successful post.
    /// </summary>
    public SubmissionResult Result { get; private set; } = new();

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
    /// <returns>The current page with validation messages or submitted values.</returns>
    public IActionResult OnPost()
    {
        var validationResult = _validator.Validate(Submission);
        if (!validationResult.IsValid)
        {
            Errors = validationResult.Errors;
            return Page();
        }

        Result = _submissionService.Process(Submission);
        HasSubmission = true;

        return Page();
    }
}
