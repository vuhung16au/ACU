using ComprehensiveApp.Models;

namespace ComprehensiveApp.Services;

/// <summary>
/// Stores all local data used by the comprehensive capstone sample.
/// </summary>
public class CapstoneDataService
{
    private readonly List<CapstoneTaskItem> _tasks;
    private readonly List<ReflectionEntry> _reflections;
    private readonly List<TechniqueTip> _tips;
    private readonly object _syncRoot = new();
    private int _nextTaskId;
    private int _nextReflectionId;

    /// <summary>
    /// Initializes a new instance of the <see cref="CapstoneDataService"/> class with sample data.
    /// </summary>
    public CapstoneDataService()
    {
        _tasks =
        [
            new CapstoneTaskItem
            {
                Id = 1,
                Title = "Document the REST endpoints",
                Technique = "Fetch API",
                Description = "Check the JSON API in Swagger and test the GET responses.",
                IsCompleted = true,
                LastUpdated = DateTime.Now.AddMinutes(-35)
            },
            new CapstoneTaskItem
            {
                Id = 2,
                Title = "Refresh only the summary cards",
                Technique = "Polling",
                Description = "Use setInterval to update dashboard totals every 15 seconds.",
                IsCompleted = false,
                LastUpdated = DateTime.Now.AddMinutes(-20)
            },
            new CapstoneTaskItem
            {
                Id = 3,
                Title = "Load HTML fragment tips",
                Technique = "HTMX",
                Description = "Request a server-rendered fragment without custom page logic.",
                IsCompleted = false,
                LastUpdated = DateTime.Now.AddMinutes(-10)
            }
        ];

        _reflections =
        [
            new ReflectionEntry
            {
                Id = 1,
                Technique = "Fetch API",
                Message = "JSON endpoints are best when the browser needs to control the rendering.",
                CreatedAt = DateTime.Now.AddMinutes(-18)
            },
            new ReflectionEntry
            {
                Id = 2,
                Technique = "HTMX",
                Message = "HTML fragments keep the client code smaller for simple partial updates.",
                CreatedAt = DateTime.Now.AddMinutes(-8)
            }
        ];

        _tips =
        [
            new TechniqueTip
            {
                Technique = "Fetch API",
                Title = "Use Fetch For Flexible UI Logic",
                Description = "Fetch is strongest when the browser needs full control over parsing JSON, managing loading states, and re-rendering several parts of the page."
            },
            new TechniqueTip
            {
                Technique = "HTMX",
                Title = "Use HTMX For Small HTML Swaps",
                Description = "HTMX is strongest when the server can return the final HTML fragment and the page only needs a small targeted update."
            },
            new TechniqueTip
            {
                Technique = "Blazor",
                Title = "Use Blazor For C#-Driven Interactivity",
                Description = "Blazor is strongest when you want component state, event handling, and UI updates to stay in C# instead of browser JavaScript."
            }
        ];

        _nextTaskId = _tasks.Max(item => item.Id) + 1;
        _nextReflectionId = _reflections.Max(item => item.Id) + 1;
    }

    /// <summary>
    /// Returns all capstone tasks.
    /// </summary>
    /// <returns>A copied task list.</returns>
    public List<CapstoneTaskItem> GetTasks()
    {
        lock (_syncRoot)
        {
            return _tasks
                .OrderBy(item => item.Id)
                .Select(CloneTask)
                .ToList();
        }
    }

    /// <summary>
    /// Returns one task by identifier.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <returns>The matching task or <see langword="null"/>.</returns>
    public CapstoneTaskItem? GetTaskById(int id)
    {
        lock (_syncRoot)
        {
            var item = _tasks.FirstOrDefault(task => task.Id == id);
            return item is null ? null : CloneTask(item);
        }
    }

    /// <summary>
    /// Creates a new capstone task.
    /// </summary>
    /// <param name="request">The validated request body.</param>
    /// <returns>The created task.</returns>
    public CapstoneTaskItem CreateTask(CapstoneTaskRequest request)
    {
        lock (_syncRoot)
        {
            var item = new CapstoneTaskItem
            {
                Id = _nextTaskId++,
                Title = request.Title.Trim(),
                Technique = request.Technique.Trim(),
                Description = string.IsNullOrWhiteSpace(request.Description) ? null : request.Description.Trim(),
                IsCompleted = request.IsCompleted,
                LastUpdated = DateTime.Now
            };

            _tasks.Add(item);
            return CloneTask(item);
        }
    }

    /// <summary>
    /// Updates an existing capstone task.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <param name="request">The replacement values.</param>
    /// <returns>The updated task or <see langword="null"/>.</returns>
    public CapstoneTaskItem? UpdateTask(int id, CapstoneTaskRequest request)
    {
        lock (_syncRoot)
        {
            var item = _tasks.FirstOrDefault(task => task.Id == id);

            if (item is null)
            {
                return null;
            }

            item.Title = request.Title.Trim();
            item.Technique = request.Technique.Trim();
            item.Description = string.IsNullOrWhiteSpace(request.Description) ? null : request.Description.Trim();
            item.IsCompleted = request.IsCompleted;
            item.LastUpdated = DateTime.Now;

            return CloneTask(item);
        }
    }

    /// <summary>
    /// Deletes a capstone task.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <returns><see langword="true"/> when deleted; otherwise <see langword="false"/>.</returns>
    public bool DeleteTask(int id)
    {
        lock (_syncRoot)
        {
            var item = _tasks.FirstOrDefault(task => task.Id == id);

            if (item is null)
            {
                return false;
            }

            _tasks.Remove(item);
            return true;
        }
    }

    /// <summary>
    /// Returns the current summary for polling updates.
    /// </summary>
    /// <returns>A summary object.</returns>
    public CapstoneSummary GetSummary()
    {
        lock (_syncRoot)
        {
            var completedTasks = _tasks.Count(task => task.IsCompleted);

            return new CapstoneSummary
            {
                TotalTasks = _tasks.Count,
                CompletedTasks = completedTasks,
                PendingTasks = _tasks.Count - completedTasks,
                StatusText = "Capstone dashboard refreshed locally. Summary cards can update independently from the rest of the page.",
                RefreshedAt = DateTime.Now
            };
        }
    }

    /// <summary>
    /// Returns one technique tip based on the current task count.
    /// </summary>
    /// <returns>A tip for the server-rendered fragment panel.</returns>
    public TechniqueTip GetTechniqueTip()
    {
        lock (_syncRoot)
        {
            var tip = _tips[_tasks.Count % _tips.Count];

            return new TechniqueTip
            {
                Technique = tip.Technique,
                Title = tip.Title,
                Description = tip.Description
            };
        }
    }

    /// <summary>
    /// Returns the current reflection feed.
    /// </summary>
    /// <returns>A copied reflection list ordered from newest to oldest.</returns>
    public List<ReflectionEntry> GetReflections()
    {
        lock (_syncRoot)
        {
            return _reflections
                .OrderByDescending(item => item.CreatedAt)
                .Select(CloneReflection)
                .ToList();
        }
    }

    /// <summary>
    /// Adds a new reflection entry to the feed.
    /// </summary>
    /// <param name="request">The validated reflection request.</param>
    /// <returns>The created reflection entry.</returns>
    public ReflectionEntry AddReflection(ReflectionRequest request)
    {
        lock (_syncRoot)
        {
            var entry = new ReflectionEntry
            {
                Id = _nextReflectionId++,
                Technique = request.Technique.Trim(),
                Message = request.Message.Trim(),
                CreatedAt = DateTime.Now
            };

            _reflections.Add(entry);
            return CloneReflection(entry);
        }
    }

    private static CapstoneTaskItem CloneTask(CapstoneTaskItem item)
    {
        return new CapstoneTaskItem
        {
            Id = item.Id,
            Title = item.Title,
            Technique = item.Technique,
            Description = item.Description,
            IsCompleted = item.IsCompleted,
            LastUpdated = item.LastUpdated
        };
    }

    private static ReflectionEntry CloneReflection(ReflectionEntry item)
    {
        return new ReflectionEntry
        {
            Id = item.Id,
            Technique = item.Technique,
            Message = item.Message,
            CreatedAt = item.CreatedAt
        };
    }
}
