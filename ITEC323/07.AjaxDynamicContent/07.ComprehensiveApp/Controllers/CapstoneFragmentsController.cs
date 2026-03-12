using ComprehensiveApp.Models;
using ComprehensiveApp.Services;
using Microsoft.AspNetCore.Mvc;

namespace ComprehensiveApp.Controllers;

/// <summary>
/// Returns HTML fragments for the HTMX-style capstone panel.
/// </summary>
[Route("fragments")]
public class CapstoneFragmentsController : Controller
{
    private readonly CapstoneDataService _capstoneDataService;

    /// <summary>
    /// Initializes a new instance of the <see cref="CapstoneFragmentsController"/> class.
    /// </summary>
    /// <param name="capstoneDataService">The local capstone data service.</param>
    public CapstoneFragmentsController(CapstoneDataService capstoneDataService)
    {
        _capstoneDataService = capstoneDataService;
    }

    /// <summary>
    /// Returns the current technique tip fragment.
    /// </summary>
    /// <returns>A partial view result.</returns>
    [HttpGet("technique-tip")]
    public PartialViewResult TechniqueTip()
    {
        return PartialView("~/Views/Shared/Fragments/_TechniqueTip.cshtml", _capstoneDataService.GetTechniqueTip());
    }

    /// <summary>
    /// Returns the current reflection feed fragment.
    /// </summary>
    /// <returns>A partial view result.</returns>
    [HttpGet("reflection-feed")]
    public PartialViewResult ReflectionFeed()
    {
        return PartialView("~/Views/Shared/Fragments/_ReflectionFeed.cshtml", _capstoneDataService.GetReflections());
    }

    /// <summary>
    /// Accepts a reflection form post and returns one new reflection card.
    /// </summary>
    /// <param name="request">The posted reflection request.</param>
    /// <returns>A reflection card or validation message fragment.</returns>
    [HttpPost("reflection")]
    public IActionResult Reflection([FromForm] ReflectionRequest request)
    {
        if (!ModelState.IsValid)
        {
            var errors = ModelState.Values
                .SelectMany(state => state.Errors)
                .Select(error => error.ErrorMessage)
                .ToList();

            return PartialView("~/Views/Shared/Fragments/_FormError.cshtml", errors);
        }

        var createdEntry = _capstoneDataService.AddReflection(request);
        return PartialView("~/Views/Shared/Fragments/_ReflectionEntry.cshtml", createdEntry);
    }
}
