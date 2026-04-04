using Microsoft.AspNetCore.Mvc.RazorPages;
using RazorPagesCICDDemo.Models;
using RazorPagesCICDDemo.Services;

namespace RazorPagesCICDDemo.Pages;

/// <summary>
/// Displays common Azure deployment troubleshooting tips for the lesson.
/// </summary>
public class PrivacyModel : PageModel
{
    private readonly AzureCliDeploymentGuideService _deploymentGuideService;

    /// <summary>
    /// Initializes a new instance of the <see cref="PrivacyModel"/> class.
    /// </summary>
    /// <param name="deploymentGuideService">Provides troubleshooting notes for manual deployment.</param>
    public PrivacyModel(AzureCliDeploymentGuideService deploymentGuideService)
    {
        _deploymentGuideService = deploymentGuideService;
    }

    /// <summary>
    /// Gets the troubleshooting tips shown on the page.
    /// </summary>
    public IReadOnlyList<DeploymentTroubleshootingTip> TroubleshootingTips { get; private set; } = [];

    /// <summary>
    /// Loads the troubleshooting tips for display.
    /// </summary>
    public void OnGet()
    {
        TroubleshootingTips = _deploymentGuideService.GetTroubleshootingTips();
    }
}
