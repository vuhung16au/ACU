namespace _01.ReactWebApiDemo.Server.Models;

/// <summary>
/// Holds the starter content returned to the React app on first load.
/// </summary>
public class SpaModuleOverview
{
    /// <summary>
    /// Gets or sets the title displayed in the React page header.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the one-sentence summary of the sample project.
    /// </summary>
    public string Description { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the explanation of local development mode.
    /// </summary>
    public string LocalDevelopmentFlow { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the explanation of published deployment mode.
    /// </summary>
    public string PublishedDeploymentFlow { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the key routes that students should inspect.
    /// </summary>
    public List<ApiRouteInfo> ApiRoutes { get; set; } = [];

    /// <summary>
    /// Gets or sets the checklist shown in the React page.
    /// </summary>
    public List<string> LearningChecklist { get; set; } = [];
}
