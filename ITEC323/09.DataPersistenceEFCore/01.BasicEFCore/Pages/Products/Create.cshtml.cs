using BasicEFCore.Data;
using BasicEFCore.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace BasicEFCore.Pages.Products;

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
    /// Handles GET requests for the create page.
    /// </summary>
    public void OnGet()
    {
    }

    /// <summary>
    /// Handles POST requests to create a product.
    /// </summary>
    /// <returns>An action result that redirects on success or redisplays the form on failure.</returns>
    public async Task<IActionResult> OnPostAsync()
    {
        if (!ModelState.IsValid)
        {
            return Page();
        }

        _context.Products.Add(Product);
        await _context.SaveChangesAsync();

        return RedirectToPage("Index");
    }
}
