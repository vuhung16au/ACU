# dotnet Commands Cheat Sheet

A quick one-line guide to the most common dotnet commands used in this repository.

## Most Used Commands

- `dotnet --version` - Show the current .NET SDK version.
  
- `dotnet --list-sdks` - List all installed SDK versions.
  

- `dotnet restore` - Download project dependencies from NuGet.
  
- `dotnet build` - Compile the project and check for compile errors.
  
- `dotnet run` - Run the app locally.
  
- `dotnet run --urls "https://localhost:5001;http://localhost:5000"` - Run on specific URLs/ports.
  
- `dotnet run --project /absolute/path/to/Project.csproj` - Run a specific nested project from anywhere.
  
## Useful Extra Commands

- `dotnet clean` - Remove previous build output (`bin/`, `obj/`) for a fresh rebuild.
- `dotnet watch run` - Run the app with auto-rebuild on file changes.
- `dotnet test` - Run unit tests in test projects/solutions.
- `dotnet new webapp` - Create a new Razor Pages project.
- `dotnet new sln` - Create a new solution file.
- `dotnet sln add <path-to-csproj>` - Add a project to a solution.
- `dotnet add package <PackageName>` - Add a NuGet package to a project.

## Typical Student Workflow

1. `dotnet --version`
2. `dotnet restore`
3. `dotnet build`
4. `dotnet run`

For nested projects, use:

`dotnet run --project /absolute/path/to/Project.csproj`
