using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc.RazorPages;
using ComprehensiveNavigation.Models;
using ComprehensiveNavigation.Services;

namespace ComprehensiveNavigation.Pages.Account;

public class OrdersModel : PageModel
{
    private readonly IOrderService _orderService;

    public IEnumerable<Order> Orders { get; set; } = new List<Order>();

    public OrdersModel(IOrderService orderService)
    {
        _orderService = orderService;
    }

    public void OnGet()
    {
        Orders = _orderService.GetUserOrders();
    }
}
