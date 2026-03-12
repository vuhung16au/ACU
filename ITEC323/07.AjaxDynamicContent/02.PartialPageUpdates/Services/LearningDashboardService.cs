using PartialPageUpdates.Models;

namespace PartialPageUpdates.Services;

/// <summary>
/// Stores the sample dashboard data used to demonstrate partial page updates.
/// </summary>
public class LearningDashboardService
{
    private readonly List<ActivityLogEntry> _activityLog;
    private readonly List<AnnouncementItem> _announcements;
    private readonly List<ResourceSpotlight> _resources;
    private readonly object _syncRoot = new();
    private int _nextActivityId;

    /// <summary>
    /// Initializes a new instance of the <see cref="LearningDashboardService"/> class.
    /// </summary>
    public LearningDashboardService()
    {
        _announcements =
        [
            new AnnouncementItem
            {
                Id = 1,
                Title = "Lab Reminder",
                Message = "Bring your browser DevTools notes so you can inspect each asynchronous request.",
                Topic = "Week 7",
                PublishedAt = DateTime.Now.AddHours(-6)
            },
            new AnnouncementItem
            {
                Id = 2,
                Title = "Lecture Focus",
                Message = "Today we compare replacing HTML in one section with appending new content to another section.",
                Topic = "Partial Updates",
                PublishedAt = DateTime.Now.AddHours(-3)
            },
            new AnnouncementItem
            {
                Id = 3,
                Title = "Practice Goal",
                Message = "Try updating the summary cards without losing the text already typed into the check-in form.",
                Topic = "State Management",
                PublishedAt = DateTime.Now.AddMinutes(-45)
            }
        ];

        _resources =
        [
            new ResourceSpotlight
            {
                Title = "Replace A Single Section",
                Summary = "Use one fetch request to refresh only the announcement list instead of the whole page.",
                NextStep = "Click the Load Announcements button and inspect which area changes.",
                ResourceType = "Demo"
            },
            new ResourceSpotlight
            {
                Title = "Append New Content",
                Summary = "Use DOM methods such as insertAdjacentHTML to add activity items under the existing list.",
                NextStep = "Submit a check-in and watch the activity log grow without clearing older entries.",
                ResourceType = "Technique"
            },
            new ResourceSpotlight
            {
                Title = "Keep Page State",
                Summary = "Store form values in the browser while other sections update independently.",
                NextStep = "Type into the reflection box, then refresh the summary cards to confirm the form stays unchanged.",
                ResourceType = "Tip"
            }
        ];

        _activityLog =
        [
            new ActivityLogEntry
            {
                Id = 1,
                ActivityName = "Read the FRD",
                Reflection = "I identified the acceptance criteria for partial updates.",
                CreatedAt = DateTime.Now.AddMinutes(-30)
            },
            new ActivityLogEntry
            {
                Id = 2,
                ActivityName = "Inspect the DOM",
                Reflection = "I checked that only the targeted section changed after the request.",
                CreatedAt = DateTime.Now.AddMinutes(-12)
            }
        ];

        _nextActivityId = _activityLog.Max(entry => entry.Id) + 1;
    }

    /// <summary>
    /// Returns the latest announcements sorted by publish time.
    /// </summary>
    /// <returns>A copied announcement list.</returns>
    public List<AnnouncementItem> GetAnnouncements()
    {
        lock (_syncRoot)
        {
            return _announcements
                .OrderByDescending(item => item.PublishedAt)
                .Select(CloneAnnouncement)
                .ToList();
        }
    }

    /// <summary>
    /// Returns the current learning progress summary.
    /// </summary>
    /// <returns>A summary built from the current activity data.</returns>
    public LearningProgressSummary GetProgressSummary()
    {
        lock (_syncRoot)
        {
            var completedActivities = _activityLog.Count;

            return new LearningProgressSummary
            {
                CompletedActivities = completedActivities,
                RemainingActivities = Math.Max(0, 6 - completedActivities),
                NextFocus = completedActivities >= 4 ? "Prepare for HTMX fragments" : "Practise DOM replacement and append operations",
                RefreshedAt = DateTime.Now
            };
        }
    }

    /// <summary>
    /// Returns a resource spotlight based on the current activity count.
    /// </summary>
    /// <returns>A resource item for the spotlight panel.</returns>
    public ResourceSpotlight GetSpotlight()
    {
        lock (_syncRoot)
        {
            var index = _activityLog.Count % _resources.Count;
            return CloneResource(_resources[index]);
        }
    }

    /// <summary>
    /// Returns the current activity log entries.
    /// </summary>
    /// <returns>A copied activity log list ordered from newest to oldest.</returns>
    public List<ActivityLogEntry> GetActivityLog()
    {
        lock (_syncRoot)
        {
            return _activityLog
                .OrderByDescending(entry => entry.CreatedAt)
                .Select(CloneActivity)
                .ToList();
        }
    }

    /// <summary>
    /// Adds a new activity log entry to the in-memory collection.
    /// </summary>
    /// <param name="request">The validated request body.</param>
    /// <returns>The newly created activity entry.</returns>
    public ActivityLogEntry AddCheckIn(CheckInRequest request)
    {
        lock (_syncRoot)
        {
            var entry = new ActivityLogEntry
            {
                Id = _nextActivityId++,
                ActivityName = request.ActivityName.Trim(),
                Reflection = request.Reflection.Trim(),
                CreatedAt = DateTime.Now
            };

            _activityLog.Add(entry);
            return CloneActivity(entry);
        }
    }

    private static AnnouncementItem CloneAnnouncement(AnnouncementItem item)
    {
        return new AnnouncementItem
        {
            Id = item.Id,
            Title = item.Title,
            Message = item.Message,
            Topic = item.Topic,
            PublishedAt = item.PublishedAt
        };
    }

    private static ActivityLogEntry CloneActivity(ActivityLogEntry entry)
    {
        return new ActivityLogEntry
        {
            Id = entry.Id,
            ActivityName = entry.ActivityName,
            Reflection = entry.Reflection,
            CreatedAt = entry.CreatedAt
        };
    }

    private static ResourceSpotlight CloneResource(ResourceSpotlight item)
    {
        return new ResourceSpotlight
        {
            Title = item.Title,
            Summary = item.Summary,
            NextStep = item.NextStep,
            ResourceType = item.ResourceType
        };
    }
}
