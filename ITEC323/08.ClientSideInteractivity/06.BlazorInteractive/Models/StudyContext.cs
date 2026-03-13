namespace BlazorInteractive.Models;

public sealed class StudyContext
{
    public required string Cohort { get; init; }
    public required string FocusTopic { get; init; }
}
