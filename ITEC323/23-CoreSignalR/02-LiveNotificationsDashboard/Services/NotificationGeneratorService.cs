using _02_LiveNotificationsDashboard.Hubs;
using _02_LiveNotificationsDashboard.Models;
using Microsoft.AspNetCore.SignalR;

namespace _02_LiveNotificationsDashboard.Services;

/// <summary>
/// Generates fake server-side events so students can observe live updates
/// without opening a second tool or background system.
/// </summary>
public class NotificationGeneratorService : BackgroundService
{
    private readonly IHubContext<NotificationHub> _hubContext;
    private readonly NotificationStore _notificationStore;

    private static readonly (string Title, string Message, string Severity, string Source)[] Templates =
    {
        ("New submission received", "ITEC323 Assignment 2 was uploaded to the portal.", "info", "Assessment"),
        ("Build completed", "The weekly sample solution finished building successfully.", "success", "CI Pipeline"),
        ("Server latency warning", "The demo API response time rose above 700 ms.", "warning", "Monitoring"),
        ("Student joined session", "A new learner joined the live coding support room.", "info", "Classroom"),
        ("Feedback published", "Rubric comments are now available for review.", "success", "Teaching Team"),
        ("Low disk space alert", "The deployment VM is running below 15% free space.", "warning", "Infrastructure")
    };

    /// <summary>
    /// Initializes a new instance of the notification generator service.
    /// </summary>
    /// <param name="hubContext">The SignalR hub context used to broadcast notifications.</param>
    /// <param name="notificationStore">The in-memory notification store.</param>
    public NotificationGeneratorService(
        IHubContext<NotificationHub> hubContext,
        NotificationStore notificationStore)
    {
        _hubContext = hubContext;
        _notificationStore = notificationStore;
    }

    /// <summary>
    /// Runs the repeating demo feed loop.
    /// </summary>
    /// <param name="stoppingToken">Signals when the host is shutting down.</param>
    /// <returns>A task that completes when the service stops.</returns>
    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        var random = new Random();

        while (!stoppingToken.IsCancellationRequested)
        {
            await Task.Delay(TimeSpan.FromSeconds(6), stoppingToken);

            var template = Templates[random.Next(Templates.Length)];
            var notification = new NotificationItem
            {
                Id = Guid.NewGuid(),
                Title = template.Title,
                Message = template.Message,
                Severity = template.Severity,
                Source = template.Source,
                CreatedAtUtc = DateTime.UtcNow
            };

            _notificationStore.Add(notification);

            await _hubContext.Clients.All.SendAsync(
                "ReceiveNotification",
                notification,
                _notificationStore.GetSummary(),
                cancellationToken: stoppingToken);
        }
    }
}
