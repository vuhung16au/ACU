using MongoDBDocker.Data;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace MongoDBDocker.Pages;

/// <summary>
/// Displays the module home page and a quick MongoDB summary.
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
    /// Gets the number of products currently stored in the database.
    /// </summary>
    public int ProductCount { get; private set; }

    /// <summary>
    /// Handles GET requests for the home page.
    /// </summary>
    /// <returns>A task that represents the asynchronous operation.</returns>
    public async Task OnGetAsync()
    {
        ProductCount = (int)await _repository.CountAsync();
    }
}
