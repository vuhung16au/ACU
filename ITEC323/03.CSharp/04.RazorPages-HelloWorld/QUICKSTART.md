# Quick Start Guide - Razor Pages Hello World

This guide provides step-by-step instructions to build and run your first ASP.NET Core Razor Pages application.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Verify Your Setup](#verify-your-setup)
3. [Navigate to the Project](#navigate-to-the-project)
4. [Build the Project](#build-the-project)
5. [Run the Application](#run-the-application)
6. [Access in Browser](#access-in-browser)
7. [Stop the Server](#stop-the-server)
8. [Exploring the Code](#exploring-the-code)
9. [Making Changes](#making-changes)
10. [Troubleshooting](#troubleshooting)

---

## Prerequisites

Before you begin, ensure you have:

- ✅ **.NET 10.0 SDK** or later
- ✅ **Terminal** or command prompt
- ✅ **Web browser** (Chrome, Firefox, Edge, Safari)
- ✅ **Text editor** (VS Code recommended)

---

## Verify Your Setup

### Step 1: Check .NET Version

Open your terminal and run:

```bash
dotnet --version
```

**Expected output:**
```
10.0.103
```

Or any version starting with `10.0.x`.

**If this fails:**
- Install .NET from [dotnet.microsoft.com/download](https://dotnet.microsoft.com/download)
- Restart your terminal after installation
- Run the command again

### Step 2: Verify Web SDK

Check that the Web SDK is available:

```bash
dotnet --list-sdks
```

**You should see:**
```
10.0.103 [C:\Program Files\dotnet\sdk]  (Windows)
10.0.103 [/usr/local/share/dotnet/sdk] (macOS/Linux)
```

---

## Navigate to the Project

### macOS / Linux

```bash
cd ~/path/to/ITEC323/03.CSharp/04.RazorPages-HelloWorld
```

### Windows

```cmd
cd C:\path\to\ITEC323\03.CSharp\04.RazorPages-HelloWorld
```

### Verify Location

Confirm you're in the right directory:

```bash
ls    # macOS/Linux
dir   # Windows
```

**You should see:**
- `RazorPagesHelloWorld.csproj`
- `Program.cs`
- `Pages/` folder
- `wwwroot/` folder

---

## Build the Project

### Step 1: Restore Dependencies

Restore NuGet packages (ASP.NET Core dependencies):

```bash
dotnet restore
```

**Expected output:**
```
Restore completed in 234 ms for RazorPagesHelloWorld.csproj.
```

### Step 2: Build the Application

Compile the C# code and Razor Pages:

```bash
dotnet build
```

**Expected output:**
```
Build succeeded.
    0 Warning(s)
    0 Error(s)

Time Elapsed 00:00:01.23
```

**Success indicators:**
- ✅ "Build succeeded" message
- ✅ No errors (0 Error(s))
- ✅ `bin/Debug/net10.0/` folder created
- ✅ `.dll` files generated

### If Build Fails

**Common issues:**

1. **Wrong directory**: Make sure you're in `04.RazorPages-HelloWorld/`
2. **Missing SDK**: Verify .NET 10.0 is installed
3. **Syntax errors**: Check for typos in code files

**Fix and rebuild:**
```bash
dotnet clean   # Remove build artifacts
dotnet build   # Build again
```

---

## Run the Application

### Step 1: Start the Web Server

```bash
dotnet run
```

**Expected output:**
```
Building...
info: Microsoft.Hosting.Lifetime[14]
      Now listening on: https://localhost:5001
info: Microsoft.Hosting.Lifetime[14]
      Now listening on: http://localhost:5000
info: Microsoft.Hosting.Lifetime[0]
      Application started. Press Ctrl+C to shut down.
info: Microsoft.Hosting.Lifetime[0]
      Hosting environment: Development
info: Microsoft.Hosting.Lifetime[0]
      Content root path: /path/to/04.RazorPages-HelloWorld
```

**Important URLs:**
- **HTTPS**: `https://localhost:5001` ← Use this (secure)
- **HTTP**: `http://localhost:5000` (insecure, redirects to HTTPS)

**What's happening:**
1. ✅ Kestrel web server starts
2. ✅ Port 5001 (HTTPS) and 5000 (HTTP) bind
3. ✅ Application loads and waits for requests
4. ✅ Server runs continuously (doesn't exit like console apps)

### Step 2: Confirm Server is Running

Look for these messages:
- ✅ "Now listening on: https://localhost:5001"
- ✅ "Application started. Press Ctrl+C to shut down."

**If you don't see these:**
- Check for error messages (port already in use, certificate issues)
- See [Troubleshooting](#troubleshooting) section

---

## Access in Browser

### Step 1: Open Your Browser

Launch your web browser (Chrome, Firefox, Edge, or Safari).

### Step 2: Navigate to the Application

Type in the address bar:

```
https://localhost:5001
```

Press **Enter**.

### Step 3: Certificate Warning (First Time Only)

You may see a security warning:
- **"Your connection is not private"**
- **"NET::ERR_CERT_AUTHORITY_INVALID"**

**This is normal** for development. The certificate is self-signed.

**To proceed:**

**Chrome:**
- Click "Advanced"
- Click "Proceed to localhost (unsafe)"

**Firefox:**
- Click "Advanced"
- Click "Accept the Risk and Continue"

**Safari:**
- Click "Show Details"
- Click "visit this website"

**To fix permanently:**
```bash
dotnet dev-certs https --trust
```

### Step 4: View the Application

You should see:

- 🎉 **Welcome message**: "Welcome to ASP.NET Core Razor Pages!"
- 💻 **System information**: Framework, language, .NET version, current time
- 🔑 **Key concepts**: List of topics covered
- 🚀 **How it works**: Request/response flow diagram
- 📁 **Project structure**: File organization
- 🎯 **Next steps**: Suggested exercises

**Congratulations!** 🎉 Your web application is running!

---

## Stop the Server

### Method 1: Keyboard Shortcut (Recommended)

In the terminal where `dotnet run` is running:

Press **Ctrl + C**

**Expected output:**
```
info: Microsoft.Hosting.Lifetime[0]
      Application is shutting down...
```

### Method 2: Close Terminal

Simply close the terminal window (server stops automatically).

### Verify Server Stopped

Try accessing `https://localhost:5001` again.

**Expected:** Browser shows "Can't connect" or "Connection refused"

---

## Exploring the Code

Now that it's running, let's understand the code.

### File 1: Program.cs (Entry Point)

**Location:** `04.RazorPages-HelloWorld/Program.cs`

**What it does:**
- Configures the web application
- Sets up services (Razor Pages)
- Configures middleware pipeline
- Starts the web server

**Open in editor:**
```bash
code Program.cs  # VS Code
```

**Key sections:**
1. **Service Configuration** (line ~20): `builder.Services.AddRazorPages();`
2. **Middleware Pipeline** (line ~35): Exception handling, HTTPS, static files, routing
3. **Run Application** (line ~71): `app.Run();`

**Read the comments** - they explain each part in detail.

### File 2: Index.cshtml (The View)

**Location:** `Pages/Index.cshtml`

**What it does:**
- Defines the HTML structure
- Uses Razor syntax to mix C# with HTML
- Displays data from the page model

**Key Razor syntax:**
- `@page` - Makes this a Razor Page
- `@model IndexModel` - Links to the code-behind class
- `@Model.PropertyName` - Accesses C# properties
- `@foreach` - C# loop in HTML

**Try modifying:**
- Change the heading text
- Add your name to the page
- Save and refresh the browser

### File 3: Index.cshtml.cs (The Page Model)

**Location:** `Pages/Index.cshtml.cs`

**What it does:**
- Contains C# logic for the page
- Provides data via properties
- Handles HTTP GET requests in `OnGet()` method

**Key concepts:**
- `IndexModel` class inherits from `PageModel`
- `OnGet()` runs when the page loads
- Properties (`WelcomeMessage`, `CurrentDateTime`) are accessible in the view

**Try modifying:**
- Change the `WelcomeMessage` value
- Add a new property: `public string YourName { get; set; } = "Your Name";`
- Set it in `OnGet()`: `YourName = "Alice";`
- Display it in `Index.cshtml`: `<p>Created by @Model.YourName</p>`

### File 4: _Layout.cshtml (Shared Layout)

**Location:** `Pages/Shared/_Layout.cshtml`

**What it does:**
- Defines the common structure (header, footer)
- Wraps around all pages
- `@RenderBody()` is where page content goes

**Try modifying:**
- Change the header title
- Update the footer text
- Add a link to your favorite website

### File 5: site.css (Stylesheet)

**Location:** `wwwroot/css/site.css`

**What it does:**
- Controls colors, fonts, spacing
- Creates responsive design
- Styles the cards and components

**Try modifying:**
- Change background colors
- Adjust font sizes
- Modify the gradient colors

---

## Making Changes

### Workflow for Editing Code

1. **Stop the server** (Ctrl+C)
2. **Edit files** in your text editor
3. **Save changes**
4. **Rebuild** (optional): `dotnet build`
5. **Run again**: `dotnet run`
6. **Refresh browser** to see changes

### Hot Reload (Advanced)

With .NET 10, some changes apply without restart:

```bash
dotnet watch run
```

Now when you save changes, the application updates automatically!

**What updates without restart:**
- ✅ Changes to `.cshtml` files
- ✅ Changes to `.cs` code-behind files
- ✅ CSS changes

**What requires restart:**
- ❌ Changes to `Program.cs`
- ❌ Changes to `.csproj` file
- ❌ Adding new NuGet packages

### Testing Your Changes

**Example: Change the Welcome Message**

1. Open `Pages/Index.cshtml.cs`
2. Find: `WelcomeMessage = "Welcome to ASP.NET Core Razor Pages!";`
3. Change to: `WelcomeMessage = "Hello from [Your Name]!";`
4. Save the file
5. If using `dotnet watch`: See changes immediately
6. If using `dotnet run`: Restart with Ctrl+C, then `dotnet run`
7. Refresh browser: See your new message

---

## Troubleshooting

### Issue 1: Build Errors

**Symptom:**
```
error CS0103: The name 'Model' does not exist in the current context
```

**Causes:**
- Missing `@model` directive in `.cshtml` file
- Typo in namespace or class name

**Fix:**
1. Check `Index.cshtml` has: `@model RazorPagesHelloWorld.Pages.IndexModel`
2. Check namespace in `Index.cshtml.cs` matches
3. Rebuild: `dotnet clean && dotnet build`

---

### Issue 2: Port Already in Use

**Symptom:**
```
Unable to bind to https://localhost:5001 on the IPv4 loopback interface: 
'Address already in use'.
```

**Cause:** Another application is using port 5001

**Fix (macOS/Linux):**
```bash
# Find the process
lsof -ti:5001

# Kill it
lsof -ti:5001 | xargs kill -9
```

**Fix (Windows):**
```cmd
# Find the process
netstat -ano | findstr :5001

# Note the PID (last column), then:
taskkill /PID [PID] /F
```

**Alternative:** Change the port in `Program.cs` or use:
```bash
dotnet run --urls "https://localhost:6001"
```

---

### Issue 3: Certificate Trust Error

**Symptom:**
Browser shows "Your connection is not private" every time.

**Fix:**
```bash
# Trust the development certificate
dotnet dev-certs https --trust
```

**macOS:** Will prompt for password
**Windows:** Will show security dialog
**Linux:** Manual certificate installation required

After trusting, restart browser and try again.

---

### Issue 4: Page Not Found (404)

**Symptom:**
Browser shows "404 - Page Not Found"

**Causes:**
1. Wrong URL (not `localhost:5001`)
2. Server not running
3. Missing `@page` directive

**Fix:**
1. Verify URL is `https://localhost:5001` (not 5000, not 5002)
2. Check terminal shows "Now listening on: https://localhost:5001"
3. Verify `Index.cshtml` starts with `@page`
4. Restart server

---

### Issue 5: White Screen / No Content

**Symptom:**
Page loads but shows blank white screen.

**Causes:**
1. CSS not loading
2. Error in page model
3. Browser caching old version

**Fix:**
1. Check browser console (F12) for errors
2. Hard refresh: **Ctrl+Shift+R** (Windows/Linux) or **Cmd+Shift+R** (macOS)
3. Check `wwwroot/css/site.css` exists
4. Verify `_Layout.cshtml` includes: `<link rel="stylesheet" href="~/css/site.css" />`

---

### Issue 6: Code Changes Not Showing

**Symptom:**
You changed code, but browser shows old content.

**Fix:**
1. **Stop server**: Ctrl+C
2. **Clean build**: `dotnet clean`
3. **Rebuild**: `dotnet build`
4. **Run**: `dotnet run`
5. **Hard refresh browser**: Ctrl+Shift+R (Cmd+Shift+R on macOS)

---

### Issue 7: .NET Not Found

**Symptom:**
```
'dotnet' is not recognized as an internal or external command
```

**Fix:**
1. Install .NET 10.0 SDK from [dotnet.microsoft.com](https://dotnet.microsoft.com/download)
2. **Restart terminal** after installation
3. Verify: `dotnet --version`

---

## Common Commands Reference

| Command | Purpose |
|---------|---------|
| `dotnet --version` | Check .NET version |
| `dotnet restore` | Restore NuGet packages |
| `dotnet clean` | Remove build artifacts |
| `dotnet build` | Compile the project |
| `dotnet run` | Run the application |
| `dotnet watch run` | Run with hot reload |
| `dotnet dev-certs https --trust` | Trust development certificate |

---

## Next Steps

Now that you have the application running:

1. ✅ **Read README.md** - Understand the concepts
2. ✅ **Read docs/Key-Takeaways.md** - Deep dive into web development
3. ✅ **Try the exercises** - Modify the code
4. ✅ **Experiment** - Break things and fix them (best way to learn!)
5. ✅ **Build your own** - Create a new Razor Pages project from scratch

---

## Getting Help

If you're stuck:

1. **Read error messages** carefully - they often tell you exactly what's wrong
2. **Check the documentation** in `README.md` and `docs/Key-Takeaways.md`
3. **Search online** for the specific error message
4. **Ask your instructor** - that's what they're there for!

---

## Success Checklist

You've successfully completed the Quick Start when you can:

- ✅ Build the project without errors (`dotnet build`)
- ✅ Run the application (`dotnet run`)
- ✅ Access the page in browser (`https://localhost:5001`)
- ✅ See the "Welcome to ASP.NET Core Razor Pages!" heading
- ✅ Stop the server (Ctrl+C)
- ✅ Make a small change and see it reflected

**Congratulations!** 🎉 You've successfully set up and run your first ASP.NET Core Razor Pages application!

---

**Ready to learn more?** Continue to [README.md](README.md) for concept explanations and [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for in-depth discussions.
