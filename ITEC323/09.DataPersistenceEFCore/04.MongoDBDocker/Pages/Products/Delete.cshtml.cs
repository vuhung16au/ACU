using MongoDBDocker.Data;
using MongoDBDocker.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace MongoDBDocker.Pages.Products;

/// <summary>
/// Deletes a product record after confirmation.
/// </summary>
public class DeleteModel : PageModel
{
    private readonly IProductRepository _repository;

    /// <summary>
    /// Initializes a new instance of the <see cref="DeleteModel"/> class.
    /// </summary>
    /// <param name="repository">Product repository.</param>
    public DeleteModel(IProductRepository repository)
    {
        _repository = repository;
    }

    /// <summary>
    /// Gets or sets the product being deleted.
    /// </summary>
    [BindProperty]
    public Product Product { get; set; } = new();

    /// <summary>
    /// Handles GET requests for the delete confirmation page.
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
    /// Handles POST requests to delete a product.
    /// </summary>
    /// <param name="id">Product identifier.</param>
    /// <returns>An action result that redirects to the list page.</returns>
    public async Task<IActionResult> OnPostAsync(string? id)
    {
        if (string.IsNullOrWhiteSpace(id))
        {
            return NotFound();
        }

        await _repository.DeleteAsync(id);

        return RedirectToPage("Index");
    }
}
