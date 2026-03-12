using HtmxPartialRendering.Models;

namespace HtmxPartialRendering.Services;

/// <summary>
/// Provides in-memory data for the HTMX partial rendering sample.
/// </summary>
public class HtmxCourseService
{
    private readonly List<CourseNotice> _notices;
    private readonly List<CourseTip> _tips;
    private readonly List<CheckInMessage> _checkIns;
    private readonly object _syncRoot = new();
    private int _nextCheckInId;

    /// <summary>
    /// Initializes a new instance of the <see cref="HtmxCourseService"/> class.
    /// </summary>
    public HtmxCourseService()
    {
        _notices =
        [
            new CourseNotice
            {
                Title = "Use Small Endpoints",
                Message = "Each HTMX request should return only the HTML needed for one section.",
                Category = "Fragments",
                PublishedAt = DateTime.Now.AddHours(-5)
            },
            new CourseNotice
            {
                Title = "Show A Loading State",
                Message = "Indicators help students see that a request is in progress.",
                Category = "User Experience",
                PublishedAt = DateTime.Now.AddHours(-2)
            },
            new CourseNotice
            {
                Title = "Compare Swap Modes",
                Message = "Use innerHTML for replacement and afterbegin or beforeend for append behavior.",
                Category = "HTMX",
                PublishedAt = DateTime.Now.AddMinutes(-40)
            }
        ];

        _tips =
        [
            new CourseTip
            {
                Title = "Start With hx-get",
                Description = "A button with hx-get and hx-target is the easiest way to teach partial updates.",
                NextAction = "Refresh the notices panel and inspect the returned fragment."
            },
            new CourseTip
            {
                Title = "Use hx-post For Forms",
                Description = "Posting a form can return one new HTML block that is appended into an existing list.",
                NextAction = "Submit a reflection and watch the activity feed grow."
            },
            new CourseTip
            {
                Title = "Choose The Right Swap",
                Description = "innerHTML replaces the target content, while afterbegin inserts new content at the top.",
                NextAction = "Compare the notices panel with the reflection feed."
            }
        ];

        _checkIns =
        [
            new CheckInMessage
            {
                Id = 1,
                TaskName = "Read the HTMX guide",
                Reflection = "I can see how HTML fragments reduce custom JavaScript.",
                CreatedAt = DateTime.Now.AddMinutes(-25)
            },
            new CheckInMessage
            {
                Id = 2,
                TaskName = "Inspect a fragment response",
                Reflection = "The server returned only the card markup, not a full page.",
                CreatedAt = DateTime.Now.AddMinutes(-8)
            }
        ];

        _nextCheckInId = _checkIns.Max(item => item.Id) + 1;
    }

    /// <summary>
    /// Returns the latest course notices.
    /// </summary>
    /// <returns>A copied list of notices sorted by publish time.</returns>
    public List<CourseNotice> GetNotices()
    {
        lock (_syncRoot)
        {
            return _notices
                .OrderByDescending(item => item.PublishedAt)
                .Select(item => new CourseNotice
                {
                    Title = item.Title,
                    Message = item.Message,
                    Category = item.Category,
                    PublishedAt = item.PublishedAt
                })
                .ToList();
        }
    }

    /// <summary>
    /// Returns one spotlight tip based on the current number of reflections.
    /// </summary>
    /// <returns>A learning tip.</returns>
    public CourseTip GetTip()
    {
        lock (_syncRoot)
        {
            var index = _checkIns.Count % _tips.Count;
            var tip = _tips[index];

            return new CourseTip
            {
                Title = tip.Title,
                Description = tip.Description,
                NextAction = tip.NextAction
            };
        }
    }

    /// <summary>
    /// Returns the current statistics panel data.
    /// </summary>
    /// <returns>A statistics summary.</returns>
    public CourseStats GetStats()
    {
        lock (_syncRoot)
        {
            return new CourseStats
            {
                FragmentRequests = 4 + _checkIns.Count,
                UpdatedSections = 3,
                TeachingNote = "The page uses hx-get and hx-post to fetch HTML fragments without page reloads.",
                RefreshedAt = DateTime.Now
            };
        }
    }

    /// <summary>
    /// Returns the reflection feed entries.
    /// </summary>
    /// <returns>A copied list of reflection entries ordered from newest to oldest.</returns>
    public List<CheckInMessage> GetCheckIns()
    {
        lock (_syncRoot)
        {
            return _checkIns
                .OrderByDescending(item => item.CreatedAt)
                .Select(item => new CheckInMessage
                {
                    Id = item.Id,
                    TaskName = item.TaskName,
                    Reflection = item.Reflection,
                    CreatedAt = item.CreatedAt
                })
                .ToList();
        }
    }

    /// <summary>
    /// Adds a new reflection to the in-memory feed.
    /// </summary>
    /// <param name="request">The posted form values.</param>
    /// <returns>The created reflection entry.</returns>
    public CheckInMessage AddCheckIn(CheckInRequest request)
    {
        lock (_syncRoot)
        {
            var message = new CheckInMessage
            {
                Id = _nextCheckInId++,
                TaskName = request.TaskName.Trim(),
                Reflection = request.Reflection.Trim(),
                CreatedAt = DateTime.Now
            };

            _checkIns.Add(message);

            return new CheckInMessage
            {
                Id = message.Id,
                TaskName = message.TaskName,
                Reflection = message.Reflection,
                CreatedAt = message.CreatedAt
            };
        }
    }
}
