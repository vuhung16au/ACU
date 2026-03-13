namespace HtmxAdvanced.Models;

public sealed class HealthSnapshot
{
    public required int PollCount { get; init; }
    public required int QueueDepth { get; init; }
    public required int ThroughputPerMinute { get; init; }
    public required DateTime GeneratedAtUtc { get; init; }
}
