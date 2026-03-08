# Validation Guide

## Two-Layer Validation

### Client-Side
- **Purpose:** Instant feedback, better UX
- **Technology:** jQuery Unobtrusive Validation
- **Can be bypassed:** Users can disable JavaScript

### Server-Side
- **Purpose:** Security, data integrity
- **Technology:** ModelState, Data Annotations
- **Cannot be bypassed:** Always executes

**Golden Rule:** Always validate server-side!

## Core Data Annotations

| Annotation | Purpose | Example |
|------------|---------|---------|
| `[Required]` | Field cannot be null/empty | `[Required]` |
| `[StringLength(max, MinimumLength = min)]` | String length limits | `[StringLength(100, MinimumLength = 3)]` |
| `[Range(min, max)]` | Numeric range | `[Range(18, 120)]` |
| `[EmailAddress]` | Valid email format | `[EmailAddress]` |
| `[Phone]` | Valid phone format | `[Phone]` |
| `[RegularExpression(pattern)]` | Custom pattern | `[RegularExpression(@"^04\d{8}$")]` |
| `[Compare(property)]` | Match another property | `[Compare("Password")]` |
| `[DataType(type)]` | Specify data type | `[DataType(DataType.Date)]` |

## Common Patterns

```csharp
// Australian mobile: 04xxxxxxxx
[RegularExpression(@"^04\d{8}$", ErrorMessage = "Must be Australian mobile")]
public string Mobile { get; set; }

// Strong password
[RegularExpression(@"^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$")]
public string Password { get; set; }

// Australian postcode
[RegularExpression(@"^\d{4}$")]
public string Postcode { get; set; }
```

## Custom Validation

```csharp
public class MinimumAgeAttribute : ValidationAttribute
{
    private readonly int _minimumAge;
    
    public MinimumAgeAttribute(int minimumAge)
    {
        _minimumAge = minimumAge;
    }
    
    protected override ValidationResult IsValid(object value, ValidationContext context)
    {
        if (value is DateTime birthDate)
        {
            var age = DateTime.Today.Year - birthDate.Year;
            if (birthDate.Date > DateTime.Today.AddYears(-age)) age--;
            
            if (age >= _minimumAge)
                return ValidationResult.Success;
            
            return new ValidationResult($"Must be at least {_minimumAge} years old");
        }
        return new ValidationResult("Invalid date");
    }
}

// Usage
[MinimumAge(18)]
[DataType(DataType.Date)]
public DateTime DateOfBirth { get; set; }
```

## Server-Side Validation

```csharp
public class CreateModel : PageModel
{
    [BindProperty]
    public User NewUser { get; set; }
    
    public IActionResult OnPost()
    {
        if (!ModelState.IsValid)
        {
            return Page();  // Show errors
        }
        
        // Save data
        _service.Add(NewUser);
        return RedirectToPage("Index");
    }
}
```

## Client-Side Setup

### Include Scripts

```html
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validation-unobtrusive/4.0.0/jquery.validate.unobtrusive.min.js"></script>
```

### Form with Validation

```html
<form method="post">
    <div class="mb-3">
        <label asp-for="NewUser.Name"></label>
        <input asp-for="NewUser.Name" class="form-control" />
        <span asp-validation-for="NewUser.Name" class="text-danger"></span>
    </div>
    
    <div asp-validation-summary="All" class="text-danger"></div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
```

## Best Practices

✅ **Do:**
- Always validate server-side
- Use clear error messages
- Test with invalid data
- Use built-in annotations when possible

❌ **Don't:**
- Trust client-side validation alone
- Ignore ModelState.IsValid
- Create complex custom validators for simple cases
