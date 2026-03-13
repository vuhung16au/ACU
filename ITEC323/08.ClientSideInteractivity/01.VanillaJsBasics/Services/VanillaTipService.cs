namespace VanillaJsBasics.Services;

/// <summary>
/// Provides short, beginner-friendly vanilla JavaScript tips for classroom pages.
/// </summary>
public class VanillaTipService
{
    /// <summary>
    /// Gets a fixed list of tips that keep examples simple and practical.
    /// </summary>
    /// <returns>A read-only list of classroom tips.</returns>
    public IReadOnlyList<string> GetTips()
    {
        return new[]
        {
            "Prefer querySelector with clear CSS selectors before reaching for helper libraries.",
            "Use textContent when showing user input to avoid accidental HTML injection.",
            "Toggle one CSS class at a time so behavior is easy to reason about.",
            "Keep each event handler focused on one task and extract helpers as code grows."
        };
    }
}
