# Week 4: Creating Consistent Looking Websites

## Overview

Learn how to build professional, maintainable websites by eliminating code duplication and implementing consistent layouts, styling, and UI components across your entire application.

## What You'll Learn

- **Layout System**: Master `_Layout.cshtml`, `_ViewStart.cshtml`, and `_ViewImports.cshtml`
- **DRY Principle**: Avoid repeating HTML, CSS, and navigation code
- **Partial Views**: Create reusable UI components (navigation, footers, cards)
- **CSS Frameworks**: Work with Bootstrap, Tailwind CSS, and Material Design
- **Sections**: Inject page-specific scripts and styles into shared layouts
- **Multiple Layouts**: Implement different layouts for different site sections (public, admin, print)

## Why This Matters

Without proper layout architecture, you'll:
- Copy/paste the same `<head>`, `<nav>`, and `<footer>` on every page
- Struggle to make site-wide changes (update navigation on 50+ pages?)
- Create inconsistent user experiences
- Write unmaintainable code

With layouts and partials:
- **Write once, use everywhere**: One navigation component, used site-wide
- **Easy updates**: Change your header once, all pages update automatically
- **Consistent UX**: Every page looks and behaves the same way
- **Professional structure**: Industry-standard architecture

## Essential Questions

- How do we use Partial Views for modularity?
- How do we implement shared layout logic?
- What is the role of `_ViewStart.cshtml` and `_ViewImports.cshtml`?
- When should we create multiple layouts?
- How do we choose between CSS frameworks?

## Projects in This Module

| Project | Focus | Complexity |
|---------|-------|------------|
| [01.BasicLayout](01.BasicLayout/) | Layout fundamentals without CSS distractions | ⭐ Beginner |
| [02.BootstrapTheme](02.BootstrapTheme/) | Industry-standard Bootstrap framework | ⭐⭐ Intermediate |
| [03.TailwindTheme](03.TailwindTheme/) | Modern utility-first CSS | ⭐⭐ Intermediate |
| [04.PartialViews](04.PartialViews/) | Reusable components and DRY principle | ⭐⭐⭐ Intermediate |
| [05.MultipleLayouts](05.MultipleLayouts/) | Different layouts for different purposes | ⭐⭐⭐ Advanced |
| [06.ComprehensiveExample](06.ComprehensiveExample/) | Real-world application combining all concepts | ⭐⭐⭐⭐ Advanced |

## Prerequisites

- Basic understanding of ASP.NET Core Razor Pages
- HTML and CSS fundamentals
- .NET 10.0 SDK installed

## Key Concepts

### Layout Files

- **_Layout.cshtml**: The master template that wraps your page content
- **_ViewStart.cshtml**: Specifies which layout to use (can be at root or folder level)
- **_ViewImports.cshtml**: Imports namespaces and tag helpers for all pages

### DRY Principle

**Don't Repeat Yourself**: Set up your HTML `<head>`, navigation `<nav>`, and footer once, and every page inherits them automatically.

### Partial Views

Small, reusable components that can be included in multiple pages or layouts:
```html
<partial name="_Navigation" />
```

### Sections

Allow individual pages to inject specific content into the layout:
```csharp
@section Scripts {
    <script src="/js/page-specific.js"></script>
}
```

## Additional Resources

- [Layouts Explained](docs/layouts-explained.md) - Deep dive into the layout system
- [Partial Views Guide](docs/partial-views-guide.md) - When and how to use partials
- [CSS Frameworks Comparison](docs/css-frameworks-comparison.md) - Bootstrap vs Tailwind vs Material
- [DRY Principles](docs/dry-principles.md) - Avoiding code duplication

## Quick Start

Each project folder contains its own README and QUICKSTART guide. Start with `01.BasicLayout` and progress sequentially.

```bash
# Navigate to any project
cd 04.ConsistentWebDesign/01.BasicLayout

# Follow its QUICKSTART.md
```

## Learning Path

1. **Start Simple**: Begin with `01.BasicLayout` to understand the mechanics
2. **Add Styling**: Explore `02.BootstrapTheme` and `03.TailwindTheme`
3. **Go Modular**: Learn reusable components in `04.PartialViews`
4. **Add Complexity**: Multiple layouts in `05.MultipleLayouts`
5. **Build Real**: Apply everything in `06.ComprehensiveExample`

## Tips for Success

- **Layout-first**: Always start new websites by creating the layout structure
- **Favor CSS Classes**: Avoid inline styles
- **Re-use Existing Styles**: Don't reinvent the wheel
- **Use Partial Views**: If you're copying/pasting HTML, create a partial instead
- **Organize Assets**: Keep CSS, JavaScript, and images well-organized
- **Test Across Pages**: Ensure layout changes work on all pages

