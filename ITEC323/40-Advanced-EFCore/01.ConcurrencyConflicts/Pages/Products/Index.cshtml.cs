using ConcurrencyConflicts.Data;
using ConcurrencyConflicts.Models;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace ConcurrencyConflicts.Pages.Products;

/// <summary>
/// Displays products available for editing.
/// </summary>
public class IndexModel : PageModel
{
    private readonly AppDbContext _context;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="context">The database context.</param>
    public IndexModel(AppDbContext context)
    {
        _context = context;
    }

    /// <summary>
    /// Gets the products shown on the page.
    /// </summary>
    public IReadOnlyList<Product> Products { get; private set; } = [];

    /// <summary>
    /// Handles the GET request.
    /// </summary>
    /// <returns>A task that represents the asynchronous operation.</returns>
    public async Task OnGetAsync()
    {
        Products = await _context.Products
            .AsNoTracking()
            .OrderBy(product => product.Name)
            .ToListAsync();
    }
}
