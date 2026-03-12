using Microsoft.AspNetCore.Mvc;
using ViewComponentsDemo.Models;
using ViewComponentsDemo.Services;

namespace ViewComponentsDemo.ViewComponents;

/// <summary>
/// Displays recent activity items filtered for one user.
/// </summary>
public class RecentItemsViewComponent : ViewComponent
{
    private readonly SampleDataService _sampleDataService;

    /// <summary>
    /// Initializes a new instance of the <see cref="RecentItemsViewComponent"/> class.
    /// </summary>
    /// <param name="sampleDataService">Provides sample recent activity data.</param>
    public RecentItemsViewComponent(SampleDataService sampleDataService)
    {
        _sampleDataService = sampleDataService;
    }

    /// <summary>
    /// Filters and limits recent items before rendering the component.
    /// </summary>
    /// <param name="userId">The identifier of the user whose activity is shown.</param>
    /// <param name="maxItems">The maximum number of recent items to display.</param>
    /// <returns>The rendered recent items component.</returns>
    public Task<IViewComponentResult> InvokeAsync(int userId, int maxItems = 3)
    {
        List<RecentItem> recentItems = _sampleDataService
            .GetRecentItems()
            .Where(item => item.UserId == userId)
            .OrderBy(item => item.HoursAgo)
            .Take(maxItems)
            .ToList();

        return Task.FromResult<IViewComponentResult>(View(recentItems));
    }
}
