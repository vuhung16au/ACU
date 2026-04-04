# Quick Start Guide

## Prerequisites

- .NET 10 SDK
- GNU `make`
- Git
- Azure CLI for the manual deployment walkthrough
- An Azure subscription if you want to run the App Service deployment steps

## 1. Build the Lesson Solution

```bash
/usr/local/share/dotnet/dotnet build 50-CICD-GitHubActions/50-CICD-GitHubActions.sln
```

## 2. Run the Unit Tests

```bash
/usr/local/share/dotnet/dotnet test 50-CICD-GitHubActions/50-CICD-GitHubActions.sln
```

## 3. Run the Razor Pages App

```bash
/usr/local/share/dotnet/dotnet run --project 50-CICD-GitHubActions/01.RazorPagesCICDDemo/01.RazorPagesCICDDemo.csproj
```

Open the local URL shown in the terminal. The home page explains which steps GitHub Actions automates and which steps stay manual in this lesson.

## 4. Run the GitHub Actions Workflow Manually

The workflow file is intentionally configured with `workflow_dispatch` only, so it does not run on every push.

To run it manually on GitHub:

1. Push the workflow file and lesson content to a branch in GitHub.
2. Open the repository on GitHub.
3. Select the **Actions** tab.
4. Choose **dotnet-ci-main-demo-disabled** in the left sidebar.
5. Click **Run workflow**.
6. Select the branch you want to run.
7. Click the green **Run workflow** button to start the demo run.

This keeps the workflow available for demonstration while preventing automatic runs from every commit.

## 5. Rehearse the Workflow Locally

These are the same core commands used by the workflow:

```bash
make build-dotnet DOTNET=/usr/local/share/dotnet/dotnet
/usr/local/share/dotnet/dotnet test 50-CICD-GitHubActions/50-CICD-GitHubActions.sln -v minimal
/usr/local/share/dotnet/dotnet publish 50-CICD-GitHubActions/01.RazorPagesCICDDemo/01.RazorPagesCICDDemo.csproj -c Release -o ./artifacts/publish
```

## 6. GitLab CI/CD and Docker Alternative

If your team uses GitLab instead of GitHub, the same workflow can be demonstrated with GitLab CI/CD and Docker:

1. Create a `.gitlab-ci.yml` file at the repository root.
2. Use a Docker image such as `mcr.microsoft.com/dotnet/sdk:10.0` for the build and test jobs.
3. Run the same commands used in this lesson:

```bash
/usr/local/share/dotnet/dotnet restore 50-CICD-GitHubActions/50-CICD-GitHubActions.sln
make build-dotnet DOTNET=/usr/local/share/dotnet/dotnet
/usr/local/share/dotnet/dotnet test 50-CICD-GitHubActions/50-CICD-GitHubActions.sln -v minimal
/usr/local/share/dotnet/dotnet publish 50-CICD-GitHubActions/01.RazorPagesCICDDemo/01.RazorPagesCICDDemo.csproj -c Release -o ./artifacts/publish
```

4. Save `./artifacts/publish` as a GitLab artifact.
5. If you want a container-based deployment, add a Dockerfile, build an image from the published app, and push it to GitLab Container Registry.

This is the same CI/CD story with different tooling: GitHub Actions uses workflow YAML under `.github/workflows/`, while GitLab uses `.gitlab-ci.yml` plus Docker images for the runner environment.

## 7. Manual Azure App Service Deployment

### Sign in

```bash
az login
```

### Create a resource group

```bash
az group create --name rg-itec323-cicd-demo --location australiaeast
```

### Create an App Service plan

```bash
az appservice plan create \
  --name plan-itec323-cicd-demo \
  --resource-group rg-itec323-cicd-demo \
  --sku B1 \
  --is-linux
```

### Create the web app

Replace `<unique-app-name>` with a globally unique App Service name.

```bash
az webapp create \
  --name <unique-app-name> \
  --resource-group rg-itec323-cicd-demo \
  --plan plan-itec323-cicd-demo \
  --runtime "DOTNETCORE:10.0"
```

### Publish the app locally

```bash
/usr/local/share/dotnet/dotnet publish \
  50-CICD-GitHubActions/01.RazorPagesCICDDemo/01.RazorPagesCICDDemo.csproj \
  -c Release \
  -o ./artifacts/publish
```

### Zip the published output

```bash
cd artifacts/publish
zip -r ../01.RazorPagesCICDDemo.zip .
cd ../..
```

### Deploy the zip package

```bash
az webapp deploy \
  --resource-group rg-itec323-cicd-demo \
  --name <unique-app-name> \
  --src-path ./artifacts/01.RazorPagesCICDDemo.zip \
  --type zip
```

### Inspect logs

```bash
az webapp log tail --resource-group rg-itec323-cicd-demo --name <unique-app-name>
```

### Restart after a fix

```bash
az webapp restart --resource-group rg-itec323-cicd-demo --name <unique-app-name>
```

## Troubleshooting

**Issue**: The site fails to start after deployment.
**Solution**: Confirm the App Service runtime matches the .NET version used to publish the app.

**Issue**: The zip deploy command succeeds, but the app still looks outdated.
**Solution**: Recreate the publish output and zip only the contents of the publish folder, not the folder itself.

**Issue**: The deployment command fails because the source file cannot be found.
**Solution**: Check that `./artifacts/01.RazorPagesCICDDemo.zip` exists before running `az webapp deploy`.

**Issue**: The app returns HTTP 500 after deployment.
**Solution**: Start with `az webapp log tail` so students can identify whether the failure is package-related, runtime-related, or an app startup exception.
