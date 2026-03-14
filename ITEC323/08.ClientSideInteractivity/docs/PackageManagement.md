# Package Management Guide

## Three Package Managers for .NET Web Development

| Tool | For | Files | Command Line |
|------|-----|-------|--------------|
| **NuGet** | .NET libraries | `.csproj` | `dotnet add package` |
| **NPM** | JavaScript libraries | `package.json` | `npm install` |
| **LibMan** | CDN libraries | `libman.json` | `libman install` |

## NuGet - .NET Libraries

### What It Is

Microsoft's package manager for .NET libraries.

**Use for:** C# libraries, .NET tools, server-side packages

### Adding Packages

```bash
# Command line
dotnet add package Newtonsoft.Json
dotnet add package Microsoft.EntityFrameworkCore

# Specific version
dotnet add package Serilog --version 3.1.1
```

### Package File

**ProjectName.csproj:**
```xml
<Project Sdk="Microsoft.NET.Sdk.Web">
  <PropertyGroup>
    <TargetFramework>net10.0</TargetFramework>
  </PropertyGroup>

  <ItemGroup>
    <PackageReference Include="Newtonsoft.Json" Version="13.0.3" />
    <PackageReference Include="Serilog" Version="3.1.1" />
  </ItemGroup>
</Project>
```

### Common Packages

```bash
# JSON parsing
dotnet add package Newtonsoft.Json

# Entity Framework Core
dotnet add package Microsoft.EntityFrameworkCore
dotnet add package Microsoft.EntityFrameworkCore.SqlServer

# Authentication
dotnet add package Microsoft.AspNetCore.Authentication.JwtBearer

# Testing
dotnet add package xunit
dotnet add package FluentAssertions
```

### NuGet Commands

```bash
# Restore packages (download)
dotnet restore

# List installed packages
dotnet list package

# Remove package
dotnet remove package Newtonsoft.Json

# Update packages
dotnet add package PackageName --version 2.0.0
```

## NPM - JavaScript Libraries

### What It Is

Node Package Manager - JavaScript ecosystem package manager.

**Use for:** React, Vue, build tools, JavaScript libraries

### Installation

```bash
# Check if installed
npm --version

# Install Node.js (includes NPM)
# Download from: https://nodejs.org/
```

### Creating package.json

```bash
# Interactive setup
npm init

# Quick setup (accepts defaults)
npm init -y
```

### Installing Packages

```bash
# Install and save to package.json
npm install react
npm install vue

# Install dev dependencies (build tools)
npm install --save-dev vite
npm install --save-dev @vitejs/plugin-react

# Install globally (tools)
npm install -g create-react-app
```

### Package File

**package.json:**
```json
{
  "name": "my-app",
  "version": "1.0.0",
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "react": "^18.2.0",
    "react-dom": "^18.2.0"
  },
  "devDependencies": {
    "vite": "^5.0.0",
    "@vitejs/plugin-react": "^4.2.0"
  }
}
```

### NPM Scripts

```bash
# Run scripts defined in package.json
npm run dev      # Start development server
npm run build    # Build for production
npm test         # Run tests
```

### NPM Commands

```bash
# Install all dependencies from package.json
npm install

# Update packages
npm update

# Remove package
npm uninstall react

# List installed packages
npm list

# Search for packages
npm search htmx
```

### Common JavaScript Packages

```bash
# Frameworks
npm install react react-dom
npm install vue

# Build tools
npm install vite

# Utilities
npm install axios          # HTTP requests
npm install lodash         # Utility functions
npm install dayjs          # Date handling

# UI libraries
npm install bootstrap
npm install tailwindcss
```

### Version Numbers Explained

```json
{
  "dependencies": {
    "react": "18.2.0",      // Exact version
    "vue": "^3.3.4",        // Compatible (minor updates OK)
    "lodash": "~4.17.21"    // Patch updates only
  }
}
```

**Symbols:**
- `18.2.0` - Exact version only
- `^18.2.0` - Any 18.x.x (compatible updates)
- `~18.2.0` - Only 18.2.x (patch updates)

## LibMan - CDN Libraries

### What It Is

Library Manager for client-side libraries via CDN.

**Use for:** Quick addition of JS/CSS from CDN (Bootstrap, jQuery, Alpine.js)

### Installation

```bash
# Global install
dotnet tool install -g Microsoft.Web.LibraryManager.Cli

# Check version
libman --version
```

### Initialize LibMan

```bash
# Creates libman.json
libman init
```

### Installing Libraries

```bash
# From cdnjs
libman install bootstrap@5.3.0 -p cdnjs -d wwwroot/lib/bootstrap

# From jsdelivr
libman install alpinejs -p jsdelivr -d wwwroot/lib/alpine

# From unpkg
libman install htmx.org -p unpkg -d wwwroot/lib/htmx
```

### Configuration File

**libman.json:**
```json
{
  "version": "1.0",
  "defaultProvider": "cdnjs",
  "libraries": [
    {
      "library": "bootstrap@5.3.0",
      "destination": "wwwroot/lib/bootstrap",
      "files": [
        "css/bootstrap.min.css",
        "js/bootstrap.bundle.min.js"
      ]
    },
    {
      "library": "alpinejs@3.13.0",
      "destination": "wwwroot/lib/alpine",
      "provider": "jsdelivr"
    }
  ]
}
```

### Using in Razor Pages

```html
<!-- _Layout.cshtml -->
<link rel="stylesheet" href="~/lib/bootstrap/css/bootstrap.min.css" />
<script src="~/lib/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="~/lib/alpine/alpine.min.js" defer></script>
```

### LibMan Commands

```bash
# Restore all libraries
libman restore

# Update library
libman update bootstrap

# Uninstall library
libman uninstall bootstrap

# List installed libraries
libman list

# Clean (delete all library files)
libman clean
```

### Pros and Cons

**Pros:**
- ✅ No build process needed
- ✅ Fast to add libraries
- ✅ Files downloaded to project
- ✅ Works offline after restore

**Cons:**
- ❌ Limited to CDN-available libraries
- ❌ No dependency management
- ❌ Manual updates

## Which to Use?

### Decision Tree

```
Backend .NET library?
    ↓ YES → Use NuGet
    ↓ NO
    ↓
Need build process? (React/Vue/TypeScript)
    ↓ YES → Use NPM
    ↓ NO
    ↓
Simple CDN script? (Bootstrap, Alpine, HTMX)
    ↓ YES → Use LibMan OR CDN link
```

### Common Scenarios

**Scenario 1: ASP.NET Razor Pages with Bootstrap**
- NuGet for .NET packages
- LibMan for Bootstrap

**Scenario 2: React SPA with .NET API**
- NuGet for .NET backend
- NPM for React frontend

**Scenario 3: Blazor Application**
- NuGet only (no JavaScript build needed)

**Scenario 4: HTMX + Alpine.js**
- NuGet for .NET packages
- LibMan OR CDN links for HTMX/Alpine

## Security Considerations

### NuGet

```bash
# Check for vulnerabilities
dotnet list package --vulnerable
```

### NPM

```bash
# Audit packages for security issues
npm audit

# Fix automatically (when possible)
npm audit fix
```

### Best Practices

- ✅ Keep packages updated
- ✅ Review package popularity/maintenance
- ✅ Check for known vulnerabilities
- ✅ Use exact versions in production
- ✅ Don't commit `node_modules/` to Git

## .gitignore Settings

```gitignore
# .NET
bin/
obj/
*.user
*.suo

# NPM
node_modules/
package-lock.json  # Or commit for production stability
npm-debug.log*

# LibMan
wwwroot/lib/       # Or commit if you want offline support
```

## CDN Alternative (No Package Manager)

**For simple projects:**

```html
<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" 
      rel="stylesheet">

<!-- HTMX -->
<script src="https://unpkg.com/htmx.org@1.9.10"></script>

<!-- Alpine.js -->
<script defer src="https://cdn.jsdelivr.net/npm/alpinejs@3.x.x/dist/cdn.min.js"></script>
```

**Pros:**
- ✅ Fastest to add
- ✅ No package manager needed
- ✅ Cached across sites

**Cons:**
- ❌ Requires internet
- ❌ CDN could go down
- ❌ Version changes unexpectedly (if not pinned)

## Summary

| Task | Tool | Command |
|------|------|---------|
| Add .NET library | **NuGet** | `dotnet add package Name` |
| Build React/Vue app | **NPM** | `npm install` |
| Quick Bootstrap/HTMX | **LibMan** | `libman install bootstrap` |
| No build needed | **CDN** | `<script src="https://...">` |

**For ITEC323 projects:**
- Use **NuGet** for all .NET packages
- Use **NPM** for React/Vue projects (07-08)
- Use **LibMan** OR **CDN** for Bootstrap, Alpine, HTMX (01-06)

---

**Next:** Choose your package manager based on your project type!
