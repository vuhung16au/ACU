# Key Takeaways: 03.NavigationMenus

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
