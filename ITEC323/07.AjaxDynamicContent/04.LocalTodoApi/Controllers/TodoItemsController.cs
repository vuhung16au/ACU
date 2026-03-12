using LocalTodoApi.Models;
using LocalTodoApi.Services;
using Microsoft.AspNetCore.Mvc;

namespace LocalTodoApi.Controllers;

/// <summary>
/// Provides a local RESTful todo API backed by in-memory storage.
/// </summary>
[ApiController]
[Route("api/[controller]")]
public class TodoItemsController : ControllerBase
{
    private readonly TodoItemService _todoItemService;

    /// <summary>
    /// Initializes a new instance of the <see cref="TodoItemsController"/> class.
    /// </summary>
    /// <param name="todoItemService">The in-memory todo service.</param>
    public TodoItemsController(TodoItemService todoItemService)
    {
        _todoItemService = todoItemService;
    }

    /// <summary>
    /// Returns every todo item as JSON.
    /// </summary>
    /// <returns>A list of todo items.</returns>
    [HttpGet]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public ActionResult<IEnumerable<TodoItem>> GetTodoItems()
    {
        return Ok(_todoItemService.GetAll());
    }

    /// <summary>
    /// Returns one todo item by identifier.
    /// </summary>
    /// <param name="id">The identifier of the todo item.</param>
    /// <returns>The matching item when found.</returns>
    [HttpGet("{id:int}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public ActionResult<TodoItem> GetTodoItem(int id)
    {
        var todoItem = _todoItemService.GetById(id);

        if (todoItem is null)
        {
            return NotFound(new { message = $"Todo item with ID {id} was not found." });
        }

        return Ok(todoItem);
    }

    /// <summary>
    /// Returns summary data for the todo dashboard.
    /// </summary>
    /// <returns>A todo summary object.</returns>
    [HttpGet("summary")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public ActionResult<TodoSummary> GetSummary()
    {
        return Ok(_todoItemService.GetSummary());
    }

    /// <summary>
    /// Creates a new todo item.
    /// </summary>
    /// <param name="request">The request body for the new item.</param>
    /// <returns>The created todo item.</returns>
    [HttpPost]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public ActionResult<TodoItem> CreateTodoItem([FromBody] TodoItemRequest request)
    {
        var createdItem = _todoItemService.Create(request);
        return CreatedAtAction(nameof(GetTodoItem), new { id = createdItem.Id }, createdItem);
    }

    /// <summary>
    /// Updates an existing todo item.
    /// </summary>
    /// <param name="id">The identifier of the item to update.</param>
    /// <param name="request">The replacement values.</param>
    /// <returns>The updated todo item when found.</returns>
    [HttpPut("{id:int}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public ActionResult<TodoItem> UpdateTodoItem(int id, [FromBody] TodoItemRequest request)
    {
        var updatedItem = _todoItemService.Update(id, request);

        if (updatedItem is null)
        {
            return NotFound(new { message = $"Todo item with ID {id} was not found." });
        }

        return Ok(updatedItem);
    }

    /// <summary>
    /// Deletes a todo item from memory.
    /// </summary>
    /// <param name="id">The identifier of the item to delete.</param>
    /// <returns>No content when deletion succeeds.</returns>
    [HttpDelete("{id:int}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public IActionResult DeleteTodoItem(int id)
    {
        var wasDeleted = _todoItemService.Delete(id);

        if (!wasDeleted)
        {
            return NotFound(new { message = $"Todo item with ID {id} was not found." });
        }

        return NoContent();
    }
}
