namespace PromisesAsyncAwait.Models;

/// <summary>
/// Represents the request payload for creating a new learning session.
/// </summary>
public class CreateLearningSessionRequest
{
    /// <summary>
    /// Gets or sets the student display name.
    /// </summary>
    public string StudentName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the topic being studied.
    /// </summary>
    public string Topic { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the number of minutes studied.
    /// </summary>
    public int MinutesStudied { get; set; }

    /// <summary>
    /// Gets or sets a value indicating whether the session is complete.
    /// </summary>
    public bool IsCompleted { get; set; }
}
