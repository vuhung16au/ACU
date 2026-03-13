using LinqQueries.Data;
using LinqQueries.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace LinqQueries.Pages.Products;

/// <summary>
/// Updates an existing product record.
/// </summary>
public class EditModel : PageModel
{
    private readonly AppDbContext _context;

    /// <summary>
    /// Initializes a new instance of the <see cref="EditModel"/> class.
    /// </summary>
    /// <param name="context">Application database context.</param>
    public EditModel(AppDbContext context)
    {
        _context = context;
    }

    /// <summary>
    /// Gets or sets the product being edited.
    /// </summary>
    [BindProperty]
    public Product Product { get; set; } = new();

    /// <summary>
    /// Handles GET requests for the edit page.
    /// </summary>
    /// <param name="id">Product identifier.</param>
    /// <returns>An action result that loads the page or returns not found.</returns>
    public async Task<IActionResult> OnGetAsync(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var product = await _context.Products.FindAsync(id.Value);
        if (product == null)
        {
            return NotFound();
        }

        Product = product;
        return Page();
    }

    /// <summary>
    /// Handles POST requests to save product changes.
    /// </summary>
    /// <returns>An action result that redirects on success or redisplays the form on failure.</returns>
    public async Task<IActionResult> OnPostAsync()
    {
        if (!ModelState.IsValid)
        {
            return Page();
        }

        _context.Attach(Product).State = EntityState.Modified;

        try
        {
            await _context.SaveChangesAsync();
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!await ProductExistsAsync(Product.Id))
            {
                return NotFound();
            }

            throw;
        }

        return RedirectToPage("Index");
    }

    private Task<bool> ProductExistsAsync(int id)
    {
        return _context.Products.AnyAsync(e => e.Id == id);
    }
}
