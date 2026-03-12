using Microsoft.AspNetCore.Mvc.RazorPages;
using PartialViews.Models;

namespace PartialViews.Pages;

/// <summary>
/// Displays all sample products using a shared product card partial.
/// </summary>
public class ProductsModel : PageModel
{
    /// <summary>
    /// Gets the products shown on the products page.
    /// </summary>
    public List<Product> Products { get; } =
    [
        new Product
        {
            Name = "Wireless Mouse",
            Description = "A simple product used to demonstrate repeated HTML with different data.",
            Price = 29.90m,
            Category = "Hardware"
        },
        new Product
        {
            Name = "Laptop Stand",
            Description = "Shows how one partial can be reused on another page without copying markup.",
            Price = 39.00m,
            Category = "Accessories"
        },
        new Product
        {
            Name = "Study Planner",
            Description = "A printable planner example for beginner-friendly product data.",
            Price = 12.75m,
            Category = "Stationery"
        },
        new Product
        {
            Name = "Noise-Cancelling Headphones",
            Description = "Another example card to prove the partial handles multiple records cleanly.",
            Price = 129.00m,
            Category = "Audio"
        }
    ];

    /// <summary>
    /// Handles GET requests for the products page.
    /// </summary>
    public void OnGet()
    {
    }
}
