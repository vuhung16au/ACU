# React vs Angular with ASP.NET Core

## Short Summary

Both React and Angular can integrate well with ASP.NET Core:

- React is often lighter and more flexible for teams that want to choose their own patterns.
- Angular is often more structured for teams that want an all-in-one framework with strong conventions.

## React Pros

- smaller mental model for simple UI projects
- flexible composition with components and hooks
- easy to add to projects that only need a focused interactive frontend
- often a good fit when teams want more control over library choices

## React Cons

- fewer built-in decisions, so teams may need to choose routing, state, and form patterns
- project structure can become inconsistent without team conventions
- beginners may need help understanding how multiple React libraries fit together

## Angular Pros

- strong built-in structure for components, services, routing, and forms
- TypeScript-first development out of the box
- dependency injection and service patterns are very explicit
- often easier to keep large teams aligned around one framework style

## Angular Cons

- larger framework to learn at the start
- more files and framework concepts for small features
- can feel heavier than necessary for smaller interactive pages

## Common Use Cases

Choose React when:

- you want a lightweight frontend shell over an ASP.NET Core API
- the app is component-focused and you want flexible library choices
- you want to teach state updates and UI rendering with fewer framework abstractions

Choose Angular when:

- you want a more opinionated framework with services and dependency injection built in
- the team benefits from stronger structure and consistent conventions
- you want to teach a full frontend framework alongside ASP.NET Core

## Integration with ASP.NET Core

Both approaches usually follow the same high-level integration pattern:

1. The frontend runs its own development server in local development.
2. The frontend sends HTTP requests to ASP.NET Core API routes.
3. ASP.NET Core returns JSON responses.
4. For publish, ASP.NET Core serves the built static frontend files.

## Key Comparison in This Repository

Compare these two modules directly:

- `33-SPA-Integration` uses Angular services and Angular component binding.
- `34-React-SPA-Integration` uses React state, event handlers, and `fetch`.

The backend responsibilities stay intentionally similar so students can focus on how the frontend framework changes the development style.
