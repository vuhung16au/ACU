using RazorPagesCICDDemo.Models;

namespace RazorPagesCICDDemo.Services;

/// <summary>
/// Supplies the CI/CD workflow stages explained in the lesson.
/// </summary>
public class WorkflowStageService
{
    /// <summary>
    /// Gets the ordered stages used by the GitHub Actions and Azure CLI lesson.
    /// </summary>
    /// <returns>An ordered list of workflow stages.</returns>
    public IReadOnlyList<WorkflowStage> GetStages()
    {
        return
        [
            new WorkflowStage(
                "Push to main",
                "A commit to the main branch starts the GitHub Actions workflow automatically.",
                "A fresh CI run is queued on GitHub-hosted infrastructure.",
                true),
            new WorkflowStage(
                "Restore and build",
                "The workflow runs the repo's established .NET build path so students can see that CI uses the same commands they use locally.",
                "All configured .NET teaching projects compile successfully.",
                true),
            new WorkflowStage(
                "Run unit tests",
                "The lesson keeps the test scope focused by running only the CI/CD demo tests.",
                "Fast feedback that the teaching sample still behaves correctly.",
                true),
            new WorkflowStage(
                "Publish artifact",
                "GitHub Actions packages the Razor Pages app output so students can inspect the handoff between build automation and deployment.",
                "A downloadable publish artifact for the demo app.",
                true),
            new WorkflowStage(
                "Deploy with Azure CLI",
                "Students use manual Azure CLI commands to understand the deployment flow and troubleshoot real failures before adding automation.",
                "A running Azure App Service instance using the published app package.",
                false)
        ];
    }
}
