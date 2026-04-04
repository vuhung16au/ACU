namespace RazorPagesCICDDemo.Models;

/// <summary>
/// Represents one beginner-friendly deployment troubleshooting note.
/// </summary>
/// <param name="Issue">The issue title shown on the troubleshooting page.</param>
/// <param name="Symptom">The student-facing symptom that suggests this issue.</param>
/// <param name="Resolution">The recommended next step to resolve the issue.</param>
public sealed record DeploymentTroubleshootingTip(string Issue, string Symptom, string Resolution);
