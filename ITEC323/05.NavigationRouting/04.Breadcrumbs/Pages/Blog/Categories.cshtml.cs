using Breadcrumbs.Models;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Breadcrumbs.Pages.Blog;

/// <summary>
/// Lists blog categories — breadcrumb trail: Home &gt; Blog &gt; Categories.
/// </summary>
public class CategoriesModel : PageModel
{
    /// <summary>Handles GET requests and sets a three-level manual breadcrumb.</summary>
    public void OnGet()
    {
        ViewData["Breadcrumbs"] = new List<BreadcrumbItem>
        {
            new() { Text = "Home", Url = "/" },
            new() { Text = "Blog", Url = "/Blog" },
            new() { Text = "Categories", IsActive = true }
        };
    }
}
