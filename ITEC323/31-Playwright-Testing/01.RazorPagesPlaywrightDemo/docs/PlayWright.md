# Installing Playwright on macOS

You can install Playwright browsers on your Mac without PowerShell. Choose one of the following methods:

## Option 1: .NET Global Tool (Recommended for .NET Projects)

Install the Playwright CLI as a global .NET tool:

```bash
dotnet tool install --global Microsoft.Playwright.CLI
```

Then install the browsers in your project directory:

```bash
playwright install
```

**Why use this?** This method integrates naturally with .NET development and works across all platforms.

## Option 2: Node.js/npm (If You Have Node.js Installed)

Use the Node Package Manager to install browsers:

```bash
npx playwright install
```

**Why use this?** If you already have Node.js for web development, this is quick and straightforward. The browsers are stored in a shared cache that .NET Playwright can access.

## Option 3: Programmatic Installation (No CLI Required)

Add this code to your test setup or `Program.cs`:

```csharp
/// <summary>
/// Downloads Playwright browsers programmatically on first run.
/// </summary>
var exitCode = Microsoft.Playwright.Program.Main(new[] { "install" });

if (exitCode != 0) 
{
    throw new Exception("Failed to install Playwright browsers.");
}
```

**Why use this?** Perfect for automated environments or when you want installation handled entirely in code.

## Browser Storage Location

Regardless of which method you use, Playwright stores browser binaries in:
- **macOS**: `~/Library/Caches/ms-playwright`

All installation methods use the same browser cache, so you only need to install once.

## Why No PowerShell?

The official .NET Playwright docs often reference a PowerShell script (`pwsh bin/Debug/net8.0/playwright.ps1 install`), which leads developers to think PowerShell is required. It's not — that `.ps1` script is just a wrapper. Any of the methods above will work perfectly on macOS using your standard terminal (Zsh or Bash).

## Next Steps

After installing, verify the setup by creating a simple test that launches a browser. See the test examples in this project for guidance.
