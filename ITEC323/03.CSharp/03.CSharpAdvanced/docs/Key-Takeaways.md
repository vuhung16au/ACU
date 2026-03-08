# Key Takeaways - C# Advanced Concepts

**Project**: `03.CSharp/03.CSharpAdvanced`  
**Course**: ITEC323 - Software Technologies  
**Last Updated**: March 8, 2026

---

## 📖 Overview

This document provides **in-depth explanations** of the 14 advanced C# concepts demonstrated in this project. Each section includes:

- **Concept explanation** - What it is and why it matters
- **Code examples** - Practical demonstrations
- **Best practices** - How to use it correctly
- **Common mistakes** - What to avoid
- **When to use** - Real-world scenarios

This is your comprehensive reference guide for mastering advanced C# programming.

---

## Table of Contents

1. [Methods](#1-methods)
2. [Method Parameters](#2-method-parameters)
3. [Method Overloading](#3-method-overloading)
4. [Classes and Objects](#4-classes-and-objects)
5. [Constructors](#5-constructors)
6. [Access Modifiers](#6-access-modifiers)
7. [Properties (Get and Set)](#7-properties-get-and-set)
8. [Inheritance](#8-inheritance)
9. [Polymorphism](#9-polymorphism)
10. [Abstraction](#10-abstraction)
11. [Interfaces](#11-interfaces)
12. [Enums](#12-enums)
13. [File Operations](#13-file-operations)
14. [Exception Handling](#14-exception-handling)

---

## 1. Methods

### What Are Methods?

**Methods** are reusable blocks of code that perform a specific task. They help organize code, reduce repetition, and make programs easier to understand and maintain.

Think of methods as **mini-programs within your program**.

### Method Structure

```csharp
/// <summary>
/// Documentation comment
/// </summary>
[access modifier] [return type] MethodName([parameters])
{
    // Method body
    // Code to execute
    return value; // If return type is not void
}
```

### Types of Methods

#### 1. Void Methods (No Return Value)

```csharp
static void PrintWelcomeMessage()
{
    Console.WriteLine("Welcome to C#!");
}

// Usage
PrintWelcomeMessage(); // Just call it
```

#### 2. Methods with Return Values

```csharp
static int Add(int x, int y)
{
    return x + y;
}

// Usage
int result = Add(5, 3);  // result = 8
Console.WriteLine(result);
```

#### 3. Methods with Parameters

```csharp
static string GetGreeting(string name)
{
    return $"Hello, {name}!";
}

// Usage
string greeting = GetGreeting("Alice");
Console.WriteLine(greeting); // "Hello, Alice!"
```

### Why Use Methods?

✅ **Code Reusability** - Write once, use many times  
✅ **Organization** - Break complex logic into smaller pieces  
✅ **Readability** - Clear method names explain what code does  
✅ **Maintainability** - Fix bugs in one place  
✅ **Testing** - Test individual methods independently  

### Best Practices

✅ **Naming**: Use verbs (Add, Calculate, Print, Get)  
✅ **Single Responsibility**: Each method does one thing well  
✅ **Keep it short**: Ideally 10-20 lines  
✅ **Parameters**: Pass only what's needed  
✅ **Documentation**: Add XML comments for public methods  

### Common Mistakes

❌ **Methods too long** - Hard to understand and maintain  
❌ **Unclear names** - `DoStuff()` doesn't explain purpose  
❌ **Too many parameters** - More than 3-4 is a code smell  
❌ **No return type when needed** - Should return a value for reusability  

### When to Create a Method

- Code is used more than once
- A section of code has a clear, specific purpose
- Code block is more than 5-10 lines
- You want to test a specific functionality

---

## 2. Method Parameters

### What Are Parameters?

**Parameters** allow methods to accept input data. They make methods flexible and reusable with different values.

### Types of Parameters

#### 1. Value Parameters (Default)

Data is **copied** to the method. Changes inside the method don't affect the original variable.

```csharp
static void ModifyByValue(int number)
{
    number = 100; // Only modifies the copy
}

int x = 5;
ModifyByValue(x);
Console.WriteLine(x); // Still 5 (unchanged)
```

#### 2. Reference Parameters (`ref` keyword)

The method receives a **reference** to the original variable. Changes affect the original.

```csharp
static void ModifyByRef(ref int number)
{
    number = 100; // Modifies the original
}

int x = 5;
ModifyByRef(ref x);
Console.WriteLine(x); // Now 100 (changed!)
```

**Requirements**:
- Variable must be initialized before passing
- Use `ref` keyword both in method signature AND at call site

#### 3. Output Parameters (`out` keyword)

Similar to `ref`, but the method **must** assign a value before returning. Used to return multiple values.

```csharp
static void MultiplyByTwo(int input, out int result)
{
    result = input * 2; // Must assign before returning
}

int value = 5;
int doubled;
MultiplyByTwo(value, out doubled);
Console.WriteLine(doubled); // 10
```

**Differences from `ref`**:
- Variable doesn't need to be initialized before passing
- Method must assign a value to the `out` parameter

#### 4. Optional Parameters (Default Values)

Parameters can have default values, making them optional when calling the method.

```csharp
static void Greet(string name = "Guest", string greeting = "Hello")
{
    Console.WriteLine($"{greeting}, {name}!");
}

// Usage
Greet();                      // "Hello, Guest!"
Greet("Alice");              // "Hello, Alice!"
Greet("Bob", "Hi");          // "Hi, Bob!"
```

**Rules**:
- Optional parameters must come **after** required parameters
- Syntax: `type parameterName = defaultValue`

### Parameter Passing - Visual Comparison

| Feature | Value | `ref` | `out` |
|---------|-------|-------|-------|
| **Copies value** | ✅ Yes | ❌ No | ❌ No |
| **Modifies original** | ❌ No | ✅ Yes | ✅ Yes |
| **Must initialize before** | ✅ Yes | ✅ Yes | ❌ No |
| **Must assign in method** | ❌ No | ❌ No | ✅ Yes |
| **Keyword at call site** | ❌ No | ✅ `ref` | ✅ `out` |

### Best Practices

✅ **Use value parameters by default** - Safest option  
✅ **Use `ref` sparingly** - Only when you need to modify the original  
✅ **Use `out` for multiple return values** - Better than returning arrays  
✅ **Use optional parameters** - Makes APIs more flexible  
✅ **Document behavior** - Clearly state if method modifies parameters  

### Common Mistakes

❌ Using `ref` when value parameters would work  
❌ Forgetting `ref`/`out` keyword at call site  
❌ Not assigning `out` parameters in all code paths  
❌ Too many optional parameters (3-4 max recommended)  

### When to Use Each Type

**Value Parameters**: Most common use case, safe default choice  
**`ref` Parameters**: Need to modify existing variable (rare)  
**`out` Parameters**: Return multiple values from a method  
**Optional Parameters**: Provide sensible defaults for flexibility  

---

## 3. Method Overloading

### What Is Method Overloading?

**Method overloading** allows multiple methods to have the **same name** but different **signatures** (different parameter types, number, or order).

The compiler chooses which method to call based on the arguments you provide.

### How It Works

```csharp
class Calculator
{
    // Overload 1: Two integers
    static int Calculate(int a, int b)
    {
        return a + b;
    }
    
    // Overload 2: Two doubles
    static double Calculate(double a, double b)
    {
        return a + b;
    }
    
    // Overload 3: Three integers
    static int Calculate(int a, int b, int c)
    {
        return a + b + c;
    }
}

// Usage
int result1 = Calculate(5, 3);           // Calls overload 1 → 8
double result2 = Calculate(2.5, 3.7);    // Calls overload 2 → 6.2
int result3 = Calculate(1, 2, 3);        // Calls overload 3 → 6
```

### Method Signature Components

A method signature consists of:
1. **Method name**
2. **Number of parameters**
3. **Type of parameters**
4. **Order of parameters**

**Note**: Return type is NOT part of the signature!

### Valid Overloading

✅ Different number of parameters:
```csharp
void Print(string message) { }
void Print(string message, int times) { }
```

✅ Different parameter types:
```csharp
void Display(int number) { }
void Display(string text) { }
```

✅ Different parameter order:
```csharp
void Show(int number, string text) { }
void Show(string text, int number) { }
```

### Invalid Overloading

❌ Only return type differs:
```csharp
int Calculate(int a, int b) { }
double Calculate(int a, int b) { } // ERROR: Same signature!
```

❌ Only parameter names differ:
```csharp
void Print(string message) { }
void Print(string text) { } // ERROR: Same signature!
```

### Benefits of Overloading

✅ **Intuitive API** - Same method name for similar operations  
✅ **Flexibility** - Handle different data types  
✅ **Readability** - Clear and consistent naming  
✅ **Convenience** - Don't need to remember different method names  

### Real-World Example

```csharp
// Console.WriteLine is overloaded for many types!
Console.WriteLine(42);           // int version
Console.WriteLine(3.14);         // double version
Console.WriteLine("Hello");      // string version
Console.WriteLine(true);         // bool version
```

### Best Practices

✅ **Related functionality** - Overloads should do similar things  
✅ **Consistent behavior** - All overloads should behave similarly  
✅ **Clear documentation** - Explain each overload's purpose  
✅ **Avoid confusion** - Don't create too many overloads  

### Common Mistakes

❌ Too many overloads (harder to maintain)  
❌ Inconsistent behavior between overloads  
❌ Overloading unrelated methods (use different names instead)  

### When to Use Overloading

- Same logical operation on different types
- Same operation with optional parameters
- Convenience methods (short versions of longer methods)

---

## 4. Classes and Objects

### What Are Classes?

A **class** is a **blueprint** or **template** for creating objects. It defines:
- **Fields** (data/state)
- **Methods** (behavior/actions)
- **Properties** (controlled access to data)

Think of a class as a **cookie cutter** and objects as the **cookies**.

### What Are Objects?

An **object** is an **instance** of a class. It's the actual "thing" created from the blueprint.

### Basic Class Structure

```csharp
class Car
{
    // Fields (data)
    public string Brand;
    public string Model;
    public int Year;
    public string Color;
    
    // Methods (behavior)
    public void DisplayInfo()
    {
        Console.WriteLine($"{Year} {Brand} {Model} ({Color})");
    }
    
    public void StartEngine()
    {
        Console.WriteLine($"The {Brand} {Model} engine is starting...");
    }
}
```

### Creating and Using Objects

```csharp
// Create an object (instantiate the class)
Car myCar = new Car();

// Set field values
myCar.Brand = "Toyota";
myCar.Model = "Camry";
myCar.Year = 2024;
myCar.Color = "Blue";

// Call methods
myCar.DisplayInfo();   // "2024 Toyota Camry (Blue)"
myCar.StartEngine();   // "The Toyota Camry engine is starting..."
```

### Creating Multiple Objects

```csharp
Car car1 = new Car();
car1.Brand = "Toyota";
car1.Model = "Camry";

Car car2 = new Car();
car2.Brand = "Honda";
car2.Model = "Accord";

// car1 and car2 are independent objects
// Changes to car1 don't affect car2
```

### Class Members

#### Fields
Variables that hold data for each object:
```csharp
public string Brand;  // Each car has its own brand
public int Year;      // Each car has its own year
```

#### Methods
Functions that define behavior:
```csharp
public void StartEngine() 
{ 
    // Code that runs when you call car.StartEngine()
}
```

### Benefits of Classes and Objects

✅ **Organization** - Related data and behavior together  
✅ **Reusability** - Create many objects from one class  
✅ **Modularity** - Each class has a specific purpose  
✅ **Maintainability** - Changes in one place affect all objects  
✅ **Real-world modeling** - Classes represent real things  

### Best Practices

✅ **Naming**: Use `PascalCase` for class names (Car, Person, BankAccount)  
✅ **Single Responsibility**: Each class should do one thing well  
✅ **Cohesion**: Put related fields and methods together  
✅ **Encapsulation**: Use properties instead of public fields (see Properties section)  

### Common Mistakes

❌ **God classes** - Classes that do everything  
❌ **Public fields** - Use properties for better control  
❌ **Poor naming** - Class names should be nouns (Car, not Drive)  
❌ **Too many responsibilities** - Keep classes focused  

### When to Create a Class

- Representing a real-world entity (Person, Car, Order)
- Grouping related data and behavior
- Need multiple instances with similar structure
- Organizing complex functionality

### Class vs Object - Key Difference

| Class | Object |
|-------|--------|
| Blueprint/Template | Actual instance |
| Defined once | Created many times |
| `class Car { }` | `Car myCar = new Car();` |
| Abstract concept | Concrete thing |

---

## 5. Constructors

### What Are Constructors?

**Constructors** are special methods that run when you create an object. They **initialize** the object's state (set initial values for fields).

### Constructor Syntax

```csharp
class ClassName
{
    public ClassName()
    {
        // Constructor code
    }
}
```

**Key characteristics**:
- Same name as the class
- No return type (not even `void`)
- Called automatically with `new` keyword

### Types of Constructors

#### 1. Default Constructor

If you don't write any constructor, C# creates an implicit default constructor:

```csharp
class Person
{
    public string Name;
    public int Age;
}

// Implicit default constructor exists
Person person = new Person(); // Name = null, Age = 0
```

#### 2. Parameterless Constructor (Explicit)

You can explicitly define a default constructor:

```csharp
class Person
{
    public string Name;
    public int Age;
    
    // Explicit parameterless constructor
    public Person()
    {
        Name = "Unknown";
        Age = 0;
        Console.WriteLine("Person object created!");
    }
}

Person person = new Person(); // Prints "Person object created!"
```

#### 3. Parameterized Constructor

Accept parameters to initialize fields with custom values:

```csharp
class Person
{
    public string Name;
    public int Age;
    
    // Parameterized constructor
    public Person(string name, int age)
    {
        Name = name;
        Age = age;
    }
}

// Usage
Person alice = new Person("Alice", 25);
// alice.Name = "Alice", alice.Age = 25
```

#### 4. Multiple Constructors (Constructor Overloading)

A class can have multiple constructors with different parameters:

```csharp
class Person
{
    public string Name;
    public int Age;
    
    // Constructor 1: No parameters
    public Person()
    {
        Name = "Unknown";
        Age = 0;
    }
    
    // Constructor 2: Name only
    public Person(string name)
    {
        Name = name;
        Age = 0;
    }
    
    // Constructor 3: Name and age
    public Person(string name, int age)
    {
        Name = name;
        Age = age;
    }
}

// Usage - choose which constructor to use
Person p1 = new Person();                // Constructor 1
Person p2 = new Person("Bob");           // Constructor 2
Person p3 = new Person("Charlie", 30);   // Constructor 3
```

### Constructor Chaining (`this` keyword)

Constructors can call other constructors to avoid code duplication:

```csharp
class Person
{
    public string Name;
    public int Age;
    
    // Main constructor with full initialization
    public Person(string name, int age)
    {
        Name = name;
        Age = age;
        Console.WriteLine($"Created: {Name}, {Age} years old");
    }
    
    // This constructor calls the main constructor
    public Person(string name) : this(name, 0)
    {
        // Calls Person(name, 0) first
        // Then executes this body (if needed)
    }
    
    // This constructor also calls the main constructor
    public Person() : this("Unknown", 0)
    {
        // Calls Person("Unknown", 0) first
    }
}
```

### Why Use Constructors?

✅ **Guaranteed initialization** - Objects start in valid state  
✅ **Convenience** - Initialize in one line  
✅ **Required values** - Force callers to provide essential data  
✅ **Validation** - Check values before assignment  
✅ **Setup logic** - Run initialization code automatically  

### Best Practices

✅ **Initialize all fields** - Don't leave fields with default values if they need specific values  
✅ **Validate parameters** - Check for null, negative numbers, etc.  
✅ **Keep it simple** - Complex logic belongs in methods, not constructors  
✅ **Use constructor chaining** - Avoid duplicating initialization code  
✅ **Provide default constructor** - For flexibility (if it makes sense)  

### Common Mistakes

❌ **Too much logic** - Constructors should initialize, not perform complex operations  
❌ **No validation** - Always validate parameters  
❌ **Duplicated code** - Use constructor chaining instead  
❌ **Forgetting to initialize** - All fields should have meaningful values  

### Constructor vs Method

| Constructor | Regular Method |
|-------------|----------------|
| Same name as class | Any name |
| No return type | Has return type |
| Called with `new` | Called with `.methodName()` |
| Runs once at creation | Can be called anytime |
| Initializes object | Performs actions |

### When to Use Constructors

- Set initial values for fields
- Validate input before creating object
- Perform necessary setup
- Provide convenience for object creation

---

## 6. Access Modifiers

### What Are Access Modifiers?

**Access modifiers** control the **visibility** and **accessibility** of classes, methods, fields, and properties. They are fundamental to **encapsulation** - hiding internal implementation details.

### Why Access Modifiers Matter

✅ **Encapsulation** - Hide internal details  
✅ **Data protection** - Prevent unauthorized access  
✅ **Maintainability** - Change internal implementation without breaking external code  
✅ **Clear API** - Show what's intended for public use  

### Types of Access Modifiers

#### 1. `public`

**Accessible from anywhere** - no restrictions.

```csharp
public class BankAccount
{
    public string AccountNumber; // Anyone can access
    
    public void Deposit(double amount)  // Anyone can call
    {
        // Code
    }
}

// Usage from anywhere
BankAccount account = new BankAccount();
account.AccountNumber = "12345";  // ✅ Allowed
account.Deposit(100);             // ✅ Allowed
```

#### 2. `private`

**Only accessible within the same class** - most restrictive.

```csharp
public class BankAccount
{
    private double balance;  // Only accessible inside this class
    
    private void CalculateInterest()  // Only callable inside this class
    {
        // Code
    }
    
    public double GetBalance()
    {
        return balance;  // ✅ OK: Same class can access private members
    }
}

// Usage
BankAccount account = new BankAccount();
account.balance = 1000;          // ❌ ERROR: balance is private
account.CalculateInterest();     // ❌ ERROR: method is private
account.GetBalance();            // ✅ OK: public method
```

#### 3. `protected`

**Accessible within the class and derived classes** (child classes).

```csharp
public class Animal
{
    protected string species;  // Accessible in derived classes
    
    protected void Sleep()     // Accessible in derived classes
    {
        Console.WriteLine("Sleeping...");
    }
}

public class Dog : Animal
{
    public void ShowSpecies()
    {
        species = "Canis familiaris";  // ✅ OK: Derived class can access
        Sleep();                        // ✅ OK: Can call protected method
    }
}

// Usage
Animal animal = new Animal();
animal.species = "Unknown";     // ❌ ERROR: protected (not public)
animal.Sleep();                 // ❌ ERROR: protected

Dog dog = new Dog();
dog.species = "Wolf";           // ❌ ERROR: Still protected
dog.ShowSpecies();              // ✅ OK: Public method
```

#### 4. `internal` (C# specific)

Accessible within the same **assembly** (project).

```csharp
internal class Helper  // Only accessible in this project
{
    internal void DoSomething()
    {
        // Code
    }
}
```

#### 5. `protected internal`

Accessible within the same assembly OR derived classes.

```csharp
protected internal void SpecialMethod()
{
    // Accessible in this assembly or any derived class
}
```

#### 6. `private protected` (C# 7.2+)

Accessible within the same assembly AND only in derived classes.

```csharp
private protected void RestrictedMethod()
{
    // Accessible only in derived classes within this assembly
}
```

### Access Modifier Comparison Table

| Modifier | Same Class | Derived Class | Same Assembly | Other Assembly |
|----------|------------|---------------|---------------|----------------|
| `public` | ✅ | ✅ | ✅ | ✅ |
| `private` | ✅ | ❌ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ❌ | ❌* |
| `internal` | ✅ | ✅ | ✅ | ❌ |
| `protected internal` | ✅ | ✅ | ✅ | ✅* |
| `private protected` | ✅ | ✅** | ❌ | ❌ |

*Only if it's a derived class  
**Only within same assembly

### Encapsulation Example

**Bad** - Public fields (no control):
```csharp
class BankAccount
{
    public double balance;  // ❌ Anyone can set to any value!
}

BankAccount account = new BankAccount();
account.balance = -1000;  // ❌ Negative balance? Allowed but wrong!
```

**Good** - Private fields with public methods (controlled access):
```csharp
class BankAccount
{
    private double balance;  // ✅ Protected from outside access
    
    public void Deposit(double amount)
    {
        if (amount > 0)  // ✅ Validation
        {
            balance += amount;
        }
    }
    
    public double GetBalance()
    {
        return balance;  // ✅ Controlled read access
    }
}

BankAccount account = new BankAccount();
account.Deposit(-100);  // ✅ Rejected by validation
```

### Best Practices

✅ **Start with `private`** - Make everything private by default, then open up as needed  
✅ **Use `public` sparingly** - Only for intended public API  
✅ **Encapsulate fields** - Fields should be private, accessed via properties  
✅ **`protected` for inheritance** - When derived classes need access  
✅ **Consistent visibility** - Don't expose internal details  

### Common Mistakes

❌ **Everything `public`** - Breaks encapsulation  
❌ **Public fields** - Use properties instead  
❌ **Too restrictive** - Don't make everything private (hard to use)  
❌ **Inconsistent access** - Public method returns private type  

### General Guidelines

**Classes**: Usually `public` (for libraries) or `internal` (for internal use)  
**Fields**: Almost always `private`  
**Properties**: `public` or `internal` for API, `private`/`protected` for internal use  
**Methods**: `public` for API, `private` for helpers, `protected` for inheritance  

### When to Use Each

**`public`**: Part of your class's public API  
**`private`**: Implementation details, helper methods, internal fields  
**`protected`**: Override or extend in derived classes  
**`internal`**: Share within the project, but not external users  

---

## 7. Properties (Get and Set)

### What Are Properties?

**Properties** provide **controlled access** to private fields. They look like fields but use **accessor methods** (`get` and `set`) to read and write values.

Properties are the **recommended way** to expose data in C#, instead of public fields.

### Why Use Properties?

✅ **Validation** - Check values before assigning  
✅ **Encapsulation** - Hide the actual field  
✅ **Read-only/Write-only** - Control read/write access  
✅ **Logic on access** - Run code when getting/setting  
✅ **Computed values** - Calculate values dynamically  
✅ **Maintain compatibility** - Change implementation without breaking code  

### Property Syntax

#### Basic Property (with backing field)

```csharp
class Student
{
    private string name;  // Backing field (private)
    
    public string Name    // Property (public)
    {
        get 
        { 
            return name;  // Called when reading: student.Name
        }
        set 
        { 
            name = value; // Called when writing: student.Name = "Alice"
        }
    }
}

// Usage
Student student = new Student();
student.Name = "Alice";     // Calls set accessor
Console.WriteLine(student.Name); // Calls get accessor
```

**Note**: `value` is a special keyword representing the assigned value.

#### Property with Validation

```csharp
class Student
{
    private int age;
    
    public int Age
    {
        get 
        { 
            return age; 
        }
        set 
        { 
            if (value >= 0 && value <= 120)  // Validation
            {
                age = value;
            }
            else
            {
                throw new ArgumentException("Age must be between 0 and 120");
            }
        }
    }
}

// Usage
Student student = new Student();
student.Age = 25;     // ✅ Valid - sets age to 25
student.Age = -5;     // ❌ Throws exception
student.Age = 150;    // ❌ Throws exception
```

### Auto-Implemented Properties

When you don't need validation, C# can create the backing field automatically:

```csharp
class Student
{
    // C# creates a hidden backing field automatically
    public string StudentId { get; set; }
    public string Email { get; set; }
}

// Usage - works exactly like regular properties
Student student = new Student();
student.StudentId = "S12345";
student.Email = "alice@university.edu";
```

**When to use**: When you don't need validation or custom logic.

### Read-Only Properties

#### Read-only with `get` only

```csharp
class Student
{
    private string studentId = "S00000";
    
    public string StudentId
    {
        get { return studentId; }
        // No set accessor - read-only!
    }
}

// Usage
Student student = new Student();
Console.WriteLine(student.StudentId);  // ✅ Can read
student.StudentId = "S12345";          // ❌ ERROR: No set accessor
```

#### Auto-implemented read-only (C# 6.0+)

```csharp
class Student
{
    public string StudentId { get; } = "S00000";  // Read-only, initialized
    
    public Student(string id)
    {
        StudentId = id;  // ✅ Can set in constructor
    }
}

// Usage
Student student = new Student("S12345");
Console.WriteLine(student.StudentId);  // "S12345"
student.StudentId = "S99999";          // ❌ ERROR: Read-only
```

### Write-Only Properties (Rare)

```csharp
class User
{
    private string password;
    
    public string Password
    {
        set 
        { 
            // Hash the password before storing
            password = HashPassword(value); 
        }
        // No get accessor - can't read password!
    }
}
```

### Computed Properties

Properties can calculate values instead of storing them:

```csharp
class Rectangle
{
    public double Width { get; set; }
    public double Height { get; set; }
    
    // Computed property - no backing field
    public double Area
    {
        get { return Width * Height; }
        // No set - read-only computed value
    }
}

// Usage
Rectangle rect = new Rectangle();
rect.Width = 5;
rect.Height = 10;
Console.WriteLine(rect.Area);  // 50 (calculated, not stored)
```

### Properties with different access modifiers

```csharp
class Student
{
    // Public get, private set (read-only from outside)
    public string Name { get; private set; }
    
    public Student(string name)
    {
        Name = name;  // ✅ Can set inside class
    }
    
    public void ChangeName(string newName)
    {
        Name = newName;  // ✅ Can set inside class
    }
}

// Usage
Student student = new Student("Alice");
Console.WriteLine(student.Name);  // ✅ Can read
student.Name = "Bob";             // ❌ ERROR: set is private
```

### Expression-Bodied Properties (C# 6.0+)

Shorter syntax for simple properties:

```csharp
class Rectangle
{
    public double Width { get; set; }
    public double Height { get; set; }
    
    // Expression-bodied property
    public double Area => Width * Height;
    // Equivalent to: public double Area { get { return Width * Height; } }
}
```

### Properties vs Fields

| Feature | Field | Property |
|---------|-------|----------|
| **Syntax** | `public string Name;` | `public string Name { get; set; }` |
| **Validation** | ❌ No | ✅ Yes |
| **Encapsulation** | ❌ Direct access | ✅ Controlled access |
| **Read-only** | `readonly` keyword | `get` only |
| **Computed values** | ❌ No | ✅ Yes |
| **Future changes** | ❌ Breaking change | ✅ Compatible |

### Best Practices

✅ **Use properties, not public fields** - Always (except for constants)  
✅ **PascalCase naming** - `Name`, not `name`  
✅ **Validate in set** - Check values before assigning  
✅ **Auto-properties when NO validation needed** - Simpler code  
✅ **Read-only when appropriate** - Protect data that shouldn't change  
✅ **Consistent naming** - Property name matches backing field (without underscore)  

### Common Mistakes

❌ **Using public fields** - No encapsulation  
❌ **No validation** - Allowing invalid data  
❌ **Get/set doing too much** - Keep logic simple  
❌ **Throwing exceptions in get** - Gets should be safe to call  

### When to Use Properties

- Exposing class data publicly
- Validating input before assignment
- Creating read-only or write-only members
- Computing values from other fields
- Future-proofing for potential logic changes

---

## 8. Inheritance

### What Is Inheritance?

**Inheritance** allows a class (child/derived class) to **inherit** attributes and methods from another class (parent/base class). It represents an **"is-a" relationship**.

Example: **Dog** is an **Animal**, **Car** is a **Vehicle**.

### Why Use Inheritance?

✅ **Code reuse** - Don't duplicate common code  
✅ **Hierarchy** - Model real-world relationships  
✅ **Polymorphism** - Treat different types uniformly  
✅ **Extensibility** - Add features without modifying base class  
✅ **Maintainability** - Changes in base class affect all derived classes  

### Basic Inheritance Syntax

```csharp
// Base class (parent)
class Animal
{
    public string Name;
    
    public void Eat()
    {
        Console.WriteLine($"{Name} is eating");
    }
    
    public void Sleep()
    {
        Console.WriteLine($"{Name} is sleeping");
    }
}

// Derived class (child) - inherits from Animal
class Dog : Animal
{
    public void Bark()
    {
        Console.WriteLine($"{Name} says: Woof!");
    }
}

// Usage
Dog dog = new Dog();
dog.Name = "Buddy";
dog.Eat();    // ✅ Inherited from Animal
dog.Sleep();  // ✅ Inherited from Animal
dog.Bark();   // ✅ Defined in Dog
```

### The `base` Keyword

The `base` keyword refers to the **parent class**.

#### Calling Base Constructor

```csharp
class Animal
{
    public string Name;
    
    public Animal(string name)
    {
        Name = name;
        Console.WriteLine($"Animal {name} created");
    }
}

class Dog : Animal
{
    public string Breed;
    
    public Dog(string name, string breed) : base(name)  // Call parent constructor
    {
        Breed = breed;
        Console.WriteLine($"Dog {name} of breed {breed} created");
    }
}

// Usage
Dog dog = new Dog("Buddy", "Golden Retriever");
// Output:
// Animal Buddy created
// Dog Buddy of breed Golden Retriever created
```

#### Calling Base Method

```csharp
class Animal
{
    public virtual void MakeSound()
    {
        Console.WriteLine("Some generic animal sound");
    }
}

class Dog : Animal
{
    public override void MakeSound()
    {
        base.MakeSound();  // Call parent version first
        Console.WriteLine("Woof!");
    }
}
```

### Inheritance Hierarchy

You can create multi-level hierarchies:

```csharp
class Animal { }
class Mammal : Animal { }
class Dog : Mammal { }
class GoldenRetriever : Dog { }
```

**Note**: C# supports **single inheritance only** - a class can inherit from only ONE direct parent (unlike C++).

### Protected Members in Inheritance

```csharp
class Animal
{
    protected string species;  // Accessible in derived classes
    
    protected void InternalBehavior()
    {
        // Accessible in derived classes
    }
}

class Dog : Animal
{
    public void ShowInfo()
    {
        species = "Canis familiaris";  // ✅ Can access protected member
        InternalBehavior();            // ✅ Can call protected method
    }
}
```

### Preventing Inheritance - `sealed` Keyword

```csharp
sealed class FinalClass
{
    // This class cannot be inherited
}

class DerivedClass : FinalClass  // ❌ ERROR: Can't inherit from sealed class
{
}
```

### Constructor Execution Order

When creating a derived object:
1. **Base constructor** runs first
2. **Derived constructor** runs second

```csharp
class Animal
{
    public Animal()
    {
        Console.WriteLine("1. Animal constructor");
    }
}

class Dog : Animal
{
    public Dog()
    {
        Console.WriteLine("2. Dog constructor");
    }
}

// Usage
Dog dog = new Dog();
// Output:
// 1. Animal constructor
// 2. Dog constructor
```

### Best Practices

✅ **Use meaningful hierarchies** - Model real "is-a" relationships  
✅ **Don't inherit for code reuse alone** - Use composition instead  
✅ **Keep hierarchies shallow** - 2-3 levels maximum recommended  
✅ **Use `base` to call parent constructors** - Proper initialization  
✅ **`protected` for derived class access** - Hide from outside world  

### Common Mistakes

❌ **Deep hierarchies** - Hard to understand and maintain  
❌ **Inheritance just for reuse** - Composition is often better  
❌ **Breaking Liskov Substitution** - Derived class changes parent behavior unexpectedly  
❌ **Not calling base constructor** - Missing initialization  

### Inheritance vs Composition

**Inheritance (is-a)**:
```csharp
class Dog : Animal  // Dog IS-A Animal
{
}
```

**Composition (has-a)**:
```csharp
class Car
{
    private Engine engine;  // Car HAS-A Engine
    
    public Car()
    {
        engine = new Engine();
    }
}
```

**Rule of thumb**: Prefer composition over inheritance when in doubt.

### When to Use Inheritance

- Clear "is-a" relationship exists
- Shared behavior across multiple classes
- Want to take advantage of polymorphism
- Building frameworks or class libraries

### When NOT to Use Inheritance

- Relationship is "has-a" (use composition)
- Only need to reuse some methods (use helper classes)
- Classes are unrelated conceptually
- Creates deep or complex hierarchies

---

## 9. Polymorphism

### What Is Polymorphism?

**Polymorphism** means "many forms". It allows objects of different types to be treated as objects of a common base type, with each type responding differently to the same method call.

**Key idea**: The **same method call** produces **different behaviors** depending on the object type.

### Why Polymorphism?

✅ **Flexibility** - Write code that works with any derived type  
✅ **Extensibility** - Add new types without changing existing code  
✅ **Simplified code** - Treat different types uniformly  
✅ **Runtime decisions** - Behavior determined at runtime  

### Types of Polymorphism

#### 1. Compile-Time Polymorphism (Method Overloading)

Covered in [Method Overloading section](#3-method-overloading).

#### 2. Runtime Polymorphism (Method Overriding)

The focus of this section.

### Method Overriding

#### Step 1: Mark base method as `virtual`

```csharp
class Animal
{
    public virtual void MakeSound()  // virtual = can be overridden
    {
        Console.WriteLine("Some generic sound");
    }
}
```

#### Step 2: Override in derived class

```csharp
class Dog : Animal
{
    public override void MakeSound()  // override = replace base implementation
    {
        Console.WriteLine("Woof!");
    }
}

class Cat : Animal
{
    public override void MakeSound()
    {
        Console.WriteLine("Meow!");
    }
}
```

#### Step 3: Use polymorphically

```csharp
// Create different animal types
Animal animal1 = new Dog();
Animal animal2 = new Cat();
Animal animal3 = new Animal();

// Same method call, different behavior!
animal1.MakeSound();  // "Woof!"
animal2.MakeSound();  // "Meow!"
animal3.MakeSound();  // "Some generic sound"
```

### Polymorphic Collections

One of the most powerful uses of polymorphism:

```csharp
// Store different types in the same collection
Animal[] animals = new Animal[3];
animals[0] = new Dog();
animals[1] = new Cat();
animals[2] = new Bird();

// Call the same method on all - each behaves differently
foreach (Animal animal in animals)
{
    animal.MakeSound();  // Woof! Meow! Chirp!
}
```

### Virtual vs Override

| Keyword | Purpose | Where Used |
|---------|---------|------------|
| `virtual` | Mark method as overridable | Base class |
| `override` | Replace base implementation | Derived class |

#### Example:

```csharp
class Base
{
    public virtual void Method()  // Allows overriding
    {
        Console.WriteLine("Base method");
    }
}

class Derived : Base
{
    public override void Method()  // Replaces base version
    {
        Console.WriteLine("Derived method");
    }
}

// Usage
Base obj = new Derived();
obj.Method();  // "Derived method" (calls override version)
```

### Without `override` - Method Hiding (Warning!)

```csharp
class Base
{
    public virtual void Method()
    {
        Console.WriteLine("Base method");
    }
}

class Derived : Base
{
    public void Method()  // HIDING (no override keyword)
    {
        Console.WriteLine("Derived method");
    }
}

// Usage
Derived derived = new Derived();
derived.Method();  // "Derived method" ✅

Base baseRef = new Derived();
baseRef.Method();  // "Base method" ❌ (Not polymorphic!)
```

**Lesson**: Always use `override` for polymorphism to work correctly!

### The `new` Keyword (Intentional Hiding)

If you intentionally want to hide (not override):

```csharp
class Derived : Base
{
    public new void Method()  // Explicitly hides base method
    {
        Console.WriteLine("Derived method");
    }
}
```

This suppresses the compiler warning, but still not polymorphic.

### Calling Base Implementation

You can call the base version even after overriding:

```csharp
class Dog : Animal
{
    public override void MakeSound()
    {
        base.MakeSound();  // Call base implementation first
        Console.WriteLine("Woof!");
    }
}

// Usage
Animal dog = new Dog();
dog.MakeSound();
// Output:
// Some generic sound
// Woof!
```

### Real-World Example: Payment Processing

```csharp
abstract class Payment
{
    public decimal Amount { get; set; }
    
    public virtual void ProcessPayment()
    {
        Console.WriteLine($"Processing payment of ${Amount}");
    }
}

class CreditCardPayment : Payment
{
    public override void ProcessPayment()
    {
        base.ProcessPayment();
        Console.WriteLine("Charging credit card...");
    }
}

class PayPalPayment : Payment
{
    public override void ProcessPayment()
    {
        base.ProcessPayment();
        Console.WriteLine("Redirecting to PayPal...");
    }
}

class BitcoinPayment : Payment
{
    public override void ProcessPayment()
    {
        base.ProcessPayment();
        Console.WriteLine("Waiting for blockchain confirmation...");
    }
}

// Usage - polymorphic collection
List<Payment> payments = new List<Payment>
{
    new CreditCardPayment { Amount = 100 },
    new PayPalPayment { Amount = 50 },
    new BitcoinPayment { Amount = 200 }
};

foreach (Payment payment in payments)
{
    payment.ProcessPayment();  // Different behavior for each type!
}
```

### Benefits of Polymorphism

✅ **Open/Closed Principle** - Open for extension, closed for modification  
✅ **Loose coupling** - Code depends on abstractions, not concrete types  
✅ **Easier testing** - Can mock different implementations  
✅ **Plugin architecture** - Add new types without changing existing code  

### Best Practices

✅ **Use `virtual` and `override` keywords** - Required for polymorphism  
✅ **Program to interfaces/base classes** - Not concrete types  
✅ **Liskov Substitution Principle** - Derived classes should be substitutable  
✅ **Avoid overriding too much** - Keep base implementation meaningful  

### Common Mistakes

❌ **Forgetting `override` keyword** - Results in method hiding, not overriding  
❌ **Not marking base method `virtual`** - Can't be overridden  
❌ **Changing method signature** - Must match exactly (or won't override)  
❌ **Breaking expected behavior** - Override should do what base method promises  

### Polymorphism Requirements Checklist

For polymorphism to work:
1. ✅ Base method must be marked `virtual` (or `abstract`)
2. ✅ Derived method must use `override` keyword
3. ✅ Method signatures must match exactly
4. ✅ Use base class reference to derived objects

### When to Use Polymorphism

- Multiple classes share common behavior with different implementations
- Want to treat different types uniformly
- Design for extensibility (add new types later)
- Building frameworks or plugin systems

---

## 10. Abstraction

### What Is Abstraction?

**Abstraction** is hiding implementation details and showing only essential features. In C#, we achieve abstraction using **abstract classes** and **abstract methods**.

An **abstract class** is a template for derived classes. It can contain both:
- **Abstract methods** (no implementation - must be overridden)
- **Concrete methods** (with implementation - can be inherited or overridden)

**Key rule**: You **cannot instantiate** (create objects from) abstract classes.

### Why Use Abstraction?

✅ **Enforce structure** - Derived classes must implement certain methods  
✅ **Common behavior** - Share code in base class  
✅ **Design contracts** - Define what derived classes must do  
✅ **Reduce duplication** - Common code in one place  
✅ **Polymorphism** - Use base class type for different implementations  

### Abstract Class Syntax

```csharp
abstract class ClassName  // abstract keyword
{
    // Abstract method (no implementation)
    public abstract void AbstractMethod();
    
    // Concrete method (with implementation)
    public void ConcreteMethod()
    {
        // Implementation
    }
}
```

### Basic Example

```csharp
abstract class Shape
{
    // Abstract method - NO implementation
    public abstract double CalculateArea();
    
    // Concrete method - HAS implementation
    public void Display()
    {
        Console.WriteLine($"The area is: {CalculateArea()}");
    }
}

class Circle : Shape
{
    public double Radius { get; set; }
    
    // MUST implement abstract method
    public override double CalculateArea()
    {
        return Math.PI * Radius * Radius;
    }
}

class Rectangle : Shape
{
    public double Width { get; set; }
    public double Height { get; set; }
    
    // MUST implement abstract method
    public override double CalculateArea()
    {
        return Width * Height;
    }
}

// Usage
Shape circle = new Circle { Radius = 5 };
circle.Display();  // "The area is: 78.54..."

Shape rectangle = new Rectangle { Width = 4, Height = 6 };
rectangle.Display();  // "The area is: 24"
```

### Cannot Instantiate Abstract Classes

```csharp
Shape shape = new Shape();  // ❌ ERROR: Cannot create instance of abstract class
```

Why? Because abstract methods have no implementation - what would happen if you called them?

### Abstract Methods Must Be Overridden

```csharp
abstract class Animal
{
    public abstract void MakeSound();  // Must be implemented by derived classes
}

class Dog : Animal
{
    // ✅ Correct - overrides abstract method
    public override void MakeSound()
    {
        Console.WriteLine("Woof!");
    }
}

class Cat : Animal
{
    // ❌ ERROR if you don't override MakeSound()
    // Compiler forces you to implement it
}
```

### Abstract Classes Can Have:

#### 1. Abstract Methods (must override)
```csharp
public abstract void AbstractMethod();
```

#### 2. Virtual Methods (can override)
```csharp
public virtual void VirtualMethod()
{
    // Default implementation
}
```

#### 3. Concrete Methods (inherit as-is)
```csharp
public void ConcreteMethod()
{
    // Implementation
}
```

#### 4. Fields and Properties
```csharp
public string Name { get; set; }
private int count;
```

#### 5. Constructors
```csharp
protected Shape(string name)
{
    Name = name;
}
```

**Note**: Constructors usually `protected` (can't call `new Shape()` anyway).

### Complete Example

```csharp
abstract class Vehicle
{
    public string Brand { get; set; }
    public string Model { get; set; }
    
    protected Vehicle(string brand, string model)
    {
        Brand = brand;
        Model = model;
    }
    
    // Abstract method - derived classes MUST implement
    public abstract void StartEngine();
    
    // Virtual method - derived classes CAN override
    public virtual void Stop()
    {
        Console.WriteLine($"{Brand} {Model} is stopping");
    }
    
    // Concrete method - all derived classes inherit
    public void DisplayInfo()
    {
        Console.WriteLine($"Vehicle: {Brand} {Model}");
    }
}

class Car : Vehicle
{
    public Car(string brand, string model) : base(brand, model)
    {
    }
    
    public override void StartEngine()
    {
        Console.WriteLine($"{Brand} {Model} engine started: Vroom!");
    }
}

class ElectricCar : Vehicle
{
    public ElectricCar(string brand, string model) : base(brand, model)
    {
    }
    
    public override void StartEngine()
    {
        Console.WriteLine($"{Brand} {Model} powering on: Whirrrr!");
    }
    
    public override void Stop()
    {
        Console.WriteLine($"{Brand} {Model} regenerative braking activated");
        base.Stop();  // Also call base implementation
    }
}

// Usage
Vehicle[] vehicles = new Vehicle[]
{
    new Car("Toyota", "Camry"),
    new ElectricCar("Tesla", "Model 3")
};

foreach (Vehicle vehicle in vehicles)
{
    vehicle.DisplayInfo();
    vehicle.StartEngine();
    vehicle.Stop();
    Console.WriteLine();
}
```

### Abstract Class vs Concrete Class

| Feature | Abstract Class | Concrete Class |
|---------|----------------|----------------|
| **Instantiate** | ❌ No | ✅ Yes |
| **Abstract methods** | ✅ Yes | ❌ No |
| **Concrete methods** | ✅ Yes | ✅ Yes |
| **Purpose** | Template for derived classes | Regular usable class |
| **Keyword** | `abstract` | None |

### Abstract Class vs Interface

| Feature | Abstract Class | Interface |
|---------|----------------|-----------|
| **Methods** | Abstract + concrete | Abstract only (C# 8+: default implementations) |
| **Fields** | ✅ Yes | ❌ No (only properties) |
| **Constructors** | ✅ Yes | ❌ No |
| **Access modifiers** | ✅ Any | ❌ All public |
| **Multiple inheritance** | ❌ No (single) | ✅ Yes (multiple interfaces) |
| **When to use** | "is-a" + shared code | "can-do" contracts |

### Best Practices

✅ **Use for shared behavior** - When derived classes have common code  
✅ **Abstract methods for required behavior** - Force implementation  
✅ **Virtual methods for optional behavior** - Provide default  
✅ **`protected` constructors** - Can't instantiate directly anyway  
✅ **Meaningful names** - Base class name represents concept  

### Common Mistakes

❌ **Trying to instantiate** - `new AbstractClass()` won't work  
❌ **Too many concrete methods** - Consider regular inheritance instead  
❌ **No shared behavior** - Use interface instead  
❌ **Deep hierarchies** - Keep it simple  

### When to Use Abstract Classes

- Multiple derived classes share common behavior (code reuse)
- Want to provide default implementations for some methods
- Need to enforce that certain methods must be implemented
- "is-a" relationship exists with shared functionality
- Need fields or constructors in base class

### When to Use Interfaces Instead

- No shared implementation code needed
- Want multiple inheritance
- "can-do" relationship rather than "is-a"
- Designing for maximum flexibility

---

## 11. Interfaces

### What Are Interfaces?

An **interface** is a **contract** that defines a set of methods and properties that a class must implement. It specifies **what** a class can do, but not **how** it does it.

Think of an interface as a **job description** - it lists required skills, but each person implements those skills differently.

### Why Use Interfaces?

✅ **Multiple inheritance** - Class can implement multiple interfaces  
✅ **Loose coupling** - Depend on contracts, not implementations  
✅ **Testability** - Easy to mock interfaces  
✅ **Flexibility** - Swap implementations easily  
✅ **Polymorphism** - Different classes, same interface  
✅ **Design by contract** - Define capabilities clearly  

### Interface Syntax

```csharp
interface IInterfaceName  // Convention: Start with "I"
{
    // Method signatures (no implementation)
    void MethodName();
    
    // Property signatures
    string PropertyName { get; set; }
    
    // All members are implicitly public
}
```

### Basic Interface Example

```csharp
interface IPlayable
{
    void Play();   // Method signature (no body)
    void Stop();
}

class Guitar : IPlayable
{
    public void Play()  // MUST implement
    {
        Console.WriteLine("Strumming the guitar: ♪♫");
    }
    
    public void Stop()  // MUST implement
    {
        Console.WriteLine("Stopped playing guitar");
    }
}

class Piano : IPlayable
{
    public void Play()
    {
        Console.WriteLine("Playing the piano: ♪♫♪");
    }
    
    public void Stop()
    {
        Console.WriteLine("Stopped playing piano");
    }
}

// Usage - polymorphism with interface
IPlayable instrument = new Guitar();
instrument.Play();  // "Strumming the guitar: ♪♫"

instrument = new Piano();
instrument.Play();  // "Playing the piano: ♪♫♪"
```

### Multiple Interfaces

A class can implement **multiple interfaces** (unlike inheritance where only one base class is allowed):

```csharp
interface ICallable
{
    void MakeCall(string number);
}

interface IConnectable
{
    void ConnectToWifi(string network);
}

// Implements TWO interfaces
class SmartPhone : ICallable, IConnectable
{
    public void MakeCall(string number)
    {
        Console.WriteLine($"Calling {number}...");
    }
    
    public void ConnectToWifi(string network)
    {
        Console.WriteLine($"Connecting to {network}...");
    }
}

// Usage
SmartPhone phone = new SmartPhone();
phone.MakeCall("555-1234");
phone.ConnectToWifi("HomeNetwork");

// Can use either interface type
ICallable callable = phone;
callable.MakeCall("555-5678");

IConnectable connectable = phone;
connectable.ConnectToWifi("CoffeeShop-WiFi");
```

### Interface Members

Interfaces can define:

#### 1. Methods
```csharp
void MethodName();
```

#### 2. Properties
```csharp
string PropertyName { get; set; }
int ReadOnlyProperty { get; }
```

#### 3. Indexers
```csharp
string this[int index] { get; set; }
```

#### 4. Events
```csharp
event EventHandler EventName;
```

#### 5. Default Implementations (C# 8.0+)
```csharp
interface ILogger
{
    void Log(string message);
    
    // Default implementation
    void LogError(string message)
    {
        Log($"ERROR: {message}");
    }
}
```

### Explicit Interface Implementation

When a class implements multiple interfaces with the same method name:

```csharp
interface IPrintable
{
    void Print();
}

interface IDisplayable
{
    void Print();
}

class Document : IPrintable, IDisplayable
{
    // Explicit implementation for IPrintable
    void IPrintable.Print()
    {
        Console.WriteLine("Printing document...");
    }
    
    // Explicit implementation for IDisplayable
    void IDisplayable.Print()
    {
        Console.WriteLine("Displaying document...");
    }
}

// Usage - must cast to specific interface
Document doc = new Document();
// doc.Print();  // ❌ ERROR: Ambiguous

((IPrintable)doc).Print();    // "Printing document..."
((IDisplayable)doc).Print();  // "Displaying document..."
```

### Interface Inheritance

Interfaces can inherit from other interfaces:

```csharp
interface IAnimal
{
    void Eat();
}

interface IMammal : IAnimal  // Inherits from IAnimal
{
    void GiveBirth();
}

class Dog : IMammal
{
    public void Eat()  // From IAnimal
    {
        Console.WriteLine("Dog is eating");
    }
    
    public void GiveBirth()  // From IMammal
    {
        Console.WriteLine("Dog gave birth to puppies");
    }
}
```

### Polymorphism with Interfaces

```csharp
interface IShape
{
    double GetArea();
}

class Circle : IShape
{
    public double Radius { get; set; }
    
    public double GetArea()
    {
        return Math.PI * Radius * Radius;
    }
}

class Square : IShape
{
    public double Side { get; set; }
    
    public double GetArea()
    {
        return Side * Side;
    }
}

// Polymorphic collection
List<IShape> shapes = new List<IShape>
{
    new Circle { Radius = 5 },
    new Square { Side = 4 },
    new Circle { Radius = 3 }
};

foreach (IShape shape in shapes)
{
    Console.WriteLine($"Area: {shape.GetArea()}");
}
```

### Interface vs Abstract Class - Detailed Comparison

| Feature | Interface | Abstract Class |
|---------|-----------|----------------|
| **Multiple inheritance** | ✅ Yes (multiple interfaces) | ❌ No (single base class) |
| **Implementation** | ❌ No (except C# 8+ default) | ✅ Yes (concrete methods) |
| **Fields** | ❌ No | ✅ Yes |
| **Constructors** | ❌ No | ✅ Yes |
| **Access modifiers** | ❌ All public | ✅ Any (public, protected, private) |
| **Static members** | ❌ No (C# 8+: yes) | ✅ Yes |
| **When to use** | "can-do" capability | "is-a" with shared code |
| **Keyword** | `interface` | `abstract class` |

### Naming Convention

✅ **Interface names start with "I"** (capital I):
- `IPlayable`
- `IComparable`
- `IEnumerable`
- `IDisposable`

This convention makes interfaces immediately recognizable.

### Real-World Example: Repository Pattern

```csharp
interface IRepository<T>
{
    T GetById(int id);
    IEnumerable<T> GetAll();
    void Add(T item);
    void Update(T item);
    void Delete(int id);
}

class UserRepository : IRepository<User>
{
    public User GetById(int id)
    {
        // Implementation for users
    }
    
    public IEnumerable<User> GetAll()
    {
        // Implementation
    }
    
    // ... other methods
}

class ProductRepository : IRepository<Product>
{
    // Same interface, different implementation
}
```

**Benefits**:
- Swap implementations (SQL → MongoDB → InMemory)
- Easy to test (mock `IRepository`)
- Consistent API across different types

### Best Practices

✅ **Start name with "I"** - `IPlayable`, not `Playable`  
✅ **Small, focused interfaces** - Interface Segregation Principle  
✅ **Describe capability** - What the class can do (IPlayable, IComparable)  
✅ **Program to interfaces** - Depend on `IService`, not `ConcreteService`  
✅ **Use for testability** - Easy to mock in unit tests  

### Common Mistakes

❌ **Interface with one method per implementation** - Might not need interface  
❌ **"Fat" interfaces** - Too many methods, hard to implement  
❌ **Not using "I" prefix** - Against C# conventions  
❌ **Trying to add implementation** - Use abstract class or C# 8+ default implementations  

### When to Use Interfaces

- Multiple classes share capability but not implementation
- Need multiple inheritance
- Want loose coupling and high testability
- Designing plugin architecture
- "Can-do" relationship (IFlyable, ISwimmable)
- Want to swap implementations easily

### When to Use Abstract Classes Instead

- Classes share significant implementation code
- Need fields or constructors
- "Is-a" relationship with shared behavior
- Want to provide default implementations
- Only need single inheritance

---

## 12. Enums

### What Are Enums?

**Enums** (enumerations) are a special type that defines a set of **named integer constants**. They make code more readable by replacing "magic numbers" with meaningful names.

### Why Use Enums?

✅ **Readability** - `Day.Monday` vs `1`  
✅ **Type safety** - Can't assign invalid values  
✅ **IntelliSense** - IDE shows available options  
✅ **Maintainability** - Change value in one place  
✅ **Self-documenting** - Clear what values are valid  

### Basic Enum Syntax

```csharp
enum EnumName
{
    Value1,
    Value2,
    Value3
}
```

### Simple Example

```csharp
enum Day
{
    Sunday,    // 0 (by default)
    Monday,    // 1
    Tuesday,   // 2
    Wednesday, // 3
    Thursday,  // 4
    Friday,    // 5
    Saturday   // 6
}

// Usage
Day today = Day.Monday;
Console.WriteLine(today);  // "Monday"
Console.WriteLine((int)today);  // 1

if (today == Day.Monday)
{
    Console.WriteLine("It's the start of the work week!");
}
```

### Explicit Values

You can assign custom values:

```csharp
enum OrderStatus
{
    Pending = 1,
    Processing = 2,
    Shipped = 3,
    Delivered = 4,
    Cancelled = 0
}

// Usage
OrderStatus status = OrderStatus.Shipped;
Console.WriteLine((int)status);  // 3
```

### Enum with Non-Sequential Values

```csharp
enum ErrorCode
{
    Success = 0,
    FileNotFound = 404,
    Unauthorized = 401,
    ServerError = 500
}
```

### Using Enums in Switch Statements

```csharp
enum OrderStatus
{
    Pending,
    Processing,
    Shipped,
    Delivered,
    Cancelled
}

string GetStatusMessage(OrderStatus status)
{
    switch (status)
    {
        case OrderStatus.Pending:
            return "Your order is pending";
        case OrderStatus.Processing:
            return "Your order is being processed";
        case OrderStatus.Shipped:
            return "Your order has been shipped";
        case OrderStatus.Delivered:
            return "Your order has been delivered";
        case OrderStatus.Cancelled:
            return "Your order was cancelled";
        default:
            return "Unknown status";
    }
}

// Usage
OrderStatus status = OrderStatus.Shipped;
Console.WriteLine(GetStatusMessage(status));  // "Your order has been shipped"
```

### Enum Methods

#### Get enum name as string
```csharp
Day day = Day.Monday;
string name = day.ToString();  // "Monday"
```

#### Parse string to enum
```csharp
Day day = (Day)Enum.Parse(typeof(Day), "Monday");
// or (safer):
Enum.TryParse("Monday", out Day result);
```

#### Get all enum values
```csharp
foreach (Day day in Enum.GetValues(typeof(Day)))
{
    Console.WriteLine(day);
}
// Output: Sunday, Monday, Tuesday, ...
```

#### Get all enum names
```csharp
string[] names = Enum.GetNames(typeof(Day));
// ["Sunday", "Monday", "Tuesday", ...]
```

#### Check if value is defined
```csharp
bool isValid = Enum.IsDefined(typeof(Day), "Monday");  // true
bool isValid2 = Enum.IsDefined(typeof(Day), "Holiday");  // false
```

### Enums with Flags (Bitwise Operations)

For enums representing combinations of values:

```csharp
[Flags]
enum FileAccess
{
    None = 0,      // 0000
    Read = 1,      // 0001
    Write = 2,     // 0010
    Execute = 4,   // 0100
    ReadWrite = Read | Write  // 0011 (combination)
}

// Usage - combine multiple values
FileAccess permission = FileAccess.Read | FileAccess.Write;

// Check if flag is set
if ((permission & FileAccess.Read) == FileAccess.Read)
{
    Console.WriteLine("Has read permission");
}

if (permission.HasFlag(FileAccess.Write))
{
    Console.WriteLine("Has write permission");
}
```

### Enum Underlying Type

Enums are integers by default, but you can use other integer types:

```csharp
enum SmallEnum : byte  // Values 0-255
{
    Value1,
    Value2
}

enum BigEnum : long  // Large values
{
    Value1 = 1000000000000
}
```

### Real-World Examples

#### 1. Priority Levels
```csharp
enum Priority
{
    Low = 1,
    Medium = 2,
    High = 3,
    Critical = 4
}

class Task
{
    public string Name { get; set; }
    public Priority Priority { get; set; }
}

Task task = new Task 
{ 
    Name = "Fix bug", 
    Priority = Priority.Critical 
};
```

#### 2. Days of Week
```csharp
enum DayOfWeek
{
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
    Sunday
}

bool IsWeekend(DayOfWeek day)
{
    return day == DayOfWeek.Saturday || day == DayOfWeek.Sunday;
}
```

#### 3. HTTP Status Codes
```csharp
enum HttpStatusCode
{
    OK = 200,
    BadRequest = 400,
    Unauthorized = 401,
    Forbidden = 403,
    NotFound = 404,
    InternalServerError = 500
}
```

### Best Practices

✅ **Meaningful names** - Describe what the values represent  
✅ **PascalCase** - For enum name and values  
✅ **Start at 0 or 1** - Unless specific values needed  
✅ **Explicit values** - For clarity (optional)  
✅ **Use in switch** - Exhaustive case handling  
✅ **[Flags] attribute** - For bitwise combinations  

### Common Mistakes

❌ **Using strings instead** - Lose type safety  
❌ **Using magic numbers** - `if (status == 2)` vs `if (status == OrderStatus.Processing)`  
❌ **Not using Flags attribute** - For combinable enums  
❌ **Inconsistent naming** - Mix of plural/singular  

### Enum vs Constants

```csharp
// ❌ Without enum (magic numbers)
const int STATUS_PENDING = 1;
const int STATUS_PROCESSING = 2;
void UpdateStatus(int status) { }  // Any int accepted!

// ✅ With enum (type-safe)
enum Status { Pending = 1, Processing = 2 }
void UpdateStatus(Status status) { }  // Only valid Status values!
```

### When to Use Enums

- Fixed set of related constants
- Values rarely change
- Want type safety
- Improve code readability
- Switch statement scenarios
- Flags/combinations of values

### When NOT to Use Enums

- Values change frequently (use database/config instead)
- Need description/metadata (consider class with constants)
- Many values (hundreds) - hard to maintain

---

## 13. File Operations

### What Are File Operations?

**File operations** allow programs to interact with files: creating, reading, writing, and deleting. The `System.IO` namespace provides classes and methods for file I/O.

### Why File Operations?

✅ **Data persistence** - Save data between program runs  
✅ **Configuration** - Read settings from files  
✅ **Logging** - Write error/activity logs  
✅ **Data exchange** - Import/export data  
✅ **User content** - Save user-created content  

### Common File Operations

#### 1. Writing to a File

```csharp
using System.IO;

// Write text to file (overwrites if exists)
File.WriteAllText("demo.txt", "Hello, World!");

// Write multiple lines
string[] lines = { "Line 1", "Line 2", "Line 3" };
File.WriteAllLines("demo.txt", lines);
```

#### 2. Appending to a File

```csharp
// Add text to end of file (creates if doesn't exist)
File.AppendAllText("demo.txt", "\nNew line appended");

// Append multiple lines
string[] newLines = { "Line 4", "Line 5" };
File.AppendAllLines("demo.txt", newLines);
```

#### 3. Reading from a File

```csharp
// Read entire file as one string
string content = File.ReadAllText("demo.txt");
Console.WriteLine(content);

// Read file as array of lines
string[] lines = File.ReadAllLines("demo.txt");
foreach (string line in lines)
{
    Console.WriteLine(line);
}
```

#### 4. Checking if File Exists

```csharp
if (File.Exists("demo.txt"))
{
    Console.WriteLine("File exists!");
}
else
{
    Console.WriteLine("File not found");
}
```

#### 5. Deleting a File

```csharp
if (File.Exists("demo.txt"))
{
    File.Delete("demo.txt");
    Console.WriteLine("File deleted");
}
```

### File Class Methods Summary

| Method | Purpose |
|--------|---------|
| `WriteAllText(path, text)` | Write text, overwrite if exists |
| `WriteAllLines(path, lines)` | Write array of lines, overwrite if exists |
| `AppendAllText(path, text)` | Add text to end of file |
| `AppendAllLines(path, lines)` | Add lines to end of file |
| `ReadAllText(path)` | Read entire file as string |
| `ReadAllLines(path)` | Read file as array of strings |
| `Exists(path)` | Check if file exists |
| `Delete(path)` | Delete file |
| `Copy(source, dest)` | Copy file |
| `Move(source, dest)` | Move/rename file |

### File Operations with Exception Handling

```csharp
try
{
    File.WriteAllText("demo.txt", "Content");
    Console.WriteLine("File written successfully");
}
catch (UnauthorizedAccessException)
{
    Console.WriteLine("Permission denied");
}
catch (DirectoryNotFoundException)
{
    Console.WriteLine("Directory doesn't exist");
}
catch (IOException ex)
{
    Console.WriteLine($"I/O error: {ex.Message}");
}
```

### Working with Paths

```csharp
using System;
using System.IO;

// Combine path parts
string path = Path.Combine("folder", "subfolder", "file.txt");
// Result: "folder/subfolder/file.txt" (or folder\subfolder\file.txt on Windows)

// Get file name
string fileName = Path.GetFileName("C:\\folder\\file.txt");  // "file.txt"

// Get directory
string directory = Path.GetDirectoryName("C:\\folder\\file.txt");  // "C:\folder"

// Get extension
string extension = Path.GetExtension("file.txt");  // ".txt"

// Get file name without extension
string nameOnly = Path.GetFileNameWithoutExtension("file.txt");  // "file"

// Current directory
string currentDir = Directory.GetCurrentDirectory();
```

### Reading File Line by Line (Efficient for Large Files)

```csharp
// Better for large files - doesn't load entire file into memory
foreach (string line in File.ReadLines("large-file.txt"))
{
    Console.WriteLine(line);
    // Process one line at a time
}
```

### StreamReader and StreamWriter

For more control:

#### StreamWriter (Writing)
```csharp
using (StreamWriter writer = new StreamWriter("demo.txt"))
{
    writer.WriteLine("Line 1");
    writer.WriteLine("Line 2");
    writer.WriteLine("Line 3");
}  // Auto-closes and flushes
```

#### StreamReader (Reading)
```csharp
using (StreamReader reader = new StreamReader("demo.txt"))
{
    string line;
    while ((line = reader.ReadLine()) != null)
    {
        Console.WriteLine(line);
    }
}  // Auto-closes
```

### Directory Operations

```csharp
// Create directory
Directory.CreateDirectory("newfolder");

// Check if directory exists
if (Directory.Exists("newfolder"))
{
    Console.WriteLine("Directory exists");
}

// Get files in directory
string[] files = Directory.GetFiles("folder");
foreach (string file in files)
{
    Console.WriteLine(file);
}

// Delete directory (must be empty)
Directory.Delete("newfolder");

// Delete directory and contents
Directory.Delete("newfolder", recursive: true);
```

### File Attributes

```csharp
// Get file info
FileInfo fileInfo = new FileInfo("demo.txt");
Console.WriteLine($"Size: {fileInfo.Length} bytes");
Console.WriteLine($"Created: {fileInfo.CreationTime}");
Console.WriteLine($"Last modified: {fileInfo.LastWriteTime}");
Console.WriteLine($"Read-only: {fileInfo.IsReadOnly}");

// Set attributes
fileInfo.IsReadOnly = true;
```

### Best Practices

✅ **Use `using` statement** - Auto-closes streams  
✅ **Check `File.Exists()`** - Before reading/deleting  
✅ **Use `Path.Combine()`** - Cross-platform paths  
✅ **Handle exceptions** - File operations can fail  
✅ **Close streams** - Prevent file locks  
✅ **Validate paths** - Check for invalid characters  

### Common Mistakes

❌ **Not closing files** - File locks prevent other access  
❌ **Hardcoded paths** - Use `Path.Combine()` for portability  
❌ **No error handling** - File operations can fail  
❌ **Reading large files with `ReadAllText()`** - Memory issues  
❌ **Assuming file exists** - Always check first  

### Exception Types

| Exception | Cause |
|-----------|-------|
| `FileNotFoundException` | File doesn't exist |
| `DirectoryNotFoundException` | Directory doesn't exist |
| `UnauthorizedAccessException` | No permission |
| `IOException` | General I/O error |
| `PathTooLongException` | Path exceeds max length |

### Security Considerations

⚠️ **Validate user input** - Don't trust file paths from users  
⚠️ **Be careful with deletion** - No recycle bin in code!  
⚠️ **Check permissions** - App might not have write access  
⚠️ **Sanitize file names** - Remove invalid characters  

### Real-World Examples

#### 1. Simple Logging
```csharp
void Log(string message)
{
    string logMessage = $"{DateTime.Now}: {message}\n";
    File.AppendAllText("app.log", logMessage);
}
```

#### 2. Configuration File
```csharp
// Save settings
File.WriteAllText("config.txt", "Theme=Dark\nLanguage=English");

// Read settings
Dictionary<string, string> settings = new Dictionary<string, string>();
foreach (string line in File.ReadAllLines("config.txt"))
{
    string[] parts = line.Split('=');
    settings[parts[0]] = parts[1];
}
```

#### 3. CSV Export
```csharp
List<string> students = new List<string>
{
    "Alice,25,A",
    "Bob,22,B",
    "Charlie,23,A"
};
File.WriteAllLines("students.csv", students);
```

### When to Use File Operations

- Save user data between sessions
- Import/export data
- Read configuration files
- Write logs
- Cache data locally
- Backup data

---

## 14. Exception Handling

### What Is Exception Handling?

**Exception handling** is a mechanism to handle **runtime errors** gracefully. Instead of crashing, your program can catch errors, handle them appropriately, and continue running (or exit gracefully).

An **exception** is an object representing an error condition.

### Why Exception Handling?

✅ **Prevent crashes** - Handle errors instead of dying  
✅ **Clear error messages** - Inform users what went wrong  
✅ **Resource cleanup** - Close files/connections properly  
✅ **Debugging** - Better error information  
✅ **User experience** - Graceful degradation  
✅ **Robustness** - Production-ready code  

### Basic Try-Catch Syntax

```csharp
try
{
    // Code that might throw an exception
}
catch (ExceptionType ex)
{
    // Handle the exception
}
```

### Simple Example

```csharp
try
{
    int[] numbers = { 1, 2, 3 };
    Console.WriteLine(numbers[10]);  // Index out of range!
}
catch (IndexOutOfRangeException ex)
{
    Console.WriteLine("Error: Array index is invalid");
    Console.WriteLine($"Details: {ex.Message}");
}

Console.WriteLine("Program continues...");
```

**Without try-catch**: Program crashes  
**With try-catch**: Error is caught, program continues

### Multiple Catch Blocks

Handle different exception types differently:

```csharp
try
{
    string input = Console.ReadLine();
    int number = int.Parse(input);  // Might throw FormatException
    int result = 100 / number;      // Might throw DivideByZeroException
    Console.WriteLine($"Result: {result}");
}
catch (FormatException)
{
    Console.WriteLine("Error: Please enter a valid number");
}
catch (DivideByZeroException)
{
    Console.WriteLine("Error: Cannot divide by zero");
}
catch (Exception ex)  // Catch all other exceptions
{
    Console.WriteLine($"Unexpected error: {ex.Message}");
}
```

**Order matters**: Most specific exceptions first, general ones last!

### The Finally Block

Code in `finally` **always executes**, whether an exception occurs or not:

```csharp
FileStream file = null;
try
{
    file = File.OpenRead("data.txt");
    // Read file...
}
catch (FileNotFoundException)
{
    Console.WriteLine("File not found");
}
finally
{
    // Always executes - cleanup code
    if (file != null)
    {
        file.Close();
        Console.WriteLine("File closed");
    }
}
```

**Use `finally` for**:
- Closing files
- Releasing resources
- Cleanup operations

### Try-Catch-Finally Pattern

```csharp
try
{
    // Code that might fail
}
catch (SpecificException)
{
    // Handle specific error
}
catch (Exception ex)
{
    // Handle any other error
}
finally
{
    // Cleanup (always runs)
}
```

### Throwing Exceptions

You can throw your own exceptions:

```csharp
void SetAge(int age)
{
    if (age < 0 || age > 120)
    {
        throw new ArgumentException("Age must be between 0 and 120");
    }
    
    // If we get here, age is valid
    this.age = age;
}

// Usage
try
{
    SetAge(-5);  // Throws ArgumentException
}
catch (ArgumentException ex)
{
    Console.WriteLine($"Error: {ex.Message}");
}
```

### Common Exception Types

| Exception | Cause |
|-----------|-------|
| `ArgumentException` | Invalid argument passed to method |
| `ArgumentNullException` | Null argument when not allowed |
| `ArgumentOutOfRangeException` | Argument value out of range |
| `InvalidOperationException` | Operation not valid in current state |
| `NullReferenceException` | Accessing member of null object |
| `IndexOutOfRangeException` | Array/list index invalid |
| `DivideByZeroException` | Division by zero |
| `FormatException` | String format invalid (parsing) |
| `FileNotFoundException` | File doesn't exist |
| `IOException` | I/O operation failed |
| `OverflowException` | Arithmetic overflow |

### Exception Properties

```csharp
try
{
    // Code
}
catch (Exception ex)
{
    Console.WriteLine($"Message: {ex.Message}");         // Error description
    Console.WriteLine($"Stack Trace: {ex.StackTrace}");  // Where it occurred
    Console.WriteLine($"Source: {ex.Source}");           // Where it originated
    Console.WriteLine($"Type: {ex.GetType()}");          // Exception type
}
```

### Re-throwing Exceptions

```csharp
try
{
    // Some operation
}
catch (Exception ex)
{
    Console.WriteLine("Logging error...");
    Log(ex.Message);
    
    throw;  // Re-throw the same exception
    // or: throw ex;  // This resets the stack trace (not recommended)
}
```

### Custom Exceptions

```csharp
public class InsufficientFundsException : Exception
{
    public InsufficientFundsException()
    {
    }
    
    public InsufficientFundsException(string message) 
        : base(message)
    {
    }
}

// Usage
void Withdraw(decimal amount)
{
    if (amount > balance)
    {
        throw new InsufficientFundsException(
            $"Cannot withdraw ${amount}. Balance: ${balance}");
    }
    
    balance -= amount;
}
```

### Using Statement (Automatic Disposal)

`using` automatically disposes resources even if exceptions occur:

```csharp
using (StreamReader reader = new StreamReader("file.txt"))
{
    string content = reader.ReadAllText();
    // If exception occurs, reader is still disposed
}
// reader.Dispose() called automatically
```

Equivalent to:

```csharp
StreamReader reader = new StreamReader("file.txt");
try
{
    string content = reader.ReadAllText();
}
finally
{
    if (reader != null)
        reader.Dispose();
}
```

### Best Practices

✅ **Catch specific exceptions** - Not just `Exception`  
✅ **Don't catch if you can't handle** - Let it propagate  
✅ **Use `finally` for cleanup** - Ensure resources are released  
✅ **Provide meaningful error messages** - Help users understand  
✅ **Log exceptions** - For debugging production issues  
✅ **Validate input** - Prevent exceptions when possible  
✅ **Use `using` for disposables** - Automatic cleanup  

### Common Mistakes

❌ **Empty catch blocks** - Swallowing exceptions silently  
❌ **Catching `Exception`** - Too generic, hides bugs  
❌ **Using exceptions for control flow** - Slow, bad practice  
❌ **Not cleaning up resources** - Memory leaks, file locks  
❌ **Throwing generic exceptions** - Use specific types  
❌ **Re-throwing with `throw ex`** - Loses stack trace  

### Exception Handling Anti-Patterns

#### ❌ Anti-pattern 1: Pokemon Exception Handling
```csharp
try
{
    // Code
}
catch (Exception)
{
    // Gotta catch 'em all! (bad - hides all errors)
}
```

#### ❌ Anti-pattern 2: Silent Failures
```csharp
try
{
    // Code
}
catch
{
    // Ignore error (very bad!)
}
```

#### ❌ Anti-pattern 3: Using Exceptions for Flow Control
```csharp
// Bad - exceptions are expensive!
try
{
    return dictionary[key];
}
catch (KeyNotFoundException)
{
    return default;
}

// Good - check first
return dictionary.ContainsKey(key) ? dictionary[key] : default;
```

### Real-World Example: Robust File Reading

```csharp
string ReadConfigFile(string path)
{
    try
    {
        if (!File.Exists(path))
        {
            throw new FileNotFoundException($"Config file not found: {path}");
        }
        
        string content = File.ReadAllText(path);
        
        if (string.IsNullOrWhiteSpace(content))
        {
            throw new InvalidOperationException("Config file is empty");
        }
        
        return content;
    }
    catch (FileNotFoundException ex)
    {
        Console.WriteLine($"Error: {ex.Message}");
        // Return default configuration
        return GetDefaultConfig();
    }
    catch (UnauthorizedAccessException)
    {
        Console.WriteLine("Error: Permission denied to read config file");
        return GetDefaultConfig();
    }
    catch (IOException ex)
    {
        Console.WriteLine($"I/O Error: {ex.Message}");
        return GetDefaultConfig();
    }
}
```

### When to Use Exception Handling

- File I/O operations
- Network operations
- Parsing user input
- Database operations
- Any operation that can fail
- Resource management (files, connections)

### When NOT to Use Exceptions

- Normal control flow (use if/else)
- Expected conditions (check before acting)
- Performance-critical code (exceptions are slow)

---

## 🎓 Summary

You've now learned **14 advanced C# concepts**:

1. ✅ **Methods** - Reusable code blocks
2. ✅ **Method Parameters** - Value, ref, out, optional
3. ✅ **Method Overloading** - Same name, different signatures
4. ✅ **Classes and Objects** - Blueprints and instances
5. ✅ **Constructors** - Object initialization
6. ✅ **Access Modifiers** - Encapsulation and visibility
7. ✅ **Properties** - Controlled field access with validation
8. ✅ **Inheritance** - Code reuse through parent-child relationships
9. ✅ **Polymorphism** - One interface, many implementations
10. ✅ **Abstraction** - Abstract classes and methods
11. ✅ **Interfaces** - Contracts for capabilities
12. ✅ **Enums** - Named constants for readability
13. ✅ **File Operations** - Reading, writing, and managing files
14. ✅ **Exception Handling** - Graceful error handling

### Next Steps

1. **Practice** - Try the exercises in [README.md](../README.md)
2. **Experiment** - Modify the code in [Program.cs](../Program.cs)
3. **Build** - Create your own projects using these concepts
4. **Learn more** - Explore LINQ, async/await, generics, delegates

