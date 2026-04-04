# ITEC323 - Web and Mobile Application Development

This repository contains supplementary code and examples for **ITEC323**

## 🚧 Work in Progress

This repository is actively being developed and updated with new examples and projects throughout the semester.
Folders with names `20+`, such as `20-Kotlin` and `21-Blazor`, are work in progress and may change as new materials are prepared.

## 📋 About

This repository provides practical examples and demonstrations covering:
- **.NET Console Applications** - Command-line interface development
- **ASP.NET Core** - Web application development
- **MAUI.NET** - Cross-platform mobile and desktop applications
- **Android Development** - Native mobile development

## 🏗️ Project Structure

```
ITEC323/
├── README.md                    # This file
├── LICENSE.md                   # MIT License
├── ITEC323.sln                  # Visual Studio solution file
├── 00.DotnetOverview/           # .NET fundamentals and supporting notes
├── 00.SetupDevelopmentEnvironments/
│                                  # Setup instructions for student machines
├── 01.HelloDotnet/              # Week 1
├── 02.HTML-CSS/                 # Week 2
├── 03.CSharp-Razorpages/        # Week 3
├── 04.ConsistentWebDesign/      # Week 4
├── 05.NavigationRouting/        # Week 5
├── 06.ReusableComponentsValidation/
│                                  # Week 6
├── 07.AjaxDynamicContent/       # Week 7
├── 08.ClientSideInteractivity/  # Week 8
├── 09.DataPersistenceEFCore/    # Week 9
├── 10.AndroidKotlinJetpackCompose/
│                                  # Week 10 Android apps
├── 11.AndroidGreetingApps/      # Week 11 Android hands-on practice
├── 12.MauiCrossPlatform/        # Week 12
├── 20-Kotlin/                   # Work in progress
├── 21-Blazor/                   # Work in progress
├── 22-MinimalAPIs-REST/         # Supplementary Minimal API examples
├── 22-Unit-Testing/             # Supplementary unit testing examples
├── 23-CoreSignalR/              # Supplementary SignalR examples
├── 30-Responsive-Design/        # Supplementary responsive design examples
├── 31-Playwright-Testing/       # Supplementary browser automation examples
├── 32-AI-Integration/           # Supplementary AI integration examples
├── 33-SPA-Integration/          # Supplementary Angular + ASP.NET Core example
└── .vscode/                     # VS Code configuration
```

The main teaching materials are organised into 12 folders for the 12 teaching weeks: `01.` through `12.`.

## 🚀 How to Build and Run

### Prerequisites

- [.NET SDK](https://dotnet.microsoft.com/download) (version 10.0 or later)
- A code editor (Visual Studio, Visual Studio Code, or JetBrains Rider)

### Building the Solution

To build all .NET projects in the solution for weeks 1-9 and 12:

```bash
# Navigate to the repository root
cd ITEC323

# Build all projects
dotnet build ITEC323.sln -v minimal
```

Weeks 10 and 11 contain Android projects, so they are not included in the root `.sln` build.

### Running Individual Projects

**Console Application (DotNetHelloWorldCLI):**
```bash
dotnet run --project DotNetHelloWorldCLI/src/DotNetHelloWorldCLI
```

**Web Application (AspNetHelloWorld):**
```bash
dotnet run --project AspNetHelloWorld
```

Then open your browser to: `http://localhost:5000` or `https://localhost:5001`

### Other Useful Commands

```bash
# List all projects in solution
dotnet sln list

# Clean build outputs
dotnet clean

# Restore NuGet packages
dotnet restore
```

## 📚 Topics Covered

This repository includes examples and code for the following topics covered in ITEC323:

### Weekly Structure
- Week 1: Hello .NET
- Week 2: HTML and CSS
- Week 3: C# and Razor Pages
- Week 4: Consistent Web Design
- Week 5: Navigation and Routing
- Week 6: Reusable Components and Validation
- Week 7: AJAX and Dynamic Content
- Week 8: Client-side Interactivity
- Week 9: Data Persistence with EF Core
- Week 10: Android Kotlin Jetpack Compose
- Week 11: Android Greeting Apps
- Week 12: MAUI Cross-Platform Apps

### Work In Progress
- `20-Kotlin`
- `21-Blazor`

### Supplementary Modules
- `22-MinimalAPIs-REST`
- `22-Unit-Testing`
- `23-CoreSignalR`
- `30-Responsive-Design`
- `31-Playwright-Testing`
- `32-AI-Integration`
- `33-SPA-Integration`

## 📖 Individual Project Documentation

Each weekly folder contains its own examples and documentation:

- [**00.DotnetOverview/README.md**](00.DotnetOverview/README.md) - .NET overview notes
- [**00.SetupDevelopmentEnvironments/README.md**](00.SetupDevelopmentEnvironments/README.md) - setup guide
- [**01.HelloDotnet/**](01.HelloDotnet) to [**12.MauiCrossPlatform/**](12.MauiCrossPlatform) - weekly teaching materials
- [**20-Kotlin/**](20-Kotlin) - work in progress
- [**21-Blazor/**](21-Blazor) - work in progress
- [**33-SPA-Integration/**](33-SPA-Integration) - Angular and ASP.NET Core SPA integration sample

## 🛠️ Development

### Adding a New Project to the Solution

```bash
# Create a new project
dotnet new <template> -n ProjectName

# Add it to the solution
dotnet sln ITEC323.sln add ProjectName/ProjectName.csproj
```

### Opening in Visual Studio

Simply open the `ITEC323.sln` file in Visual Studio, and all projects will be loaded.

## 📝 License

This repository is licensed under the MIT License. See [LICENSE.md](LICENSE.md) for details.
