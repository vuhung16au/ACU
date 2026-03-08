# Week 7: AJAX and Dynamic Content

## Overview

Learn to make web pages "alive" by fetching and sending data in the background without full-page reloads.

**Key Goal:** Move away from heavy full-page reloads, reduce server bandwidth, and create seamless user experiences.

## Learning Objectives

By the end of this module, you will:

✅ Use JavaScript Fetch API for asynchronous requests  
✅ Build RESTful Web APIs with ASP.NET Core  
✅ Update specific page sections without reloading  
✅ Implement HTMX for declarative partial updates  
✅ Create simple Blazor components with real-time updates  
✅ Handle loading states and errors gracefully  
✅ Configure CORS for cross-origin requests  
✅ Document APIs with Swagger  
✅ Implement timed polling for auto-refresh  

## Essential Questions

1. **How do we use the JavaScript Fetch API to target a specific element on the page?**
2. **How do we build server-side endpoints that return structured JSON data?**
3. **How do we handle the "Loading" state during asynchronous requests?**

## Technology Stack

- **Frontend:** JavaScript Fetch API, Axios, HTMX
- **Backend:** ASP.NET Core Razor Pages, Web API
- **Real-Time:** Blazor Server with SignalR
- **Documentation:** Swagger/OpenAPI
- **Styling:** Bootstrap 5
- **Data:** Mock data (no external APIs required)

## Projects

| Project | Focus | Difficulty |
|---------|-------|-----------|
| [01.BasicFetchAPI](01.BasicFetchAPI/) | Fetch API basics, loading states | ⭐ Beginner |
| [02.PartialPageUpdates](02.PartialPageUpdates/) | DOM updates, View Components | ⭐⭐ Easy |
| [03.HtmxPartialRendering](03.HtmxPartialRendering/) | HTMX declarative updates | ⭐⭐ Easy |
| [04.LocalTodoApi](04.LocalTodoApi/) | RESTful API, CRUD operations | ⭐⭐⭐ Medium |
| [05.WeatherDashboard](05.WeatherDashboard/) | Mock data, auto-refresh | ⭐⭐⭐ Medium |
| [06.BlazorCounter](06.BlazorCounter/) | Blazor intro, SignalR | ⭐⭐ Easy |
| [07.ComprehensiveApp](07.ComprehensiveApp/) | Integration of all techniques | ⭐⭐⭐⭐ Advanced |

## Documentation

### Core Concepts
- [Fetch API Guide](docs/fetch-api-guide.md) - JavaScript Fetch for async requests (includes Axios comparison)
- [HTMX Guide](docs/htmx-guide.md) - Partial updates without custom JavaScript
- [Blazor Basics Guide](docs/blazor-basics-guide.md) - C# SPA framework
- [Web API Guide](docs/web-api-guide.md) - Building RESTful APIs

### Advanced Topics
- [Loading States Guide](docs/loading-states-guide.md) - UI feedback patterns
- [CORS Guide](docs/cors-guide.md) - Cross-Origin Resource Sharing
- [Swagger Guide](docs/swagger-guide.md) - API documentation
- [WCF Introduction](docs/wcf-intro.md) - Historical context (legacy)

## Prerequisites

- Completed Week 6: Reusable Components and Validation
- Understanding of Razor Pages
- Basic JavaScript knowledge
- Familiarity with async/await

## Quick Start

1. **Start with basics:**
   ```bash
   cd 01.BasicFetchAPI
   dotnet run
   ```

2. **Explore HTMX:**
   ```bash
   cd 03.HtmxPartialRendering
   dotnet run
   ```

3. **Build a complete API:**
   ```bash
   cd 04.LocalTodoApi
   dotnet run
   ```

## Key Takeaways

### Asynchronous Requests
- Use Fetch API or libraries like Axios
- Update specific UI parts without page refresh
- Handle loading states and errors

### Partial Page Updates
- Use JavaScript Fetch for dynamic content
- Use HTMX for simple HTML attribute-based updates
- Use Blazor for C#-based real-time updates

### Web APIs
- Build RESTful services with ASP.NET Core
- Use HTTP verbs ([HttpGet], [HttpPost], etc.)
- Return lightweight JSON data
- Document with Swagger

### Real-Time Updates
- Client-side polling with `setInterval()`
- Blazor with SignalR for true real-time push

## Resources

- [MDN Fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API)
- [HTMX Documentation](https://htmx.org/)
- [Blazor Documentation](https://learn.microsoft.com/aspnet/core/blazor/)
- [ASP.NET Core Web API](https://learn.microsoft.com/aspnet/core/web-api/)

