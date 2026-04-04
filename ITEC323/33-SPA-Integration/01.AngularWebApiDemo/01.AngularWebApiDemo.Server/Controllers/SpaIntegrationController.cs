using _01.AngularWebApiDemo.Server.Models;
using _01.AngularWebApiDemo.Server.Services;
using Microsoft.AspNetCore.Mvc;

namespace _01.AngularWebApiDemo.Server.Controllers;

/// <summary>
/// Exposes a small JSON API that the Angular frontend can call while students
/// learn how an ASP.NET Core backend and SPA frontend work together.
/// </summary>
[ApiController]
[Route("api/spa-integration")]
public class SpaIntegrationController : ControllerBase
{
    private readonly SpaLessonService _spaLessonService;

    /// <summary>
    /// Initializes a new controller instance.
    /// </summary>
    /// <param name="spaLessonService">Provides the lesson content returned by the API.</param>
    public SpaIntegrationController(SpaLessonService spaLessonService)
    {
        _spaLessonService = spaLessonService;
    }

    /// <summary>
    /// Returns the starter content shown when the Angular app first loads.
    /// </summary>
    /// <returns>A structured overview of local development and publish flow.</returns>
    [HttpGet("overview")]
    [ProducesResponseType(typeof(SpaModuleOverview), StatusCodes.Status200OK)]
    public ActionResult<SpaModuleOverview> GetOverview()
    {
        return Ok(_spaLessonService.GetOverview());
    }

    /// <summary>
    /// Builds a short practice message from the learner's input.
    /// </summary>
    /// <param name="request">The student name and current topic to include.</param>
    /// <returns>A guided response that the Angular app can render.</returns>
    [HttpPost("practice-message")]
    [ProducesResponseType(typeof(PracticeMessageResponse), StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public ActionResult<PracticeMessageResponse> CreatePracticeMessage([FromBody] PracticeMessageRequest request)
    {
        if (string.IsNullOrWhiteSpace(request.StudentName))
        {
            ModelState.AddModelError(nameof(request.StudentName), "Student name is required.");
        }

        if (string.IsNullOrWhiteSpace(request.CurrentTopic))
        {
            ModelState.AddModelError(nameof(request.CurrentTopic), "Current topic is required.");
        }

        if (!ModelState.IsValid)
        {
            return ValidationProblem(ModelState);
        }

        return Ok(_spaLessonService.CreatePracticeMessage(request.StudentName, request.CurrentTopic));
    }
}
