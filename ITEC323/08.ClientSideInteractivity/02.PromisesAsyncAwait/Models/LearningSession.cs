namespace PromisesAsyncAwait.Models;

/// <summary>
/// Represents one learning session row returned by the local JSON API.
/// </summary>
public class LearningSession
{
    /// <summary>
    /// Gets or sets the unique identifier of the learning session.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the student display name.
    /// </summary>
    public string StudentName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the topic that the student studied.
    /// </summary>
    public string Topic { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the number of minutes spent on this session.
    /// </summary>
    public int MinutesStudied { get; set; }

    /// <summary>
    /// Gets or sets a value indicating whether the student finished the task.
    /// </summary>
    public bool IsCompleted { get; set; }

    /// <summary>
    /// Gets or sets the UTC timestamp of the latest update.
    /// </summary>
    public DateTime LastUpdatedUtc { get; set; }
}
