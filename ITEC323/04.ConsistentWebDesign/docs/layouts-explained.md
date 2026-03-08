# Layouts Explained: _ViewStart, _ViewImports, and Layout Files

## What is a Layout?

A **layout** is a master template that wraps your page content. Think of it as a picture frame—the frame stays the same, but you swap out different pictures (pages) inside it.

## The Three Key Files

### 1. _Layout.cshtml (The Master Template)

**Location**: `Pages/Shared/_Layout.cshtml` or `Views/Shared/_Layout.cshtml`

**Purpose**: Contains the HTML structure shared by all pages (header, navigation, footer).

**Example**:
```html
<!DOCTYPE html>
<html>
<head>
    <title>@ViewBag.Title</title>
    <link rel="stylesheet" href="/css/site.css" />
</head>
<body>
    <header>
        <nav><!-- Navigation here --></nav>
    </header>
    
    <main>
        @RenderBody() <!-- Your page content appears here -->
    </main>
    
    <footer>
        <p>&copy; 2026 My Site</p>
    </footer>
    
    @RenderSection("Scripts", required: false)
</body>
</html>
```

**Key Methods**:
- `@RenderBody()`: Injects the page content here
- `@RenderSection("Name", required: true/false)`: Placeholder for optional page-specific content

### 2. _ViewStart.cshtml (Layout Selector)

**Location**: Root of `Pages/` folder or any subfolder

**Purpose**: Specifies which layout file to use for all pages in that folder.

**Example**:
```csharp
@{
    Layout = "_Layout";
}
```

**Hierarchy**:
- Root `_ViewStart.cshtml` applies to all pages
- Folder-level `_ViewStart.cshtml` overrides root for that folder
- Individual page can override with `@{ Layout = "_OtherLayout"; }`

### 3. _ViewImports.cshtml (Shared Imports)

**Location**: Root of `Pages/` or any subfolder

**Purpose**: Imports namespaces, tag helpers, and directives for all pages.

**Example**:
```csharp
@using MyApp
@using MyApp.Models
@namespace MyApp.Pages
@addTagHelper *, Microsoft.AspNetCore.Mvc.TagHelpers
```

**What it does**:
- `@using`: Import namespaces (like C# using statements)
- `@namespace`: Set the default namespace for pages
- `@addTagHelper`: Enable tag helpers like `<partial>`, `<environment>`

## How They Work Together

```
1. User requests /About page
2. ASP.NET looks for About.cshtml
3. _ViewStart.cshtml says "use _Layout.cshtml"
4. _ViewImports.cshtml makes tag helpers available
5. _Layout.cshtml renders with About.cshtml content in @RenderBody()
6. Final HTML sent to browser
```

## Layout Hierarchy Example

```
Pages/
├── _ViewStart.cshtml          → Layout = "_Layout"
├── _ViewImports.cshtml        → Tag helpers for all
├── Index.cshtml               → Uses _Layout
├── About.cshtml               → Uses _Layout
├── Admin/
│   ├── _ViewStart.cshtml      → Layout = "_AdminLayout"
│   ├── Dashboard.cshtml       → Uses _AdminLayout
│   └── Users.cshtml           → Uses _AdminLayout
└── Shared/
    ├── _Layout.cshtml         → Public layout
    └── _AdminLayout.cshtml    → Admin layout
```

## Sections Deep Dive

### Defining Sections in Layout

```csharp
@RenderSection("Scripts", required: false)
@RenderSection("Styles", required: true)  // Page MUST define this
```

### Using Sections in Pages

```csharp
@section Scripts {
    <script src="/js/chart.js"></script>
    <script>
        // Page-specific JavaScript
        initializeChart();
    </script>
}

@section Styles {
    <link rel="stylesheet" href="/css/chart.css" />
}
```

### Checking if Section Exists

```csharp
@if (IsSectionDefined("Scripts"))
{
    @RenderSection("Scripts", required: false)
}
else
{
    <script src="/js/default.js"></script>
}
```

## Common Patterns

### Pattern 1: Global Layout for All Pages
```
Pages/_ViewStart.cshtml → Layout = "_Layout"
```

### Pattern 2: Different Layout per Section
```
Pages/_ViewStart.cshtml        → Layout = "_Layout"
Pages/Admin/_ViewStart.cshtml  → Layout = "_AdminLayout"
```

### Pattern 3: Per-Page Layout Override
```csharp
@page
@model IndexModel
@{
    Layout = "_LandingLayout";  // This page only
}
```

## ViewBag vs ViewData

Both pass data from page to layout:

```csharp
// In Page
ViewBag.Title = "About Us";
ViewData["ActiveMenu"] = "about";

// In Layout
<title>@ViewBag.Title</title>
<li class="@(ViewData["ActiveMenu"] == "about" ? "active" : "")">About</li>
```

## Best Practices

1. **Keep layouts simple**: Only shared elements belong here
2. **One layout per site section**: Don't create too many layouts
3. **Root _ViewStart**: Always have one at the root
4. **Consistent sections**: Use same section names across layouts
5. **Optional sections**: Make most sections optional with `required: false`
6. **Semantic HTML**: Use `<header>`, `<nav>`, `<main>`, `<footer>`

## Troubleshooting

**Problem**: Page not using layout  
**Solution**: Check `_ViewStart.cshtml` exists and has correct layout name

**Problem**: Tag helpers not working  
**Solution**: Check `_ViewImports.cshtml` has `@addTagHelper *, Microsoft.AspNetCore.Mvc.TagHelpers`

**Problem**: Namespace errors  
**Solution**: Add required `@using` statements to `_ViewImports.cshtml`

**Problem**: Section not rendering  
**Solution**: Ensure section name matches exactly (case-sensitive)

## Quick Reference

| File | Purpose | Runs When |
|------|---------|-----------|
| `_Layout.cshtml` | Master template | Every page load |
| `_ViewStart.cshtml` | Choose layout | Before page render |
| `_ViewImports.cshtml` | Import namespaces/tag helpers | Before page compile |

---

**Next**: Learn about [Partial Views](partial-views-guide.md) for even more reusability.
