# Functional Requirements Document

## Purpose

Create a beginner-friendly advanced unit testing sample that reuses a simple Razor Pages form and teaches how to test thrown exceptions and failure paths.

## Functional Requirements

1. The application must display a form on the home page.
2. The form must include a text field for `Name`.
3. The form must include a text field for `Email`.
4. The form must include a dropdown list for `Favourite Language`.
5. The dropdown list must contain `Unselected`, `Java`, `Python`, and `C#`.
6. The page must include a `Submit` button.
7. The submission service must throw `ArgumentException` when `Name` is missing.
8. The submission service must throw `ArgumentException` when `Email` is missing.
9. The submission service must throw `FormatException` when the email format is invalid.
10. The submission service must throw `InvalidOperationException` when the email is already registered.
11. The page must catch known exceptions and display a friendly error message instead of crashing.
12. After a valid submission, the page must display the submitted `Name`.
13. After a valid submission, the page must display the submitted `Email`.
14. After a valid submission, the page must display the submitted `Favourite Language`.
15. When no language is selected, the page must display `Unselected`.
16. The solution must include unit tests for successful results, thrown exceptions, and dependency failure propagation.

## Non-Functional Requirements

1. The code must remain simple enough for first-time learners.
2. Public C# members must include XML documentation comments.
3. The project must build with .NET 10.
4. The unit tests must use xUnit, FluentAssertions, and Moq.
5. The documentation must explain how to run the app and tests.

## Constraints

- Use ASP.NET Core Razor Pages for the sample web app.
- Keep the same simple form domain as the earlier unit testing projects.
- Use one async dependency for duplicate email checking.
- Keep the example small and focused on teaching exception testing.

## Success Criteria

- A student can run the Razor Pages app locally.
- A student can run the unit tests locally.
- The tests pass and clearly show how to test thrown exceptions and failure paths.
- The page displays the correct values after a valid submission.
