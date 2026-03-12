namespace ViewComponentsDemo.Models;

/// <summary>
/// Represents an item stored in the sample shopping cart.
/// </summary>
public class CartItem
{
    /// <summary>
    /// Gets or sets the product name.
    /// </summary>
    public string ProductName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the number of units in the cart.
    /// </summary>
    public int Quantity { get; set; }

    /// <summary>
    /// Gets or sets the price for one unit.
    /// </summary>
    public decimal UnitPrice { get; set; }
}
