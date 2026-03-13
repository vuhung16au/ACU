using HtmxAdvanced.Models;
using HtmxAdvanced.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace HtmxAdvanced.Pages;

public class HtmxAdvancedLabModel : PageModel
{
    private const int PageSize = 8;
    private readonly FeedService _feedService;

    public HtmxAdvancedLabModel(FeedService feedService)
    {
        _feedService = feedService;
        InitialFeed = new FeedQueryResult
        {
            Items = Array.Empty<FeedItem>(),
            Page = 1,
            PageSize = PageSize,
            TotalMatches = 0
        };
    }

    public string Query { get; private set; } = string.Empty;
    public FeedQueryResult InitialFeed { get; private set; }

    public void OnGet(string? q)
    {
        Query = (q ?? string.Empty).Trim();
        InitialFeed = _feedService.Search(Query, 1, PageSize);
    }

    public IActionResult OnGetFeed(string? q, int page = 1)
    {
        var result = _feedService.Search(q, page, PageSize);
        return Partial("Partials/_FeedChunk", result);
    }

    public IActionResult OnGetHealth()
    {
        var snapshot = _feedService.GetHealthSnapshot();
        return Partial("Partials/_HealthPanel", snapshot);
    }
}
