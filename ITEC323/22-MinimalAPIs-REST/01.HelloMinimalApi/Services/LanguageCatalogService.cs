namespace HelloMinimalApiDemo.Services;

/// <summary>
/// Provides the small list of programming languages used by the sample API.
/// </summary>
public class LanguageCatalogService
{
    /// <summary>
    /// Gets the list of example programming languages.
    /// </summary>
    /// <returns>A fixed list of language names.</returns>
    public List<string> GetLanguages()
    {
        return
        [
            "Java",
            "Python",
            "C#"
        ];
    }
}
