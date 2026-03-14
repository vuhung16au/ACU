using ComprehensiveApp.Data;
using ComprehensiveApp.Models;
using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;

namespace ComprehensiveApp.Pages.Orders;

/// <summary>
/// Creates a new single-item order and updates stock levels.
/// </summary>
public class CreateModel : PageModel
{
    private readonly AppDbContext _context;

    public CreateModel(AppDbContext context)
    {
        _context = context;
    }

    [BindProperty]
    public OrderInputModel OrderInput { get; set; } = new();

    public List<SelectListItem> CustomerOptions { get; private set; } = new();

    public List<SelectListItem> ProductOptions { get; private set; } = new();

    public async Task OnGetAsync()
    {
        await LoadOptionsAsync();
    }

    public async Task<IActionResult> OnPostAsync()
    {
        await LoadOptionsAsync();

        if (!ModelState.IsValid)
        {
            return Page();
        }

        var product = await _context.Products.FindAsync(OrderInput.ProductId);
        var customer = await _context.Customers.FindAsync(OrderInput.CustomerId);

        if (product == null || customer == null)
        {
            ModelState.AddModelError(string.Empty, "Choose a valid customer and product.");
            return Page();
        }

        if (OrderInput.Quantity > product.StockQuantity)
        {
            ModelState.AddModelError(nameof(OrderInput.Quantity), "Quantity cannot exceed the available stock.");
            return Page();
        }

        var order = new Order
        {
            CustomerId = customer.Id,
            OrderDateUtc = DateTime.UtcNow,
            Status = "Pending",
            Notes = OrderInput.Notes,
            OrderItems =
            [
                new OrderItem
                {
                    ProductId = product.Id,
                    Quantity = OrderInput.Quantity,
                    UnitPrice = product.Price
                }
            ]
        };

        product.StockQuantity -= OrderInput.Quantity;
        _context.Orders.Add(order);
        await _context.SaveChangesAsync();

        return RedirectToPage("Details", new { id = order.Id });
    }

    private async Task LoadOptionsAsync()
    {
        CustomerOptions = await _context.Customers
            .AsNoTracking()
            .OrderBy(customer => customer.FullName)
            .Select(customer => new SelectListItem
            {
                Value = customer.Id.ToString(),
                Text = $"{customer.FullName} ({customer.City})"
            })
            .ToListAsync();

        ProductOptions = await _context.Products
            .AsNoTracking()
            .Where(product => product.StockQuantity > 0)
            .OrderBy(product => product.Name)
            .Select(product => new SelectListItem
            {
                Value = product.Id.ToString(),
                Text = $"{product.Name} - {product.Price:C} ({product.StockQuantity} in stock)"
            })
            .ToListAsync();
    }

    public class OrderInputModel
    {
        [BindProperty]
        [Display(Name = "Customer")]
        public int CustomerId { get; set; }

        [BindProperty]
        [Display(Name = "Product")]
        public int ProductId { get; set; }

        [BindProperty]
        [Range(1, 1000)]
        public int Quantity { get; set; } = 1;

        [BindProperty]
        [MaxLength(300)]
        public string? Notes { get; set; }
    }
}