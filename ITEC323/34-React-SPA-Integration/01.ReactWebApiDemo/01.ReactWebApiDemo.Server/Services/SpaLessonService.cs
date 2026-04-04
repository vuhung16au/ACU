using _01.ReactWebApiDemo.Server.Models;

namespace _01.ReactWebApiDemo.Server.Services;

/// <summary>
/// Provides small, deterministic data sets that make the SPA integration flow
/// easy for beginners to inspect and test.
/// </summary>
public class SpaLessonService
{
    /// <summary>
    /// Creates the overview data shown when the React app first loads.
    /// </summary>
    /// <returns>A starter payload describing the sample and its routes.</returns>
    public SpaModuleOverview GetOverview()
    {
        return new SpaModuleOverview
        {
            Title = "React SPA Integration Lesson Board",
            Description = "This sample shows a React frontend calling a small ASP.NET Core backend through a clear JSON API.",
            LocalDevelopmentFlow = "During local development, the React Vite dev server renders the page while proxying API requests back to ASP.NET Core.",
            PublishedDeploymentFlow = "After publish, ASP.NET Core serves the built React files as static assets so one deployed app can provide both the UI and the API.",
            ApiRoutes =
            [
                new ApiRouteInfo
                {
                    HttpMethod = "GET",
                    Route = "/api/spa-integration/overview",
                    Purpose = "Loads the page content when the React app starts."
                },
                new ApiRouteInfo
                {
                    HttpMethod = "POST",
                    Route = "/api/spa-integration/practice-message",
                    Purpose = "Sends learner input to the backend and returns a personalized response."
                }
            ],
            LearningChecklist =
            [
                "Open the React page and confirm the overview data appears from the API.",
                "Submit the form and watch the POST request return a JSON response.",
                "Compare how React state updates differ from the Angular component and service approach."
            ]
        };
    }

    /// <summary>
    /// Creates a short, personalized response from learner input.
    /// </summary>
    /// <param name="studentName">The learner name to personalize the reply.</param>
    /// <param name="currentTopic">The topic the learner wants to practice.</param>
    /// <returns>A small coaching response suitable for rendering in the SPA.</returns>
    /// <exception cref="ArgumentException">Thrown when either input is empty.</exception>
    public PracticeMessageResponse CreatePracticeMessage(string studentName, string currentTopic)
    {
        if (string.IsNullOrWhiteSpace(studentName))
        {
            throw new ArgumentException("Student name is required.", nameof(studentName));
        }

        if (string.IsNullOrWhiteSpace(currentTopic))
        {
            throw new ArgumentException("Current topic is required.", nameof(currentTopic));
        }

        var trimmedStudentName = studentName.Trim();
        var trimmedTopic = currentTopic.Trim();

        return new PracticeMessageResponse
        {
            Heading = $"Nice work, {trimmedStudentName}.",
            Message = $"Your React form sent \"{trimmedTopic}\" to ASP.NET Core, and the backend returned this JSON response for the page to render.",
            NextStep = "Try changing the form values and inspect how the React component updates local state after the fetch call completes.",
            ReminderItems =
            [
                "React state stores the current API result.",
                "The controller validates input before returning JSON.",
                "The same backend can serve the published frontend files later."
            ]
        };
    }
}
