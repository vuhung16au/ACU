using Microsoft.AspNetCore.Mvc.RazorPages;
using PartialViews.Models;

namespace PartialViews.Pages;

/// <summary>
/// Displays the home page for the partial views example.
/// </summary>
public class IndexModel : PageModel
{
    /// <summary>
    /// Gets the products shown on the home page.
    /// </summary>
    public List<Product> FeaturedProducts { get; } =
    [
        new Product
        {
            Name = "Starter Keyboard",
            Description = "A reliable keyboard used to demonstrate a strongly-typed product card partial.",
            Price = 49.95m,
            Category = "Hardware"
        },
        new Product
        {
            Name = "Razor Pages Handbook",
            Description = "A beginner-friendly guide that explains how shared Razor markup reduces duplication.",
            Price = 24.50m,
            Category = "Books"
        },
        new Product
        {
            Name = "USB-C Dock",
            Description = "A clean example item for showing repeated cards across multiple pages.",
            Price = 89.00m,
            Category = "Accessories"
        }
    ];

    /// <summary>
    /// Handles GET requests for the home page.
    /// </summary>
    public void OnGet()
    {
    }
}
