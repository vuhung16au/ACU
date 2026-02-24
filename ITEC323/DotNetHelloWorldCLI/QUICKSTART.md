# Quick Start Guide

This guide will help you run the DotNetHelloWorldCLI application quickly.

## Prerequisites

Before running this application, ensure you have:

- **.NET SDK** (version 6.0 or later) installed on your machine
  - To check if .NET is installed, run: `dotnet --version`
  - To install .NET, visit: https://dotnet.microsoft.com/download

## How to Run

There are two ways to run this application:

### Option 1: Run from Root Directory (Recommended)

From the `DotNetHelloWorldCLI` folder, run:

```bash
dotnet run --project src/DotNetHelloWorldCLI
```

### Option 2: Navigate to Project Directory

```bash
cd src/DotNetHelloWorldCLI
dotnet run
```

You should see the following output:

```
Hello, World!
```

## Alternative: Build and Run

You can also build the application first, then run the executable:

### From Root Directory

```bash
dotnet build
dotnet run --project src/DotNetHelloWorldCLI
```

### From Project Directory

```bash
cd src/DotNetHelloWorldCLI
dotnet build
dotnet bin/Debug/net*/DotNetHelloWorldCLI.dll
```

## Troubleshooting

**Problem:** `dotnet: command not found`
- **Solution:** Install the .NET SDK from https://dotnet.microsoft.com/download

**Problem:** Build errors
- **Solution:** Ensure you're in the correct directory (`src/DotNetHelloWorldCLI`)

## Next Steps

- Explore the [Program.cs](src/DotNetHelloWorldCLI/Program.cs) file to see the source code
- Read the [FRD.md](FRD.md) for functional requirements
- Check out [docs/README.md](docs/README.md) to learn more about .NET and CLI applications
