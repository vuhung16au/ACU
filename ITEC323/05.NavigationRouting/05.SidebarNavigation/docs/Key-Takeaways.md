# Key Takeaways: 05.SidebarNavigation

## The Big Idea

Sidebar navigation works best when the site has enough pages that a top navbar alone becomes crowded. In Razor Pages, Bootstrap gives the expand/collapse and offcanvas behavior, while Tag Helpers keep the links route-safe.

## Core Sidebar Patterns

### 1. Sticky desktop sidebar

Use a sidebar that remains visible while the content scrolls.

```css
.sidebar-panel {
  position: sticky;
  top: 1rem;
}
```

### 2. Collapsible sections with Bootstrap Collapse

Use buttons to open and close major sections without overwhelming the user.

```html
<button data-bs-toggle="collapse" data-bs-target="#desktop-foundation">
```

### 3. Nested links for deeper hierarchy

Use a third level only when the hierarchy is meaningful.

```text
Foundation
  Guides
    Sidebar Patterns
```

### 4. Mobile Offcanvas sidebar

Move the same navigation into an Offcanvas panel on small screens.

```html
<div class="offcanvas offcanvas-start" id="mobileSidebar">
```

### 5. Active-state highlighting

Check the current request path so the active page stands out and the current section stays expanded.

## Why This Matters

1. Helps learners understand how larger applications organize many pages.
2. Keeps related content grouped without a long flat menu.
3. Gives a clear mobile strategy for sidebar-heavy layouts.
4. Reinforces the connection between route structure and UI structure.

## Files to Remember

- `Pages/Shared/_Layout.cshtml`: app shell and mobile offcanvas container.
- `Pages/Shared/_SidebarNavigation.cshtml`: sidebar sections, nested links, active-state logic.
- `Pages/Index.cshtml`: FR5 overview and suggested sidebar paths.
- `Pages/Docs/Guides/SidebarPatterns.cshtml`: third-level navigation example.
- `wwwroot/css/site.css`: sticky sidebar and active-link styling.

## Common Mistakes

1. Repeating the sidebar markup in multiple places instead of centralizing it in a partial.
2. Forgetting unique collapse IDs when the sidebar appears in both desktop and mobile views.
3. Making every page a top-level item instead of grouping related pages.
4. Forgetting to keep the current section expanded when a child page is active.
5. Hiding the sidebar on mobile without replacing it with another pattern such as Offcanvas.

## One-Sentence Summary

Use a sticky sidebar, collapsible sections, and a mobile offcanvas panel to organize larger Razor Pages applications clearly and responsively.
