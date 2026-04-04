namespace NPlusOne.Models;

/// <summary>
/// Represents a product category in the N+1 lesson.
/// </summary>
public class Category
{
    /// <summary>
    /// Gets or sets the category identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the category name.
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the products in the category.
    /// </summary>
    public ICollection<Product> Products { get; set; } = new List<Product>();
}
