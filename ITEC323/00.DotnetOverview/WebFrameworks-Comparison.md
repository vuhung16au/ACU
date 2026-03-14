# ASP.NET Core Web Frameworks: A Comparison

This document compares the three main ASP.NET Core web frameworks you will encounter in this unit:
**ASP.NET Core MVC**, **ASP.NET Core Razor Pages**, and **Blazor**.

Understanding when to use each one is a key skill for any .NET web developer.

---

## Quick Summary

| Framework | Pattern | Rendering | Best For |
|---|---|---|---|
| ASP.NET Core MVC | Model-View-Controller | Server-Side | Large, team-separated apps |
| Razor Pages | Page-Focused | Server-Side | CRUD sites, form-heavy apps |
| Blazor | Component-Based SPA | Server or Client (WASM) | Rich, interactive SPAs |

---

## Detailed Comparison

| Feature / Aspect | ASP.NET Core MVC | ASP.NET Core Razor Pages | Blazor (Web App / WASM / Server) |
| --- | --- | --- | --- |
| **Architectural Pattern** | **Model-View-Controller.** The app is split into three distinct roles. Controllers handle routing and logic, Models hold data, and Views display the UI. | **Page-Focused.** Uses a Page and a `PageModel` (code-behind). Each page encapsulates its own routing, UI, and logic. | **Component-Based SPA.** The UI is built using reusable, stateful components (similar to React or Angular), completely decoupling the UI from traditional page routes. |
| **Execution & Rendering** | **Server-Side Rendering (SSR).** The server processes the request, generates raw HTML, and sends it to the browser. | **Server-Side Rendering (SSR).** The server processes the request, generates raw HTML, and sends it to the browser. | **Client-Side or Server-Side.** Runs either directly in the browser via WebAssembly (WASM) or on the server with UI updates sent via WebSockets (SignalR). |
| **UI Interactivity** | **Requires JavaScript.** Once the HTML is delivered, any dynamic behavior (modals, live validation) requires JS. | **Requires JavaScript.** Similar to MVC, the resulting page is static HTML until enhanced with JS. | **C# Driven.** You write C# to handle button clicks, DOM updates, and state management directly in the browser — no JS required. |
| **State Management** | **Stateless.** Relies on traditional HTTP mechanisms (Cookies, TempData, Session) across page reloads. | **Stateless.** Relies on traditional HTTP mechanisms (Cookies, TempData, Session) across page reloads. | **Stateful.** Variables and component state remain in memory while the user interacts with the application, as the page never fully reloads. |
| **File Structure** | Separated by concern: `/Controllers`, `/Models`, `/Views`. (Requires jumping between folders to understand one feature). | Organized by feature: `/Pages`. The UI (`.cshtml`) and the logic (`.cshtml.cs`) live together in the same folder. | Organized by components: `/Components`. UI and logic live in the same `.razor` file, or a closely coupled code-behind. |
| **Best Used For...** | Massive, complex applications where the UI team and backend API team are completely separate. | Standard websites, CRUD applications, and form-heavy sites. **(This is the ideal starting point for the current labs in this unit.)** | Highly interactive, dashboard-style Single Page Applications (SPAs) where you want a rich, desktop-like experience in the browser. |
| **Learning Curve** | **High.** Students must understand the complex routing mechanics of how Controllers pass data to Views. | **Low to Medium.** Very intuitive for beginners because the URL route directly matches the physical file path of the page. | **Medium to High.** Requires understanding component lifecycles, state management, and the nuances of WebAssembly vs. Server rendering. |

---

## Framework Deep Dives

### 1. ASP.NET Core MVC

MVC separates an application into three layers, each with a distinct responsibility:

- **Model** — represents the data and business rules (e.g., a `Student` class).
- **View** — the `.cshtml` template that renders HTML to the user.
- **Controller** — receives HTTP requests, coordinates the Model, and selects the View.

```
/Controllers
    StudentController.cs   ← handles /Student/Index, /Student/Details, etc.
/Models
    Student.cs
/Views
    /Student
        Index.cshtml
        Details.cshtml
```

**When to choose MVC**: Large teams where front-end and back-end work is clearly divided, or when building an API backend alongside a web UI.

---

### 2. ASP.NET Core Razor Pages

Razor Pages uses a **page-centric** model. Every page has two files kept together:

- `Index.cshtml` — the HTML template (using Razor syntax).
- `Index.cshtml.cs` — the `PageModel` code-behind with handlers like `OnGet()` and `OnPost()`.

```
/Pages
    Index.cshtml            ← renders the home page at URL: /
    Index.cshtml.cs
    /Students
        Index.cshtml        ← URL: /Students
        Details.cshtml      ← URL: /Students/Details
        Edit.cshtml         ← URL: /Students/Edit
```

**Key insight**: The URL path mirrors the file path directly. This makes navigation predictable and easy for beginners.

**When to choose Razor Pages**: Standard CRUD websites, admin dashboards, form-driven applications — the majority of web projects.

---

### 3. Blazor

Blazor lets you write **interactive web UIs entirely in C#**, avoiding JavaScript for most tasks.

There are three hosting models:

| Hosting Model | Where Code Runs | Notes |
|---|---|---|
| **Blazor Server** | On the server | UI events sent over WebSocket (SignalR). Requires a constant server connection. |
| **Blazor WebAssembly (WASM)** | In the browser | C# compiled to WASM; runs offline. Larger initial download. |
| **Blazor Web App (.NET 10+)** | Both (per-component) | The modern default. You choose interactivity per component. |

A Blazor component (`.razor` file) combines template and logic:

```razor
@* Counter.razor *@
<h3>Count: @count</h3>
<button @onclick="Increment">Click me</button>

@code {
    private int count = 0;

    private void Increment()
    {
        count++;  // UI updates automatically — no JavaScript needed
    }
}
```

**When to choose Blazor**: Real-time dashboards, interactive forms with live feedback, SPAs where you want to stay in C# throughout.

---

## Which Framework Should You Learn First?

For this unit (ITEC323), the recommended learning path is:

1. **Start with Razor Pages** — the labs in weeks 3–8 use Razor Pages because it is the most approachable framework for beginners and covers 80% of real-world web scenarios.
2. **Explore MVC** — once you are comfortable with Razor Pages, MVC concepts (Controllers, Views) will feel familiar.
3. **Try Blazor** — the advanced labs introduce Blazor components, building on the C# skills you already have.

---

## Related Reading

- [dotnet-core-WebUI.md](dotnet-core-WebUI.md) — Overview of ASP.NET Core web UI options
- [CodeBehind.md](CodeBehind.md) — How the code-behind pattern works in Razor Pages
- [ASP.NET-is-Razor-Pages.md](ASP.NET-is-Razor-Pages.md) — Why Razor Pages sits inside ASP.NET Core

---

*Last updated: March 2026*
