using FluentAssertions;
using RazorPagesCICDDemo.Services;

namespace RazorPagesCICDDemo.Tests;

/// <summary>
/// Tests for the <see cref="AzureCliDeploymentGuideService"/> class.
/// </summary>
public class AzureCliDeploymentGuideServiceTests
{
    private readonly AzureCliDeploymentGuideService _service = new();

    /// <summary>
    /// Verifies that the deployment guide includes the zip deploy command used in the lesson.
    /// </summary>
    [Fact]
    public void GetDeploymentSteps_ManualDeployment_ReturnsZipDeployCommand()
    {
        // Arrange

        // Act
        var commands = _service.GetDeploymentSteps().Select(step => step.Command).ToList();

        // Assert
        commands.Should().Contain(command => command.Contains("az webapp deploy", StringComparison.Ordinal));
    }

    /// <summary>
    /// Verifies that the troubleshooting guide explains runtime stack problems.
    /// </summary>
    [Fact]
    public void GetTroubleshootingTips_CommonIssues_ContainsWrongRuntimeGuidance()
    {
        // Arrange

        // Act
        var tips = _service.GetTroubleshootingTips();

        // Assert
        tips.Should().Contain(tip => tip.Issue == "Wrong runtime stack");
    }
}
