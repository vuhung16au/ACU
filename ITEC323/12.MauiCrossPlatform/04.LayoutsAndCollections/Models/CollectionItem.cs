namespace LayoutsAndCollections.Models;

/// <summary>
/// Represents one row displayed in the CollectionView.
/// </summary>
public sealed class CollectionItem
{
	/// <summary>
	/// Stable identifier for item tracking.
	/// </summary>
	public int Id { get; init; }

	/// <summary>
	/// Main title shown in each template card.
	/// </summary>
	public string Title { get; set; } = string.Empty;

	/// <summary>
	/// Supporting item description.
	/// </summary>
	public string Description { get; set; } = string.Empty;

	/// <summary>
	/// Category pill shown in the footer row.
	/// </summary>
	public string Category { get; set; } = "General";

	/// <summary>
	/// Indicates feature highlighting in the UI.
	/// </summary>
	public bool IsFeatured { get; set; }

	/// <summary>
	/// Last update timestamp used for status text.
	/// </summary>
	public DateTime UpdatedAt { get; set; }
}
