# Partial Views Guide

## What are Partial Views?

Reusable `.cshtml` files containing HTML and Razor markup **without backend logic**.

**Use for:** Headers, footers, product cards, repeated HTML patterns  
**Don't use for:** Components needing C# logic/data fetching → Use View Components

## Key Concepts

### 1. File Naming

Start with underscore: `_MyPartial.cshtml`

```
Pages/Shared/
├── _Header.cshtml
├── _Footer.cshtml
└── _ProductCard.cshtml
```

### 2. No @page Directive

Partials are fragments, not full pages:

```html
<!-- _Header.cshtml -->
<header>
    <h1>My Website</h1>
    <nav>
        <a href="/">Home</a>
        <a href="/about">About</a>
    </nav>
</header>
```

### 3. Rendering

```html
<!-- Without model -->
<partial name="_Header" />

<!-- With model -->
<partial name="_ProductCard" model="product" />

<!-- In loop -->
@foreach (var product in Model.Products)
{
    <partial name="_ProductCard" model="product" />
}
```

### 4. Strongly-Typed Model

```html
<!-- _ProductCard.cshtml -->
@model Product
<div class="card">
    <h5>@Model.Name</h5>
    <p>$@Model.Price</p>
</div>
```

## Partial Views vs View Components

| Feature | Partial Views | View Components |
|---------|---------------|-----------------|
| **Purpose** | Simple HTML reuse | Logic + UI |
| **Backend code** | ❌ No | ✅ Yes |
| **Data fetching** | ❌ No | ✅ Yes |
| **Best for** | Headers, footers, cards | Shopping cart, widgets |

**Rule:** Need logic? → View Component. Just HTML? → Partial View.

## Best Practices

✅ **Do:**
- Use underscore prefix (`_Header.cshtml`)
- Store shared partials in `Pages/Shared/`
- Keep partials simple and focused
- Use strongly-typed models

❌ **Don't:**
- Put business logic in partials
- Use `@page` directive
- Fetch data (use View Components)
- Make partials too complex
