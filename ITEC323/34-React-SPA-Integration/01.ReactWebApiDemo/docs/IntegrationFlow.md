# Integration Flow Notes

## Request Flow

This sample is designed so students can trace a single request end to end:

1. The React component starts and calls `fetch`.
2. The browser sends an HTTP request to ASP.NET Core.
3. The ASP.NET Core controller receives the request and uses `SpaLessonService`.
4. `SpaLessonService` returns plain C# objects.
5. ASP.NET Core serializes those objects to JSON.
6. React receives the JSON and updates component state.

## Development Mode

In local development:

- the frontend uses the React Vite development server
- ASP.NET Core provides the backend API
- the development setup forwards API requests so the React app can call the backend cleanly

## Published Mode

After publish:

- React builds static browser assets
- ASP.NET Core serves those files
- the same ASP.NET Core app also serves the API routes

This lets students compare a two-process development experience with a single deployed application.
