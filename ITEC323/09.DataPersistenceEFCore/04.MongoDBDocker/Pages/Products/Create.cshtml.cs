using MongoDBDocker.Data;
using MongoDBDocker.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace MongoDBDocker.Pages.Products;

/// <summary>
/// Creates a new product record.
/// </summary>
public class CreateModel : PageModel
{
    private readonly IProductRepository _repository;

    /// <summary>
    /// Initializes a new instance of the <see cref="CreateModel"/> class.
    /// </summary>
    /// <param name="repository">Product repository.</param>
    public CreateModel(IProductRepository repository)
    {
        _repository = repository;
    }

    /// <summary>
    /// Gets or sets the product being created.
    /// </summary>
    [BindProperty]
    public Product Product { get; set; } = new();

    /// <summary>
    /// Handles GET requests for the create page.
    /// </summary>
    public void OnGet()
    {
    }

    /// <summary>
    /// Handles POST requests to create a product.
    /// </summary>
    /// <returns>An action result that redirects on success or redisplays the form on failure.</returns>
    public async Task<IActionResult> OnPostAsync()
    {
        if (!ModelState.IsValid)
        {
            return Page();
        }

        Product.CreatedAtUtc = DateTime.UtcNow;
        await _repository.CreateAsync(Product);

        return RedirectToPage("Index");
    }
}
