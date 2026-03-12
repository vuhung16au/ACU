using ComprehensiveAppDemo.Models;
using ComprehensiveAppDemo.Services;
using Microsoft.AspNetCore.Mvc;

namespace ComprehensiveAppDemo.ViewComponents;

/// <summary>
/// Displays the most recent user activity entries.
/// </summary>
public class RecentActivityViewComponent : ViewComponent
{
    private readonly CsvUserService _csvUserService;

    /// <summary>
    /// Initializes a new instance of the <see cref="RecentActivityViewComponent"/> class.
    /// </summary>
    /// <param name="csvUserService">Provides CSV-backed user data.</param>
    public RecentActivityViewComponent(CsvUserService csvUserService)
    {
        _csvUserService = csvUserService;
    }

    /// <summary>
    /// Loads the recent activity entries before rendering the component.
    /// </summary>
    /// <returns>The rendered view component.</returns>
    public async Task<IViewComponentResult> InvokeAsync()
    {
        List<User> users = await _csvUserService.GetUsersAsync();
        List<User> recentUsers = users
            .OrderByDescending(user => user.Id)
            .Take(5)
            .ToList();

        return View(recentUsers);
    }
}
