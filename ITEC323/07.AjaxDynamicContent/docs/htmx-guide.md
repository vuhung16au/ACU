# HTMX Guide

## What is HTMX?

Library for building dynamic web pages using **HTML attributes only** - no custom JavaScript required.

**Use for:** Partial page updates, form submissions, auto-refresh

**Website:** https://htmx.org/

## Installation

```html
<!-- Add to _Layout.cshtml or page -->
<script src="https://unpkg.com/htmx.org@1.9.10"></script>
```

## Core Attributes

### hx-get (Fetch Content)

```html
<button hx-get="/api/data" hx-target="#result">
    Load Data
</button>
<div id="result"></div>
```

Click button → GET request → Response goes into `#result`

### hx-post (Submit Data)

```html
<form hx-post="/api/save" hx-target="#message">
    <input name="name" />
    <button type="submit">Save</button>
</form>
<div id="message"></div>
```

### hx-target (Where to Put Response)

```html
<!-- CSS selector -->
<button hx-get="/data" hx-target="#result">Load</button>
<button hx-get="/data" hx-target=".container">Load</button>

<!-- Special values -->
<button hx-get="/data" hx-target="this">Replace button</button>
```

### hx-swap (How to Insert)

```html
<!-- Replace inner HTML (default) -->
<button hx-get="/data" hx-swap="innerHTML">Load</button>

<!-- Replace entire element -->
<button hx-get="/data" hx-swap="outerHTML">Load</button>

<!-- Append at end -->
<button hx-get="/data" hx-target="#list" hx-swap="beforeend">Load More</button>

<!-- Insert at start -->
<button hx-get="/data" hx-target="#list" hx-swap="afterbegin">Prepend</button>
```

## Triggers

### hx-trigger (When to Make Request)

```html
<!-- On click (default for buttons) -->
<button hx-get="/data" hx-trigger="click">Click Me</button>

<!-- On input change -->
<input hx-get="/search" hx-trigger="keyup" hx-target="#results" />

<!-- On input with delay (debounce) -->
<input hx-get="/search" hx-trigger="keyup delay:500ms" hx-target="#results" />

<!-- On page load -->
<div hx-get="/data" hx-trigger="load" hx-target="this"></div>

<!-- Every N seconds -->
<div hx-get="/updates" hx-trigger="every 5s" hx-target="this"></div>
```

## Loading Indicators

### Show Spinner During Request

```html
<style>
    .htmx-indicator {
        display: none;  /* Hidden by default */
    }
    .htmx-request .htmx-indicator {
        display: inline;  /* Show during request */
    }
</style>

<button hx-get="/data" hx-target="#result">
    Load Data
    <span class="htmx-indicator">⏳</span>
</button>
<div id="result"></div>
```

### Global Loading Indicator

```html
<div id="loading" class="htmx-indicator">
    <div class="spinner-border"></div>
</div>

<button hx-get="/data" hx-indicator="#loading">Load</button>
```

## Server-Side Endpoints

### Return HTML Fragment

```csharp
// Pages/api/SearchResults.cshtml.cs
public class SearchResultsModel : PageModel
{
    public string Query { get; set; }
    public List<Product> Results { get; set; }
    
    public void OnGet(string query)
    {
        Query = query;
        Results = _service.Search(query);
    }
}
```

```html
<!-- Pages/api/SearchResults.cshtml -->
@page
@model SearchResultsModel

@foreach (var product in Model.Results)
{
    <div class="product-card">
        <h5>@product.Name</h5>
        <p>$@product.Price</p>
    </div>
}
```

### Return JSON (Convert to HTML Client-Side)

HTMX prefers HTML responses, but can work with JSON using extensions.

## Complete Example: Live Search

```html
<!-- Search.cshtml -->
<h2>Product Search</h2>

<input 
    type="text" 
    name="query"
    placeholder="Search products..."
    hx-get="/api/SearchResults"
    hx-trigger="keyup changed delay:500ms"
    hx-target="#results"
    class="form-control"
/>

<div id="results" class="mt-3">
    <!-- Results appear here -->
</div>

<script src="https://unpkg.com/htmx.org@1.9.10"></script>
```

## Complete Example: Load More

```html
<div id="product-list">
    <!-- Initial products -->
    @foreach (var product in Model.Products)
    {
        <div class="product">@product.Name</div>
    }
</div>

<button 
    hx-get="/api/Products?page=2"
    hx-target="#product-list"
    hx-swap="beforeend"
    class="btn btn-primary">
    Load More
</button>
```

## HTMX vs JavaScript Fetch

| Feature | HTMX | JavaScript Fetch |
|---------|------|------------------|
| **JavaScript needed** | ❌ No | ✅ Yes |
| **Response format** | HTML fragments | JSON |
| **Learning curve** | Low | Medium |
| **Flexibility** | Limited | High |
| **Best for** | Simple updates | Complex logic |

**Rule:** HTMX for simple partial updates, Fetch for complex interactions.

## Common Patterns

### Form Validation

```html
<form hx-post="/api/validate" hx-target="#errors">
    <input name="email" required />
    <button type="submit">Submit</button>
</form>
<div id="errors" class="text-danger"></div>
```

### Delete with Confirmation

```html
<button 
    hx-delete="/api/products/5"
    hx-confirm="Are you sure?"
    hx-target="#product-5"
    hx-swap="outerHTML">
    Delete
</button>
```

### Auto-Refresh

```html
<div 
    hx-get="/api/stats"
    hx-trigger="every 10s"
    hx-target="this"
    hx-swap="innerHTML">
    <!-- Stats update every 10 seconds -->
</div>
```

## Best Practices

✅ **Do:**
- Return HTML fragments from server
- Use `hx-target` to specify destination
- Add loading indicators
- Use `delay` with keyup triggers
- Keep server responses simple

❌ **Don't:**
- Return complex JSON (use Fetch instead)
- Forget loading states
- Use for complex JavaScript logic
- Overuse auto-refresh (battery drain)

## Quick Reference

```html
<!-- Basic GET -->
<button hx-get="/api/data" hx-target="#result">Load</button>

<!-- POST form -->
<form hx-post="/api/save" hx-target="#msg">...</form>

<!-- Live search (debounced) -->
<input hx-get="/search" hx-trigger="keyup delay:500ms" hx-target="#results" />

<!-- Auto-refresh -->
<div hx-get="/data" hx-trigger="every 5s" hx-target="this"></div>

<!-- Load on page load -->
<div hx-get="/data" hx-trigger="load"></div>

<!-- Loading indicator -->
<button hx-get="/data">
    Load <span class="htmx-indicator">⏳</span>
</button>
```
