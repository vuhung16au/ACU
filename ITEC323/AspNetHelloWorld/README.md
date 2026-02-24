# AspNetHelloWorld

A minimal ASP.NET Core web application that serves "Hello World!" at the root URL. Built with .NET 10 and minimal APIs.

## What this project does

- Single endpoint: `GET /` returns `"Hello World!"`
- No MVC, no Razor—just a lightweight web app using `WebApplication` and `MapGet`

## Quick start

See **[QUICKSTART.md](QUICKSTART.md)** for the shortest path to run the app.

## Documentation

- [Hello ASP.NET](docs/HelloASP.NET.md) — Overview of this project and minimal ASP.NET Core

## Requirements

- [.NET 10 SDK](https://dotnet.microsoft.com/download) (or .NET 8+; adjust target in the project file if needed)

## Build and run (from project folder)

```bash
dotnet restore
dotnet build
dotnet run
```

Then open **http://localhost:5150** in a browser. You should see `Hello World!`

## Create from scratch

To recreate this project from scratch:

```bash
dotnet new web -n AspNetHelloWorld
cd AspNetHelloWorld
dotnet run
```

Then visit **http://localhost:5150**.

## Project structure

| Path | Purpose |
|------|---------|
| `Program.cs` | App entry point and minimal API (`MapGet("/", ...)`) |
| `AspNetHelloWorld.csproj` | Project file (targets `net10.0`) |
| `appsettings.json` | Default configuration |
| `appsettings.Development.json` | Development overrides |
| `Properties/launchSettings.json` | Launch profiles and URLs |

## License

Use as needed for learning or reference.
