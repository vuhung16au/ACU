namespace MvvmDependencyInjection.Models;

/// <summary>
/// Represents the latest counter state shown in the UI.
/// </summary>
/// <param name="LearnerName">Name entered by the learner.</param>
/// <param name="ClickCount">Current click count.</param>
/// <param name="UpdatedAt">Timestamp of the latest update.</param>
public sealed record CounterSnapshot(string LearnerName, int ClickCount, DateTime UpdatedAt);
