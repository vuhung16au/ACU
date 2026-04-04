using EfficientLINQ.Models;
using EfficientLINQ.Services;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace EfficientLINQ.Pages.QueryLab;

/// <summary>
/// Displays the query comparison lab.
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
    /// Gets the query lab view model.
    /// </summary>
    public QueryLabViewModel Lab { get; private set; } = new();

    /// <summary>
    /// Handles the GET request.
    /// </summary>
    /// <returns>A task that represents the asynchronous operation.</returns>
    public async Task OnGetAsync()
    {
        Lab = await _queryLabService.BuildQueryLabAsync();
    }
}
