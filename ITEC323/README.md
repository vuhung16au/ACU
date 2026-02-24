# ITEC323 - Web and Mobile Application Development

This repository contains supplementary code and examples for **ITEC323 - Web and Mobile Application Development** at ACU.

## 🚧 Work in Progress

This repository is actively being developed and updated with new examples and projects throughout the semester.

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
├── ITEC323.sln                  # Visual Studio solution file
├── 00.SetupDotnet/              # .NET setup instructions
├── AspNetHelloWorld/            # ASP.NET Core web application example
│   ├── Program.cs
│   ├── README.md
│   └── docs/
├── DotNetHelloWorldCLI/         # .NET console application example
│   ├── src/
│   ├── docs/
│   ├── README.md
│   └── FRD.md
└── .vscode/                     # VS Code configuration
```

## 🚀 How to Build and Run

### Prerequisites

- [.NET SDK](https://dotnet.microsoft.com/download) (version 6.0 or later)
- A code editor (Visual Studio, Visual Studio Code, or JetBrains Rider)

### Building the Solution

To build all projects in the solution:

```bash
# Navigate to the repository root
cd ITEC323

# Build all projects
dotnet build ITEC323.sln
```

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

### .NET Fundamentals
- ✅ Getting Started with .NET
- ✅ Building an ASP.NET Website

### Web Development
- Designing Web Pages
- Working with ASP.NET Server Controls
- Programming ASP.NET Web Pages
- Creating Consistent Looking Websites
- Navigation and User Control

### User Input and Validation
- Validating User Input
- ASP.NET Validation Controls

### Advanced Web Technologies
- jQuery and AJAX
- Data-bound Controls

### Mobile Development
- Introduction to Android Mobile Development

## 📖 Individual Project Documentation

Each project folder contains its own documentation:

- [**AspNetHelloWorld/README.md**](AspNetHelloWorld/README.md) - ASP.NET Core web application details
- [**DotNetHelloWorldCLI/README.md**](DotNetHelloWorldCLI/README.md) - Console application details
- [**00.SetupDotnet/README.md**](00.SetupDotnet/README.md) - .NET setup guide

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

This repository is for educational purposes as part of the ITEC323 unit at ACU.

## 👥 Contributing

This is a course repository. Students should follow their instructor's guidelines for contributions and submissions.
