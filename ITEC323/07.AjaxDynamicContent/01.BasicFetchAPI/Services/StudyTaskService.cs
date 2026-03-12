using BasicFetchAPI.Models;

namespace BasicFetchAPI.Services;

/// <summary>
/// Stores tasks in memory so the Fetch API demo works without a database or internet connection.
/// </summary>
public class StudyTaskService
{
    private readonly List<StudyTaskItem> _tasks;
    private readonly object _syncRoot = new();
    private int _nextId;

    /// <summary>
    /// Initializes a new instance of the <see cref="StudyTaskService"/> class with sample data.
    /// </summary>
    public StudyTaskService()
    {
        _tasks =
        [
            new StudyTaskItem
            {
                Id = 1,
                Title = "Read the Fetch API notes",
                Category = "Reading",
                DueDate = DateTime.Today.AddDays(1),
                Notes = "Focus on GET, POST, PUT, and DELETE examples.",
                IsCompleted = false,
                LastUpdated = DateTime.Now.AddMinutes(-40)
            },
            new StudyTaskItem
            {
                Id = 2,
                Title = "Test the local API with browser tools",
                Category = "Practice",
                DueDate = DateTime.Today.AddDays(2),
                Notes = "Open the Network tab and inspect JSON responses.",
                IsCompleted = true,
                LastUpdated = DateTime.Now.AddMinutes(-25)
            },
            new StudyTaskItem
            {
                Id = 3,
                Title = "Explain polling in your own words",
                Category = "Reflection",
                DueDate = DateTime.Today.AddDays(3),
                Notes = "Describe why repeated requests can keep a page updated.",
                IsCompleted = false,
                LastUpdated = DateTime.Now.AddMinutes(-10)
            }
        ];

        _nextId = _tasks.Max(task => task.Id) + 1;
    }

    /// <summary>
    /// Returns every task currently stored in memory.
    /// </summary>
    /// <returns>A copied list ordered by identifier.</returns>
    public List<StudyTaskItem> GetAll()
    {
        lock (_syncRoot)
        {
            return _tasks
                .OrderBy(task => task.Id)
                .Select(CloneTask)
                .ToList();
        }
    }

    /// <summary>
    /// Returns one task by identifier when it exists.
    /// </summary>
    /// <param name="id">The identifier of the task to search for.</param>
    /// <returns>The matching task, or <see langword="null"/> when not found.</returns>
    public StudyTaskItem? GetById(int id)
    {
        lock (_syncRoot)
        {
            var task = _tasks.FirstOrDefault(item => item.Id == id);
            return task is null ? null : CloneTask(task);
        }
    }

    /// <summary>
    /// Creates a new task from the request body and stores it in memory.
    /// </summary>
    /// <param name="request">The validated request from the API controller.</param>
    /// <returns>The created task with a generated identifier.</returns>
    public StudyTaskItem Create(StudyTaskRequest request)
    {
        lock (_syncRoot)
        {
            var task = new StudyTaskItem
            {
                Id = _nextId++,
                Title = request.Title.Trim(),
                Category = request.Category.Trim(),
                DueDate = request.DueDate,
                Notes = string.IsNullOrWhiteSpace(request.Notes) ? null : request.Notes.Trim(),
                IsCompleted = request.IsCompleted,
                LastUpdated = DateTime.Now
            };

            _tasks.Add(task);

            return CloneTask(task);
        }
    }

    /// <summary>
    /// Replaces an existing task with updated values.
    /// </summary>
    /// <param name="id">The identifier of the task to update.</param>
    /// <param name="request">The new values sent by the browser.</param>
    /// <returns>The updated task, or <see langword="null"/> when the task does not exist.</returns>
    public StudyTaskItem? Update(int id, StudyTaskRequest request)
    {
        lock (_syncRoot)
        {
            var task = _tasks.FirstOrDefault(item => item.Id == id);

            if (task is null)
            {
                return null;
            }

            task.Title = request.Title.Trim();
            task.Category = request.Category.Trim();
            task.DueDate = request.DueDate;
            task.Notes = string.IsNullOrWhiteSpace(request.Notes) ? null : request.Notes.Trim();
            task.IsCompleted = request.IsCompleted;
            task.LastUpdated = DateTime.Now;

            return CloneTask(task);
        }
    }

    /// <summary>
    /// Removes one task from memory.
    /// </summary>
    /// <param name="id">The identifier of the task to remove.</param>
    /// <returns><see langword="true"/> when the task was removed; otherwise, <see langword="false"/>.</returns>
    public bool Delete(int id)
    {
        lock (_syncRoot)
        {
            var task = _tasks.FirstOrDefault(item => item.Id == id);

            if (task is null)
            {
                return false;
            }

            _tasks.Remove(task);
            return true;
        }
    }

    /// <summary>
    /// Builds a simple summary for the dashboard cards.
    /// </summary>
    /// <returns>A calculated task summary.</returns>
    public StudyTaskSummary GetSummary()
    {
        lock (_syncRoot)
        {
            var completedTasks = _tasks.Count(task => task.IsCompleted);

            return new StudyTaskSummary
            {
                TotalTasks = _tasks.Count,
                CompletedTasks = completedTasks,
                PendingTasks = _tasks.Count - completedTasks,
                GeneratedAt = DateTime.Now
            };
        }
    }

    private static StudyTaskItem CloneTask(StudyTaskItem task)
    {
        return new StudyTaskItem
        {
            Id = task.Id,
            Title = task.Title,
            Category = task.Category,
            DueDate = task.DueDate,
            Notes = task.Notes,
            IsCompleted = task.IsCompleted,
            LastUpdated = task.LastUpdated
        };
    }
}
