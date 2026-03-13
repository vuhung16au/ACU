namespace BlazorInteractive.Models;

public sealed class AnnouncementDto
{
    public int Id { get; set; }
    public string Title { get; set; } = string.Empty;
    public string Audience { get; set; } = string.Empty;
    public DateTime StartsAtUtc { get; set; }
    public string Mode { get; set; } = string.Empty;
}
