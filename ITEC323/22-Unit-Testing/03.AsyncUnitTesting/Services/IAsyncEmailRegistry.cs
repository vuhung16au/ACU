namespace AsyncUnitTestingDemo.Services;

/// <summary>
/// Defines a service that checks whether an email is already registered.
/// </summary>
public interface IAsyncEmailRegistry
{
    /// <summary>
    /// Determines asynchronously whether the given email already exists.
    /// </summary>
    /// <param name="email">The email address to check.</param>
    /// <returns>A task containing <c>true</c> when the email already exists; otherwise, <c>false</c>.</returns>
    Task<bool> EmailExistsAsync(string email);
}
