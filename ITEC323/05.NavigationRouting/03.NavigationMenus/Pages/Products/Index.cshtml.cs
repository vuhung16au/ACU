using NavigationMenus.Models;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace NavigationMenus.Pages.Products;

/// <summary>
/// Displays the list of products used by the routing examples.
/// </summary>
public class IndexModel : PageModel
{
    /// <summary>
    /// Gets the sample products.
    /// </summary>
    public IReadOnlyList<Product> Products { get; private set; } = Array.Empty<Product>();

    /// <summary>
    /// Handles GET requests for the product list page.
    /// </summary>
    public void OnGet()
    {
        Products = DemoData.GetProducts();
    }
}