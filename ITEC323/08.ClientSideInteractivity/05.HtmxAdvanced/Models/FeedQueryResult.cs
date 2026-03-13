namespace HtmxAdvanced.Models;

public sealed class FeedQueryResult
{
    public required IReadOnlyList<FeedItem> Items { get; init; }
    public required int Page { get; init; }
    public required int PageSize { get; init; }
    public required int TotalMatches { get; init; }

    public int VisibleCount => Math.Min(Page * PageSize, TotalMatches);
    public bool HasMore => Page * PageSize < TotalMatches;
    public int NextPage => Page + 1;
    public DateTime GeneratedAtUtc { get; init; } = DateTime.UtcNow;
}
