using Microsoft.AspNetCore.Diagnostics;

namespace ErrorHandlingAndStatusCodesDemo.Services;

/// <summary>
/// Converts unexpected exceptions into a safe problem details response.
/// </summary>
public class GlobalExceptionHandler : IExceptionHandler
{
    /// <summary>
    /// Attempts to handle the current exception.
    /// </summary>
    /// <param name="httpContext">The current HTTP context.</param>
    /// <param name="exception">The exception that was thrown.</param>
    /// <param name="cancellationToken">The request cancellation token.</param>
    /// <returns>A task that returns <see langword="true"/> when the exception was handled.</returns>
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
