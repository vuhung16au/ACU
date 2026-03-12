using System;

namespace ComprehensiveNavigation.Models;

/// <summary>
/// Represents a user's past order.
/// </summary>
public class Order
{
    public int Id { get; set; }
    public DateTime Date { get; set; }
    public decimal Total { get; set; }
    public string Status { get; set; } = string.Empty;
}
