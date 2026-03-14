using ComprehensiveApp.Data;
using ComprehensiveApp.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace ComprehensiveApp.Pages.Categories;

/// <summary>
/// Deletes a category when it has no dependent products.
/// </summary>
public class DeleteModel : PageModel
{
    private readonly AppDbContext _context;

    public DeleteModel(AppDbContext context)
    {
        _context = context;
    }

    [BindProperty]
    public Category Category { get; set; } = new();

    public async Task<IActionResult> OnGetAsync(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var category = await _context.Categories
            .AsNoTracking()
            .Include(existingCategory => existingCategory.Products)
            .FirstOrDefaultAsync(existingCategory => existingCategory.Id == id.Value);

        if (category == null)
        {
            return NotFound();
        }

        Category = category;
        return Page();
    }

    public async Task<IActionResult> OnPostAsync(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var category = await _context.Categories
            .Include(existingCategory => existingCategory.Products)
            .FirstOrDefaultAsync(existingCategory => existingCategory.Id == id.Value);

        if (category == null)
        {
            return NotFound();
        }

        if (category.Products.Any())
        {
            ModelState.AddModelError(string.Empty, "Delete or reassign products before removing this category.");
            Category = category;
            return Page();
        }

        _context.Categories.Remove(category);
        await _context.SaveChangesAsync();
        return RedirectToPage("Index");
    }
}