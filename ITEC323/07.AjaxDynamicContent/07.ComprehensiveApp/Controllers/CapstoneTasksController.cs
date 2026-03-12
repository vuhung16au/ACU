using ComprehensiveApp.Models;
using ComprehensiveApp.Services;
using Microsoft.AspNetCore.Mvc;

namespace ComprehensiveApp.Controllers;

/// <summary>
/// Provides JSON endpoints for the comprehensive capstone dashboard.
/// </summary>
[ApiController]
[Route("api/[controller]")]
public class CapstoneTasksController : ControllerBase
{
    private readonly CapstoneDataService _capstoneDataService;

    /// <summary>
    /// Initializes a new instance of the <see cref="CapstoneTasksController"/> class.
    /// </summary>
    /// <param name="capstoneDataService">The local capstone data service.</param>
    public CapstoneTasksController(CapstoneDataService capstoneDataService)
    {
        _capstoneDataService = capstoneDataService;
    }

    /// <summary>
    /// Returns every capstone task as JSON.
    /// </summary>
    /// <returns>A list of tasks.</returns>
    [HttpGet]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public ActionResult<IEnumerable<CapstoneTaskItem>> GetTasks()
    {
        return Ok(_capstoneDataService.GetTasks());
    }

    /// <summary>
    /// Returns one task by identifier.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <returns>The matching task when found.</returns>
    [HttpGet("{id:int}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public ActionResult<CapstoneTaskItem> GetTask(int id)
    {
        var item = _capstoneDataService.GetTaskById(id);

        if (item is null)
        {
            return NotFound(new { message = $"Task with ID {id} was not found." });
        }

        return Ok(item);
    }

    /// <summary>
    /// Returns the dashboard summary used for polling.
    /// </summary>
    /// <returns>A summary object.</returns>
    [HttpGet("summary")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public ActionResult<CapstoneSummary> GetSummary()
    {
        return Ok(_capstoneDataService.GetSummary());
    }

    /// <summary>
    /// Creates a new capstone task.
    /// </summary>
    /// <param name="request">The request body for the new task.</param>
    /// <returns>The created task.</returns>
    [HttpPost]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public ActionResult<CapstoneTaskItem> CreateTask([FromBody] CapstoneTaskRequest request)
    {
        var createdItem = _capstoneDataService.CreateTask(request);
        return CreatedAtAction(nameof(GetTask), new { id = createdItem.Id }, createdItem);
    }

    /// <summary>
    /// Updates an existing capstone task.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <param name="request">The replacement values.</param>
    /// <returns>The updated task when found.</returns>
    [HttpPut("{id:int}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public ActionResult<CapstoneTaskItem> UpdateTask(int id, [FromBody] CapstoneTaskRequest request)
    {
        var updatedItem = _capstoneDataService.UpdateTask(id, request);

        if (updatedItem is null)
        {
            return NotFound(new { message = $"Task with ID {id} was not found." });
        }

        return Ok(updatedItem);
    }

    /// <summary>
    /// Deletes a capstone task.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <returns>No content when deletion succeeds.</returns>
    [HttpDelete("{id:int}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public IActionResult DeleteTask(int id)
    {
        var wasDeleted = _capstoneDataService.DeleteTask(id);

        if (!wasDeleted)
        {
            return NotFound(new { message = $"Task with ID {id} was not found." });
        }

        return NoContent();
    }
}
