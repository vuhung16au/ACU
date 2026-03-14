using ComprehensiveApp.Data;
using ComprehensiveApp.Models;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace ComprehensiveApp.Pages.Orders;

/// <summary>
/// Displays the order list.
/// </summary>
public class IndexModel : PageModel
{
    private readonly AppDbContext _context;

    public IndexModel(AppDbContext context)
    {
        _context = context;
    }

    public IList<Order> Orders { get; private set; } = new List<Order>();

    public async Task OnGetAsync()
    {
        Orders = await _context.Orders
            .AsNoTracking()
            .Include(order => order.Customer)
            .Include(order => order.OrderItems)
                .ThenInclude(orderItem => orderItem.Product)
            .OrderByDescending(order => order.OrderDateUtc)
            .ToListAsync();
    }
}