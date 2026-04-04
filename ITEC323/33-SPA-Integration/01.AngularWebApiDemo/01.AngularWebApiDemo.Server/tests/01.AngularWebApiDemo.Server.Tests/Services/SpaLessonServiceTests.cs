using _01.AngularWebApiDemo.Server.Services;
using FluentAssertions;

namespace _01.AngularWebApiDemo.Server.Tests.Services;

/// <summary>
/// Tests for the <see cref="SpaLessonService"/> class.
/// </summary>
public class SpaLessonServiceTests
{
    private readonly SpaLessonService _service = new();

    [Fact]
    public void GetOverview_DefaultCall_ReturnsStarterContent()
    {
        var result = _service.GetOverview();

        result.Title.Should().Be("SPA Integration Lesson Board");
        result.ApiRoutes.Should().HaveCount(2);
        result.LearningChecklist.Should().Contain(item => item.Contains("Angular page", StringComparison.Ordinal));
    }

    [Fact]
    public void CreatePracticeMessage_ValidInput_ReturnsPersonalizedResponse()
    {
        var result = _service.CreatePracticeMessage("Mia", "API calls");

        result.Heading.Should().Be("Nice work, Mia.");
        result.Message.Should().Contain("API calls");
        result.ReminderItems.Should().HaveCount(3);
    }

    [Fact]
    public void CreatePracticeMessage_EmptyStudentName_ThrowsArgumentException()
    {
        Action act = () => _service.CreatePracticeMessage(string.Empty, "Forms");

        act.Should().Throw<ArgumentException>()
            .WithMessage("*Student name is required*");
    }
}
