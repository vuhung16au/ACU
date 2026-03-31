namespace _02_LiveNotificationsDashboard.Models;

/// <summary>
/// Represents summary counts shown at the top of the dashboard.
/// </summary>
public class DashboardSummary
{
    /// <summary>
    /// Gets or sets the total number of notifications in memory.
    /// </summary>
    public int TotalNotifications { get; set; }

    /// <summary>
    /// Gets or sets the number of information notifications.
    /// </summary>
    public int InfoCount { get; set; }

    /// <summary>
    /// Gets or sets the number of success notifications.
    /// </summary>
    public int SuccessCount { get; set; }

    /// <summary>
    /// Gets or sets the number of warning notifications.
    /// </summary>
    public int WarningCount { get; set; }
}
