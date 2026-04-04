# Functional Requirements Document

## Purpose

Create a beginner-friendly CI/CD lesson that teaches how GitHub Actions can build and test a .NET repository while leaving Azure App Service deployment as a deliberate manual CLI exercise.

## Functional Requirements

1. The module must include a new `50-CICD-GitHubActions` folder.
2. The module must include a Razor Pages sample application named `01.RazorPagesCICDDemo`.
3. The sample application must explain the difference between automated CI stages and manual deployment stages.
4. The sample application must show that the lesson workflow is triggered manually from GitHub.
5. The sample application must explain that the workflow builds the repository and runs unit tests.
6. The sample application must show that GitHub Actions publishes a deployable artifact.
7. The module must include unit tests for the lesson services.
8. The module must include a `.github/workflows/dotnet-ci-main.yml` workflow file.
9. The workflow must use `workflow_dispatch` so it runs only when started manually.
10. The workflow must build the repository by using the established `make build-dotnet` path.
11. The workflow must run unit tests only for the CI/CD lesson solution.
12. The workflow must publish the Razor Pages app as an artifact.
13. The workflow must not perform a live Azure deployment.
14. The documentation must teach manual Azure App Service deployment with Azure CLI.
15. The documentation must teach log inspection and restart commands for troubleshooting.

## Non-Functional Requirements

1. The lesson must remain approachable for students who are new to CI/CD.
2. Public C# members must include XML documentation comments.
3. The code must build with .NET 10.
4. The tests must use xUnit and FluentAssertions.
5. The documentation must clearly describe the boundary between CI automation and manual deployment.

## Constraints

- Use ASP.NET Core Razor Pages for the sample application.
- Use Azure App Service for the manual deployment example.
- Keep deployment outside GitHub Actions in this lesson.
- Follow the repository convention of including `README.md`, `QUICKSTART.md`, and `FRD.md`.

## Success Criteria

- Students can run the lesson app locally and explain the workflow stages.
- Students can run the lesson unit tests locally.
- Students can read the GitHub Actions YAML and connect it to the commands they use on their machine.
- Students can describe how to start the workflow manually from the GitHub Actions UI.
- Students can follow the Azure CLI sequence to deploy the published app manually.
- Students can name at least two common deployment troubleshooting checks.
