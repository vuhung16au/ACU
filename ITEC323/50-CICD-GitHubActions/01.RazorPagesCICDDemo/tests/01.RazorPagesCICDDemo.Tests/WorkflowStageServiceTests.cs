using FluentAssertions;
using RazorPagesCICDDemo.Services;

namespace RazorPagesCICDDemo.Tests;

/// <summary>
/// Tests for the <see cref="WorkflowStageService"/> class.
/// </summary>
public class WorkflowStageServiceTests
{
    private readonly WorkflowStageService _service = new();

    /// <summary>
    /// Verifies that the workflow stages appear in the teaching order.
    /// </summary>
    [Fact]
    public void GetStages_DefaultWorkflow_ReturnsExpectedTeachingSequence()
    {
        // Arrange

        // Act
        var stages = _service.GetStages();

        // Assert
        stages.Select(stage => stage.Name).Should().ContainInOrder(
            "Push to main",
            "Restore and build",
            "Run unit tests",
            "Publish artifact",
            "Deploy with Azure CLI");
    }

    /// <summary>
    /// Verifies that only the final lesson stage is kept manual.
    /// </summary>
    [Fact]
    public void GetStages_LessonBoundary_LeavesOnlyDeploymentManual()
    {
        // Arrange

        // Act
        var manualStages = _service.GetStages().Where(stage => !stage.IsAutomated).ToList();

        // Assert
        manualStages.Should().ContainSingle();
        manualStages[0].Name.Should().Be("Deploy with Azure CLI");
    }
}
