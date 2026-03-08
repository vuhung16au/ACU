# Quick Start - Bootstrap Theme

## Prerequisites

- ✅ .NET 10.0 SDK installed
- ✅ Code editor (VS Code, Visual Studio, Rider)
- ✅ Basic understanding of Razor Pages and layouts
- ✅ Internet connection (for Bootstrap CDN)

## Step 1: Verify .NET Installation

```bash
dotnet --version
# Should show 10.x.x
```

## Step 2: Navigate to Project

```bash
cd 04.ConsistentWebDesign/02.BootstrapTheme
```

## Step 3: Build the Project

```bash
dotnet build
```

**Expected Output**: `Build succeeded. 0 Warning(s) 0 Error(s)`

## Step 4: Run the Application

```bash
dotnet run
```

**Expected Output**:
```
Now listening on: http://localhost:5000
```

## Step 5: Open in Browser

Navigate to: **https://localhost:5001**

You should see:
- Bootstrap-styled navigation bar with logo and icon
- Hero section with blue background
- Cards showing key learning points
- Responsive design (try resizing your browser)

## Step 6: Test Responsive Navigation

1. Open browser DevTools (F12)
2. Click the device toolbar icon (mobile view)
3. Resize to < 992px width
4. Notice the navigation collapses into a hamburger menu
5. Click the hamburger to expand/collapse the menu

## Step 7: Explore Pages

Visit each page to see different Bootstrap features:

1. **Home** (`/`) - Hero section, cards, alerts
2. **Components** (`/Components`) - Alerts, buttons, cards, badges, forms
3. **Grid** (`/Grid`) - Column layouts, responsive breakpoints, nested grids

## Step 8: Inspect Bootstrap Integration

Open `Pages/Shared/_Layout.cshtml` and look for:

```html
<!-- Bootstrap CSS (CDN) -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">

<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
```

## Step 9: Test Bootstrap Components

### Experiment 1: Change Navbar Color

In `_Layout.cshtml`, find:
```html
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
```

Change `bg-primary` to:
- `bg-success` (green)
- `bg-danger` (red)
- `bg-dark` (black)

Refresh browser to see the change applied to **all pages** (DRY principle!).

### Experiment 2: Modify Grid Layout

In `Pages/Grid.cshtml`, find:
```html
<div class="col-md-8">
<div class="col-md-4">
```

Change to `col-md-6` and `col-md-6` for equal columns. Refresh to see 50/50 split.

### Experiment 3: Add New Alert

In `Pages/Index.cshtml`, add:
```html
<div class="alert alert-info" role="alert">
    <i class="bi bi-lightbulb-fill"></i> This is a custom info alert!
</div>
```

## Step 10: Compare CDN vs Local Files

**Current Setup (CDN)**:
- ✅ Files loaded from `cdn.jsdelivr.net`
- ✅ Fast (cached by browsers)
- ✅ Always up-to-date
- ❌ Requires internet connection

**Alternative (Local Files)**:
1. Download Bootstrap from [getbootstrap.com](https://getbootstrap.com)
2. Extract to `wwwroot/lib/bootstrap/`
3. Update `_Layout.cshtml` links:
```html
<link href="~/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="~/lib/bootstrap/js/bootstrap.bundle.min.js"></script>
```

## Troubleshooting

### Issue: Navbar Not Collapsing

**Solution**: Ensure Bootstrap JS is loaded:
```html
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
```

### Issue: Icons Not Showing

**Solution**: Check Bootstrap Icons CDN link in `<head>`:
```html
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
```

### Issue: Styles Not Loading

**Solution**: Verify internet connection and check browser console (F12) for CDN errors.

## Key Takeaways

1. **Bootstrap via CDN** = 2 lines of code (CSS + JS)
2. **Responsive navbar** = Automatic hamburger menu on mobile
3. **Grid system** = `col-md-*` classes for flexible layouts
4. **Components** = Professional UI with minimal effort
5. **DRY Principle** = Layout changes affect all pages instantly

## Next Steps

- Review `docs/Key-Takeaways.md` for deeper concepts
- Experiment with different Bootstrap components
- Try building a custom page using Bootstrap grid and components
- Compare this project with `01.BasicLayout` to see the Bootstrap advantage
