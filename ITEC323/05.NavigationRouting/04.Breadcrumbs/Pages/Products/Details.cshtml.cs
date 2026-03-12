using Breadcrumbs.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Breadcrumbs.Pages.Products;

/// <summary>
/// Displays a single product — breadcrumb trail: Home &gt; Products &gt; {Product Name}.
/// </summary>
public class DetailsModel : PageModel
{
    /// <summary>Gets the product selected by the route parameter.</summary>
    public Product? Product { get; private set; }

    /// <summary>Gets the product identifier captured from the URL.</summary>
    public int ProductId { get; private set; }

    /// <summary>
    /// Handles GET requests and sets a three-level manual breadcrumb.
    /// </summary>
    /// <param name="id">The integer route value from the URL.</param>
    public IActionResult OnGet(int id)
    {
        Product = DemoData.FindProduct(id);

        if (Product is null)
        {
            return NotFound();
        }

        ProductId = id;

        ViewData["Breadcrumbs"] = new List<BreadcrumbItem>
        {
            new() { Text = "Home", Url = "/" },
            new() { Text = "Products", Url = "/Products" },
            new() { Text = Product.Name, IsActive = true }
        };

        return Page();
    }
}