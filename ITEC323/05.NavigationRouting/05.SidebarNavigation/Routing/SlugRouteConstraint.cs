using System.Globalization;
using System.Text.RegularExpressions;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Routing;

namespace SidebarNavigation.Routing;

/// <summary>
/// Validates that a route value looks like a simple blog slug.
/// Allowed characters are lowercase letters, numbers, and hyphens.
/// </summary>
public sealed partial class SlugRouteConstraint : IRouteConstraint
{
    /// <summary>
    /// Checks whether the route value matches the allowed slug format.
    /// </summary>
    /// <param name="httpContext">The current HTTP context.</param>
    /// <param name="route">The current router.</param>
    /// <param name="routeKey">The route key to validate.</param>
    /// <param name="values">The available route values.</param>
    /// <param name="routeDirection">The direction of route processing.</param>
    /// <returns><see langword="true" /> when the slug is valid; otherwise <see langword="false" />.</returns>
    public bool Match(
        HttpContext? httpContext,
        IRouter? route,
        string routeKey,
        RouteValueDictionary values,
        RouteDirection routeDirection)
    {
        if (!values.TryGetValue(routeKey, out var routeValue) || routeValue is null)
        {
            return false;
        }

        var slug = Convert.ToString(routeValue, CultureInfo.InvariantCulture) ?? string.Empty;
        return SlugPattern().IsMatch(slug);
    }

    [GeneratedRegex("^[a-z0-9]+(?:-[a-z0-9]+)*$")]
    private static partial Regex SlugPattern();
}