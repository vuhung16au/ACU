# Functional Requirements Document

## Purpose

Create a beginner-friendly sample that demonstrates how a React frontend and ASP.NET Core backend work together by using `Microsoft.JavaScript.Templates` with `reactwebapi` on `.NET 10`.

## Functional Requirements

1. The sample must provide a React frontend and an ASP.NET Core backend in one teaching example.
2. The backend must expose a small JSON endpoint that returns starter content for the React page.
3. The backend must expose a second endpoint that accepts learner input and returns a personalized response.
4. The React page must load overview data when the app starts.
5. The React page must allow the user to trigger a backend POST request from a form.
6. The React page must show loading, success, and error states clearly.
7. The project documentation must explain both local development flow and published deployment flow.
8. The project documentation must compare React and Angular for beginner ASP.NET Core integration scenarios.
9. The solution must include automated tests for the small backend service logic.

## Non-Functional Requirements

1. The project must target `.NET 10.0` on the ASP.NET Core side.
2. The code must remain simple enough for beginners to follow.
3. The sample must avoid databases, authentication, and advanced frontend state management.
4. The UI must remain usable on both desktop and smaller screens.

## Success Criteria

- `dotnet build` succeeds for the module
- `dotnet test` succeeds for the backend tests
- students can run the app locally and see React data loaded from ASP.NET Core
- students can publish the app and run the integrated output successfully
- students can compare the React sample with `33-SPA-Integration`
