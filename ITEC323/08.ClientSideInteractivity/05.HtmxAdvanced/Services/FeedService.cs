using HtmxAdvanced.Models;

namespace HtmxAdvanced.Services;

public sealed class FeedService
{
    private readonly List<FeedItem> _items;
    private int _pollCount;

    public FeedService()
    {
        _items = SeedItems();
    }

    public FeedQueryResult Search(string? query, int page, int pageSize)
    {
        page = Math.Max(1, page);
        pageSize = Math.Clamp(pageSize, 4, 24);

        var normalized = (query ?? string.Empty).Trim();

        IEnumerable<FeedItem> source = _items;
        if (!string.IsNullOrWhiteSpace(normalized))
        {
            source = source.Where(item =>
                item.Title.Contains(normalized, StringComparison.OrdinalIgnoreCase) ||
                item.Category.Contains(normalized, StringComparison.OrdinalIgnoreCase) ||
                item.Summary.Contains(normalized, StringComparison.OrdinalIgnoreCase));
        }

        var ordered = source.OrderByDescending(i => i.UpdatedAtUtc).ToList();
        var slice = ordered
            .Skip((page - 1) * pageSize)
            .Take(pageSize)
            .ToList();

        return new FeedQueryResult
        {
            Items = slice,
            Page = page,
            PageSize = pageSize,
            TotalMatches = ordered.Count,
            GeneratedAtUtc = DateTime.UtcNow
        };
    }

    public HealthSnapshot GetHealthSnapshot()
    {
        var tick = Interlocked.Increment(ref _pollCount);

        return new HealthSnapshot
        {
            PollCount = tick,
            QueueDepth = 2 + (tick % 5),
            ThroughputPerMinute = 110 + ((tick * 7) % 35),
            GeneratedAtUtc = DateTime.UtcNow
        };
    }

    private static List<FeedItem> SeedItems()
    {
        var categories = new[]
        {
            "Admissions",
            "Assessments",
            "Campus Life",
            "IT Support",
            "Learning",
            "Timetable"
        };

        var topics = new[]
        {
            "Portal rollout",
            "Assignment feedback",
            "Workshop reminder",
            "Infrastructure update",
            "Study group highlights",
            "Lab booking notice"
        };

        var list = new List<FeedItem>(48);
        var now = DateTime.UtcNow;

        for (var i = 1; i <= 48; i++)
        {
            var category = categories[i % categories.Length];
            var topic = topics[i % topics.Length];

            list.Add(new FeedItem
            {
                Id = i,
                Category = category,
                Title = $"{topic} #{i:D2}",
                Summary = "HTMX fragment example: this card is rendered by Razor and streamed into the page as HTML.",
                UpdatedAtUtc = now.AddMinutes(-i * 11),
                IsHighPriority = i % 5 == 0
            });
        }

        return list;
    }
}
