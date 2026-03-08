# Key Takeaways - C# Core Concepts

This document provides comprehensive explanations of the fundamental C# programming concepts demonstrated in this project. Use this as both a learning guide and a reference as you explore C# programming.

---

## Table of Contents

1. [C# Output](#1-c-output)
2. [Comments](#2-comments)
3. [Variables](#3-variables)
4. [Data Types](#4-data-types)
5. [Type Casting](#5-type-casting)
6. [User Input](#6-user-input)
7. [Operators](#7-operators)
8. [Math Operations](#8-math-operations)
9. [Strings](#9-strings)
10. [Booleans](#10-booleans)
11. [If-Else Statements](#11-if-else-statements)
12. [Switch Statements](#12-switch-statements)
13. [While Loops](#13-while-loops)
14. [For Loops](#14-for-loops)
15. [Break and Continue](#15-break-and-continue)
16. [Arrays](#16-arrays)

---

## 1. C# Output

### Console.WriteLine() vs Console.Write()

**Console.WriteLine()** - Writes text and adds a new line:
```csharp
Console.WriteLine("Line 1");
Console.WriteLine("Line 2");
// Output:
// Line 1
// Line 2
```

**Console.Write()** - Writes text without adding a new line:
```csharp
Console.Write("Hello ");
Console.Write("World");
// Output:
// Hello World
```

### When to use each:

- **WriteLine**: Most common usage - each message on its own line
- **Write**: When you want multiple pieces on the same line, or when building output incrementally

### Key Points:

- Both methods accept strings, numbers, and other data types
- The output goes to the "standard output" (usually your terminal/console)
- You can use escape sequences like `\n` (newline) and `\t` (tab) within strings

---

## 2. Comments

Comments are text in your code that the compiler ignores. They're for humans to read and understand the code.

### Single-Line Comments

```csharp
// This is a single-line comment
int age = 25; // Comments can go after code
```

### Multi-Line Comments

```csharp
/*
This is a multi-line comment.
It can span several lines.
Useful for longer explanations.
*/
```

### XML Documentation Comments

```csharp
/// <summary>
/// This method calculates the sum of two numbers.
/// </summary>
/// <param name="a">The first number</param>
/// <param name="b">The second number</param>
/// <returns>The sum of a and b</returns>
public int Add(int a, int b)
{
    return a + b;
}
```

### Best Practices:

- **Explain WHY, not WHAT**: Comment the reason, not the obvious
  ```csharp
  // ❌ Bad: Increment i by 1
  i++;
  
  // ✅ Good: Skip the first element as it's a header
  i++;
  ```

- **Keep comments up-to-date**: Outdated comments are worse than no comments
- **Use XML comments for public methods**: They appear in IntelliSense
- **Don't over-comment**: Clean code often speaks for itself

---

## 3. Variables

Variables are containers that store data values. Think of them as named boxes where you can put information.

### Declaring Variables

```csharp
// Syntax: dataType variableName = value;
int age = 25;
string name = "Alice";
double price = 19.99;
```

### Variable Naming Rules

**Must follow:**
- Start with a letter or underscore: `age`, `_count`
- Can contain letters, digits, underscores: `age1`, `user_name`
- Cannot use C# keywords: `int`, `class`, `string` (as variable names)
- Case-sensitive: `Age` and `age` are different

**Conventions (camelCase for local variables):**
```csharp
int studentAge;          // ✅ Good
int student_age;         // ❌ Avoid (underscores)
int StudentAge;          // ❌ Wrong (PascalCase is for types/methods)
int STUDENT_AGE;         // ❌ Wrong (constants only)
```

### Reassigning Variables

```csharp
int score = 100;
score = 150;        // Reassign to new value
score = score + 50; // Use current value in calculation
```

### Variable Scope

Variables only exist within their scope (the curly braces where they're declared):

```csharp
if (true)
{
    int x = 10;  // x only exists here
    Console.WriteLine(x); // ✅ Works
}
Console.WriteLine(x); // ❌ Error: x doesn't exist here
```

---

## 4. Data Types

Data types specify what kind of data a variable can hold. C# is a **strongly-typed** language, meaning each variable has a specific type.

### Common Value Types

| Type | Description | Size | Range | Example |
|------|-------------|------|-------|---------|
| `int` | Whole numbers | 4 bytes | -2.1B to 2.1B | `123` |
| `long` | Large whole numbers | 8 bytes | -9.2E+18 to 9.2E+18 | `9999999999L` |
| `float` | Decimal numbers | 4 bytes | 7 digits precision | `3.14F` |
| `double` | Decimal numbers | 8 bytes | 15 digits precision | `3.14159` |
| `decimal` | High-precision decimals | 16 bytes | 28-29 digits | `19.99M` |
| `char` | Single character | 2 bytes | Unicode character | `'A'` |
| `bool` | True or false | 1 bit | true/false | `true` |
| `byte` | Small whole number | 1 byte | 0 to 255 | `255` |

### Reference Types

| Type | Description | Example |
|------|-------------|---------|
| `string` | Text (sequence of characters) | `"Hello World"` |
| `object` | Base type for all types | `object obj = 123;` |
| Arrays | Collection of same type | `int[] numbers` |

### Choosing the Right Type

```csharp
// For whole numbers (counts, IDs, etc.)
int studentCount = 30;

// For prices, money (use decimal for accuracy)
decimal productPrice = 19.99M;

// For measurements, scientific calculations
double temperature = 36.7;

// For single characters
char grade = 'A';

// For true/false conditions
bool isStudent = true;

// For text
string name = "Alice";
```

### Type Suffixes

Some types require suffixes to distinguish them:

```csharp
long bigNum = 9999999999L;    // L or l for long
float decimal1 = 3.14F;       // F or f for float
decimal money = 19.99M;       // M or m for decimal
double decimal2 = 3.14;       // No suffix needed (default)
```

---

## 5. Type Casting

Type casting is converting a value from one data type to another.

### Implicit Casting (Automatic)

Happens automatically when converting from smaller to larger types (no data loss):

```csharp
// char -> int -> long -> float -> double
int myInt = 9;
double myDouble = myInt;  // Automatic casting: int to double
Console.WriteLine(myDouble); // 9.0
```

### Explicit Casting (Manual)

Required when converting from larger to smaller types (potential data loss):

```csharp
// double -> float -> long -> int -> char
double myDouble = 9.78;
int myInt = (int)myDouble;  // Manual casting: double to int
Console.WriteLine(myInt);    // 9 (decimal part lost!)
```

### Type Conversion Methods

For safer and more controlled conversions:

```csharp
// Convert using Convert class
string numberString = "123";
int number = Convert.ToInt32(numberString);

// Convert using Parse
int number2 = int.Parse("456");

// Safe conversion with TryParse (recommended)
string input = "789";
if (int.TryParse(input, out int result))
{
    Console.WriteLine($"Converted: {result}");
}
else
{
    Console.WriteLine("Invalid number");
}

// Convert to string
int age = 25;
string ageString = age.ToString();
```

### Common Pitfalls

```csharp
// ❌ Information loss
double price = 19.99;
int dollars = (int)price;  // dollars = 19 (cents lost!)

// ❌ Overflow
int bigNum = 999999999;
byte smallNum = (byte)bigNum; // Unexpected result due to overflow

// ✅ Better approach
double price = 19.99;
int dollars = (int)Math.Round(price); // Round first
```

---

## 6. User Input

Reading input from the user makes programs interactive.

### Console.ReadLine()

Returns the user's input as a string:

```csharp
Console.Write("Enter your name: ");
string? name = Console.ReadLine();
Console.WriteLine($"Hello, {name}!");
```

### The `?` in `string?`

In C# 10 with nullable reference types enabled:
- `string?` means the value can be null
- `Console.ReadLine()` can return null if there's no input
- The `?` acknowledges this possibility

### Reading Numbers

User input is always a string, so convert it:

```csharp
Console.Write("Enter your age: ");
string? ageInput = Console.ReadLine();

// Safe conversion
if (int.TryParse(ageInput, out int age))
{
    Console.WriteLine($"You are {age} years old");
}
else
{
    Console.WriteLine("Invalid age");
}
```

### Reading Multiple Values

```csharp
Console.Write("Enter first number: ");
int num1 = Convert.ToInt32(Console.ReadLine());

Console.Write("Enter second number: ");
int num2 = Convert.ToInt32(Console.ReadLine());

int sum = num1 + num2;
Console.WriteLine($"Sum: {sum}");
```

### Reading Single Characters

```csharp
Console.Write("Press any key: ");
char key = Console.ReadKey().KeyChar;
Console.WriteLine($"\nYou pressed: {key}");
```

---

## 7. Operators

Operators perform operations on variables and values.

### Arithmetic Operators

```csharp
int a = 10;
int b = 3;

Console.WriteLine(a + b);  // 13 (addition)
Console.WriteLine(a - b);  // 7  (subtraction)
Console.WriteLine(a * b);  // 30 (multiplication)
Console.WriteLine(a / b);  // 3  (division - integer division!)
Console.WriteLine(a % b);  // 1  (modulus - remainder)
```

**Integer Division Trap:**
```csharp
int result1 = 10 / 3;      // 3 (integer division)
double result2 = 10.0 / 3; // 3.333... (floating-point division)
```

### Increment and Decrement

```csharp
int x = 5;

x++;    // Increment: x = x + 1 (now 6)
x--;    // Decrement: x = x - 1 (now 5)

// Prefix vs Postfix
int a = 5;
int b = ++a;  // Increment THEN assign: a=6, b=6
int c = a++;  // Assign THEN increment: a=7, c=6
```

### Assignment Operators

```csharp
int x = 10;

x += 5;  // x = x + 5  (now 15)
x -= 3;  // x = x - 3  (now 12)
x *= 2;  // x = x * 2  (now 24)
x /= 4;  // x = x / 4  (now 6)
x %= 4;  // x = x % 4  (now 2)
```

### Comparison Operators

Return `true` or `false`:

```csharp
int a = 10;
int b = 5;

a == b   // false (equal to)
a != b   // true  (not equal to)
a > b    // true  (greater than)
a < b    // false (less than)
a >= 10  // true  (greater than or equal)
b <= 5   // true  (less than or equal)
```

### Logical Operators

Combine boolean expressions:

```csharp
bool isStudent = true;
bool hasID = false;

// AND (&&) - both must be true
bool canEnter = isStudent && hasID;  // false

// OR (||) - at least one must be true
bool canDiscount = isStudent || hasID;  // true

// NOT (!) - inverts the value
bool notStudent = !isStudent;  // false
```

**Short-Circuit Evaluation:**
```csharp
// && stops if first is false (second not evaluated)
if (age >= 18 && hasLicense())  // hasLicense() not called if age < 18

// || stops if first is true (second not evaluated)
if (isAdmin || hasPermission())  // hasPermission() not called if isAdmin
```

---

## 8. Math Operations

The `Math` class provides mathematical functions.

### Common Math Methods

```csharp
// Maximum and Minimum
Math.Max(10, 20);     // 20
Math.Min(10, 20);     // 10

// Absolute value
Math.Abs(-15);        // 15

// Square root
Math.Sqrt(64);        // 8.0

// Power (exponentiation)
Math.Pow(2, 3);       // 8.0 (2³ = 8)

// Rounding
Math.Round(4.7);      // 5.0
Math.Round(4.5);      // 4.0 (banker's rounding)
Math.Round(4.6);      // 5.0

// Floor and Ceiling
Math.Floor(4.7);      // 4.0 (round down)
Math.Ceiling(4.3);    // 5.0 (round up)

// Truncate (remove decimal part)
Math.Truncate(4.9);   // 4.0
```

### Trigonometric Functions

```csharp
Math.Sin(angle);      // Sine
Math.Cos(angle);      // Cosine
Math.Tan(angle);      // Tangent
```

### Constants

```csharp
Math.PI;              // 3.14159265358979
Math.E;               // 2.71828182845905
```

### Practical Examples

```csharp
// Calculate circle area
double radius = 5;
double area = Math.PI * Math.Pow(radius, 2);

// Distance between two points
double distance = Math.Sqrt(Math.Pow(x2-x1, 2) + Math.Pow(y2-y1, 2));

// Random numbers (using Random class, not Math)
Random random = new Random();
int dice = random.Next(1, 7);  // 1 to 6
```

---

## 9. Strings

Strings are sequences of characters used to store text.

### String Declaration

```csharp
string name = "Alice";
string message = "Hello, World!";
string empty = "";
string? nullable = null;
```

### String Concatenation

```csharp
string first = "Hello";
string second = "World";

// Method 1: + operator
string result1 = first + " " + second;

// Method 2: String.Concat
string result2 = string.Concat(first, " ", second);
```

### String Interpolation (Preferred)

```csharp
string name = "Alice";
int age = 25;

// Readable and efficient
string message = $"My name is {name} and I'm {age} years old";

// With expressions
string info = $"Next year, I'll be {age + 1}";

// With formatting
decimal price = 19.99M;
string formatted = $"Price: ${price:F2}";  // $19.99
```

### Accessing Characters

Strings are arrays of characters:

```csharp
string text = "Hello";

char first = text[0];     // 'H' (index starts at 0)
char last = text[4];      // 'o'
int length = text.Length; // 5

// ❌ Can't modify individual characters (strings are immutable)
// text[0] = 'h';  // Compilation error!
```

### Common String Methods

```csharp
string text = "Hello World";

// Case conversion
text.ToUpper();           // "HELLO WORLD"
text.ToLower();           // "hello world"

// Substring
text.Substring(0, 5);     // "Hello"
text.Substring(6);        // "World"

// Searching
text.Contains("World");   // true
text.IndexOf("World");    // 6
text.StartsWith("Hello"); // true
text.EndsWith("World");   // true

// Replacing
text.Replace("World", "C#");  // "Hello C#"

// Splitting
string csv = "apple,banana,orange";
string[] fruits = csv.Split(',');  // ["apple", "banana", "orange"]

// Trimming whitespace
string padded = "  text  ";
padded.Trim();            // "text"
```

### Special Characters (Escape Sequences)

```csharp
"\n"   // New line
"\t"   // Tab
"\\"   // Backslash
"\""   // Double quote
"\'"   // Single quote

// Example:
string path = "C:\\Users\\Alice\\Documents";
string quote = "He said \"Hello\"";
```

### Verbatim Strings

Use `@` to treat backslashes literally:

```csharp
// Regular string (need to escape)
string path1 = "C:\\Users\\Alice\\Documents";

// Verbatim string (no escaping needed)
string path2 = @"C:\Users\Alice\Documents";

// Multi-line verbatim string
string multiLine = @"Line 1
Line 2
Line 3";
```

### String Immutability

Strings in C# are immutable (can't be changed):

```csharp
string text = "Hello";
text.ToUpper();  // Creates NEW string, doesn't modify original
Console.WriteLine(text);  // Still "Hello"

// To modify, assign the result
text = text.ToUpper();  // Now text is "HELLO"
```

---

## 10. Booleans

Boolean values represent `true` or `false`. Essential for decision-making in programs.

### Boolean Variables

```csharp
bool isStudent = true;
bool hasPass = false;
```

### Boolean Expressions

Expressions that evaluate to true or false:

```csharp
int age = 18;
bool isAdult = age >= 18;  // true

int score = 85;
bool passed = score >= 60;  // true

string name = "Alice";
bool isEmpty = name.Length == 0;  // false
```

### Using Booleans in Conditions

```csharp
bool isRaining = true;

if (isRaining)
{
    Console.WriteLine("Take an umbrella");
}

// Equivalent to:
if (isRaining == true)  // Redundant, avoid this style
{
    Console.WriteLine("Take an umbrella");
}
```

### Boolean Logic

```csharp
bool hasTicket = true;
bool hasID = true;
bool isAdult = true;

// Must have both ticket AND ID
bool canEnter = hasTicket && hasID;

// Must be adult OR have guardian
bool canWatch = isAdult || hasGuardian;

// Invert a boolean
bool isChild = !isAdult;
```

---

## 11. If-Else Statements

Control the flow of your program based on conditions.

### Basic If Statement

```csharp
if (condition)
{
    // Code runs if condition is true
}
```

### If-Else

```csharp
int age = 17;

if (age >= 18)
{
    Console.WriteLine("You can vote");
}
else
{
    Console.WriteLine("You cannot vote yet");
}
```

### If-Else If-Else

```csharp
int score = 85;

if (score >= 90)
{
    Console.WriteLine("Grade: A");
}
else if (score >= 80)
{
    Console.WriteLine("Grade: B");
}
else if (score >= 70)
{
    Console.WriteLine("Grade: C");
}
else if (score >= 60)
{
    Console.WriteLine("Grade: D");
}
else
{
    Console.WriteLine("Grade: F");
}
```

### Nested If Statements

```csharp
bool hasAccount = true;
string password = "correct123";

if (hasAccount)
{
    if (password == "correct123")
    {
        Console.WriteLine("Login successful");
    }
    else
    {
        Console.WriteLine("Wrong password");
    }
}
else
{
    Console.WriteLine("No account found");
}
```

### Ternary Operator (Shorthand)

For simple if-else:

```csharp
// Syntax: condition ? valueIfTrue : valueIfFalse

int age = 20;
string status = (age >= 18) ? "Adult" : "Minor";

// Equivalent to:
string status;
if (age >= 18)
{
    status = "Adult";
}
else
{
    status = "Minor";
}
```

### Best Practices

```csharp
// ✅ Good: Check specific conditions first
if (score >= 90)      // Most specific
else if (score >= 80)
else if (score >= 70)

// ❌ Bad: General condition first prevents others from running
if (score >= 60)      // Too general, always matches first!
else if (score >= 90)  // Never reached!
```

---

## 12. Switch Statements

An alternative to multiple if-else statements when checking one variable against multiple values.

### Basic Switch

```csharp
int day = 3;

switch (day)
{
    case 1:
        Console.WriteLine("Monday");
        break;
    case 2:
        Console.WriteLine("Tuesday");
        break;
    case 3:
        Console.WriteLine("Wednesday");
        break;
    default:
        Console.WriteLine("Other day");
        break;
}
```

### The `break` Statement

Essential to prevent "fall-through":

```csharp
// ❌ Without break (not allowed in C#)
switch (day)
{
    case 1:
        Console.WriteLine("Monday");
        // Error: missing break!
    case 2:
        Console.WriteLine("Tuesday");
        break;
}

// ✅ With break
switch (day)
{
    case 1:
        Console.WriteLine("Monday");
        break;  // Exit the switch
    case 2:
        Console.WriteLine("Tuesday");
        break;
}
```

### Multiple Cases with Same Action

```csharp
int month = 12;

switch (month)
{
    case 12:
    case 1:
    case 2:
        Console.WriteLine("Winter");
        break;
    case 3:
    case 4:
    case 5:
        Console.WriteLine("Spring");
        break;
    case 6:
    case 7:
    case 8:
        Console.WriteLine("Summer");
        break;
    case 9:
    case 10:
    case 11:
        Console.WriteLine("Fall");
        break;
    default:
        Console.WriteLine("Invalid month");
        break;
}
```

### Switch with Strings

```csharp
string command = "start";

switch (command)
{
    case "start":
        Console.WriteLine("Starting...");
        break;
    case "stop":
        Console.WriteLine("Stopping...");
        break;
    case "pause":
        Console.WriteLine("Pausing...");
        break;
    default:
        Console.WriteLine("Unknown command");
        break;
}
```

### When to Use Switch vs If-Else

**Use Switch when:**
- Checking one variable against multiple specific values
- Values are constants (numbers, strings, enums)
- Makes code more readable than many if-else

**Use If-Else when:**
- Checking different variables
- Using complex conditions (ranges, multiple conditions)
- Need more flexibility

```csharp
// ✅ Good use of switch
switch (dayOfWeek)
{
    case "Monday":
    case "Tuesday":
    // ...
}

// ✅ Better with if-else (range checking)
if (age < 13)
    category = "Child";
else if (age < 20)
    category = "Teen";
else if (age < 65)
    category = "Adult";
else
    category = "Senior";
```

---

## 13. While Loops

Loops repeat code multiple times. While loops continue while a condition is true.

### While Loop

Checks condition **before** running:

```csharp
int i = 1;
while (i <= 5)
{
    Console.WriteLine(i);
    i++;  // Don't forget to update!
}
// Output: 1 2 3 4 5
```

### Do-While Loop

Runs once, **then** checks condition:

```csharp
int i = 1;
do
{
    Console.WriteLine(i);
    i++;
} while (i <= 5);
// Output: 1 2 3 4 5
```

### Difference Between While and Do-While

```csharp
// While: might not run at all
int x = 10;
while (x < 5)
{
    Console.WriteLine(x);  // Never runs
}

// Do-While: runs at least once
int y = 10;
do
{
    Console.WriteLine(y);  // Runs once: prints 10
} while (y < 5);
```

### Common Patterns

**Counter loop:**
```csharp
int count = 0;
while (count < 10)
{
    Console.WriteLine(count);
    count++;
}
```

**Sentinel value:**
```csharp
string input = "";
while (input != "quit")
{
    Console.Write("Enter command (or 'quit'): ");
    input = Console.ReadLine();
}
```

**Infinite loop (with break):**
```csharp
while (true)
{
    Console.Write("Continue? (y/n): ");
    string answer = Console.ReadLine();
    
    if (answer == "n")
        break;  // Exit the loop
}
```

### ⚠️ Common Mistakes

**Infinite loop (forgot to update):**
```csharp
int i = 1;
while (i <= 5)
{
    Console.WriteLine(i);
    // ❌ Forgot i++; - loops forever!
}
```

**Off-by-one error:**
```csharp
// ❌ Runs 6 times (0, 1, 2, 3, 4, 5)
int i = 0;
while (i <= 5)
{
    Console.WriteLine(i);
    i++;
}

// ✅ Runs 5 times (0, 1, 2, 3, 4)
int i = 0;
while (i < 5)
{
    Console.WriteLine(i);
    i++;
}
```

---

## 14. For Loops

For loops are ideal when you know how many times to repeat.

### Basic For Loop

```csharp
// Syntax: for (initialization; condition; update)
for (int i = 0; i < 5; i++)
{
    Console.WriteLine(i);
}
// Output: 0 1 2 3 4
```

### How For Loops Work

1. **Initialization**: `int i = 0` - runs once at start
2. **Condition**: `i < 5` - checked before each iteration
3. **Body**: `Console.WriteLine(i)` - runs if condition is true
4. **Update**: `i++` - runs after each iteration
5. Repeat from step 2

### Common Patterns

**Count up:**
```csharp
for (int i = 1; i <= 10; i++)
{
    Console.WriteLine(i);  // 1 to 10
}
```

**Count down:**
```csharp
for (int i = 10; i >= 1; i--)
{
    Console.WriteLine(i);  // 10 to 1
}
```

**Step by 2:**
```csharp
for (int i = 0; i <= 10; i += 2)
{
    Console.WriteLine(i);  // 0 2 4 6 8 10
}
```

**Iterate through array:**
```csharp
string[] names = { "Alice", "Bob", "Charlie" };
for (int i = 0; i < names.Length; i++)
{
    Console.WriteLine($"{i}: {names[i]}");
}
```

### Nested For Loops

Loops within loops:

```csharp
// Multiplication table
for (int i = 1; i <= 5; i++)
{
    for (int j = 1; j <= 5; j++)
    {
        Console.Write($"{i * j}\t");
    }
    Console.WriteLine();
}
// Output:
// 1  2  3  4  5
// 2  4  6  8  10
// 3  6  9  12 15
// 4  8  12 16 20
// 5  10 15 20 25
```

### Foreach Loop

Simpler way to iterate through collections:

```csharp
string[] fruits = { "Apple", "Banana", "Orange" };

// For loop
for (int i = 0; i < fruits.Length; i++)
{
    Console.WriteLine(fruits[i]);
}

// Foreach loop (cleaner)
foreach (string fruit in fruits)
{
    Console.WriteLine(fruit);
}
```

### For vs While

**Use for when:**
- You know the number of iterations
- Counting or indexing through a range
- Iterating through arrays/collections

**Use while when:**
- Number of iterations is unknown
- Looping based on a condition
- Reading until end of file/input

```csharp
// ✅ Good use of for (known iterations)
for (int i = 0; i < 100; i++)
{
    // Process i
}

// ✅ Good use of while (unknown iterations)
while (!endOfFile)
{
    line = ReadNextLine();
}
```

---

## 15. Break and Continue

Control statements that modify loop behavior.

### Break Statement

Exits the loop immediately:

```csharp
// Find first even number
for (int i = 1; i <= 10; i++)
{
    if (i % 2 == 0)
    {
        Console.WriteLine($"First even: {i}");
        break;  // Exit the loop
    }
}
// Output: First even: 2
```

### Continue Statement

Skips the rest of the current iteration:

```csharp
// Print odd numbers only
for (int i = 1; i <= 10; i++)
{
    if (i % 2 == 0)
    {
        continue;  // Skip to next iteration
    }
    Console.WriteLine(i);
}
// Output: 1 3 5 7 9
```

### Break vs Continue

```csharp
// Break: stops the loop entirely
for (int i = 1; i <= 5; i++)
{
    if (i == 3)
        break;
    Console.WriteLine(i);
}
// Output: 1 2

// Continue: skips current iteration, continues loop
for (int i = 1; i <= 5; i++)
{
    if (i == 3)
        continue;
    Console.WriteLine(i);
}
// Output: 1 2 4 5
```

### Practical Examples

**Search and exit:**
```csharp
string[] names = { "Alice", "Bob", "Charlie", "David" };
string searchName = "Charlie";
bool found = false;

for (int i = 0; i < names.Length; i++)
{
    if (names[i] == searchName)
    {
        Console.WriteLine($"Found {searchName} at index {i}");
        found = true;
        break;  // No need to keep searching
    }
}

if (!found)
{
    Console.WriteLine($"{searchName} not found");
}
```

**Skip invalid data:**
```csharp
int[] scores = { 95, -1, 87, 0, 92, -1, 88 };

for (int i = 0; i < scores.Length; i++)
{
    if (scores[i] < 0)
    {
        continue;  // Skip invalid scores
    }
    
    Console.WriteLine($"Score: {scores[i]}");
}
```

### Break in Nested Loops

Break only exits the innermost loop:

```csharp
for (int i = 1; i <= 3; i++)
{
    for (int j = 1; j <= 3; j++)
    {
        if (j == 2)
            break;  // Only breaks inner loop
        Console.WriteLine($"{i},{j}");
    }
}
// Output: 1,1  2,1  3,1
```

---

## 16. Arrays

Arrays store multiple values of the same type in a single variable.

### Declaring and Initializing Arrays

```csharp
// Method 1: Declare with values
string[] fruits = { "Apple", "Banana", "Orange" };

// Method 2: Declare size, add values later
int[] numbers = new int[5];
numbers[0] = 10;
numbers[1] = 20;
// ... etc

// Method 3: Declare and initialize
double[] prices = new double[] { 19.99, 29.99, 39.99 };
```

### Accessing Array Elements

Arrays use zero-based indexing:

```csharp
string[] fruits = { "Apple", "Banana", "Orange" };

string first = fruits[0];   // "Apple"
string second = fruits[1];  // "Banana"
string third = fruits[2];   // "Orange"

// Modify an element
fruits[1] = "Mango";  // Now: ["Apple", "Mango", "Orange"]
```

### Array Length

```csharp
string[] fruits = { "Apple", "Banana", "Orange" };
int count = fruits.Length;  // 3

// Use Length in loops
for (int i = 0; i < fruits.Length; i++)
{
    Console.WriteLine(fruits[i]);
}
```

### Looping Through Arrays

**For loop (with index):**
```csharp
string[] names = { "Alice", "Bob", "Charlie" };

for (int i = 0; i < names.Length; i++)
{
    Console.WriteLine($"{i}: {names[i]}");
}
// Output:
// 0: Alice
// 1: Bob
// 2: Charlie
```

**Foreach loop (no index):**
```csharp
foreach (string name in names)
{
    Console.WriteLine(name);
}
// Output:
// Alice
// Bob
// Charlie
```

### Common Array Operations

**Find element:**
```csharp
int[] numbers = { 10, 20, 30, 40, 50 };

// Find if element exists
bool contains = Array.Exists(numbers, x => x == 30);  // true

// Find index
int index = Array.IndexOf(numbers, 30);  // 2
```

**Sort array:**
```csharp
int[] numbers = { 50, 10, 40, 20, 30 };
Array.Sort(numbers);
// Now: { 10, 20, 30, 40, 50 }
```

**Reverse array:**
```csharp
int[] numbers = { 1, 2, 3, 4, 5 };
Array.Reverse(numbers);
// Now: { 5, 4, 3, 2, 1 }
```

**Calculate sum:**
```csharp
int[] numbers = { 10, 20, 30, 40, 50 };
int sum = 0;

foreach (int num in numbers)
{
    sum += num;
}
Console.WriteLine($"Sum: {sum}");  // 150
```

**Find maximum:**
```csharp
int[] numbers = { 10, 50, 30, 20, 40 };
int max = numbers[0];

foreach (int num in numbers)
{
    if (num > max)
    {
        max = num;
    }
}
Console.WriteLine($"Max: {max}");  // 50

// Or use built-in method:
int max2 = numbers.Max();
```

### Multi-Dimensional Arrays

Arrays within arrays:

```csharp
// 2D array (matrix)
int[,] matrix = new int[2, 3]
{
    { 1, 2, 3 },
    { 4, 5, 6 }
};

// Access elements
int value = matrix[0, 1];  // 2 (row 0, column 1)
int value2 = matrix[1, 2]; // 6 (row 1, column 2)

// Loop through 2D array
for (int row = 0; row < matrix.GetLength(0); row++)
{
    for (int col = 0; col < matrix.GetLength(1); col++)
    {
        Console.Write($"{matrix[row, col]} ");
    }
    Console.WriteLine();
}
// Output:
// 1 2 3
// 4 5 6
```

### Array Limitations

1. **Fixed size**: Can't change size after creation
2. **Single type**: All elements must be same type
3. **No built-in methods**: Limited operations

For more flexibility, consider using `List<T>` (covered in advanced topics).

### Common Mistakes

**Index out of bounds:**
```csharp
int[] numbers = { 10, 20, 30 };

// ❌ Error: Array has 3 elements (indices 0, 1, 2)
int value = numbers[3];  // IndexOutOfRangeException!

// ✅ Safe: Check bounds
if (index >= 0 && index < numbers.Length)
{
    int value = numbers[index];
}
```

**Forgetting Length-1:**
```csharp
int[] numbers = { 10, 20, 30 };

// ❌ Wrong: Loop runs 0,1,2,3 (3 is out of bounds!)
for (int i = 0; i <= numbers.Length; i++)

// ✅ Correct: Loop runs 0,1,2
for (int i = 0; i < numbers.Length; i++)
```

---

## Summary

Congratulations on learning the core concepts of C#! Here's what you've covered:

✅ **Output**: `Console.WriteLine()` and `Console.Write()`  
✅ **Comments**: Single-line, multi-line, and XML documentation  
✅ **Variables**: Declaring, assigning, and using variables  
✅ **Data Types**: int, double, char, string, bool, and more  
✅ **Type Casting**: Converting between types  
✅ **User Input**: Reading from the console  
✅ **Operators**: Arithmetic, comparison, logical operators  
✅ **Math**: Mathematical functions and operations  
✅ **Strings**: Text manipulation and formatting  
✅ **Booleans**: True/false logic  
✅ **If-Else**: Decision-making in code  
✅ **Switch**: Multi-way branching  
✅ **While Loops**: Condition-based repetition  
✅ **For Loops**: Counter-based repetition  
✅ **Break/Continue**: Loop control  
✅ **Arrays**: Collections of data  

These fundamentals form the foundation for all C# programming. Master these concepts, and you'll be ready to build real applications!

## Next Steps

1. **Practice**: Build small programs using these concepts
2. **Experiment**: Modify the demo code and observe results
3. **Challenge yourself**: Try the exercises in [README.md](../README.md)
4. **Learn more**: Explore object-oriented programming, classes, and methods
5. **Build projects**: Create calculators, games, or data processors

Happy coding! 🚀
