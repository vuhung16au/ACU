namespace RazorPagesCICDDemo.Models;

/// <summary>
/// Represents one stage in the lesson's CI/CD workflow.
/// </summary>
/// <param name="Name">The stage name shown in the UI.</param>
/// <param name="Description">The teaching explanation for the stage.</param>
/// <param name="Output">The output students should expect from the stage.</param>
/// <param name="IsAutomated">Indicates whether GitHub Actions performs the stage automatically.</param>
public sealed record WorkflowStage(string Name, string Description, string Output, bool IsAutomated);
