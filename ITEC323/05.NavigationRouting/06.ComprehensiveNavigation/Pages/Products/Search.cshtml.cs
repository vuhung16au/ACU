using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using ComprehensiveNavigation.Models;
using ComprehensiveNavigation.Services;

namespace ComprehensiveNavigation.Pages.Products;

public class SearchModel : PageModel
{
    private readonly IProductService _productService;

    public IEnumerable<Product> Products { get; set; } = new List<Product>();
    
    [BindProperty(SupportsGet = true)]
    public string? SearchTerm { get; set; }

    // Sometimes search parameters come as 'q' for simpler URLs
    [BindProperty(SupportsGet = true, Name = "q")]
    public string? Q { get; set; }

    public string SearchQuery => SearchTerm ?? Q ?? string.Empty;

    public SearchModel(IProductService productService)
    {
        _productService = productService;
    }

    public void OnGet()
    {
        if (!string.IsNullOrWhiteSpace(SearchQuery))
        {
            Products = _productService.Search(SearchQuery);
        }
    }

    // Capture the POST from navbar, and redirect to GET for friendly URL and refresh safety
    public IActionResult OnPost(string searchTerm)
    {
        if (string.IsNullOrWhiteSpace(searchTerm))
        {
            return RedirectToPage("/Products/Index");
        }
        
        return RedirectToPage("/Products/Search", new { q = searchTerm });
    }
}
