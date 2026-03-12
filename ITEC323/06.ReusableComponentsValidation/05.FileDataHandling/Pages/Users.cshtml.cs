using FileDataHandlingDemo.Models;
using FileDataHandlingDemo.Services;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace FileDataHandlingDemo.Pages;

/// <summary>
/// Displays users loaded from the CSV file.
/// </summary>
public class UsersModel : PageModel
{
    private readonly CsvUserService _csvUserService;

    /// <summary>
    /// Initializes a new instance of the <see cref="UsersModel"/> class.
    /// </summary>
    /// <param name="csvUserService">Provides CSV file access.</param>
    public UsersModel(CsvUserService csvUserService)
    {
        _csvUserService = csvUserService;
    }

    /// <summary>
    /// Gets the users loaded from the CSV file.
    /// </summary>
    public List<UserRecord> Users { get; private set; } = [];

    /// <summary>
    /// Gets the file error message displayed to the user.
    /// </summary>
    public string? ErrorMessage { get; private set; }

    /// <summary>
    /// Handles GET requests for the users page.
    /// </summary>
    /// <returns>A task representing the load operation.</returns>
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
            ErrorMessage = "The user data file is currently unavailable. Close any app using the file and try again.";
        }
    }
}
