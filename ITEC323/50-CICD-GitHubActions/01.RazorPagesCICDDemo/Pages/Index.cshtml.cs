using Microsoft.AspNetCore.Mvc.RazorPages;
using RazorPagesCICDDemo.Models;
using RazorPagesCICDDemo.Services;

namespace RazorPagesCICDDemo.Pages;

/// <summary>
/// Displays the workflow stages and manual deployment steps for the CI/CD lesson.
/// </summary>
public class IndexModel : PageModel
{
    private readonly AzureCliDeploymentGuideService _deploymentGuideService;
    private readonly WorkflowStageService _workflowStageService;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="workflowStageService">Provides workflow stage information for the lesson.</param>
    /// <param name="deploymentGuideService">Provides manual Azure deployment commands.</param>
    public IndexModel(
        WorkflowStageService workflowStageService,
        AzureCliDeploymentGuideService deploymentGuideService)
    {
        _workflowStageService = workflowStageService;
        _deploymentGuideService = deploymentGuideService;
    }

    /// <summary>
    /// Gets the workflow stages shown on the page.
    /// </summary>
    public IReadOnlyList<WorkflowStage> WorkflowStages { get; private set; } = [];

    /// <summary>
    /// Gets the Azure CLI deployment steps shown on the page.
    /// </summary>
    public IReadOnlyList<AzureCliCommandStep> DeploymentSteps { get; private set; } = [];

    /// <summary>
    /// Gets the count of automated workflow stages.
    /// </summary>
    public int AutomatedStageCount { get; private set; }

    /// <summary>
    /// Gets the count of manual workflow stages.
    /// </summary>
    public int ManualStageCount { get; private set; }

    /// <summary>
    /// Loads the workflow and deployment data for display.
    /// </summary>
    public void OnGet()
    {
        WorkflowStages = _workflowStageService.GetStages();
        DeploymentSteps = _deploymentGuideService.GetDeploymentSteps();
        AutomatedStageCount = WorkflowStages.Count(stage => stage.IsAutomated);
        ManualStageCount = WorkflowStages.Count(stage => !stage.IsAutomated);
    }
}
