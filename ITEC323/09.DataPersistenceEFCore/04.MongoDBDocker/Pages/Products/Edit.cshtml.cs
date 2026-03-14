using MongoDBDocker.Data;
using MongoDBDocker.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace MongoDBDocker.Pages.Products;

/// <summary>
/// Updates an existing product record.
/// </summary>
public class EditModel : PageModel
{
    private readonly IProductRepository _repository;

    /// <summary>
    /// Initializes a new instance of the <see cref="EditModel"/> class.
    /// </summary>
    /// <param name="repository">Product repository.</param>
    public EditModel(IProductRepository repository)
    {
        _repository = repository;
    }

    /// <summary>
    /// Gets or sets the product being edited.
    /// </summary>
    [BindProperty]
    public Product Product { get; set; } = new();

    /// <summary>
    /// Handles GET requests for the edit page.
    /// </summary>
    /// <param name="id">Product identifier.</param>
    /// <returns>An action result that loads the page or returns not found.</returns>
    public async Task<IActionResult> OnGetAsync(string? id)
    {
        if (string.IsNullOrWhiteSpace(id))
        {
            return NotFound();
        }

        var product = await _repository.GetByIdAsync(id);
        if (product == null)
        {
            return NotFound();
        }

        Product = product;
        return Page();
    }

    /// <summary>
    /// Handles POST requests to save product changes.
    /// </summary>
    /// <returns>An action result that redirects on success or redisplays the form on failure.</returns>
    public async Task<IActionResult> OnPostAsync()
    {
        if (!ModelState.IsValid)
        {
            return Page();
        }

        var existingProduct = await _repository.GetByIdAsync(Product.Id);
        if (existingProduct == null)
        {
            return NotFound();
        }

        existingProduct.Name = Product.Name;
        existingProduct.Description = Product.Description;
        existingProduct.Price = Product.Price;
        existingProduct.StockQuantity = Product.StockQuantity;

        await _repository.UpdateAsync(existingProduct);

        return RedirectToPage("Index");
    }
}
