# FRD-30 Portfolio – Quick start

## Prerequisites

- [.NET 10.0 SDK](https://dotnet.microsoft.com/download) (or compatible SDK)
- **macOS**: Use Terminal (or iTerm2). Commands below are the same.

Check:

```bash
dotnet --version
```

## Build and run

1. Open a terminal and go to the project folder:

   ```bash
   cd 02.HTML-CSS/FRD-30-portfolio-website
   ```

2. Restore and build:

   ```bash
   dotnet build
   ```

3. Run the site:

   ```bash
   dotnet run
   ```

4. In a browser, open the URL shown in the console (e.g. `https://localhost:5001` or `http://localhost:5000`).

5. Use the nav: **Home**, **About**, **Projects**, **Contact**.

## Stop

Press `Ctrl+C` (or `Cmd+C` on macOS) in the terminal where `dotnet run` is running.

## Notes

- The project targets **.NET 10.0**. Install the [.NET 10 SDK](https://dotnet.microsoft.com/download) for macOS.
- Static files (CSS, JS) are served from `wwwroot/`. Changes to `wwwroot/css/site.css` or `wwwroot/js/site.js` are picked up after a refresh.
