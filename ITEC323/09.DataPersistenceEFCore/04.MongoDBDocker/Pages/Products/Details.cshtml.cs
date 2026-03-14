using MongoDBDocker.Data;
using MongoDBDocker.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace MongoDBDocker.Pages.Products;

/// <summary>
/// Displays details for one product.
/// </summary>
public class DetailsModel : PageModel
{
    private readonly IProductRepository _repository;

    /// <summary>
    /// Initializes a new instance of the <see cref="DetailsModel"/> class.
    /// </summary>
    /// <param name="repository">Product repository.</param>
    public DetailsModel(IProductRepository repository)
    {
        _repository = repository;
    }

    /// <summary>
    /// Gets the selected product.
    /// </summary>
    public Product Product { get; private set; } = new();

    /// <summary>
    /// Handles GET requests for the details page.
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
}
