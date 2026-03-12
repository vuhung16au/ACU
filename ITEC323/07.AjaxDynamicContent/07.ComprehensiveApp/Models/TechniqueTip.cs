namespace ComprehensiveApp.Models;

/// <summary>
/// Represents one server-rendered technique tip.
/// </summary>
public class TechniqueTip
{
    /// <summary>
    /// Gets or sets the tip title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the tip description.
    /// </summary>
    public string Description { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the technique label.
    /// </summary>
    public string Technique { get; set; } = string.Empty;
}
