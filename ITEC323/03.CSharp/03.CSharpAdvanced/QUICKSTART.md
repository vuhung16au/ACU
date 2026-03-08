# QUICKSTART Guide - C# Advanced Concepts

**Project**: `03.CSharp/03.CSharpAdvanced`  
**Course**: ITEC323 - Software Technologies  
**Last Updated**: March 8, 2026

---

## 📖 Overview

This guide will walk you through building and running the **C# Advanced Concepts** project step-by-step. Follow these instructions to see demonstrations of 14 advanced C# concepts including methods, OOP (object-oriented programming), inheritance, polymorphism, interfaces, and exception handling.

**Estimated Time**: 10-15 minutes (first-time setup)

---

## ✅ Prerequisites Check

Before starting, make sure you have everything installed:

### 1. Check .NET SDK Installation

Open your terminal (Command Prompt, PowerShell, or Terminal on macOS/Linux) and run:

```bash
dotnet --version
```

**Expected Output**:
```
10.0.103
```
Or any version `10.0.x`, `9.0.x`, or `8.0.x`

**If .NET is not installed**:
- Download from: https://dotnet.microsoft.com/download
- Install .NET SDK 10.0 (or .NET 8.0 LTS minimum)
- Restart your terminal after installation
- Run `dotnet --version` again to verify

### 2. Verify Code Editor (Optional but Recommended)

Any text editor works, but these are recommended:

- **Visual Studio Code** with C# Dev Kit extension
- **Visual Studio 2022** (Windows/Mac)
- **JetBrains Rider**

---

## 🚀 Step-by-Step Instructions

### Step 1: Navigate to Project Directory

Open your terminal and navigate to the project folder:

**On Windows (Command Prompt or PowerShell)**:
```cmd
cd C:\path\to\ITEC323\03.CSharp\03.CSharpAdvanced
```

**On macOS/Linux (Terminal)**:
```bash
cd /path/to/ITEC323/03.CSharp/03.CSharpAdvanced
```

**Verify you're in the correct directory**:
```bash
ls        # macOS/Linux
dir       # Windows
```

You should see files: `Program.cs`, `CSharpAdvanced.csproj`, `README.md`, etc.

---

### Step 2: Build the Project

Build the project to compile the C# code:

```bash
dotnet build
```

**What this does**:
- Compiles all C# code in `Program.cs`
- Checks for syntax errors
- Creates executable files in `bin/` folder
- Shows any build warnings or errors

**Expected Output**:
```
MSBuild version X.X.X for .NET
  Determining projects to restore...
  All projects are up-to-date for restore.
  CSharpAdvanced -> /path/to/bin/Debug/net10.0/CSharpAdvanced.dll

Build succeeded.
    0 Warning(s)
    0 Error(s)

Time Elapsed XX:XX:XX.XX
```

**✅ Success Indicator**: "Build succeeded" with 0 errors

**❌ If build fails**: See [Troubleshooting](#-troubleshooting) section below

---

### Step 3: Run the Project

Execute the program to see all demonstrations:

```bash
dotnet run
```

**What this does**:
- Builds the project (if needed)
- Runs the compiled executable
- Displays output in the terminal

**Expected Output**: See [Understanding the Output](#-understanding-the-output) section below

---

## 📺 Understanding the Output

When you run `dotnet run`, you'll see **14 sections** demonstrating different concepts:

### Output Structure

```
=== C# Advanced Concepts Demonstration ===

=== 1. Methods ===
Methods are reusable blocks of code...
[Examples and output]

=== 2. Method Parameters ===
Parameters allow passing data to methods...
[Examples and output]

=== 3. Method Overloading ===
Multiple methods can have the same name...
[Examples and output]

=== 4. Classes and Objects ===
Classes are blueprints for objects...
[Examples and output]

=== 5. Constructors ===
Constructors initialize objects...
[Examples and output]

=== 6. Access Modifiers ===
Control visibility with public/private...
[Examples and output]

=== 7. Properties (Get and Set) ===
Properties provide controlled access...
[Examples and output]

=== 8. Inheritance ===
Child classes inherit from parent classes...
[Examples and output]

=== 9. Polymorphism ===
Objects can take multiple forms...
[Examples and output]

=== 10. Abstraction ===
Abstract classes provide templates...
[Examples and output]

=== 11. Interfaces ===
Interfaces define contracts...
[Examples and output]

=== 12. Enums ===
Named constants for better readability...
[Examples and output]

=== 13. File Operations ===
Creating, reading, and writing files...
[Examples and output]

=== 14. Exception Handling ===
Handling errors gracefully...
[Examples and output]

=== Demonstration Complete! ===
```

### Total Output

- **200+ lines** of educational output
- **14 concept demonstrations**
- **Explanations** followed by practical examples
- **File operations** (creates and deletes `demo.txt`)

---

## 🔍 Exploring the Code

### Opening the Code

**Using Visual Studio Code**:
```bash
code .
```

**Using Visual Studio 2022**:
- File → Open → Select `CSharpAdvanced.csproj`

**Using any text editor**:
- Open `Program.cs` in your preferred editor

### Code Structure

```csharp
// Program.cs structure:

using System;
using System.IO;

namespace CSharpAdvanced
{
    class Program
    {
        static void Main(string[] args)
        {
            // Calls 14 demonstration methods
            DemonstrateMethods();
            DemonstrateMethodParameters();
            // ... and so on
        }
        
        // 14 demonstration methods
        static void DemonstrateMethods() { }
        static void DemonstrateMethodParameters() { }
        // ... etc.
        
        // 20+ supporting classes
        class Car { }
        class Person { }
        class Animal { }
        // ... etc.
    }
}
```

### Reading the Code

1. **Start with Main()** - See what demonstrations are called
2. **Read each demonstration method** - Understand one concept at a time
3. **Study supporting classes** - See how classes are defined
4. **Read comments** - Every concept is explained inline

**Tip**: The code is heavily commented. Read the comments to understand each concept!

---

## 🛠️ Modifying the Code

### Safe Experiments to Try

#### 1. Modify Output Messages

Change a greeting message:

```csharp
// Original
Console.WriteLine("Welcome to C# Advanced Concepts!");

// Modified
Console.WriteLine("Hello! Let's learn advanced C#!");
```

#### 2. Add Your Own Method

Add a new method in the Program class:

```csharp
/// <summary>
/// Your custom method
/// </summary>
static void MyCustomMethod()
{
    Console.WriteLine("This is my custom method!");
}
```

Then call it from Main:

```csharp
static void Main(string[] args)
{
    Console.WriteLine("=== C# Advanced Concepts Demonstration ===\n");
    
    MyCustomMethod();  // Add this line
    
    DemonstrateMethods();
    // ... rest of the code
}
```

#### 3. Create a New Class

Add a new class at the bottom of Program.cs:

```csharp
/// <summary>
/// A simple book class
/// </summary>
class Book
{
    public string Title;
    public string Author;
    public int Pages;
    
    public void DisplayInfo()
    {
        Console.WriteLine($"{Title} by {Author} ({Pages} pages)");
    }
}
```

Then use it in Main:

```csharp
Book myBook = new Book();
myBook.Title = "C# Advanced Concepts";
myBook.Author = "ITEC323 Team";
myBook.Pages = 300;
myBook.DisplayInfo();
```

#### 4. Experiment with Existing Examples

- Change the Car brand from "Toyota" to "Honda"
- Change the Student name from "Alice" to your name
- Modify the BankAccount initial balance
- Add a new animal type (e.g., Rabbit)

**After each change**:
1. Save the file
2. Run `dotnet build` to check for errors
3. Run `dotnet run` to see your changes

---

## 🐛 Troubleshooting

### Common Issues and Solutions

#### Issue 1: ".NET SDK not installed"

**Error Message**:
```
'dotnet' is not recognized as an internal or external command
```

**Solution**:
1. Install .NET SDK from https://dotnet.microsoft.com/download
2. Choose .NET 10.0 SDK (or .NET 8.0 LTS)
3. Restart your terminal/command prompt
4. Verify with `dotnet --version`

---

#### Issue 2: "Build Failed" - Missing Namespace

**Error Message**:
```
error CS0246: The type or namespace name 'Console' could not be found
```

**Solution**:
- Ensure `using System;` is at the top of Program.cs
- Check that you haven't accidentally deleted the using statements

---

#### Issue 3: "Build Failed" - Syntax Error

**Error Message**:
```
error CS1002: ; expected
error CS1513: } expected
```

**Solution**:
- Check for missing semicolons `;` at the end of statements
- Check for mismatched curly braces `{}`
- Ensure all method calls end with `();`
- Use an editor with syntax highlighting to spot errors

---

#### Issue 4: "File already exists" when running

**Symptom**: Program creates `demo.txt` but doesn't delete it

**Solution**:
- The program should automatically delete `demo.txt` at the end
- If it remains, manually delete it: `rm demo.txt` (macOS/Linux) or `del demo.txt` (Windows)
- This can happen if the program crashes before reaching the delete section

---

#### Issue 5: "Access denied" when creating file

**Error Message**:
```
System.UnauthorizedAccessException: Access to the path ... is denied
```

**Solution**:
- Ensure you have write permissions in the project directory
- Try running the terminal/command prompt as administrator (Windows)
- Check that the directory isn't read-only

---

#### Issue 6: Build succeeds but output is incomplete

**Symptom**: Only see partial output

**Solution**:
- Ensure you're running `dotnet run` not `dotnet build`
- Check that the terminal window is large enough to display all output
- Try redirecting output to a file: `dotnet run > output.txt`
- Then view: `cat output.txt` (macOS/Linux) or `type output.txt` (Windows)

---

#### Issue 7: "The type or namespace name 'X' could not be found"

**Error Message**:
```
error CS0246: The type or namespace name 'File' could not be found
```

**Solution**:
- Add `using System.IO;` at the top of Program.cs (for File operations)
- Check that all required using statements are present

---

### Getting Additional Help

If you're still having issues:

1. **Read the error message carefully** - It usually tells you exactly what's wrong
2. **Check the line number** - Error messages include line numbers (e.g., "Program.cs(45)")
3. **Compare with original** - Make sure your code matches the repository version
4. **Ask in class** - Your instructor or tutor can help
5. **Search online** - Copy the error message and search on Stack Overflow

---

## 📚 Next Steps After Running

### 1. Study the Code (15-30 minutes)

- Open `Program.cs` in your editor
- Read each demonstration method
- Understand how concepts work

### 2. Read Key Takeaways (30-60 minutes)

- Open [docs/Key-Takeaways.md](docs/Key-Takeaways.md)
- Deep dive into each of the 14 concepts
- Study examples and best practices

### 3. Try Exercises (1-2 hours)

- See exercises in [README.md](README.md)
- Start with beginner exercises
- Progress to intermediate and advanced

### 4. Experiment (30+ minutes)

- Modify the code
- Add your own classes
- Try the suggestions in [Modifying the Code](#-modifying-the-code) section
- Break things and learn from errors!

### 5. Build Your Own Project (2+ hours)

Apply what you've learned:
- Create a new console app: `dotnet new console -n MyAdvancedProject`
- Use concepts from this project
- Build something interesting!

---

## 🔄 Rebuilding and Cleaning

### Clean Build Artifacts

Remove generated files to start fresh:

```bash
dotnet clean
```

This removes `bin/` and `obj/` folders.

### Rebuild from Scratch

```bash
dotnet clean
dotnet build
```

### Full Rebuild and Run

```bash
dotnet clean && dotnet build && dotnet run
```

**When to clean and rebuild**:
- After major code changes
- If you see unexpected behavior
- If build errors persist

---

## 📊 Performance Notes

### Build Time

- **First build**: 5-10 seconds (restores dependencies)
- **Subsequent builds**: 1-3 seconds (only changed files)

### Run Time

- **Execution time**: 1-2 seconds
- **Output**: ~200 lines to console
- **File operations**: Creates and deletes `demo.txt` (very fast)

### Project Size

- **Source code**: ~1,100 lines (Program.cs)
- **Compiled output**: ~10 KB (in bin/Debug/net10.0/)
- **Total project**: ~50 KB (including source and documentation)

---

## 🎯 Verification Checklist

Use this checklist to verify everything works:

- [ ] .NET SDK is installed (`dotnet --version` works)
- [ ] Navigated to project directory (see `Program.cs` when listing files)
- [ ] `dotnet build` completes successfully (0 errors, 0 warnings)
- [ ] `dotnet run` executes without errors
- [ ] Output shows all 14 demonstrations
- [ ] `demo.txt` is created and deleted automatically
- [ ] Can open and read Program.cs in an editor
- [ ] Code includes comments explaining concepts

**If all checkboxes are ticked**: ✅ Everything is working perfectly!

---

## 🎓 Learning Approach

### For Visual Learners

1. Run the program first (`dotnet run`)
2. See the output
3. Then read the code to understand how it works

### For Abstract Thinkers

1. Read the code first (each demonstration method)
2. Predict what the output will be
3. Run the program to verify your predictions

### For Hands-On Learners

1. Run the program once
2. Make a small change to the code
3. Run again and see the difference
4. Repeat with different changes

**All approaches are valid!** Use what works best for you.

---

## 📖 Related Documentation

- [README.md](README.md) - Project overview and learning objectives
- [docs/Key-Takeaways.md](docs/Key-Takeaways.md) - Detailed concept explanations
- [FRD.md](FRD.md) - Functional requirements
- [../README.md](../README.md) - Overview of all C# projects in this folder

---

## ✅ Success Criteria

You'll know you've successfully completed this quickstart when you can:

✅ Build the project without errors  
✅ Run the project and see all 14 demonstrations  
✅ Understand the output  
✅ Open and read Program.cs  
✅ Identify where each concept is demonstrated  
✅ Make a simple code change and re-run successfully  

---

## 🎉 Congratulations!

If you've followed this guide and can build and run the project successfully, you're ready to dive deep into C# advanced concepts!

**Next Actions**:
1. ✅ Complete this quickstart
2. 📖 Study [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for concept deep-dives
3. 💪 Try exercises in [README.md](README.md)
4. 🚀 Build your own project using these concepts

**Happy coding!** 🎊

