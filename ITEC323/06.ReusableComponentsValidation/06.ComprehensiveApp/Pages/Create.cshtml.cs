using ComprehensiveAppDemo.Models;
using ComprehensiveAppDemo.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ComprehensiveAppDemo.Pages;

/// <summary>
/// Displays and processes the create user form.
/// </summary>
public class CreateModel : PageModel
{
    private readonly CsvUserService _csvUserService;

    /// <summary>
    /// Initializes a new instance of the <see cref="CreateModel"/> class.
    /// </summary>
    /// <param name="csvUserService">Provides CSV-backed user data.</param>
    public CreateModel(CsvUserService csvUserService)
    {
        _csvUserService = csvUserService;
    }

    /// <summary>
    /// Gets or sets the user entered on the form.
    /// </summary>
    [BindProperty]
    public User UserInput { get; set; } = new();

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
    public async Task<IActionResult> OnPostAsync()
    {
        if (!ModelState.IsValid)
        {
            return Page();
        }

        try
        {
            await _csvUserService.AddUserAsync(UserInput);
            TempData["SuccessMessage"] = $"User {UserInput.Name} was created successfully.";
            return RedirectToPage("/Users");
        }
        catch (FileNotFoundException)
        {
            ErrorMessage = "The user data file could not be found. Check that Data/users.csv exists.";
            return Page();
        }
        catch (IOException)
        {
            ErrorMessage = "The user data file is unavailable right now. Try again after closing any app using the file.";
            return Page();
        }
    }
}
