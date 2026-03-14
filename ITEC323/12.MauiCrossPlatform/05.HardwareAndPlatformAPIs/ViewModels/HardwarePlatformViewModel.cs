using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using HardwareAndPlatformAPIs.Services;

namespace HardwareAndPlatformAPIs.ViewModels;

/// <summary>
/// View model for the Hardware and Platform APIs demo page.
/// Manages state and commands for camera capture, geolocation, and file-system operations.
/// </summary>
public partial class HardwarePlatformViewModel : ObservableObject
{
	private readonly IHardwareService _hardware;

	// -------------------------------------------------------------------------
	// Shared state

	/// <summary>
	/// General status line shown at the top of the page.
	/// </summary>
	[ObservableProperty]
	private string statusMessage = "Tap a button in any section to try a hardware API.";

	/// <summary>
	/// Guards all sections while an async operation is running.
	/// </summary>
	[ObservableProperty]
	[NotifyPropertyChangedFor(nameof(IsIdle))]
	private bool isBusy;

	/// <summary>
	/// True when no operation is in progress — used for button visibility.
	/// </summary>
	public bool IsIdle => !IsBusy;

	// -------------------------------------------------------------------------
	// Camera / Photo section

	/// <summary>
	/// Absolute path to the most recently captured or picked photo.
	/// Bound to an <c>ImageSource</c> converter in XAML.
	/// Null until the user captures or picks a photo.
	/// </summary>
	[ObservableProperty]
	[NotifyPropertyChangedFor(nameof(HasPhoto))]
	private string? capturedPhotoPath;

	/// <summary>
	/// Display name of the captured photo file.
	/// </summary>
	[ObservableProperty]
	private string photoStatus = "No photo yet.";

	/// <summary>
	/// True when a photo path is available — controls Image visibility.
	/// </summary>
	public bool HasPhoto => CapturedPhotoPath is not null;

	// -------------------------------------------------------------------------
	// Location section

	/// <summary>
	/// Human-readable GPS coordinates shown in the Location card.
	/// </summary>
	[ObservableProperty]
	private string locationStatus = "Location not read yet.";

	/// <summary>
	/// Latitude from the last successful geolocation read.
	/// </summary>
	[ObservableProperty]
	private double? latitude;

	/// <summary>
	/// Longitude from the last successful geolocation read.
	/// </summary>
	[ObservableProperty]
	private double? longitude;

	/// <summary>
	/// Horizontal accuracy in metres from the last fix.
	/// </summary>
	[ObservableProperty]
	private double? accuracyMetres;

	// -------------------------------------------------------------------------
	// File System section

	/// <summary>
	/// Text the user types in the Editor before saving to a file.
	/// </summary>
	[ObservableProperty]
	private string fileContent = string.Empty;

	/// <summary>
	/// Status line for the file-system card (saved path, load result, errors).
	/// </summary>
	[ObservableProperty]
	private string fileStatus = "Enter text above, then save or load.";

	// -------------------------------------------------------------------------
	// Constructor

	/// <summary>
	/// Initialises the view model with an injected hardware service.
	/// </summary>
	/// <param name="hardware">Platform hardware abstraction registered in DI.</param>
	public HardwarePlatformViewModel(IHardwareService hardware)
	{
		_hardware = hardware;
	}

	// -------------------------------------------------------------------------
	// Camera commands

	/// <summary>
	/// Opens the device camera. Updates <see cref="CapturedPhotoPath"/> on success.
	/// </summary>
	[RelayCommand(CanExecute = nameof(IsIdle))]
	private async Task TakePhotoAsync()
	{
		IsBusy = true;
		PhotoStatus = "Opening camera…";
		try
		{
			var result = await _hardware.TakePhotoAsync();
			if (result is null)
			{
				PhotoStatus = "Camera cancelled or permission denied.";
				StatusMessage = "Camera access was not granted or was cancelled.";
				return;
			}

			CapturedPhotoPath = result.FilePath;
			PhotoStatus = $"Captured: {result.FileName} at {result.CapturedAt:HH:mm:ss}";
			StatusMessage = "Photo captured successfully!";
		}
		catch (Exception ex)
		{
			PhotoStatus = $"Error: {ex.Message}";
			StatusMessage = "An error occurred while capturing the photo.";
		}
		finally
		{
			IsBusy = false;
		}
	}

	/// <summary>
	/// Opens the system photo gallery. Updates <see cref="CapturedPhotoPath"/> on success.
	/// </summary>
	[RelayCommand(CanExecute = nameof(IsIdle))]
	private async Task PickPhotoAsync()
	{
		IsBusy = true;
		PhotoStatus = "Opening gallery…";
		try
		{
			var result = await _hardware.PickPhotoAsync();
			if (result is null)
			{
				PhotoStatus = "Gallery cancelled or permission denied.";
				StatusMessage = "Photo library access was not granted or was cancelled.";
				return;
			}

			CapturedPhotoPath = result.FilePath;
			PhotoStatus = $"Picked: {result.FileName} at {result.CapturedAt:HH:mm:ss}";
			StatusMessage = "Photo picked from gallery!";
		}
		catch (Exception ex)
		{
			PhotoStatus = $"Error: {ex.Message}";
			StatusMessage = "An error occurred while picking the photo.";
		}
		finally
		{
			IsBusy = false;
		}
	}

	// -------------------------------------------------------------------------
	// Location command

	/// <summary>
	/// Requests the current GPS fix and updates the location properties.
	/// </summary>
	[RelayCommand(CanExecute = nameof(IsIdle))]
	private async Task GetLocationAsync()
	{
		IsBusy = true;
		LocationStatus = "Requesting GPS fix…";
		Latitude = null;
		Longitude = null;
		AccuracyMetres = null;
		try
		{
			var reading = await _hardware.GetLocationAsync();
			if (reading is null)
			{
				LocationStatus = "Location unavailable — check permissions or Location Services.";
				StatusMessage = "Could not get location. Permission may be denied.";
				return;
			}

			Latitude = reading.Latitude;
			Longitude = reading.Longitude;
			AccuracyMetres = reading.AccuracyMetres;

			var accuracyText = reading.AccuracyMetres.HasValue
				? $" ±{reading.AccuracyMetres:F0} m"
				: string.Empty;
			LocationStatus = $"Lat: {reading.Latitude:F6}  Lon: {reading.Longitude:F6}{accuracyText}";
			StatusMessage = $"Location retrieved at {reading.Timestamp:HH:mm:ss}.";
		}
		catch (Exception ex)
		{
			LocationStatus = $"Error: {ex.Message}";
			StatusMessage = "An error occurred while reading the location.";
		}
		finally
		{
			IsBusy = false;
		}
	}

	// -------------------------------------------------------------------------
	// File system commands

	/// <summary>
	/// Saves <see cref="FileContent"/> to a fixed file name in AppDataDirectory.
	/// </summary>
	[RelayCommand(CanExecute = nameof(IsIdle))]
	private async Task SaveFileAsync()
	{
		if (string.IsNullOrWhiteSpace(FileContent))
		{
			FileStatus = "Nothing to save — type some text first.";
			return;
		}

		IsBusy = true;
		try
		{
			const string fileName = "demo_note.txt";
			await _hardware.SaveFileAsync(fileName, FileContent);
			FileStatus = $"Saved to: {fileName}  ({FileContent.Length} chars)";
			StatusMessage = "File saved to app data directory.";
		}
		catch (Exception ex)
		{
			FileStatus = $"Save failed: {ex.Message}";
		}
		finally
		{
			IsBusy = false;
		}
	}

	/// <summary>
	/// Loads the previously saved file and puts its content into <see cref="FileContent"/>.
	/// </summary>
	[RelayCommand(CanExecute = nameof(IsIdle))]
	private async Task LoadFileAsync()
	{
		IsBusy = true;
		FileStatus = "Loading…";
		try
		{
			const string fileName = "demo_note.txt";
			var content = await _hardware.LoadFileAsync(fileName);
			if (content is null)
			{
				FileStatus = "File not found — save something first.";
				StatusMessage = "No saved file found.";
				return;
			}

			FileContent = content;
			FileStatus = $"Loaded from: {fileName}  ({content.Length} chars)";
			StatusMessage = "File loaded from app data directory.";
		}
		catch (Exception ex)
		{
			FileStatus = $"Load failed: {ex.Message}";
		}
		finally
		{
			IsBusy = false;
		}
	}
}
