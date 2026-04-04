# Quick Start Guide

## 1. Restore Dependencies

```bash
/usr/local/share/dotnet/dotnet restore
```

## 2. Build the Project

```bash
/usr/local/share/dotnet/dotnet build
```

## 3. Run the Application

```bash
/usr/local/share/dotnet/dotnet run
```

## 4. Run the Tests

```bash
/usr/local/share/dotnet/dotnet test tests/01.RazorPagesCICDDemo.Tests/01.RazorPagesCICDDemo.Tests.csproj
```

## 5. Run the Workflow Manually on GitHub

This lesson's workflow is a manual demo. It does not run on push.

1. Open the repository on GitHub.
2. Go to **Actions**.
3. Open **dotnet-ci-main-demo-disabled**.
4. Click **Run workflow**.
5. Pick the branch you want to demonstrate.
6. Start the run.

## Expected Result

The home page should show a CI/CD overview with:

- GitHub Actions workflow stages
- Azure CLI deployment commands
- a clear note that deployment is manual in this lesson

The troubleshooting page should show common Azure deployment issues and fixes.

## Troubleshooting

**Issue**: The site does not start locally.
**Solution**: Check that the .NET 10 SDK is installed and restore packages before running.

**Issue**: Tests fail after editing the teaching services.
**Solution**: Compare the stage names and command text with the expected assertions in the test project.
