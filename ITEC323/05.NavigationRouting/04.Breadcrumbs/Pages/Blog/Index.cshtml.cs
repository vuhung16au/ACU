using Breadcrumbs.Models;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Breadcrumbs.Pages.Blog;

/// <summary>
/// Displays the blog post list — breadcrumb trail: Home &gt; Blog.
/// </summary>
public class IndexModel : PageModel
{
    /// <summary>Gets the sample posts.</summary>
    public IReadOnlyList<BlogPost> Posts { get; private set; } = Array.Empty<BlogPost>();

    /// <summary>Handles GET requests and sets a two-level manual breadcrumb.</summary>
    public void OnGet()
    {
        Posts = DemoData.GetPosts();

        ViewData["Breadcrumbs"] = new List<BreadcrumbItem>
        {
            new() { Text = "Home", Url = "/" },
            new() { Text = "Blog", IsActive = true }
        };
    }
}