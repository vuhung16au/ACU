# QUICKSTART — 04.HtmxPartialUpdates

## Prerequisites

| Tool | Version |
| --- | --- |
| .NET SDK | 8.0 or later (project targets .NET 10) |
| Browser | Chrome, Edge, Firefox, or Safari (any modern version) |
| Internet | Required — HTMX is loaded from CDN |

No Node.js, no npm, no Bootstrap.  
HTMX is loaded entirely from a CDN link in `_Layout.cshtml`.

## Run the project

```bash
cd 08.ClientSideInteractivity/04.HtmxPartialUpdates

dotnet run
```

Then open the URL shown in the terminal (typically `https://localhost:5001` or `http://localhost:5000`).

## Build only (no run)

```bash
dotnet build
```

## URLs once running

| URL | What you see |
| --- | --- |
| `/` | Module landing page with learning objectives |
| `/HtmxLab` | CRUD table with add, inline edit, and delete |
| `/Privacy` | Privacy policy stub |

## Classroom walkthrough (4 steps)

1. **Open the HTMX Lab** — three seed students appear in the table.
2. **DevTools → Network tab** — click Edit on any row and observe that only a small HTML fragment is returned, not the full page.
3. **Add a student** — fill in the form at the bottom and click "Add Student". The new row appears at the top without a page reload.
4. **Delete a student** — click Delete, confirm the dialog, and watch the row disappear via `hx-swap="outerHTML"` with an empty response.

## Troubleshooting

| Problem | Fix |
| --- | --- |
| HTMX requests do nothing | Check the browser console for 400/404 errors; ensure `?handler=` matches the method name |
| Antiforgery token errors (400) | HTMX POSTs include form data but not the CSRF token by default; the project uses `[IgnoreAntiforgeryToken]` on the page or passes the token via a hidden field |
| Page styling looks broken | Run `dotnet run` (not just `dotnet watch`); static files are served from `wwwroot/` |
| `dotnet run` fails | Run `dotnet restore` first, then `dotnet run` |

## Antiforgery note

ASP.NET Core requires an antiforgery token on POST requests.
The HTMX Lab page includes it automatically because the forms use standard Razor
tag helpers. For programmatic `hx-post` buttons (Edit/Save/Delete), the project
adds `@Html.AntiForgeryToken()` hidden field in the layout's Scripts section, or
the page models use `[IgnoreAntiforgeryToken]` where appropriate for the lab context.
In a real application you would always validate CSRF tokens.
