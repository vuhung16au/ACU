# Week 5: Navigation & Routing

## Overview

Learn how ASP.NET Core maps URLs to Razor Pages and build dynamic navigation structures using Tag Helpers, Bootstrap components, and routing patterns.

## Module Goal

Master navigation and routing to create user-friendly, SEO-optimized websites with intuitive site structure.

## Learning Objectives

By completing this module, you will:

1. Understand endpoint routing and URL-to-page mapping
2. Use Tag Helpers for dynamic URL generation
3. Build responsive navigation menus with Bootstrap
4. Implement breadcrumbs for site hierarchy
5. Create sidebar navigation for complex sites
6. Generate sitemaps for SEO
7. Use programmatic redirects (301/302)

## Why Navigation & Routing Matters

- **User Experience**: Good navigation transforms disconnected pages into a cohesive application
- **SEO**: Friendly URLs improve search engine rankings
- **Maintainability**: Tag Helpers generate URLs that survive route changes
- **Accessibility**: Proper navigation structure helps all users navigate your site

## Projects

| Project | Focus | Key Concepts |
|---------|-------|--------------|
| **01.BasicRouting** | URL mapping | Route parameters, constraints, conventions |
| **02.TagHelpers** | Dynamic URLs | asp-page, asp-route-*, active links |
| **03.NavigationMenus** | Top navigation | Bootstrap navbar, dropdowns, responsive |
| **04.Breadcrumbs** | Site hierarchy | Breadcrumb trails, dynamic generation |
| **05.SidebarNavigation** | Multi-level menus | Sidebars, collapsible sections, offcanvas |
| **06.ComprehensiveNavigation** | Complete system | All navigation types combined |

## Prerequisites

- Completed Week 4 (Consistent Web Design)
- Understanding of Razor Pages and layouts
- Basic Bootstrap knowledge
- Familiarity with HTML/CSS

## Essential Questions

- How does .NET 10 map `/Products/Edit/5` to `/Pages/Products/Edit.cshtml`?
- How do we pass data through URLs using `@page "{id}"`?
- Why use Tag Helpers instead of hardcoded links?
- How do we implement responsive navigation menus?
- What makes a URL "friendly" for users and search engines?

## Project Progression

### Foundation (Projects 01-02)
Start with routing fundamentals and Tag Helpers to understand how URLs work in ASP.NET Core.

### Navigation UI (Projects 03-05)
Build different navigation patterns: horizontal menus, breadcrumbs, and sidebars.

### Integration (Project 06)
Combine all concepts into a production-ready navigation system.

## Key Technologies

- **ASP.NET Core Razor Pages** - Server-side page framework
- **Endpoint Routing** - URL pattern matching engine
- **Tag Helpers** - Dynamic URL generation
- **Bootstrap 5** - Navigation components (navbar, breadcrumbs, offcanvas)
- **Bootstrap Icons** - Visual navigation elements

## Documentation

- **[Routing Explained](docs/routing-explained.md)** - How endpoint routing works
- **[Tag Helpers Guide](docs/tag-helpers-guide.md)** - Navigation Tag Helpers reference
- **[Navigation Patterns](docs/navigation-patterns.md)** - Menu, breadcrumb, sidebar patterns
- **[SEO & Sitemap](docs/seo-sitemap.md)** - Search engine optimization

## Quick Start

1. Start with **01.BasicRouting** to understand URL mapping
2. Progress to **02.TagHelpers** for dynamic link generation
3. Build navigation UI with **03.NavigationMenus** and **04.Breadcrumbs**
4. Add complexity with **05.SidebarNavigation**
5. Complete with **06.ComprehensiveNavigation**

## What You'll Build

By the end of this module, you'll have built:
- ✅ Pages with clean, SEO-friendly URLs
- ✅ Responsive Bootstrap navigation bars
- ✅ Dynamic breadcrumb trails
- ✅ Multi-level sidebar navigation
- ✅ Programmatic redirects
- ✅ XML sitemap for search engines

## Common Routing Patterns

```
/                           → Pages/Index.cshtml
/Products                   → Pages/Products/Index.cshtml
/Products/Details/5         → Pages/Products/Details.cshtml with id=5
/Blog/2026/03/my-post       → Pages/Blog/Post.cshtml with year, month, slug
```

## Navigation Best Practices

1. **Use Tag Helpers**: `asp-page` instead of hardcoded URLs
2. **Mobile First**: Ensure navigation works on all devices
3. **Semantic HTML**: Use `<nav>`, `<ul>`, `<ol>` appropriately
4. **Accessibility**: Add ARIA labels and keyboard navigation
5. **Consistency**: Keep navigation structure predictable
6. **Feedback**: Highlight the current page in navigation

## Resources

- [ASP.NET Core Routing Documentation](https://learn.microsoft.com/en-us/aspnet/core/fundamentals/routing)
- [Tag Helpers Documentation](https://learn.microsoft.com/en-us/aspnet/core/mvc/views/tag-helpers/intro)
- [Bootstrap Navigation Components](https://getbootstrap.com/docs/5.3/components/navbar/)
- [SEO Best Practices](https://developers.google.com/search/docs/crawling-indexing/sitemaps/overview)

---

**Module**: Week 5 | **Topic**: Navigation & Routing | **Framework**: ASP.NET Core Razor Pages (.NET 10)
