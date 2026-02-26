# Quick Start Guide - Hello World Blazor

This guide will walk you through building and running your first Blazor application step by step.

## Prerequisites Check

Before you begin, verify you have the required tools installed:

```bash
# Check .NET version (should be 8.0 or later)
dotnet --version

# Expected output: 10.0.xxx or 9.0.xxx or 8.0.xxx
```

If you don't have .NET installed, visit [https://dotnet.microsoft.com/download](https://dotnet.microsoft.com/download)

## Step 1: Navigate to the Project Directory

Open your terminal or command prompt and navigate to this project's directory:

```bash
cd /path/to/01.HelloDotnet/01.HelloWorldBlazor
```

Or if you're starting from the repository root:

```bash
cd 01.HelloDotnet/01.HelloWorldBlazor
```

## Step 2: Restore Dependencies

Restore all NuGet packages required by the project:

```bash
dotnet restore
```

**Expected Output**:
```
Determining projects to restore...
Restored /path/to/HelloWorldBlazor.csproj (in X ms).
```

**What this does**: Downloads all required libraries and dependencies specified in the `.csproj` file.

## Step 3: Build the Project

Compile the application to check for any errors:

```bash
dotnet build
```

**Expected Output**:
```
Build succeeded.
    0 Warning(s)
    0 Error(s)
```

**What this does**: Compiles your C# code into an executable format and validates that everything is correct.

## Step 4: Run the Application

Start the web application:

```bash
dotnet run
```

**Expected Output**:
```
info: Microsoft.Hosting.Lifetime[14]
      Now listening on: http://localhost:5000
info: Microsoft.Hosting.Lifetime[14]
      Now listening on: https://localhost:5001
info: Microsoft.Hosting.Lifetime[0]
      Application started. Press Ctrl+C to shut down.
```

**What this does**: Starts the Kestrel web server and hosts your Blazor application locally.

## Step 5: Open in Browser

1. Open your web browser
2. Navigate to one of the URLs displayed in the terminal:
   - HTTP: `http://localhost:5000`
   - HTTPS: `https://localhost:5001` (recommended)

**What you should see**:
- A page with the title "Hello World" in the browser tab
- A heading that says "Hello, World!"
- A welcome message: "Welcome to my very first Blazor application."

## Step 6: Stop the Application

To stop the running application, press:
- **Windows/Linux**: `Ctrl + C`
- **macOS**: `Cmd + C` or `Ctrl + C`

## Alternative: Run with Hot Reload

For a better development experience with automatic browser refresh when you save changes:

```bash
dotnet watch
```

**What this does**: Runs the application and automatically rebuilds and refreshes the browser when you modify files.

### Try it:
1. Run `dotnet watch`
2. Open `Components/Pages/Home.razor` in your editor
3. Change "Hello, World!" to "Hello, Blazor!"
4. Save the file
5. Watch the browser automatically refresh with your changes!

## Creating This Project From Scratch

If you want to recreate this project yourself, here are the commands used:

### Step 1: Create New Blazor Project

```bash
dotnet new blazor -o HelloWorldBlazor
```

**Explanation**: 
- `dotnet new blazor` - Uses the Blazor Web App template
- `-o HelloWorldBlazor` - Creates a new folder named "HelloWorldBlazor"

### Step 2: Navigate to Project

```bash
cd HelloWorldBlazor
```

### Step 3: Customize the Home Page

Edit `Components/Pages/Home.razor` to:

```razor
@page "/"

<PageTitle>Hello World</PageTitle>

<h1>Hello, World!</h1>
<p>Welcome to my very first Blazor application.</p>
```

### Step 4: Run It!

```bash
dotnet run
```

## Understanding the Output

When you run the application, you'll see several log messages:

| Message | What It Means |
|---------|---------------|
| `Now listening on: http://localhost:5000` | The HTTP endpoint where your app is accessible |
| `Now listening on: https://localhost:5001` | The HTTPS endpoint (encrypted) |
| `Application started. Press Ctrl+C to shut down.` | App is running successfully |

## Common Issues and Solutions

### Issue 1: Port Already in Use

**Error**: `Address already in use`

**Solution**: Another application is using the default port. Either:
1. Stop the other application
2. Or specify a different port:
   ```bash
   dotnet run --urls "http://localhost:5005"
   ```

### Issue 2: .NET Version Mismatch

**Error**: `The current .NET SDK does not support targeting .NET 10.0`

**Solution**: Either:
1. Install .NET 10 SDK (recommended)
2. Or edit `HelloWorldBlazor.csproj` and change `<TargetFramework>net10.0</TargetFramework>` to `net8.0` or `net9.0`

### Issue 3: Browser Shows "Connection Refused"

**Symptoms**: Can't connect to `localhost`

**Solutions**:
1. Verify the application is still running in the terminal
2. Check you're using the correct URL from the terminal output
3. Try the HTTP version instead of HTTPS
4. Clear your browser cache

### Issue 4: Changes Not Appearing

**Symptoms**: Modified code but browser shows old version

**Solutions**:
1. Use `dotnet watch` instead of `dotnet run` for auto-reload
2. Hard refresh your browser (Ctrl+Shift+R or Cmd+Shift+R)
3. Stop and restart the application

## Exploring the Application

### Where is the "Hello World" code?

Open `Components/Pages/Home.razor`:

```razor
@page "/"          ← This tells Blazor: "This component is available at the root URL '/'"

<PageTitle>Hello World</PageTitle>  ← Sets the browser tab title

<h1>Hello, World!</h1>  ← Standard HTML heading
<p>Welcome to my very first Blazor application.</p>  ← Standard HTML paragraph
```

### How does routing work?

The `@page "/"` directive at the top of `Home.razor` tells Blazor:
- When someone visits the root URL (`/`), show this component
- This is called **convention-based routing**

### Where is the main entry point?

Open `Program.cs` to see how the application starts:

```csharp
var builder = WebApplication.CreateBuilder(args);  // ← Creates the web app builder
builder.Services.AddRazorComponents();             // ← Registers Blazor services

var app = builder.Build();                         // ← Builds the configured app

app.MapRazorComponents<App>();                     // ← Maps Blazor components to routes

app.Run();                                         // ← Starts the web server
```

## Next Steps

Now that you have a working Blazor application:

### 1. Add Interactivity

Create a simple counter by modifying `Home.razor`:

```razor
@page "/"

<PageTitle>Hello World</PageTitle>

<h1>Hello, World!</h1>
<p>Welcome to my very first Blazor application.</p>

<p>Current count: @currentCount</p>
<button @onclick="IncrementCount">Click me!</button>

@code {
    private int currentCount = 0;

    private void IncrementCount()
    {
        currentCount++;
    }
}
```

### 2. Create a New Page

1. Create `Components/Pages/About.razor`:
   ```razor
   @page "/about"
   
   <PageTitle>About</PageTitle>
   
   <h1>About This App</h1>
   <p>This is my first Blazor application!</p>
   ```

2. Visit `https://localhost:5001/about` to see your new page

### 3. Customize the Navigation

Edit `Components/Layout/NavMenu.razor` to add your new page to the menu.

### 4. Style Your App

Edit `wwwroot/css/app.css` to customize colors, fonts, and layout.

## Additional Commands

### Clean Build Artifacts

```bash
dotnet clean
```

### View Project Info

```bash
dotnet list package
```

### Run Tests (if you add them later)

```bash
dotnet test
```

## Development Tips

1. **Use `dotnet watch`** for the best development experience
2. **Keep browser DevTools open** (F12) to see any errors
3. **Check the terminal** for compile-time errors
4. **Use browser refresh** (F5) if hot reload doesn't work
5. **Read the error messages** - they're usually helpful!

## Getting Help

If you encounter issues:

1. Check this guide's troubleshooting section
2. Review [docs/BlazorBasics.md](docs/BlazorBasics.md) for concepts
3. Read the error message carefully
4. Search the error message online
5. Visit [https://learn.microsoft.com/en-us/aspnet/core/blazor/](https://learn.microsoft.com/en-us/aspnet/core/blazor/)

---

**Ready to learn more?** Check out the [FRD.md](FRD.md) to understand what this application does, or explore the [docs/](docs/) folder for deeper dives into Blazor concepts.
