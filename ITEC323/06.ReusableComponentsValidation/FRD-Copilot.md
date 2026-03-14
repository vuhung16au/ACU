
Looking at your Week 6 curriculum, I'll suggest a comprehensive module structure that progressively teaches Reusable Components and Validation.

## Folder Name
**06.ReusableComponentsValidation**

## Proposed Project Structure (6 Projects)

| # | Project Name | Key Concepts | Complexity |
|---|--------------|--------------|------------|
| **01** | PartialViews | Basic HTML reuse, `<partial>` tag | ⭐ Foundation |
| **02** | ViewComponents | Logic-driven components, InvokeAsync | ⭐⭐ Foundation |
| **03** | BasicFormValidation | Data Annotations, ModelState | ⭐⭐ Core |
| **04** | AdvancedValidation | [RegularExpression], Custom validation, XSS | ⭐⭐⭐ Core |
| **05** | FileDataHandling | Read/write CSV, file validation | ⭐⭐⭐ Integration |
| **06** | ComprehensiveApp | All concepts combined | ⭐⭐⭐⭐ Capstone |

## Detailed Project Breakdown

### **01.PartialViews** - Reusable HTML Snippets
**FR1: Partial Views Fundamentals**

**What students build:**
- Simple partial views without logic
- Reusable UI components: `_Header.cshtml`, `_Footer.cshtml`, `_ProductCard.cshtml`
- Learn `<partial name="_Header" />` syntax
- Pass simple data with models

**Key Files:**
```
Pages/Shared/
  _Header.cshtml
  _Footer.cshtml
  _ProductCard.cshtml
  _ContactForm.cshtml
Pages/
  Index.cshtml (uses all partials)
```

**Learning Objectives:**
- When to use Partial Views vs View Components
- How to render partials with `<partial>` tag helper
- Passing models to partial views
- DRY principle in action

---

### **02.ViewComponents** - Components with Logic
**FR2: View Components with Backend Logic**

**What students build:**
- View Components with C# logic classes
- Shopping cart summary widget
- Recent items component
- User profile card with data fetching

**Key Files:**
```
ViewComponents/
  ShoppingCartViewComponent.cs
  RecentItemsViewComponent.cs
  UserProfileViewComponent.cs
Views/Shared/Components/
  ShoppingCart/Default.cshtml
  RecentItems/Default.cshtml
  UserProfile/Default.cshtml
```

**Learning Objectives:**
- View Component architecture (C# class + Razor view)
- InvokeAsync method and parameters
- Tag Helper syntax: `<vc:shopping-cart></vc:shopping-cart>`
- When to use View Components over Partial Views

---

### **03.BasicFormValidation** - Data Annotations Basics
**FR3: Core Validation with Data Annotations**

**What students build:**
- User registration form
- Contact form with validation
- Product entry form

**Data Annotations Covered:**
```csharp
[Required]
[StringLength(100, MinimumLength = 3)]
[Range(18, 120)]
[EmailAddress]
[Phone]
[Display(Name = "Full Name")]
```

**Key Features:**
- Client-side validation (jQuery Unobtrusive)
- Server-side validation with ModelState
- `asp-validation-for` tag helper
- `asp-validation-summary` tag helper
- Error message customization

**Example Form:**
```html
<form method="post">
    <input asp-for="Name" class="form-control" />
    <span asp-validation-for="Name" class="text-danger"></span>
    
    <input asp-for="Email" class="form-control" />
    <span asp-validation-for="Email" class="text-danger"></span>
    
    <button type="submit">Register</button>
    <div asp-validation-summary="All" class="text-danger"></div>
</form>
```

---

### **04.AdvancedValidation** - Complex Validation & Security
**FR4: Advanced Validation Patterns & XSS Prevention**

**What students build:**
- Password complexity validation with [RegularExpression]
- Password confirmation with [Compare]
- Custom validation attributes
- XSS attack prevention demo

**Data Annotations Covered:**
```csharp
[RegularExpression(@"^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,}$", 
    ErrorMessage = "Password must contain uppercase, lowercase, and digit")]
[Compare("Password", ErrorMessage = "Passwords don't match")]
[EmailAddress(ErrorMessage = "Invalid email format")]
[Phone(ErrorMessage = "Invalid phone number")]
```

**Custom Validation Example:**
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
            int age = DateTime.Today.Year - birthDate.Year;
            if (age >= _minimumAge)
                return ValidationResult.Success;
        }
        return new ValidationResult($"Must be at least {_minimumAge} years old");
    }
}
```

**XSS Prevention:**
```csharp
// Automatic encoding (safe by default)
<p>@Model.UserInput</p>

// Raw HTML (dangerous - only for trusted content)
<div>@Html.Raw(Model.Content)</div>

// Anti-forgery token
<form method="post">
    @Html.AntiForgeryToken()
    ...
</form>
```

---

### **05.FileDataHandling** - CSV File Operations
**FR5: Reading/Writing Text Files with Validation**

**What students build:**
- Read users from `users.csv`
- Display users in table
- Add new user form with validation
- Append validated data to CSV
- Handle file errors gracefully

**users.csv structure:**
```csv
Id,Name,Email,Phone,DateOfBirth
1,John Doe,john@example.com,0412345678,1990-05-15
2,Jane Smith,jane@example.com,0498765432,1985-08-22
```

**Key Features:**
- Async file I/O (`File.ReadAllLinesAsync`)
- CSV parsing and generation
- Path.Combine for cross-platform paths
- File locking and error handling
- Form validation before file write

**Code Example:**
```csharp
public class IndexModel : PageModel
{
    private readonly string _filePath;
    
    public IndexModel(IWebHostEnvironment env)
    {
        _filePath = Path.Combine(env.ContentRootPath, "Data", "users.csv");
    }
    
    public List<User> Users { get; set; }
    
    [BindProperty]
    public User NewUser { get; set; }
    
    public async Task OnGetAsync()
    {
        Users = await LoadUsersFromCsvAsync();
    }
    
    public async Task<IActionResult> OnPostAsync()
    {
        if (!ModelState.IsValid)
            return Page();
        
        await AppendUserToCsvAsync(NewUser);
        return RedirectToPage();
    }
}
```

---

### **06.ComprehensiveApp** - Complete System
**FR6: User Management System (All Concepts)**

**What students build:**
- Full CRUD user management application
- Combines all previous concepts

**Architecture:**
```
Components/
  Partial Views:
    _Header.cshtml (navigation)
    _Footer.cshtml (copyright)
    _UserCard.cshtml (display user)
  View Components:
    UserStatsViewComponent (total users, recent activity)
    ValidationSummaryViewComponent (custom error display)

Pages/
  Index.cshtml (list users from CSV, use View Component)
  Create.cshtml (form with full validation)
  Edit.cshtml (edit user, load from CSV)
  Delete.cshtml (confirmation page)
  Details.cshtml (user details with partial views)

Models/
  User.cs (all data annotations)
  
Data/
  users.csv
```

**User Model (Complete):**
```csharp
public class User
{
    public int Id { get; set; }
    
    [Required]
    [StringLength(100, MinimumLength = 2)]
    [Display(Name = "Full Name")]
    public string Name { get; set; }
    
    [Required]
    [EmailAddress]
    public string Email { get; set; }
    
    [Required]
    [Phone]
    [RegularExpression(@"^04\d{8}$", ErrorMessage = "Must be Australian mobile: 04xxxxxxxx")]
    public string Phone { get; set; }
    
    [Required]
    [DataType(DataType.Date)]
    [MinimumAge(18, ErrorMessage = "Must be 18 or older")]
    [Display(Name = "Date of Birth")]
    public DateTime DateOfBirth { get; set; }
    
    [Required]
    [StringLength(100, MinimumLength = 8)]
    [RegularExpression(@"^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$",
        ErrorMessage = "Password must contain uppercase, lowercase, digit, and special character")]
    [DataType(DataType.Password)]
    public string Password { get; set; }
    
    [Required]
    [Compare("Password")]
    [DataType(DataType.Password)]
    [Display(Name = "Confirm Password")]
    public string ConfirmPassword { get; set; }
    
    [StringLength(500)]
    [Display(Name = "Bio")]
    public string Bio { get; set; } // For XSS demo
}
```

**Optional Features:**
- Email notification on user creation (MailKit demo)
- Rich text editor for Bio with XSS protection
- Export filtered users to new CSV
- Search and filter functionality

---

## Technology Stack

- **.NET 10.0**
- **Razor Pages** (not MVC)
- **Bootstrap 5** for styling
- **jQuery Unobtrusive Validation** for client-side
- **System.IO** for file operations
- **MailKit** (optional, for email demo)

## Learning Progression

**Week Flow:**
1. **Day 1-2**: Partial Views & View Components (FR1, FR2)
2. **Day 3**: Basic validation (FR3)
3. **Day 4**: Advanced validation & XSS (FR4)
4. **Day 5**: File I/O (FR5)
5. **Day 6-7**: Comprehensive app (FR6) + Lab assignment

## Documentation Structure

Each project includes:
- **README.md** - Project overview, learning objectives
- **QUICKSTART.md** - Setup and running instructions
- **docs/Key-Takeaways.md** - Concepts explained

Module-level docs:
- **docs/partial-views-guide.md**
- **docs/view-components-guide.md**
- **docs/validation-guide.md**
- **docs/file-io-guide.md**
- **docs/xss-prevention.md**

## Essential Questions Answers

**Covered in projects:**
- ✅ When to use Partial View vs View Component → FR1 vs FR2
- ✅ What are Data Annotations → FR3, FR4
- ✅ How .NET tracks validation errors → ModelState in FR3, FR4
- ✅ Why server-side validation is critical → Security demo in FR4
- ✅ Reading text files → FR5, FR6
- ✅ Preventing XSS → FR4, FR6
