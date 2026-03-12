using Breadcrumbs.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Breadcrumbs.Pages.Products;

/// <summary>
/// Demonstrates an optional integer route parameter.
/// </summary>
public class EditModel : PageModel
{
    /// <summary>
    /// Gets the selected product when an ID is supplied.
    /// </summary>
    public Product? Product { get; private set; }

    /// <summary>
    /// Gets the current page mode (Edit or Preview).
    /// </summary>
    public string Mode { get; private set; } = "Edit";

    /// <summary>
    /// Handles GET requests and sets a four-level manual breadcrumb when a product is found.
    /// </summary>
    /// <param name="id">An optional product identifier from the URL.</param>
    /// <returns>The page result when the route is valid.</returns>
    public IActionResult OnGet(int? id)
    {
        Mode = "Edit";

        if (!id.HasValue)
        {
            ViewData["Breadcrumbs"] = new List<BreadcrumbItem>
            {
                new() { Text = "Home", Url = "/" },
                new() { Text = "Products", Url = "/Products" },
                new() { Text = "Edit", IsActive = true }
            };
            return Page();
        }

        Product = DemoData.FindProduct(id.Value);

        if (Product is null)
        {
            return NotFound();
        }

        ViewData["Breadcrumbs"] = new List<BreadcrumbItem>
        {
            new() { Text = "Home", Url = "/" },
            new() { Text = "Products", Url = "/Products" },
            new() { Text = Product.Name, Url = $"/Products/Details/{Product.Id}" },
            new() { Text = "Edit", IsActive = true }
        };

        return Page();
    }

    /// <summary>
    /// Handles GET requests for the Preview page handler (Preview mode breadcrumb).
    /// </summary>
    /// <param name="id">The product identifier from the route.</param>
    /// <returns>The page result when the route is valid.</returns>
    public IActionResult OnGetPreview(int id)
    {
        Mode = "Preview";
        Product = DemoData.FindProduct(id);

        if (Product is null)
        {
            return NotFound();
        }

        ViewData["Breadcrumbs"] = new List<BreadcrumbItem>
        {
            new() { Text = "Home", Url = "/" },
            new() { Text = "Products", Url = "/Products" },
            new() { Text = Product.Name, Url = $"/Products/Details/{Product.Id}" },
            new() { Text = "Preview", IsActive = true }
        };

        return Page();
    }
}