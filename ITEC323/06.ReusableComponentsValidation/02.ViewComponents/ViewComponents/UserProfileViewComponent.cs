using Microsoft.AspNetCore.Mvc;
using ViewComponentsDemo.Models;
using ViewComponentsDemo.Services;

namespace ViewComponentsDemo.ViewComponents;

/// <summary>
/// Displays a selected user profile loaded from sample data.
/// </summary>
public class UserProfileViewComponent : ViewComponent
{
    private readonly SampleDataService _sampleDataService;

    /// <summary>
    /// Initializes a new instance of the <see cref="UserProfileViewComponent"/> class.
    /// </summary>
    /// <param name="sampleDataService">Provides sample user profile data.</param>
    public UserProfileViewComponent(SampleDataService sampleDataService)
    {
        _sampleDataService = sampleDataService;
    }

    /// <summary>
    /// Finds the requested user profile before rendering the component.
    /// </summary>
    /// <param name="userId">The identifier of the user to display.</param>
    /// <returns>The rendered user profile component.</returns>
    public Task<IViewComponentResult> InvokeAsync(int userId)
    {
        UserProfile userProfile = _sampleDataService
            .GetUserProfiles()
            .FirstOrDefault(profile => profile.UserId == userId)
            ?? new UserProfile
            {
                UserId = userId,
                Name = "Unknown User",
                Role = "Guest",
                CourseProgressPercent = 0,
                NextTask = "No matching profile was found"
            };

        return Task.FromResult<IViewComponentResult>(View(userProfile));
    }
}
