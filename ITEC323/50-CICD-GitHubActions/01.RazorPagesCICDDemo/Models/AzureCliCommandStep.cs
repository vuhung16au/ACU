namespace RazorPagesCICDDemo.Models;

/// <summary>
/// Represents one Azure CLI step used during manual deployment.
/// </summary>
/// <param name="Title">The short label shown in the lesson.</param>
/// <param name="Purpose">The reason this step exists in the deployment flow.</param>
/// <param name="Command">The exact command students should review and run.</param>
public sealed record AzureCliCommandStep(string Title, string Purpose, string Command);
