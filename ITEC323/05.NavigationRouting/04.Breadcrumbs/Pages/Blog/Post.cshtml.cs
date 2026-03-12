using Breadcrumbs.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Breadcrumbs.Pages.Blog;

/// <summary>
/// Displays a blog post — deepest breadcrumb trail: Home > Blog > {Year} > {Month} > {Title}.
/// </summary>
public class PostModel : PageModel
{
    /// <summary>Gets the blog post matched by the route values.</summary>
    public BlogPost? Post { get; private set; }

    /// <summary>
    /// Handles GET requests and sets a five-level manual breadcrumb using the post's date.
    /// </summary>
    /// <param name="year">The publication year from the route.</param>
    /// <param name="month">The publication month from the route.</param>
    /// <param name="slug">The post slug from the route.</param>
    public IActionResult OnGet(int year, int month, string slug)
    {
        Post = DemoData.FindPost(year, month, slug);

        if (Post is null)
        {
            return NotFound();
        }

        var monthName = new DateTime(year, month, 1).ToString("MMMM");

        ViewData["Breadcrumbs"] = new List<BreadcrumbItem>
        {
            new() { Text = "Home",    Url = "/" },
            new() { Text = "Blog",    Url = "/Blog" },
            new() { Text = year.ToString(), Url = $"/Blog/Archive/{year}" },
            new() { Text = monthName },   // intermediate, no link
            new() { Text = Post.Title, IsActive = true }
        };

        return Page();
    }
}