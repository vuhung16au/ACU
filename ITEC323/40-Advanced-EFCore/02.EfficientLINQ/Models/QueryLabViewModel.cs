namespace EfficientLINQ.Models;

/// <summary>
/// Holds the query lab page content.
/// </summary>
public class QueryLabViewModel
{
    /// <summary>
    /// Gets or sets the comparisons displayed on the page.
    /// </summary>
    public IReadOnlyList<QueryComparisonExample> Comparisons { get; set; } = [];
}
