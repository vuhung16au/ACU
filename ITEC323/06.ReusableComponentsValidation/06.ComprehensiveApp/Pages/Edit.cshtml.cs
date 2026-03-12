using ComprehensiveAppDemo.Models;
using ComprehensiveAppDemo.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ComprehensiveAppDemo.Pages;

/// <summary>
/// Displays and processes the edit user form.
/// </summary>
public class EditModel : PageModel
{
    private readonly CsvUserService _csvUserService;

    /// <summary>
    /// Initializes a new instance of the <see cref="EditModel"/> class.
    /// </summary>
    /// <param name="csvUserService">Provides CSV-backed user data.</param>
    public EditModel(CsvUserService csvUserService)
    {
        _csvUserService = csvUserService;
    }

    /// <summary>
    /// Gets or sets the user being edited.
    /// </summary>
    [BindProperty]
    public User UserInput { get; set; } = new();

    /// <summary>
    /// Gets the error message displayed to the user.
    /// </summary>
    public string? ErrorMessage { get; private set; }

    /// <summary>
    /// Handles GET requests for the edit page.
    /// </summary>
    public async Task<IActionResult> OnGetAsync(int id)
    {
        try
        {
            User? user = await _csvUserService.GetUserByIdAsync(id);

            if (user is null)
            {
                TempData["ErrorMessage"] = "The selected user could not be found.";
                return RedirectToPage("/Users");
            }

            UserInput = user;
            return Page();
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

    /// <summary>
    /// Handles POST requests for the edit page.
    /// </summary>
    public async Task<IActionResult> OnPostAsync()
    {
        if (!ModelState.IsValid)
        {
            return Page();
        }

        try
        {
            await _csvUserService.UpdateUserAsync(UserInput);
            TempData["SuccessMessage"] = $"User {UserInput.Name} was updated successfully.";
            return RedirectToPage("/Details", new { id = UserInput.Id });
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
