using Microsoft.AspNetCore.Mvc.RazorPages;
using RawSQLDapper.Models;
using RawSQLDapper.Services;

namespace RawSQLDapper.Pages.Reports;

/// <summary>
/// Displays the EF Core and Dapper reports comparison.
/// </summary>
public class IndexModel : PageModel
{
    private readonly ReportingLabService _reportingLabService;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="reportingLabService">The reporting lab service.</param>
    public IndexModel(ReportingLabService reportingLabService)
    {
        _reportingLabService = reportingLabService;
    }

    /// <summary>
    /// Gets the reports page content.
    /// </summary>
    public ReportsPageViewModel ReportPage { get; private set; } = new();

    /// <summary>
    /// Handles the GET request.
    /// </summary>
    /// <returns>A task that represents the asynchronous operation.</returns>
    public async Task OnGetAsync()
    {
        ReportPage = await _reportingLabService.BuildReportsPageAsync();
    }
}
