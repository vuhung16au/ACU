using ComprehensiveApp.Data;
using ComprehensiveApp.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace ComprehensiveApp.Pages.Orders;

/// <summary>
/// Displays order details with related customer and product data.
/// </summary>
public class DetailsModel : PageModel
{
    private readonly AppDbContext _context;

    public DetailsModel(AppDbContext context)
    {
        _context = context;
    }

    public Order Order { get; private set; } = new();

    public async Task<IActionResult> OnGetAsync(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var order = await _context.Orders
            .AsNoTracking()
            .Include(existingOrder => existingOrder.Customer)
            .Include(existingOrder => existingOrder.OrderItems)
                .ThenInclude(orderItem => orderItem.Product)
            .FirstOrDefaultAsync(existingOrder => existingOrder.Id == id.Value);

        if (order == null)
        {
            return NotFound();
        }

        Order = order;
        return Page();
    }
}