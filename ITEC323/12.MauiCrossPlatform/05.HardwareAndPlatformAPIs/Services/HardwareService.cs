using HardwareAndPlatformAPIs.Models;

namespace HardwareAndPlatformAPIs.Services;

/// <summary>
/// Wraps MAUI Essentials APIs — MediaPicker, Geolocation, and FileSystem — behind a single
/// testable interface so ViewModels remain platform-agnostic.
/// </summary>
public sealed class HardwareService : IHardwareService
{
	// -------------------------------------------------------------------------
	// Camera / Photo

	/// <inheritdoc/>
	public async Task<PhotoResult?> TakePhotoAsync(CancellationToken cancellationToken = default)
	{
		// Check whether the device has a camera before requesting the permission.
		if (!MediaPicker.Default.IsCaptureSupported)
		{
			return null;
		}

		try
		{
			// CapturePhotoAsync shows the native camera UI and returns a FileResult.
			var fileResult = await MediaPicker.Default.CapturePhotoAsync();
			return fileResult is null ? null : await CopyToAppDataAsync(fileResult);
		}
		catch (PermissionException)
		{
			// User denied the camera permission — return null so the VM shows feedback.
			return null;
		}
		catch (OperationCanceledException)
		{
			return null;
		}
	}

	/// <inheritdoc/>
	public async Task<PhotoResult?> PickPhotoAsync(CancellationToken cancellationToken = default)
	{
		try
		{
			// PickPhotosAsync is the current API; take the first selected photo.
			var fileResults = await MediaPicker.Default.PickPhotosAsync();
			var fileResult = fileResults?.FirstOrDefault();
			return fileResult is null ? null : await CopyToAppDataAsync(fileResult);
		}
		catch (PermissionException)
		{
			return null;
		}
		catch (OperationCanceledException)
		{
			return null;
		}
	}

	/// <summary>
	/// Copies a MediaPicker <see cref="FileResult"/> into AppDataDirectory so the
	/// image is accessible on all platforms without extra file-provider config.
	/// </summary>
	private static async Task<PhotoResult> CopyToAppDataAsync(FileResult fileResult)
	{
		// Build a unique destination path inside the app's sandboxed data directory.
		var destFileName = $"photo_{DateTime.Now:yyyyMMdd_HHmmss}{Path.GetExtension(fileResult.FileName)}";
		var destPath = Path.Combine(FileSystem.AppDataDirectory, destFileName);

		// Copy the source stream to the destination file.
		await using var sourceStream = await fileResult.OpenReadAsync();
		await using var destStream = File.OpenWrite(destPath);
		await sourceStream.CopyToAsync(destStream);

		return new PhotoResult
		{
			FilePath = destPath,
			FileName = fileResult.FileName,
			CapturedAt = DateTime.Now
		};
	}

	// -------------------------------------------------------------------------
	// Geolocation

	/// <inheritdoc/>
	public async Task<LocationReading?> GetLocationAsync(CancellationToken cancellationToken = default)
	{
		try
		{
			// GeolocationRequest specifies the desired accuracy level.
			// Medium accuracy balances battery use with precision (~ 100 m).
			var request = new GeolocationRequest(GeolocationAccuracy.Medium, TimeSpan.FromSeconds(10));
			var location = await Geolocation.Default.GetLocationAsync(request, cancellationToken);

			if (location is null)
			{
				return null;
			}

			return new LocationReading
			{
				Latitude = location.Latitude,
				Longitude = location.Longitude,
				AccuracyMetres = location.Accuracy,
				AltitudeMetres = location.Altitude,
				Timestamp = location.Timestamp
			};
		}
		catch (PermissionException)
		{
			// Location permission denied — surface as null so the VM can show feedback.
			return null;
		}
		catch (FeatureNotSupportedException)
		{
			// No GPS hardware (e.g., desktop Mac without location services).
			return null;
		}
		catch (FeatureNotEnabledException)
		{
			// Location services turned off at the OS level.
			return null;
		}
		catch (OperationCanceledException)
		{
			return null;
		}
	}

	// -------------------------------------------------------------------------
	// File System

	/// <inheritdoc/>
	public async Task SaveFileAsync(string fileName, string content)
	{
		// FileSystem.AppDataDirectory is the platform's sandboxed private storage.
		// On Android: /data/data/<package>/files/
		// On iOS/Mac: <App Container>/Library/Application Support/
		var path = Path.Combine(FileSystem.AppDataDirectory, SanitizeFileName(fileName));
		await File.WriteAllTextAsync(path, content);
	}

	/// <inheritdoc/>
	public async Task<string?> LoadFileAsync(string fileName)
	{
		var path = Path.Combine(FileSystem.AppDataDirectory, SanitizeFileName(fileName));
		if (!File.Exists(path))
		{
			return null;
		}

		return await File.ReadAllTextAsync(path);
	}

	/// <summary>
	/// Strips path traversal characters to prevent accidental directory escapes.
	/// Only keeps the file name portion so callers cannot write outside AppData.
	/// </summary>
	private static string SanitizeFileName(string fileName)
	{
		return Path.GetFileName(fileName.Trim());
	}
}
