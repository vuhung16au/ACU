using FluentAssertions;
using HelloMinimalApiDemo.Models;
using HelloMinimalApiDemo.Services;
using Xunit;

namespace HelloMinimalApiDemo.Tests.Services;

/// <summary>
/// Tests for the <see cref="SubmissionEchoService"/> class.
/// </summary>
public class SubmissionEchoServiceTests
{
    private readonly SubmissionEchoService _service = new();

    /// <summary>
    /// Verifies that missing names return the expected error message.
    /// </summary>
    [Fact]
    public void Validate_WithMissingName_ReturnsNameRequiredMessage()
    {
        // Arrange
        var request = new FormSubmissionRequest
        {
            Name = "",
            Email = "ada@example.com"
        };

        // Act
        var result = _service.Validate(request);

        // Assert
        result.Should().Be("Name is required.");
    }

    /// <summary>
    /// Verifies that missing emails return the expected error message.
    /// </summary>
    [Fact]
    public void Validate_WithMissingEmail_ReturnsEmailRequiredMessage()
    {
        // Arrange
        var request = new FormSubmissionRequest
        {
            Name = "Ada Lovelace",
            Email = ""
        };

        // Act
        var result = _service.Validate(request);

        // Assert
        result.Should().Be("Email is required.");
    }

    /// <summary>
    /// Verifies that valid requests create a normalized response.
    /// </summary>
    [Fact]
    public void CreateResponse_WithValidRequest_ReturnsNormalizedResponse()
    {
        // Arrange
        var request = new FormSubmissionRequest
        {
            Name = " Ada Lovelace ",
            Email = " ada@example.com ",
            FavoriteLanguage = ""
        };

        // Act
        var result = _service.CreateResponse(request);

        // Assert
        result.Name.Should().Be("Ada Lovelace");
        result.Email.Should().Be("ada@example.com");
        result.FavoriteLanguage.Should().Be("Unselected");
        result.Message.Should().Be("Submission received successfully.");
    }
}
