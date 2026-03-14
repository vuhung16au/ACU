namespace MvvmDependencyInjection.Services;

/// <summary>
/// Builds user-facing messages for the main learning workflow.
/// </summary>
public interface IWelcomeMessageService
{
	/// <summary>
	/// Creates the status message shown after each counter update.
	/// </summary>
	/// <param name="learnerName">Name entered in the UI.</param>
	/// <param name="clickCount">Current number of clicks.</param>
	/// <returns>A friendly status message.</returns>
	string BuildStatusMessage(string learnerName, int clickCount);
}
