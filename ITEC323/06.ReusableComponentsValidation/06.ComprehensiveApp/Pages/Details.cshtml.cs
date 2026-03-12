using ComprehensiveAppDemo.Models;
using ComprehensiveAppDemo.Services;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ComprehensiveAppDemo.Pages;

/// <summary>
/// Displays details for one user.
/// </summary>
public class DetailsModel : PageModel
{
    private readonly CsvUserService _csvUserService;
    private readonly HtmlSanitizerService _htmlSanitizerService;

    /// <summary>
    /// Initializes a new instance of the <see cref="DetailsModel"/> class.
    /// </summary>
    /// <param name="csvUserService">Provides CSV-backed user data.</param>
    /// <param name="htmlSanitizerService">Provides HTML sanitisation.</param>
    public DetailsModel(CsvUserService csvUserService, HtmlSanitizerService htmlSanitizerService)
    {
        _csvUserService = csvUserService;
        _htmlSanitizerService = htmlSanitizerService;
    }

    /// <summary>
    /// Gets the selected user.
    /// </summary>
    public User? SelectedUser { get; private set; }

    /// <summary>
    /// Gets the sanitised bio preview.
    /// </summary>
    public string SanitizedBio { get; private set; } = string.Empty;

    /// <summary>
    /// Gets the error message shown when the user cannot be loaded.
    /// </summary>
    public string? ErrorMessage { get; private set; }

    /// <summary>
    /// Handles GET requests for the details page.
    /// </summary>
    public async Task OnGetAsync(int id)
    {
        try
        {
            SelectedUser = await _csvUserService.GetUserByIdAsync(id);

            if (SelectedUser is null)
            {
                ErrorMessage = "The selected user could not be found.";
                return;
            }

            SanitizedBio = _htmlSanitizerService.Sanitize(SelectedUser.Bio);
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
