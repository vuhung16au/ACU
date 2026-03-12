namespace NavigationMenus.Models;

/// <summary>
/// Represents a sample course code lookup entry.
/// </summary>
public class CourseReference
{
    /// <summary>
    /// Gets or sets the course code used in the URL.
    /// </summary>
    public string Code { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the course title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the main learning focus for the course.
    /// </summary>
    public string Focus { get; set; } = string.Empty;
}