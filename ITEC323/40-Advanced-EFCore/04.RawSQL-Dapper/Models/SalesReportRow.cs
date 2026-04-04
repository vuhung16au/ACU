namespace RawSQLDapper.Models;

/// <summary>
/// Represents a sales report row shown on the reports page.
/// </summary>
public class SalesReportRow
{
    /// <summary>
    /// Gets or sets the product name.
    /// </summary>
    public string ProductName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the category.
    /// </summary>
    public string CategoryName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the total units sold.
    /// </summary>
    public int UnitsSold { get; set; }

    /// <summary>
    /// Gets or sets the total revenue.
    /// </summary>
    public decimal Revenue { get; set; }
}
