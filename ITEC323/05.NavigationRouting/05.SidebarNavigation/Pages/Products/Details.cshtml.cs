using SidebarNavigation.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace SidebarNavigation.Pages.Products;

/// <summary>
/// Displays a single product using a required integer route value.
/// </summary>
public class DetailsModel : PageModel
{
    /// <summary>
    /// Gets the product selected by the route parameter.
    /// </summary>
    public Product? Product { get; private set; }

    /// <summary>
    /// Gets the product identifier captured from the URL.
    /// </summary>
    public int ProductId { get; private set; }

    /// <summary>
    /// Handles GET requests for the details page.
    /// </summary>
    /// <param name="id">The integer route value from the URL.</param>
    /// <returns>The page result when the product exists; otherwise a 404 response.</returns>
    public IActionResult OnGet(int id)
    {
        Product = DemoData.FindProduct(id);

        if (Product is null)
        {
            return NotFound();
        }

        ProductId = id;
        return Page();
    }
}