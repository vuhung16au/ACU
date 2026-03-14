namespace ShellNavigation.Models;

/// <summary>
/// Represents one task item used in Shell navigation examples.
/// </summary>
public sealed class NavigationTask
{
	/// <summary>
	/// Unique identifier used in route query parameters.
	/// </summary>
	public int Id { get; init; }

	/// <summary>
	/// Title shown in list and detail pages.
	/// </summary>
	public string Title { get; set; } = string.Empty;

	/// <summary>
	/// Longer task description.
	/// </summary>
	public string Description { get; set; } = string.Empty;

	/// <summary>
	/// Indicates whether the task is completed.
	/// </summary>
	public bool IsCompleted { get; set; }
}
