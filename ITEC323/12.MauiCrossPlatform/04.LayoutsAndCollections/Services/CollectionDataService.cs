namespace LayoutsAndCollections.Services;

using LayoutsAndCollections.Models;

/// <summary>
/// In-memory item service used to demonstrate data-bound collection layouts.
/// </summary>
public sealed class CollectionDataService : ICollectionDataService
{
	private int _nextId = 6;

	private readonly List<CollectionItem> _items =
	[
		new() { Id = 1, Title = "Layout Basics", Description = "Use Grid rows and columns for structured form-style layouts.", Category = "Grid", IsFeatured = true, UpdatedAt = DateTime.Now.AddMinutes(-25) },
		new() { Id = 2, Title = "Stack Layouts", Description = "Combine VerticalStackLayout and HorizontalStackLayout for readable sections.", Category = "StackLayout", IsFeatured = false, UpdatedAt = DateTime.Now.AddMinutes(-20) },
		new() { Id = 3, Title = "CollectionView ItemTemplate", Description = "Define reusable DataTemplate blocks for each row card.", Category = "CollectionView", IsFeatured = true, UpdatedAt = DateTime.Now.AddMinutes(-15) },
		new() { Id = 4, Title = "Empty States", Description = "Provide friendly EmptyView messaging when there is no content.", Category = "CollectionView", IsFeatured = false, UpdatedAt = DateTime.Now.AddMinutes(-10) },
		new() { Id = 5, Title = "Pull To Refresh", Description = "Wrap lists in RefreshView to support touch-based reload interactions.", Category = "RefreshView", IsFeatured = true, UpdatedAt = DateTime.Now.AddMinutes(-5) }
	];

	/// <inheritdoc/>
	public IReadOnlyList<CollectionItem> GetItems()
	{
		return _items
			.OrderByDescending(item => item.UpdatedAt)
			.Select(Clone)
			.ToList();
	}

	/// <inheritdoc/>
	public async Task<IReadOnlyList<CollectionItem>> RefreshAsync(CancellationToken cancellationToken = default)
	{
		await Task.Delay(350, cancellationToken);

		foreach (var item in _items)
		{
			item.IsFeatured = !item.IsFeatured;
			item.UpdatedAt = DateTime.Now;
		}

		return GetItems();
	}

	/// <inheritdoc/>
	public CollectionItem Add(string title, string description, string category)
	{
		var item = new CollectionItem
		{
			Id = _nextId++,
			Title = string.IsNullOrWhiteSpace(title) ? "Untitled item" : title.Trim(),
			Description = string.IsNullOrWhiteSpace(description) ? "No description provided." : description.Trim(),
			Category = string.IsNullOrWhiteSpace(category) ? "General" : category.Trim(),
			IsFeatured = false,
			UpdatedAt = DateTime.Now
		};

		_items.Add(item);
		return Clone(item);
	}

	/// <inheritdoc/>
	public void Clear()
	{
		_items.Clear();
	}

	private static CollectionItem Clone(CollectionItem item)
	{
		return new CollectionItem
		{
			Id = item.Id,
			Title = item.Title,
			Description = item.Description,
			Category = item.Category,
			IsFeatured = item.IsFeatured,
			UpdatedAt = item.UpdatedAt
		};
	}
}
