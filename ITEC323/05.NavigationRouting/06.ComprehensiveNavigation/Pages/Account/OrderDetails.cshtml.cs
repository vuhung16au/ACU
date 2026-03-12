using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using ComprehensiveNavigation.Models;
using ComprehensiveNavigation.Services;

namespace ComprehensiveNavigation.Pages.Account;

public class OrderDetailsModel : PageModel
{
    private readonly IOrderService _orderService;

    public Order? Order { get; set; }

    public OrderDetailsModel(IOrderService orderService)
    {
        _orderService = orderService;
    }

    public IActionResult OnGet(int id)
    {
        Order = _orderService.GetOrderById(id);
        if (Order == null)
        {
            return RedirectToPage("/Error"); // Or just show null in the view
        }
        return Page();
    }
}
