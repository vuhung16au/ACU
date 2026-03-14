# 04.HtmxPartialUpdates

Week 8, Project 04 — **HTMX Partial Updates**  
Part of the [08.ClientSideInteractivity](../) module for ITEC323.

## What this project teaches

HTMX lets you replace parts of a page with server responses using only HTML attributes.
There is no custom JavaScript to write — the browser handles everything.

| HTMX attribute | What it does |
| --- | --- |
| `hx-get` | Send a GET request and load the response into the target |
| `hx-post` | Send a POST request (create, update, or delete) |
| `hx-target` | CSS selector for the element that receives the response |
| `hx-swap` | How to insert the response (`innerHTML`, `outerHTML`, `beforeend`, …) |
| `hx-indicator` | Selector for a loading spinner shown while the request is in-flight |
| `hx-confirm` | Show a confirm dialog before sending |

## Screenshots / Demos

![HTMX Partial Updates Demo](images/htmlx-partial-update-demo.gif)

## Learning Objectives

By the end of this project students will be able to:

1. Add HTMX to an ASP.NET Core Razor Pages project via CDN.
2. Use `hx-get` and `hx-post` to make HTTP requests from HTML elements.
3. Use `hx-target` and `hx-swap` to control where and how server responses appear.
4. Return partial views (HTML fragments) from Razor Pages handler methods.
5. Implement a full CRUD table with inline row editing — no page reloads.
6. Display a loading indicator during in-flight HTMX requests.
7. Use `hx-confirm` to prompt users before destructive actions.

## Project structure

```text
04.HtmxPartialUpdates/
├── 04.HtmxPartialUpdates.csproj
├── Program.cs                         # Service registration, middleware
├── Models/
│   └── Student.cs                     # Data model for CRUD table
├── Services/
│   └── StudentService.cs              # In-memory CRUD store
├── Pages/
│   ├── Index.cshtml / .cs             # Module landing page
│   ├── HtmxLab.cshtml / .cs          # CRUD lab page + HTMX handler methods
│   ├── Partials/
│   │   ├── _StudentRow.cshtml         # Read-mode table row
│   │   └── _StudentEditRow.cshtml     # Edit-mode table row (inline form)
│   └── Shared/
│       └── _Layout.cshtml             # Shared layout — includes HTMX via CDN
└── wwwroot/css/site.css               # Custom stylesheet (no Bootstrap)
```

## How the CRUD flow works

```text
User clicks Edit
  → browser sends   GET /HtmxLab?handler=EditRow&id=1
  → server returns  HTML fragment (_StudentEditRow partial)
  → HTMX swaps      the <tr> outerHTML → inputs appear inline

User clicks Save
  → browser sends   POST /HtmxLab?handler=Update&id=1  (form values in body)
  → server returns  HTML fragment (_StudentRow partial)
  → HTMX swaps      the <tr> back to read mode

User clicks Delete
  → hx-confirm      shows browser confirm dialog
  → browser sends   POST /HtmxLab?handler=Delete&id=1
  → server returns  empty body (200 OK)
  → HTMX swaps      outerHTML with nothing → row disappears

User submits Add form
  → browser sends   POST /HtmxLab?handler=Create
  → server returns  HTML fragment (_StudentRow partial for new student)
  → HTMX inserts    at the top of the table body (hx-swap="afterbegin")
```

## Technology stack

- **Runtime:** .NET 10 (Razor Pages)
- **HTMX:** 1.9.12 via CDN — `https://unpkg.com/htmx.org@1.9.12/dist/htmx.min.js`
- **CSS:** Custom stylesheet, no Bootstrap, no jQuery

## See also

- [QUICKSTART.md](QUICKSTART.md) — how to run the project
- [docs/Key-Takeaways.md](docs/Key-Takeaways.md) — what to remember
- [HTMX Documentation](https://htmx.org/docs/)
- [HTMX with ASP.NET tutorial](https://htmx.org/server-examples/)
