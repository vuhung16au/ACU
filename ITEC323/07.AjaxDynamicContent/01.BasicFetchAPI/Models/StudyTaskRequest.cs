using System.ComponentModel.DataAnnotations;

namespace BasicFetchAPI.Models;

/// <summary>
/// Represents the data sent from the Fetch API form to the local JSON API.
/// </summary>
public class StudyTaskRequest
{
    /// <summary>
    /// Gets or sets the task title entered by the user.
    /// </summary>
    [Required]
    [StringLength(80, MinimumLength = 3)]
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the task category such as Reading or Coding.
    /// </summary>
    [Required]
    [StringLength(40, MinimumLength = 3)]
    public string Category { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets an optional due date for the task.
    /// </summary>
    public DateTime? DueDate { get; set; }

    /// <summary>
    /// Gets or sets extra notes that explain the task.
    /// </summary>
    [StringLength(200)]
    public string? Notes { get; set; }

    /// <summary>
    /// Gets or sets a value indicating whether the task is complete.
    /// </summary>
    public bool IsCompleted { get; set; }
}
