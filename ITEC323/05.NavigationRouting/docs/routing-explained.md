# Routing Explained

## What is Endpoint Routing?

**Endpoint routing** is the system that matches incoming URLs to specific Razor Pages in your application. It's the traffic director that says "This URL → That Page".

## Convention-Based Routing

ASP.NET Core uses **folder structure** to determine routes automatically:

| URL | Maps To | Rule |
|-----|---------|------|
| `/` | `Pages/Index.cshtml` | Root default |
| `/About` | `Pages/About.cshtml` | Page name |
| `/Products` | `Pages/Products/Index.cshtml` | Folder default |
| `/Products/Details` | `Pages/Products/Details.cshtml` | Folder + page |

**Key Rule**: File path becomes the URL path (minus `Pages/` and `.cshtml`)

## Route Parameters

### Basic Parameter

Capture values from the URL:

```csharp
@page "{id}"
```

URL: `/Products/Details/5` → `id = "5"`

### Optional Parameter

Parameter is not required:

```csharp
@page "{id?}"
```

URLs:
- `/Products/Edit` → `id = null`
- `/Products/Edit/5` → `id = "5"`

### Multiple Parameters

```csharp
@page "{category}/{id}"
```

URL: `/Products/Electronics/42` → `category = "Electronics"`, `id = "42"`

### Real-World Example (Blog)

```csharp
@page "{year:int}/{month:int}/{slug}"
```

URL: `/Blog/2026/03/my-first-post`
- `year = 2026`
- `month = 3`
- `slug = "my-first-post"`

## Route Constraints

Validate parameters automatically:

| Constraint | Syntax | Example | Matches |
|------------|--------|---------|---------|
| **Integer** | `{id:int}` | `/Details/5` | ✅ 5, ❌ "abc" |
| **String** | `{name:alpha}` | `/User/John` | ✅ "John", ❌ "John123" |
| **Min/Max** | `{id:int:min(1)}` | `/Item/10` | ✅ 10, ❌ 0 |
| **Length** | `{code:length(5)}` | `/Code/AB123` | ✅ "AB123", ❌ "AB" |
| **Regex** | `{slug:regex(^[a-z]+$)}` | `/post/hello` | ✅ "hello", ❌ "Hello" |

**Common Constraints**:

```csharp
// Integer ID
@page "{id:int}"

// ID must be positive
@page "{id:int:min(1)}"

// Optional integer
@page "{id:int?}"

// Year between 2000-2099
@page "{year:int:range(2000,2099)}"
```

## Accessing Route Parameters

In your PageModel:

```csharp
public class DetailsModel : PageModel
{
    // Option 1: Property binding
    [BindProperty(SupportsGet = true)]
    public int Id { get; set; }
    
    public void OnGet()
    {
        // Id is automatically populated from route
        var product = _db.Products.Find(Id);
    }
    
    // Option 2: Method parameter
    public void OnGet(int id)
    {
        var product = _db.Products.Find(id);
    }
}
```

## Friendly URLs

### ❌ Bad (Query Strings)
```
/Products/Details?id=5
/Blog/Post?year=2026&month=3&title=my-post
```

### ✅ Good (Route Parameters)
```
/Products/Details/5
/Blog/2026/03/my-post
```

**Why?**
- More readable for humans
- Better for SEO (search engines prefer clean URLs)
- Easier to remember and share
- Professional appearance

## Custom Route Override

Override the default routing:

```csharp
// Make About.cshtml the homepage
@page "/"

// Custom route for Products/Index.cshtml
@page "/shop"

// Multiple routes for same page
@page "/products"
@page "/shop"
```

## Route Priority

1. **Exact match**: `/About` → `About.cshtml`
2. **With parameter**: `/{id}` → matches any single segment
3. **Catch-all**: `/{*path}` → matches everything

## How Routing Works (Behind the Scenes)

1. **Request arrives**: Browser sends `GET /Products/Details/5`
2. **Routing engine activates**: Scans all `@page` directives
3. **Pattern match**: Finds `@page "{id:int}"` in `Products/Details.cshtml`
4. **Parameter extraction**: Captures `id = 5`
5. **Page execution**: Calls `OnGet(5)` on the PageModel
6. **Response**: Returns rendered HTML

## Common Routing Patterns

### E-commerce
```
/Products              → Product listing
/Products/Details/5    → Product details
/Products/Category/Electronics → Category page
/Cart                  → Shopping cart
/Checkout/Step/1       → Multi-step checkout
```

### Blog
```
/Blog                  → Recent posts
/Blog/2026/03/my-post  → Single post
/Blog/Category/Tech    → Category archive
/Blog/Author/John      → Author archive
/Blog/Search?q=dotnet  → Search results
```

### Documentation
```
/Docs                  → Docs home
/Docs/GettingStarted   → Getting started guide
/Docs/API/Reference    → API reference
/Docs/Tutorials/First  → Tutorial page
```

## Default Pages

- **Index.cshtml** is the default page for any folder
- `/Products` routes to `/Products/Index.cshtml`
- Root `/` routes to `/Pages/Index.cshtml`

## Route Debugging

View all registered routes:

```bash
dotnet run --urls="https://localhost:5001"
# Check terminal output for registered endpoints
```

Or add to `Program.cs`:

```csharp
app.MapGet("/debug/routes", (IEnumerable<EndpointDataSource> endpointSources) =>
    string.Join("\n", endpointSources.SelectMany(source => source.Endpoints)));
```

## Best Practices

✅ **Do**:
- Use constraints to validate parameters
- Keep URLs short and meaningful
- Use hyphens for multi-word URLs (`/my-page` not `/my_page`)
- Be consistent with casing (lowercase recommended)
- Use route parameters for IDs and dynamic values

❌ **Don't**:
- Use query strings when route parameters work better
- Make URLs too long or complex
- Include sensitive data in URLs
- Use underscores (hyphens are SEO-friendly)
- Change routes frequently (breaks bookmarks)

## Quick Reference

```csharp
// Basic route
@page

// With parameter
@page "{id}"

// Optional parameter
@page "{id?}"

// Integer constraint
@page "{id:int}"

// Multiple parameters
@page "{category}/{id:int}"

// Custom route
@page "/custom-path"

// Blog-style route
@page "{year:int}/{month:int}/{slug}"
```

## Next Steps

- Practice in **01.BasicRouting** project
- Learn Tag Helpers in **02.TagHelpers** project
- Read [Tag Helpers Guide](tag-helpers-guide.md)
