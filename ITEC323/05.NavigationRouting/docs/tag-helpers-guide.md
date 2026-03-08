# Tag Helpers Guide

## What are Tag Helpers?

Tag Helpers are C# code that runs on the server to generate HTML. They make your markup clean and your URLs dynamic.

## Why Use Tag Helpers?

### ❌ Without Tag Helpers (Hardcoded)
```html
<a href="/Products/Details?id=5">View Product</a>
```

**Problems**:
- Breaks if route changes
- Manually manage query strings
- No compile-time checking
- Hard to maintain

### ✅ With Tag Helpers (Dynamic)
```html
<a asp-page="/Products/Details" asp-route-id="5">View Product</a>
```

**Benefits**:
- ✅ Generates correct URL automatically
- ✅ Survives route changes
- ✅ Compile-time checking
- ✅ Cleaner syntax

## Navigation Tag Helpers

### asp-page

Link to a Razor Page:

```html
<!-- Link to Index.cshtml -->
<a asp-page="/Index">Home</a>

<!-- Link to Products/Index.cshtml -->
<a asp-page="/Products/Index">Products</a>

<!-- Link to Products/Details.cshtml -->
<a asp-page="/Products/Details">Details</a>
```

**Generated**:
```html
<a href="/">Home</a>
<a href="/Products">Products</a>
<a href="/Products/Details">Details</a>
```

### asp-route-{parameter}

Pass route parameters:

```html
<!-- Single parameter -->
<a asp-page="/Products/Details" asp-route-id="5">Product #5</a>

<!-- Multiple parameters -->
<a asp-page="/Blog/Post" 
   asp-route-year="2026" 
   asp-route-month="3" 
   asp-route-slug="my-post">
    Read Post
</a>

<!-- Parameter from variable -->
<a asp-page="/Products/Edit" asp-route-id="@Model.ProductId">Edit</a>
```

**Generated**:
```html
<a href="/Products/Details/5">Product #5</a>
<a href="/Blog/2026/3/my-post">Read Post</a>
<a href="/Products/Edit/42">Edit</a>
```

### asp-page-handler

Target specific page handlers:

```csharp
// In PageModel
public IActionResult OnPostDelete(int id) { ... }
public IActionResult OnPostRestore(int id) { ... }
```

```html
<form asp-page-handler="Delete" asp-route-id="5" method="post">
    <button type="submit">Delete</button>
</form>

<form asp-page-handler="Restore" asp-route-id="5" method="post">
    <button type="submit">Restore</button>
</form>
```

### asp-fragment

Link to anchor on page:

```html
<a asp-page="/Docs/API" asp-fragment="authentication">Jump to Auth Section</a>
```

**Generated**:
```html
<a href="/Docs/API#authentication">Jump to Auth Section</a>
```

### asp-route

Use named routes:

```csharp
@page "/custom" @attribute [Route("/custom", Name = "CustomRoute")]
```

```html
<a asp-route="CustomRoute">Custom Page</a>
```

## Active Link Highlighting

### Manual Approach

```csharp
// In PageModel
public string ActivePage { get; set; }

public void OnGet()
{
    ActivePage = "Products";
}
```

```html
<ul class="navbar-nav">
    <li class="nav-item">
        <a class="nav-link @(Model.ActivePage == "Home" ? "active" : "")" 
           asp-page="/Index">Home</a>
    </li>
    <li class="nav-item">
        <a class="nav-link @(Model.ActivePage == "Products" ? "active" : "")" 
           asp-page="/Products/Index">Products</a>
    </li>
</ul>
```

### ViewContext Approach

```csharp
@{
    var currentPage = ViewContext.RouteData.Values["page"]?.ToString();
}

<ul class="navbar-nav">
    <li class="nav-item">
        <a class="nav-link @(currentPage == "/Index" ? "active" : "")" 
           asp-page="/Index">Home</a>
    </li>
</ul>
```

### Helper Method

```csharp
@functions {
    public string IsActive(string page)
    {
        var currentPage = ViewContext.RouteData.Values["page"]?.ToString();
        return currentPage == page ? "active" : "";
    }
}

<a class="nav-link @IsActive("/Products/Index")" asp-page="/Products/Index">Products</a>
```

## Form Tag Helpers

### Basic Form

```html
<form asp-page="/Products/Create" method="post">
    <input asp-for="Product.Name" class="form-control" />
    <button type="submit">Create</button>
</form>
```

### Form with Route Parameter

```html
<form asp-page="/Products/Edit" asp-route-id="@Model.ProductId" method="post">
    <input asp-for="Product.Name" class="form-control" />
    <button type="submit">Update</button>
</form>
```

### Form with Handler

```html
<form asp-page-handler="Delete" asp-route-id="@Model.ProductId" method="post">
    <button type="submit" class="btn btn-danger">Delete</button>
</form>
```

## Programmatic URL Generation

In C# code (not HTML):

```csharp
// In PageModel
public IActionResult OnPostDelete(int id)
{
    _db.Products.Remove(id);
    
    // Redirect to Index page
    return RedirectToPage("/Products/Index");
}

public IActionResult OnGetEdit(int id)
{
    // Redirect with route parameter
    return RedirectToPage("/Products/Edit", new { id = id });
}

public IActionResult OnPostSave()
{
    // Redirect with query string
    return RedirectToPage("/Products/Index", new { message = "Saved" });
}

// Get URL as string
public void OnGet()
{
    var url = Url.Page("/Products/Details", new { id = 5 });
    // url = "/Products/Details/5"
}
```

## Common Patterns

### Navigation List

```html
<ul class="nav">
    <li class="nav-item">
        <a class="nav-link" asp-page="/Index">Home</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" asp-page="/Products/Index">Products</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" asp-page="/About">About</a>
    </li>
</ul>
```

### Breadcrumbs

```html
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item">
            <a asp-page="/Index">Home</a>
        </li>
        <li class="breadcrumb-item">
            <a asp-page="/Products/Index">Products</a>
        </li>
        <li class="breadcrumb-item active" aria-current="page">
            Details
        </li>
    </ol>
</nav>
```

### Product List

```html
@foreach (var product in Model.Products)
{
    <div class="card">
        <h5>@product.Name</h5>
        <a asp-page="/Products/Details" asp-route-id="@product.Id" class="btn btn-primary">
            View Details
        </a>
        <a asp-page="/Products/Edit" asp-route-id="@product.Id" class="btn btn-secondary">
            Edit
        </a>
    </div>
}
```

### Action Buttons

```html
<a asp-page="/Products/Create" class="btn btn-success">New Product</a>
<a asp-page="/Products/Edit" asp-route-id="@Model.Id" class="btn btn-warning">Edit</a>

<form asp-page-handler="Delete" asp-route-id="@Model.Id" method="post" style="display:inline;">
    <button type="submit" class="btn btn-danger" 
            onclick="return confirm('Are you sure?')">Delete</button>
</form>
```

## Tag Helper Attributes Reference

| Attribute | Purpose | Example |
|-----------|---------|---------|
| `asp-page` | Target page | `asp-page="/About"` |
| `asp-route-{param}` | Route parameter | `asp-route-id="5"` |
| `asp-page-handler` | Handler method | `asp-page-handler="Delete"` |
| `asp-fragment` | Anchor/hash | `asp-fragment="section1"` |
| `asp-route` | Named route | `asp-route="ProductDetails"` |
| `asp-for` | Model binding | `asp-for="Product.Name"` |
| `asp-area` | Area routing | `asp-area="Admin"` |

## Debugging Tag Helpers

View generated HTML in browser:

1. Run app: `dotnet run`
2. Open page in browser
3. Right-click → Inspect Element
4. See actual `href` values generated

## Enable Tag Helpers

In `_ViewImports.cshtml`:

```csharp
@addTagHelper *, Microsoft.AspNetCore.Mvc.TagHelpers
```

**Already included in standard templates!**

## Best Practices

✅ **Do**:
- Use `asp-page` for all internal navigation
- Use `asp-route-*` for parameters
- Keep Tag Helper syntax clean and readable
- Use IntelliSense (quotes trigger suggestions)

❌ **Don't**:
- Mix Tag Helpers and hardcoded URLs
- Hardcode route parameters in `href`
- Forget to quote attribute values
- Use Tag Helpers for external links (use regular `href`)

## Quick Reference

```html
<!-- Basic link -->
<a asp-page="/About">About</a>

<!-- Link with parameter -->
<a asp-page="/Details" asp-route-id="5">View</a>

<!-- Link with multiple parameters -->
<a asp-page="/Post" asp-route-year="2026" asp-route-month="3">Post</a>

<!-- Form -->
<form asp-page="/Create" method="post">...</form>

<!-- Form with handler -->
<form asp-page-handler="Delete" method="post">...</form>

<!-- Link with fragment -->
<a asp-page="/Docs" asp-fragment="api">API Docs</a>
```

## Next Steps

- Practice in **02.TagHelpers** project
- Build navigation in **03.NavigationMenus**
- Read [Navigation Patterns](navigation-patterns.md)
