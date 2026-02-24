# Hello ASP.NET

This document describes the AspNetHelloWorld project: what it is, how it’s built, and how to run it.

## What is AspNetHelloWorld?

AspNetHelloWorld is a minimal ASP.NET Core web app that:

- Listens on **http://localhost:5150** (and https://localhost:7170 when using the HTTPS profile)
- Responds to `GET /` with the text **"Hello World!"**
- Uses .NET **minimal APIs** (no MVC, no Razor, no controllers)

It’s a good starting point for learning ASP.NET Core or for a tiny HTTP service.

## How to build and run

### Using the existing project

```bash
cd AspNetHelloWorld
dotnet run
```

Then open **http://localhost:5150** in a browser.

### Creating the project from scratch

```bash
dotnet new web -n AspNetHelloWorld
cd AspNetHelloWorld
dotnet run
```

Again, open **http://localhost:5150**.

### One-time setup (restore and build)

```bash
dotnet restore
dotnet build
dotnet run
```

## How it works

The app is defined in `Program.cs`:

1. **Create the app builder** — `WebApplication.CreateBuilder(args)` loads configuration (e.g. `appsettings.json`) and sets up the host.
2. **Build the app** — `builder.Build()` creates the `WebApplication`.
3. **Map the route** — `app.MapGet("/", () => "Hello World!")` registers a GET handler for `/` that returns the string `"Hello World!"`.
4. **Run** — `app.Run()` starts the Kestrel server and listens for HTTP requests.

No controllers, no views—just one route and a response.

## URLs and launch profiles

- **HTTP:** http://localhost:5150  
- **HTTPS (when used):** https://localhost:7170  

These come from `Properties/launchSettings.json`. To use HTTPS by default, run with the `https` profile:

```bash
dotnet run --launch-profile https
```

## Next steps

- Add more routes with `MapGet`, `MapPost`, etc.
- Use `app.MapGet("/path", (HttpContext ctx) => ...)` to read query strings or headers.
- Add services in `Program.cs` with `builder.Services.Add...` and use dependency injection in route handlers.
- See [QUICKSTART.md](../QUICKSTART.md) for the shortest run instructions and [README.md](../README.md) for project overview and structure.
