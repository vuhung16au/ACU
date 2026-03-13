using LinqQueries.Data;
using LinqQueries.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace LinqQueries.Pages.Products;

/// <summary>
/// Displays details for one product.
/// </summary>
public class DetailsModel : PageModel
{
    private readonly AppDbContext _context;

    /// <summary>
    /// Initializes a new instance of the <see cref="DetailsModel"/> class.
    /// </summary>
    /// <param name="context">Application database context.</param>
    public DetailsModel(AppDbContext context)
    {
        _context = context;
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
    public async Task<IActionResult> OnGetAsync(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var product = await _context.Products
            .AsNoTracking()
            .FirstOrDefaultAsync(m => m.Id == id.Value);

        if (product == null)
        {
            return NotFound();
        }

        Product = product;
        return Page();
    }
}
