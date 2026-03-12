namespace ComprehensiveAppDemo.Models;

/// <summary>
/// Represents summary values displayed in the user stats component.
/// </summary>
public class UserStatsSummary
{
    /// <summary>
    /// Gets or sets the total number of users.
    /// </summary>
    public int TotalUsers { get; set; }

    /// <summary>
    /// Gets or sets the number of adult users.
    /// </summary>
    public int AdultUsers { get; set; }

    /// <summary>
    /// Gets or sets the newest user name.
    /// </summary>
    public string NewestUserName { get; set; } = string.Empty;
}
