using WeatherDashboard.Models;

namespace WeatherDashboard.Services;

/// <summary>
/// Generates local mock weather data so the dashboard works without an external API.
/// </summary>
public class WeatherSimulationService
{
    private static readonly string[] Conditions =
    [
        "Sunny",
        "Partly Cloudy",
        "Cloudy",
        "Light Rain",
        "Windy",
        "Storm Watch"
    ];

    private readonly object _syncRoot = new();
    private readonly Random _random = new();
    private readonly List<WeatherSnapshot> _snapshots;

    /// <summary>
    /// Initializes a new instance of the <see cref="WeatherSimulationService"/> class with sample city data.
    /// </summary>
    public WeatherSimulationService()
    {
        _snapshots =
        [
            new WeatherSnapshot
            {
                City = "Sydney",
                TemperatureCelsius = 25,
                Condition = "Sunny",
                HumidityPercent = 54,
                WindSpeedKph = 18,
                ObservedAt = DateTime.Now
            },
            new WeatherSnapshot
            {
                City = "Melbourne",
                TemperatureCelsius = 20,
                Condition = "Partly Cloudy",
                HumidityPercent = 61,
                WindSpeedKph = 24,
                ObservedAt = DateTime.Now
            },
            new WeatherSnapshot
            {
                City = "Brisbane",
                TemperatureCelsius = 27,
                Condition = "Sunny",
                HumidityPercent = 66,
                WindSpeedKph = 16,
                ObservedAt = DateTime.Now
            }
        ];
    }

    /// <summary>
    /// Returns a new simulated weather dashboard payload.
    /// </summary>
    /// <returns>A local weather dashboard response.</returns>
    public WeatherDashboardResponse GetDashboard()
    {
        lock (_syncRoot)
        {
            SimulateChanges();

            var refreshedAt = DateTime.Now;

            return new WeatherDashboardResponse
            {
                CurrentWeather = _snapshots
                    .Select(snapshot => new WeatherSnapshot
                    {
                        City = snapshot.City,
                        TemperatureCelsius = snapshot.TemperatureCelsius,
                        Condition = snapshot.Condition,
                        HumidityPercent = snapshot.HumidityPercent,
                        WindSpeedKph = snapshot.WindSpeedKph,
                        ObservedAt = refreshedAt
                    })
                    .ToList(),
                Forecast = BuildForecast(),
                StatusMessage = "Mock weather data refreshed locally. Values change slightly on each request.",
                RefreshedAt = refreshedAt
            };
        }
    }

    private void SimulateChanges()
    {
        foreach (var snapshot in _snapshots)
        {
            snapshot.TemperatureCelsius = Math.Clamp(snapshot.TemperatureCelsius + _random.Next(-2, 3), 12, 35);
            snapshot.HumidityPercent = Math.Clamp(snapshot.HumidityPercent + _random.Next(-4, 5), 35, 90);
            snapshot.WindSpeedKph = Math.Clamp(snapshot.WindSpeedKph + _random.Next(-3, 4), 5, 40);

            if (_random.NextDouble() > 0.55)
            {
                snapshot.Condition = Conditions[_random.Next(Conditions.Length)];
            }

            snapshot.ObservedAt = DateTime.Now;
        }
    }

    private List<ForecastDay> BuildForecast()
    {
        return
        [
            new ForecastDay
            {
                Day = "Today",
                Condition = Conditions[_random.Next(Conditions.Length)],
                MaxTemperatureCelsius = 28,
                MinTemperatureCelsius = 19
            },
            new ForecastDay
            {
                Day = "Tomorrow",
                Condition = Conditions[_random.Next(Conditions.Length)],
                MaxTemperatureCelsius = 27,
                MinTemperatureCelsius = 18
            },
            new ForecastDay
            {
                Day = "Next Day",
                Condition = Conditions[_random.Next(Conditions.Length)],
                MaxTemperatureCelsius = 26,
                MinTemperatureCelsius = 17
            }
        ];
    }
}
