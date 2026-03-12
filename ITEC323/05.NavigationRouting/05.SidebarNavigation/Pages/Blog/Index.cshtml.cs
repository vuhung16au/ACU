using SidebarNavigation.Models;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace SidebarNavigation.Pages.Blog;

/// <summary>
/// Displays the list of sample blog posts.
/// </summary>
public class IndexModel : PageModel
{
    /// <summary>
    /// Gets the sample posts.
    /// </summary>
    public IReadOnlyList<BlogPost> Posts { get; private set; } = Array.Empty<BlogPost>();

    /// <summary>
    /// Handles GET requests for the blog list page.
    /// </summary>
    public void OnGet()
    {
        Posts = DemoData.GetPosts();
    }
}