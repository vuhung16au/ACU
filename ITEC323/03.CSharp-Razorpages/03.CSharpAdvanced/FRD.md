# Functional Requirements Document (FRD)
# C# Advanced Concepts


## 1. Purpose

### 1.1 Project Overview

The **C# Advanced Concepts** project is an educational console application designed to teach students advanced C# programming concepts with a focus on **object-oriented programming (OOP)**. This project builds upon the foundation established in [02.CSharpCore](../02.CSharpCore/) and introduces intermediate to advanced programming techniques.

### 1.2 Target Audience

- **Primary**: ITEC323 students
- **Skill Level**: Beginners who have completed basic C# fundamentals (variables, loops, arrays, control flow)
- **Prerequisites**: Understanding of [01.HelloWorldCLI](../01.HelloWorldCLI/) and [02.CSharpCore](../02.CSharpCore/)

### 1.3 Educational Goals

Students will:
1. Master method creation and usage
2. Understand object-oriented programming principles
3. Apply inheritance, polymorphism, and abstraction
4. Implement interfaces for contract-based design
5. Handle exceptions and file operations
6. Write robust, production-quality code

---

## 2. Functional Requirements

### FR1: Methods Demonstration

**Priority**: High  
**Status**: ✅ Implemented

#### Description
Demonstrate fundamental method concepts including method creation, parameters, and return values.

#### Specific Requirements
- FR1.1: Display a parameterless void method
- FR1.2: Show a method with two integer parameters returning an integer
- FR1.3: Demonstrate a method with one parameter returning a string
- FR1.4: Call all methods from the main program
- FR1.5: Display method results to console

#### Acceptance Criteria
- ✅ All three method types are demonstrated
- ✅ Methods are properly documented with XML comments
- ✅ Output clearly shows method functionality
- ✅ Code demonstrates proper naming conventions

#### Example Output
```
=== 1. Methods ===
Methods are reusable blocks of code...
Welcome to C# Advanced Concepts!
5 + 3 = 8
Hello, Alice!
```

---

### FR2: Method Parameters

**Priority**: High  
**Status**: ✅ Implemented

#### Description
Demonstrate different parameter passing mechanisms including by-value, by-reference, out parameters, and optional parameters.

#### Specific Requirements
- FR2.1: Show pass-by-value (data is copied)
- FR2.2: Demonstrate `ref` keyword (pass-by-reference)
- FR2.3: Demonstrate `out` keyword (return multiple values)
- FR2.4: Show optional parameters with default values
- FR2.5: Clearly show the differences in behavior

#### Acceptance Criteria
- ✅ All four parameter types are demonstrated
- ✅ Output clearly shows value changes (or lack thereof)
- ✅ Examples use meaningful variable names
- ✅ Comments explain the differences

#### Example Output
```
=== 2. Method Parameters ===
Pass by value: original = 5, after method = 5
Pass by reference: original = 5, after method = 10
Out parameter: result = 20
Optional parameters: Hello, Guest! | Hello, Alice!
```

---

### FR3: Method Overloading

**Priority**: Medium  
**Status**: ✅ Implemented

#### Description
Demonstrate method overloading by creating multiple methods with the same name but different signatures.

#### Specific Requirements
- FR3.1: Create at least 3 overloaded versions of a method
- FR3.2: Overloads should differ by parameter count or types
- FR3.3: Show polymorphic method resolution
- FR3.4: Demonstrate practical use cases

#### Acceptance Criteria
- ✅ Multiple overloaded methods demonstrated
- ✅ Compiler correctly selects appropriate overload
- ✅ Output shows each overload working correctly
- ✅ Examples are practical and educational

#### Example Output
```
=== 3. Method Overloading ===
Calculate(5, 3) = 8
Calculate(2.5, 3.7) = 6.2
Calculate(1, 2, 3) = 6
```

---

### FR4: Classes and Objects

**Priority**: High  
**Status**: ✅ Implemented

#### Description
Introduce classes as blueprints and objects as instances, demonstrating basic OOP concepts.

#### Specific Requirements
- FR4.1: Define a class with at least 4 fields
- FR4.2: Include at least 2 methods in the class
- FR4.3: Create multiple object instances
- FR4.4: Show accessing fields and calling methods on objects
- FR4.5: Demonstrate that objects are independent

#### Acceptance Criteria
- ✅ Class is well-defined with clear purpose
- ✅ Multiple objects created and manipulated
- ✅ Output shows object state and behavior
- ✅ Code demonstrates encapsulation basics

#### Example Output
```
=== 4. Classes and Objects ===
2024 Toyota Camry (Blue)
The Toyota Camry engine is starting...
```

---

### FR5: Constructors

**Priority**: High  
**Status**: ✅ Implemented

#### Description
Demonstrate constructors for object initialization, including default and parameterized constructors.

#### Specific Requirements
- FR5.1: Show default (parameterless) constructor
- FR5.2: Demonstrate parameterized constructor
- FR5.3: Show constructor overloading
- FR5.4: Display initialization messages
- FR5.5: Compare objects created with different constructors

#### Acceptance Criteria
- ✅ Both constructor types demonstrated
- ✅ Objects properly initialized
- ✅ Output shows constructor execution
- ✅ Code demonstrates best practices

#### Example Output
```
=== 5. Constructors ===
Default constructor: Name = Unknown, Age = 0
Parameterized constructor: Name = Alice, Age = 25
```

---

### FR6: Access Modifiers

**Priority**: High  
**Status**: ✅ Implemented

#### Description
Demonstrate access modifiers (public, private, protected) and encapsulation principles.

#### Specific Requirements
- FR6.1: Create a class with private fields
- FR6.2: Provide public methods for controlled access
- FR6.3: Show data validation in methods
- FR6.4: Demonstrate why private fields are protected
- FR6.5: Show proper encapsulation practices

#### Acceptance Criteria
- ✅ Private fields cannot be accessed directly
- ✅ Public methods provide controlled access
- ✅ Validation prevents invalid data
- ✅ Comments explain encapsulation benefits

#### Example Output
```
=== 6. Access Modifiers ===
Account holder: Alice
Initial balance: $1000.00
After deposit: $1500.00
After withdrawal: $1300.00
```

---

### FR7: Properties (Get and Set)

**Priority**: High  
**Status**: ✅ Implemented

#### Description
Demonstrate properties as a mechanism for controlled field access with validation.

#### Specific Requirements
- FR7.1: Show basic property with get and set
- FR7.2: Demonstrate property with validation
- FR7.3: Show auto-implemented properties
- FR7.4: Display successful and failed validations
- FR7.5: Compare properties vs public fields

#### Acceptance Criteria
- ✅ All property types demonstrated
- ✅ Validation prevents invalid data
- ✅ Output shows validation in action
- ✅ Best practices followed

#### Example Output
```
=== 7. Properties (Get and Set) ===
Student: Alice
Age: 20
Grade: A
Student ID: S12345
```

---

### FR8: Inheritance

**Priority**: High  
**Status**: ✅ Implemented

#### Description
Demonstrate inheritance with base and derived classes, showing code reuse and "is-a" relationships.

#### Specific Requirements
- FR8.1: Create a base class with common functionality
- FR8.2: Create at least 2 derived classes
- FR8.3: Show inherited methods being called
- FR8.4: Demonstrate derived class-specific methods
- FR8.5: Show proper use of base class constructors

#### Acceptance Criteria
- ✅ Clear "is-a" relationship demonstrated
- ✅ Base class methods inherited and used
- ✅ Derived classes add specific behavior
- ✅ Output shows inheritance working

#### Example Output
```
=== 8. Inheritance ===
Buddy is eating
Buddy is sleeping
Buddy says: Woof!
```

---

### FR9: Polymorphism

**Priority**: High  
**Status**: ✅ Implemented

#### Description
Demonstrate runtime polymorphism through method overriding and virtual/override keywords.

#### Specific Requirements
- FR9.1: Create virtual method in base class
- FR9.2: Override method in derived classes
- FR9.3: Use base class references to derived objects
- FR9.4: Show polymorphic collections
- FR9.5: Demonstrate dynamic method resolution

#### Acceptance Criteria
- ✅ Virtual and override keywords used correctly
- ✅ Same method call produces different behavior
- ✅ Polymorphic collection demonstrated
- ✅ Output clearly shows polymorphism

#### Example Output
```
=== 9. Polymorphism ===
Demonstrating polymorphism with Animal references:
Woof!
Meow!
Chirp!
```

---

### FR10: Abstraction

**Priority**: High  
**Status**: ✅ Implemented

#### Description
Demonstrate abstraction using abstract classes and abstract methods.

#### Specific Requirements
- FR10.1: Create abstract base class
- FR10.2: Include at least one abstract method
- FR10.3: Include at least one concrete method
- FR10.4: Create derived classes implementing abstract methods
- FR10.5: Show that abstract classes cannot be instantiated

#### Acceptance Criteria
- ✅ Abstract class properly defined
- ✅ Abstract methods implemented in derived classes
- ✅ Concrete methods inherited and used
- ✅ Polymorphic usage demonstrated

#### Example Output
```
=== 10. Abstraction ===
Circle with radius 5:
Area = 78.54
Rectangle with width 4 and height 6:
Area = 24.00
```

---

### FR11: Interfaces

**Priority**: High  
**Status**: ✅ Implemented

#### Description
Demonstrate interfaces as contracts for class behavior, including multiple interface implementation.

#### Specific Requirements
- FR11.1: Define an interface with method signatures
- FR11.2: Implement interface in multiple classes
- FR11.3: Show polymorphic usage via interface type
- FR11.4: Demonstrate multiple interface implementation
- FR11.5: Follow naming convention (prefix with "I")

#### Acceptance Criteria
- ✅ Interface properly defined
- ✅ Multiple classes implement interface
- ✅ Polymorphic calls through interface reference
- ✅ Multiple interfaces on one class demonstrated

#### Example Output
```
=== 11. Interfaces ===
Strumming the guitar: ♪♫
Playing the piano: ♪♫♪
Playing the drums: ♪ ♪
SmartPhone implementing multiple interfaces:
Calling 555-1234...
Connecting to WiFi-Home...
```

---

### FR12: Enums

**Priority**: Medium  
**Status**: ✅ Implemented

#### Description
Demonstrate enumerations for named constants with clear, readable code.

#### Specific Requirements
- FR12.1: Create enum with default values
- FR12.2: Create enum with explicit values
- FR12.3: Use enum in switch statement
- FR12.4: Show enum-to-string conversion
- FR12.5: Demonstrate practical enum usage

#### Acceptance Criteria
- ✅ Both enum types demonstrated
- ✅ Switch statement uses enum values
- ✅ Code is more readable than magic numbers
- ✅ Practical examples provided

#### Example Output
```
=== 12. Enums ===
Today is: Monday
Numeric value: 1
Is weekend? False

Order status: Shipped (3)
Status message: Your order has been shipped
```

---

### FR13: File Operations

**Priority**: High  
**Status**: ✅ Implemented

#### Description
Demonstrate file I/O operations including creating, reading, writing, and deleting files.

#### Specific Requirements
- FR13.1: Create/write a file
- FR13.2: Append to a file
- FR13.3: Read file contents
- FR13.4: Check if file exists
- FR13.5: Delete a file
- FR13.6: Handle file operation errors

#### Acceptance Criteria
- ✅ All file operations demonstrated
- ✅ File is created, modified, read, and deleted
- ✅ Code checks for file existence
- ✅ Error handling included

#### Example Output
```
=== 13. File Operations ===
File created and written: demo.txt
Content appended to file
File contents:
Hello, File I/O!
This is an appended line.
File deleted: demo.txt
```

---

### FR14: Exception Handling

**Priority**: High  
**Status**: ✅ Implemented

#### Description
Demonstrate exception handling with try-catch-finally blocks and throwing exceptions.

#### Specific Requirements
- FR14.1: Show basic try-catch block
- FR14.2: Demonstrate multiple catch blocks
- FR14.3: Show finally block execution
- FR14.4: Demonstrate throwing exceptions
- FR14.5: Show exception information (message, type)

#### Acceptance Criteria
- ✅ All exception handling mechanisms demonstrated
- ✅ Errors are caught and handled gracefully
- ✅ Finally block always executes
- ✅ Custom exceptions thrown appropriately

#### Example Output
```
=== 14. Exception Handling ===
Caught: Index was out of range...
Caught: Attempted to divide by zero
Finally block executed
Caught ArgumentException: Age must be between 0 and 120
```

---

## 3. Non-Functional Requirements

### NFR1: Code Quality

**Priority**: High  
**Status**: ✅ Met

#### Requirements
- All code must follow C# naming conventions
- XML documentation on all public methods
- Inline comments explaining complex logic
- DRY principle (Don't Repeat Yourself)
- Consistent formatting and indentation

#### Verification
- ✅ PascalCase used for classes and methods
- ✅ camelCase used for variables and parameters
- ✅ All public methods have XML comments
- ✅ Code is well-commented for beginners

---

### NFR2: Educational Value

**Priority**: High  
**Status**: ✅ Met

#### Requirements
- Code must be beginner-friendly
- Concepts introduced progressively
- Examples must be practical and relatable
- Output must be clear and descriptive
- Comments must explain "why" not just "what"

#### Verification
- ✅ Simple, clear examples used
- ✅ Concepts build on each other
- ✅ Real-world analogies provided
- ✅ Output is formatted and descriptive

---

### NFR3: Completeness

**Priority**: High  
**Status**: ✅ Met

#### Requirements
- All 14 concepts must be covered
- Each concept must have dedicated demonstration method
- Supporting classes must be provided
- Documentation must be comprehensive

#### Verification
- ✅ 14 demonstration methods implemented
- ✅ 20+ supporting classes created
- ✅ README, QUICKSTART, Key-Takeaways, FRD completed
- ✅ All requirements documented

---

### NFR4: Performance

**Priority**: Low  
**Status**: ✅ Met

#### Requirements
- Program should execute in under 5 seconds
- No memory leaks or resource leaks
- File operations should be efficient
- Reasonable output size (not overwhelming)

#### Verification
- ✅ Execution time: ~1-2 seconds
- ✅ All resources properly disposed
- ✅ Output: ~200 lines (manageable)

---

### NFR5: Platform Compatibility

**Priority**: High  
**Status**: ✅ Met

#### Requirements
- Must build and run on .NET 10.0
- Must be compatible with .NET 10.0
- Must work on Windows, macOS, and Linux
- No platform-specific dependencies

#### Verification
- ✅ Targets net10.0
- ✅ Uses cross-platform APIs only
- ✅ File paths use Path.Combine()
- ✅ No OS-specific code

---

### NFR6: Maintainability

**Priority**: Medium  
**Status**: ✅ Met

#### Requirements
- Code must be easy to modify
- Adding new demonstrations should be straightforward
- Supporting classes should be separated
- Clear structure and organization

#### Verification
- ✅ Each concept in separate method
- ✅ Supporting classes in dedicated region
- ✅ Consistent structure throughout
- ✅ Easy to add new demonstrations

---

## 4. Success Criteria

### Overall Project Success

The project is successful if:

1. ✅ All 14 functional requirements are implemented
2. ✅ All non-functional requirements are met
3. ✅ Program builds without errors
4. ✅ Program runs without exceptions (except demonstration)
5. ✅ Output is clear and educational
6. ✅ Code follows all conventions and best practices
7. ✅ Documentation is complete and accurate
8. ✅ Students can understand and learn from the code

**Current Status**: ✅ **ALL SUCCESS CRITERIA MET**

---

## 5. Constraints

### Technical Constraints

- **Language**: C# only
- **Framework**: .NET 10.0
- **Project Type**: Console Application
- **Dependencies**: System, System.IO (built-in only)
- **Output**: Console text only (no GUI)

### Educational Constraints

- **Target audience**: First-time learners
- **Complexity**: Appropriate for beginners
- **Prerequisites**: Basic C# knowledge required
- **Time commitment**: 2-4 hours to complete and understand

### Resource Constraints

- **File operations**: Use only temporary demo files
- **Memory**: Minimal memory usage
- **External dependencies**: None (self-contained)

---

## 6. Assumptions

1. Students have completed [02.CSharpCore](../02.CSharpCore/)
2. Students have .NET SDK installed
3. Students have access to a code editor
4. Students can run terminal/command prompt commands
5. Students have basic understanding of programming concepts
6. Students can read and understand English
7. Students have write permissions in project directory

---

## 7. Dependencies

### Internal Dependencies

- **01.HelloWorldCLI**: Introduction to C# and console apps
- **02.CSharpCore**: Foundation of C# fundamentals
- **03.CSharp/README.md**: Context for this project series

### External Dependencies

- **.NET SDK**: 10.0 or 8.0+ required
- **Operating System**: Windows, macOS, or Linux
- **File System**: Write access for file operations demonstration

### Documentation Dependencies

- README.md: Project overview
- QUICKSTART.md: Build/run instructions
- docs/Key-Takeaways.md: Concept explanations
- This FRD.md: Requirements documentation

---

## 8. Risk Analysis

### Risk 1: Students Skip Prerequisites

**Impact**: Medium  
**Probability**: Low  
**Mitigation**: Clear prerequisites stated in README  
**Status**: ✅ Mitigated

### Risk 2: File Operation Permissions

**Impact**: Low  
**Probability**: Low  
**Mitigation**: Try-catch blocks, clear error messages  
**Status**: ✅ Mitigated

### Risk 3: .NET Version Compatibility

**Impact**: Medium  
**Probability**: Low  
**Mitigation**: Target net10.0, compatible with 8.0+  
**Status**: ✅ Mitigated

### Risk 4: Overwhelming Complexity

**Impact**: High  
**Probability**: Low  
**Mitigation**: Progressive complexity, extensive comments  
**Status**: ✅ Mitigated

---

## 9. Testing and Verification

### Unit Testing

**Status**: N/A (Educational demonstration project)

This project is designed for learning, not production use. Unit tests are not included, but students are encouraged to:
- Modify code and observe behavior
- Add new examples
- Experiment with concepts

### Manual Testing

All demonstrations have been manually tested:

✅ Program builds successfully  
✅ Program runs without crashes  
✅ All 14 demonstrations execute correctly  
✅ File operations work (create, read, delete)  
✅ Exception handling catches errors appropriately  
✅ Output is clear and descriptive  

### Integration Testing

✅ All supporting classes work with demonstration methods  
✅ Object-oriented concepts integrate correctly  
✅ File operations work across platforms  
✅ Exception handling doesn't interfere with normal flow  

---

## 10. Deployment

### Deployment Method

**Type**: Educational Resource - Source Code Distribution

Students receive:
1. Source code (Program.cs)
2. Project file (CSharpAdvanced.csproj)
3. Documentation (README, QUICKSTART, Key-Takeaways, FRD)

### Deployment Requirements

- .NET SDK 10.0 or 8.0+
- Terminal/Command Prompt access
- Code editor (optional but recommended)
- Write permissions in project directory

### Deployment Process

Students will:
1. Clone or download repository
2. Navigate to project directory
3. Run `dotnet build`
4. Run `dotnet run`
5. Study code and documentation

**Status**: ✅ Ready for deployment

---

## 11. Maintenance

### Maintenance Plan

**Review Schedule**: Beginning of each semester

**Maintenance Activities**:
- Update for new .NET versions
- Fix any discovered errors
- Improve clarity based on feedback
- Add additional examples if needed
- Update documentation

**Responsible Party**: ITEC323 Course Team

---

## 12. Future Enhancements

### Planned Enhancements

These are NOT part of current scope but may be added in future versions:

1. **Generics** - Generic classes and methods
2. **LINQ** - Language Integrated Query
3. **Async/Await** - Asynchronous programming
4. **Delegates and Events** - Callback mechanisms
5. **Collections** - Advanced collection types
6. **Attributes** - Metadata and reflection
7. **Extension Methods** - Extending existing types
8. **Nullable Types** - Handling null values
9. **Records** - Immutable reference types (C# 9+)
10. **Pattern Matching** - Advanced switch expressions

---

## 13. Glossary

| Term | Definition |
|------|------------|
| **Abstract Class** | Class that cannot be instantiated, used as template |
| **Access Modifier** | Keyword controlling visibility (public, private, protected) |
| **Constructor** | Special method that initializes objects |
| **Encapsulation** | Hiding implementation details |
| **Enum** | Set of named integer constants |
| **Exception** | Object representing an error condition |
| **Inheritance** | Deriving classes from base classes |
| **Interface** | Contract defining methods a class must implement |
| **Method Overloading** | Multiple methods with same name, different signatures |
| **Polymorphism** | One interface, multiple implementations |
| **Property** | Member providing controlled access to fields |

---

## 14. Approval and Sign-Off

### Document Version Control

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0 | 2026-03-08 | ITEC323 Course Team | Initial FRD creation |

### Requirement Status Summary

| Category | Total | Implemented | Percentage |
|----------|-------|-------------|------------|
| Functional Requirements | 14 | 14 | 100% |
| Non-Functional Requirements | 6 | 6 | 100% |
| Success Criteria | 8 | 8 | 100% |

### Overall Assessment

✅ **Project Status**: COMPLETE  
✅ **Requirements**: ALL MET  
✅ **Quality**: HIGH  
✅ **Documentation**: COMPREHENSIVE  
✅ **Educational Value**: EXCELLENT  

**Project is ready for educational use.**

---

## 15. References

### Internal References

- [README.md](README.md) - Project overview
- [QUICKSTART.md](QUICKSTART.md) - Build and run guide
- [docs/Key-Takeaways.md](docs/Key-Takeaways.md) - Concept explanations
- [Program.cs](Program.cs) - Source code
- [../README.md](../README.md) - C# projects overview

### External References

- [Microsoft C# Documentation](https://learn.microsoft.com/en-us/dotnet/csharp/)
- [C# Programming Guide](https://learn.microsoft.com/en-us/dotnet/csharp/programming-guide/)
- [.NET API Reference](https://learn.microsoft.com/en-us/dotnet/api/)
- [AGENTS.md](../../AGENTS.md) - Project contribution guidelines

