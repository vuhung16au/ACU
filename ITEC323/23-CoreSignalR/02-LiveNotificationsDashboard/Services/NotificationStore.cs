using _02_LiveNotificationsDashboard.Models;

namespace _02_LiveNotificationsDashboard.Services;

/// <summary>
/// Stores the most recent notifications in memory for this teaching sample.
/// </summary>
public class NotificationStore
{
    private const int MaxNotifications = 12;
    private readonly List<NotificationItem> _notifications = new();
    private readonly object _lock = new();

    /// <summary>
    /// Gets the most recent notifications with newest first.
    /// </summary>
    /// <returns>A read-only copy of the stored notifications.</returns>
    public IReadOnlyList<NotificationItem> GetNotifications()
    {
        lock (_lock)
        {
            return _notifications.ToList();
        }
    }

    /// <summary>
    /// Builds the dashboard summary counts from the stored notifications.
    /// </summary>
    /// <returns>The current summary values.</returns>
    public DashboardSummary GetSummary()
    {
        lock (_lock)
        {
            return new DashboardSummary
            {
                TotalNotifications = _notifications.Count,
                InfoCount = _notifications.Count(notification => notification.Severity == "info"),
                SuccessCount = _notifications.Count(notification => notification.Severity == "success"),
                WarningCount = _notifications.Count(notification => notification.Severity == "warning")
            };
        }
    }

    /// <summary>
    /// Adds a new notification and trims the collection to the most recent items.
    /// </summary>
    /// <param name="notification">The notification to store.</param>
    public void Add(NotificationItem notification)
    {
        lock (_lock)
        {
            _notifications.Insert(0, notification);

            if (_notifications.Count > MaxNotifications)
            {
                _notifications.RemoveRange(MaxNotifications, _notifications.Count - MaxNotifications);
            }
        }
    }
}
