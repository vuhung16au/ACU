namespace HelloWorldData.Models;

/// <summary>
/// Represents a simple student record stored in the SQLite database.
/// </summary>
public class Student
{
    /// <summary>
    /// Gets or sets the unique identifier for a student.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the name of the student.
    /// </summary>
    public string Name { get; set; } = string.Empty;
}
