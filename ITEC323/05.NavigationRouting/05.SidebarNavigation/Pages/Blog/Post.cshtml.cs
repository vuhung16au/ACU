using SidebarNavigation.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace SidebarNavigation.Pages.Blog;

/// <summary>
/// Displays a friendly blog URL using multiple route values.
/// </summary>
public class PostModel : PageModel
{
    /// <summary>
    /// Gets the blog post selected by the route values.
    /// </summary>
    public BlogPost? Post { get; private set; }

    /// <summary>
    /// Handles GET requests for the blog post page.
    /// </summary>
    /// <param name="year">The publication year from the route.</param>
    /// <param name="month">The publication month from the route.</param>
    /// <param name="slug">The post slug from the route.</param>
    /// <returns>The page result when the route matches a post; otherwise a 404 response.</returns>
    public IActionResult OnGet(int year, int month, string slug)
    {
        Post = DemoData.FindPost(year, month, slug);

        if (Post is null)
        {
            return NotFound();
        }

        return Page();
    }
}