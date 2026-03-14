using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using MvvmDependencyInjection.Models;
using MvvmDependencyInjection.Services;

namespace MvvmDependencyInjection.ViewModels;

/// <summary>
/// View model for the main MVVM + DI learning page.
/// Demonstrates observable properties and relay commands.
/// </summary>
public partial class MainViewModel : ObservableObject
{
	private readonly IWelcomeMessageService _welcomeMessageService;

	/// <summary>
	/// Learner name entered in the Entry control.
	/// </summary>
	[ObservableProperty]
	private string learnerName = "Student";

	/// <summary>
	/// Current click count displayed in the UI.
	/// </summary>
	[ObservableProperty]
	private int clickCount;

	/// <summary>
	/// Dynamic status message shown below the Entry.
	/// </summary>
	[ObservableProperty]
	private string statusMessage = "Press Increment Count to trigger a RelayCommand.";

	/// <summary>
	/// Dynamic text for the primary action button.
	/// </summary>
	[ObservableProperty]
	private string counterButtonText = "Increment Count";

	/// <summary>
	/// Snapshot model used to demonstrate ViewModel-to-Model updates.
	/// </summary>
	[ObservableProperty]
	private CounterSnapshot latestSnapshot = new("Student", 0, DateTime.Now);

	/// <summary>
	/// Guards long-running command execution.
	/// </summary>
	[ObservableProperty]
	private bool isBusy;

	/// <summary>
	/// Initializes a new main view model with injected services.
	/// </summary>
	/// <param name="welcomeMessageService">Service used to compose status text.</param>
	public MainViewModel(IWelcomeMessageService welcomeMessageService)
	{
		_welcomeMessageService = welcomeMessageService;
		StatusMessage = _welcomeMessageService.BuildStatusMessage(LearnerName, ClickCount);
	}

	partial void OnLearnerNameChanged(string value)
	{
		if (ClickCount == 0)
		{
			StatusMessage = _welcomeMessageService.BuildStatusMessage(value, ClickCount);
		}
	}

	/// <summary>
	/// Increments the counter and refreshes all bound state.
	/// </summary>
	/// <returns>A task representing command completion.</returns>
	[RelayCommand]
	private async Task IncrementAsync()
	{
		if (IsBusy)
		{
			return;
		}

		try
		{
			IsBusy = true;
			await Task.Delay(120);

			ClickCount++;
			CounterButtonText = ClickCount == 1 ? "Incremented 1 time" : $"Incremented {ClickCount} times";
			StatusMessage = _welcomeMessageService.BuildStatusMessage(LearnerName, ClickCount);
			LatestSnapshot = new CounterSnapshot(LearnerName, ClickCount, DateTime.Now);
		}
		catch (Exception ex)
		{
			StatusMessage = "An error occurred while updating the count.";
			System.Diagnostics.Debug.WriteLine(ex);
		}
		finally
		{
			IsBusy = false;
		}
	}

	/// <summary>
	/// Resets all state to the initial values.
	/// </summary>
	[RelayCommand]
	private void Reset()
	{
		ClickCount = 0;
		CounterButtonText = "Increment Count";
		StatusMessage = _welcomeMessageService.BuildStatusMessage(LearnerName, ClickCount);
		LatestSnapshot = new CounterSnapshot(LearnerName, ClickCount, DateTime.Now);
	}
}
