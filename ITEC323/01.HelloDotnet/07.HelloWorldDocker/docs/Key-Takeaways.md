# Key Takeaways

1. Docker can package a .NET web app into a portable container image.
2. Multi-stage Dockerfiles keep final images smaller by separating build and runtime.
3. mcr.microsoft.com/dotnet/sdk:10.0 is used to build and publish the app.
4. mcr.microsoft.com/dotnet/aspnet:10.0 is used to run the published app.
5. Mapping ports with -p 8080:8080 allows browser access on localhost.
6. Razor Pages is a good beginner starting point for server-rendered .NET web apps.
7. Simple project structure and clear docs reduce learning friction for first-time users.
