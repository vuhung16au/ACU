# C# Core Concepts - Console Application

A comprehensive C# console application that demonstrates fundamental programming concepts for beginners. This project builds upon the Hello World application and introduces core C# language features including variables, data types, operators, control flow, loops, and arrays.

## Overview

This educational project provides hands-on examples of essential C# concepts that every beginner should learn. Each concept is demonstrated with clear, commented code and produces visible output to help you understand how C# works.

## Learning Objectives

By exploring and running this project, you will learn:

1. **Output Methods**: Using `Console.WriteLine()` and `Console.Write()`
2. **Comments**: Single-line and multi-line comments for documenting code
3. **Variables**: Declaring and using variables to store data
4. **Data Types**: Understanding int, double, char, string, bool, and more
5. **Type Casting**: Converting between different data types
6. **User Input**: Reading user input with `Console.ReadLine()`
7. **Operators**: Arithmetic, assignment, comparison, and logical operators
8. **Math Operations**: Using the Math class for calculations
9. **Strings**: String manipulation, concatenation, and interpolation
10. **Booleans**: Working with true/false values and boolean expressions
11. **Control Flow**: If-else statements and switch statements
12. **Loops**: While, do-while, and for loops
13. **Break & Continue**: Controlling loop execution
14. **Arrays**: Storing and accessing multiple values

## Prerequisites

Before you begin, ensure you have:

- **.NET 10.0 SDK** (or .NET 8.0 LTS or later) installed on your system
- A **code editor** such as Visual Studio Code, Visual Studio, or Rider
- Basic understanding of **command-line interfaces** (Terminal/Command Prompt)
- Completion of the **Hello World CLI** project (recommended)

## Technology Stack

- **Language**: C# 
- **Framework**: .NET 10.0 (verified)
- **Project Type**: Console Application
- **Development Environment**: Cross-platform (Windows, macOS, Linux)

## Quick Links

- [QUICKSTART.md](QUICKSTART.md) - Step-by-step guide to build and run this project
- [docs/Key-Takeaways.md](docs/Key-Takeaways.md) - Detailed explanations of each concept
- [FRD.md](FRD.md) - Functional Requirements Document

## Project Files

```
02.CSharpCore/
├── README.md                  # This file - project overview
├── QUICKSTART.md              # Build and run instructions
├── FRD.md                     # Functional requirements
├── CSharpCore.csproj          # Project configuration file
├── Program.cs                 # Main application with all demos
└── docs/
    └── Key-Takeaways.md       # Detailed concept explanations
```

## What This Program Demonstrates

When you run this program, it demonstrates the following concepts in sequence:

1. **Output** - Different ways to display text
2. **Comments** - How to document your code
3. **Variables** - Storing and updating values
4. **Data Types** - Different kinds of data (numbers, text, etc.)
5. **Type Casting** - Converting between types
6. **Operators** - Mathematical and logical operations
7. **Math** - Built-in mathematical functions
8. **Strings** - Working with text
9. **Booleans** - True/false values
10. **If-Else** - Making decisions in code
11. **Switch** - Selecting from multiple options
12. **While Loop** - Repeating code with a condition
13. **For Loop** - Repeating code a specific number of times
14. **Break & Continue** - Controlling loops
15. **Arrays** - Working with collections of data

Each section produces clear output that shows how the concept works.

## Code Structure

The program is organized into separate methods for each concept:

```csharp
static void Main(string[] args)
{
    DemonstrateOutput();
    DemonstrateComments();
    DemonstrateVariables();
    // ... and more
}
```

This modular structure makes it easy to:
- Understand each concept independently
- Run specific demonstrations
- Modify and experiment with the code
- Add your own examples

## Getting Started

1. **Navigate to the project directory**:
   ```bash
   cd ITEC323/03.CSharp/02.CSharpCore
   ```

2. **Build the project**:
   ```bash
   dotnet build
   ```

3. **Run the application**:
   ```bash
   dotnet run
   ```

4. **View the output**: The program will display demonstrations of all core concepts

For detailed instructions, see [QUICKSTART.md](QUICKSTART.md).

## Experimenting with the Code

This project is designed for hands-on learning. Try these activities:

### Beginner Exercises

1. **Modify output**: Change the messages in `DemonstrateOutput()`
2. **Create new variables**: Add more variables in `DemonstrateVariables()`
3. **Try different operators**: Experiment with calculations in `DemonstrateOperators()`
4. **Change conditions**: Modify the if-else logic in `DemonstrateIfElse()`
5. **Adjust loop ranges**: Change loop counters in `DemonstrateForLoop()`

### Intermediate Exercises

1. **Add new methods**: Create a method to demonstrate a concept not covered
2. **Combine concepts**: Write code that uses variables, operators, and loops together
3. **Create a calculator**: Build a simple calculator using operators and user input
4. **Array operations**: Find the maximum, minimum, or average of an array
5. **String manipulation**: Create a method that reverses a string

### Advanced Exercises

1. **User input integration**: Uncomment `DemonstrateUserInput()` and enhance it
2. **Grade calculator**: Build a program that calculates letter grades from scores
3. **Number guessing game**: Create a game using loops, conditionals, and random numbers
4. **Array sorting**: Implement a simple sorting algorithm
5. **Text analyzer**: Count words, characters, or specific patterns in text

## Understanding the Output

When you run the program, you'll see output like this:

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
...
```

Each section is clearly labeled, making it easy to follow along with the code.

## Common Beginner Questions

### Q: Why are some methods `static`?
A: In a console application, the `Main` method is `static`, so all methods it calls directly must also be `static`. This is a fundamental concept you'll understand better as you progress.

### Q: What's the difference between `int` and `double`?
A: `int` stores whole numbers (123), while `double` stores decimal numbers (123.45). Use `int` for counts, `double` for measurements.

### Q: Why do arrays start at index 0?
A: This is a convention in most programming languages. The first element is at position 0, the second at position 1, and so on.

### Q: What's string interpolation?
A: String interpolation (`$"Hello {name}"`) is a clean way to insert variables into strings. It's more readable than concatenation (`"Hello " + name`).

## Next Steps

After completing this project:

1. **Practice**: Modify the code and observe the results
2. **Build something**: Create a small program using these concepts
3. **Learn more**: Explore object-oriented programming (classes and objects)
4. **Move forward**: Progress to more advanced C# topics like LINQ, async/await, and file I/O

## Support

If you encounter any issues:

1. Verify your .NET SDK is installed: `dotnet --version`
2. Check the [QUICKSTART.md](QUICKSTART.md) for detailed setup instructions
3. Review the [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for concept explanations
4. Examine the code comments in [Program.cs](Program.cs)

## Additional Resources

- [Official C# Documentation](https://docs.microsoft.com/en-us/dotnet/csharp/)
- [C# Programming Guide](https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/)
- [.NET CLI Reference](https://docs.microsoft.com/en-us/dotnet/core/tools/)

---

**Course**: ITEC323 - Software Technologies  
**Institution**: Australian Catholic University (ACU)  
**Target Audience**: Beginners learning C# for the first time  
**Last Updated**: March 2026
