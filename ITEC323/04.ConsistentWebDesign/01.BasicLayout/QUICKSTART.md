# Quick Start Guide: BasicLayout

## Prerequisites

Ensure you have:
- ✅ .NET 10.0 SDK installed
- ✅ Text editor or IDE (VS Code, Visual Studio, or Rider)
- ✅ Terminal/Command Prompt access

### Verify .NET Installation

```bash
dotnet --version
```

Expected output: `10.0.x` or higher

If not installed, download from: https://dotnet.microsoft.com/download

## Step 1: Navigate to Project Directory

```bash
cd /path/to/ITEC323/04.ConsistentWebDesign/01.BasicLayout
```

Or from the repository root:

```bash
cd 04.ConsistentWebDesign/01.BasicLayout
```

## Step 2: Restore Dependencies (Optional)

The project has no external dependencies, but you can run this to ensure everything is set up:

```bash
dotnet restore
```

## Step 3: Build the Project

```bash
dotnet build
```


## Step 4: Run the Application

```bash
dotnet run
```


## Step 5: Open in Browser

Open your web browser and navigate to:

- **HTTPS**: https://localhost:5001
- **HTTP**: http://localhost:5000

You should see the BasicLayout Demo home page.

### Browser Security Warning

If you see a security warning about the SSL certificate (HTTPS):
- Click "Advanced" or "Show Details"
- Click "Proceed to localhost (unsafe)" or similar
- This is normal for local development

## Step 6: Explore the Application

### Navigate Between Pages

Click the navigation links:
- **Home** - Main page with layout explanation
- **About** - Information about the project structure
- **Contact** - Demonstrates `@section Scripts` usage

### What to Observe

1. **Consistent Layout**: Header, navigation, and footer are identical on all pages
2. **Different Content**: Only the main content area changes per page
3. **URL Changes**: Notice `/`, `/About`, `/Contact` in the address bar
4. **No Page Reloads**: For full page loads (this is server-rendered, not SPA)

### View Page Source

Right-click anywhere on the page → **View Page Source**

Notice the complete HTML includes:
- `<header>` with navigation (from layout)
- `<main>` with page content (from individual page)
- `<footer>` (from layout)

All combined into one complete HTML document!

## Step 7: Open Browser DevTools

### On the Contact Page:

1. Navigate to the **Contact** page
2. Press **F12** (or Ctrl+Shift+I / Cmd+Option+I)
3. Click the **Console** tab

You should see:
```
🎉 Contact page loaded!
This message only appears on the Contact page because of @section Scripts
```

**Why?** The Contact page has a `@section Scripts` block that only runs on that page. This demonstrates how sections work!

## Step 8: Explore the Code

### Recommended Order:

1. **Pages/Index.cshtml** - Start here to see a simple page
2. **Pages/Shared/_Layout.cshtml** - See the master template
3. **Pages/_ViewStart.cshtml** - One line that sets the default layout
4. **Pages/_ViewImports.cshtml** - Tag helper imports
5. **Pages/About.cshtml** - More content examples
6. **Pages/Contact.cshtml** - See `@section Scripts` in action

### Using VS Code:

```bash
code .
```

This opens the current directory in VS Code.

### Using Visual Studio:

Open `BasicLayout.csproj` in Visual Studio.

## Step 9: Make a Change

Let's modify the layout to see it change on all pages.

### Edit the Layout Header

1. Open `Pages/Shared/_Layout.cshtml`
2. Find the `<header>` section (around line 25)
3. Change the `<h1>` text:

```html
<!-- Before -->
<h1>BasicLayout Demo</h1>

<!-- After -->
<h1>MY AWESOME WEBSITE</h1>
```

4. **Save the file**
5. **Refresh your browser** (Ctrl+R or Cmd+R)

**Result**: The header changes on **all three pages** (Home, About, Contact) because they all use the same layout!

This is the power of layouts: **change once, update everywhere**.

## Step 10: Stop the Application

In the terminal where `dotnet run` is running:

Press **Ctrl+C** to stop the server.

Expected output:
```
info: Microsoft.Hosting.Lifetime[0]
      Application is shutting down...
```

## Troubleshooting

### Port Already in Use

**Error**: `Address already in use`

**Solution**: 
```bash
# Kill process on port 5000 (macOS/Linux)
lsof -ti:5000 | xargs kill -9

# Or run on a different port
dotnet run --urls "https://localhost:5051;http://localhost:5050"
```

### Build Errors

**Error**: `The framework 'Microsoft.NETCore.App', version '10.0.0' was not found`

**Solution**: Install .NET 10.0 SDK from https://dotnet.microsoft.com/download

### 404 Not Found

**Issue**: Pages return 404 errors

**Check**:
1. URL is correct: `/` for Home, `/About` for About, `/Contact` for Contact
2. Files are in `Pages/` directory with `.cshtml` extension
3. Each `.cshtml` file has `@page` at the top

### Layout Not Applied

**Issue**: Pages appear without header/navigation/footer

**Check**:
1. `Pages/_ViewStart.cshtml` exists and contains `Layout = "_Layout";`
2. `Pages/Shared/_Layout.cshtml` exists
3. Pages don't have `@{ Layout = null; }` which would override the layout

### CSS Not Loading

**Issue**: Page looks unstyled

**Check**:
1. `wwwroot/css/site.css` exists
2. `_Layout.cshtml` has `<link rel="stylesheet" href="~/css/site.css" />`
3. `Program.cs` has `app.UseStaticFiles();`

## What's Next?

Now that the application is running:

1. **Read the code**: Start with [README.md](README.md) for detailed explanations
2. **Review key concepts**: Check out [docs/Key-Takeaways.md](docs/Key-Takeaways.md)
3. **Experiment**: Try the experiments listed in the README
4. **Next project**: Move to `02.BootstrapTheme` to learn CSS frameworks

## Running Tests (Optional)

This project doesn't include unit tests to keep it simple. For testing examples, see later projects in the ITEC323 course.

## Running in Production Mode

By default, the app runs in Development mode. To run in Production:

```bash
# Set environment variable (macOS/Linux)
export ASPNETCORE_ENVIRONMENT=Production
dotnet run

# Or (Windows PowerShell)
$env:ASPNETCORE_ENVIRONMENT="Production"
dotnet run
```

**Difference**: Production mode has different error handling (shows Error.cshtml instead of detailed errors).

## IDE-Specific Instructions

### Visual Studio 2022

1. Open `BasicLayout.csproj`
2. Press **F5** (or click ▶ button) to run with debugging
3. Browser opens automatically
4. Press **Shift+F5** to stop

### VS Code

1. Open folder: `File → Open Folder → 01.BasicLayout`
2. Terminal: `View → Terminal` (or Ctrl+`)
3. Run: `dotnet run`
4. Open browser manually to https://localhost:5001

### JetBrains Rider

1. Open `BasicLayout.csproj`
2. Click ▶ **Run** button in toolbar
3. Browser opens automatically
4. Click ⏹ **Stop** button to stop

---

**Need Help?**
- Check [README.md](README.md) for concept explanations
- See [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for quick reference
- Review [../docs/layouts-explained.md](../docs/layouts-explained.md) for technical details

**Ready to build?** You now understand how to run and explore ASP.NET Core Razor Pages with layouts!
