using Breadcrumbs.Models;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Breadcrumbs.Pages.Blog;

/// <summary>
/// Displays archive posts for a selected year — breadcrumb: Home &gt; Blog &gt; Archive &gt; {Year}.
/// </summary>
public class ArchiveModel : PageModel
{
    /// <summary>Gets the selected archive year.</summary>
    public int Year { get; private set; }

    /// <summary>Gets the posts filtered to the selected year.</summary>
    public IReadOnlyList<BlogPost> Posts { get; private set; } = Array.Empty<BlogPost>();

    /// <summary>Handles GET requests and sets a four-level manual breadcrumb.</summary>
    /// <param name="year">Optional archive year from the route.</param>
    public void OnGet(int? year)
    {
        Year = year ?? 2026;

        Posts = DemoData
            .GetPosts()
            .Where(post => post.Year == Year)
            .ToList();

        ViewData["Breadcrumbs"] = new List<BreadcrumbItem>
        {
            new() { Text = "Home", Url = "/" },
            new() { Text = "Blog", Url = "/Blog" },
            new() { Text = "Archive", Url = "/Blog/Archive" },
            new() { Text = Year.ToString(), IsActive = true }
        };
    }
}
