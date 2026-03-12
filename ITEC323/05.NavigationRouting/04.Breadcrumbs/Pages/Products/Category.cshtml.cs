using Breadcrumbs.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Breadcrumbs.Pages.Products;

/// <summary>
/// Displays products filtered by category from dropdown navigation.
/// </summary>
public class CategoryModel : PageModel
{
    /// <summary>
    /// Gets the selected category name from the route.
    /// </summary>
    public string CategoryName { get; private set; } = string.Empty;

    /// <summary>
    /// Gets products in the selected category.
    /// </summary>
    public IReadOnlyList<Product> Products { get; private set; } = Array.Empty<Product>();

    /// <summary>
    /// Handles GET requests for category pages.
    /// </summary>
    /// <param name="name">Category name from the route.</param>
    /// <returns>The page result when category has matches; otherwise 404.</returns>
    public IActionResult OnGet(string name)
    {
        CategoryName = name;

        Products = DemoData
            .GetProducts()
            .Where(product => string.Equals(product.Category, name, StringComparison.OrdinalIgnoreCase))
            .ToList();

        if (Products.Count == 0)
        {
            return NotFound();
        }

        ViewData["Breadcrumbs"] = new List<BreadcrumbItem>
        {
            new() { Text = "Home", Url = "/" },
            new() { Text = "Products", Url = "/Products" },
            new() { Text = CategoryName, IsActive = true }
        };

        return Page();
    }
}
