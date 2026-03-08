# Week 6: Reusable UI Components and Model Validation

## Overview

This module teaches you how to build maintainable, secure web applications using reusable components and robust validation. You'll learn to break down complex interfaces into manageable pieces and protect your application from bad data and security vulnerabilities.

## What You'll Build

Six progressive projects that teach component reuse, form validation, and data handling:

| # | Project | Key Concepts | Difficulty |
|---|---------|--------------|-----------|
| **01** | [PartialViews](01.PartialViews/) | HTML reuse, `<partial>` tag | ⭐ Foundation |
| **02** | [ViewComponents](02.ViewComponents/) | Logic + UI, InvokeAsync | ⭐⭐ Foundation |
| **03** | [BasicFormValidation](03.BasicFormValidation/) | Data Annotations, ModelState | ⭐⭐ Core |
| **04** | [AdvancedValidation](04.AdvancedValidation/) | RegularExpression, XSS prevention | ⭐⭐⭐ Core |
| **05** | [FileDataHandling](05.FileDataHandling/) | CSV operations, validation | ⭐⭐⭐ Integration |
| **06** | [ComprehensiveApp](06.ComprehensiveApp/) | Complete CRUD system | ⭐⭐⭐⭐ Capstone |

## Learning Objectives

By the end of this module, you will:

1. **Understand component architecture** - Know when to use Partial Views vs View Components
2. **Create reusable UI** - Build components that eliminate code duplication
3. **Implement validation** - Use Data Annotations to define input rules
4. **Secure applications** - Prevent XSS attacks and data corruption
5. **Handle user input** - Process forms with GET and POST methods
6. **Work with files** - Read and write CSV data asynchronously
7. **Validate client & server** - Implement two-layer validation strategy
8. **Build complete systems** - Combine all concepts into functional applications

## Why This Matters

**Reusable Components:**
- Reduce code duplication (DRY principle)
- Make maintenance easier
- Enable consistent design across pages
- Separate concerns (logic vs presentation)

**Validation:**
- Prevent database corruption from bad data
- Stop security attacks (XSS, injection)
- Improve user experience with instant feedback
- Ensure data integrity

## Essential Questions

**Component Architecture:**
- When should we use a simple Partial View versus a View Component?
- How do View Components handle complex backend logic?
- How does `InvokeAsync` enable dynamic components?

**Validation:**
- What are Data Annotations and how do they work?
- How does the .NET runtime track validation errors with ModelState?
- Why is server-side validation critical even with client-side checks?
- How can malicious users exploit unsanitized input?

**Security:**
- What is XSS (Cross-Site Scripting)?
- How does ASP.NET Core protect against XSS by default?
- When is `@Html.Raw()` dangerous?

## Project Progression

### Phase 1: Component Patterns (Projects 01-02)
Learn the building blocks of reusable UI:
- **01.PartialViews**: Simple HTML reuse without logic
- **02.ViewComponents**: Components with C# backend logic

### Phase 2: Form Validation (Projects 03-04)
Master data validation and security:
- **03.BasicFormValidation**: Core Data Annotations ([Required], [EmailAddress], [Phone])
- **04.AdvancedValidation**: Complex patterns, custom validation, XSS prevention

### Phase 3: Data Integration (Projects 05-06)
Combine components with file operations:
- **05.FileDataHandling**: Read/write CSV files with validation
- **06.ComprehensiveApp**: Complete user management system

## Key Technologies

- **ASP.NET Core Razor Pages** (.NET 10)
- **Partial Views** - Reusable .cshtml files
- **View Components** - C# class + Razor view
- **Data Annotations** - Validation attributes
- **Tag Helpers** - asp-validation-for, asp-validation-summary
- **jQuery Unobtrusive Validation** - Client-side validation
- **System.IO** - File operations
- **Bootstrap 5** - UI styling

## Prerequisites

Before starting this module, you should understand:
- Razor Pages basics (Week 3)
- Layout system (Week 4)
- Routing and Tag Helpers (Week 5)
- HTML forms and input elements
- Basic C# classes and methods

## Quick Start

1. **Read the documentation** in [docs/](docs/) folder
2. **Start with Project 01** - Build foundation understanding
3. **Work sequentially** - Each project builds on previous concepts
4. **Test thoroughly** - Run `dotnet build` and `dotnet run`
5. **Experiment** - Modify code to deepen understanding

## Documentation

Comprehensive guides in [docs/](docs/) folder:

- **[Partial Views Guide](docs/partial-views-guide.md)** - When and how to use Partial Views
- **[View Components Guide](docs/view-components-guide.md)** - Building components with logic
- **[Validation Guide](docs/validation-guide.md)** - Data Annotations and ModelState
- **[File I/O Guide](docs/file-io-guide.md)** - Reading and writing CSV files
- **[XSS Prevention](docs/xss-prevention.md)** - Security best practices

## Common Patterns

### Rendering Components

```html
<!-- Partial View (simple HTML) -->
<partial name="_Header" />
<partial name="_ProductCard" model="product" />

<!-- View Component (with logic) -->
<vc:shopping-cart user-id="@Model.UserId"></vc:shopping-cart>
<vc:user-profile username="@User.Identity.Name"></vc:user-profile>
```

### Form Validation

```csharp
// Model with Data Annotations
public class User
{
    [Required]
    [StringLength(100)]
    public string Name { get; set; }
    
    [Required]
    [EmailAddress]
    public string Email { get; set; }
    
    [Phone]
    public string Phone { get; set; }
}

// PageModel validation
public IActionResult OnPost()
{
    if (!ModelState.IsValid)
        return Page();
    
    // Process valid data
    return RedirectToPage("Success");
}
```

```html
<!-- View with validation -->
<form method="post">
    <input asp-for="Name" class="form-control" />
    <span asp-validation-for="Name" class="text-danger"></span>
    
    <button type="submit">Submit</button>
    <div asp-validation-summary="All" class="text-danger"></div>
</form>
```

## Best Practices

**Components:**
- Use Partial Views for simple HTML reuse
- Use View Components for logic-driven UI
- Keep components small and focused
- Pass only necessary data to components

**Validation:**
- Always validate on server-side (client validation can be bypassed)
- Use built-in Data Annotations when possible
- Create custom validators for complex rules
- Provide clear error messages

**Security:**
- Never trust user input
- Use automatic HTML encoding (default in Razor)
- Avoid `@Html.Raw()` unless absolutely necessary
- Validate all data before saving to files/database

**File Operations:**
- Use async methods (ReadAllLinesAsync, WriteAllLinesAsync)
- Handle file not found errors gracefully
- Use Path.Combine for cross-platform paths
- Lock files during write operations

## Troubleshooting

**View Component not found:**
- Check `_ViewImports.cshtml` has `@addTagHelper *, YourProjectName`
- Verify component name matches class name (minus "ViewComponent")

**Validation not working:**
- Ensure client-side scripts are included (jQuery, jQuery Validation)
- Check ModelState.IsValid in PageModel
- Verify Data Annotations are on properties

**File operations failing:**
- Check file paths are correct
- Ensure directory exists
- Handle file locking (close streams properly)

## Getting Help

1. **Check documentation** - Read guides in docs/ folder
2. **Review examples** - See working code in project folders
3. **Check build errors** - Run `dotnet build` for compilation errors
4. **Test incrementally** - Make small changes and test often

## Next Steps

After completing this module:
- **Week 7**: Database integration with Entity Framework Core
- **Week 8**: Authentication and authorization
- **Week 9**: API development and consumption

---

**Module:** Week 6 - Reusable Components & Validation  
**Target Framework:** .NET 10.0  
**Last Updated:** March 2026
