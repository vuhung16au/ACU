# Understanding ASP.NET Core and Razor Pages

## What is .NET 10?

**.NET 10** is Microsoft's latest platform for building modern applications (as of Mar 2026). It's a unified framework that lets you build web apps, desktop apps, mobile apps, and more using C#.

## What is ASP.NET Core?

**ASP.NET Core** is the web development framework within .NET 10. It's what you use to build websites and web applications.

Think of it this way:
- **.NET 10** = The entire platform
- **ASP.NET Core** = The part specifically for web development

## What is Razor Pages?

**Razor Pages** is one way to build web applications in ASP.NET Core. It uses a page-based approach where:

- Each web page has a `.cshtml` file (contains HTML and Razor syntax)
- Each page has a `.cshtml.cs` file (contains C# code-behind)

**Example structure:**
```
Index.cshtml          ← Your HTML and Razor markup
Index.cshtml.cs       ← Your C# page logic (PageModel)
```

## Why Razor Pages for ITEC323?

Razor Pages is ideal for learning web development because:

1. **Simple structure**: One page = one set of files
2. **Easy to understand**: HTML stays in `.cshtml`, C# logic stays in `.cshtml.cs`
3. **Industry standard**: Used in modern .NET web applications
4. **Clean separation**: Keep your display code separate from your logic

## Other Options in ASP.NET Core

While we focus on Razor Pages, ASP.NET Core offers other approaches:

- **MVC**: Separates apps into Models, Views, and Controllers
- **Blazor**: Build interactive UIs with C# instead of JavaScript
- **Web API**: Create REST APIs that return JSON data

For this unit, **Razor Pages** gives you the best balance of simplicity and modern practices.

## Key Takeaway

**ASP.NET Core with Razor Pages** is Microsoft's modern, industry-standard way to build page-based web applications in 2026. It's what you'll use throughout ITEC323 to learn web development with C#.

