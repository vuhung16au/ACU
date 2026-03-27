# Functional Requirements Document

## Purpose

Create a beginner-friendly advanced unit testing sample that reuses a simple Razor Pages form and introduces an asynchronous dependency for duplicate-email checking.

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
10. `Email` must be checked asynchronously against an email registry for duplicates.
11. When the email already exists, the page must display `Email is already registered.`
12. After a valid submission, the page must display the submitted `Name`.
13. After a valid submission, the page must display the submitted `Email`.
14. After a valid submission, the page must display the submitted `Favourite Language`.
15. When no language is selected, the page must display `Unselected`.
16. The solution must include unit tests for async validation, result processing, and mocked async dependency interactions.

## Non-Functional Requirements

1. The code must remain simple enough for first-time learners.
2. Public C# members must include XML documentation comments.
3. The project must build with .NET 10.
4. The unit tests must use xUnit, FluentAssertions, and Moq.
5. The documentation must explain how to run the app and tests.

## Constraints

- Use ASP.NET Core Razor Pages for the sample web app.
- Use xUnit for unit testing.
- Use one async mockable interface for the advanced concept.
- Keep the example small and focused on teaching.

## Success Criteria

- A student can run the Razor Pages app locally.
- A student can run the unit tests locally.
- The tests pass and clearly show async result testing and async interaction testing.
- The page displays the correct values after a valid submission.
