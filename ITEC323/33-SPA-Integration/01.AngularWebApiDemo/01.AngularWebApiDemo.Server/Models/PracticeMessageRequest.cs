namespace _01.AngularWebApiDemo.Server.Models;

/// <summary>
/// Represents the learner input sent from the Angular form to the backend.
/// </summary>
public class PracticeMessageRequest
{
    /// <summary>
    /// Gets or sets the learner name to personalize the response.
    /// </summary>
    public string StudentName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the topic the learner is currently exploring.
    /// </summary>
    public string CurrentTopic { get; set; } = string.Empty;
}
