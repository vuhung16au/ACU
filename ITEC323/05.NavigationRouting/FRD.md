# Functional Requirements Document: Navigation & Routing Module

## Purpose

This module teaches students how ASP.NET Core Razor Pages routing works and how to implement professional navigation structures using Tag Helpers and Bootstrap components.

## Target Audience

First-year students with basic ASP.NET Core Razor Pages knowledge and understanding of layouts from Week 4.

## Learning Objectives

By completing this module, students will be able to:

1. Understand how URLs map to Razor Pages using endpoint routing
2. Use route parameters to capture data from URLs
3. Generate dynamic URLs using Tag Helpers (asp-page, asp-route-*)
4. Build responsive navigation menus with Bootstrap
5. Implement breadcrumb navigation for site hierarchy
6. Create sidebar navigation for complex sites
7. Use programmatic redirects (301/302)
8. Generate sitemap.xml for SEO

## Functional Requirements

### FR1: Routing Fundamentals
**Priority**: Critical  
**Folder**: 01.BasicRouting  
**Description**: Students must understand endpoint routing and URL patterns.

**Acceptance Criteria**:
- Can explain how `/Products/Details/5` maps to `/Pages/Products/Details.cshtml`
- Can use route parameters: `@page "{id}"`
- Can apply route constraints: `@page "{id:int}"`
- Understand optional parameters: `@page "{id?}"`
- Create friendly URLs (e.g., `/blog/2026/03/my-post`)

**Environment**:
- ASP.NET Core Razor Pages
- .NET 10.0
- C# 14
- macOS

**Deliverables**:
- README.md: Routing concepts, conventions
- QUICKSTART.md: Setup and testing instructions
- `docs/Key-Takeaways.md`: Route patterns reference

**Build Requirements**:
- `dotnet build` succeeds
- `dotnet run` works successfully
- Same structure as previous projects

---

### FR2: Tag Helpers for Navigation
**Priority**: Critical  
**Folder**: 02.TagHelpers  
**Description**: Students can generate URLs dynamically using Tag Helpers.

**Acceptance Criteria**:
- Can use `asp-page="/About"` instead of `href="/About"`
- Can pass route parameters: `asp-route-id="5"`
- Can use multiple route parameters: `asp-route-year`, `asp-route-month`
- Can highlight active navigation links
- Understand why Tag Helpers are better than hardcoded URLs

**Key Learning**: Dynamic URL generation, route parameter passing

---

### FR3: Navigation Menus with Bootstrap
**Priority**: High  
**Folder**: 03.NavigationMenus  
**Description**: Students can build responsive navigation menus.

**Acceptance Criteria**:
- Can create Bootstrap navbar with dropdown menus
- Can implement responsive navigation (hamburger menu on mobile)
- Can highlight the current page in navigation
- Can structure multi-level menus (parent > child)
- Navigation works on desktop and mobile

**Components**:
- Horizontal navbar with Bootstrap
- Dropdown menus for grouped pages
- Active link highlighting
- Mobile-responsive design

---

### FR4: Breadcrumb Navigation
**Priority**: High  
**Folder**: 04.Breadcrumbs  
**Description**: Students can implement breadcrumb trails.

**Acceptance Criteria**:
- Can create Bootstrap breadcrumb component
- Can generate breadcrumbs dynamically from route
- Can pass breadcrumb data from PageModel
- Breadcrumbs are accessible (aria-label)
- Show current location in site hierarchy

**Example**: Home > Products > Electronics > Laptops

---

### FR5: Sidebar Navigation
**Priority**: Medium  
**Folder**: 05.SidebarNavigation  
**Description**: Students can implement sidebar navigation for complex sites.

**Acceptance Criteria**:
- Can create fixed/sticky sidebar navigation
- Can implement collapsible menu sections
- Can use Bootstrap Offcanvas for mobile
- Can nest navigation items (2-3 levels)
- Sidebar highlights current page

**Use Cases**:
- Documentation sites
- Admin dashboards
- E-commerce category navigation

---

### FR6: Comprehensive Navigation System
**Priority**: High  
**Folder**: 06.ComprehensiveNavigation  
**Description**: Students combine all navigation patterns into a real-world application.

**Acceptance Criteria**:
- Implements top navbar with dropdowns
- Implements breadcrumbs on all pages
- Implements sidebar navigation (where appropriate)
- Includes footer navigation
- Generates sitemap.xml for SEO
- Uses programmatic redirects appropriately
- All navigation is responsive and accessible

**Advanced Features**:
- Data-driven menus (from configuration/database)
- View Components for reusable navigation
- Search functionality
- Redirect patterns (login, 404, etc.)

---

## Non-Functional Requirements

### NFR1: Code Quality
- Follow C# and Razor naming conventions
- Use semantic HTML for navigation (`<nav>`, `<ul>`, `<ol>`)
- Include XML documentation for complex logic
- Clean, readable code with comments

### NFR2: Educational Value
- Projects progress from simple to complex
- Each concept isolated before combining
- Clear comments explaining routing mechanics
- Real-world examples

### NFR3: Performance
- Generated URLs are efficient
- Navigation renders without delay
- Lazy loading for large menus (if needed)
- Minimal JavaScript for navigation

### NFR4: Accessibility
- Semantic HTML in all navigation
- ARIA labels on navigation elements
- Keyboard navigation support
- Screen reader friendly
- Focus indicators on links

### NFR5: Responsive Design
- Mobile-first approach
- Bootstrap breakpoints used correctly
- Hamburger menu for mobile
- Offcanvas sidebars on mobile
- Touch-friendly navigation

### NFR6: SEO
- Friendly URLs (no query strings when avoidable)
- Proper HTML structure
- sitemap.xml generated correctly
- Canonical URLs for duplicate content
- Breadcrumb structured data (JSON-LD)

## Project Requirements

### 01.BasicRouting
- Multiple pages with different routing patterns
- Route parameters (required and optional)
- Route constraints (int, regex, custom)
- Friendly URL examples (blog posts, products)
- Default routing conventions demonstrated

### 02.TagHelpers
- Pages using `asp-page` for all links
- Examples of `asp-route-*` with single/multiple parameters
- Active link highlighting implementation
- Comparison: Tag Helpers vs hardcoded URLs
- Page handler examples (`asp-page-handler`)

### 03.NavigationMenus
- Bootstrap navbar with logo/brand
- Dropdown menus (at least 2)
- Responsive hamburger menu
- Active page highlighting
- Minimum 5-6 navigation items

### 04.Breadcrumbs
- Bootstrap breadcrumb component
- Manual breadcrumb implementation (ViewData)
- Automatic breadcrumb generation (route-based)
- Breadcrumbs on at least 5 pages
- Accessible markup

### 05.SidebarNavigation
- Fixed/sticky sidebar with 2-3 levels of nesting
- Collapsible sections (Bootstrap Collapse)
- Bootstrap Offcanvas for mobile
- Active section/item highlighting
- Minimum 10 navigation items in sidebar

### 06.ComprehensiveNavigation
- Real-world application (blog, docs, or e-commerce)
- All navigation types integrated
- sitemap.xml generation
- Redirect examples (301, 302)
- View Component for reusable navigation
- Search functionality

## Documentation Requirements

Each project must include:
- **README.md**: Overview, learning objectives, key concepts
- **QUICKSTART.md**: Step-by-step setup and run instructions
- **docs/Key-Takeaways.md**: Summary of important concepts and patterns

Module must include:
- **docs/routing-explained.md**: Endpoint routing deep dive
- **docs/tag-helpers-guide.md**: Complete Tag Helper reference
- **docs/navigation-patterns.md**: UI patterns and best practices
- **docs/seo-sitemap.md**: SEO optimization guide

## Constraints

- Must use ASP.NET Core Razor Pages (not MVC)
- Must target .NET 10.0
- Must work on Windows, macOS, and Linux
- Bootstrap 5 for UI components
- No JavaScript frameworks (keep it simple)
- Focus on server-side rendering

## Success Criteria

Students successfully complete this module when they can:

1. Explain how endpoint routing maps URLs to pages
2. Create clean, friendly URLs with route parameters
3. Use Tag Helpers exclusively for navigation links
4. Build a responsive Bootstrap navbar
5. Implement breadcrumb navigation
6. Create sidebar navigation with multiple levels
7. Generate sitemap.xml for SEO
8. Use programmatic redirects appropriately

## Testing Requirements

- Each project must build without errors (`dotnet build`)
- All pages must render correctly (`dotnet run`)
- Navigation links must work on all pages
- Responsive design must work on mobile/desktop
- Route parameters must be captured correctly
- Breadcrumbs must reflect site hierarchy
- sitemap.xml must be valid XML

## Key Routing Patterns to Demonstrate

```
/                               → Pages/Index.cshtml
/About                          → Pages/About.cshtml
/Products                       → Pages/Products/Index.cshtml
/Products/Details/5             → Pages/Products/Details.cshtml (id=5)
/Products/Edit/5                → Pages/Products/Edit.cshtml (id=5)
/Blog/2026/03/my-post          → Pages/Blog/Post.cshtml (year, month, slug)
/Account/Orders/12345          → Pages/Account/OrderDetails.cshtml (id=12345)
```

## Navigation Patterns to Implement

1. **Horizontal Navbar** (Bootstrap navbar with dropdowns)
2. **Breadcrumbs** (Bootstrap breadcrumb component)
3. **Sidebar** (Fixed/sticky with collapsible sections)
4. **Footer Links** (Sitemap-style footer navigation)
5. **Mobile Navigation** (Hamburger menu, offcanvas)

## Future Enhancements

Potential additions for advanced students:
- Area support for modular applications
- Custom route constraints
- Attribute routing alternatives
- Advanced View Components
- Client-side routing with Blazor
- Internationalization (i18n) with routing


## Module Structure Overview

Similar to Week 4's progressive approach, create **5-6 projects** that build from basic to advanced:

```
05.NavigationRouting/
├── README.md                          # Module overview
├── FRD.md                             # Functional requirements
├── docs/
│   ├── routing-explained.md           # Endpoint routing, URL patterns
│   ├── tag-helpers-guide.md           # asp-page, asp-route-* helpers
│   ├── navigation-patterns.md         # Menus, breadcrumbs, sidebars
│   └── seo-sitemap.md                 # sitemap.xml, SEO considerations
│
├── 01.BasicRouting/                   # FR1: Routing fundamentals
├── 02.TagHelpers/                     # FR2: Dynamic URL generation
├── 03.NavigationMenus/                # FR3: Menus with Bootstrap
├── 04.Breadcrumbs/                    # FR4: Breadcrumb navigation
├── 05.SidebarNavigation/              # FR5: Sidebars and multi-level menus
└── 06.ComprehensiveNavigation/        # FR6: Complete navigation system
```


## Project Breakdown

### **01.BasicRouting** - Understanding Endpoint Routing

**Purpose**: Learn how URLs map to Razor Pages

**Key Concepts**:
- How `/Products/Edit` maps to `/Pages/Products/Edit.cshtml`
- Default routing conventions (Index.cshtml = default page)
- Route parameters: `@page "{id}"`
- Optional parameters: `@page "{id?}"`
- Route constraints: `@page "{id:int}"`

**Pages**:
```
Pages/
├── Index.cshtml                       # Root page (/)
├── About.cshtml                       # Simple route (/About)
├── Products/
│   ├── Index.cshtml                   # /Products
│   ├── Details.cshtml                 # @page "{id:int}"
│   └── Edit.cshtml                    # @page "{id:int?}"
└── Blog/
    ├── Index.cshtml                   # /Blog
    └── Post.cshtml                    # @page "{year:int}/{month:int}/{slug}"
```

**Learning Outcomes**:
- Understand folder-based routing
- Use route parameters to capture data from URLs
- Apply route constraints for validation
- See how friendly URLs work (`/blog/2026/03/my-post` instead of `?id=123`)

---

### **02.TagHelpers** - Dynamic URL Generation

**Purpose**: Use Tag Helpers to generate URLs dynamically

**Key Concepts**:
- `asp-page="/About"` vs `href="/About"`
- `asp-route-id="5"` for passing route parameters
- `asp-route-*` for multiple parameters
- `asp-page-handler="Delete"` for page handlers
- Active link highlighting with CSS classes

**Pages**:
```
Pages/
├── Index.cshtml                       # Navigation hub
├── Products/
│   ├── Index.cshtml                   # List with links to Details
│   ├── Details.cshtml                 # Uses asp-route-id
│   └── Edit.cshtml                    # Uses asp-route-id, asp-page-handler
└── Shared/
    └── _Layout.cshtml                 # Nav bar with Tag Helpers
```

**Examples to Demonstrate**:
```html
<!-- Instead of: -->
<a href="/Products/Details?id=5">View Product</a>

<!-- Use Tag Helpers: -->
<a asp-page="/Products/Details" asp-route-id="5">View Product</a>

<!-- Multiple parameters: -->
<a asp-page="/Blog/Post" 
   asp-route-year="2026" 
   asp-route-month="03" 
   asp-route-slug="my-post">Read Post</a>
```

**Learning Outcomes**:
- Generate links that survive route changes
- Pass data through URLs cleanly
- Highlight active navigation items
- Understand why Tag Helpers are better than hardcoded URLs

---

### **03.NavigationMenus** - Building Menus with Bootstrap

**Purpose**: Create responsive navigation menus

**Key Concepts**:
- Bootstrap NavBar with dropdown menus
- Multi-level navigation (nav > dropdown > items)
- Active link highlighting based on current page
- Data-driven menus (fetch from config/database)

**Features**:
- **Horizontal NavBar** (top navigation)
- **Dropdown menus** for grouped pages
- **Active state** styling (current page highlighted)
- **Mobile responsive** (hamburger menu)

**Implementation Ideas**:
```
Menu structure:
├── Home
├── Products ▼
│   ├── All Products
│   ├── Electronics
│   ├── Clothing
│   └── Books
├── Blog ▼
│   ├── Recent Posts
│   ├── Categories
│   └── Archive
└── Contact
```

**Code Pattern**:
```html
<!-- Static menu in _Layout.cshtml -->
<ul class="navbar-nav">
    <li class="nav-item">
        <a class="nav-link" asp-page="/Index">Home</a>
    </li>
    <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
            Products
        </a>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item" asp-page="/Products/Index">All Products</a></li>
            <li><a class="dropdown-item" asp-page="/Products/Electronics">Electronics</a></li>
        </ul>
    </li>
</ul>
```

**Advanced**: Create a `NavigationService` or View Component that reads menu structure from `appsettings.json` or database.

---

### **04.Breadcrumbs** - Implementing Breadcrumb Navigation

**Purpose**: Show current page location in site hierarchy

**Key Concepts**:
- Bootstrap breadcrumb component
- Dynamic breadcrumb generation
- Passing breadcrumb data from PageModel
- Accessible breadcrumb markup (aria-label)

**Example Breadcrumbs**:
```
Home > Products > Electronics > Laptops > MacBook Pro
Home > Blog > 2026 > March > My Post Title
Home > Account > Orders > Order #12345
```

**Implementation Approaches**:

**Approach 1: Manual (ViewData)**
```csharp
// In PageModel
public void OnGet()
{
    ViewData["Breadcrumbs"] = new List<(string Text, string Url)>
    {
        ("Home", "/"),
        ("Products", "/Products"),
        ("Electronics", "/Products/Electronics"),
        ("Laptops", null) // Current page, no link
    };
}
```

**Approach 2: Automatic (Route-based)**
Generate breadcrumbs from URL segments:
- `/Products/Details/5` → Home > Products > Details

**Approach 3: View Component**
Reusable component that generates breadcrumbs based on current route.

**Bootstrap Markup**:
```html
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a asp-page="/Index">Home</a></li>
        <li class="breadcrumb-item"><a asp-page="/Products/Index">Products</a></li>
        <li class="breadcrumb-item active" aria-current="page">Details</li>
    </ol>
</nav>
```

---

### **05.SidebarNavigation** - Sidebars & Multi-Level Menus

**Purpose**: Implement sidebar navigation for complex sites

**Key Concepts**:
- Bootstrap Offcanvas for mobile sidebars
- Hierarchical/nested navigation (parent-child relationships)
- Collapsible menu sections (Bootstrap Collapse)
- Sticky sidebar positioning

**Layout Structure**:
```
┌─────────────────────────────────────┐
│         Top NavBar                  │
├──────────┬──────────────────────────┤
│          │                          │
│ Sidebar  │   Main Content           │
│          │                          │
│ ▼ Docs   │   Page content here...   │
│   - Intro│                          │
│   - Setup│                          │
│ ▼ Guides │                          │
│   - Basic│                          │
│   - Adv  │                          │
└──────────┴──────────────────────────┘
```

**Features**:
- **Nested navigation** (2-3 levels deep)
- **Expandable sections** (Bootstrap Collapse or Accordion)
- **Responsive** (offcanvas on mobile, fixed on desktop)
- **Active item highlighting** (show current page in sidebar)

**Example Use Cases**:
- Documentation site navigation
- Dashboard with module sections
- E-commerce category navigation
- Admin panel menu

**Implementation Pattern**:
```html
<!-- Sidebar with Bootstrap -->
<nav id="sidebar" class="col-md-3 col-lg-2 d-md-block bg-light sidebar">
    <div class="position-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link" data-bs-toggle="collapse" href="#docsMenu">
                    Documentation
                </a>
                <div class="collapse" id="docsMenu">
                    <ul class="nav flex-column ms-3">
                        <li><a asp-page="/Docs/GettingStarted">Getting Started</a></li>
                        <li><a asp-page="/Docs/Installation">Installation</a></li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
</nav>
```

---

### **06.ComprehensiveNavigation** - Real-World Navigation System

**Purpose**: Combine all concepts into a production-ready navigation system

**Features**:
- ✅ Top navbar with dropdowns
- ✅ Breadcrumbs on all pages
- ✅ Sidebar navigation (for specific sections)
- ✅ Footer navigation with sitemap
- ✅ Dynamic menu generation from data source
- ✅ `sitemap.xml` generation for SEO
- ✅ Programmatic redirects (301/302)
- ✅ Search functionality in navigation

**Page Structure**:
```
Pages/
├── Index.cshtml                       # Homepage
├── Products/
│   ├── Index.cshtml                   # Product listing
│   ├── Details.cshtml                 # Product details with breadcrumbs
│   ├── Category.cshtml                # @page "{category}"
│   └── Search.cshtml                  # Product search
├── Docs/
│   ├── _Layout.cshtml                 # Custom layout with sidebar
│   ├── Index.cshtml
│   ├── GettingStarted.cshtml
│   └── ...
├── Account/
│   ├── Login.cshtml
│   ├── Orders.cshtml
│   └── OrderDetails.cshtml            # @page "{id:int}"
└── Admin/
    └── Dashboard.cshtml               # Demonstrates redirects
```

**Advanced Features**:

1. **View Component for Navigation**:
```csharp
public class NavigationMenuViewComponent : ViewComponent
{
    public async Task<IViewComponentResult> InvokeAsync()
    {
        var menuItems = await _menuService.GetMenuItemsAsync();
        return View(menuItems);
    }
}
```

2. **Sitemap.xml Generation**:
```csharp
// Sitemap.cshtml.cs
public class SitemapModel : PageModel
{
    public IActionResult OnGet()
    {
        var xml = GenerateSitemapXml();
        return Content(xml, "application/xml");
    }
}
```

3. **Programmatic Redirects**:
```csharp
public IActionResult OnGet()
{
    if (!User.Identity.IsAuthenticated)
    {
        return RedirectToPage("/Account/Login");
    }
    
    // Permanent redirect (301)
    return RedirectToPagePermanent("/Products/NewLocation");
}
```

4. **Search with Redirect**:
```csharp
public IActionResult OnPost(string searchTerm)
{
    return RedirectToPage("/Products/Search", new { q = searchTerm });
}
```

---

## Documentation Files (docs/)

### **routing-explained.md**
- Endpoint routing architecture
- Convention-based routing vs attribute routing
- Route parameter syntax (`{id}`, `{id?}`, `{id:int}`)
- Route constraints and custom constraints
- Route templates and patterns
- How `/Products/Details/5` maps to `Pages/Products/Details.cshtml`

### **tag-helpers-guide.md**
- Complete reference of navigation Tag Helpers
- `asp-page`, `asp-route-*`, `asp-page-handler`
- `asp-area`, `asp-fragment` (anchor links)
- Generating URLs programmatically (`Url.Page()`)
- Active link highlighting techniques
- Form Tag Helpers for navigation

### **navigation-patterns.md**
- Bootstrap NavBar patterns
- Dropdown menus (single and multi-level)
- Breadcrumb implementation strategies
- Sidebar patterns (fixed, sticky, offcanvas)
- Footer navigation
- Mobile-first navigation design
- Accessibility best practices (ARIA attributes)

### **seo-sitemap.md**
- Why sitemap.xml matters for SEO
- XML sitemap structure
- Static vs dynamic sitemap generation
- Submitting to Google Search Console
- robots.txt configuration
- Canonical URLs for duplicate content
- Structured data for breadcrumbs (JSON-LD)

---

## Learning Progression

### Week 5 Learning Path:

1. **Day 1-2**: Basic routing concepts (Project 01)
   - Students learn URL-to-page mapping
   - Understand route parameters
   - Create friendly URLs

2. **Day 3**: Tag Helpers (Project 02)
   - Replace hardcoded links with Tag Helpers
   - Pass data through routes
   - Highlight active links

3. **Day 4**: Navigation menus (Project 03)
   - Build Bootstrap navbar
   - Implement dropdown menus
   - Make responsive

4. **Day 5**: Breadcrumbs (Project 04)
   - Add breadcrumbs to pages
   - Understand site hierarchy
   - Improve UX

5. **Day 6**: Advanced navigation (Projects 05-06)
   - Implement sidebar navigation
   - Combine all navigation types
   - Add programmatic redirects