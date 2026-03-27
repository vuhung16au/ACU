using FluentAssertions;
using RouteAndQueryParametersDemo.Services;
using Xunit;

namespace RouteAndQueryParameters.Tests.Services;

/// <summary>
/// Tests the task catalog filtering logic.
/// </summary>
public class TaskCatalogServiceTests
{
    [Fact]
    public void GetById_ExistingId_ReturnsTask()
    {
        // Arrange
        var service = new TaskCatalogService();

        // Act
        var result = service.GetById(2);

        // Assert
        result.Should().NotBeNull();
        result!.Title.Should().Be("Build the first task endpoint");
    }

    [Fact]
    public void FilterByPriority_ExistingPriority_ReturnsMatchingTasks()
    {
        // Arrange
        var service = new TaskCatalogService();

        // Act
        var result = service.FilterByPriority("High");

        // Assert
        result.Should().ContainSingle();
        result[0].Priority.Should().Be("High");
    }

    [Fact]
    public void FilterByCompletion_False_ReturnsIncompleteTasks()
    {
        // Arrange
        var service = new TaskCatalogService();

        // Act
        var result = service.FilterByCompletion(false);

        // Assert
        result.Should().OnlyContain(task => task.IsCompleted == false);
    }

    [Fact]
    public void GetById_UnknownId_ReturnsNull()
    {
        // Arrange
        var service = new TaskCatalogService();

        // Act
        var result = service.GetById(99);

        // Assert
        result.Should().BeNull();
    }
}
