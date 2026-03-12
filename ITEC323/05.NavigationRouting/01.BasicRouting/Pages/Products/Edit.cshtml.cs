using BasicRouting.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace BasicRouting.Pages.Products;

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
    /// Handles GET requests for the edit route example.
    /// </summary>
    /// <param name="id">An optional product identifier from the URL.</param>
    /// <returns>The page result when the route is valid.</returns>
    public IActionResult OnGet(int? id)
    {
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
}