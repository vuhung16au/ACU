namespace PerformanceOptimization.Models;

/// <summary>
/// Represents a product row shown in the optimized leaderboard.
/// </summary>
public class ProductLeaderboardRow
{
    /// <summary>
    /// Gets or sets the product identifier.
    /// </summary>
    public int ProductId { get; set; }

    /// <summary>
    /// Gets or sets the product name.
    /// </summary>
    public string ProductName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the category name.
    /// </summary>
    public string CategoryName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the product price.
    /// </summary>
    public decimal Price { get; set; }

    /// <summary>
    /// Gets or sets the quantity in stock.
    /// </summary>
    public int QuantityInStock { get; set; }

    /// <summary>
    /// Gets or sets the number of units sold in the selected window.
    /// </summary>
    public int UnitsSoldLast30Days { get; set; }

    /// <summary>
    /// Gets or sets the calculated revenue for the selected window.
    /// </summary>
    public decimal RevenueLast30Days { get; set; }
}
