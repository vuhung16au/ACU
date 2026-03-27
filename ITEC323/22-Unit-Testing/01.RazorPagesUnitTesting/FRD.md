# Functional Requirements Document

## Purpose

Create a beginner-friendly sample that teaches unit testing in .NET by using a simple Razor Pages form and small C# classes for validation and submission processing.

## Functional Requirements

1. The application must display a form on the home page.
2. The form must include a text field for `Name`.
3. The form must include a text field for `Email`.
4. The form must include a dropdown list for `Favourite Language`.
5. The dropdown list must contain `Unselected`, `Java`, `Python`, and `C#`.
6. The page must include a `Submit` button.
7. `Name` must be required.
8. `Email` must be required.
9. `Email` must be checked for a simple valid email format.
10. After a valid submission, the page must display the submitted `Name`.
11. After a valid submission, the page must display the submitted `Email`.
12. After a valid submission, the page must display the submitted `Favourite Language`.
13. When no language is selected, the page must display `Unselected`.
14. The solution must include unit tests for validation and result processing behavior.

## Non-Functional Requirements

1. The code must remain simple enough for first-time learners.
2. Public C# members must include XML documentation comments.
3. The project must build with .NET 10.
4. The unit tests must use xUnit and FluentAssertions.
5. The documentation must explain how to run the app and tests.

## Constraints

- Use ASP.NET Core Razor Pages for the sample web app.
- Use xUnit for unit testing.
- Keep the example small and focused on teaching.
- Prefer plain classes over complex mocking frameworks.

## Success Criteria

- A student can run the Razor Pages app locally.
- A student can run the unit tests locally.
- The tests pass and clearly show the behavior being checked.
- The page displays the correct values after a valid submission.
