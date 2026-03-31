using _02_LiveNotificationsDashboard.Models;
using _02_LiveNotificationsDashboard.Services;
using Microsoft.AspNetCore.SignalR;

namespace _02_LiveNotificationsDashboard.Hubs;

/// <summary>
/// Provides live dashboard updates to all connected browser clients.
/// </summary>
public class NotificationHub : Hub
{
    private readonly NotificationStore _notificationStore;

    /// <summary>
    /// Initializes a new instance of the notification hub.
    /// </summary>
    /// <param name="notificationStore">The in-memory notification store.</param>
    public NotificationHub(NotificationStore notificationStore)
    {
        _notificationStore = notificationStore;
    }

    /// <summary>
    /// Sends the current dashboard state to the client that just connected.
    /// </summary>
    /// <returns>A task that completes when the initial state has been sent.</returns>
    public override async Task OnConnectedAsync()
    {
        await Clients.Caller.SendAsync(
            "LoadDashboard",
            _notificationStore.GetNotifications(),
            _notificationStore.GetSummary());

        await base.OnConnectedAsync();
    }

    /// <summary>
    /// Allows the page to create a manual notification event for demo purposes.
    /// </summary>
    /// <param name="title">The short title shown on the dashboard.</param>
    /// <param name="message">The longer detail text for the notification.</param>
    /// <param name="severity">The severity level such as info, success, or warning.</param>
    /// <param name="source">The feature or system that produced the event.</param>
    /// <returns>A task that completes when the notification has been broadcast.</returns>
    public async Task SendManualNotification(string title, string message, string severity, string source)
    {
        var notification = new NotificationItem
        {
            Id = Guid.NewGuid(),
            Title = title,
            Message = message,
            Severity = severity,
            Source = source,
            CreatedAtUtc = DateTime.UtcNow
        };

        _notificationStore.Add(notification);

        await Clients.All.SendAsync(
            "ReceiveNotification",
            notification,
            _notificationStore.GetSummary());
    }
}
