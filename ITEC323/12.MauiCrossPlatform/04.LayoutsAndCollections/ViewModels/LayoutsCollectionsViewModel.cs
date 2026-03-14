using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using LayoutsAndCollections.Models;
using LayoutsAndCollections.Services;
using System.Collections.ObjectModel;

namespace LayoutsAndCollections.ViewModels;

/// <summary>
/// View model for responsive layout and data-bound collection examples.
/// </summary>
public partial class LayoutsCollectionsViewModel : ObservableObject
{
	private readonly ICollectionDataService _dataService;

	/// <summary>
	/// Collection items displayed by the CollectionView.
	/// </summary>
	[ObservableProperty]
	private ObservableCollection<CollectionItem> items = [];

	/// <summary>
	/// Status text shown above the form and list.
	/// </summary>
	[ObservableProperty]
	private string statusMessage = "Use the form to add items, then pull down to refresh.";

	/// <summary>
	/// Form title input bound to Grid-based form layout.
	/// </summary>
	[ObservableProperty]
	private string newItemTitle = string.Empty;

	/// <summary>
	/// Form description input bound to Editor.
	/// </summary>
	[ObservableProperty]
	private string newItemDescription = string.Empty;

	/// <summary>
	/// Selected category from Picker control.
	/// </summary>
	[ObservableProperty]
	private string selectedCategory = "General";

	/// <summary>
	/// Indicates pull-to-refresh state in RefreshView.
	/// </summary>
	[ObservableProperty]
	private bool isRefreshing;

	/// <summary>
	/// Guards list load operations.
	/// </summary>
	[ObservableProperty]
	private bool isBusy;

	/// <summary>
	/// Categories shown in Picker and item chips.
	/// </summary>
	public IReadOnlyList<string> Categories { get; } = ["General", "Grid", "StackLayout", "CollectionView", "RefreshView"];

	/// <summary>
	/// Initializes a new view model with injected collection data service.
	/// </summary>
	/// <param name="dataService">In-memory data provider for list and refresh operations.</param>
	public LayoutsCollectionsViewModel(ICollectionDataService dataService)
	{
		_dataService = dataService;
	}

	/// <summary>
	/// Loads initial item data for the CollectionView.
	/// </summary>
	[RelayCommand]
	private Task LoadAsync()
	{
		if (IsBusy)
		{
			return Task.CompletedTask;
		}

		try
		{
			IsBusy = true;
			Items = new ObservableCollection<CollectionItem>(_dataService.GetItems());
			StatusMessage = Items.Count == 0
				? "No items yet. Add one using the form below."
				: $"Loaded {Items.Count} layout examples.";
		}
		finally
		{
			IsBusy = false;
		}

		return Task.CompletedTask;
	}

	/// <summary>
	/// Reloads item data through RefreshView pull-to-refresh interaction.
	/// </summary>
	[RelayCommand]
	private async Task RefreshAsync()
	{
		if (IsRefreshing)
		{
			return;
		}

		try
		{
			IsRefreshing = true;
			Items = new ObservableCollection<CollectionItem>(await _dataService.RefreshAsync());
			StatusMessage = $"Refreshed at {DateTime.Now:HH:mm:ss}.";
		}
		catch (Exception ex)
		{
			StatusMessage = "Refresh failed. Try again.";
			System.Diagnostics.Debug.WriteLine(ex);
		}
		finally
		{
			IsRefreshing = false;
		}
	}

	/// <summary>
	/// Adds a new item using form values and updates the list.
	/// </summary>
	[RelayCommand]
	private Task AddItemAsync()
	{
		var item = _dataService.Add(NewItemTitle, NewItemDescription, SelectedCategory);
		Items.Insert(0, item);

		NewItemTitle = string.Empty;
		NewItemDescription = string.Empty;
		StatusMessage = $"Added: {item.Title}";

		return Task.CompletedTask;
	}

	/// <summary>
	/// Clears all list data to demonstrate EmptyView behavior.
	/// </summary>
	[RelayCommand]
	private Task ClearItemsAsync()
	{
		_dataService.Clear();
		Items.Clear();
		StatusMessage = "List cleared. EmptyView is now active.";

		return Task.CompletedTask;
	}
}
