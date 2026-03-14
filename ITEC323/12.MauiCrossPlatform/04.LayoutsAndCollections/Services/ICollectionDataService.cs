namespace LayoutsAndCollections.Services;

using LayoutsAndCollections.Models;

/// <summary>
/// Data source contract for layouts and collections learning scenarios.
/// </summary>
public interface ICollectionDataService
{
	/// <summary>
	/// Returns the current item set for initial page load.
	/// </summary>
	IReadOnlyList<CollectionItem> GetItems();

	/// <summary>
	/// Simulates pull-to-refresh and returns updated item values.
	/// </summary>
	/// <param name="cancellationToken">Cancellation token from UI refresh command.</param>
	Task<IReadOnlyList<CollectionItem>> RefreshAsync(CancellationToken cancellationToken = default);

	/// <summary>
	/// Adds a new item with normalized values.
	/// </summary>
	/// <param name="title">Title entered by the learner.</param>
	/// <param name="description">Description entered by the learner.</param>
	/// <param name="category">Category selected by the learner.</param>
	CollectionItem Add(string title, string description, string category);

	/// <summary>
	/// Removes all items from the in-memory set.
	/// </summary>
	void Clear();
}
