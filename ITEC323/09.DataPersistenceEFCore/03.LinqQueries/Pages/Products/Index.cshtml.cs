using LinqQueries.Data;
using LinqQueries.Models;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace LinqQueries.Pages.Products;

/// <summary>
/// Displays the product list.
/// </summary>
public class IndexModel : PageModel
{
    private readonly AppDbContext _context;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="context">Application database context.</param>
    public IndexModel(AppDbContext context)
    {
        _context = context;
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
        Products = await _context.Products
            .AsNoTracking()
            .OrderBy(p => p.Name)
            .ToListAsync();
    }
}
