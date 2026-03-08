namespace BlazorPagesAdvanced.Models;

/// <summary>
/// Represents a user in the system.
/// Maps to database columns via Entity Framework Core.
/// </summary>
public class User
{
    public int Id { get; set; }
    public string Name { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string Country { get; set; } = string.Empty;
    public DateTime CreatedDate { get; set; }
}
