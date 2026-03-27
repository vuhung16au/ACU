namespace MockingDependenciesDemo.Services;

/// <summary>
/// Defines a service that checks whether an email is already registered.
/// </summary>
public interface IEmailRegistry
{
    /// <summary>
    /// Determines whether the given email already exists.
    /// </summary>
    /// <param name="email">The email address to check.</param>
    /// <returns><c>true</c> when the email already exists; otherwise, <c>false</c>.</returns>
    bool EmailExists(string email);
}
