# DRY Principles: Don't Repeat Yourself in Web Development

## What is DRY?

**DRY (Don't Repeat Yourself)** is a software development principle that states:

> "Every piece of knowledge must have a single, unambiguous, authoritative representation within a system."

In simpler terms: **Don't copy and paste code. Write it once, reuse it everywhere.**

## Why DRY Matters

### Without DRY (WET: "Write Everything Twice")

**Problem**: You have 50 pages, each with this navigation:

```html
<!-- Page1.cshtml -->
<nav>
    <ul>
        <li><a href="/">Home</a></li>
        <li><a href="/about">About</a></li>
        <li><a href="/contact">Contact</a></li>
    </ul>
</nav>
<h1>Page 1 Content</h1>

<!-- Page2.cshtml -->
<nav>
    <ul>
        <li><a href="/">Home</a></li>
        <li><a href="/about">About</a></li>
        <li><a href="/contact">Contact</a></li>
    </ul>
</nav>
<h1>Page 2 Content</h1>

<!-- ...and 48 more pages -->
```

**What happens when you need to add a "Products" link?**
- ❌ Edit 50 files
- ❌ 2+ hours of tedious work
- ❌ High chance of mistakes
- ❌ Easy to miss pages

### With DRY

```html
<!-- _Layout.cshtml -->
<nav>
    <ul>
        <li><a href="/">Home</a></li>
        <li><a href="/about">About</a></li>
        <li><a href="/contact">Contact</a></li>
        <li><a href="/products">Products</a></li> <!-- Added once -->
    </ul>
</nav>

@RenderBody()
```

**Result**:
- ✅ Edit 1 file
- ✅ 30 seconds of work
- ✅ Change applies to all 50 pages automatically
- ✅ Zero mistakes

## DRY in ASP.NET Core Razor Pages

### 1. Layouts (Eliminate Page Structure Duplication)

#### ❌ WET Approach
```html
<!-- Every page has this -->
<!DOCTYPE html>
<html>
<head>
    <title>My Site</title>
    <link rel="stylesheet" href="/css/site.css">
</head>
<body>
    <header><!-- navigation --></header>
    <main>
        <!-- Page content here -->
    </main>
    <footer><!-- footer --></footer>
</body>
</html>
```

#### ✅ DRY Approach
```html
<!-- _Layout.cshtml - Write once -->
<!DOCTYPE html>
<html>
<head>
    <title>@ViewBag.Title</title>
    <link rel="stylesheet" href="/css/site.css">
</head>
<body>
    <header><partial name="_Navigation" /></header>
    <main>@RenderBody()</main>
    <footer><partial name="_Footer" /></footer>
</body>
</html>

<!-- Index.cshtml - Focus on content -->
@page
@{ ViewBag.Title = "Home"; }

<h1>Welcome to our site!</h1>
<p>This page only contains unique content.</p>
```

### 2. Partial Views (Eliminate Component Duplication)

#### ❌ WET Approach
```html
<!-- On 20 different pages, you have this product card: -->
<div class="product-card">
    <img src="@product.ImageUrl" />
    <h3>@product.Name</h3>
    <p>@product.Price.ToString("C")</p>
    <button>Add to Cart</button>
</div>
```

**Problems**:
- Need to update styling? Change 20 pages
- Want to add a "Wishlist" button? Edit 20 files
- Find a bug? Fix in 20 places

#### ✅ DRY Approach
```html
<!-- _ProductCard.cshtml - Write once -->
@model Product
<div class="product-card">
    <img src="@Model.ImageUrl" />
    <h3>@Model.Name</h3>
    <p>@Model.Price.ToString("C")</p>
    <button>Add to Cart</button>
    <button>Add to Wishlist</button> <!-- Added once, appears everywhere -->
</div>

<!-- Usage everywhere -->
<partial name="_ProductCard" model="product" />
```

### 3. CSS Classes (Eliminate Style Duplication)

#### ❌ WET Approach
```html
<!-- Inline styles everywhere -->
<button style="background: blue; color: white; padding: 10px 20px; border-radius: 5px;">
    Submit
</button>

<button style="background: blue; color: white; padding: 10px 20px; border-radius: 5px;">
    Save
</button>

<button style="background: blue; color: white; padding: 10px 20px; border-radius: 5px;">
    Send
</button>
```

#### ✅ DRY Approach
```css
/* site.css - Define once */
.btn-primary {
    background: blue;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
}

/* Or use Bootstrap/Tailwind */
```

```html
<!-- Use class everywhere -->
<button class="btn-primary">Submit</button>
<button class="btn-primary">Save</button>
<button class="btn-primary">Send</button>
```

### 4. C# Code (Eliminate Logic Duplication)

#### ❌ WET Approach
```csharp
// In 10 different Page Models:
public class Page1Model : PageModel
{
    public void OnGet()
    {
        // Same validation logic
        if (string.IsNullOrEmpty(User.Identity?.Name))
        {
            Response.Redirect("/login");
            return;
        }
        
        // Same logging
        Console.WriteLine($"User {User.Identity.Name} visited Page1");
    }
}

// Repeated in Page2Model, Page3Model, etc.
```

#### ✅ DRY Approach
```csharp
// BasePageModel.cs - Write once
public class BasePageModel : PageModel
{
    protected void EnsureAuthenticated()
    {
        if (string.IsNullOrEmpty(User.Identity?.Name))
        {
            Response.Redirect("/login");
        }
    }
    
    protected void LogPageVisit(string pageName)
    {
        Console.WriteLine($"User {User.Identity.Name} visited {pageName}");
    }
}

// Page1Model.cs - Inherit
public class Page1Model : BasePageModel
{
    public void OnGet()
    {
        EnsureAuthenticated();
        LogPageVisit("Page1");
        
        // Only page-specific logic here
    }
}
```

## Common Duplication Patterns (and Solutions)

### Pattern 1: Repeated `<head>` Elements

#### ❌ Problem
```html
<!-- Every page -->
<head>
    <title>My Site</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/site.css">
</head>
```

#### ✅ Solution: Layout
```html
<!-- _Layout.cshtml -->
<head>
    <title>@ViewBag.Title - My Site</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/site.css">
    @RenderSection("Styles", required: false)
</head>
```

### Pattern 2: Repeated Navigation

#### ❌ Problem
Every page has the same 50-line navigation bar copy/pasted.

#### ✅ Solution: Partial View
```html
<!-- _Layout.cshtml -->
<body>
    <partial name="_Navigation" />
    @RenderBody()
</body>

<!-- _Navigation.cshtml -->
<nav class="navbar">
    <!-- 50 lines of navigation here, in ONE place -->
</nav>
```

### Pattern 3: Repeated Footer

#### ❌ Problem
Footer with copyright, links, social media on every page.

#### ✅ Solution: Partial View
```html
<!-- _Layout.cshtml -->
<body>
    <partial name="_Navigation" />
    @RenderBody()
    <partial name="_Footer" />
</body>

<!-- _Footer.cshtml -->
<footer>
    <p>&copy; @DateTime.Now.Year My Company. All rights reserved.</p>
    <!-- Links, social media, etc. -->
</footer>
```

### Pattern 4: Repeated Form Validation

#### ❌ Problem
```csharp
// On every page with a form
if (string.IsNullOrWhiteSpace(Input.Email))
{
    ModelState.AddModelError("Email", "Email is required");
}
if (!Input.Email.Contains("@"))
{
    ModelState.AddModelError("Email", "Invalid email format");
}
```

#### ✅ Solution: Data Annotations
```csharp
public class ContactModel
{
    [Required(ErrorMessage = "Email is required")]
    [EmailAddress(ErrorMessage = "Invalid email format")]
    public string Email { get; set; }
}
```

### Pattern 5: Repeated Error Handling

#### ❌ Problem
```csharp
// On every page
try
{
    // Some code
}
catch (Exception ex)
{
    Console.WriteLine($"Error: {ex.Message}");
    TempData["Error"] = "An error occurred";
}
```

#### ✅ Solution: Base Class or Middleware
```csharp
// BasePageModel.cs
protected IActionResult HandleError(Exception ex, string userMessage)
{
    Console.WriteLine($"Error: {ex.Message}");
    TempData["Error"] = userMessage;
    return Page();
}

// Usage
try { /* code */ }
catch (Exception ex) { return HandleError(ex, "An error occurred"); }
```

## The Cost of Duplication

### Maintenance Cost
- **WET**: Change navigation → Edit 50 files → 2 hours
- **DRY**: Change navigation → Edit 1 file → 2 minutes

### Bug Fix Cost
- **WET**: Fix bug in card component → Find all 30 instances → Fix 30 times
- **DRY**: Fix bug in card partial → Edit 1 file → Fixed everywhere

### New Feature Cost
- **WET**: Add "dark mode" → Update CSS in 50 files
- **DRY**: Add "dark mode" → Update CSS in _Layout.cshtml → Done

### Testing Cost
- **WET**: Test navigation → Test on 50 pages
- **DRY**: Test navigation → Test once (applies to all pages)

## DRY Checklist

Before writing code, ask yourself:

- [ ] Have I written this HTML before?
- [ ] Will this code appear on multiple pages?
- [ ] If I need to change this, how many files would I edit?
- [ ] Can I extract this into a layout, partial, or class?
- [ ] Am I copying and pasting? (Red flag! 🚩)

## When NOT to DRY

DRY is not always the answer:

### Don't Over-DRY

❌ **Bad**: Create a partial for 1 line of HTML
```html
<!-- _OneLinePartial.cshtml -->
<p>Copyright 2026</p>
```

✅ **Good**: Just write it directly if it's tiny and never changes

### Don't Premature DRY

❌ **Bad**: Extract to partial after using once
```html
<!-- Used on only one page -->
<partial name="_ThingUsedOnce" />
```

✅ **Good**: Extract to partial after second or third use (Rule of Three)

### Similar ≠ Same

If two components look similar but serve different purposes, don't force them into one partial:

```html
<!-- ProductCard (for products) -->
<div class="product-card">
    <img src="@Model.ImageUrl" />
    <h3>@Model.Name</h3>
    <p>@Model.Price</p>
    <button>Add to Cart</button>
</div>

<!-- BlogCard (for articles) -->
<div class="blog-card">
    <img src="@Model.ThumbnailUrl" />
    <h3>@Model.Title</h3>
    <p>@Model.Excerpt</p>
    <a href="/blog/@Model.Slug">Read More</a>
</div>
```

These are **different enough** to keep separate, even though they look similar.

## Rule of Three

A good guideline for when to extract reusable code:

1. **First time**: Write the code inline
2. **Second time**: Note the duplication, but don't refactor yet
3. **Third time**: Extract to partial/layout/class

This prevents premature optimization while catching real duplication.

## Real-World Example

### Before DRY (WET)

**50 pages, each with**:
- 30 lines of duplicated `<head>` (1,500 total lines)
- 80 lines of duplicated navigation (4,000 total lines)
- 50 lines of duplicated footer (2,500 total lines)

**Total**: 8,000 lines of duplicated code across 50 pages

### After DRY

- **_Layout.cshtml**: 30 lines
- **_Navigation.cshtml**: 80 lines
- **_Footer.cshtml**: 50 lines
- **50 pages**: Only their unique content (~20 lines each = 1,000 lines)

**Total**: 1,160 lines of code (85% reduction!)

## Summary

| Technique | Eliminates | Example |
|-----------|-----------|---------|
| **Layouts** | Repeated page structure | `<head>`, `<body>` wrapper |
| **Partial Views** | Repeated components | Navigation, footer, cards |
| **CSS Classes** | Repeated styles | Button styles, layouts |
| **Base Classes** | Repeated logic | Authentication, validation |
| **Tag Helpers** | Repeated HTML patterns | Form controls |

**Golden Rule**: If you're tempted to copy/paste, stop and think "Can I reuse this instead?"

---

**Next**: Apply DRY principles in [04.PartialViews](../04.PartialViews/) project to see the benefits firsthand.
