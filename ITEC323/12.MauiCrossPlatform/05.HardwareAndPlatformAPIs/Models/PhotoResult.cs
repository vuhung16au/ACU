namespace HardwareAndPlatformAPIs.Models;

/// <summary>
/// Holds metadata for a photo captured via MediaPicker.
/// </summary>
public sealed class PhotoResult
{
	/// <summary>
	/// Absolute path to the photo file copied into the app's local data directory.
	/// </summary>
	public string FilePath { get; init; } = string.Empty;

	/// <summary>
	/// Original file name returned by MediaPicker.
	/// </summary>
	public string FileName { get; init; } = string.Empty;

	/// <summary>
	/// Timestamp when the photo was captured or picked.
	/// </summary>
	public DateTime CapturedAt { get; init; } = DateTime.Now;
}
