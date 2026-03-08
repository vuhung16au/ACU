# Quick Start Guide - C# Core Concepts

This guide will walk you through building and running the C# Core Concepts demonstration project.

## Prerequisites Check

Before starting, verify you have the .NET SDK installed:

```bash
dotnet --version
```

You should see a version number like `10.0.x`, `9.0.x`, or `8.0.x`. This project has been verified with .NET 10.0.103 on macOS. If you don't have .NET installed, visit the [Setup Guide](../../00.SetupDevelopmentEnvironments/SetupDotnet.md).

## Step 1: Navigate to the Project Directory

Open your terminal or command prompt and navigate to the project folder:

```bash
cd /path/to/ITEC323/03.CSharp/02.CSharpCore
```

Or if you're already in the ITEC323 folder:

```bash
cd 03.CSharp/02.CSharpCore
```

## Step 2: Build the Project

Compile the C# code into an executable program:

```bash
dotnet build
```

**What happens during build:**
- The C# compiler reads your source code (`Program.cs`)
- It checks for syntax errors and type mismatches
- If successful, it creates compiled binaries in the `bin/` folder
- The output includes a `.dll` file and executable

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

You'll see a comprehensive demonstration of C# concepts:

```
==============================================
Welcome to C# Core Concepts Demo!
==============================================

--- C# Output ---
This uses Console.WriteLine()
Each WriteLine starts on a new line
This uses Console.Write() and continues on the same line.

--- C# Comments ---
Comments help explain code without affecting execution
Single-line: // comment
Multi-line: /* comment */

--- C# Variables ---
Name: Alice
Age: 25
Price: $19.99
Updated Age: 26

... (continues with all demonstrations)
```

The program will display demonstrations of:
- Output methods
- Variables and data types
- Operators and math
- Strings and booleans
- Control flow (if-else, switch)
- Loops (while, for)
- Arrays

Congratulations! You've successfully built and run the C# Core Concepts demo! 🎉

## Understanding the Program Flow

The program executes demonstrations in this order:

1. **DemonstrateOutput()** - Console output methods
2. **DemonstrateComments()** - Code documentation
3. **DemonstrateVariables()** - Variable declaration and usage
4. **DemonstrateDataTypes()** - Different data types
5. **DemonstrateTypeCasting()** - Type conversions
6. **DemonstrateOperators()** - Mathematical and logical operators
7. **DemonstrateMath()** - Math class functions
8. **DemonstrateStrings()** - String operations
9. **DemonstrateBooleans()** - Boolean values
10. **DemonstrateIfElse()** - Conditional statements
11. **DemonstrateSwitch()** - Switch statements
12. **DemonstrateWhileLoop()** - While loops
13. **DemonstrateForLoop()** - For loops
14. **DemonstrateBreakContinue()** - Loop control
15. **DemonstrateArrays()** - Array operations

## Alternative: Run Without Building Explicitly

The `dotnet run` command automatically builds if needed:

```bash
dotnet run
```

This single command will:
1. Check if the project needs rebuilding
2. Compile if source code has changed
3. Execute the application

## Exploring the Build Output

After running `dotnet build`, check the generated files:

```bash
# List the build output (macOS/Linux)
ls -la bin/Debug/net10.0/

# List the build output (Windows)
dir bin\Debug\net10.0\
```

You'll find:
- `CSharpCore.dll` - The compiled .NET assembly
- `CSharpCore.exe` - The executable launcher
- `CSharpCore.deps.json` - Dependency information
- `CSharpCore.runtimeconfig.json` - Runtime configuration
- `CSharpCore.pdb` - Debug symbols

## Running the Compiled Executable Directly

After building, you can run the executable directly without `dotnet run`:

**macOS/Linux:**
```bash
./bin/Debug/net10.0/CSharpCore
```

**Windows:**
```powershell
.\bin\Debug\net10.0\CSharpCore.exe
```

**Using the .NET runtime:**
```bash
dotnet bin/Debug/net10.0/CSharpCore.dll
```

## Modifying and Re-running

To experiment with the code:

1. **Open Program.cs** in your code editor (VS Code, etc.)
2. **Make changes** to any demonstration method
3. **Save the file**
4. **Run again**: `dotnet run`

The program will automatically rebuild with your changes.

### Example Modifications

Try these simple modifications:

**Change a message:**
```csharp
// In DemonstrateOutput(), change:
Console.WriteLine("Hello World!");
// to:
Console.WriteLine("Hello, C# Learner!");
```

**Add a new variable:**
```csharp
// In DemonstrateVariables(), add:
string favoriteColor = "blue";
Console.WriteLine($"Favorite Color: {favoriteColor}");
```

**Modify a loop:**
```csharp
// In DemonstrateForLoop(), change:
for (int i = 0; i < 5; i++)
// to:
for (int i = 0; i < 10; i++)
```

After each modification, run `dotnet run` to see the results!

## Common Issues and Solutions

### Issue: `dotnet: command not found`

**Solution**: The .NET SDK is not installed or not in your PATH.
- Install the .NET SDK from [https://dotnet.microsoft.com/download](https://dotnet.microsoft.com/download)
- Restart your terminal after installation
- Verify with `dotnet --version`

### Issue: `Build FAILED`

**Solution**: Check the error messages for details. Common causes:
- Syntax errors in `Program.cs` (missing semicolons, brackets, etc.)
- Missing or corrupted `.csproj` file
- Incompatible .NET SDK version

To see detailed error information:
```bash
dotnet build --verbosity detailed
```

### Issue: Output is too fast to read

**Solution**: The program displays all demonstrations sequentially. To pause:
- Redirect output to a file: `dotnet run > output.txt`
- Read the output file: `cat output.txt` (macOS/Linux) or `type output.txt` (Windows)
- Or add pauses in code using `Console.ReadKey()` between demonstrations

### Issue: Want to run only specific demonstrations

**Solution**: Comment out methods you don't want to run in `Main()`:
```csharp
static void Main(string[] args)
{
    // ... welcome message ...
    
    DemonstrateOutput();
    // DemonstrateComments();        // Commented out
    // DemonstrateVariables();        // Commented out
    DemonstrateDataTypes();           // This will run
    // DemonstrateTypeCasting();      // Commented out
    
    // ... etc ...
}
```

## Additional Commands

### Clean build artifacts
```bash
dotnet clean
```

### Build in Release mode (optimized)
```bash
dotnet build --configuration Release
dotnet run --configuration Release
```

### Check for compilation errors only (no build)
```bash
dotnet build --no-restore
```

### Restore dependencies
```bash
dotnet restore
```

### Get help on commands
```bash
dotnet run --help
dotnet build --help
```

## Running with Different .NET Versions

This project targets .NET 10.0 but is compatible with .NET 8.0 LTS and later.

To verify compatibility:
```bash
dotnet --list-sdks
```

If you have multiple SDKs installed, the project will use the one specified in the `.csproj` file.

## Enabling User Input (Optional)

The program includes a `DemonstrateUserInput()` method that's commented out by default (to avoid blocking in automated tests).

To enable interactive user input:

1. Open `Program.cs`
2. Find this line in `Main()`:
   ```csharp
   // Optional: User input demonstration (commented out to avoid blocking in automated runs)
   // DemonstrateUserInput();
   ```
3. Uncomment it:
   ```csharp
   // Optional: User input demonstration
   DemonstrateUserInput();
   ```
4. Save and run: `dotnet run`

The program will now prompt you for input:
```
--- C# User Input ---
Enter your name: [type your name here]
Hello, [your name]!
Enter your age: [type your age]
You are [age] years old.
```

## Next Steps

Now that you have the program running:

1. **Read the code**: Open [Program.cs](Program.cs) and study each method
2. **Experiment**: Modify values, add new examples, break things and fix them
3. **Learn more**: Read [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for deeper explanations
4. **Practice**: Try the exercises suggested in [README.md](README.md)
5. **Build something**: Create your own small program using these concepts

## Performance Notes

- **Build time**: Should complete in under 3 seconds
- **Run time**: Executes instantly and completes in less than 1 second
- **Output**: Approximately 200 lines of demonstration output

## Troubleshooting Checklist

If something isn't working, verify:

- [ ] .NET SDK is installed (`dotnet --version`)
- [ ] You're in the correct directory (`pwd` or `cd`)
- [ ] The `.csproj` file exists and is valid
- [ ] `Program.cs` exists and has no syntax errors
- [ ] No antivirus software is blocking execution
- [ ] Terminal has proper permissions to execute

## Getting Help

Need more assistance?

1. Check [README.md](README.md) for project overview
2. Review [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for concept explanations
3. Read the code comments in [Program.cs](Program.cs)
4. Consult the [.NET documentation](https://docs.microsoft.com/en-us/dotnet/)

---

**Need Help?**
- Project overview: [README.md](README.md)
- Detailed concepts: [docs/Key-Takeaways.md](docs/Key-Takeaways.md)
- Official docs: [Microsoft .NET Documentation](https://docs.microsoft.com/en-us/dotnet/)
