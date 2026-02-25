# Using `dotnet new` to Create Projects

## Overview

`dotnet new` is the CLI command for creating new .NET projects from templates. It's the fastest way to scaffold a .NET application with the correct structure and files.

**Basic syntax:**
```bash
dotnet new <template> -n <ProjectName>
```

## Common Project Templates for ITEC323

| Template | Short Name | Description | Use Case in ITEC323 |
|----------|-----------|-------------|---------------------|
| **Console Application** | `console` | Basic command-line app | Week 1-2: Learning C# basics |
| **ASP.NET Core Web App** | `webapp` or `razor` | Razor Pages web application | Week 3-8: Building web apps |
| **ASP.NET Core MVC** | `mvc` | Model-View-Controller web app | Alternative to Razor Pages |
| **Blazor Web App** | `blazor` | Interactive web UI with C# | Advanced web development |
| **Class Library** | `classlib` | Reusable code library | Creating shared components |
| **xUnit Test Project** | `xunit` | Unit testing project | Writing tests for your code |
| **Web API** | `webapi` | RESTful API project | Backend services |
| **Solution File** | `sln` | Solution container | Organizing multiple projects |

## Practical Examples

### Create a Console App
```bash
dotnet new console -n MyFirstApp
cd MyFirstApp
dotnet run
```

### Create a Razor Pages Web App (Most Common for ITEC323)
```bash
dotnet new webapp -n MyWebApp
cd MyWebApp
dotnet run
# Opens at https://localhost:<port>
```

### Create a Project with Specific Framework
```bash
# Target .NET 8.0 (LTS)
dotnet new webapp -n MyApp -f net8.0

# Target .NET 10.0 (Current)
dotnet new webapp -n MyApp -f net10.0
```

### Create a Solution with Multiple Projects
```bash
# Create solution
dotnet new sln -n MySolution

# Create web app
dotnet new webapp -n MySolution.Web

# Create class library
dotnet new classlib -n MySolution.Core

# Create test project
dotnet new xunit -n MySolution.Tests

# Add projects to solution
dotnet sln add MySolution.Web/MySolution.Web.csproj
dotnet sln add MySolution.Core/MySolution.Core.csproj
dotnet sln add MySolution.Tests/MySolution.Tests.csproj
```

### Create Project with HTTPS Disabled (Learning)
```bash
dotnet new webapp -n MyApp --no-https
```

## Useful Commands

### List All Available Templates
```bash
dotnet new list
```

### Get Help for a Specific Template
```bash
dotnet new webapp --help
```

### Search for Templates
```bash
dotnet new search blazor
```

### Install Additional Templates
```bash
# Install template packages from NuGet
dotnet new install <package-name>
```

## Tips and Best Practices

### 1. Naming Conventions
- Use **PascalCase** for project names: `StudentManager`, `WebStore`, `TodoApp`
- Avoid spaces and special characters
- Be descriptive but concise

### 2. Target Framework
For ITEC323, always use:
```bash
dotnet new webapp -n MyApp -f net8.0
```
- **net8.0** = .NET 8.0 LTS (Long Term Support)
- Compatible with .NET 10.0
- Industry standard for 2026

### 3. Project Organization
```bash
# Create organized structure
mkdir ITEC323/Week4Assignment
cd ITEC323/Week4Assignment
dotnet new webapp -n StudentPortal
```

### 4. Quick Workflow
```bash
# Create, build, and run in one go
dotnet new webapp -n MyApp && cd MyApp && dotnet run
```

### 5. Template Options
Many templates have useful options:
```bash
# No HTTPS (simpler for learning)
dotnet new webapp -n MyApp --no-https

# Exclude sample pages
dotnet new webapp -n MyApp --exclude-launch-settings

# Use Program.cs only (minimal hosting)
dotnet new webapp -n MyApp --use-program-main
```

## Common Template Options

| Option | Description | Example |
|--------|-------------|---------|
| `-n, --name` | Project name | `-n MyApp` |
| `-o, --output` | Output directory | `-o ./src/MyApp` |
| `-f, --framework` | Target framework | `-f net8.0` |
| `--no-https` | Disable HTTPS | Simpler for local dev |
| `--dry-run` | Preview without creating | Test before creating |

## Troubleshooting

### Template Not Found?
```bash
# Update .NET SDK
dotnet --version

# Clear template cache
dotnet new --debug:reinit
```

### Check What Was Created
```bash
# After creating a project
tree MyApp  # macOS/Linux
dir MyApp   # Windows
```

## Quick Reference

**Most used commands for ITEC323:**
```bash
# Console app
dotnet new console -n MyApp

# Web app (Razor Pages)
dotnet new webapp -n MyWebApp -f net8.0

# Test project
dotnet new xunit -n MyApp.Tests

# Solution file
dotnet new sln -n MySolution
```

## Official Documentation

- **Create Projects from Templates**: https://learn.microsoft.com/en-us/dotnet/core/tutorials/cli-templates-create-project-template
- **dotnet new Command Reference**: https://learn.microsoft.com/en-us/dotnet/core/tools/dotnet-new
- **Available Templates**: https://learn.microsoft.com/en-us/dotnet/core/tools/dotnet-new-sdk-templates
- **Custom Templates**: https://learn.microsoft.com/en-us/dotnet/core/tools/custom-templates

## Next Steps

After creating a project:

1. **Explore the structure**: Look at the generated files
2. **Read the README**: If the template includes one
3. **Run the app**: `dotnet run`
4. **Start coding**: Modify `Program.cs` or add new pages

---

**Remember**: `dotnet new` is just the starting point. The template gives you the structure — you build the application!
