using FluentAssertions;
using RequestBodiesAndValidationDemo.Models;
using RequestBodiesAndValidationDemo.Services;
using Xunit;

namespace RequestBodiesAndValidation.Tests.Services;

/// <summary>
/// Tests the request validation rules.
/// </summary>
public class TaskRequestValidatorTests
{
    [Fact]
    public void Validate_ValidRequest_ReturnsNull()
    {
        // Arrange
        var validator = new TaskRequestValidator();
        var request = new CreateTaskRequest { Title = "Write tests", Priority = "High" };

        // Act
        var result = validator.Validate(request);

        // Assert
        result.Should().BeNull();
    }

    [Fact]
    public void Validate_MissingTitle_ReturnsError()
    {
        // Arrange
        var validator = new TaskRequestValidator();
        var request = new CreateTaskRequest { Priority = "High" };

        // Act
        var result = validator.Validate(request);

        // Assert
        result.Should().Be("Title is required.");
    }

    [Fact]
    public void Validate_InvalidPriority_ReturnsError()
    {
        // Arrange
        var validator = new TaskRequestValidator();
        var request = new CreateTaskRequest { Title = "Write tests", Priority = "Urgent" };

        // Act
        var result = validator.Validate(request);

        // Assert
        result.Should().Be("Priority must be Low, Medium, or High.");
    }

    [Fact]
    public void ValidateUpdate_ValidRequest_ReturnsNull()
    {
        // Arrange
        var validator = new TaskRequestValidator();
        var request = new UpdateTaskRequest
        {
            Title = "Update the task",
            Description = "Use PUT with JSON.",
            Priority = "Medium",
            IsCompleted = true
        };

        // Act
        var result = validator.Validate(request);

        // Assert
        result.Should().BeNull();
    }
}
