# C# Advanced Concepts


## 📖 Overview

Welcome to the **C# Advanced Concepts** project! This console application is designed to help you progress beyond the fundamentals and explore advanced C# programming concepts. Building upon the basics from [02.CSharpCore](../02.CSharpCore/), this project introduces you to **object-oriented programming (OOP)**, advanced method techniques, and robust error handling.

This project is **part of the ITEC323 Software Technologies course** and serves as a comprehensive learning resource for students ready to take their C# skills to the next level.

---

## 🎯 Learning Objectives

By completing this project, you will:

✅ **Master Methods** - Create reusable code blocks with parameters and return values  
✅ **Understand Method Overloading** - Use the same method name with different signatures  
✅ **Learn Object-Oriented Programming** - Work with classes, objects, and encapsulation  
✅ **Use Constructors** - Initialize objects with custom logic  
✅ **Apply Access Modifiers** - Control visibility with public, private, and protected  
✅ **Implement Properties** - Use get/set accessors for controlled field access  
✅ **Work with Inheritance** - Create class hierarchies and reuse code  
✅ **Apply Polymorphism** - Use parent references for child objects  
✅ **Understand Abstraction** - Design with abstract classes and methods  
✅ **Use Interfaces** - Define contracts for class behavior  
✅ **Work with Enums** - Create named constants for better readability  
✅ **Handle Files** - Read and write files using System.IO  
✅ **Handle Exceptions** - Write robust code with try-catch-finally  

---

## 📚 Topics Covered

This project demonstrates **14 advanced C# concepts** through dedicated modules:

### 1. **Methods**
- Creating methods with and without parameters
- Return values and void methods
- Method naming conventions

### 2. **Method Parameters**
- Pass by value (default)
- Pass by reference (`ref` keyword)
- Output parameters (`out` keyword)
- Optional parameters with default values

### 3. **Method Overloading**
- Multiple methods with the same name
- Different parameter types and counts
- Compiler method resolution

### 4. **Classes and Objects**
- Defining classes as blueprints
- Creating object instances
- Class members (fields and methods)
- Using object references

### 5. **Constructors**
- Default constructors
- Parameterized constructors
- Constructor overloading
- Object initialization

### 6. **Access Modifiers**
- `public` - accessible everywhere
- `private` - accessible within class only
- `protected` - accessible in class and derived classes
- Encapsulation principles

### 7. **Properties (Get and Set)**
- Properties vs fields
- Get and set accessors
- Validation in setters
- Auto-implemented properties
- Read-only and write-only properties

### 8. **Inheritance**
- Parent and child classes
- `base` keyword for parent access
- Inheriting fields and methods
- The `is-a` relationship

### 9. **Polymorphism**
- Method overriding with `virtual` and `override`
- Runtime type resolution
- Parent references to child objects
- Polymorphic collections

### 10. **Abstraction**
- Abstract classes with `abstract` keyword
- Abstract methods (no implementation)
- Concrete methods in abstract classes
- Cannot instantiate abstract classes

### 11. **Interfaces**
- Defining contracts with `interface`
- Implementing interface members
- Multiple interface implementation
- Interface vs abstract class

### 12. **Enums**
- Named constants for readability
- Default and explicit enum values
- Using enums in conditionals
- Iterating through enum values

### 13. **File Operations**
- Creating and writing files
- Reading file contents
- Checking file existence
- Deleting files
- Exception handling for file operations

### 14. **Exception Handling**
- Try-catch blocks
- Multiple catch blocks for different exception types
- Finally block (always executes)
- Throwing exceptions with `throw`
- Custom exception messages

---

## 🚀 Prerequisites

Before starting this project, you should:

1. **Complete [02.CSharpCore](../02.CSharpCore/)** - Understand variables, data types, operators, control flow, loops, and arrays

2. **Have .NET SDK 10.0 installed** (or .NET 8.0+)
   ```bash
   dotnet --version
   # Should show: 10.0.103 or higher
   ```

3. **Basic understanding of**:
   - Variables and data types
   - Control flow (if-else, switch)
   - Loops (for, while, foreach)
   - Arrays

4. **Code Editor**:
   - Visual Studio Code (recommended) with C# Dev Kit
   - Visual Studio 2022
   - JetBrains Rider
   - Any text editor + terminal

---

## 🏗️ Project Structure

```
03.CSharpAdvanced/
├── README.md                      # This file - project overview
├── QUICKSTART.md                  # Step-by-step build and run guide
├── FRD.md                         # Functional Requirements Document
├── CSharpAdvanced.csproj          # Project configuration (.NET 10.0)
├── Program.cs                     # Main code (1000+ lines, well-commented)
├── docs/
│   └── Key-Takeaways.md          # Detailed concept explanations
├── bin/                           # Build output (auto-generated)
└── obj/                           # Build intermediates (auto-generated)
```

---

## 📋 Quick Start

### Step 1: Navigate to Project Directory

```bash
cd /path/to/ITEC323/03.CSharp/03.CSharpAdvanced
```

### Step 2: Build the Project

```bash
dotnet build
```

Expected output:
```
Build succeeded in X.Xs
```

### Step 3: Run the Project

```bash
dotnet run
```

You'll see demonstrations of all 14 advanced concepts with clear output and explanations!

📖 **For detailed instructions**, see [QUICKSTART.md](QUICKSTART.md)

---

## 🎓 How to Use This Project

### For First-Time Learners

**Recommended Learning Path**:

1. **Read this README** - Understand what concepts are covered
2. **Run the program** - See all demonstrations in action
3. **Study Program.cs** - Read the heavily commented code
4. **Read Key-Takeaways.md** - Deep dive into each concept
5. **Experiment** - Modify the code and try your own examples
6. **Complete exercises** - Practice what you've learned

### Study Structure

Each concept in Program.cs follows this pattern:

```csharp
/// <summary>
/// Demonstrates [concept name]
/// Brief explanation of the concept
/// </summary>
static void Demonstrate[ConceptName]()
{
    Console.WriteLine("\n=== [Number]. [Concept Name] ===");
    Console.WriteLine("Explanation...");
    
    // Example code with detailed comments
    // Demonstrates the concept in action
    // Shows practical usage
}
```

### Self-Study Tips

1. **Read Before Running** - Understand what each demonstration does
2. **Predict Output** - Before running, guess what will happen
3. **Run and Observe** - See if your predictions were correct
4. **Modify and Experiment** - Change values, add code, break things
5. **Document Learning** - Take notes on new concepts
6. **Ask Questions** - Write down anything you don't understand
7. **Practice** - Try the exercises in this README

---

## 💡 Code Highlights

### Supporting Classes

The project includes multiple supporting classes to demonstrate concepts:

- **Car** - Basic class structure
- **Person** - Constructor demonstration
- **BankAccount** - Access modifiers and encapsulation
- **Student** - Properties with validation
- **Animal, Dog, Cat, Bird** - Inheritance and polymorphism
- **Shape, Circle, Rectangle, Triangle** - Abstract classes
- **IPlayable, Guitar, Piano, Drums** - Interfaces
- **SmartPhone** - Multiple interface implementation
- **Day, OrderStatus** - Enumerations

### Key Features

✨ **1000+ lines** of well-documented code  
✨ **14 demonstration methods** covering all key concepts  
✨ **20+ supporting classes** showing real-world examples  
✨ **Extensive comments** explaining every concept  
✨ **XML documentation** on all public methods  
✨ **Beginner-friendly** explanations and examples  
✨ **Progressive complexity** from simple to advanced  

---

## 🏋️ Exercises

### Beginner Exercises

1. **Add a New Method**
   - Create a method called `Multiply` that takes two integers and returns their product
   - Call it from `Main` and display the result

2. **Create a Book Class**
   - Add fields: title, author, pages, price
   - Add a method: `DisplayInfo()`
   - Create two book objects and display their info

3. **Extend the Animal Class**
   - Add a new animal type (e.g., `Rabbit`)
   - Override the `MakeSound()` method
   - Add a unique method (e.g., `Hop()`)

### Intermediate Exercises

4. **Build a Calculator Class**
   - Create a `Calculator` class with methods: Add, Subtract, Multiply, Divide
   - Overload methods to work with int and double
   - Add exception handling for division by zero

5. **Create a Library System**
   - Create classes: `LibraryItem` (abstract), `Book`, `Magazine`, `DVD`
   - Add properties: Title, CheckedOut (bool)
   - Implement methods: `CheckOut()`, `Return()`

6. **Implement a Vehicle Hierarchy**
   - Create abstract `Vehicle` class with abstract method `GetFuelEfficiency()`
   - Create derived classes: `Car`, `Truck`, `Motorcycle`
   - Add specific properties for each type

### Advanced Exercises

7. **Shopping Cart System**
   - Create interfaces: `IShoppable`, `IDiscountable`
   - Create classes: `Product`, `ShoppingCart`
   - Implement add/remove items, calculate total with discounts
   - Add exception handling for invalid operations

8. **File-Based Contact Manager**
   - Create a `Contact` class with properties: Name, Phone, Email
   - Implement methods to save contacts to a file
   - Implement methods to load contacts from a file
   - Add exception handling for file operations
   - Use properties to validate email format

9. **Game Character System**
   - Create abstract `Character` class with abstract method `Attack()`
   - Create interfaces: `IHealable`, `IMagicUser`
   - Implement multiple character types: Warrior, Mage, Healer
   - Add properties for health, damage, and use enums for character states

### Challenge Exercises

10. **Build a Complete Application**
    - Combine multiple concepts (classes, inheritance, interfaces, exceptions)
    - Create a mini-project: to-do list, quiz game, or inventory system
    - Use file I/O to persist data
    - Implement proper error handling throughout

---

## 📖 Documentation

This project includes comprehensive documentation:

### [QUICKSTART.md](QUICKSTART.md)
- Prerequisites check
- Step-by-step build instructions
- Running the application
- Expected output
- Troubleshooting common issues
- Modifying the code

### [docs/Key-Takeaways.md](docs/Key-Takeaways.md)
- Detailed explanations of all 14 concepts
- Code examples with explanations
- Best practices for each concept
- Common mistakes to avoid
- Comparison tables (interface vs abstract class, etc.)
- When to use each feature

### [FRD.md](FRD.md)
- Functional requirements for each concept
- Non-functional requirements
- Success criteria
- Verification status
- Project scope and constraints

---

## 🔗 Related Projects

### Previous Projects (Build Foundation)
- [01.HelloWorldCLI](../01.HelloWorldCLI/) - Your first C# program
- [02.CSharpCore](../02.CSharpCore/) - Fundamental C# concepts

### Course Materials
- [ITEC323 Repository Root](../../) - Main course repository
- [03.CSharp README](../README.md) - Overview of all C# projects
- [01.HelloDotnet](../../01.HelloDotnet/) - Web and desktop examples

---

## 🛠️ Technology Stack

| Component | Version/Technology |
|-----------|-------------------|
| **Language** | C# (compatible with C# 10.0+) |
| **Framework** | .NET 10.0 LTS |
| **Project Type** | Console Application |
| **Platform** | Cross-platform (Windows, macOS, Linux) |
| **Dependencies** | System, System.IO (built-in) |
| **Build Tool** | .NET CLI (`dotnet` command) |

---

## ⚠️ Common Issues and Solutions

### Build Errors

**Issue**: "The type or namespace name 'X' could not be found"  
**Solution**: Ensure you have the correct `using` statements at the top of Program.cs

**Issue**: ".NET SDK not found"  
**Solution**: Install .NET SDK 10.0 from https://dotnet.microsoft.com/download

### Runtime Errors

**Issue**: "File not found" exception  
**Solution**: The program will create/delete a demo.txt file - ensure you have write permissions in the directory

**Issue**: Unexpected output  
**Solution**: Make sure you're running the latest version - try `dotnet clean` then `dotnet build`

### Understanding the Code

**Issue**: "Too much code, feeling overwhelmed"  
**Solution**: Focus on one demonstration at a time. Read the method, run the program, see that specific output

**Issue**: "Don't understand a concept"  
**Solution**: Read [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for detailed explanations

---

## 🤝 Contributing and Feedback

This project is part of ITEC323 course materials. If you:

- Find errors or typos
- Have suggestions for improvements
- Want to add more examples
- Need clarification on concepts

Please discuss with your instructor or course coordinator.

For AI contributors: Refer to [AGENTS.md](../../AGENTS.md) for contribution guidelines.

---

## 📜 License

See [LICENSE.md](../../LICENSE.md) in the repository root for details.

---

## ✨ What Makes This Project Special

🎯 **Comprehensive** - Covers all essential advanced C# concepts in one place  
📖 **Educational** - Designed specifically for learners, not experienced developers  
💬 **Well-Commented** - Every concept explained with clear comments  
🏗️ **Real Examples** - Practical, relatable examples (not abstract theory)  
🚀 **Modern** - Uses .NET 10.0 and current best practices  
🔄 **Progressive** - Builds on fundamentals, introduces concepts gradually  
✅ **Complete** - Includes all documentation, no gaps in learning materials  
🌍 **Cross-Platform** - Runs on Windows, macOS, and Linux  

---

## 🎓 Next Steps

After completing this project, you'll be ready to:

1. **Build Real Applications** - Create your own C# projects
2. **Learn Advanced Topics** - LINQ, async/await, generics, delegates
3. **Explore Frameworks** - ASP.NET Core, Entity Framework, WPF, MAUI
4. **Study Design Patterns** - Singleton, Factory, Observer, etc.
5. **Contribute to Projects** - Join open-source C# communities

### Recommended Next Learning

- [01.HelloDotnet/01.HelloWorldBlazor](../../01.HelloDotnet/01.HelloWorldBlazor/) - Build web UIs with Blazor
- [01.HelloDotnet/02.HelloWorldApi](../../01.HelloDotnet/02.HelloWorldApi/) - Create REST APIs
- Microsoft Learn C# Path - https://learn.microsoft.com/en-us/dotnet/csharp/

---

## 📞 Getting Help

### Within This Repository
- Read the [QUICKSTART.md](QUICKSTART.md) for build/run help
- Study [Key-Takeaways.md](docs/Key-Takeaways.md) for concept explanations
- Review comments in [Program.cs](Program.cs)

### Course Support
- Ask your ITEC323 instructor or tutor
- Participate in course discussion forums
- Attend lab sessions and ask questions

### External Resources
- [Microsoft C# Documentation](https://learn.microsoft.com/en-us/dotnet/csharp/)
- [Stack Overflow C# Tag](https://stackoverflow.com/questions/tagged/c%23)
- [C# Programming Guide](https://learn.microsoft.com/en-us/dotnet/csharp/programming-guide/)

---

## 🎉 Summary

This **C# Advanced Concepts** project provides a comprehensive, hands-on introduction to advanced C# programming. With **14 core concepts**, **1000+ lines of commented code**, and **extensive documentation**, you have everything you need to master object-oriented programming and advanced C# techniques.

**Start your journey now**:
1. Build the project: `dotnet build`
2. Run it: `dotnet run`
3. Explore the code in Program.cs
4. Complete the exercises
5. Build something amazing!

**Happy coding!** 🚀
