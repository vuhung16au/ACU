using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using RazorPagesAdvanced.Data;
using RazorPagesAdvanced.Models;

namespace RazorPagesAdvanced.Pages;

/// <summary>
/// Page model for the Index page.
/// Loads all users from the database to display in a table.
/// </summary>
public class IndexModel : PageModel
{
    private readonly AppDbContext _context;

    /// <summary>
    /// Initializes a new instance of IndexModel with the database context.
    /// </summary>
    /// <param name="context">The database context for accessing users</param>
    public IndexModel(AppDbContext context)
    {
        _context = context;
    }

    /// <summary>
    /// Gets or sets the list of users loaded from the database.
    /// </summary>
    public List<User> Users { get; set; } = new();

    /// <summary>
    /// Handles GET requests to load users from the database.
    /// This method runs when the page is first loaded.
    /// </summary>
    public async Task OnGetAsync()
    {
        // Load all users from the database asynchronously
        Users = await _context.Users.ToListAsync();
    }
}
