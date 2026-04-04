using EfficientLINQ.Models;
using EfficientLINQ.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace EfficientLINQ.Pages.Products;

/// <summary>
/// Displays a realistic paged product list using efficient LINQ.
/// </summary>
public class IndexModel : PageModel
{
    private readonly EfficientQueryLabService _queryLabService;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="queryLabService">The query lab service.</param>
    public IndexModel(EfficientQueryLabService queryLabService)
    {
        _queryLabService = queryLabService;
    }

    /// <summary>
    /// Gets the page size.
    /// </summary>
    public int PageSize { get; } = 5;

    /// <summary>
    /// Gets the current page number.
    /// </summary>
    [BindProperty(SupportsGet = true)]
    public int PageNumber { get; set; } = 1;

    /// <summary>
    /// Gets the projected products.
    /// </summary>
    public IReadOnlyList<ProductListItemViewModel> Products { get; private set; } = [];

    /// <summary>
    /// Gets the generated SQL.
    /// </summary>
    public string GeneratedSql { get; private set; } = string.Empty;

    /// <summary>
    /// Gets the total product count.
    /// </summary>
    public int TotalCount { get; private set; }

    /// <summary>
    /// Handles the GET request.
    /// </summary>
    /// <returns>A task that represents the asynchronous operation.</returns>
    public async Task OnGetAsync()
    {
        if (PageNumber < 1)
        {
            PageNumber = 1;
        }

        var result = await _queryLabService.GetPagedProductsAsync(PageNumber, PageSize);
        Products = result.Products;
        GeneratedSql = result.Sql;
        TotalCount = result.TotalCount;
    }
}
