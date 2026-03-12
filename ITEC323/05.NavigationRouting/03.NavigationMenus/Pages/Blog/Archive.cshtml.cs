using NavigationMenus.Models;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace NavigationMenus.Pages.Blog;

/// <summary>
/// Displays archive links for a selected year.
/// </summary>
public class ArchiveModel : PageModel
{
    /// <summary>
    /// Gets the selected archive year.
    /// </summary>
    public int Year { get; private set; }

    /// <summary>
    /// Gets the posts in the selected year.
    /// </summary>
    public IReadOnlyList<BlogPost> Posts { get; private set; } = Array.Empty<BlogPost>();

    /// <summary>
    /// Handles GET requests for archive pages.
    /// </summary>
    /// <param name="year">Optional archive year from the route.</param>
    public void OnGet(int? year)
    {
        Year = year ?? 2026;

        Posts = DemoData
            .GetPosts()
            .Where(post => post.Year == Year)
            .ToList();
    }
}
