using FluentAssertions;
using RequestBodiesAndValidationDemo.Models;
using RequestBodiesAndValidationDemo.Services;
using Xunit;

namespace RequestBodiesAndValidation.Tests.Services;

/// <summary>
/// Tests the small in-memory task store.
/// </summary>
public class InMemoryTaskStoreTests
{
    [Fact]
    public void GetAll_DefaultState_ReturnsSeededTasks()
    {
        // Arrange
        var store = new InMemoryTaskStore();

        // Act
        var result = store.GetAll();

        // Assert
        result.Should().HaveCount(2);
    }

    [Fact]
    public void Add_ValidTask_ReturnsStoredTaskWithNewId()
    {
        // Arrange
        var store = new InMemoryTaskStore();

        // Act
        var result = store.Add(new TaskItem
        {
            Title = "Add a new task",
            Description = "Store it in memory.",
            Priority = "Low",
            IsCompleted = false
        });

        // Assert
        result.Id.Should().Be(3);
        store.GetAll().Should().HaveCount(3);
    }

    [Fact]
    public void Update_ExistingTask_ReturnsUpdatedTask()
    {
        // Arrange
        var store = new InMemoryTaskStore();
        var updatedTask = new TaskItem
        {
            Id = 1,
            Title = "Plan the CRUD routes",
            Description = "Now updated.",
            Priority = "High",
            IsCompleted = true
        };

        // Act
        var result = store.Update(1, updatedTask);

        // Assert
        result.Should().NotBeNull();
        result!.Description.Should().Be("Now updated.");
        result.IsCompleted.Should().BeTrue();
    }

    [Fact]
    public void Delete_ExistingTask_ReturnsTrue()
    {
        // Arrange
        var store = new InMemoryTaskStore();

        // Act
        var result = store.Delete(2);

        // Assert
        result.Should().BeTrue();
        store.GetAll().Should().HaveCount(1);
    }
}
