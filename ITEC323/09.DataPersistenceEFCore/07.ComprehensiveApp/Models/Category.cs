using System.ComponentModel.DataAnnotations;

namespace ComprehensiveApp.Models;

/// <summary>
/// Represents a product category for related-data scaffolding examples.
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
    [Required]
    [MaxLength(80)]
    [Display(Name = "Category Name")]
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets products associated with this category.
    /// </summary>
    public List<Product> Products { get; set; } = new();
}
