namespace Breadcrumbs.Models;

/// <summary>
/// Represents a single item in a breadcrumb navigation trail.
/// </summary>
/// <remarks>
/// Pass a list of <see cref="BreadcrumbItem"/> objects via <c>ViewData["Breadcrumbs"]</c>
/// in a PageModel's <c>OnGet</c> method. The shared layout reads this list and renders
/// a Bootstrap breadcrumb component. When <c>ViewData["Breadcrumbs"]</c> is not set,
/// the layout falls back to automatic breadcrumb generation from the URL path.
/// </remarks>
public class BreadcrumbItem
{
    /// <summary>
    /// Gets or sets the display label shown in the breadcrumb trail.
    /// </summary>
    public string Text { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the URL for this crumb's link.
    /// When <see langword="null" /> or empty, the item renders as plain text.
    /// The active (last) crumb should have no URL.
    /// </summary>
    public string? Url { get; set; }

    /// <summary>
    /// Gets or sets whether this crumb represents the current page.
    /// Active crumbs are styled differently and include <c>aria-current="page"</c>.
    /// </summary>
    public bool IsActive { get; set; }
}
