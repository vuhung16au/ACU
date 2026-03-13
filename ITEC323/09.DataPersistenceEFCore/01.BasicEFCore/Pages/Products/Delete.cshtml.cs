using BasicEFCore.Data;
using BasicEFCore.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace BasicEFCore.Pages.Products;

/// <summary>
/// Deletes a product record after confirmation.
/// </summary>
public class DeleteModel : PageModel
{
    private readonly AppDbContext _context;

    /// <summary>
    /// Initializes a new instance of the <see cref="DeleteModel"/> class.
    /// </summary>
    /// <param name="context">Application database context.</param>
    public DeleteModel(AppDbContext context)
    {
        _context = context;
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

    /// <summary>
    /// Handles POST requests to delete a product.
    /// </summary>
    /// <param name="id">Product identifier.</param>
    /// <returns>An action result that redirects to the list page.</returns>
    public async Task<IActionResult> OnPostAsync(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var product = await _context.Products.FindAsync(id.Value);
        if (product != null)
        {
            _context.Products.Remove(product);
            await _context.SaveChangesAsync();
        }

        return RedirectToPage("Index");
    }
}
