namespace HtmxPartialUpdates.Models;

/// <summary>
/// Represents a single student record displayed and edited in the HTMX CRUD table.
/// </summary>
public class Student
{
    /// <summary>Unique identifier. Auto-assigned by StudentService.</summary>
    public int Id { get; set; }

    /// <summary>Full name of the student.</summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>Name of the enrolled course.</summary>
    public string Course { get; set; } = string.Empty;

    /// <summary>Grade score from 0 to 100.</summary>
    public int Grade { get; set; }

    /// <summary>Whether the student is currently enrolled.</summary>
    public bool IsActive { get; set; } = true;
}
