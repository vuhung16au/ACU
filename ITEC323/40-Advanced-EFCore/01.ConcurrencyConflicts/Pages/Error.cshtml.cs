using System.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ConcurrencyConflicts.Pages;

/// <summary>
/// Displays error information for unexpected failures.
/// </summary>
[ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
public class ErrorModel : PageModel
{
    private readonly ILogger<ErrorModel> _logger;

    /// <summary>
    /// Initializes a new instance of the <see cref="ErrorModel"/> class.
    /// </summary>
    /// <param name="logger">The logger.</param>
    public ErrorModel(ILogger<ErrorModel> logger)
    {
        _logger = logger;
    }

    /// <summary>
    /// Gets the request identifier.
    /// </summary>
    public string? RequestId { get; set; }

    /// <summary>
    /// Gets a value indicating whether the request ID should be displayed.
    /// </summary>
    public bool ShowRequestId => !string.IsNullOrEmpty(RequestId);

    /// <summary>
    /// Handles the GET request.
    /// </summary>
    public void OnGet()
    {
        RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier;
        _logger.LogWarning("The error page was displayed for request {RequestId}.", RequestId);
    }
}
