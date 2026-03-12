namespace PartialPageUpdates.Models;

/// <summary>
/// Represents one highlighted learning resource.
/// </summary>
public class ResourceSpotlight
{
    /// <summary>
    /// Gets or sets the resource title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets a short summary of the resource.
    /// </summary>
    public string Summary { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the recommended action students should take next.
    /// </summary>
    public string NextStep { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the resource type such as Video or Lab.
    /// </summary>
    public string ResourceType { get; set; } = string.Empty;
}
