# Quickstart: 06. Comprehensive Navigation

This guide will help you build and run the comprehensive navigation project locally.

## Prerequisites
- [.NET 10.0 SDK](https://dotnet.microsoft.com/download/dotnet/10.0)
- A terminal/command prompt

## Building the Project
Navigate to this directory in your terminal and restore the dependencies:

```bash
cd 06.ComprehensiveNavigation
dotnet build
```

The application should compile with zero errors or warnings.

## Running the Application
Launch the project using the CLI:

```bash
dotnet run
```

Watch the terminal output for the local URL (typically `http://localhost:5000` or `https://localhost:5001`), and open it in your browser.

## What to Try
1. **Navigate the Main Menu:** Click through the top navigation, including the nested "Products" dropdown.
2. **Breadcrumbs:** Go to `Products -> Any Product Details`. Notice how the breadcrumbs intelligently trail your location (`Home / Products / Details`).
3. **Docs Sidebar:** Go to `Docs` in the navbar. Notice the layout changes slightly to include a persistent sidebar structure on the left.
4. **Programmatic Redirect:** Type `/Admin/Dashboard` manually into your browser address bar. Observe how you immediately get bounced to `/Account/Login` with an alert.
5. **View the Sitemap:** Navigate to `/sitemap.xml` to see dynamic XML structurally generated without a physical `.xml` file on disk.

## Troubleshooting
- **Port Conflicts:** If `dotnet run` fails stating the port is in use, modify `Properties/launchSettings.json` to assign a different applicationUrl port, or utilize `dotnet run --urls=http://localhost:5999`.
