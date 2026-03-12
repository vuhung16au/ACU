# Key Takeaways: Navigation & Routing

This module brings everything together. Here are the most critical patterns to absorb:

## 1. ViewComponents are Powerful
Instead of bloating `_Layout.cshtml` with logic to fetch database menus or construct breadcrumbs, we extracted that logic into **ViewComponents** (`NavigationMenuViewComponent`, `BreadcrumbsViewComponent`, etc.).
- Keeps Razor views clean.
- Encapsulates complex UI-generation logic.
- Easily unit testable.

## 2. Dynamic Breadcrumbs using Route Segments
Breadcrumbs can be tough to calculate. We demonstrated a trick in `BreadcrumbsViewComponent`:
```csharp
var path = HttpContext.Request.Path.Value;
var segments = path.Split('/', StringSplitOptions.RemoveEmptyEntries);
```
By simply interpreting the URL path organically, we can auto-generate breadcrumbs without needing manual `ViewData["Breadcrumb"]` declarations on every single page!

## 3. Nested Layouts for Sub-sections
We created `_DocsLayout.cshtml` which itself declares `Layout = "_Layout"`. This allowed us to inject a left-side `SidebarViewComponent` exclusively for the Documentation area of the website without polluting the global `_Layout.cshtml`.

## 4. Native Sitemap Generation
Routing isn't just about rendering HTML pages. With `@page "/sitemap.xml"` and a `ContentResult`, we hijacked Razor Pages into acting like a seamless API endpoint, generating raw XML optimized for search engines in real-time.

## 5. Post-Redirect-Get (PRG) Pattern
On the search bar, when a user posts their query, we caught it in `OnPost()`, formatted it cleanly as a path/query string parameter, and returned a `RedirectToPage("/Products/Search", new { q = searchTerm })`. This prevents the "Confirm Form Resubmission" warnings if a user refreshes the page and makes URLs highly sharable.
