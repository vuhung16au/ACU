namespace MvvmDependencyInjection.Services;

/// <summary>
/// Default implementation for status and greeting message formatting.
/// </summary>
public sealed class WelcomeMessageService : IWelcomeMessageService
{
	/// <inheritdoc />
	public string BuildStatusMessage(string learnerName, int clickCount)
	{
		var safeName = string.IsNullOrWhiteSpace(learnerName) ? "Student" : learnerName.Trim();
		var clickText = clickCount == 1 ? "1 click" : $"{clickCount} clicks";
		return $"Hello {safeName}, you have made {clickText}.";
	}
}
