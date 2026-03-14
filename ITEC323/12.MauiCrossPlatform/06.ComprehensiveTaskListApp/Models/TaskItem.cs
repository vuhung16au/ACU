namespace ComprehensiveTaskListApp.Models;

/// <summary>
/// Represents a user task displayed in the list and edited in the detail page.
/// </summary>
public sealed class TaskItem
{
	/// <summary>
	/// Unique identifier used for routing and persistence operations.
	/// </summary>
	public string Id { get; set; } = Guid.NewGuid().ToString("N");

	/// <summary>
	/// Short task title shown as the primary row text.
	/// </summary>
	public string Title { get; set; } = string.Empty;

	/// <summary>
	/// Optional task details shown in the secondary row text.
	/// </summary>
	public string Description { get; set; } = string.Empty;

	/// <summary>
	/// Indicates whether the task has been completed.
	/// </summary>
	public bool IsCompleted { get; set; }

	/// <summary>
	/// UTC timestamp of initial task creation.
	/// </summary>
	public DateTimeOffset CreatedAtUtc { get; set; } = DateTimeOffset.UtcNow;

	/// <summary>
	/// UTC timestamp of the latest task update.
	/// </summary>
	public DateTimeOffset UpdatedAtUtc { get; set; } = DateTimeOffset.UtcNow;
}
