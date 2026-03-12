# Quick Start

## Run

```bash
cd 05.MultipleLayouts
dotnet restore
dotnet build
dotnet run
```

Open:

- `https://localhost:5001`
- `http://localhost:5000`

## Check These Pages

1. `/` uses the public layout.
2. `/Admin` uses the admin layout from the folder-level `_ViewStart.cshtml`.
3. `/Print` uses a page-level layout override.
4. `/About` shows optional sections in the public layout.
