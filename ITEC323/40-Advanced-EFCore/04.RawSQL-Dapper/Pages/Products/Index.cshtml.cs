using Microsoft.AspNetCore.Mvc.RazorPages;
using RawSQLDapper.Models;
using RawSQLDapper.Services;

namespace RawSQLDapper.Pages.Products;

/// <summary>
/// Displays the EF Core-oriented products page.
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
    /// Gets the products.
    /// </summary>
    public IReadOnlyList<Product> Products { get; private set; } = [];

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
        var result = await _reportingLabService.GetProductsAsync();
        Products = result.Products;
        GeneratedSql = result.Sql;
    }
}
