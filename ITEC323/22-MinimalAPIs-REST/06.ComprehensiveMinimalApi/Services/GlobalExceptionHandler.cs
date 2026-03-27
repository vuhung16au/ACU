using Microsoft.AspNetCore.Diagnostics;

namespace ComprehensiveMinimalApiDemo.Services;

/// <summary>
/// Handles unexpected exceptions with a safe JSON response.
/// </summary>
public class GlobalExceptionHandler : IExceptionHandler
{
    /// <summary>
    /// Attempts to handle the current exception.
    /// </summary>
    /// <param name="httpContext">The current HTTP context.</param>
    /// <param name="exception">The thrown exception.</param>
    /// <param name="cancellationToken">The cancellation token.</param>
    /// <returns>A task that returns <see langword="true"/> when the exception is handled.</returns>
    public async ValueTask<bool> TryHandleAsync(HttpContext httpContext, Exception exception, CancellationToken cancellationToken)
    {
        httpContext.Response.StatusCode = StatusCodes.Status500InternalServerError;
        await httpContext.Response.WriteAsJsonAsync(new
        {
            error = "An unexpected server error occurred."
        }, cancellationToken);

        return true;
    }
}
