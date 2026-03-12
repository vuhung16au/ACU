using ComprehensiveAppDemo.Models;
using ComprehensiveAppDemo.Services;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ComprehensiveAppDemo.Pages;

/// <summary>
/// Displays the user directory.
/// </summary>
public class UsersModel : PageModel
{
    private readonly CsvUserService _csvUserService;

    /// <summary>
    /// Initializes a new instance of the <see cref="UsersModel"/> class.
    /// </summary>
    /// <param name="csvUserService">Provides CSV-backed user data.</param>
    public UsersModel(CsvUserService csvUserService)
    {
        _csvUserService = csvUserService;
    }

    /// <summary>
    /// Gets the users loaded from the CSV file.
    /// </summary>
    public List<User> Users { get; private set; } = [];

    /// <summary>
    /// Gets the error message shown when file access fails.
    /// </summary>
    public string? ErrorMessage { get; private set; }

    /// <summary>
    /// Handles GET requests for the users page.
    /// </summary>
    public async Task OnGetAsync()
    {
        try
        {
            Users = await _csvUserService.GetUsersAsync();
        }
        catch (FileNotFoundException)
        {
            ErrorMessage = "The user data file could not be found. Check that Data/users.csv exists.";
        }
        catch (IOException)
        {
            ErrorMessage = "The user data file is unavailable right now. Try again after closing any app using the file.";
        }
    }
}
