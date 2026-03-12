using Microsoft.AspNetCore.Mvc;
using PartialPageUpdates.Models;
using PartialPageUpdates.Services;

namespace PartialPageUpdates.Controllers;

/// <summary>
/// Provides JSON endpoints used to update independent page sections.
/// </summary>
[ApiController]
[Route("api/[controller]")]
public class LearningDashboardController : ControllerBase
{
    private readonly LearningDashboardService _learningDashboardService;

    /// <summary>
    /// Initializes a new instance of the <see cref="LearningDashboardController"/> class.
    /// </summary>
    /// <param name="learningDashboardService">The in-memory dashboard service.</param>
    public LearningDashboardController(LearningDashboardService learningDashboardService)
    {
        _learningDashboardService = learningDashboardService;
    }

    /// <summary>
    /// Returns the announcements section data.
    /// </summary>
    /// <returns>A list of announcements.</returns>
    [HttpGet("announcements")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public ActionResult<IEnumerable<AnnouncementItem>> GetAnnouncements()
    {
        return Ok(_learningDashboardService.GetAnnouncements());
    }

    /// <summary>
    /// Returns the current progress summary.
    /// </summary>
    /// <returns>A summary object for the dashboard cards.</returns>
    [HttpGet("progress")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public ActionResult<LearningProgressSummary> GetProgress()
    {
        return Ok(_learningDashboardService.GetProgressSummary());
    }

    /// <summary>
    /// Returns the highlighted learning resource.
    /// </summary>
    /// <returns>A spotlight resource.</returns>
    [HttpGet("spotlight")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public ActionResult<ResourceSpotlight> GetSpotlight()
    {
        return Ok(_learningDashboardService.GetSpotlight());
    }

    /// <summary>
    /// Returns the current activity log.
    /// </summary>
    /// <returns>The activity log entries.</returns>
    [HttpGet("activity-log")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public ActionResult<IEnumerable<ActivityLogEntry>> GetActivityLog()
    {
        return Ok(_learningDashboardService.GetActivityLog());
    }

    /// <summary>
    /// Creates one new activity log entry.
    /// </summary>
    /// <param name="request">The student check-in request body.</param>
    /// <returns>The created activity entry.</returns>
    [HttpPost("check-ins")]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public ActionResult<ActivityLogEntry> CreateCheckIn([FromBody] CheckInRequest request)
    {
        var createdEntry = _learningDashboardService.AddCheckIn(request);
        return CreatedAtAction(nameof(GetActivityLog), new { id = createdEntry.Id }, createdEntry);
    }
}
