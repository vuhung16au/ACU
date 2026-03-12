using System;
using System.Collections.Generic;
using System.Linq;
using ComprehensiveNavigation.Models;

namespace ComprehensiveNavigation.Services;

/// <summary>
/// Service providing dummy order data.
/// </summary>
public interface IOrderService
{
    IEnumerable<Order> GetUserOrders();
    Order? GetOrderById(int id);
}

public class OrderService : IOrderService
{
    private readonly List<Order> _orders;

    public OrderService()
    {
        _orders = new List<Order>
        {
            new Order { Id = 1001, Date = DateTime.Now.AddDays(-10), Total = 2044.99m, Status = "Delivered" },
            new Order { Id = 1002, Date = DateTime.Now.AddDays(-2), Total = 45.00m, Status = "Shipped" },
            new Order { Id = 1003, Date = DateTime.Now.AddDays(-1), Total = 25.00m, Status = "Processing" }
        };
    }

    public IEnumerable<Order> GetUserOrders() => _orders.OrderByDescending(o => o.Date);

    public Order? GetOrderById(int id) => _orders.FirstOrDefault(o => o.Id == id);
}
