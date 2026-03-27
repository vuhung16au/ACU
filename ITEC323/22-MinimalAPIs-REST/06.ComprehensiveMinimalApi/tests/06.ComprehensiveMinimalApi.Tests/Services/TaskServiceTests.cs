using ComprehensiveMinimalApiDemo.Models;
using ComprehensiveMinimalApiDemo.Services;
using FluentAssertions;
using Xunit;

namespace ComprehensiveMinimalApi.Tests.Services;

/// <summary>
/// Tests the combined task service behavior.
/// </summary>
public class TaskServiceTests
{
    [Fact]
    public void Search_HighPriorityIncompleteTasks_ReturnsMatchingTasks()
    {
        // Arrange
        var service = new TaskService(new InMemoryTaskRepository());

        // Act
        var result = service.Search("High", false);

        // Assert
        result.Should().ContainSingle();
        result[0].Title.Should().Be("Plan the final demo");
    }

    [Fact]
    public void Create_ValidRequest_ReturnsCreatedTask()
    {
        // Arrange
        var service = new TaskService(new InMemoryTaskRepository());
        var request = new CreateTaskRequest
        {
            Title = "Record curl examples",
            Description = "Use realistic sample data.",
            Priority = "Low"
        };

        // Act
        var result = service.Create(request);

        // Assert
        result.Id.Should().Be(3);
        result.IsCompleted.Should().BeFalse();
    }

    [Fact]
    public void Create_DuplicateTitle_ThrowsDuplicateTaskTitleException()
    {
        // Arrange
        var service = new TaskService(new InMemoryTaskRepository());

        // Act
        Action action = () => service.Create(new CreateTaskRequest
        {
            Title = "Plan the final demo",
            Description = "Duplicate",
            Priority = "High"
        });

        // Assert
        action.Should().Throw<DuplicateTaskTitleException>();
    }

    [Fact]
    public void Update_UnknownId_ThrowsTaskNotFoundException()
    {
        // Arrange
        var service = new TaskService(new InMemoryTaskRepository());
        var request = new UpdateTaskRequest
        {
            Title = "Unknown",
            Description = "Missing",
            Priority = "Low",
            IsCompleted = false
        };

        // Act
        Action action = () => service.Update(99, request);

        // Assert
        action.Should().Throw<TaskNotFoundException>();
    }

    [Fact]
    public void GetSummary_DefaultData_ReturnsCounts()
    {
        // Arrange
        var service = new TaskService(new InMemoryTaskRepository());

        // Act
        var result = service.GetSummary();

        // Assert
        result.Total.Should().Be(2);
        result.Completed.Should().Be(1);
        result.Pending.Should().Be(1);
    }
}
