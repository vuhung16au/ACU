using ComprehensiveApp.Data;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace ComprehensiveApp.Pages.Categories;

/// <summary>
/// Displays categories with product counts.
/// </summary>
public class IndexModel : PageModel
{
    private readonly AppDbContext _context;

    public IndexModel(AppDbContext context)
    {
        _context = context;
    }

    public IList<CategoryListItem> Categories { get; private set; } = new List<CategoryListItem>();

    public async Task OnGetAsync()
    {
        Categories = await _context.Categories
            .AsNoTracking()
            .OrderBy(category => category.Name)
            .Select(category => new CategoryListItem(
                category.Id,
                category.Name,
                category.Products.Count))
            .ToListAsync();
    }

    public record CategoryListItem(int Id, string Name, int ProductCount);
}