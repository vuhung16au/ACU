# Key Takeaways: 02.TagHelpers

## The Big Idea

Tag Helpers let Razor Pages generate links from route definitions instead of hardcoded strings.
This makes navigation more reliable, easier to maintain, and safer when route templates change.

## Core Patterns

### 1. Use `asp-page` for page links

```html
<a asp-page="/About">About</a>
```

### 2. Use `asp-route-*` for route values

```html
<a asp-page="/Products/Details" asp-route-id="5">View Product</a>
```

### 3. Use multiple `asp-route-*` values for friendly URLs

```html
<a asp-page="/Blog/Post"
   asp-route-year="2026"
   asp-route-month="3"
   asp-route-slug="my-post">
   Read Post
</a>
```

### 4. Use `asp-page-handler` for named handlers

```html
<a asp-page="/Products/Edit" asp-page-handler="Preview" asp-route-id="2">Preview</a>
```

This calls `OnGetPreview(int id)` automatically.

## Why This Matters

1. Links stay valid when route templates evolve.
2. Route data is explicit and readable.
3. Navigation code is consistent across the app.
4. You avoid manual query-string assembly mistakes.

## Files to Remember

- `Pages/Shared/_Layout.cshtml`: main navigation with `asp-page` and active-link highlighting.
- `Pages/Index.cshtml`: side-by-side hardcoded vs Tag Helper examples.
- `Pages/Products/Index.cshtml`: `asp-route-id` and handler link samples.
- `Pages/Products/Edit.cshtml.cs`: `OnGetPreview(int id)` named handler.
- `Pages/Blog/Index.cshtml`: multiple route parameter generation.

## Common Mistakes

1. Mixing hardcoded `href` values with Tag Helpers in main navigation.
2. Forgetting one of the required `asp-route-*` values.
3. Using `asp-page-handler` without a matching `OnGet...` or `OnPost...` method.
4. Hardcoding `/Page?handler=...` instead of using `asp-page-handler`.

## One-Sentence Summary

Use Tag Helpers to generate navigation links from your Razor Pages routes so URLs stay correct and maintainable.
