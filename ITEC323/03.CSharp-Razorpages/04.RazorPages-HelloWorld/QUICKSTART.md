# Quick Start Guide - Razor Pages Hello World

This guide shows you how to build and run your first ASP.NET Core Razor Pages application.

## Prerequisites

Before you begin:

- **.NET 10.0 SDK** or later
- **Web browser** (Chrome, Firefox, Edge, Safari)  
- **Terminal** or command prompt

### Verify .NET Installation

```bash
dotnet --version
```

Expected output: `10.0.103` or higher

## Build and Run

### 1. Navigate to the Project

```bash
cd 03.CSharp/04.RazorPages-HelloWorld
```

### 2. Build the Project

```bash
dotnet build
```

Expected output:
```
Build succeeded.
    0 Warning(s)
    0 Error(s)
```

### 3. Run the Application

```bash
dotnet run
```

Expected output:
```
info: Microsoft.Hosting.Lifetime[14]
      Now listening on: https://localhost:5001
info: Microsoft.Hosting.Lifetime[0]
      Application started. Press Ctrl+C to shut down.
```

### 4. Open in Browser

Navigate to:
```
https://localhost:5001
```

**Note**: You may see a certificate warning on first run. This is normal for development - click "Advanced" and proceed.

### 5. Stop the Server

Press **Ctrl+C** in the terminal.

## How It Works

When you run the application:

1. **Kestrel web server starts** and listens on port 5001
2. **Browser sends request** to `https://localhost:5001`
3. **ASP.NET Core routing** maps the request to `Index.cshtml`
4. **OnGet() method** in `Index.cshtml.cs` runs and sets the Message property
5. **Razor engine** combines the C# data with HTML markup
6. **Server returns HTML** to the browser
7. **Browser displays** the page

## Project Structure

```
04.RazorPages-HelloWorld/
├── Program.cs                  # Application entry point
│                               # - Configures services
│                               # - Sets up middleware pipeline
│                               # - Starts web server
│
├── RazorPagesHelloWorld.csproj # Project file
│                               # - Specifies .NET version
│                               # - References ASP.NET Core
│
├── Pages/
│   ├── Index.cshtml            # View (HTML + Razor syntax)
│   │                           # - @page directive makes it routable
│   │                           # - @model links to code-behind
│   │                           # - Displays @Model.Message
│   │
│   ├── Index.cshtml.cs         # PageModel (C# code-behind)
│   │                           # - Contains OnGet() method
│   │                           # - Provides Message property
│   │                           # - Separates logic from presentation
│   │
│   ├── Error.cshtml            # Error page view
│   ├── Error.cshtml.cs         # Error page model
│   ├── _ViewImports.cshtml     # Common using directives
│   ├── _ViewStart.cshtml       # Layout configuration
│   └── Shared/
│       └── _Layout.cshtml      # Shared layout (header/footer)
│
└── wwwroot/                    # Static files
    └── css/
        └── site.css            # Stylesheet
```

## Making Changes

### Edit the Message

1. **Stop the server** (Ctrl+C)
2. **Open** `Pages/Index.cshtml.cs`
3. **Find** the line: `Message = $"Hello! Current time is {DateTime.Now:yyyy-MM-dd HH:mm:ss}";`
4. **Change** it to: `Message = "Hello from my first web app!";`
5. **Save** the file
6. **Run** again: `dotnet run`
7. **Refresh** your browser

### Change the Date Format

Try different DateTime formats:
- `DateTime.Now:hh:mm:ss tt` → 12-hour time with AM/PM
- `DateTime.Now:dddd, MMMM d, yyyy` → Full date like "Saturday, March 8, 2026"
- `DateTime.Now:yyyy-MM-dd` → ISO date like "2026-03-08"

## Common Issues

### Port Already in Use

**Error**: `Address already in use`

**Solution**: Stop the previous instance (Ctrl+C) or change the port in `Program.cs`

### Certificate Warning

**Error**: Browser shows "Your connection is not private"

**Solution**: This is normal for development. Click "Advanced" and proceed. To trust the certificate permanently:
```bash
dotnet dev-certs https --trust
```

### Build Errors

**Error**: Build fails

**Solution**: 
1. Make sure you're in the correct directory
2. Check .NET version: `dotnet --version`
3. Try: `dotnet clean` then `dotnet build`

## Next Steps

1. ✅ Run the application successfully
2. ✅ View it in your browser
3. ✅ Modify the message and see your changes
4. 📖 Read [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for deeper explanations
5. 🧪 Try adding a new property and displaying it

---

**You've successfully run your first web application!** 🎉
