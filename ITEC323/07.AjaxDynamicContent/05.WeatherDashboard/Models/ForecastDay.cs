namespace WeatherDashboard.Models;

/// <summary>
/// Represents one forecast item in the mock weather dashboard.
/// </summary>
public class ForecastDay
{
    /// <summary>
    /// Gets or sets the day label.
    /// </summary>
    public string Day { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the predicted weather condition.
    /// </summary>
    public string Condition { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the maximum temperature.
    /// </summary>
    public int MaxTemperatureCelsius { get; set; }

    /// <summary>
    /// Gets or sets the minimum temperature.
    /// </summary>
    public int MinTemperatureCelsius { get; set; }
}
