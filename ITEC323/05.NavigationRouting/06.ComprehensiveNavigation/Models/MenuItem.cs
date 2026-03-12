namespace ComprehensiveNavigation.Models;

/// <summary>
/// Represents a node in the navigation hierarchy.
/// </summary>
public class MenuItem
{
    /// <summary>
    /// Gets or sets the display text for the menu item.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the URL or Razor Page route for the menu item.
    /// </summary>
    public string Url { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets an optional icon name or class (e.g. Bootstrap icon class).
    /// </summary>
    public string? Icon { get; set; }

    /// <summary>
    /// Gets or sets a value indicating whether this item represents an exact route match for active link highlighting.
    /// </summary>
    public bool ExactMatch { get; set; }

    /// <summary>
    /// Gets or sets the child menu items (dropdown links or nested sidebar items).
    /// </summary>
    public List<MenuItem> Children { get; set; } = new List<MenuItem>();

    /// <summary>
    /// Checks if the menu item has any children.
    /// </summary>
    public bool HasChildren => Children != null && Children.Any();
}
