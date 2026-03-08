# Quick start (macOS / VS Code)

## Prerequisites

- .NET 10.0 SDK (`dotnet --version` → 10.0.x)
- Terminal or VS Code integrated terminal

## 1. Go to project folder

```bash
cd /path/to/ITEC323/03.CSharp-Razorpages/07.BlazorPages-Advanced
```

## 2. Restore and build

```bash
dotnet restore
dotnet build
```

Expect: `Build succeeded`.

## 3. Run

```bash
dotnet run
```

Note the URLs (e.g. `https://localhost:5001`, `http://localhost:5000`).

## 4. Open in browser

Open the HTTPS URL. You should see **User Directory**, a table of 5 users, and a JSON block.

## 5. Stop

`Ctrl+C` (or `Cmd+C` on macOS) in the terminal.

## Create from scratch (optional)

To recreate the project:

```bash
cd 03.CSharp-Razorpages
dotnet new blazor -n BlazorPagesAdvanced -o 07.BlazorPages-Advanced
cd 07.BlazorPages-Advanced
```

Then add EF Core, Models, Data, and pages as in this repo.

## Troubleshooting

- **Port in use**: Change ports in `Properties/launchSettings.json`.
- **Database locked**: Stop the app, delete `users.db`, run again.
- **Build errors**: `dotnet clean && dotnet restore && dotnet build`.
