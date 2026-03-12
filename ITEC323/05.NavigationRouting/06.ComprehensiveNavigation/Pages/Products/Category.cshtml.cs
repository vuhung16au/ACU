using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using ComprehensiveNavigation.Models;
using ComprehensiveNavigation.Services;

namespace ComprehensiveNavigation.Pages.Products;

public class CategoryModel : PageModel
{
    private readonly IProductService _productService;

    public IEnumerable<Product> Products { get; set; } = new List<Product>();
    public string CategoryName { get; set; } = string.Empty;

    public CategoryModel(IProductService productService)
    {
        _productService = productService;
    }

    public void OnGet(string category)
    {
        CategoryName = category;
        Products = _productService.GetByCategory(category);
    }
}
