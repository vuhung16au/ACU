using System.ComponentModel.DataAnnotations;

namespace ComprehensiveApp.Models;

/// <summary>
/// Represents a customer who places orders.
/// </summary>
public class Customer
{
    /// <summary>
    /// Gets or sets the customer identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the customer's full name.
    /// </summary>
    [Required]
    [MaxLength(120)]
    [Display(Name = "Customer Name")]
    public string FullName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the customer's email address.
    /// </summary>
    [Required]
    [EmailAddress]
    [MaxLength(160)]
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the customer's city.
    /// </summary>
    [MaxLength(80)]
    public string? City { get; set; }

    /// <summary>
    /// Gets or sets the UTC created timestamp.
    /// </summary>
    public DateTime CreatedAtUtc { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Gets or sets orders placed by the customer.
    /// </summary>
    public List<Order> Orders { get; set; } = new();
}