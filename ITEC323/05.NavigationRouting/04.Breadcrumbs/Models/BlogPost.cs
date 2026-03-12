namespace Breadcrumbs.Models;

/// <summary>
/// Represents a blog post used to demonstrate friendly routes.
/// </summary>
public class BlogPost
{
    /// <summary>
    /// Gets or sets the publication year.
    /// </summary>
    public int Year { get; set; }

    /// <summary>
    /// Gets or sets the publication month.
    /// </summary>
    public int Month { get; set; }

    /// <summary>
    /// Gets or sets the route-friendly slug.
    /// </summary>
    public string Slug { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the post title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets a short summary for the post.
    /// </summary>
    public string Summary { get; set; } = string.Empty;
}