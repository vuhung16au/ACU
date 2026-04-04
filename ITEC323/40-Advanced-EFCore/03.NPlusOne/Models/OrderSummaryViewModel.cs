namespace NPlusOne.Models;

/// <summary>
/// Represents a lightweight order summary shown on the page.
/// </summary>
public class OrderSummaryViewModel
{
    /// <summary>
    /// Gets or sets the order identifier.
    /// </summary>
    public int OrderId { get; set; }

    /// <summary>
    /// Gets or sets the customer name.
    /// </summary>
    public string CustomerName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the order date.
    /// </summary>
    public DateTime OrderedAtUtc { get; set; }

    /// <summary>
    /// Gets or sets the item count.
    /// </summary>
    public int ItemCount { get; set; }

    /// <summary>
    /// Gets or sets the total value.
    /// </summary>
    public decimal TotalValue { get; set; }

    /// <summary>
    /// Gets or sets the product names shown in the summary.
    /// </summary>
    public IReadOnlyList<string> ProductNames { get; set; } = [];
}
