# 06. Blazor Interactive

This module demonstrates how to embed interactive Blazor components inside a Razor Pages application. It focuses on C#-driven UI updates, data binding, component composition, and API data loading.

## Learning goals

- Understand event handling with `@onclick`
- Use two-way binding with `@bind`
- Build a small multi-step interactive flow
- Share data between components via cascading values
- Fetch server data from a Blazor component

## Demo

Open the Blazor lab page after running the app:

- `/BlazorLab`

![Blazor Interactive Demo](images/blazor-interactive.gif)

## Project structure highlights

- `Pages/BlazorLab.cshtml`: host page for interactive islands
- `Components/CounterIsland.razor`: event handling and local state
- `Components/GradeCalculatorIsland.razor`: `@bind` and derived values
- `Components/PlannerWizardIsland.razor`: multi-step interaction
- `Components/CascadingContextIsland.razor`: cascading value demo
- `Components/AnnouncementsIsland.razor`: API fetch from `/api/announcements`
- `Services/BlazorDemoService.cs`: mock data source for announcements

## Notes

This project uses server-side Blazor interactivity in a Razor Pages app and includes the Blazor server script in the shared layout.
