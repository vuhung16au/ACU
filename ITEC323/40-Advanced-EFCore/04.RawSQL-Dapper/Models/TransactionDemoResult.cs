namespace RawSQLDapper.Models;

/// <summary>
/// Represents the outcome of the shared transaction demo.
/// </summary>
public class TransactionDemoResult
{
    /// <summary>
    /// Gets or sets the updated product name.
    /// </summary>
    public string ProductName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the new stock quantity.
    /// </summary>
    public int NewQuantityInStock { get; set; }

    /// <summary>
    /// Gets or sets the audit message.
    /// </summary>
    public string AuditMessage { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the SQL used by Dapper.
    /// </summary>
    public string AuditInsertSql { get; set; } = string.Empty;
}
