namespace NPlusOne.Models;

/// <summary>
/// Represents a customer order.
/// </summary>
public class Order
{
    /// <summary>
    /// Gets or sets the order identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the customer name.
    /// </summary>
    public string CustomerName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the order date in UTC.
    /// </summary>
    public DateTime OrderedAtUtc { get; set; }

    /// <summary>
    /// Gets or sets the order items.
    /// </summary>
    public ICollection<OrderItem> Items { get; set; } = new List<OrderItem>();
}
