using RazorPagesCICDDemo.Models;

namespace RazorPagesCICDDemo.Services;

/// <summary>
/// Supplies Azure CLI deployment steps and troubleshooting guidance for the lesson.
/// </summary>
public class AzureCliDeploymentGuideService
{
    /// <summary>
    /// Gets the manual Azure App Service deployment steps taught in the lesson.
    /// </summary>
    /// <returns>An ordered list of Azure CLI deployment steps.</returns>
    public IReadOnlyList<AzureCliCommandStep> GetDeploymentSteps()
    {
        return
        [
            new AzureCliCommandStep(
                "Sign in",
                "Connect the local terminal to the correct Azure subscription before creating resources.",
                "az login"),
            new AzureCliCommandStep(
                "Create a resource group",
                "Keep the App Service resources together so they are easy to inspect and remove later.",
                "az group create --name rg-itec323-cicd-demo --location australiaeast"),
            new AzureCliCommandStep(
                "Create an App Service plan",
                "Define the pricing tier and compute capacity for the deployment target.",
                "az appservice plan create --name plan-itec323-cicd-demo --resource-group rg-itec323-cicd-demo --sku B1 --is-linux"),
            new AzureCliCommandStep(
                "Create the web app",
                "Provision an ASP.NET Core hosting target that matches the published app output.",
                "az webapp create --name <unique-app-name> --resource-group rg-itec323-cicd-demo --plan plan-itec323-cicd-demo --runtime \"DOTNETCORE:10.0\""),
            new AzureCliCommandStep(
                "Publish the app",
                "Generate the Release output that GitHub Actions also publishes as an artifact.",
                "/usr/local/share/dotnet/dotnet publish 50-CICD-GitHubActions/01.RazorPagesCICDDemo/01.RazorPagesCICDDemo.csproj -c Release -o ./artifacts/publish"),
            new AzureCliCommandStep(
                "Deploy the published files",
                "Zip deploy the published output so students can inspect the packaging step directly.",
                "cd artifacts/publish && zip -r ../01.RazorPagesCICDDemo.zip . && cd ../.. && az webapp deploy --resource-group rg-itec323-cicd-demo --name <unique-app-name> --src-path ./artifacts/01.RazorPagesCICDDemo.zip --type zip"),
            new AzureCliCommandStep(
                "Inspect logs",
                "Use logs to troubleshoot startup issues and confirm that the correct package reached App Service.",
                "az webapp log tail --resource-group rg-itec323-cicd-demo --name <unique-app-name>"),
            new AzureCliCommandStep(
                "Restart after a fix",
                "Restart the app after changing configuration or redeploying a corrected package.",
                "az webapp restart --resource-group rg-itec323-cicd-demo --name <unique-app-name>")
        ];
    }

    /// <summary>
    /// Gets troubleshooting tips for common Azure App Service deployment failures.
    /// </summary>
    /// <returns>An ordered list of troubleshooting notes.</returns>
    public IReadOnlyList<DeploymentTroubleshootingTip> GetTroubleshootingTips()
    {
        return
        [
            new DeploymentTroubleshootingTip(
                "Wrong runtime stack",
                "The deployment succeeds, but App Service shows a startup failure or the site never becomes healthy.",
                "Check the runtime set on the web app and confirm it matches the .NET version used during publish."),
            new DeploymentTroubleshootingTip(
                "Missing publish output",
                "The deploy command runs, but the app serves old files or fails because expected assemblies are missing.",
                "Delete the local publish folder, rerun dotnet publish in Release mode, and zip only the published output."),
            new DeploymentTroubleshootingTip(
                "Bad startup behavior",
                "The app deploys, but requests return HTTP 500 or the log stream shows unhandled exceptions during startup.",
                "Read the live log stream first, then confirm that required configuration values and environment assumptions are valid."),
            new DeploymentTroubleshootingTip(
                "Zip deploy failure",
                "The deploy command reports a packaging or extraction error.",
                "Verify that the zip file was created from inside the publish directory and that the path passed to az webapp deploy is correct.")
        ];
    }
}
