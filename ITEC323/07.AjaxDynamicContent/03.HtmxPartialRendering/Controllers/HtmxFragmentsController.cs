using HtmxPartialRendering.Models;
using HtmxPartialRendering.Services;
using Microsoft.AspNetCore.Mvc;

namespace HtmxPartialRendering.Controllers;

/// <summary>
/// Returns HTML fragments used by the HTMX sample page.
/// </summary>
[Route("fragments")]
public class HtmxFragmentsController : Controller
{
    private readonly HtmxCourseService _htmxCourseService;

    /// <summary>
    /// Initializes a new instance of the <see cref="HtmxFragmentsController"/> class.
    /// </summary>
    /// <param name="htmxCourseService">The in-memory HTMX demo data service.</param>
    public HtmxFragmentsController(HtmxCourseService htmxCourseService)
    {
        _htmxCourseService = htmxCourseService;
    }

    /// <summary>
    /// Returns the notices panel HTML.
    /// </summary>
    /// <returns>A fragment with notice cards.</returns>
    [HttpGet("notices")]
    public PartialViewResult Notices()
    {
        return PartialView("~/Views/Shared/Fragments/_NoticeList.cshtml", _htmxCourseService.GetNotices());
    }

    /// <summary>
    /// Returns the spotlight tip HTML.
    /// </summary>
    /// <returns>A fragment with the current tip.</returns>
    [HttpGet("tip")]
    public PartialViewResult Tip()
    {
        return PartialView("~/Views/Shared/Fragments/_TipCard.cshtml", _htmxCourseService.GetTip());
    }

    /// <summary>
    /// Returns the statistics panel HTML.
    /// </summary>
    /// <returns>A fragment with the current statistics.</returns>
    [HttpGet("stats")]
    public PartialViewResult Stats()
    {
        return PartialView("~/Views/Shared/Fragments/_StatsPanel.cshtml", _htmxCourseService.GetStats());
    }

    /// <summary>
    /// Returns the full reflection feed HTML.
    /// </summary>
    /// <returns>A fragment with all reflection entries.</returns>
    [HttpGet("feed")]
    public PartialViewResult Feed()
    {
        return PartialView("~/Views/Shared/Fragments/_ReflectionFeed.cshtml", _htmxCourseService.GetCheckIns());
    }

    /// <summary>
    /// Accepts a posted reflection and returns one new feed entry fragment.
    /// </summary>
    /// <param name="request">The posted check-in form values.</param>
    /// <returns>A single reflection entry fragment or a validation message fragment.</returns>
    [HttpPost("check-in")]
    public IActionResult CheckIn([FromForm] CheckInRequest request)
    {
        if (!ModelState.IsValid)
        {
            var errors = ModelState.Values
                .SelectMany(state => state.Errors)
                .Select(error => error.ErrorMessage)
                .ToList();

            return PartialView("~/Views/Shared/Fragments/_FormError.cshtml", errors);
        }

        var createdMessage = _htmxCourseService.AddCheckIn(request);
        return PartialView("~/Views/Shared/Fragments/_ReflectionEntry.cshtml", createdMessage);
    }
}
