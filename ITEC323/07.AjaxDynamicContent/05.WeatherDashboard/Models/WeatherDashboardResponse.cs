namespace WeatherDashboard.Models;

/// <summary>
/// Represents the full dashboard payload returned by the mock weather API.
/// </summary>
public class WeatherDashboardResponse
{
    /// <summary>
    /// Gets or sets the current weather snapshots.
    /// </summary>
    public List<WeatherSnapshot> CurrentWeather { get; set; } = [];

    /// <summary>
    /// Gets or sets the forecast list.
    /// </summary>
    public List<ForecastDay> Forecast { get; set; } = [];

    /// <summary>
    /// Gets or sets the dashboard status message.
    /// </summary>
    public string StatusMessage { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the last refresh time.
    /// </summary>
    public DateTime RefreshedAt { get; set; }
}
