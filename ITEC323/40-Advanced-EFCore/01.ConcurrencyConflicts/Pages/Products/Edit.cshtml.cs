using ConcurrencyConflicts.Data;
using ConcurrencyConflicts.Models;
using ConcurrencyConflicts.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace ConcurrencyConflicts.Pages.Products;

/// <summary>
/// Edits a product and resolves optimistic concurrency conflicts.
/// </summary>
public class EditModel : PageModel
{
    private readonly AppDbContext _context;

    /// <summary>
    /// Initializes a new instance of the <see cref="EditModel"/> class.
    /// </summary>
    /// <param name="context">The database context.</param>
    public EditModel(AppDbContext context)
    {
        _context = context;
    }

    /// <summary>
    /// Gets or sets the editable product values.
    /// </summary>
    [BindProperty]
    public ProductInputModel Input { get; set; } = new();

    /// <summary>
    /// Gets the conflict details when a concurrency exception occurs.
    /// </summary>
    public ProductConflictViewModel? Conflict { get; private set; }

    /// <summary>
    /// Gets the status message shown on the page.
    /// </summary>
    public string? StatusMessage { get; private set; }

    /// <summary>
    /// Loads the product for editing.
    /// </summary>
    /// <param name="id">The product identifier.</param>
    /// <returns>A task that represents the asynchronous operation.</returns>
    public async Task<IActionResult> OnGetAsync(int id)
    {
        var product = await _context.Products.AsNoTracking().FirstOrDefaultAsync(item => item.Id == id);
        if (product is null)
        {
            return NotFound();
        }

        Input = ConcurrencyConflictDemoService.ToInputModel(product);
        return Page();
    }

    /// <summary>
    /// Saves the edit request or resolves a detected conflict.
    /// </summary>
    /// <param name="action">The action selected by the user.</param>
    /// <returns>A task that represents the asynchronous operation.</returns>
    public async Task<IActionResult> OnPostAsync(string action = "save")
    {
        var existingProduct = await _context.Products.FirstOrDefaultAsync(item => item.Id == Input.Id);
        if (existingProduct is null)
        {
            return NotFound();
        }

        if (action == "refresh")
        {
            await ReloadFromDatabaseAsync(existingProduct, "The form was refreshed with the latest database values.");
            return Page();
        }

        if (!ModelState.IsValid)
        {
            return Page();
        }

        existingProduct.Name = Input.Name;
        existingProduct.Price = Input.Price;
        existingProduct.QuantityInStock = Input.QuantityInStock;
        existingProduct.Notes = Input.Notes;

        var originalVersion = action == "overwrite" ? Input.DatabaseVersion : Input.Version;
        _context.Entry(existingProduct).Property(product => product.Version).OriginalValue = originalVersion;

        try
        {
            await _context.SaveChangesAsync();
            return RedirectToPage("/Products/Index");
        }
        catch (DbUpdateConcurrencyException)
        {
            await LoadConflictAsync(existingProduct);
            return Page();
        }
    }

    private async Task ReloadFromDatabaseAsync(Product existingProduct, string message)
    {
        await _context.Entry(existingProduct).ReloadAsync();
        Input = ConcurrencyConflictDemoService.ToInputModel(existingProduct);
        StatusMessage = message;
        Conflict = null;
        ModelState.Clear();
    }

    private async Task LoadConflictAsync(Product attemptedProduct)
    {
        var databaseValues = await _context.Entry(attemptedProduct).GetDatabaseValuesAsync();
        if (databaseValues is null)
        {
            ModelState.AddModelError(string.Empty, "The record was deleted by another user.");
            return;
        }

        var databaseProduct = (Product)databaseValues.ToObject();
        Conflict = new ProductConflictViewModel
        {
            AttemptedValues = new ProductInputModel
            {
                Id = Input.Id,
                Name = Input.Name,
                Price = Input.Price,
                QuantityInStock = Input.QuantityInStock,
                Notes = Input.Notes,
                Version = Input.Version,
                DatabaseVersion = databaseProduct.Version
            },
            DatabaseValues = ConcurrencyConflictDemoService.ToInputModel(databaseProduct)
        };

        Input.DatabaseVersion = databaseProduct.Version;
        StatusMessage = "Review the database values before saving again.";
        ModelState.AddModelError(string.Empty, "Another user saved newer values for this product.");
        _context.Entry(attemptedProduct).State = EntityState.Detached;
    }
}
