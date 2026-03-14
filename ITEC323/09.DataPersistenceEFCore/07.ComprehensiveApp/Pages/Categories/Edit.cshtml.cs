using ComprehensiveApp.Data;
using ComprehensiveApp.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace ComprehensiveApp.Pages.Categories;

/// <summary>
/// Edits an existing category.
/// </summary>
public class EditModel : PageModel
{
    private readonly AppDbContext _context;

    public EditModel(AppDbContext context)
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

        var category = await _context.Categories.FindAsync(id.Value);
        if (category == null)
        {
            return NotFound();
        }

        Category = category;
        return Page();
    }

    public async Task<IActionResult> OnPostAsync()
    {
        if (!ModelState.IsValid)
        {
            return Page();
        }

        var existingCategory = await _context.Categories.FindAsync(Category.Id);
        if (existingCategory == null)
        {
            return NotFound();
        }

        existingCategory.Name = Category.Name;

        try
        {
            await _context.SaveChangesAsync();
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!await _context.Categories.AnyAsync(category => category.Id == Category.Id))
            {
                return NotFound();
            }

            throw;
        }

        return RedirectToPage("Index");
    }
}