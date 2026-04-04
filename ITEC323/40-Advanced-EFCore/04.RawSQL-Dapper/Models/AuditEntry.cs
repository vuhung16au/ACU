namespace RawSQLDapper.Models;

/// <summary>
/// Represents an audit entry written during the transaction demo.
/// </summary>
public class AuditEntry
{
    /// <summary>
    /// Gets or sets the audit entry identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the event description.
    /// </summary>
    public string EventName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the event details.
    /// </summary>
    public string Details { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the event timestamp.
    /// </summary>
    public DateTime CreatedAtUtc { get; set; }
}
