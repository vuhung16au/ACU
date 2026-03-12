using FileDataHandlingDemo.Models;
using FileDataHandlingDemo.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace FileDataHandlingDemo.Pages;

/// <summary>
/// Displays and processes the create user form.
/// </summary>
public class CreateModel : PageModel
{
    private readonly CsvUserService _csvUserService;

    /// <summary>
    /// Initializes a new instance of the <see cref="CreateModel"/> class.
    /// </summary>
    /// <param name="csvUserService">Provides CSV file access.</param>
    public CreateModel(CsvUserService csvUserService)
    {
        _csvUserService = csvUserService;
    }

    /// <summary>
    /// Gets or sets the user data entered on the form.
    /// </summary>
    [BindProperty]
    public UserRecord NewUser { get; set; } = new();

    /// <summary>
    /// Gets the file error message displayed to the user.
    /// </summary>
    public string? ErrorMessage { get; private set; }

    /// <summary>
    /// Handles GET requests for the create page.
    /// </summary>
    public void OnGet()
    {
    }

    /// <summary>
    /// Handles POST requests for the create page.
    /// </summary>
    /// <returns>A redirect when successful, otherwise the current page.</returns>
    public async Task<IActionResult> OnPostAsync()
    {
        if (!ModelState.IsValid)
        {
            return Page();
        }

        try
        {
            await _csvUserService.AddUserAsync(NewUser);
            TempData["SuccessMessage"] = $"User {NewUser.Name} was added to the CSV file.";
            return RedirectToPage("/Users");
        }
        catch (FileNotFoundException)
        {
            ErrorMessage = "The user data file could not be found. Check that Data/users.csv exists.";
            return Page();
        }
        catch (IOException)
        {
            ErrorMessage = "The user data file is currently unavailable. Close any app using the file and try again.";
            return Page();
        }
    }
}
