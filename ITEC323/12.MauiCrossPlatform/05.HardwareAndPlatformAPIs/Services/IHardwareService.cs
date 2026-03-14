using HardwareAndPlatformAPIs.Models;

namespace HardwareAndPlatformAPIs.Services;

/// <summary>
/// Provides cross-platform access to device hardware: camera, GPS, and file system.
/// </summary>
public interface IHardwareService
{
	/// <summary>
	/// Opens the device camera and returns metadata for the captured photo.
	/// Copies the image into the app's local data directory for reliable display.
	/// Returns <c>null</c> when the user cancels or the permission is denied.
	/// </summary>
	Task<PhotoResult?> TakePhotoAsync(CancellationToken cancellationToken = default);

	/// <summary>
	/// Opens the system photo gallery and returns metadata for the selected photo.
	/// Returns <c>null</c> when the user cancels or the permission is denied.
	/// </summary>
	Task<PhotoResult?> PickPhotoAsync(CancellationToken cancellationToken = default);

	/// <summary>
	/// Retrieves the current GPS location.
	/// Returns <c>null</c> if location permission is denied or unavailable.
	/// </summary>
	Task<LocationReading?> GetLocationAsync(CancellationToken cancellationToken = default);

	/// <summary>
	/// Writes <paramref name="content"/> to a named text file in the app's data directory.
	/// </summary>
	/// <param name="fileName">Relative file name (no path separators).</param>
	/// <param name="content">Text content to persist.</param>
	Task SaveFileAsync(string fileName, string content);

	/// <summary>
	/// Reads a text file previously written by <see cref="SaveFileAsync"/>.
	/// Returns <c>null</c> when the file does not exist.
	/// </summary>
	/// <param name="fileName">Relative file name used when saving.</param>
	Task<string?> LoadFileAsync(string fileName);
}
