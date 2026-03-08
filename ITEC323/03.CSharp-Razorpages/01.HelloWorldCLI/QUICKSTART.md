# Quick Start Guide - Hello World CLI

This guide will walk you through building and running your first C# console application.

## Prerequisites Check

Before starting, verify you have the .NET SDK installed:

```bash
dotnet --version
```

You should see a version number like `10.0.x`, `9.0.x`, or `8.0.x`. This project has been verified with .NET 10.0.103 on macOS. If you don't have .NET installed, visit the [Setup Guide](../../00.SetupDevelopmentEnvironments/SetupDotnet.md).

## Step 1: Navigate to the Project Directory

Open your terminal or command prompt and navigate to the project folder:

```bash
cd /path/to/ITEC323/03.CSharp/01.HelloWorldCLI
```

Or if you're already in the ITEC323 folder:

```bash
cd 03.CSharp/01.HelloWorldCLI
```

## Step 2: Build the Project

Compile the C# code into an executable program:

```bash
dotnet build
```

**What happens during build:**
- The C# compiler (`csc`) reads your source code
- It checks for syntax errors and type issues
- If successful, it creates compiled binaries in the `bin/` folder

**Expected Output:**
```
Build succeeded.
    0 Warning(s)
    0 Error(s)
```

## Step 3: Run the Application

Execute your program:

```bash
dotnet run
```

**Expected Output:**
```
Hello World!
```

Congratulations! You've successfully built and run your first C# console application! 🎉

## Alternative: Run Without Building Explicitly

The `dotnet run` command automatically builds the project if needed, so you can skip Step 2 if you prefer:

```bash
dotnet run
```

This single command will build (if necessary) and run your application.

## Understanding the Commands

### `dotnet build`
- **Purpose**: Compiles your source code into executable files
- **When to use**: When you want to check for compilation errors without running
- **Output location**: `bin/Debug/net8.0/`

### `dotnet run`
- **Purpose**: Builds and executes your application
- **When to use**: During development when you want to test your code quickly
- **Convenience**: Automatically rebuilds if source code has changed

### `dotnet clean`
- **Purpose**: Removes all compiled files and build artifacts
- **When to use**: When you want a fresh start or to save disk space
- **Usage**: `dotnet clean`

## Exploring the Output

After running `dotnet build`, check the `bin/` folder:

```bash
# List the build output (macOS/Linux)
ls -R bin/

# List the build output (Windows)
dir bin\ /s
```

You'll find:
- `HelloWorldCLI.dll` - The compiled .NET assembly
- `HelloWorldCLI.exe` - The executable (Windows) or launcher (macOS/Linux)
- `HelloWorldCLI.deps.json` - Dependency information
- `HelloWorldCLI.runtimeconfig.json` - Runtime configuration

## Running the Compiled Executable Directly

After building, you can run the compiled executable directly:

**macOS/Linux:**
```bash
./bin/Debug/net10.0/HelloWorldCLI
```

**Windows:**
```powershell
.\bin\Debug\net10.0\HelloWorldCLI.exe
```

Or using the .NET runtime:
```bash
dotnet bin/Debug/net10.0/HelloWorldCLI.dll
```

## Common Issues and Solutions

### Issue: `dotnet: command not found`

**Solution**: The .NET SDK is not installed or not in your PATH.
- Install the .NET SDK from [https://dotnet.microsoft.com/download](https://dotnet.microsoft.com/download)
- Restart your terminal after installation

### Issue: `Build FAILED`

**Solution**: Check the error messages for details. Common causes:
- Syntax errors in `Program.cs`
- Missing or corrupted `.csproj` file
- Incompatible .NET SDK version

To see detailed error information:
```bash
dotnet build --verbosity detailed
```

### Issue: "Nothing happens" when running

**Solution**: Check if the output is going to the console:
- Make sure you're in the correct directory
- Try running with: `dotnet run --no-build` to use the existing build
- Check if any antivirus software is blocking execution

## Next Steps

Now that you have the basic application running:

1. **Modify the message**: Open [Program.cs](Program.cs) and change `"Hello World!"` to something else
2. **Rebuild and run**: Use `dotnet run` to see your changes
3. **Experiment**: Try adding multiple `Console.WriteLine()` statements
4. **Learn more**: Read [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for deeper understanding

## Additional Commands

### Check for errors without building
```bash
dotnet build --no-restore
```

### Run with release configuration (optimized)
```bash
dotnet run --configuration Release
```

### Restore dependencies (rarely needed for this simple project)
```bash
dotnet restore
```

### Get help on any command
```bash
dotnet run --help
dotnet build --help
```

---

**Need Help?**
- Check [README.md](README.md) for project overview
- Review [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for concept explanations
- Consult the [.NET CLI documentation](https://docs.microsoft.com/en-us/dotnet/core/tools/)
