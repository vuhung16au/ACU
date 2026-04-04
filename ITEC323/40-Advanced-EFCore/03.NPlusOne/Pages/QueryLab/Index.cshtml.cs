using Microsoft.AspNetCore.Mvc.RazorPages;
using NPlusOne.Models;
using NPlusOne.Services;

namespace NPlusOne.Pages.QueryLab;

/// <summary>
/// Displays the N+1 comparison lab.
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
    /// Gets the lab results.
    /// </summary>
    public NPlusOneLabViewModel Lab { get; private set; } = new();

    /// <summary>
    /// Handles the GET request.
    /// </summary>
    /// <returns>A task that represents the asynchronous operation.</returns>
    public async Task OnGetAsync()
    {
        Lab = await _labService.BuildLabAsync();
    }
}
