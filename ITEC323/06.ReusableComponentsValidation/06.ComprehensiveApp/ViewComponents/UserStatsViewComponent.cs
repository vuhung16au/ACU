using ComprehensiveAppDemo.Models;
using ComprehensiveAppDemo.Services;
using Microsoft.AspNetCore.Mvc;

namespace ComprehensiveAppDemo.ViewComponents;

/// <summary>
/// Displays summary statistics for users stored in the CSV file.
/// </summary>
public class UserStatsViewComponent : ViewComponent
{
    private readonly CsvUserService _csvUserService;

    /// <summary>
    /// Initializes a new instance of the <see cref="UserStatsViewComponent"/> class.
    /// </summary>
    /// <param name="csvUserService">Provides CSV-backed user data.</param>
    public UserStatsViewComponent(CsvUserService csvUserService)
    {
        _csvUserService = csvUserService;
    }

    /// <summary>
    /// Builds the user summary before rendering the component.
    /// </summary>
    /// <returns>The rendered view component.</returns>
    public async Task<IViewComponentResult> InvokeAsync()
    {
        List<User> users = await _csvUserService.GetUsersAsync();
        UserStatsSummary summary = new()
        {
            TotalUsers = users.Count,
            AdultUsers = users.Count(user => user.Age >= 18),
            NewestUserName = users.OrderByDescending(user => user.Id).FirstOrDefault()?.Name ?? "No users yet"
        };

        return View(summary);
    }
}
