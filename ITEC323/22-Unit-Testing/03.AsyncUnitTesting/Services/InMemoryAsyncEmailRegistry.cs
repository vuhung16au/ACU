namespace AsyncUnitTestingDemo.Services;

/// <summary>
/// Provides a simple in-memory asynchronous email registry for the sample web application.
/// </summary>
public class InMemoryAsyncEmailRegistry : IAsyncEmailRegistry
{
    private readonly HashSet<string> _existingEmails;

    /// <summary>
    /// Initializes a new instance of the <see cref="InMemoryAsyncEmailRegistry"/> class.
    /// </summary>
    /// <param name="existingEmails">The email values that should be treated as already registered.</param>
    public InMemoryAsyncEmailRegistry(IEnumerable<string> existingEmails)
    {
        _existingEmails = new HashSet<string>(
            existingEmails.Select(email => email.Trim()),
            StringComparer.OrdinalIgnoreCase);
    }

    /// <summary>
    /// Determines asynchronously whether the given email already exists.
    /// </summary>
    /// <param name="email">The email address to check.</param>
    /// <returns>A task containing <c>true</c> when the email already exists; otherwise, <c>false</c>.</returns>
    public Task<bool> EmailExistsAsync(string email)
    {
        return Task.FromResult(_existingEmails.Contains(email.Trim()));
    }
}
