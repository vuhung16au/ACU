using NavigationMenus.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace NavigationMenus.Pages.Products;

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
    /// Handles GET requests for the edit route example.
    /// </summary>
    /// <param name="id">An optional product identifier from the URL.</param>
    /// <returns>The page result when the route is valid.</returns>
    public IActionResult OnGet(int? id)
    {
        Mode = "Edit";

        if (!id.HasValue)
        {
            return Page();
        }

        Product = DemoData.FindProduct(id.Value);

        if (Product is null)
        {
            return NotFound();
        }

        return Page();
    }

    /// <summary>
    /// Handles GET requests for the Preview page handler.
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

        return Page();
    }
}