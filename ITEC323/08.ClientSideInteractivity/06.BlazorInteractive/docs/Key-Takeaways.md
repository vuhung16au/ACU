# Key Takeaways - Blazor Interactive

## Core ideas

- Blazor components keep UI state in C# fields and rerender automatically on state changes.
- `@onclick` wires browser events directly to C# methods.
- `@bind` creates two-way synchronization between inputs and component state.
- Cascading values are useful for sharing context without passing many parameters.
- Components can call backend endpoints with `HttpClient` to load dynamic data.

## Practical guidance

- Keep components focused: one interaction pattern per component while learning.
- Use host pages to compose multiple interactive islands.
- Prefer strongly typed DTOs for API payloads.
- Handle loading and error states explicitly for better UX.

## Suggested exercises

1. Add validation to the planner wizard.
2. Add sorting to announcements by date/title.
3. Add a reusable card component for all islands.
