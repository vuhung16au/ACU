namespace TagHelpers.Models;

/// <summary>
/// Represents a simple product used in the routing examples.
/// </summary>
public class Product
{
    /// <summary>
    /// Gets or sets the product identifier used in route examples.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the product name.
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the product category.
    /// </summary>
    public string Category { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the sample price.
    /// </summary>
    public decimal Price { get; set; }

    /// <summary>
    /// Gets or sets the short teaching summary shown on the page.
    /// </summary>
    public string Summary { get; set; } = string.Empty;
}