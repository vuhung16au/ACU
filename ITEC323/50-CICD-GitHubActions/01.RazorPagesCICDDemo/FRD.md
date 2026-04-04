# Functional Requirements Document

## Purpose

Create a small Razor Pages app that teaches the shape of a CI/CD workflow while supporting unit tests and manual Azure deployment notes.

## Functional Requirements

1. The home page must explain the workflow trigger.
2. The home page must list the automated GitHub Actions stages.
3. The home page must identify the manual deployment step clearly.
4. The home page must list Azure CLI commands for Azure App Service deployment.
5. The troubleshooting page must list common deployment failures and fixes.
6. The project must include unit tests for the service classes that provide workflow and deployment content.

## Non-Functional Requirements

1. The project must stay simple enough for students new to CI/CD.
2. Public C# members must include XML documentation comments.
3. The project must build with .NET 10.
4. The tests must use xUnit and FluentAssertions.

## Constraints

- Use ASP.NET Core Razor Pages.
- Keep the project read-only from a deployment perspective; it teaches concepts rather than collecting user input.
- Focus on Azure App Service and Azure CLI rather than multiple hosting targets.

## Success Criteria

- Students can run the app and read the workflow story from the UI.
- Students can run the tests and connect them to the teaching services.
- Students can identify the manual deployment boundary without extra explanation.
