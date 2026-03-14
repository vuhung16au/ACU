namespace Week4Lab2Assignment;

/// <summary>
/// Contains helper methods for formatting messages about a user's preferences.
/// </summary>
public static class UserPreferenceFormatter
{
    /// <summary>
    /// Builds the message that will be shown to the user.
    /// </summary>
    /// <param name="name">The student's name entered on the page.</param>
    /// <param name="favouriteLanguage">The student's favourite programming language.</param>
    /// <returns>
    /// A sentence in the form:
    /// "Your name is &lt;name&gt; and your favourite programming language is &lt;language&gt;".
    /// </returns>
    public static string FormatMessage(string name, string favouriteLanguage)
    {
        return $"Your name is {name} and your favourite programming language is {favouriteLanguage}";
    }
}

