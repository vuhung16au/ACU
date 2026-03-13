namespace HtmxAdvanced.Models;

public sealed class FeedItem
{
    public required int Id { get; init; }
    public required string Title { get; init; }
    public required string Category { get; init; }
    public required string Summary { get; init; }
    public required DateTime UpdatedAtUtc { get; init; }
    public required bool IsHighPriority { get; init; }
}
