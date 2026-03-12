using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using ComprehensiveNavigation.Models;
using ComprehensiveNavigation.Services;

namespace ComprehensiveNavigation.Pages.Products;

public class DetailsModel : PageModel
{
    private readonly IProductService _productService;

    public Product? Product { get; set; }

    public DetailsModel(IProductService productService)
    {
        _productService = productService;
    }

    public IActionResult OnGet(int id)
    {
        Product = _productService.GetById(id);
        
        // Let view handle null product for educational purposes, instead of 404
        return Page();
    }
}
