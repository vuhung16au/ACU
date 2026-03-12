# Key Takeaways — Breadcrumb Navigation (FR4)

---

## 1. Bootstrap breadcrumb anatomy

Bootstrap's breadcrumb is an `<ol>` (ordered list), not a `<ul>`, inside a `<nav>` with `aria-label="breadcrumb"`:

```html
<nav aria-label="breadcrumb">
   <ol class="breadcrumb">
      <li class="breadcrumb-item"><a href="/">Home</a></li>
      <li class="breadcrumb-item"><a href="/Products">Products</a></li>
      <li class="breadcrumb-item active" aria-current="page">Student Laptop</li>
   </ol>
</nav>
```

Key points:
- `aria-label="breadcrumb"` tells screen readers the landmark purpose.
- `aria-current="page"` goes **only** on the last (active) item — not on all items.
- Bootstrap injects separators (›) automatically via `::before` CSS pseudo-elements; no separator markup needed.
- `.active` on the `<li>` + `aria-current="page"` on the `<li>` are both required.

---

## 2. BreadcrumbItem model — passing structured data via ViewData

Rather than passing a raw string or a dictionary, define a strongly-typed model:

```csharp
public class BreadcrumbItem
{
   public string  Text     { get; set; } = string.Empty;
   public string? Url      { get; set; }   // null = non-linked item
   public bool    IsActive { get; set; }
}
```

In the PageModel:
```csharp
ViewData["Breadcrumbs"] = new List<BreadcrumbItem> { ... };
```

In `_Layout.cshtml`, cast with a null-safe `as`:
```razor
var crumbs = ViewData["Breadcrumbs"] as IList<BreadcrumbItem>;
```

Why `IList<BreadcrumbItem>` instead of `List<BreadcrumbItem>`? The interface makes the layout resilient to other collection types.

---

## 3. Manual vs automatic — when to use each

| Criterion | Manual (ViewData) | Automatic (URL parse) |
|-----------|-------------------|-----------------------|
| Labels need runtime data | ✅ Product names, post titles, month names | ❌ Only capitalises URL segments |
| Route has int/slug params | ✅ `month=3` → "March" | ❌ Shows "3" |
| Simple static pages | Overkill | ✅ Zero boilerplate |
| Label differs from segment | ✅ Full control | ❌ Cannot diverge |

Use manual crumbs whenever a URL segment is an opaque id or when you need a display name that differs from the URL.

---

## 4. Intermediate crumbs without links

In the Blog Post trail (`Home > Blog > 2026 > March > {Title}`), "March" is a display label with no corresponding URL (there is no `/Blog/2026/March` route). Set `Url = null` and omit `IsActive`:

```csharp
new() { Text = monthName },   // Url is null, IsActive is false → rendered as plain text
```

The layout renders it as plain text since both conditions trigger the non-link branch:
```razor
if (crumb.IsActive || string.IsNullOrEmpty(crumb.Url))
{
   <li class="breadcrumb-item active" aria-current="page">@crumb.Text</li>
}
```

> Correction: for intermediate unlinked items you should NOT set `aria-current="page"`. Only the final active item gets that attribute. A cleaner model would have a separate `IsLinked` flag, but for a simple demo this is acceptable.

---

## 5. Automatic breadcrumb pitfalls

```csharp
// URL: /Products/Details/1
// segments: ["Products", "Details", "1"]
var label = int.TryParse(segments[i], out var num) ? $"#{num}" : Capitalise(segments[i]);
// → Home > Products > Details > #1
```

Problem: "Details" and "#1" are meaningless labels. The manual approach replaces this with the real product name.  
Rule: **always use the manual approach when the URL contains ids or slugs.**

---

## 6. Common mistakes

| Mistake | Consequence | Fix |
|---------|-------------|-----|
| `aria-current="page"` on every `<li>` | Screen reader announces every item as the current page | Only put it on the last / active item |
| Forgetting `aria-label="breadcrumb"` on `<nav>` | Landmark is unnamed; poor screen reader UX | Always include it |
| Showing breadcrumbs on the root `/` page | Single-item "Home" trail adds no value | Guard with `breadcrumbs.Count > 1` |
| Hardcoding absolute URLs in crumbs (`https://...`) | Breaks in non-root deployments | Use root-relative paths (`/Products`) |
| Setting `ViewData["Breadcrumbs"]` in `OnPost()` but not `OnGet()` | Crumbs missing on GET, present on POST | Set in every handler or a shared private helper |

---

## 7. Architecture summary

```
PageModel.OnGet()
   └─ sets ViewData["Breadcrumbs"] = List<BreadcrumbItem>

_Layout.cshtml @{ block }
   ├─ reads ViewData["Breadcrumbs"] (manual crumbs)
   ├─ if null AND path != "/" → BuildAutoCrumbs() from URL
   └─ showAuto flag for the learning hint

_Layout.cshtml <main>
   └─ @if (breadcrumbs?.Count > 1)
         renders <nav aria-label="breadcrumb"><ol class="breadcrumb">
         then @RenderBody()
```

Centralising the render in `_Layout.cshtml` means every page gets breadcrumbs automatically — no breadcrumb markup needed in individual page views.

## The Big Idea

Navigation menus are the backbone of site usability. In Razor Pages, Bootstrap gives responsive UI behavior, while Tag Helpers keep links route-safe and maintainable.

## Core Menu Patterns

### 1. Responsive Bootstrap Navbar

Use a navbar that expands on desktop and collapses on smaller screens.

```html
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
```

### 2. Mobile Hamburger Toggler

Use Bootstrap collapse to show/hide menu on mobile.

```html
<button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#mainNav">
```

### 3. Grouped Navigation with Dropdowns

Organize related links under parent items.

```html
<li class="nav-item dropdown">
   <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Products</a>
   <ul class="dropdown-menu">...</ul>
</li>
```

### 4. Active Link Highlighting

Use current route path checks in layout to apply `active` class on the current section.

## Why This Matters

1. Helps users understand where they are in the site.
2. Improves usability on both desktop and mobile.
3. Scales better as page count grows.
4. Keeps navigation structure clear and consistent.

## Files to Remember

- `Pages/Shared/_Layout.cshtml`: navbar, dropdowns, active-state logic.
- `Pages/Index.cshtml`: FR3 overview and test links.
- `Pages/Products/Category.cshtml`: child navigation destination.
- `Pages/Blog/Categories.cshtml`: child navigation destination.
- `Pages/Blog/Archive.cshtml`: grouped route destination.

## Common Mistakes

1. Forgetting to include Bootstrap bundle JS (toggler and dropdowns stop working).
2. Not using `navbar-expand-*`, causing poor desktop/mobile behavior.
3. Missing active class logic, so users lose context.
4. Too many flat links without grouping related sections.

## One-Sentence Summary

Use Bootstrap navbar + dropdown patterns with route-aware active states to create clear, responsive navigation in Razor Pages.
