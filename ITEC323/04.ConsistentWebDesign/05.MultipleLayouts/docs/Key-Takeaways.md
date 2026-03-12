# Key Takeaways

- Layout selection starts at the page, then folder `_ViewStart.cshtml`, then parent `_ViewStart.cshtml`.
- Use a folder-level `_ViewStart.cshtml` when a whole section needs a different shell.
- Use a page-level layout override for one-off cases like print views.
- Keep shared structure in layouts and page-specific content in Razor pages.
- `@RenderSection()` and `IsSectionDefined()` help layouts stay flexible.
