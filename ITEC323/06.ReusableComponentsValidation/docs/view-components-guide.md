# View Components Guide

## What are View Components?

Reusable UI elements combining **C# backend logic** with **Razor views**. Like mini-controllers with their own views.

**Use for:** Shopping cart, user profile, recent items, login status, notification badge

## Two-Part Architecture

1. **C# Class** - Business logic, data fetching
2. **Razor View** - Display logic

## Creating a View Component

### 1. C# Class

**Location:** `ViewComponents/ShoppingCartViewComponent.cs`

```csharp
public class ShoppingCartViewComponent : ViewComponent
{
    private readonly ICartService _cartService;
    
    public ShoppingCartViewComponent(ICartService cartService)
    {
        _cartService = cartService;
    }
    
    public async Task<IViewComponentResult> InvokeAsync(int userId)
    {
        var items = await _cartService.GetUserCartAsync(userId);
        var model = new CartViewModel
        {
            Items = items,
            Total = items.Sum(i => i.Price * i.Quantity)
        };
        return View(model);
    }
}
```

### 2. Razor View

**Location:** `Views/Shared/Components/ShoppingCart/Default.cshtml`

```html
@model CartViewModel
<div class="cart-widget">
    <h5>Cart</h5>
    <p>@Model.Items.Count items - $@Model.Total</p>
</div>
```

## Rendering View Components

### Tag Helper Syntax (Recommended)

```html
<vc:shopping-cart user-id="@Model.UserId"></vc:shopping-cart>
<vc:user-profile user-id="5" show-avatar="true"></vc:user-profile>
```

**Naming:** `ShoppingCartViewComponent` → `<vc:shopping-cart>`  
**Parameters:** PascalCase → kebab-case (`userId` → `user-id`)

### Register Tag Helpers

Add to `_ViewImports.cshtml`:

```csharp
@addTagHelper *, YourProjectName
```

## View Components vs Partial Views

| Feature | View Components | Partial Views |
|---------|----------------|---------------|
| **C# Logic** | ✅ Yes (InvokeAsync) | ❌ No |
| **Data fetching** | ✅ Yes | ❌ No |
| **Files needed** | 2 (C# + Razor) | 1 (.cshtml) |
| **Best for** | Dynamic widgets | Simple HTML reuse |

## Best Practices

✅ **Do:**
- Use for components with logic/data fetching
- Keep components focused (single responsibility)
- Use async methods for I/O operations
- Inject dependencies via constructor

❌ **Don't:**
- Use for simple HTML (use Partial Views)
- Put page-specific logic in components
- Make components too large
- Access HttpContext directly
