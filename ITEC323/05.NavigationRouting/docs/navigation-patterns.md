# Navigation Patterns

## Common Navigation Patterns

Well-designed navigation helps users find content quickly. This guide covers the most common patterns using Bootstrap 5 and Razor Pages.

## 1. Top Navbar (Horizontal)

**Best for**: Main site navigation, always visible

### Basic Navbar

```html
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" asp-page="/Index">MySite</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
                data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" asp-page="/Index">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" asp-page="/Products/Index">Products</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" asp-page="/About">About</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
```

### Navbar with Dropdown

```html
<ul class="navbar-nav">
    <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" role="button" 
           data-bs-toggle="dropdown">
            Products
        </a>
        <ul class="dropdown-menu">
            <li>
                <a class="dropdown-item" asp-page="/Products/Index">All Products</a>
            </li>
            <li>
                <a class="dropdown-item" asp-page="/Products/Category" asp-route-name="Electronics">
                    Electronics
                </a>
            </li>
            <li>
                <a class="dropdown-item" asp-page="/Products/Category" asp-route-name="Books">
                    Books
                </a>
            </li>
            <li><hr class="dropdown-divider"></li>
            <li>
                <a class="dropdown-item" asp-page="/Products/Featured">Featured</a>
            </li>
        </ul>
    </li>
</ul>
```

### Active Link Highlighting

```csharp
@functions {
    public string GetActiveClass(string page)
    {
        var currentPage = ViewContext.RouteData.Values["page"]?.ToString();
        return currentPage?.StartsWith(page) == true ? "active" : "";
    }
}

<ul class="navbar-nav">
    <li class="nav-item">
        <a class="nav-link @GetActiveClass("/Index")" asp-page="/Index">Home</a>
    </li>
    <li class="nav-item">
        <a class="nav-link @GetActiveClass("/Products")" asp-page="/Products/Index">Products</a>
    </li>
    <li class="nav-item">
        <a class="nav-link @GetActiveClass("/About")" asp-page="/About">About</a>
    </li>
</ul>
```

## 2. Breadcrumbs

**Best for**: Showing user location in site hierarchy

### Static Breadcrumbs

```html
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item">
            <a asp-page="/Index">Home</a>
        </li>
        <li class="breadcrumb-item">
            <a asp-page="/Products/Index">Products</a>
        </li>
        <li class="breadcrumb-item active" aria-current="page">
            Details
        </li>
    </ol>
</nav>
```

### Dynamic Breadcrumbs

```csharp
// PageModel
public List<BreadcrumbItem> Breadcrumbs { get; set; }

public void OnGet()
{
    Breadcrumbs = new List<BreadcrumbItem>
    {
        new BreadcrumbItem("Home", "/Index"),
        new BreadcrumbItem("Products", "/Products/Index"),
        new BreadcrumbItem("Electronics", "/Products/Category?name=Electronics"),
        new BreadcrumbItem("Laptop XYZ", null) // Current page, no link
    };
}

public class BreadcrumbItem
{
    public string Text { get; set; }
    public string Page { get; set; }
    
    public BreadcrumbItem(string text, string page)
    {
        Text = text;
        Page = page;
    }
}
```

```html
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        @foreach (var item in Model.Breadcrumbs)
        {
            @if (item.Page != null)
            {
                <li class="breadcrumb-item">
                    <a asp-page="@item.Page">@item.Text</a>
                </li>
            }
            else
            {
                <li class="breadcrumb-item active" aria-current="page">
                    @item.Text
                </li>
            }
        }
    </ol>
</nav>
```

## 3. Sidebar Navigation (Vertical)

**Best for**: Documentation sites, admin panels, dashboards

### Basic Sidebar

```html
<div class="row">
    <!-- Sidebar -->
    <nav class="col-md-3 col-lg-2 d-md-block bg-light sidebar">
        <div class="position-sticky pt-3">
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link" asp-page="/Docs/Index">
                        Getting Started
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" asp-page="/Docs/Installation">
                        Installation
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" asp-page="/Docs/Configuration">
                        Configuration
                    </a>
                </li>
            </ul>
        </div>
    </nav>
    
    <!-- Main Content -->
    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
        @RenderBody()
    </main>
</div>
```

### Collapsible Sidebar

```html
<ul class="nav flex-column">
    <li class="nav-item">
        <a class="nav-link" asp-page="/Docs/Index">Home</a>
    </li>
    
    <!-- Collapsible section -->
    <li class="nav-item">
        <a class="nav-link d-flex justify-content-between align-items-center" 
           data-bs-toggle="collapse" href="#gettingStarted">
            Getting Started
            <i class="bi bi-chevron-down"></i>
        </a>
        <div class="collapse" id="gettingStarted">
            <ul class="nav flex-column ms-3">
                <li class="nav-item">
                    <a class="nav-link" asp-page="/Docs/Installation">Installation</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" asp-page="/Docs/Quickstart">Quickstart</a>
                </li>
            </ul>
        </div>
    </li>
    
    <li class="nav-item">
        <a class="nav-link d-flex justify-content-between align-items-center" 
           data-bs-toggle="collapse" href="#tutorials">
            Tutorials
            <i class="bi bi-chevron-down"></i>
        </a>
        <div class="collapse" id="tutorials">
            <ul class="nav flex-column ms-3">
                <li class="nav-item">
                    <a class="nav-link" asp-page="/Docs/Tutorial1">Tutorial 1</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" asp-page="/Docs/Tutorial2">Tutorial 2</a>
                </li>
            </ul>
        </div>
    </li>
</ul>
```

### Offcanvas Sidebar (Mobile-Friendly)

```html
<!-- Toggle Button -->
<button class="btn btn-primary d-lg-none" type="button" 
        data-bs-toggle="offcanvas" data-bs-target="#sidebar">
    <i class="bi bi-list"></i> Menu
</button>

<!-- Offcanvas Sidebar -->
<div class="offcanvas offcanvas-start d-lg-none" tabindex="-1" id="sidebar">
    <div class="offcanvas-header">
        <h5 class="offcanvas-title">Navigation</h5>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas"></button>
    </div>
    <div class="offcanvas-body">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link" asp-page="/Index">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" asp-page="/About">About</a>
            </li>
        </ul>
    </div>
</div>

<!-- Desktop Sidebar (always visible on large screens) -->
<nav class="col-lg-2 d-none d-lg-block bg-light sidebar">
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link" asp-page="/Index">Home</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" asp-page="/About">About</a>
        </li>
    </ul>
</nav>
```

## 4. Footer Navigation

**Best for**: Secondary links, legal, social media

```html
<footer class="bg-dark text-white mt-5 py-4">
    <div class="container">
        <div class="row">
            <!-- Company Info -->
            <div class="col-md-4">
                <h5>MySite</h5>
                <p>Building amazing web experiences.</p>
            </div>
            
            <!-- Quick Links -->
            <div class="col-md-4">
                <h5>Quick Links</h5>
                <ul class="list-unstyled">
                    <li><a class="text-white" asp-page="/About">About</a></li>
                    <li><a class="text-white" asp-page="/Contact">Contact</a></li>
                    <li><a class="text-white" asp-page="/Privacy">Privacy</a></li>
                    <li><a class="text-white" asp-page="/Terms">Terms</a></li>
                </ul>
            </div>
            
            <!-- Social Media -->
            <div class="col-md-4">
                <h5>Follow Us</h5>
                <ul class="list-unstyled">
                    <li><a class="text-white" href="https://twitter.com">Twitter</a></li>
                    <li><a class="text-white" href="https://github.com">GitHub</a></li>
                </ul>
            </div>
        </div>
        
        <hr class="bg-white">
        
        <div class="text-center">
            <p>&copy; 2026 MySite. All rights reserved.</p>
        </div>
    </div>
</footer>
```

## 5. Pagination

**Best for**: Large lists, search results

```html
<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        <li class="page-item @(Model.CurrentPage == 1 ? "disabled" : "")">
            <a class="page-link" asp-page="/Products/Index" asp-route-page="@(Model.CurrentPage - 1)">
                Previous
            </a>
        </li>
        
        @for (int i = 1; i <= Model.TotalPages; i++)
        {
            <li class="page-item @(Model.CurrentPage == i ? "active" : "")">
                <a class="page-link" asp-page="/Products/Index" asp-route-page="@i">
                    @i
                </a>
            </li>
        }
        
        <li class="page-item @(Model.CurrentPage == Model.TotalPages ? "disabled" : "")">
            <a class="page-link" asp-page="/Products/Index" asp-route-page="@(Model.CurrentPage + 1)">
                Next
            </a>
        </li>
    </ul>
</nav>
```

## 6. Tabs

**Best for**: Organizing related content on same page

```html
<ul class="nav nav-tabs" role="tablist">
    <li class="nav-item" role="presentation">
        <button class="nav-link active" id="overview-tab" data-bs-toggle="tab" 
                data-bs-target="#overview" type="button" role="tab">
            Overview
        </button>
    </li>
    <li class="nav-item" role="presentation">
        <button class="nav-link" id="specs-tab" data-bs-toggle="tab" 
                data-bs-target="#specs" type="button" role="tab">
            Specifications
        </button>
    </li>
    <li class="nav-item" role="presentation">
        <button class="nav-link" id="reviews-tab" data-bs-toggle="tab" 
                data-bs-target="#reviews" type="button" role="tab">
            Reviews
        </button>
    </li>
</ul>

<div class="tab-content mt-3">
    <div class="tab-pane fade show active" id="overview" role="tabpanel">
        <p>Product overview content...</p>
    </div>
    <div class="tab-pane fade" id="specs" role="tabpanel">
        <p>Specifications content...</p>
    </div>
    <div class="tab-pane fade" id="reviews" role="tabpanel">
        <p>Customer reviews...</p>
    </div>
</div>
```

## Navigation Best Practices

### Accessibility
- Use semantic HTML: `<nav>`, `<ul>`, `<li>`
- Add ARIA labels: `aria-label`, `aria-current`
- Ensure keyboard navigation works
- Provide text alternatives for icons

### Mobile-First
- Test on small screens first
- Use hamburger menu for mobile
- Make touch targets at least 44×44 pixels
- Use offcanvas for space-constrained layouts

### User Experience
- Keep navigation consistent across pages
- Highlight current page/section
- Use clear, descriptive labels
- Group related items together
- Limit top-level items (5-7 max)

### Performance
- Load Bootstrap JS only if using interactive components
- Minimize navbar height for more content space
- Use CSS for simple effects instead of JavaScript

## Common Layouts

### E-commerce
1. **Top Navbar**: Logo, search, cart, account
2. **Category Bar**: Product categories
3. **Sidebar**: Filters (price, brand, rating)
4. **Breadcrumbs**: Home > Category > Product
5. **Footer**: Links, policies, social media

### Documentation
1. **Top Navbar**: Logo, search, GitHub link
2. **Sidebar**: Docs tree navigation
3. **Breadcrumbs**: Docs > Section > Page
4. **In-page Nav**: Table of contents
5. **Footer**: Version info, links

### Blog
1. **Top Navbar**: Logo, categories, search
2. **Breadcrumbs**: Home > Category > Post
3. **Sidebar**: Recent posts, categories, tags
4. **Pagination**: Post navigation
5. **Footer**: About, social media, archives

## Next Steps

- Implement patterns in **03.NavigationMenus** project
- Build breadcrumbs in **04.Breadcrumbs** project
- Create sidebar in **05.SidebarNavigation** project
- Read [SEO & Sitemap Guide](seo-sitemap.md)
