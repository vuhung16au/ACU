using LocalTodoApi.Models;

namespace LocalTodoApi.Services;

/// <summary>
/// Stores todo items in memory so the sample runs fully offline.
/// </summary>
public class TodoItemService
{
    private readonly List<TodoItem> _todoItems;
    private readonly object _syncRoot = new();
    private int _nextId;

    /// <summary>
    /// Initializes a new instance of the <see cref="TodoItemService"/> class with sample data.
    /// </summary>
    public TodoItemService()
    {
        _todoItems =
        [
            new TodoItem
            {
                Id = 1,
                Title = "Review HTTP verbs",
                Description = "Match GET, POST, PUT, and DELETE to the todo API endpoints.",
                Category = "Study",
                DueDate = DateTime.Today.AddDays(1),
                IsCompleted = false,
                LastUpdated = DateTime.Now.AddMinutes(-35)
            },
            new TodoItem
            {
                Id = 2,
                Title = "Inspect a 201 Created response",
                Description = "Use DevTools to confirm the API returns the created todo item.",
                Category = "Practice",
                DueDate = DateTime.Today.AddDays(2),
                IsCompleted = true,
                LastUpdated = DateTime.Now.AddMinutes(-20)
            },
            new TodoItem
            {
                Id = 3,
                Title = "Plan the next lab task",
                Description = "Write one pending task and update it later through the API.",
                Category = "Planning",
                DueDate = DateTime.Today.AddDays(3),
                IsCompleted = false,
                LastUpdated = DateTime.Now.AddMinutes(-8)
            }
        ];

        _nextId = _todoItems.Max(item => item.Id) + 1;
    }

    /// <summary>
    /// Returns every todo item in memory.
    /// </summary>
    /// <returns>A copied todo item list.</returns>
    public List<TodoItem> GetAll()
    {
        lock (_syncRoot)
        {
            return _todoItems
                .OrderBy(item => item.Id)
                .Select(CloneItem)
                .ToList();
        }
    }

    /// <summary>
    /// Returns one todo item by identifier.
    /// </summary>
    /// <param name="id">The identifier of the todo item.</param>
    /// <returns>The matching item, or <see langword="null"/> when not found.</returns>
    public TodoItem? GetById(int id)
    {
        lock (_syncRoot)
        {
            var todoItem = _todoItems.FirstOrDefault(item => item.Id == id);
            return todoItem is null ? null : CloneItem(todoItem);
        }
    }

    /// <summary>
    /// Creates a new todo item.
    /// </summary>
    /// <param name="request">The validated request body.</param>
    /// <returns>The created todo item.</returns>
    public TodoItem Create(TodoItemRequest request)
    {
        lock (_syncRoot)
        {
            var todoItem = new TodoItem
            {
                Id = _nextId++,
                Title = request.Title.Trim(),
                Description = string.IsNullOrWhiteSpace(request.Description) ? null : request.Description.Trim(),
                Category = request.Category.Trim(),
                DueDate = request.DueDate,
                IsCompleted = request.IsCompleted,
                LastUpdated = DateTime.Now
            };

            _todoItems.Add(todoItem);
            return CloneItem(todoItem);
        }
    }

    /// <summary>
    /// Updates an existing todo item.
    /// </summary>
    /// <param name="id">The identifier of the item to update.</param>
    /// <param name="request">The replacement values.</param>
    /// <returns>The updated item, or <see langword="null"/> when not found.</returns>
    public TodoItem? Update(int id, TodoItemRequest request)
    {
        lock (_syncRoot)
        {
            var todoItem = _todoItems.FirstOrDefault(item => item.Id == id);

            if (todoItem is null)
            {
                return null;
            }

            todoItem.Title = request.Title.Trim();
            todoItem.Description = string.IsNullOrWhiteSpace(request.Description) ? null : request.Description.Trim();
            todoItem.Category = request.Category.Trim();
            todoItem.DueDate = request.DueDate;
            todoItem.IsCompleted = request.IsCompleted;
            todoItem.LastUpdated = DateTime.Now;

            return CloneItem(todoItem);
        }
    }

    /// <summary>
    /// Deletes a todo item.
    /// </summary>
    /// <param name="id">The identifier of the item to delete.</param>
    /// <returns><see langword="true"/> when the item was removed; otherwise, <see langword="false"/>.</returns>
    public bool Delete(int id)
    {
        lock (_syncRoot)
        {
            var todoItem = _todoItems.FirstOrDefault(item => item.Id == id);

            if (todoItem is null)
            {
                return false;
            }

            _todoItems.Remove(todoItem);
            return true;
        }
    }

    /// <summary>
    /// Builds a summary of the current todo items.
    /// </summary>
    /// <returns>A summary object for the dashboard cards.</returns>
    public TodoSummary GetSummary()
    {
        lock (_syncRoot)
        {
            var completedItems = _todoItems.Count(item => item.IsCompleted);

            return new TodoSummary
            {
                TotalItems = _todoItems.Count,
                CompletedItems = completedItems,
                PendingItems = _todoItems.Count - completedItems,
                GeneratedAt = DateTime.Now
            };
        }
    }

    private static TodoItem CloneItem(TodoItem item)
    {
        return new TodoItem
        {
            Id = item.Id,
            Title = item.Title,
            Description = item.Description,
            Category = item.Category,
            DueDate = item.DueDate,
            IsCompleted = item.IsCompleted,
            LastUpdated = item.LastUpdated
        };
    }
}
