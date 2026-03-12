namespace ViewComponentsDemo.Models;

/// <summary>
/// Represents a user profile shown by the user profile view component.
/// </summary>
public class UserProfile
{
    /// <summary>
    /// Gets or sets the user identifier.
    /// </summary>
    public int UserId { get; set; }

    /// <summary>
    /// Gets or sets the user name.
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the user role.
    /// </summary>
    public string Role { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the course progress percentage.
    /// </summary>
    public int CourseProgressPercent { get; set; }

    /// <summary>
    /// Gets or sets the next task for the user.
    /// </summary>
    public string NextTask { get; set; } = string.Empty;
}
