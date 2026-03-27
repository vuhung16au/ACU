using FluentAssertions;
using RequestBodiesAndValidationDemo.Models;
using RequestBodiesAndValidationDemo.Services;
using Xunit;

namespace RequestBodiesAndValidation.Tests.Services;

/// <summary>
/// Tests task creation response shaping.
/// </summary>
public class TaskCreationServiceTests
{
    [Fact]
    public void Create_ValidRequest_ReturnsCreatedTask()
    {
        // Arrange
        var service = new TaskCreationService();
        var request = new CreateTaskRequest
        {
            Title = "  Build a POST endpoint  ",
            Description = "  Keep the sample short.  ",
            Priority = "High"
        };

        // Act
        var result = service.Create(request);

        // Assert
        result.Id.Should().Be(0);
        result.Title.Should().Be("Build a POST endpoint");
        result.Description.Should().Be("Keep the sample short.");
        result.Priority.Should().Be("High");
        result.IsCompleted.Should().BeFalse();
    }

    [Fact]
    public void CreateUpdatedTask_ValidRequest_ReturnsUpdatedTask()
    {
        // Arrange
        var service = new TaskCreationService();
        var request = new UpdateTaskRequest
        {
            Title = "  Update a task  ",
            Description = "  Save the new values.  ",
            Priority = "medium",
            IsCompleted = true
        };

        // Act
        var result = service.CreateUpdatedTask(4, request);

        // Assert
        result.Id.Should().Be(4);
        result.Title.Should().Be("Update a task");
        result.Description.Should().Be("Save the new values.");
        result.Priority.Should().Be("Medium");
        result.IsCompleted.Should().BeTrue();
    }
}
