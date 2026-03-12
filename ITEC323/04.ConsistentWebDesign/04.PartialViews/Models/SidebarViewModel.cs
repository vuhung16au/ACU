namespace PartialViews.Models;

public class SidebarViewModel
{
    public string Title { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public IReadOnlyList<string> Items { get; set; } = Array.Empty<string>();
}