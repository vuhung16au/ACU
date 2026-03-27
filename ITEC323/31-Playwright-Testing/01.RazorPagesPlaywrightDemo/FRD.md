# Functional Requirements Document

## Purpose

Create a beginner-friendly sample that shows how Playwright can automate a Razor Pages form, capture a screenshot, and record browser actions.

## Functional Requirements

1. The application must display a form on the home page.
2. The form must include a text field for `Name`.
3. The form must include a text field for `Email`.
4. The form must include a dropdown list for `Favourite Language`.
5. The dropdown list must contain `Unselected`, `Java`, `Python`, and `C#`.
6. The page must include a `Submit` button.
7. After the form is submitted, the page must display the submitted `Name`.
8. After the form is submitted, the page must display the submitted `Email`.
9. After the form is submitted, the page must display the submitted `Favourite Language`.
10. The solution must include an automated Playwright test for the form submission flow.
11. The Playwright test must save a screenshot after the form is submitted.
12. The Playwright test must save a recorded browser session as a `.webm` file.
13. The solution should create a `.gif` version of the recorded browser session when `ffmpeg` is available.

## Non-Functional Requirements

1. The code must remain simple enough for first-time learners.
2. Public C# members must include XML documentation comments.
3. The project must build with .NET 10.
4. The pages must remain readable on phone and desktop screen sizes.
5. The documentation must explain how to run the app and the automated test.

## Constraints

- Use ASP.NET Core Razor Pages for the sample web app.
- Use Playwright for browser automation.
- Keep the example small and focused on teaching.

## Success Criteria

- A student can run the Razor Pages app locally.
- A student can run the Playwright test locally.
- The test produces a screenshot and a `.webm` recording.
- The submitted values shown on screen match the test input.
