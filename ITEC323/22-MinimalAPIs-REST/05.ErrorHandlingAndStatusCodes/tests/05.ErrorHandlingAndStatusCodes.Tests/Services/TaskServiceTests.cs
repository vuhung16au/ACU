using ErrorHandlingAndStatusCodesDemo.Models;
using ErrorHandlingAndStatusCodesDemo.Services;
using FluentAssertions;
using Xunit;

namespace ErrorHandlingAndStatusCodes.Tests.Services;

/// <summary>
/// Tests task service success and failure cases.
/// </summary>
public class TaskServiceTests
{
    [Fact]
    public void GetById_KnownTask_ReturnsTask()
    {
        // Arrange
        var service = new TaskService();

        // Act
        var result = service.GetById(1);

        // Assert
        result.Title.Should().Be("Review API status codes");
    }

    [Fact]
    public void GetById_UnknownTask_ThrowsTaskNotFoundException()
    {
        // Arrange
        var service = new TaskService();

        // Act
        Action action = () => service.GetById(99);

        // Assert
        action.Should().Throw<TaskNotFoundException>()
            .WithMessage("Task with id 99 was not found.");
    }

    [Fact]
    public void Create_DuplicateTitle_ThrowsDuplicateTaskTitleException()
    {
        // Arrange
        var service = new TaskService();
        var request = new CreateTaskRequest
        {
            Title = "Review API status codes",
            Description = "Duplicate",
            Priority = "Medium"
        };

        // Act
        Action action = () => service.Create(request);

        // Assert
        action.Should().Throw<DuplicateTaskTitleException>()
            .WithMessage("A task with this title already exists.");
    }

    [Fact]
    public void Create_InvalidPriority_ThrowsArgumentException()
    {
        // Arrange
        var service = new TaskService();
        var request = new CreateTaskRequest
        {
            Title = "Write better docs",
            Description = "Validation sample",
            Priority = "Urgent"
        };

        // Act
        Action action = () => service.Create(request);

        // Assert
        action.Should().Throw<ArgumentException>()
            .WithMessage("Priority must be Low, Medium, or High.");
    }

    [Fact]
    public void ThrowUnexpectedFailure_Always_ThrowsInvalidOperationException()
    {
        // Arrange
        var service = new TaskService();

        // Act
        Action action = service.ThrowUnexpectedFailure;

        // Assert
        action.Should().Throw<InvalidOperationException>();
    }
}
