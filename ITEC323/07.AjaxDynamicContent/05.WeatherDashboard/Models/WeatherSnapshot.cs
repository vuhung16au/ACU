namespace WeatherDashboard.Models;

/// <summary>
/// Represents the weather data for one city at one point in time.
/// </summary>
public class WeatherSnapshot
{
    /// <summary>
    /// Gets or sets the city name.
    /// </summary>
    public string City { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the current temperature in degrees Celsius.
    /// </summary>
    public int TemperatureCelsius { get; set; }

    /// <summary>
    /// Gets or sets the weather condition summary.
    /// </summary>
    public string Condition { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the humidity percentage.
    /// </summary>
    public int HumidityPercent { get; set; }

    /// <summary>
    /// Gets or sets the wind speed in kilometres per hour.
    /// </summary>
    public int WindSpeedKph { get; set; }

    /// <summary>
    /// Gets or sets the time the snapshot was generated.
    /// </summary>
    public DateTime ObservedAt { get; set; }
}
