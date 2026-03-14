using ScaffoldingCRUD.Data;
using ScaffoldingCRUD.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;

namespace ScaffoldingCRUD.Pages.Products;

/// <summary>
/// Creates a new product record.
/// </summary>
public class CreateModel : PageModel
{
    private readonly AppDbContext _context;

    /// <summary>
    /// Initializes a new instance of the <see cref="CreateModel"/> class.
    /// </summary>
    /// <param name="context">Application database context.</param>
    public CreateModel(AppDbContext context)
    {
        _context = context;
    }

    /// <summary>
    /// Gets or sets the product being created.
    /// </summary>
    [BindProperty]
    public Product Product { get; set; } = new();

    /// <summary>
    /// Gets category select options for the form.
    /// </summary>
    public List<SelectListItem> CategoryOptions { get; private set; } = new();

    /// <summary>
    /// Handles GET requests for the create page.
    /// </summary>
    public async Task OnGetAsync()
    {
        await LoadCategoryOptionsAsync();
    }

    /// <summary>
    /// Handles POST requests to create a product.
    /// </summary>
    /// <returns>An action result that redirects on success or redisplays the form on failure.</returns>
    public async Task<IActionResult> OnPostAsync()
    {
        if (!ModelState.IsValid)
        {
            await LoadCategoryOptionsAsync();
            return Page();
        }

        _context.Products.Add(Product);
        await _context.SaveChangesAsync();

        return RedirectToPage("Index");
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
