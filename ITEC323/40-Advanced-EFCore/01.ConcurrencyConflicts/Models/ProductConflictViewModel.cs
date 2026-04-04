namespace ConcurrencyConflicts.Models;

/// <summary>
/// Holds attempted and database values after a concurrency conflict.
/// </summary>
public class ProductConflictViewModel
{
    /// <summary>
    /// Gets or sets the user-submitted values.
    /// </summary>
    public ProductInputModel AttemptedValues { get; set; } = new();

    /// <summary>
    /// Gets or sets the latest values stored in the database.
    /// </summary>
    public ProductInputModel DatabaseValues { get; set; } = new();
}
