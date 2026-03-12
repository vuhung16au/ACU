namespace PartialViews.Models;

public class AlertViewModel
{
    public string Tone { get; set; } = "info";
    public string Title { get; set; } = string.Empty;
    public string Message { get; set; } = string.Empty;
}