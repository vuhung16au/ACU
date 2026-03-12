using Microsoft.AspNetCore.Mvc;
using WeatherDashboard.Models;
using WeatherDashboard.Services;

namespace WeatherDashboard.Controllers;

/// <summary>
/// Provides local mock weather data for the dashboard.
/// </summary>
[ApiController]
[Route("api/[controller]")]
public class WeatherDashboardController : ControllerBase
{
    private readonly WeatherSimulationService _weatherSimulationService;

    /// <summary>
    /// Initializes a new instance of the <see cref="WeatherDashboardController"/> class.
    /// </summary>
    /// <param name="weatherSimulationService">The local weather simulation service.</param>
    public WeatherDashboardController(WeatherSimulationService weatherSimulationService)
    {
        _weatherSimulationService = weatherSimulationService;
    }

    /// <summary>
    /// Returns the full weather dashboard payload.
    /// </summary>
    /// <returns>Current weather, forecast, and refresh information.</returns>
    [HttpGet]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public ActionResult<WeatherDashboardResponse> GetDashboard()
    {
        return Ok(_weatherSimulationService.GetDashboard());
    }
}
