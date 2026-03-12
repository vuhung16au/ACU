namespace ViewComponentsDemo.Models;

/// <summary>
/// Represents the data shown by the shopping cart view component.
/// </summary>
public class ShoppingCartSummary
{
    /// <summary>
    /// Gets or sets the total number of items in the cart.
    /// </summary>
    public int TotalItems { get; set; }

    /// <summary>
    /// Gets or sets the total cart value.
    /// </summary>
    public decimal TotalCost { get; set; }

    /// <summary>
    /// Gets or sets the most expensive item in the cart.
    /// </summary>
    public string HighestValueItem { get; set; } = string.Empty;
}
