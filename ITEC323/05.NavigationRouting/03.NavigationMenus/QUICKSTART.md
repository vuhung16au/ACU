# Quick Start Guide: 03.NavigationMenus

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
