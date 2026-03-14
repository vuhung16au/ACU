using ScaffoldingCRUD.Data;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace ScaffoldingCRUD.Pages;

/// <summary>
/// Displays the module home page and a quick EF Core database summary.
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
    /// Gets the number of products currently stored in the database.
    /// </summary>
    public int ProductCount { get; private set; }

    /// <summary>
    /// Handles GET requests for the home page.
    /// </summary>
    /// <returns>A task that represents the asynchronous operation.</returns>
    public async Task OnGetAsync()
    {
        ProductCount = await _context.Products.AsNoTracking().CountAsync();

    }
}
