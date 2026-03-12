namespace ComprehensiveApp.Models;

/// <summary>
/// Represents one reflection entry rendered into the activity feed.
/// </summary>
public class ReflectionEntry
{
    /// <summary>
    /// Gets or sets the reflection identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the selected technique label.
    /// </summary>
    public string Technique { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the reflection text.
    /// </summary>
    public string Message { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the creation time.
    /// </summary>
    public DateTime CreatedAt { get; set; }
}
