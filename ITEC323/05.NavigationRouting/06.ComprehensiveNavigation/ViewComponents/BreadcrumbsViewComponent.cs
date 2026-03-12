using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Http;

namespace ComprehensiveNavigation.ViewComponents;

/// <summary>
/// Renders breadcrumbs automatically based on the current request path.
/// </summary>
public class BreadcrumbsViewComponent : ViewComponent
{
    public IViewComponentResult Invoke()
    {
        var breadcrumbs = new List<(string Title, string Url)>();
        breadcrumbs.Add(("Home", "/"));

        var path = HttpContext.Request.Path.Value;
        if (!string.IsNullOrEmpty(path) && path != "/")
        {
            var segments = path.Split(new[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
            var currentUrl = "";

            for (int i = 0; i < segments.Length; i++)
            {
                currentUrl += "/" + segments[i];
                // For the last segment, we don't need a clickable URL usually, but we provide it anyway
                string title = NormalizeTitle(segments[i]);
                breadcrumbs.Add((title, currentUrl));
            }
        }

        return View(breadcrumbs);
    }

    private string NormalizeTitle(string segment)
    {
        // simplistic title normalization (e.g. "GettingStarted" -> "Getting Started")
        // here we just return the segment as is for brevity, or we can use regex
        return System.Text.RegularExpressions.Regex.Replace(segment, "([a-z])([A-Z])", "$1 $2");
    }
}
