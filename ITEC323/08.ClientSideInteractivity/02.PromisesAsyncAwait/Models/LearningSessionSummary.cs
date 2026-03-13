namespace PromisesAsyncAwait.Models;

/// <summary>
/// Represents summary numbers for the async dashboard cards.
/// </summary>
public class LearningSessionSummary
{
    /// <summary>
    /// Gets or sets the total number of sessions.
    /// </summary>
    public int TotalSessions { get; set; }

    /// <summary>
    /// Gets or sets the number of completed sessions.
    /// </summary>
    public int CompletedSessions { get; set; }

    /// <summary>
    /// Gets or sets the number of pending sessions.
    /// </summary>
    public int PendingSessions { get; set; }

    /// <summary>
    /// Gets or sets the average number of minutes studied.
    /// </summary>
    public double AverageMinutes { get; set; }

    /// <summary>
    /// Gets or sets the UTC timestamp when summary values were generated.
    /// </summary>
    public DateTime GeneratedAtUtc { get; set; }
}
