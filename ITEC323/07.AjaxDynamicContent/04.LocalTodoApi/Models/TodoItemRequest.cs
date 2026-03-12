using System.ComponentModel.DataAnnotations;

namespace LocalTodoApi.Models;

/// <summary>
/// Represents the request body used to create or update a todo item.
/// </summary>
public class TodoItemRequest
{
    /// <summary>
    /// Gets or sets the todo title.
    /// </summary>
    [Required]
    [StringLength(80, MinimumLength = 3)]
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the todo description.
    /// </summary>
    [StringLength(220)]
    public string? Description { get; set; }

    /// <summary>
    /// Gets or sets the todo category.
    /// </summary>
    [Required]
    [StringLength(40, MinimumLength = 3)]
    public string Category { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the optional due date.
    /// </summary>
    public DateTime? DueDate { get; set; }

    /// <summary>
    /// Gets or sets a value indicating whether the todo item is complete.
    /// </summary>
    public bool IsCompleted { get; set; }
}
