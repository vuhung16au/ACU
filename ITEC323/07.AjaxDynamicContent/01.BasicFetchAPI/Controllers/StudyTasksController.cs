using BasicFetchAPI.Models;
using BasicFetchAPI.Services;
using Microsoft.AspNetCore.Mvc;

namespace BasicFetchAPI.Controllers;

/// <summary>
/// Provides local JSON endpoints for the Fetch API classroom demo.
/// </summary>
[ApiController]
[Route("api/[controller]")]
public class StudyTasksController : ControllerBase
{
    private readonly StudyTaskService _studyTaskService;

    /// <summary>
    /// Initializes a new instance of the <see cref="StudyTasksController"/> class.
    /// </summary>
    /// <param name="studyTaskService">The in-memory task service.</param>
    public StudyTasksController(StudyTaskService studyTaskService)
    {
        _studyTaskService = studyTaskService;
    }

    /// <summary>
    /// Returns every task as JSON.
    /// </summary>
    /// <returns>A list of tasks.</returns>
    [HttpGet]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public ActionResult<IEnumerable<StudyTaskItem>> GetTasks()
    {
        return Ok(_studyTaskService.GetAll());
    }

    /// <summary>
    /// Returns one task as JSON.
    /// </summary>
    /// <param name="id">The identifier of the task to return.</param>
    /// <returns>The matching task when it exists.</returns>
    [HttpGet("{id:int}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public ActionResult<StudyTaskItem> GetTask(int id)
    {
        var task = _studyTaskService.GetById(id);

        if (task is null)
        {
            return NotFound(new { message = $"Task with ID {id} was not found." });
        }

        return Ok(task);
    }

    /// <summary>
    /// Returns summary counts used by the dashboard cards.
    /// </summary>
    /// <returns>A task summary object.</returns>
    [HttpGet("summary")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public ActionResult<StudyTaskSummary> GetSummary()
    {
        return Ok(_studyTaskService.GetSummary());
    }

    /// <summary>
    /// Creates a new task from the posted JSON body.
    /// </summary>
    /// <param name="request">The task values to create.</param>
    /// <returns>The created task and its API location.</returns>
    [HttpPost]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public ActionResult<StudyTaskItem> CreateTask([FromBody] StudyTaskRequest request)
    {
        var createdTask = _studyTaskService.Create(request);
        return CreatedAtAction(nameof(GetTask), new { id = createdTask.Id }, createdTask);
    }

    /// <summary>
    /// Updates an existing task using the posted JSON body.
    /// </summary>
    /// <param name="id">The identifier of the task to update.</param>
    /// <param name="request">The replacement values.</param>
    /// <returns>The updated task when found.</returns>
    [HttpPut("{id:int}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public ActionResult<StudyTaskItem> UpdateTask(int id, [FromBody] StudyTaskRequest request)
    {
        var updatedTask = _studyTaskService.Update(id, request);

        if (updatedTask is null)
        {
            return NotFound(new { message = $"Task with ID {id} was not found." });
        }

        return Ok(updatedTask);
    }

    /// <summary>
    /// Deletes a task from memory.
    /// </summary>
    /// <param name="id">The identifier of the task to delete.</param>
    /// <returns>No content when deletion succeeds.</returns>
    [HttpDelete("{id:int}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public IActionResult DeleteTask(int id)
    {
        var wasDeleted = _studyTaskService.Delete(id);

        if (!wasDeleted)
        {
            return NotFound(new { message = $"Task with ID {id} was not found." });
        }

        return NoContent();
    }
}
