using ComprehensiveMinimalApiDemo.Models;
using ComprehensiveMinimalApiDemo.Services;
using FluentAssertions;
using Xunit;

namespace ComprehensiveMinimalApi.Tests.Services;

/// <summary>
/// Tests the repository used by the comprehensive sample.
/// </summary>
public class InMemoryTaskRepositoryTests
{
    [Fact]
    public void Add_NewTask_ReturnsStoredTaskWithNewId()
    {
        // Arrange
        var repository = new InMemoryTaskRepository();

        // Act
        var result = repository.Add(new TaskItem
        {
            Title = "New task",
            Description = "Created during a test.",
            Priority = "Low",
            IsCompleted = false
        });

        // Assert
        result.Id.Should().Be(3);
        repository.GetAll().Should().HaveCount(3);
    }
}
