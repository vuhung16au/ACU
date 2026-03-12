using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc.RazorPages;
using ComprehensiveNavigation.Models;
using ComprehensiveNavigation.Services;

namespace ComprehensiveNavigation.Pages.Products;

public class IndexModel : PageModel
{
    private readonly IProductService _productService;

    public IEnumerable<Product> Products { get; set; } = new List<Product>();

    public IndexModel(IProductService productService)
    {
        _productService = productService;
    }

    public void OnGet()
    {
        Products = _productService.GetAll();
    }
}
