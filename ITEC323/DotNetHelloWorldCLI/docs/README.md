# .NET and CLI Applications

This document provides an introduction to .NET and Command-Line Interface (CLI) applications.

## What is .NET?

.NET is a free, open-source, cross-platform framework developed by Microsoft for building modern applications. It supports multiple programming languages, with C# being the most popular.

### Key Features of .NET

- **Cross-Platform**: Run on Windows, macOS, and Linux
- **Open Source**: Free to use with an active community
- **High Performance**: Optimized for speed and efficiency
- **Modern**: Built with modern development practices in mind

## What is a CLI Application?

A Command-Line Interface (CLI) application is a program that runs in a terminal or command prompt. Users interact with CLI applications by typing commands rather than using a graphical user interface (GUI).

### Advantages of CLI Applications

- **Lightweight**: No graphical overhead
- **Automation**: Easy to script and automate
- **Remote Access**: Can run on servers without displays
- **Fast Development**: Quick to build and test

## Console Applications in .NET

Console applications are the simplest type of .NET application. They:

- Run in a terminal window
- Use `Console.WriteLine()` to display output
- Use `Console.ReadLine()` to receive input
- Execute from top to bottom
- Exit when the program completes

### Basic Structure

A minimal .NET console application looks like this:

```csharp
// This is a simple console application
Console.WriteLine("Hello, World!");
```

With .NET 6 and later, this uses "top-level statements" which simplifies the code by removing boilerplate.

### The Traditional Format

Before .NET 6, console applications used this longer format:

```csharp
using System;

namespace MyApp
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello, World!");
        }
    }
}
```

## Running .NET CLI Applications

Use the `dotnet` command-line tool:

- `dotnet new console`: Create a new console project
- `dotnet build`: Compile the application
- `dotnet run`: Run the application
- `dotnet --version`: Check .NET version

## Learn More

- Official .NET Documentation: https://docs.microsoft.com/dotnet
- C# Programming Guide: https://docs.microsoft.com/dotnet/csharp
- .NET CLI Overview: https://docs.microsoft.com/dotnet/core/tools
