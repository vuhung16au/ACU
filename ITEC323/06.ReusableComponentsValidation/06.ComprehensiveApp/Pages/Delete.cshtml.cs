using ComprehensiveAppDemo.Models;
using ComprehensiveAppDemo.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ComprehensiveAppDemo.Pages;

/// <summary>
/// Displays and processes the delete confirmation page.
/// </summary>
public class DeleteModel : PageModel
{
    private readonly CsvUserService _csvUserService;

    /// <summary>
    /// Initializes a new instance of the <see cref="DeleteModel"/> class.
    /// </summary>
    /// <param name="csvUserService">Provides CSV-backed user data.</param>
    public DeleteModel(CsvUserService csvUserService)
    {
        _csvUserService = csvUserService;
    }

    /// <summary>
    /// Gets or sets the user selected for deletion.
    /// </summary>
    [BindProperty]
    public User SelectedUser { get; set; } = new();

    /// <summary>
    /// Gets the error message displayed to the user.
    /// </summary>
    public string? ErrorMessage { get; private set; }

    /// <summary>
    /// Handles GET requests for the delete page.
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

            SelectedUser = user;
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
    /// Handles POST requests for the delete page.
    /// </summary>
    public async Task<IActionResult> OnPostAsync()
    {
        try
        {
            await _csvUserService.DeleteUserAsync(SelectedUser.Id);
            TempData["SuccessMessage"] = $"User {SelectedUser.Name} was deleted successfully.";
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
