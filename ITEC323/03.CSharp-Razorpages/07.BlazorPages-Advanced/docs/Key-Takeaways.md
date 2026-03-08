# Key takeaways: Blazor Advanced

## 1. Blazor vs Razor Pages

| | Razor Pages (06) | Blazor (07) |
|---|------------------|-------------|
| **Unit** | Page (`.cshtml` + `.cshtml.cs`) | Component (`.razor` + optional `.razor.cs`) |
| **Routing** | `@page` in view | `@page "/"` in component |
| **Data** | `OnGetAsync()` in page model | `OnInitializedAsync()` in `@code` |
| **UI updates** | Full postback or AJAX | SignalR (Interactive Server): no full reload |

## 2. Key Blazor concepts

- **`@page "/"`** – Route for the component.
- **`@rendermode InteractiveServer`** – Server-side interactivity (clicks, binding) over SignalR.
- **`@inject AppDbContext DbContext`** – Dependency injection (same as in Razor Pages).
- **`@code { ... }`** – C# block: properties, `OnInitializedAsync`, methods.
- **`OnInitializedAsync()`** – Runs when the component is first rendered; use for loading data.

## 3. EF Core + SQLite (same as 06)

- **DbContext** and **DbSet&lt;User&gt;** – Same pattern.
- **EnsureCreated()** in `Program.cs` – Creates DB and applies seed data.
- **Async queries** – `await DbContext.Users.ToListAsync()` in Blazor as in Razor Pages.

## 4. JSON and rendering

- Use **`System.Text.Json.JsonSerializer.Serialize()`** in the component.
- **`@foreach`** and **`@if`** – Same Razor syntax as in 06.

## 5. Layout

- **MainLayout.razor** – Wraps all pages (`@Body`).
- **NavMenu.razor** – Nav links; included in the layout.
- **Scoped CSS** – `MainLayout.razor.css`, `NavMenu.razor.css` apply to that component.

## 6. Project creation

```bash
dotnet new blazor -n BlazorPagesAdvanced -o 07.BlazorPages-Advanced
```

Add packages: `Microsoft.EntityFrameworkCore.Sqlite`, `Microsoft.EntityFrameworkCore.Design`. Register `DbContext`, call `EnsureCreated()`, add `Models/`, `Data/`, and pages.

## Best practices (same as 06)

- Async for all DB access.
- Inject `DbContext`; don’t create it manually.
- Connection string in `appsettings.json`.
- Keep components focused; reuse layout and nav.
