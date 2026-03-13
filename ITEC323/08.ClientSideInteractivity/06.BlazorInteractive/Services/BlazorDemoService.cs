namespace BlazorInteractive.Services;

public sealed class BlazorDemoService
{
    public IReadOnlyList<string> GetModuleHighlights()
    {
        return new[]
        {
            "Counter with event handling",
            "Grade calculator with two-way binding",
            "Simple planner wizard",
            "Cascading value sharing",
            "API-backed announcements list"
        };
    }
}
