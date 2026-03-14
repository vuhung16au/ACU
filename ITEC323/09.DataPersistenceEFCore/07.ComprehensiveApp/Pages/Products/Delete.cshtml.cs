using ComprehensiveApp.Data;
using ComprehensiveApp.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace ComprehensiveApp.Pages.Products;

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
            .Include(p => p.Category)
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

        var product = await _context.Products
            .Include(existingProduct => existingProduct.OrderItems)
            .FirstOrDefaultAsync(existingProduct => existingProduct.Id == id.Value);
        if (product != null)
        {
            if (product.OrderItems.Any())
            {
                ModelState.AddModelError(string.Empty, "This product is referenced by existing orders and cannot be deleted.");
                Product = product;
                await _context.Entry(Product).Reference(existingProduct => existingProduct.Category!).LoadAsync();
                return Page();
            }

            _context.Products.Remove(product);
            await _context.SaveChangesAsync();
        }

        return RedirectToPage("Index");
    }
}
