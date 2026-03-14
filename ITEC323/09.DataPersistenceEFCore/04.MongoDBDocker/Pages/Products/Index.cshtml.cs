using MongoDBDocker.Data;
using MongoDBDocker.Models;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace MongoDBDocker.Pages.Products;

/// <summary>
/// Displays the product list.
/// </summary>
public class IndexModel : PageModel
{
    private readonly IProductRepository _repository;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="repository">Product repository.</param>
    public IndexModel(IProductRepository repository)
    {
        _repository = repository;
    }

    /// <summary>
    /// Gets products shown in the table.
    /// </summary>
    public IList<Product> Products { get; private set; } = new List<Product>();

    /// <summary>
    /// Handles GET requests for the product list page.
    /// </summary>
    /// <returns>A task that represents the asynchronous operation.</returns>
    public async Task OnGetAsync()
    {
        Products = await _repository.GetAllAsync();
    }
}
