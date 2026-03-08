namespace RazorPagesAdvanced.Models;

/// <summary>
/// Represents a user in the system.
/// This is a simple model class with properties that map to database columns.
/// </summary>
public class User
{
    /// <summary>
    /// Primary key - unique identifier for each user
    /// </summary>
    public int Id { get; set; }
    
    /// <summary>
    /// User's full name
    /// </summary>
    public string Name { get; set; } = string.Empty;
    
    /// <summary>
    /// User's email address
    /// </summary>
    public string Email { get; set; } = string.Empty;
    
    /// <summary>
    /// User's country
    /// </summary>
    public string Country { get; set; } = string.Empty;
    
    /// <summary>
    /// Date when the account was created
    /// </summary>
    public DateTime CreatedDate { get; set; }
}
