# Create a new project 

Mirror the project structure and files from `ITEC323/03.CSharp/01.HelloWorldCLI` to create a new C# console application.

Save your response to `ITEC323/03.CSharp/02.CSharpCore` 

The project should include:
- README.md: Introduce the project
- QUICKSTART.md: How to build and run
- docs/Key-Takeaways.md: Important concepts and learning points

Acceptance Criteria:
- The new project is created with the same structure as `01.HelloWorldCLI`
- `dotnet build` and `dotnet run` work successfully in the new project

Other requirements:
- The target audience for this project is beginners learning C# for the first time
- Comment your code to explain key concepts and steps
- Use simple language in documentation to ensure it's accessible to all learners
- Ensure the project is compatible with .NET 10.0 and can be built and run on multiple platforms (Windows, macOS, Linux)

The purpose of this project is to create a new C# console application that serves as a foundation for learning core C# concepts. This project will be structured similarly to the "Hello World" CLI application, but will focus on introducing fundamental C# programming constructs such as variables, data types, operators, control flow, and basic input/output.

The project demonstrates the following key concepts:


C# Output

Console.WriteLine("Hello World!");
Console.Write("Hello World! ");

Single-line Comments
C# Multi-line Comments

C# Variables

In C#, there are different types of variables (defined with different keywords), for example:

int - stores integers (whole numbers), without decimals, such as 123 or -123
double - stores floating point numbers, with decimals, such as 19.99 or -19.99
char - stores single characters, such as 'a' or 'B'. Char values are surrounded by single quotes
string - stores text, such as "Hello World". String values are surrounded by double quotes
bool - stores values with two states: true or false

C# Data Types
int myNum = 5;               // Integer (whole number)
double myDoubleNum = 5.99D;  // Floating point number
char myLetter = 'D';         // Character
bool myBool = true;          // Boolean
string myText = "Hello";     // String


C# Type Casting
Implicit Casting (automatically) - converting a smaller type to a larger type size
char -> int -> long -> float -> double

Explicit Casting (manually) - converting a larger type to a smaller size type
double -> float -> long -> int -> char

C# User Input
Console.ReadLine()

C# Operators
+ - * / % ++ --
Assignment Operators
C# Comparison Operators
C# Logical Operators
 && || !
 
C# Math
Math.Max(x,y)
Math.Min(x,y)
Math.Sqrt(x)
...


C# Strings
Concat
interpolation
access string
special characters

C# Booleans
bool true false

C# If ... Else
C# Conditions and If Statements

C# Switch

C# While Loop

C# For Loop

C# Break and Continue

C# Arrays
