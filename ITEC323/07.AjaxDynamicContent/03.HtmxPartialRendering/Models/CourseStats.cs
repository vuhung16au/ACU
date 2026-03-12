namespace HtmxPartialRendering.Models;

/// <summary>
/// Represents the small statistics panel shown on the dashboard.
/// </summary>
public class CourseStats
{
    /// <summary>
    /// Gets or sets the number of fragment requests demonstrated.
    /// </summary>
    public int FragmentRequests { get; set; }

    /// <summary>
    /// Gets or sets the number of sections updated.
    /// </summary>
    public int UpdatedSections { get; set; }

    /// <summary>
    /// Gets or sets the short learning note shown to students.
    /// </summary>
    public string TeachingNote { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the last refresh time.
    /// </summary>
    public DateTime RefreshedAt { get; set; }
}
