# Key Takeaways: 01.BasicRouting

## The Big Idea

Razor Pages routing is usually based on the file path inside the `Pages` folder. The file structure gives you a clean starting point, and the `@page` directive lets you customize the route when needed.

## Core Rules

### 1. The `Pages` Folder Matters

- `Pages/Index.cshtml` maps to `/`
- `Pages/About.cshtml` maps to `/About`
- `Pages/Products/Index.cshtml` maps to `/Products`

`Index.cshtml` is the default page for its folder.

### 2. Route Parameters Capture Values from the URL

```csharp
@page "{id:int}"
```

This means `/Products/Details/5` sends `5` into `OnGet(int id)`.

### 3. Optional Parameters Use `?`

```csharp
@page "{id:int?}"
```

This allows the same page to handle:

- `/Products/Edit`
- `/Products/Edit/5`

### 4. Constraints Validate Route Values Early

Constraints help reject bad URLs before your page logic runs.

Examples from this project:

- `int` for product IDs
- `regex(^ITEC[0-9][0-9][0-9]$)` for course codes
- `slug` for blog slugs such as `getting-started-with-routing`

### 5. Friendly URLs Improve Readability

This is easier to understand:

```text
/Blog/2026/3/getting-started-with-routing
```

Than this:

```text
/Blog/Post?id=14
```

## Request Flow

1. The browser requests a URL.
2. ASP.NET Core checks the mapped Razor Pages routes.
3. The route template and constraints are evaluated.
4. The matching page model `OnGet(...)` method receives the route values.
5. The page is rendered and sent back to the browser.

## Files to Remember

- `Program.cs`: Enables Razor Pages and registers the custom route constraint.
- `Pages/Products/Details.cshtml`: Demonstrates a required integer parameter.
- `Pages/Products/Edit.cshtml`: Demonstrates an optional parameter.
- `Pages/Blog/Post.cshtml`: Demonstrates a friendly URL with multiple parameters.
- `Pages/Courses/Lookup.cshtml`: Demonstrates a regex constraint.
- `Routing/SlugRouteConstraint.cs`: Demonstrates a custom route constraint.

## Common Mistakes

1. Forgetting to add `@page` at the top of a Razor Page.
2. Using the wrong route parameter name in `OnGet(...)`.
3. Expecting `/Products/Details/abc` to work when the route requires `int`.
4. Forgetting that `Index.cshtml` acts as the default page for a folder.

## One-Sentence Summary

Razor Pages routing starts with the file system, and the `@page` directive lets you add parameters and constraints to produce clean, safe, beginner-friendly URLs.
