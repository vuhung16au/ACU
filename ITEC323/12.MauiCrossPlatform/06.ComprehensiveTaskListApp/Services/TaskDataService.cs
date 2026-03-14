using System.Text.Json;
using ComprehensiveTaskListApp.Models;
using Microsoft.Maui.Storage;

namespace ComprehensiveTaskListApp.Services;

/// <summary>
/// Persists task data in local device preferences as JSON.
/// </summary>
public sealed class TaskDataService : ITaskDataService
{
	private const string StorageKey = "task-list-items-v1";
	private static readonly JsonSerializerOptions SerializerOptions = new(JsonSerializerDefaults.Web);
	private readonly IPreferences _preferences;
	private readonly SemaphoreSlim _gate = new(1, 1);

	/// <summary>
	/// Initializes the preferences-backed task store.
	/// </summary>
	/// <param name="preferences">Abstraction over platform preference storage.</param>
	public TaskDataService(IPreferences preferences)
	{
		_preferences = preferences;
	}

	/// <inheritdoc />
	public async Task<IReadOnlyList<TaskItem>> GetTasksAsync()
	{
		await _gate.WaitAsync();
		try
		{
			var items = LoadUnsafe();
			if (items.Count == 0)
			{
				items = CreateSeedTasks();
				await PersistUnsafeAsync(items);
			}

			return SortForDisplay(items);
		}
		finally
		{
			_gate.Release();
		}
	}

	/// <inheritdoc />
	public async Task<TaskItem?> GetTaskByIdAsync(string taskId)
	{
		if (string.IsNullOrWhiteSpace(taskId))
		{
			return null;
		}

		var tasks = await GetTasksAsync();
		return tasks.FirstOrDefault(x => x.Id == taskId);
	}

	/// <inheritdoc />
	public async Task AddTaskAsync(TaskItem task)
	{
		ArgumentNullException.ThrowIfNull(task);

		await _gate.WaitAsync();
		try
		{
			var items = LoadUnsafe();
			task.Id = string.IsNullOrWhiteSpace(task.Id) ? Guid.NewGuid().ToString("N") : task.Id;
			task.CreatedAtUtc = task.CreatedAtUtc == default ? DateTimeOffset.UtcNow : task.CreatedAtUtc;
			task.UpdatedAtUtc = DateTimeOffset.UtcNow;
			items.Add(task);
			await PersistUnsafeAsync(items);
		}
		finally
		{
			_gate.Release();
		}
	}

	/// <inheritdoc />
	public async Task UpdateTaskAsync(TaskItem task)
	{
		ArgumentNullException.ThrowIfNull(task);

		await _gate.WaitAsync();
		try
		{
			var items = LoadUnsafe();
			var index = items.FindIndex(x => x.Id == task.Id);
			if (index < 0)
			{
				return;
			}

			task.UpdatedAtUtc = DateTimeOffset.UtcNow;
			items[index] = task;
			await PersistUnsafeAsync(items);
		}
		finally
		{
			_gate.Release();
		}
	}

	/// <inheritdoc />
	public async Task DeleteTaskAsync(string taskId)
	{
		if (string.IsNullOrWhiteSpace(taskId))
		{
			return;
		}

		await _gate.WaitAsync();
		try
		{
			var items = LoadUnsafe();
			items.RemoveAll(x => x.Id == taskId);
			await PersistUnsafeAsync(items);
		}
		finally
		{
			_gate.Release();
		}
	}

	/// <inheritdoc />
	public async Task ToggleCompletedAsync(string taskId)
	{
		if (string.IsNullOrWhiteSpace(taskId))
		{
			return;
		}

		await _gate.WaitAsync();
		try
		{
			var items = LoadUnsafe();
			var item = items.FirstOrDefault(x => x.Id == taskId);
			if (item is null)
			{
				return;
			}

			item.IsCompleted = !item.IsCompleted;
			item.UpdatedAtUtc = DateTimeOffset.UtcNow;
			await PersistUnsafeAsync(items);
		}
		finally
		{
			_gate.Release();
		}
	}

	private List<TaskItem> LoadUnsafe()
	{
		var payload = _preferences.Get(StorageKey, string.Empty);
		if (string.IsNullOrWhiteSpace(payload))
		{
			return [];
		}

		return JsonSerializer.Deserialize<List<TaskItem>>(payload, SerializerOptions) ?? [];
	}

	private Task PersistUnsafeAsync(List<TaskItem> items)
	{
		var payload = JsonSerializer.Serialize(items, SerializerOptions);
		_preferences.Set(StorageKey, payload);
		return Task.CompletedTask;
	}

	private static List<TaskItem> CreateSeedTasks()
	{
		return
		[
			new TaskItem
			{
				Title = "Plan assignment milestones",
				Description = "Break work into weekly goals and due dates.",
				IsCompleted = false
			},
			new TaskItem
			{
				Title = "Revise MAUI MVVM bindings",
				Description = "Review command, collection, and route-binding examples.",
				IsCompleted = true
			},
			new TaskItem
			{
				Title = "Prepare Android emulator demo",
				Description = "Check runtime permissions and startup script before class.",
				IsCompleted = false
			}
		];
	}

	private static IReadOnlyList<TaskItem> SortForDisplay(IEnumerable<TaskItem> items)
	{
		return items
			.OrderBy(x => x.IsCompleted)
			.ThenByDescending(x => x.UpdatedAtUtc)
			.ToList();
	}
}
