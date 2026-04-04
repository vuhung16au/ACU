namespace _01.ReactWebApiDemo.Server.Models;

/// <summary>
/// Describes a backend route that the React app can call.
/// </summary>
public class ApiRouteInfo
{
    /// <summary>
    /// Gets or sets the HTTP method used by the route.
    /// </summary>
    public string HttpMethod { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the route path.
    /// </summary>
    public string Route { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets a short explanation of why the route exists.
    /// </summary>
    public string Purpose { get; set; } = string.Empty;
}
