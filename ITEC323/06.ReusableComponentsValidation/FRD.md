# Functional Requirements Document (FRD)
# Week 6: Reusable UI Components and Model Validation

## Purpose

This document defines the functional requirements for six progressive projects teaching reusable components, form validation, file I/O, and security best practices in ASP.NET Core Razor Pages.

## Target Audience

First-year university students learning web development with .NET 10 and Razor Pages.

---

## FR1: Partial Views Fundamentals (01.PartialViews)

### Description
Students will create and use Partial Views to eliminate HTML duplication and improve code maintainability.

### Functional Requirements

**FR1.1: Create Partial Views**
- Create `_Header.cshtml` with site navigation
- Create `_Footer.cshtml` with copyright and links
- Create `_ProductCard.cshtml` for displaying product information
- Create `_ContactForm.cshtml` with basic form inputs
- All partial views stored in `Pages/Shared/` folder

**FR1.2: Render Partial Views**
- Use `<partial name="_Header" />` syntax
- Render partials without models (static content)
- Pass models to partials: `<partial name="_ProductCard" model="product" />`
- Use partials in multiple pages (Index, Products, Contact)

**FR1.3: Model Passing**
- Define simple model classes (Product, ContactInfo)
- Pass strongly-typed data to partial views
- Access model properties in partial views with `@Model`

### Acceptance Criteria
- ✅ At least 4 different partial views created
- ✅ Partials rendered successfully on multiple pages
- ✅ Model data displayed correctly in partials
- ✅ No code duplication between pages
- ✅ Application builds and runs without errors

### Deliverables
- Project folder: `01.PartialViews/`
- Partial views in `Pages/Shared/`
- At least 3 pages using partials
- README.md and QUICKSTART.md

---

## FR2: View Components with Logic (02.ViewComponents)

### Description
Students will create View Components that combine C# backend logic with Razor views for dynamic, reusable UI elements.

### Functional Requirements

**FR2.1: Create View Component Classes**
- Create `ShoppingCartViewComponent.cs` class
- Create `UserProfileViewComponent.cs` class
- Create `RecentItemsViewComponent.cs` class
- Inherit from `ViewComponent` base class
- Implement `InvokeAsync` method with business logic

**FR2.2: Create View Component Views**
- Create `Views/Shared/Components/ShoppingCart/Default.cshtml`
- Create `Views/Shared/Components/UserProfile/Default.cshtml`
- Create `Views/Shared/Components/RecentItems/Default.cshtml`
- Use strongly-typed models in component views

**FR2.3: Render View Components**
- Use Tag Helper syntax: `<vc:shopping-cart></vc:shopping-cart>`
- Pass parameters to components: `<vc:user-profile user-id="@Model.UserId"></vc:user-profile>`
- Register Tag Helpers in `_ViewImports.cshtml`

**FR2.4: Component Logic**
- Fetch data in `InvokeAsync` method
- Perform calculations or filtering
- Return view with model: `return View(model);`

### Acceptance Criteria
- ✅ At least 3 View Components with C# classes
- ✅ Each component has corresponding Default.cshtml
- ✅ Components rendered using Tag Helper syntax
- ✅ Business logic executes in InvokeAsync
- ✅ Parameters passed correctly to components
- ✅ Application builds and runs without errors

### Deliverables
- Project folder: `02.ViewComponents/`
- ViewComponents/ folder with C# classes
- Views/Shared/Components/ with Razor views
- _ViewImports.cshtml with Tag Helper registration
- README.md and QUICKSTART.md

---

## FR3: Basic Form Validation (03.BasicFormValidation)

### Description
Students will implement form validation using Data Annotations, ModelState, and Tag Helpers.

### Functional Requirements

**FR3.1: Model with Data Annotations**
- Create User model with validation attributes:
  - `[Required]` on Name, Email
  - `[StringLength(100, MinimumLength = 3)]` on Name
  - `[EmailAddress]` on Email
  - `[Phone]` on Phone number
  - `[Display(Name = "Full Name")]` for labels
  - `[Range(18, 120)]` on Age

**FR3.2: Create Forms**
- Registration form with all user fields
- Contact form with name, email, message
- Use `asp-for` Tag Helper for inputs
- Add `asp-validation-for` for field-level errors
- Add `asp-validation-summary` for all errors

**FR3.3: Server-Side Validation**
- Check `ModelState.IsValid` in PageModel
- Return to page if validation fails
- Process data only when valid
- Display success message after valid submission

**FR3.4: Client-Side Validation**
- Include jQuery Validation scripts
- Include jQuery Unobtrusive Validation
- Enable real-time validation feedback
- Test validation without server round-trip

### Acceptance Criteria
- ✅ Models use at least 5 different Data Annotations
- ✅ Forms display validation errors inline
- ✅ Server-side validation prevents invalid submissions
- ✅ Client-side validation provides instant feedback
- ✅ Custom error messages displayed correctly
- ✅ Application builds and runs without errors

### Deliverables
- Project folder: `03.BasicFormValidation/`
- Models/ folder with annotated classes
- Pages/ with registration and contact forms
- Validation scripts included in _Layout.cshtml
- README.md and QUICKSTART.md

---

## FR4: Advanced Validation & XSS Prevention (04.AdvancedValidation)

### Description
Students will implement complex validation patterns, custom validators, and XSS attack prevention.

### Functional Requirements

**FR4.1: Regular Expression Validation**
- Australian mobile: `[RegularExpression(@"^04\d{8}$")]`
- Password complexity: `[RegularExpression(@"^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$")]`
- Postal code: `[RegularExpression(@"^\d{4}$")]`
- Username: `[RegularExpression(@"^[a-zA-Z0-9_]{3,20}$")]`

**FR4.2: Compare Attribute**
- Password confirmation field
- Use `[Compare("Password")]` attribute
- Display error when passwords don't match

**FR4.3: Custom Validation**
- Create `MinimumAgeAttribute` class
- Inherit from `ValidationAttribute`
- Override `IsValid` method
- Calculate age from birth date
- Return ValidationResult with custom message

**FR4.4: XSS Prevention**
- Create form with text area for bio/comments
- Show automatic HTML encoding: `@Model.UserInput`
- Demonstrate XSS vulnerability with `@Html.Raw()`
- Explain when to use Raw (trusted content only)
- Include anti-forgery token in forms

**FR4.5: Rich Text Handling**
- Accept HTML input for blog post/bio
- Sanitize HTML before saving
- Display sanitized HTML safely
- Show XSS attack examples

### Acceptance Criteria
- ✅ At least 4 RegularExpression validators implemented
- ✅ Password confirmation works with [Compare]
- ✅ Custom MinimumAge validator functions correctly
- ✅ XSS prevention demonstrated with examples
- ✅ Both safe and unsafe rendering shown
- ✅ Forms include anti-forgery tokens
- ✅ Application builds and runs without errors

### Deliverables
- Project folder: `04.AdvancedValidation/`
- Models/ with complex validation attributes
- CustomValidators/ folder with custom attributes
- Pages demonstrating XSS scenarios
- README.md with security explanations
- QUICKSTART.md

---

## FR5: File Data Handling (05.FileDataHandling)

### Description
Students will read and write CSV files asynchronously with proper validation and error handling.

### Functional Requirements

**FR5.1: CSV File Structure**
- Create `Data/users.csv` with headers:
  ```csv
  Id,Name,Email,Phone,DateOfBirth
  1,John Doe,john@example.com,0412345678,1990-05-15
  2,Jane Smith,jane@example.com,0498765432,1985-08-22
  ```

**FR5.2: Read CSV File**
- Use `File.ReadAllLinesAsync()` for async reading
- Parse CSV into User objects
- Handle headers (skip first line)
- Split fields by comma
- Convert strings to appropriate types (int, DateTime)

**FR5.3: Display Users**
- Show all users in Bootstrap table
- Display: Name, Email, Phone, Age (calculated)
- Format dates consistently
- Responsive table design

**FR5.4: Add User Form**
- Create form with all user fields
- Validate using Data Annotations:
  - [Required], [EmailAddress], [Phone]
  - [RegularExpression] for phone format
  - [MinimumAge(18)] custom validator
- Check ModelState before saving

**FR5.5: Write to CSV**
- Use `File.AppendAllTextAsync()` for async writing
- Format data as CSV line
- Escape commas in data fields
- Handle file locking errors
- Redirect after successful save

**FR5.6: Error Handling**
- Try-catch for file operations
- Handle FileNotFoundException
- Handle IOException (file locked)
- Display user-friendly error messages
- Log errors for debugging

**FR5.7: Cross-Platform Paths**
- Use `Path.Combine()` for file paths
- Use `IWebHostEnvironment.ContentRootPath`
- Ensure compatibility: Windows, macOS, Linux

### Acceptance Criteria
- ✅ CSV file read successfully with async operations
- ✅ Users displayed in table with correct formatting
- ✅ Form validation works before file operations
- ✅ New users appended to CSV correctly
- ✅ File errors handled gracefully
- ✅ File paths work cross-platform
- ✅ No blocking I/O operations
- ✅ Application builds and runs without errors

### Deliverables
- Project folder: `05.FileDataHandling/`
- Data/users.csv with sample data
- Services/ folder with CsvService class
- Pages/ with list and create forms
- Error handling and logging
- README.md and QUICKSTART.md

---

## FR6: Comprehensive User Management System (06.ComprehensiveApp)

### Description
Students will build a complete CRUD application combining all concepts: Partial Views, View Components, validation, file operations, and security.

### Functional Requirements

**FR6.1: Component Architecture**
- Partial Views:
  - `_Header.cshtml` - Navigation bar
  - `_Footer.cshtml` - Copyright and links
  - `_UserCard.cshtml` - Display single user
  - `_UserForm.cshtml` - Reusable form fields
- View Components:
  - `UserStatsViewComponent` - Total users, recent signups
  - `RecentActivityViewComponent` - Last 5 actions

**FR6.2: User Model (Complete)**
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
    [RegularExpression(@"^04\d{8}$")]
    public string Phone { get; set; }
    
    [Required]
    [DataType(DataType.Date)]
    [MinimumAge(18)]
    [Display(Name = "Date of Birth")]
    public DateTime DateOfBirth { get; set; }
    
    [Required]
    [StringLength(100, MinimumLength = 8)]
    [RegularExpression(@"^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$")]
    [DataType(DataType.Password)]
    public string Password { get; set; }
    
    [Compare("Password")]
    [DataType(DataType.Password)]
    public string ConfirmPassword { get; set; }
    
    [StringLength(500)]
    public string Bio { get; set; }
}
```

**FR6.3: CRUD Operations**
- **Create**: Add new user with full validation
- **Read**: List all users, view single user details
- **Update**: Edit existing user, reload from CSV
- **Delete**: Remove user with confirmation

**FR6.4: File Operations**
- Read users from `Data/users.csv`
- Write new users to CSV
- Update CSV when editing
- Delete line from CSV when removing user
- Handle concurrent access

**FR6.5: Security Features**
- XSS prevention in bio field
- Server-side validation on all forms
- Anti-forgery tokens
- Password complexity enforcement
- HTML encoding by default

**FR6.6: User Interface**
- Bootstrap 5 styling throughout
- Responsive design (mobile-friendly)
- Validation errors displayed inline
- Success/error messages with alerts
- Accessible forms (ARIA labels)

**FR6.7: Navigation**
- Top navbar with all pages
- Breadcrumbs showing current location
- User stats in sidebar (View Component)
- Footer on all pages

### Acceptance Criteria
- ✅ All CRUD operations functional
- ✅ Partial Views and View Components used appropriately
- ✅ Complete validation on all forms
- ✅ XSS prevention demonstrated
- ✅ CSV file operations work correctly
- ✅ Error handling throughout
- ✅ Responsive UI with Bootstrap 5
- ✅ No security vulnerabilities
- ✅ Application builds and runs without errors
- ✅ Code is well-organized and commented

### Deliverables
- Project folder: `06.ComprehensiveApp/`
- Complete component architecture
- All CRUD pages implemented
- Data/users.csv with sample data
- Comprehensive error handling
- Security features implemented
- README.md with architecture overview
- QUICKSTART.md with setup instructions
- docs/Key-Takeaways.md

---

## Non-Functional Requirements

### NFR1: Security
- All user input validated server-side
- XSS prevention through automatic encoding
- Anti-forgery tokens in all POST forms
- No sensitive data in logs or error messages
- Password handling follows best practices

### NFR2: Performance
- Async file operations (no blocking I/O)
- Efficient CSV parsing
- Minimal database/file reads
- Client-side validation reduces server load

### NFR3: Usability
- Clear error messages in plain language
- Responsive design (works on mobile)
- Accessible forms (WCAG 2.1 guidelines)
- Consistent UI across all pages
- Loading indicators for async operations

### NFR4: Maintainability
- DRY principle (no code duplication)
- Separation of concerns (Models, Views, PageModels)
- Component reusability
- Clear naming conventions
- XML documentation comments on public members

### NFR5: Browser Compatibility
- Modern browsers (Chrome, Firefox, Safari, Edge)
- JavaScript enabled for client-side validation
- Graceful degradation if JS disabled
- CSS Grid and Flexbox support

### NFR6: Cross-Platform
- Works on Windows, macOS, Linux
- File paths use Path.Combine
- Line endings handled correctly (CRLF vs LF)
- .NET 10 SDK required

---

## Testing Requirements

### Unit Testing
- Test Data Annotation validation logic
- Test custom validator classes
- Test CSV parsing and formatting
- Test model validation independently

### Integration Testing
- Test form submission with valid data
- Test form submission with invalid data
- Test file read/write operations
- Test component rendering

### Manual Testing
- Test all CRUD operations
- Test validation with various inputs
- Test XSS scenarios (safe and unsafe)
- Test file operations (create, read, update, delete)
- Test error handling (missing files, locked files)
- Test UI responsiveness on different screen sizes

---

## Environment and Tools

### Required Software
- .NET 10.0 SDK or later
- Visual Studio Code or Visual Studio 2022
- Modern web browser
- Terminal/Command Prompt

### Project Structure
```
ProjectName/
├── ProjectName.csproj
├── Program.cs
├── appsettings.json
├── README.md
├── QUICKSTART.md
├── Pages/
│   ├── _ViewImports.cshtml
│   ├── _ViewStart.cshtml
│   ├── Index.cshtml
│   ├── Index.cshtml.cs
│   └── Shared/
│       └── _Layout.cshtml
├── ViewComponents/ (when needed)
├── Views/Shared/Components/ (when needed)
├── Models/
├── Data/ (for CSV files)
├── wwwroot/
│   ├── css/
│   ├── js/
│   └── lib/
└── docs/
    └── Key-Takeaways.md
```

### Dependencies
- Microsoft.AspNetCore.App (built-in)
- Bootstrap 5 (CDN)
- jQuery (CDN, for client validation)
- jQuery Validation (CDN)
- jQuery Validation Unobtrusive (CDN)

---

## Validation Patterns Reference

### Common Data Annotations
```csharp
[Required(ErrorMessage = "Name is required")]
[StringLength(100, MinimumLength = 3)]
[Range(1, 100)]
[EmailAddress]
[Phone]
[Url]
[DataType(DataType.Date)]
[DataType(DataType.Password)]
[Display(Name = "Full Name")]
[Compare("OtherProperty")]
[RegularExpression(@"pattern")]
```

### Regular Expression Patterns
```csharp
// Australian mobile
@"^04\d{8}$"

// Strong password
@"^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$"

// Australian postcode
@"^\d{4}$"

// Username (alphanumeric + underscore)
@"^[a-zA-Z0-9_]{3,20}$"

// Email (basic)
@"^[^@\s]+@[^@\s]+\.[^@\s]+$"
```

---

## Success Criteria

Students successfully complete this module when they can:
1. ✅ Explain the difference between Partial Views and View Components
2. ✅ Create and render both types of components
3. ✅ Implement comprehensive form validation
4. ✅ Use Data Annotations effectively
5. ✅ Create custom validation attributes
6. ✅ Prevent XSS attacks
7. ✅ Read and write CSV files asynchronously
8. ✅ Build a complete CRUD application
9. ✅ Handle errors gracefully
10. ✅ Write maintainable, secure code
