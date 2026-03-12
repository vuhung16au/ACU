# Quick Start — 04.Breadcrumbs

## 1. Run the project

```bash
cd /path/to/05.NavigationRouting
dotnet run --project 04.Breadcrumbs/04.Breadcrumbs.csproj
```

Open the URL printed in the terminal (typically <https://localhost:5001>).

---

## 2. Verify the breadcrumb strip

Every page should show a breadcrumb trail just below the navbar. The trail is rendered in `Pages/Shared/_Layout.cshtml` — it never appears on the root `/` page because a single-item trail (just "Home") is suppressed.

---

## 3. Test each approach

### Approach A — Manual (PageModel sets ViewData)

These pages have hardcoded-data-aware trails set in their `OnGet()`:

| URL | Expected breadcrumb trail |
|-----|--------------------------|
| `/Products` | Home › Products |
| `/Products/Details/1` | Home › Products › Student Laptop |
| `/Products/Details/2` | Home › Products › Wireless Mouse |
| `/Products/Category/Electronics` | Home › Products › Electronics |
| `/Products/Edit/1` | Home › Products › Student Laptop › Edit |
| `/Blog` | Home › Blog |
| `/Blog/Categories` | Home › Blog › Categories |
| `/Blog/Archive/2026` | Home › Blog › Archive › 2026 |
| `/Blog/2026/3/getting-started-with-routing` | Home › Blog › 2026 › March › Getting Started with Routing |

### Approach B — Automatic fallback (URL segments)

Visit a page that has **no** `ViewData["Breadcrumbs"]` set:

| URL | Expected breadcrumb trail |
|-----|--------------------------|
| `/About` | Home › About |
| `/Contact` | Home › Contact |
| `/Courses/Lookup` | Home › Courses › Lookup |

A small italic note will appear under the crumbs:
> *Auto-generated from URL — set `ViewData["Breadcrumbs"]` in the PageModel for custom labels.*

---

## 4. Check accessibility markup

Use browser DevTools → Elements panel. Find the breadcrumb `<nav>`:

```html
<nav aria-label="breadcrumb">
   <ol class="breadcrumb">
      <li class="breadcrumb-item"><a href="/">Home</a></li>
      <li class="breadcrumb-item"><a href="/Products">Products</a></li>
      <li class="breadcrumb-item active" aria-current="page">Student Laptop</li>
   </ol>
</nav>
```

Confirm:
- `aria-label="breadcrumb"` on `<nav>`
- `aria-current="page"` only on the last `<li>`
- All non-active items are `<a>` links

---

## 5. Explore the 5-level trail

Go to `/Blog/2026/3/getting-started-with-routing`.
The breadcrumb should read:  
**Home › Blog › 2026 › March › Getting Started with Routing**

"March" is a display label, not a URL segment — the route parameter is `month=3`. This demonstrates how the manual approach produces human-friendly labels that the automatic fallback cannot.

Go to `/Blog/Archive/2026` instead to see the 4-level trail:  
**Home › Blog › Archive › 2026**

---

## 6. Troubleshooting

| Symptom | Check |
|---------|-------|
| No breadcrumbs visible | `_Layout.cshtml` — ensure the breadcrumb `<nav>` block is inside `<main>` above `@RenderBody()` |
| Breadcrumbs show on home page | The `breadcrumbs != null && breadcrumbs.Count > 1` guard may be missing |
| Wrong namespace build error | `_ViewImports.cshtml` should have `@namespace Breadcrumbs.Pages`; all `.cshtml.cs` files should have `namespace Breadcrumbs.Pages.*` |
| `BreadcrumbItem` not found | Add `@using Breadcrumbs.Models` at the top of `_Layout.cshtml` |

## Prerequisites

Before running the project, confirm:

- .NET 10.0 SDK is installed
- You are in the `05.NavigationRouting` workspace

Check SDK version:

```bash
dotnet --version
```

Expected output: `10.0.x`

## Step 1: Open the Project Folder

```bash
cd 05.NavigationRouting/03.NavigationMenus
```

## Step 2: Restore and Build

```bash
dotnet restore
dotnet build
```

Build should complete without errors.

## Step 3: Run the Project

```bash
dotnet run --project /Users/vuhung/00.Work/02.ACU/github/ITEC323/05.NavigationRouting/03.NavigationMenus/03.NavigationMenus.csproj
```

Open the local URL shown in the terminal.

## Step 4: Verify FR3 Acceptance Criteria

Check each of the following:

1. **Bootstrap navbar exists**
   Confirm top menu appears with brand and nav items.

2. **Dropdown menus work**
   Open `Products` and `Blog` dropdowns on desktop.

3. **Hamburger menu works on mobile width**
   Narrow browser width and click the navbar toggler.

4. **Active page is highlighted**
   Navigate between sections and confirm the current section has `active` style.

5. **Navigation works across pages**
   Test top-level and dropdown links.

## Step 5: Suggested URL Tests

1. `/`
2. `/Products`
3. `/Products/Category/Computers`
4. `/Blog`
5. `/Blog/Categories`
6. `/Blog/Archive/2026`
7. `/About`
8. `/Contact`

## Step 6: Read Code in This Order

1. `Pages/Shared/_Layout.cshtml`
2. `Pages/Index.cshtml`
3. `Pages/Products/Category.cshtml`
4. `Pages/Blog/Archive.cshtml`
5. `wwwroot/css/site.css`

## Troubleshooting

### Build fails

Run:

```bash
dotnet --list-sdks
```

Ensure .NET 10 SDK is installed.

### Navbar toggler does not open menu

Check that Bootstrap JS is loaded in layout:

```html
<script src="~/lib/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
```

### Port already in use

Run on another port:

```bash
dotnet run --project /Users/vuhung/00.Work/02.ACU/github/ITEC323/05.NavigationRouting/03.NavigationMenus/03.NavigationMenus.csproj --urls "http://localhost:5063"
```
