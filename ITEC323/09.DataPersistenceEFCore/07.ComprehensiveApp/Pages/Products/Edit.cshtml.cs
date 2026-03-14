using ComprehensiveApp.Data;
using ComprehensiveApp.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;

namespace ComprehensiveApp.Pages.Products;

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
    /// Gets category select options for the form.
    /// </summary>
    public List<SelectListItem> CategoryOptions { get; private set; } = new();

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
        await LoadCategoryOptionsAsync();
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
            await LoadCategoryOptionsAsync();
            return Page();
        }

        var existingProduct = await _context.Products.FindAsync(Product.Id);
        if (existingProduct == null)
        {
            return NotFound();
        }

        existingProduct.Name = Product.Name;
        existingProduct.Description = Product.Description;
        existingProduct.Price = Product.Price;
        existingProduct.StockQuantity = Product.StockQuantity;
        existingProduct.CategoryId = Product.CategoryId;

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

    private async Task LoadCategoryOptionsAsync()
    {
        CategoryOptions = await _context.Categories
            .AsNoTracking()
            .OrderBy(c => c.Name)
            .Select(c => new SelectListItem
            {
                Value = c.Id.ToString(),
                Text = c.Name
            })
            .ToListAsync();
    }
}
