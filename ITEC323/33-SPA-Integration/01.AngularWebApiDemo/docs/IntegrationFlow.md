# Integration Flow Notes

## Request Flow

This sample is designed so students can trace a single request end to end:

1. The Angular component starts and calls `SpaDemoService`.
2. `SpaDemoService` sends an HTTP request to ASP.NET Core.
3. The ASP.NET Core controller receives the request and uses `SpaLessonService`.
4. `SpaLessonService` returns plain C# objects.
5. ASP.NET Core serializes those objects to JSON.
6. Angular receives the JSON and updates the screen.

## Development Mode

In local development:

- the frontend uses the Angular development server
- ASP.NET Core provides the backend API
- the development setup forwards API requests so the Angular app can call the backend cleanly

## Published Mode

After publish:

- Angular builds static browser assets
- ASP.NET Core serves those files
- the same ASP.NET Core app also serves the API routes

This lets students compare a two-process development experience with a single deployed application.
