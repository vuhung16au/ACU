using ComprehensiveApp.Data;
using ComprehensiveApp.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace ComprehensiveApp.Pages.Categories;

/// <summary>
/// Displays category details.
/// </summary>
public class DetailsModel : PageModel
{
    private readonly AppDbContext _context;

    public DetailsModel(AppDbContext context)
    {
        _context = context;
    }

    public Category Category { get; private set; } = new();

    public async Task<IActionResult> OnGetAsync(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var category = await _context.Categories
            .AsNoTracking()
            .Include(existingCategory => existingCategory.Products.OrderBy(product => product.Name))
            .FirstOrDefaultAsync(existingCategory => existingCategory.Id == id.Value);

        if (category == null)
        {
            return NotFound();
        }

        Category = category;
        return Page();
    }
}