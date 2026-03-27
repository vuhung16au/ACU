using CrudWithInMemoryDataDemo.Models;
using CrudWithInMemoryDataDemo.Services;
using FluentAssertions;
using Xunit;

namespace CrudWithInMemoryData.Tests.Services;

/// <summary>
/// Tests CRUD request validation.
/// </summary>
public class TaskValidatorTests
{
    [Fact]
    public void ValidateCreate_MissingTitle_ReturnsError()
    {
        // Arrange
        var validator = new TaskValidator();

        // Act
        var result = validator.ValidateCreate(new CreateTaskRequest { Priority = "Low" });

        // Assert
        result.Should().Be("Title is required.");
    }

    [Fact]
    public void ValidateUpdate_ValidRequest_ReturnsNull()
    {
        // Arrange
        var validator = new TaskValidator();
        var request = new UpdateTaskRequest
        {
            Title = "Updated task",
            Description = "Ready to save.",
            Priority = "Medium",
            IsCompleted = true
        };

        // Act
        var result = validator.ValidateUpdate(request);

        // Assert
        result.Should().BeNull();
    }
}
