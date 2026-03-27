namespace MockingDependenciesDemo.Services;

/// <summary>
/// Provides a simple in-memory email registry for the sample web application.
/// </summary>
public class InMemoryEmailRegistry : IEmailRegistry
{
    private readonly HashSet<string> _existingEmails;

    /// <summary>
    /// Initializes a new instance of the <see cref="InMemoryEmailRegistry"/> class.
    /// </summary>
    /// <param name="existingEmails">The email values that should be treated as already registered.</param>
    public InMemoryEmailRegistry(IEnumerable<string> existingEmails)
    {
        _existingEmails = new HashSet<string>(
            existingEmails.Select(email => email.Trim()),
            StringComparer.OrdinalIgnoreCase);
    }

    /// <summary>
    /// Determines whether the given email already exists.
    /// </summary>
    /// <param name="email">The email address to check.</param>
    /// <returns><c>true</c> when the email already exists; otherwise, <c>false</c>.</returns>
    public bool EmailExists(string email)
    {
        return _existingEmails.Contains(email.Trim());
    }
}
