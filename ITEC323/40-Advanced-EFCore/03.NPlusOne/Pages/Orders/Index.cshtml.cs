using Microsoft.AspNetCore.Mvc.RazorPages;
using NPlusOne.Models;
using NPlusOne.Services;

namespace NPlusOne.Pages.Orders;

/// <summary>
/// Displays the improved order summaries page.
/// </summary>
public class IndexModel : PageModel
{
    private readonly NPlusOneLabService _labService;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="labService">The lab service.</param>
    public IndexModel(NPlusOneLabService labService)
    {
        _labService = labService;
    }

    /// <summary>
    /// Gets the order summaries.
    /// </summary>
    public IReadOnlyList<OrderSummaryViewModel> Orders { get; private set; } = [];

    /// <summary>
    /// Gets the generated SQL.
    /// </summary>
    public string GeneratedSql { get; private set; } = string.Empty;

    /// <summary>
    /// Handles the GET request.
    /// </summary>
    /// <returns>A task that represents the asynchronous operation.</returns>
    public async Task OnGetAsync()
    {
        var result = await _labService.GetImprovedOrdersPageAsync();
        Orders = result.Orders;
        GeneratedSql = result.Sql;
    }
}
