# Key Takeaways - React Basics

## Core concepts

- React functional components return JSX.
- `useState` stores local component state and triggers rerenders.
- `useEffect` is used for side effects such as API calls.
- Controlled forms keep input values in React state.
- Backend APIs can be consumed safely using a Vite dev proxy and CORS.

## Practical patterns used in this module

- Loading state and error state for async data fetching.
- Simple component composition (`App` -> `LessonCard`, `AddLessonForm`).
- POST request to add a record and update UI optimistically.

## Suggested practice

1. Add client-side validation for empty/short summaries.
2. Add a lesson level filter (`Beginner`, `Intermediate`, `Advanced`).
3. Add delete support in backend and React UI.
4. Compare this flow with `06.BlazorInteractive` to see trade-offs.
