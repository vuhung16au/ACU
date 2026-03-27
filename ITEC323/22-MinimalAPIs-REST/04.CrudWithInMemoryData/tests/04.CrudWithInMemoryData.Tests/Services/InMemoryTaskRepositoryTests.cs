using CrudWithInMemoryDataDemo.Models;
using CrudWithInMemoryDataDemo.Services;
using FluentAssertions;
using Xunit;

namespace CrudWithInMemoryData.Tests.Services;

/// <summary>
/// Tests in-memory CRUD operations.
/// </summary>
public class InMemoryTaskRepositoryTests
{
    [Fact]
    public void GetAll_DefaultState_ReturnsSeededTasks()
    {
        // Arrange
        var repository = new InMemoryTaskRepository();

        // Act
        var result = repository.GetAll();

        // Assert
        result.Should().HaveCount(2);
    }

    [Fact]
    public void Create_ValidRequest_AddsTask()
    {
        // Arrange
        var repository = new InMemoryTaskRepository();
        var request = new CreateTaskRequest { Title = "Create docs", Description = "Keep them short.", Priority = "Low" };

        // Act
        var result = repository.Create(request);

        // Assert
        result.Id.Should().Be(3);
        repository.GetAll().Should().HaveCount(3);
    }

    [Fact]
    public void Update_ExistingTask_ReturnsUpdatedTask()
    {
        // Arrange
        var repository = new InMemoryTaskRepository();
        var request = new UpdateTaskRequest
        {
            Title = "Updated title",
            Description = "Updated description",
            Priority = "High",
            IsCompleted = true
        };

        // Act
        var result = repository.Update(1, request);

        // Assert
        result.Should().NotBeNull();
        result!.IsCompleted.Should().BeTrue();
        result.Priority.Should().Be("High");
    }

    [Fact]
    public void Delete_ExistingTask_ReturnsTrue()
    {
        // Arrange
        var repository = new InMemoryTaskRepository();

        // Act
        var result = repository.Delete(2);

        // Assert
        result.Should().BeTrue();
        repository.GetAll().Should().HaveCount(1);
    }

    [Fact]
    public void GetById_UnknownId_ReturnsNull()
    {
        // Arrange
        var repository = new InMemoryTaskRepository();

        // Act
        var result = repository.GetById(99);

        // Assert
        result.Should().BeNull();
    }
}
