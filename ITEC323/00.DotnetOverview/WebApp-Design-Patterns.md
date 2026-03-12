# .NET Web App Design Patterns

While MVC is a foundational concept, the modern .NET ecosystem has evolved significantly. Depending on whether you are building the user interface, the backend API, or the overall system architecture, different patterns dominate the landscape.

---

## Overview

Design patterns in .NET web development can be grouped into three layers:

| Layer | Patterns |
|---|---|
| **Foundation** | MVC, MVVM |
| **User Interface (UI)** | Razor Pages, Blazor, Backend-for-Frontend (BFF) |
| **API & Routing** | Controller-Based REST APIs, Minimal APIs |
| **Enterprise Backend** | Clean Architecture, CQRS, Microservices |

The patterns you need depend on the scale and purpose of your application. Most apps taught in this unit use Foundation and UI-layer patterns; the enterprise patterns appear in industry and advanced coursework.

---

## 0. Foundation Patterns

These two patterns underpin almost all .NET UI development. Understanding them first makes everything else easier.

### MVC (Model-View-Controller)

The original architectural pattern for web apps. The application is divided into three roles:

- **Model** — data and business rules (e.g., a `Product` class)
- **View** — the HTML template rendered to the browser
- **Controller** — receives HTTP requests, orchestrates the Model, and selects the View

```
Request → Controller → Model → View → Response (HTML)
```

MVC is still used widely, but Microsoft now recommends Razor Pages (see below) for most new server-rendered HTML apps.

### MVVM (Model-View-ViewModel)

MVVM is the dominant pattern for **data-binding** UIs — most commonly used in Blazor and .NET MAUI (desktop/mobile).

- **Model** — raw data
- **ViewModel** — a C# class that shapes the data for the view and holds UI state
- **View** — binds directly to ViewModel properties; updates automatically when data changes

```
View ←→ ViewModel ←→ Model
  (two-way data binding)
```

In Blazor, the `@code` block inside a `.razor` file acts as the ViewModel.

---

## 1. User Interface (UI) Patterns

### Razor Pages (Page-Based Pattern)

Microsoft's **recommended** approach for server-rendered HTML applications. Instead of splitting logic across separate `Controllers/`, `Models/`, and `Views/` folders, Razor Pages pairs the UI and logic together for each page.

```
/Pages
    /Products
        Index.cshtml        ← HTML template
        Index.cshtml.cs     ← PageModel (C# logic: OnGet, OnPost)
        Details.cshtml
        Details.cshtml.cs
```

**Key advantage**: The URL path mirrors the file path. `/Products/Details` maps directly to `Pages/Products/Details.cshtml` — no routing configuration needed.

**Best for**: Standard websites, CRUD applications, form-heavy sites. This is the primary pattern used in the ITEC323 labs (weeks 3–8).

---

### Blazor (Component-Based Pattern)

Blazor is the breakout star of modern .NET web development. It lets you build rich, interactive **Single Page Applications (SPAs) using C# instead of JavaScript**. UI is built from reusable, stateful `.razor` components.

```razor
@* ProductCard.razor *@
<div class="card">
    <h3>@Product.Name</h3>
    <p>@Product.Price</p>
    <button @onclick="AddToCart">Add to Cart</button>
</div>

@code {
    [Parameter] public Product Product { get; set; }

    private void AddToCart()
    {
        // C# runs here — no JavaScript needed
        CartService.Add(Product);
    }
}
```

Blazor has three hosting models:

| Model | Where C# Runs | Key Trait |
|---|---|---|
| **Blazor Server** | On the server | Fast startup; requires live connection |
| **Blazor WebAssembly (WASM)** | In the browser | Works offline; larger initial download |
| **Blazor Web App (.NET 8+)** | Both (per component) | Modern default; most flexible |

**Best for**: Dashboards, real-time apps, interactive forms, SPAs where you want to stay in C# end-to-end.

---

### Backend-for-Frontend (BFF)

When .NET developers use JavaScript frameworks (React, Angular, Vue) for the frontend, they often introduce a BFF — a dedicated .NET backend tailored specifically for that UI.

```
Browser (React/Vue)
        ↓
  .NET BFF API           ← handles auth, token management, aggregation
        ↓
 Downstream Services     ← microservices, databases, third-party APIs
```

The BFF keeps security concerns (OAuth tokens, cookies) out of the JavaScript frontend and gives each UI its own optimized API surface.

**Best for**: Any app that uses a JS frontend but needs a .NET backend for security and orchestration.

---

## 2. API & Routing Patterns

### Controller-Based APIs (REST)

The traditional approach to building HTTP APIs in .NET. Routes map to methods on C# Controller classes, and ASP.NET Core handles serialization, model binding, and HTTP semantics.

```csharp
[ApiController]
[Route("api/[controller]")]
public class ProductsController : ControllerBase
{
    [HttpGet("{id}")]
    public IActionResult GetById(int id) { ... }

    [HttpPost]
    public IActionResult Create(ProductDto dto) { ... }
}
```

**Best for**: Medium-to-large APIs, projects following REST conventions strictly, teams already familiar with MVC-style routing.

---

### Minimal APIs

A lightweight, modern alternative introduced in .NET 6. Endpoints are defined with minimal ceremony — no Controller class required. Ideal for microservices and small APIs.

```csharp
// Program.cs — the entire API in one place
var app = WebApplication.Create(args);

app.MapGet("/products/{id}", (int id, ProductService svc) =>
    svc.GetById(id) is Product p ? Results.Ok(p) : Results.NotFound());

app.MapPost("/products", (ProductDto dto, ProductService svc) =>
    Results.Created($"/products/{svc.Create(dto).Id}", dto));

app.Run();
```

**Best for**: Microservices, lightweight APIs, rapid prototyping. Minimal APIs have become the default in many new .NET projects.

---

## 3. Enterprise Backend Patterns

These patterns appear in production codebases at medium-to-large scale. They are not required for ITEC323 labs, but understanding them helps you read real-world .NET code.

### Clean Architecture (The Onion Architecture)

The gold standard for structuring a medium-to-large .NET solution. Business logic sits at the center, completely isolated from infrastructure concerns.

```
┌─────────────────────────────────────┐
│  Infrastructure (DB, Email, Files)  │  ← outer ring: swappable
│  ┌───────────────────────────────┐  │
│  │  Application (Use Cases)      │  │  ← orchestrates the domain
│  │  ┌─────────────────────────┐  │  │
│  │  │  Domain (Entities,      │  │  │  ← pure C#, no dependencies
│  │  │  Business Rules)        │  │  │
│  │  └─────────────────────────┘  │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
         ↑ Presentation (API / UI)
```

**Key benefit**: You can swap the database (e.g., SQL Server → PostgreSQL) without touching any business logic.

---

### CQRS (Command Query Responsibility Segregation)

In complex apps, reading data and writing data have very different requirements. CQRS splits them:

- **Command** — changes state (Create, Update, Delete). Returns a result code.
- **Query** — reads state (Get, List). Returns data. Never changes anything.

In .NET, CQRS is almost always implemented with the **MediatR** library:

```csharp
// Query
public record GetProductQuery(int Id) : IRequest<ProductDto>;

// Command
public record CreateProductCommand(string Name, decimal Price) : IRequest<int>;

// In a Controller or Minimal API:
var product = await mediator.Send(new GetProductQuery(id));
```

**Best for**: Complex domains with very different read/write workloads; systems that need an audit trail of all changes.

---

### Microservices (Cloud-Native)

For large-scale, highly available web applications, the monolith is broken into many small, independently deployable .NET services. Each service owns its own data and communicates via HTTP APIs or a message bus.

```
 Browser
    ↓
API Gateway
    ├── Product Service  (.NET)
    ├── Order Service    (.NET)
    ├── User Service     (.NET)
    └── Notification Service (.NET)
```

Microsoft released **.NET Aspire** (2024) specifically to make cloud-native microservice development easier — providing orchestration, service discovery, and observability tooling out of the box.

**Best for**: Systems that need to scale individual parts independently, or large teams working on separate bounded contexts.

---

## Choosing the Right Pattern

| If you are building... | Use this pattern |
|---|---|
| A student project or simple website | **Razor Pages** |
| An interactive, JavaScript-free SPA | **Blazor** |
| A REST API for a mobile or JS frontend | **Minimal API** or **Controller-Based API** |
| A complex app with clear business rules | **Clean Architecture** |
| A large app with heavy read/write separation | **CQRS with MediatR** |
| A cloud-scale distributed system | **Microservices with .NET Aspire** |

For this unit (ITEC323), focus on **Razor Pages** first. The later labs introduce **Blazor components** and **Minimal APIs** once those foundations are in place.

---

## Related Reading

- [WebFrameworks-Comparison.md](WebFrameworks-Comparison.md) — Side-by-side comparison of MVC, Razor Pages, and Blazor
- [dotnet-core-WebUI.md](dotnet-core-WebUI.md) — Overview of ASP.NET Core web UI options
- [CodeBehind.md](CodeBehind.md) — How the code-behind pattern works in Razor Pages
- [ASP.NET-is-Razor-Pages.md](ASP.NET-is-Razor-Pages.md) — Why Razor Pages is the recommended starting point

---

*Last updated: March 2026*
