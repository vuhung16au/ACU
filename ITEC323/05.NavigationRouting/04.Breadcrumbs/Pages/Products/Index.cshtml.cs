using Breadcrumbs.Models;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Breadcrumbs.Pages.Products;

/// <summary>
/// Displays the full product list — breadcrumb trail: Home &gt; Products.
/// </summary>
public class IndexModel : PageModel
{
    /// <summary>Gets all products for display.</summary>
    public IReadOnlyList<Product> Products { get; private set; } = Array.Empty<Product>();

    /// <summary>Handles GET requests and sets a two-level manual breadcrumb.</summary>
    public void OnGet()
    {
        Products = DemoData.GetProducts();

        ViewData["Breadcrumbs"] = new List<BreadcrumbItem>
        {
            new() { Text = "Home", Url = "/" },
            new() { Text = "Products", IsActive = true }
        };
    }
}