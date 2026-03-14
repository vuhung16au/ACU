namespace HardwareAndPlatformAPIs.Models;

/// <summary>
/// Holds one GPS reading retrieved via the Geolocation API.
/// </summary>
public sealed class LocationReading
{
	/// <summary>
	/// Latitude in decimal degrees (positive = North).
	/// </summary>
	public double Latitude { get; init; }

	/// <summary>
	/// Longitude in decimal degrees (positive = East).
	/// </summary>
	public double Longitude { get; init; }

	/// <summary>
	/// Horizontal accuracy in metres, if the platform provides it.
	/// </summary>
	public double? AccuracyMetres { get; init; }

	/// <summary>
	/// Altitude in metres above sea level, if available.
	/// </summary>
	public double? AltitudeMetres { get; init; }

	/// <summary>
	/// When the fix was obtained.
	/// </summary>
	public DateTimeOffset Timestamp { get; init; } = DateTimeOffset.Now;
}
