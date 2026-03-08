# C# Programming Fundamentals

**Course**: ITEC323 - Software Technologies  
**Institution**: Australian Catholic University (ACU)  
**Last Updated**: March 8, 2026

---

## Overview

This folder contains C# programming projects designed to help you **learn and refresh fundamental C# concepts** as part of the ITEC323 course. Whether you're new to C# or need to review the basics, these projects provide hands-on, practical examples with comprehensive documentation.

All projects are built using **.NET 10.0** and follow modern C# best practices, with extensive comments and educational resources to support your learning journey.

---

## 🎯 Learning Objectives

By completing the projects in this folder, you will:

- ✅ Understand C# syntax and basic programming concepts
- ✅ Master variables, data types, and type casting
- ✅ Learn control flow with if-else and switch statements
- ✅ Work with loops (while, do-while, for, foreach)
- ✅ Manipulate strings, arrays, and collections
- ✅ Apply operators (arithmetic, logical, comparison)
- ✅ Use built-in Math functions
- ✅ Handle user input and console output
- ✅ Write clean, well-documented C# code
- ✅ Build confidence with .NET development tools

---

## 📚 Projects

### [01. HelloWorldCLI](01.HelloWorldCLI/)

**Your First C# Program**

A simple "Hello World" console application that introduces you to:
- Creating a C# console application
- Basic project structure (`.csproj`, `Program.cs`)
- Compiling and running with the .NET CLI
- Essential console output with `Console.WriteLine()`
- XML documentation comments

**Perfect for**: Getting started with C# and .NET development

**Topics Covered**:
- Basic C# syntax
- Namespaces and classes
- Main entry point
- Console output
- Build and run process

---

### [02. CSharpCore](02.CSharpCore/)

**Comprehensive C# Fundamentals**

A complete demonstration of core C# programming concepts with 15 dedicated modules:

1. **Output** - `Console.WriteLine()` and `Console.Write()`
2. **Comments** - Single-line, multi-line, and XML documentation
3. **Variables** - Declaration, initialization, and assignment
4. **Data Types** - int, long, float, double, char, bool, string
5. **Type Casting** - Implicit, explicit, and Convert methods
6. **User Input** - Reading from the console with `Console.ReadLine()`
7. **Operators** - Arithmetic, assignment, comparison, logical
8. **Math Operations** - Math class methods (Max, Min, Sqrt, Pow, etc.)
9. **Strings** - Manipulation, concatenation, interpolation
10. **Booleans** - Boolean logic and expressions
11. **If-Else** - Conditional statements and ternary operators
12. **Switch** - Multi-way branching
13. **While Loops** - While and do-while loops
14. **For Loops** - Traditional for and foreach loops
15. **Break/Continue** - Loop control statements
16. **Arrays** - Declaration, access, iteration, multi-dimensional

**Perfect for**: Comprehensive review or first-time learning of C# fundamentals

**Includes**:
- 600+ lines of well-commented educational code
- Detailed [Key-Takeaways.md](02.CSharpCore/docs/Key-Takeaways.md) with in-depth explanations
- Step-by-step [QUICKSTART.md](02.CSharpCore/QUICKSTART.md) guide
- Complete [Functional Requirements Document](02.CSharpCore/FRD.md)
- Practical exercises (beginner, intermediate, advanced)

---

## 🚀 Quick Start

### Prerequisites

Before starting, ensure you have:

1. **.NET SDK 10.0** (or .NET 8.0+)
   ```bash
   dotnet --version
   # Should show: 10.0.103 or higher
   ```

2. **Code Editor** (choose one):
   - Visual Studio Code (recommended) with C# Dev Kit extension
   - Visual Studio 2022
   - JetBrains Rider
   - Any text editor + terminal

3. **Terminal Access**:
   - macOS/Linux: Built-in Terminal
   - Windows: Command Prompt, PowerShell, or Windows Terminal

### Installing .NET SDK

If you don't have .NET installed:

**macOS**:
```bash
brew install dotnet
```

**Windows**:
Download from https://dotnet.microsoft.com/download

**Linux** (Ubuntu/Debian):
```bash
sudo apt-get update
sudo apt-get install -y dotnet-sdk-10.0
```

---

## 📖 How to Use This Folder

### For Beginners (New to C#)

**Recommended Path**:

1. **Start with 01.HelloWorldCLI**
   - Read the [README.md](01.HelloWorldCLI/README.md)
   - Follow the [QUICKSTART.md](01.HelloWorldCLI/QUICKSTART.md)
   - Build and run your first C# program
   - Understand the basic project structure

2. **Progress to 02.CSharpCore**
   - Read the [README.md](02.CSharpCore/README.md) for overview
   - Build and run the program (see all demonstrations)
   - Read through [Program.cs](02.CSharpCore/Program.cs) - it's heavily commented!
   - Study [Key-Takeaways.md](02.CSharpCore/docs/Key-Takeaways.md) in detail
   - Try the beginner exercises

3. **Practice and Experiment**
   - Modify the code to try your own examples
   - Complete the intermediate exercises
   - Create variations of the demonstrations
   - Build small programs using what you've learned

---

### For Review (Refreshing C# Knowledge)

**Fast Track**:

1. **Jump to 02.CSharpCore**
   - Build and run: `cd 02.CSharpCore && dotnet run`
   - Review the output to see all concepts
   - Use [Key-Takeaways.md](02.CSharpCore/docs/Key-Takeaways.md) as a reference
   - Focus on areas that need refreshing

2. **Targeted Learning**
   - Open [Program.cs](02.CSharpCore/Program.cs)
   - Jump to specific methods (e.g., `DemonstrateArrays()`)
   - Study the concept you need to review
   - Try the advanced exercises for that topic

---

## 🛠️ Building and Running Projects

### General Commands

**Navigate to a project**:
```bash
cd 01.HelloWorldCLI
# or
cd 02.CSharpCore
```

**Build the project**:
```bash
dotnet build
```

**Run the project**:
```bash
dotnet run
```

**Clean build artifacts**:
```bash
dotnet clean
```

**Restore dependencies** (if needed):
```bash
dotnet restore
```

---

## 📝 Project Structure

Each project follows a consistent structure:

```
ProjectName/
├── README.md                  # Project overview and learning objectives
├── QUICKSTART.md              # Step-by-step build and run guide
├── FRD.md                     # Functional Requirements Document
├── ProjectName.csproj         # Project configuration file
├── Program.cs                 # Main source code
├── docs/                      # Additional documentation
│   └── Key-Takeaways.md      # Detailed concept explanations
├── bin/                       # Compiled binaries (auto-generated)
└── obj/                       # Build intermediates (auto-generated)
```

**Note**: `bin/` and `obj/` folders are generated during build and should not be edited manually.

---

## 🎓 Study Tips

### For Effective Learning

1. **Read Before Running**
   - Review the README.md first
   - Understand what each project demonstrates
   - Set clear learning goals

2. **Run and Observe**
   - Execute the programs
   - Carefully read the output
   - See how code translates to results

3. **Read the Code**
   - Open Program.cs in your editor
   - Read the comments and documentation
   - Trace through the logic step by step

4. **Experiment**
   - Modify values and see what changes
   - Add your own examples
   - Break things (safely) and fix them

5. **Document Your Learning**
   - Take notes on new concepts
   - Write down questions
   - Track your progress

### For Troubleshooting

**Build errors?**
- Check that .NET SDK is installed: `dotnet --version`
- Ensure you're in the correct directory
- Read the error message carefully
- Consult the QUICKSTART.md for common issues

**Runtime errors?**
- Review the code around the error line
- Check the comments for explanations
- Refer to Key-Takeaways.md for concept details

**Confused about a concept?**
- Read the XML documentation (hover over methods in VS Code)
- Study the Key-Takeaways.md section
- Try modifying the example to understand it better
- Search for additional resources online

---

## 📋 Learning Checklist

Track your progress through the C# fundamentals:

### 01. HelloWorldCLI
- [ ] Successfully built the project
- [ ] Ran the program and saw "Hello World!"
- [ ] Understand the project file structure
- [ ] Can explain what `Main()` method does
- [ ] Understand namespace and class concepts

### 02. CSharpCore
- [ ] Built and ran the comprehensive demo
- [ ] Understand console output (WriteLine vs Write)
- [ ] Can declare and use variables
- [ ] Know the common data types (int, double, string, bool, etc.)
- [ ] Understand type casting (implicit and explicit)
- [ ] Can use arithmetic and logical operators
- [ ] Understand if-else conditional statements
- [ ] Can use switch statements
- [ ] Understand while and for loops
- [ ] Know when to use break and continue
- [ ] Can work with arrays
- [ ] Completed at least 3 beginner exercises
- [ ] Completed at least 2 intermediate exercises
- [ ] Attempted at least 1 advanced exercise

---

## 🏆 Exercises and Challenges

Once you've worked through both projects, try these challenges:

### Beginner Challenges
1. **Calculator**: Create a simple calculator (add, subtract, multiply, divide)
2. **Grade Calculator**: Input scores and calculate letter grades
3. **Even/Odd Checker**: Determine if numbers are even or odd
4. **Temperature Converter**: Convert Celsius to Fahrenheit and vice versa
5. **Name Reverser**: Take a name and print it backwards

### Intermediate Challenges
1. **Multiplication Table**: Generate a times table for any number
2. **Prime Number Checker**: Determine if a number is prime
3. **Array Statistics**: Calculate mean, median, max, min of numbers
4. **Password Validator**: Check if a password meets criteria
5. **Simple Menu System**: Create an interactive console menu

### Advanced Challenges
1. **Number Guessing Game**: Generate random number, user guesses
2. **Palindrome Checker**: Check if a word/phrase is a palindrome
3. **Tic-Tac-Toe**: Build a simple two-player game
4. **Text Analyzer**: Count words, characters, sentences in text
5. **Student Grade Manager**: Store and manage student records

---

## 🔗 Additional Resources

### Official Documentation
- [Microsoft C# Documentation](https://learn.microsoft.com/en-us/dotnet/csharp/)
- [.NET Documentation](https://learn.microsoft.com/en-us/dotnet/)
- [C# Programming Guide](https://learn.microsoft.com/en-us/dotnet/csharp/programming-guide/)

### Learning Resources
- [C# Fundamentals for Absolute Beginners](https://learn.microsoft.com/en-us/shows/c-fundamentals-for-absolute-beginners/)
- [Learn C# - Interactive Tutorial](https://www.learncs.org/)
- [C# Yellow Book](http://www.csharpcourse.com/) - Free beginner book

### Course Materials
- [ITEC323 Course Repository](../../) - Main repository
- [01. Hello Dotnet](../../01.HelloDotnet/) - Web and desktop app examples
- [02. HTML-CSS](../../02.HTML-CSS/) - Web fundamentals

---

## 📞 Getting Help

### In this Repository
- Read the documentation in each project's folder
- Check the [AGENTS.md](../../AGENTS.md) for coding guidelines
- Review the troubleshooting sections in QUICKSTART.md files

### External Help
- Ask your ITEC323 instructor or tutor
- Participate in course discussion forums
- Search [Stack Overflow](https://stackoverflow.com/questions/tagged/c%23) for C# questions
- Join C# communities (Reddit: r/csharp, Discord servers)

---

## 🤝 Contributing

This is an educational repository for ITEC323 students. If you find errors or have suggestions:

1. Document what you found
2. Discuss with your instructor
3. Suggest improvements (if appropriate)

For AI contributors: Please refer to [AGENTS.md](../../AGENTS.md) for contribution guidelines.

---

## 📜 License

This project is part of the ITEC323 course materials at Australian Catholic University (ACU).

For license information, see [LICENSE.md](../../LICENSE.md) in the repository root.

---

## ✨ Summary

This folder provides a **structured path to learning C# fundamentals** with:

✅ **Beginner-Friendly**: Clear explanations and extensive comments  
✅ **Comprehensive**: Covers all essential C# concepts  
✅ **Practical**: Hands-on code you can run and modify  
✅ **Well-Documented**: Multiple documentation files for different needs  
✅ **Modern**: Uses .NET 10.0 and current best practices  
✅ **Educational**: Designed specifically for ITEC323 students  

Start with [01.HelloWorldCLI](01.HelloWorldCLI/) if you're brand new to C#, or dive into [02.CSharpCore](02.CSharpCore/) if you want a comprehensive review of fundamentals.

**Happy coding!** 🚀

