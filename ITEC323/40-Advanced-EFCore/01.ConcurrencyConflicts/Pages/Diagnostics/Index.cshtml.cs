using ConcurrencyConflicts.Services;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ConcurrencyConflicts.Pages.Diagnostics;

/// <summary>
/// Displays SQL inspection helpers for the lesson.
/// </summary>
public class IndexModel : PageModel
{
    private readonly ConcurrencyConflictDemoService _demoService;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="demoService">The lesson demo service.</param>
    public IndexModel(ConcurrencyConflictDemoService demoService)
    {
        _demoService = demoService;
    }

    /// <summary>
    /// Gets the SQL used to load products.
    /// </summary>
    public string ProductsSql { get; private set; } = string.Empty;

    /// <summary>
    /// Gets the sample update SQL.
    /// </summary>
    public string UpdateSql { get; private set; } = string.Empty;

    /// <summary>
    /// Handles the GET request.
    /// </summary>
    public void OnGet()
    {
        ProductsSql = _demoService.GetEditableProductsSql();
        UpdateSql = _demoService.GetSampleUpdateSql();
    }
}
