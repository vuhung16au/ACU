namespace AlpineJsComponents.Services;

/// <summary>
/// Provides concise Alpine.js tips for beginner pages.
/// </summary>
public class AlpineTipsService
{
    /// <summary>
    /// Gets a fixed set of tips for classroom use.
    /// </summary>
    /// <returns>A read-only collection of Alpine.js tips.</returns>
    public IReadOnlyList<string> GetTips()
    {
        return new[]
        {
            "Start each component with x-data and a tiny state object.",
            "Use x-show for simple visibility toggles before introducing x-if.",
            "Keep each component focused on one responsibility.",
            "Mix Razor rendering and Alpine state when server data is already available."
        };
    }
}
