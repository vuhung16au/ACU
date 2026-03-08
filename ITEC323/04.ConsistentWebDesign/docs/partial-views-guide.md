# Partial Views Guide: When and How to Use Them

## What is a Partial View?

A **partial view** is a small, reusable chunk of HTML that can be included in multiple pages or layouts. Think of it as a component or building block.

## When to Create a Partial View

✅ **Create a partial when**:
- You copy/paste the same HTML in multiple places
- You have a UI component used repeatedly (navigation, footer, card)
- You want to simplify a complex page by breaking it into pieces
- You need to reuse a component with different data

❌ **Don't create a partial when**:
- The HTML only appears once
- The component is too simple (1-2 lines)
- The component is page-specific with no reuse potential

## Naming Convention

Partial views start with an underscore:
- ✅ `_Navigation.cshtml`
- ✅ `_Footer.cshtml`
- ✅ `_ProductCard.cshtml`
- ❌ `Navigation.cshtml` (missing underscore)

## Location

**Recommended structure**:
```
Pages/
├── Shared/
│   ├── _Layout.cshtml
│   ├── _Navigation.cshtml      ← Global partials
│   ├── _Footer.cshtml
│   └── _Alert.cshtml
├── Products/
│   ├── _ProductCard.cshtml     ← Product-specific partials
│   └── _ProductFilters.cshtml
└── Index.cshtml
```

## How to Use Partial Views

### Method 1: Partial Tag Helper (Recommended)

```html
<!-- Simple usage -->
<partial name="_Navigation" />

<!-- With model data -->
<partial name="_ProductCard" model="product" />

<!-- With view-data -->
<partial name="_Alert" view-data="ViewData" />
```

### Method 2: Html.PartialAsync

```csharp
<!-- Async rendering -->
@await Html.PartialAsync("_Navigation")

<!-- With model -->
@await Html.PartialAsync("_ProductCard", product)
```

### Method 3: Html.RenderPartialAsync

```csharp
<!-- Writes directly to response (slightly faster) -->
@{ await Html.RenderPartialAsync("_Footer"); }
```

## Passing Data to Partial Views

### 1. Strongly Typed Model

**Partial (_ProductCard.cshtml)**:
```csharp
@model Product

<div class="card">
    <h3>@Model.Name</h3>
    <p>@Model.Price.ToString("C")</p>
    <p>@Model.Description</p>
</div>
```

**Usage**:
```html
@foreach (var product in Model.Products)
{
    <partial name="_ProductCard" model="product" />
}
```

### 2. ViewData Dictionary

**Partial**:
```csharp
<div class="alert alert-@ViewData["Type"]">
    @ViewData["Message"]
</div>
```

**Usage**:
```csharp
@{
    ViewData["Type"] = "success";
    ViewData["Message"] = "Order placed successfully!";
}
<partial name="_Alert" view-data="ViewData" />
```

### 3. Dynamic ViewBag

**Partial**:
```csharp
<h1>Welcome, @ViewBag.UserName!</h1>
```

**Usage**:
```csharp
@{
    ViewBag.UserName = "Alice";
}
<partial name="_Welcome" />
```

## Common Partial View Patterns

### Pattern 1: Navigation Menu

**_Navigation.cshtml**:
```html
<nav>
    <ul>
        <li><a href="/">Home</a></li>
        <li><a href="/about">About</a></li>
        <li><a href="/contact">Contact</a></li>
    </ul>
</nav>
```

**Usage in _Layout.cshtml**:
```html
<header>
    <partial name="_Navigation" />
</header>
```

### Pattern 2: Reusable Card Component

**_Card.cshtml**:
```csharp
@model CardViewModel

<div class="card">
    @if (!string.IsNullOrEmpty(Model.ImageUrl))
    {
        <img src="@Model.ImageUrl" alt="@Model.Title" />
    }
    <div class="card-body">
        <h3>@Model.Title</h3>
        <p>@Model.Description</p>
        <a href="@Model.LinkUrl" class="btn">@Model.LinkText</a>
    </div>
</div>
```

**Usage**:
```csharp
@foreach (var card in Model.Cards)
{
    <partial name="_Card" model="card" />
}
```

### Pattern 3: Loading Indicator

**_Loading.cshtml**:
```html
<div class="loading-spinner">
    <div class="spinner"></div>
    <p>Loading...</p>
</div>
```

**Usage**:
```html
<div id="content">
    <partial name="_Loading" />
</div>
```

### Pattern 4: Form Section

**_AddressForm.cshtml**:
```csharp
@model Address

<div class="form-group">
    <label asp-for="Street"></label>
    <input asp-for="Street" class="form-control" />
</div>
<div class="form-group">
    <label asp-for="City"></label>
    <input asp-for="City" class="form-control" />
</div>
<div class="form-group">
    <label asp-for="PostalCode"></label>
    <input asp-for="PostalCode" class="form-control" />
</div>
```

**Usage in multiple forms**:
```html
<form method="post">
    <h3>Billing Address</h3>
    <partial name="_AddressForm" model="Model.BillingAddress" />
    
    <h3>Shipping Address</h3>
    <partial name="_AddressForm" model="Model.ShippingAddress" />
    
    <button type="submit">Submit</button>
</form>
```

## Partial Views vs View Components

| Feature | Partial View | View Component |
|---------|--------------|----------------|
| **Complexity** | Simple HTML | Complex logic + data access |
| **Logic** | Minimal | Can have business logic |
| **Async Data** | No | Yes, can query database |
| **Invocation** | `<partial>` tag | `@await Component.InvokeAsync()` |
| **Use Case** | UI templates | Dynamic widgets with data |

**Rule of Thumb**: If it needs to fetch data or has complex logic, use a View Component. Otherwise, use a Partial View.

## Best Practices

### 1. Keep Partials Focused
❌ Bad: One mega-partial with everything  
✅ Good: Small, single-purpose partials

### 2. Use Strongly Typed Models
❌ Bad: `ViewBag.Property1`, `ViewBag.Property2`, etc.  
✅ Good: `@model MyViewModel` with all properties

### 3. Avoid Deep Nesting
❌ Bad: Partial → Partial → Partial → Partial (4+ levels)  
✅ Good: Maximum 2-3 levels deep

### 4. Name Descriptively
❌ Bad: `_Partial1.cshtml`, `_Thing.cshtml`  
✅ Good: `_ProductCard.cshtml`, `_UserProfile.cshtml`

### 5. Document Complex Partials
```csharp
@*
    Product Card Component
    
    Model: Product
    Required Properties: Name, Price
    Optional Properties: ImageUrl, Description
    
    Usage:
    <partial name="_ProductCard" model="product" />
*@
@model Product
```

## Common Mistakes

### Mistake 1: Missing Model Directive
```csharp
<!-- ❌ No @model directive -->
<h1>@Model.Name</h1>  <!-- Error! What is Model? -->

<!-- ✅ With @model directive -->
@model Product
<h1>@Model.Name</h1>  <!-- Works! -->
```

### Mistake 2: Wrong Path
```html
<!-- ❌ Including .cshtml extension or full path -->
<partial name="Shared/_Navigation.cshtml" />

<!-- ✅ Correct: Just the name -->
<partial name="_Navigation" />
```

### Mistake 3: Not Passing Required Data
```html
<!-- ❌ Partial expects model but none provided -->
<partial name="_ProductCard" />

<!-- ✅ Model provided -->
<partial name="_ProductCard" model="product" />
```

## Testing Partials

While you can't directly unit test partial views, you can:
1. Test the model/data you pass to them
2. Integration test the parent page that uses the partial
3. Manually verify rendering in browser

## Quick Reference

```csharp
// Create: Pages/Shared/_MyPartial.cshtml
@model MyType
<div>@Model.Property</div>

// Use: Any page
<partial name="_MyPartial" model="myData" />

// Use with ViewData:
<partial name="_MyPartial" view-data="ViewData" />

// Use async:
@await Html.PartialAsync("_MyPartial", myData)
```

## Real-World Example

**Scenario**: E-commerce site with product cards everywhere

**_ProductCard.cshtml**:
```csharp
@model Product

<div class="product-card">
    <img src="@Model.ImageUrl" alt="@Model.Name" />
    <h3>@Model.Name</h3>
    <p class="price">@Model.Price.ToString("C")</p>
    <p class="description">@Model.ShortDescription</p>
    <a href="/products/@Model.Id" class="btn">View Details</a>
    <form method="post" asp-page-handler="AddToCart">
        <input type="hidden" name="productId" value="@Model.Id" />
        <button type="submit" class="btn btn-primary">Add to Cart</button>
    </form>
</div>
```

**Usage across multiple pages**:
```csharp
// Homepage (featured products)
@foreach (var product in Model.FeaturedProducts)
{
    <partial name="_ProductCard" model="product" />
}

// Category page (filtered products)
@foreach (var product in Model.CategoryProducts)
{
    <partial name="_ProductCard" model="product" />
}

// Search results
@foreach (var product in Model.SearchResults)
{
    <partial name="_ProductCard" model="product" />
}
```

**Result**: One HTML template, reused 100+ times across site. Update card design once, changes everywhere!

---

**Next**: Explore [CSS Framework Comparison](css-frameworks-comparison.md) for styling your partials.
