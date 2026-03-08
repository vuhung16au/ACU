# ASP.NET Core Web UI Frameworks

**Last Updated**: March 8, 2026  
**Target**: .NET 10.0 / C# 14 / ASP.NET Core 10.0

---

## Overview

ASP.NET Core offers multiple frameworks for building web user interfaces. Each framework is designed for specific scenarios, from fully interactive client-side apps to server-rendered pages. This guide helps you choose the right framework for your project.

> **For This Course**: We use **ASP.NET Core Razor Pages** in this course because of its simplicity and ease of learning for beginners. It provides an excellent foundation for understanding web development concepts.

> **Industry Recommendation**: For new projects in production, **ASP.NET Core Blazor** is generally the recommended choice for most web UI scenarios.

---

## Web UI Frameworks

### 1. ASP.NET Core Razor Pages ⭐

**Type**: Page-based server-rendered UI  
**Rendering**: Server-side only

> **🎓 Course Framework Choice**: Razor Pages is our framework of choice for this course due to its simplicity, gentle learning curve, and clear page-based structure. It's perfect for learning web development fundamentals.

Razor Pages is a simplified, page-focused model for building server-rendered web UI.

**Key Features**:
- Page-based routing (one page = one file)
- Server-side rendering (HTML generated on server)
- Simple and organized structure
- Built on MVC architecture
- Page-specific logic and view models
- Excellent for content-driven sites
- **Beginner-friendly**: Easy to understand and learn

**Why Perfect for Learning**:
- ✅ **Simple structure**: Each page is self-contained
- ✅ **Easy to understand**: Clear relationship between URL and file
- ✅ **Fast to build**: Minimal setup required
- ✅ **Great for beginners**: Less complexity than MVC or Blazor
- ✅ **Production-ready**: Used in real-world applications

**When to Use**:
- ✅ Content-focused websites
- ✅ Simple CRUD applications
- ✅ Server-rendered apps without complex client interaction
- ✅ Teams new to ASP.NET Core (easier than MVC)
- ✅ Apps requiring SEO optimization
- ✅ **Educational projects and learning**

**Example Use Cases**: Blogs, documentation sites, simple forms, marketing pages, course projects

---

### 2. ASP.NET Core Blazor 🔥

**Type**: Full-stack web UI framework  
**Rendering**: Server, Client (WebAssembly), or Hybrid

Blazor is the modern, component-based framework for building interactive web UIs using C#.

**Key Features**:
- Write UI logic in C# (not JavaScript)
- Reusable component model
- Efficient diff-based rendering
- Multiple rendering modes (Server, WebAssembly, Auto)
- Server-side rendering with progressive enhancement
- Share code between client and server
- JavaScript interop when needed

**When to Use**:
- ✅ New web applications
- ✅ Apps requiring rich interactivity
- ✅ Teams primarily using C#
- ✅ Apps needing code sharing between frontend and backend
- ✅ Modern SPAs without JavaScript frameworks

**Example Use Cases**: Admin dashboards, data-driven apps, internal tools, interactive forms

---

### 3. ASP.NET Core MVC

**Type**: Model-View-Controller pattern  
**Rendering**: Server-side only

ASP.NET Core MVC uses the proven MVC architectural pattern for building server-rendered web apps.

**Key Features**:
- Clear separation of concerns (Model, View, Controller)
- Mature and scalable architecture
- Flexible routing and middleware
- Maximum control over app structure
- Testable and maintainable
- Ideal for large, complex applications

**When to Use**:
- ✅ Large enterprise applications
- ✅ Apps requiring strict separation of concerns
- ✅ Complex business logic and workflows
- ✅ Teams experienced with MVC pattern
- ✅ Apps migrating from ASP.NET MVC 5

**Example Use Cases**: Enterprise web applications, complex business systems, multi-tenant platforms

---

### 4. ASP.NET Core SPA (Single Page Applications)

**Type**: JavaScript-based client UI  
**Rendering**: Client-side (JavaScript)

Build client-side UIs using JavaScript frameworks while leveraging ASP.NET Core for the backend.

**Supported Frameworks**:
- **Angular** - TypeScript-based framework by Google
- **React** - Component library by Meta
- **Vue** - Progressive JavaScript framework

**Key Features**:
- Client-side rendering
- ASP.NET Core backend API
- Project templates for Angular, React, Vue
- Large JavaScript ecosystem
- Rich client-side interactivity

**When to Use**:
- ✅ Teams with strong JavaScript/TypeScript skills
- ✅ Apps requiring advanced JavaScript libraries
- ✅ Existing JavaScript codebases
- ✅ Client-heavy interactive applications

**Downsides**:
- ❌ Multiple languages required (C# + JavaScript/TypeScript)
- ❌ More complex tooling
- ❌ Code sharing between client/server is difficult

**Example Use Cases**: Complex SPAs, apps with existing JavaScript investment, mobile-first web apps

---

### 5. Hybrid Approach: MVC/Razor Pages + Blazor

**Type**: Combination of server-rendered pages and interactive components  
**Rendering**: Server-side pages with interactive Blazor components

Integrate Blazor components into existing MVC or Razor Pages applications.

**Key Features**:
- Add interactivity to existing apps without full rewrite
- Prerender components on server
- Progressive enhancement
- Best of both worlds

**When to Use**:
- ✅ Modernizing existing MVC/Razor Pages apps
- ✅ Adding interactive features to server-rendered pages
- ✅ Gradual migration to Blazor
- ✅ Apps needing both static and interactive content

**Example Use Cases**: Legacy app modernization, adding interactive widgets to content sites

---

## Comparison Table

| Framework | Rendering | Language | Complexity | Best For | SEO | Learning Curve |
|-----------|-----------|----------|------------|----------|-----|----------------|
| **Razor Pages** ⭐ | Server | C# + Razor | Low | Content sites, simple apps, **learning** | ✅ Excellent | Easy |
| **Blazor** | Server/Client/Hybrid | C# | Medium | Modern interactive apps | ✅ Excellent (SSR) | Medium |
| **MVC** | Server | C# + Razor | Medium-High | Enterprise apps | ✅ Excellent | Medium-High |
| **SPA (Angular/React/Vue)** | Client | C# + JS/TS | High | Complex SPAs | ⚠️ Requires SSR | High |
| **MVC/Razor + Blazor** | Hybrid | C# | Medium | Modernizing existing apps | ✅ Excellent | Medium |

⭐ = Used in this course

---

## Quick Decision Guide

> **🎓 For This Course**: We use **Razor Pages** throughout this course. It provides the best learning experience for beginners while teaching you production-ready web development skills.

### Choose **Razor Pages** if you want:
- Simple, page-focused structure
- Server-rendered content sites
- Easy learning curve
- Fast development for small to medium apps
- **Perfect for educational projects**

### Choose **Blazor** if you want:
- Modern component-based architecture
- Full C# stack (frontend + backend)
- Interactive web apps without JavaScript
- Code sharing between client and server

### Choose **Razor Pages** if you want:
- Simple, page-focused structure
- Server-rendered content sites
- Easy learning curve
- Fast development for small to medium apps

### Choose **MVC** if you want:
- Strict separation of concerns
- Large, complex enterprise applications
- Maximum flexibility and control
- Familiarity with MVC pattern

### Choose **SPA (Angular/React/Vue)** if you:
- Have strong JavaScript/TypeScript expertise
- Need specific JavaScript libraries or ecosystem
- Are building from existing JavaScript codebase
- Want pure client-side rendering

### Choose **Hybrid (MVC/Razor + Blazor)** if you:
- Have existing MVC or Razor Pages apps
- Want to add interactivity gradually
- Need to modernize legacy applications

---

## Framework Feature Comparison

| Feature | Razor Pages ⭐ | Blazor | MVC | SPA (JS) |
|---------|----------------|--------|-----|----------|
| **Component Model** | ❌ No | ✅ Yes | ❌ No | ✅ Yes (JS) |
| **C# for UI Logic** | ✅ Yes | ✅ Yes | ✅ Yes | ❌ No (JS/TS) |
| **Client Interactivity** | ❌ Limited | ✅ High | ❌ Limited | ✅ High |
| **Server Rendering** | ✅ Yes | ✅ Yes | ✅ Yes | ⚠️ Optional |
| **SEO Friendly** | ✅ Yes | ✅ Yes (SSR) | ✅ Yes | ⚠️ Requires SSR |
| **Code Sharing** | ✅ Good | ✅ Excellent | ✅ Good | ❌ Limited |
| **Real-time Updates** | ❌ Manual | ✅ Easy | ❌ Manual | ✅ Easy |
| **Offline Support** | ❌ No | ✅ Yes (WASM) | ❌ No | ✅ Yes (PWA) |
| **Initial Load Speed** | ✅ Fast | ✅ Fast (SSR) | ✅ Fast | ⚠️ Slower |
| **Development Speed** | ✅ Very Fast | ✅ Fast | ⚠️ Medium | ⚠️ Medium |
| **Learning Curve** | ✅ Easy | ⚠️ Medium | ⚠️ Medium-High | ⚠️ High |
| **Beginner Friendly** | ✅ Excellent | ⚠️ Moderate | ⚠️ Moderate | ❌ Challenging |

⭐ = Used in this course

---

## Getting Started

### Razor Pages (🎓 Used in this course)
```bash
# Create a new Razor Pages app
dotnet new webapp -n MyRazorApp
cd MyRazorApp
dotnet run
```

### Blazor
```bash
# Create a new Blazor Web App
dotnet new blazor -n MyBlazorApp
cd MyBlazorApp
dotnet run
```

### MVC
```bash
# Create a new MVC app
dotnet new mvc -n MyMvcApp
cd MyMvcApp
dotnet run
```

### SPA (React)
```bash
# Create a new React app with ASP.NET Core
dotnet new react -n MyReactApp
cd MyReactApp
dotnet run
```

---

## Official Resources

### Documentation
- **Choose a Web UI**: https://learn.microsoft.com/en-us/aspnet/core/tutorials/choose-web-ui?view=aspnetcore-10.0
- **Blazor**: https://learn.microsoft.com/en-us/aspnet/core/blazor
- **Razor Pages**: https://learn.microsoft.com/en-us/aspnet/core/razor-pages
- **MVC**: https://learn.microsoft.com/en-us/aspnet/core/mvc/overview

### Tutorials
- **Build your first Blazor app**: https://dotnet.microsoft.com/learn/aspnet/blazor-tutorial/intro
- **Get started with Razor Pages**: https://learn.microsoft.com/en-us/aspnet/core/tutorials/razor-pages/razor-pages-start
- **Get started with MVC**: https://learn.microsoft.com/en-us/aspnet/core/tutorials/first-mvc-app/start-mvc
- **Create React app**: https://learn.microsoft.com/en-us/aspnet/core/client-side/spa/react
- **Create Angular app**: https://learn.microsoft.com/en-us/aspnet/core/client-side/spa/angular

### Architecture Guides
- **Blazor Hosting Models**: https://learn.microsoft.com/en-us/aspnet/core/blazor/hosting-models
- **MVC Pattern Overview**: https://learn.microsoft.com/en-us/aspnet/core/mvc/overview
- **Integrate Blazor with MVC/Razor Pages**: https://learn.microsoft.com/en-us/aspnet/core/blazor/components/integration

---

## Key Takeaways

1. **Razor Pages for this course** - We use Razor Pages because of its simplicity and gentle learning curve, perfect for beginners
2. **Blazor is the future** - Recommended for most new production web UI projects in the industry
3. **MVC for enterprise** - Ideal for large, complex applications requiring strict architecture
4. **SPA for JavaScript teams** - Use when JavaScript expertise or ecosystem is required
5. **Hybrid for migration** - Modernize existing apps gradually by adding Blazor components

---

## Related Documentation

- [ASP.NET Core Overview](README.md)
- [dotnet new Command](dotnet-new.md)
- [Razor Pages Concepts](ASP.NET-is-Razor-Pages.md)
- [Build Tools](BuildTools.md)

