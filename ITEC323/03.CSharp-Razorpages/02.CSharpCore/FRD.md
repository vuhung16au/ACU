# Functional Requirements Document (FRD)
## C# Core Concepts Demonstration

## 1. Purpose

### 1.1 Overview
The C# Core Concepts project is an educational console application designed to demonstrate and teach fundamental C# programming concepts through practical, executable examples. This project serves as a comprehensive learning resource for students beginning their journey with C# and .NET development.

### 1.2 Target Audience
- Computer science students (all levels)
- Students enrolled in ITEC323 (Software Technologies)
- Self-learners new to C# programming
- Developers transitioning from other languages to C#

### 1.3 Educational Objectives
By studying and running this project, students will:
- Understand 15 core C# programming concepts
- See practical demonstrations of each concept in action
- Learn best practices for writing clean, documented C# code
- Gain confidence in using basic C# syntax and features
- Build a foundation for advanced C# topics

---

## 2. Scope

### 2.1 In Scope

**Core C# Concepts Covered:**
1. **Output**: Console.WriteLine() and Console.Write()
2. **Comments**: Single-line, multi-line, and XML documentation
3. **Variables**: Declaration, initialization, and assignment
4. **Data Types**: int, long, float, double, char, bool, string
5. **Type Casting**: Implicit and explicit conversion
6. **User Input**: Console.ReadLine() and user interaction
7. **Operators**: Arithmetic, assignment, comparison, logical
8. **Math Operations**: Math class methods and functions
9. **Strings**: Concatenation, interpolation, manipulation
10. **Booleans**: Boolean logic and expressions
11. **If-Else Statements**: Conditional logic and ternary operator
12. **Switch Statements**: Multi-way branching
13. **While Loops**: While and do-while loops
14. **For Loops**: Counter-based iteration and foreach
15. **Break and Continue**: Loop control statements
16. **Arrays**: Declaration, initialization, access, and iteration

**Deliverables:**
- ✅ Working console application (.NET 10.0)
- ✅ Comprehensive source code with extensive comments
- ✅ XML documentation for all public methods
- ✅ README.md with project overview
- ✅ QUICKSTART.md with build/run instructions
- ✅ docs/Key-Takeaways.md with detailed concept explanations
- ✅ This Functional Requirements Document

### 2.2 Out of Scope

**Advanced Topics (Covered in Later Courses):**
- Object-oriented programming (classes, objects, inheritance)
- LINQ (Language Integrated Query)
- Asynchronous programming (async/await)
- Exception handling (try-catch-finally)
- File I/O operations
- Collections (List<T>, Dictionary<K,V>, etc.)
- Delegates and events
- Generics
- Database connectivity
- Web development frameworks
- GUI applications
- Unit testing frameworks

---

## 3. Functional Requirements

### FR-1: Program Structure and Organization

**Priority**: Critical  
**Status**: ✅ Implemented

**Requirements:**
- FR-1.1: Program must be organized into a Main method and 15 demonstration methods
- FR-1.2: Each demonstration method must focus on one core concept
- FR-1.3: Main method must call all demonstration methods in logical order
- FR-1.4: Methods must use clear, descriptive names (e.g., DemonstrateVariables)

**Acceptance Criteria:**
- ✅ Program compiles without errors
- ✅ All 15 methods are defined and callable
- ✅ Main method orchestrates the demonstration flow
- ✅ Method names follow C# naming conventions (PascalCase)

---

### FR-2: Output Demonstrations

**Priority**: Critical  
**Status**: ✅ Implemented

**Requirements:**
- FR-2.1: Demonstrate Console.WriteLine() for line-based output
- FR-2.2: Demonstrate Console.Write() for inline output
- FR-2.3: Show the difference between the two methods
- FR-2.4: Include visual separators in output for clarity

**Acceptance Criteria:**
- ✅ DemonstrateOutput() method exists
- ✅ Examples show WriteLine adds new lines
- ✅ Examples show Write does not add new lines
- ✅ Output is clear and educational

---

### FR-3: Comment Demonstrations

**Priority**: Critical  
**Status**: ✅ Implemented

**Requirements:**
- FR-3.1: Show single-line comments (//)
- FR-3.2: Show multi-line comments (/* */)
- FR-3.3: Demonstrate XML documentation comments (///)
- FR-3.4: Explain the purpose of each comment type

**Acceptance Criteria:**
- ✅ All three comment types are demonstrated
- ✅ Code includes educational comments throughout
- ✅ XML documentation present on all public methods
- ✅ Comments explain "why" not just "what"

---

### FR-4: Variable Demonstrations

**Priority**: Critical  
**Status**: ✅ Implemented

**Requirements:**
- FR-4.1: Demonstrate variable declaration
- FR-4.2: Show variable initialization with values
- FR-4.3: Demonstrate variable reassignment
- FR-4.4: Use meaningful variable names
- FR-4.5: Follow C# naming conventions (camelCase for variables)

**Acceptance Criteria:**
- ✅ DemonstrateVariables() method exists
- ✅ Multiple variables declared and initialized
- ✅ Variables reassigned to show mutability
- ✅ Output shows variable values

---

### FR-5: Data Type Demonstrations

**Priority**: Critical  
**Status**: ✅ Implemented

**Requirements:**
- FR-5.1: Demonstrate integer types (int, long)
- FR-5.2: Demonstrate floating-point types (float, double)
- FR-5.3: Demonstrate character type (char)
- FR-5.4: Demonstrate boolean type (bool)
- FR-5.5: Demonstrate string type
- FR-5.6: Show Min and Max values for numeric types

**Acceptance Criteria:**
- ✅ DemonstrateDataTypes() method exists
- ✅ At least 7 different data types demonstrated
- ✅ Type suffixes shown (L for long, F for float)
- ✅ Output displays type names and sample values

---

### FR-6: Type Casting Demonstrations

**Priority**: High  
**Status**: ✅ Implemented

**Requirements:**
- FR-6.1: Demonstrate implicit casting (automatic)
- FR-6.2: Demonstrate explicit casting (manual with potential data loss)
- FR-6.3: Show conversion using Convert class
- FR-6.4: Explain when each casting type is appropriate

**Acceptance Criteria:**
- ✅ DemonstrateTypeCasting() method exists
- ✅ Implicit casting example (int to double)
- ✅ Explicit casting example (double to int)
- ✅ Convert.ToInt32() and ToString() examples
- ✅ Comments explain data loss in explicit casting

---

### FR-7: User Input Demonstrations

**Priority**: High  
**Status**: ✅ Implemented (Optional)

**Requirements:**
- FR-7.1: Demonstrate Console.ReadLine() for user input
- FR-7.2: Show how to handle nullable string (string?)
- FR-7.3: Demonstrate converting string input to numbers
- FR-7.4: Provide safe conversion examples
- FR-7.5: Make this demonstration optional (commented out by default)

**Acceptance Criteria:**
- ✅ DemonstrateUserInput() method exists
- ✅ Method is commented out in Main by default
- ✅ Comments explain how to enable user input
- ✅ Code shows safe null handling
- ✅ Instructions in QUICKSTART.md for enabling

---

### FR-8: Operator Demonstrations

**Priority**: Critical  
**Status**: ✅ Implemented

**Requirements:**
- FR-8.1: Demonstrate arithmetic operators (+, -, *, /, %)
- FR-8.2: Demonstrate assignment operators (=, +=, -=, *=, /=, %=)
- FR-8.3: Demonstrate comparison operators (==, !=, >, <, >=, <=)
- FR-8.4: Demonstrate logical operators (&&, ||, !)
- FR-8.5: Show operator precedence examples

**Acceptance Criteria:**
- ✅ DemonstrateOperators() method exists
- ✅ All four operator categories demonstrated
- ✅ Practical examples with real values
- ✅ Output shows operation results

---

### FR-9: Math Operations Demonstrations

**Priority**: High  
**Status**: ✅ Implemented

**Requirements:**
- FR-9.1: Demonstrate Math.Max() and Math.Min()
- FR-9.2: Demonstrate Math.Sqrt() for square root
- FR-9.3: Demonstrate Math.Abs() for absolute value
- FR-9.4: Demonstrate Math.Round() for rounding
- FR-9.5: Demonstrate Math.Pow() for exponentiation
- FR-9.6: Show Math.PI constant

**Acceptance Criteria:**
- ✅ DemonstrateMath() method exists
- ✅ At least 6 Math class methods demonstrated
- ✅ Practical examples with explanations
- ✅ Output shows calculated results

---

### FR-10: String Demonstrations

**Priority**: Critical  
**Status**: ✅ Implemented

**Requirements:**
- FR-10.1: Demonstrate string concatenation with + operator
- FR-10.2: Demonstrate string interpolation ($"")
- FR-10.3: Show accessing individual characters with indexer
- FR-10.4: Show special characters (escape sequences)
- FR-10.5: Demonstrate .Length property
- FR-10.6: Show string immutability concept

**Acceptance Criteria:**
- ✅ DemonstrateStrings() method exists
- ✅ Concatenation and interpolation both shown
- ✅ Character access with [index] demonstrated
- ✅ Escape sequences (\n, \t, \\, \") shown
- ✅ Comments explain string immutability

---

### FR-11: Boolean Demonstrations

**Priority**: High  
**Status**: ✅ Implemented

**Requirements:**
- FR-11.1: Demonstrate boolean variables (true/false)
- FR-11.2: Show boolean expressions from comparisons
- FR-11.3: Demonstrate logical operations (AND, OR, NOT)
- FR-11.4: Explain boolean logic in decision-making

**Acceptance Criteria:**
- ✅ DemonstrateBooleans() method exists
- ✅ Boolean literals and expressions shown
- ✅ Comparison operations yielding booleans
- ✅ Logical operators demonstrated

---

### FR-12: If-Else Demonstrations

**Priority**: Critical  
**Status**: ✅ Implemented

**Requirements:**
- FR-12.1: Demonstrate simple if statement
- FR-12.2: Demonstrate if-else statement
- FR-12.3: Demonstrate if-else if-else chain
- FR-12.4: Show ternary operator as shorthand
- FR-12.5: Demonstrate nested if statements
- FR-12.6: Include practical examples (age checking, grading)

**Acceptance Criteria:**
- ✅ DemonstrateIfElse() method exists
- ✅ All five if-else variations shown
- ✅ Ternary operator example included
- ✅ Practical, relatable examples used

---

### FR-13: Switch Demonstrations

**Priority**: High  
**Status**: ✅ Implemented

**Requirements:**
- FR-13.1: Demonstrate switch with integer values
- FR-13.2: Demonstrate switch with string values
- FR-13.3: Show default case handling
- FR-13.4: Demonstrate multiple cases with same action
- FR-13.5: Include break statements on all cases
- FR-13.6: Provide practical examples

**Acceptance Criteria:**
- ✅ DemonstrateSwitch() method exists
- ✅ Integer switch example (day of week)
- ✅ String switch example (color, month, or command)
- ✅ All cases include break statements
- ✅ Default case handles unexpected values

---

### FR-14: While Loop Demonstrations

**Priority**: Critical  
**Status**: ✅ Implemented

**Requirements:**
- FR-14.1: Demonstrate while loop
- FR-14.2: Demonstrate do-while loop
- FR-14.3: Show the difference between the two
- FR-14.4: Include counter increment in loop body
- FR-14.5: Show that do-while runs at least once

**Acceptance Criteria:**
- ✅ DemonstrateWhileLoop() method exists
- ✅ While loop with counter demonstrated
- ✅ Do-while loop with counter demonstrated
- ✅ Comments explain when to use each
- ✅ Output shows loop iterations

---

### FR-15: For Loop Demonstrations

**Priority**: Critical  
**Status**: ✅ Implemented

**Requirements:**
- FR-15.1: Demonstrate basic for loop with increment
- FR-15.2: Demonstrate for loop with decrement (countdown)
- FR-15.3: Demonstrate foreach loop with array
- FR-15.4: Show nested for loops
- FR-15.5: Include examples with different step values

**Acceptance Criteria:**
- ✅ DemonstrateForLoop() method exists
- ✅ Count-up for loop (i++)
- ✅ Count-down for loop (i--)
- ✅ Step-by-2 example (i += 2)
- ✅ Foreach with array example

---

### FR-16: Break and Continue Demonstrations

**Priority**: High  
**Status**: ✅ Implemented

**Requirements:**
- FR-16.1: Demonstrate break statement to exit loop
- FR-16.2: Demonstrate continue statement to skip iteration
- FR-16.3: Show practical examples (finding first match, skipping evens)
- FR-16.4: Explain when to use each

**Acceptance Criteria:**
- ✅ DemonstrateBreakContinue() method exists
- ✅ Break example (exit on condition)
- ✅ Continue example (skip on condition)
- ✅ Comments explain behavior
- ✅ Output shows which numbers are processed/skipped

---

### FR-17: Array Demonstrations

**Priority**: Critical  
**Status**: ✅ Implemented

**Requirements:**
- FR-17.1: Demonstrate array declaration with initialization
- FR-17.2: Show accessing array elements with indexer
- FR-17.3: Demonstrate .Length property
- FR-17.4: Show iterating with for loop
- FR-17.5: Show iterating with foreach loop
- FR-17.6: Demonstrate multi-dimensional arrays (optional)
- FR-17.7: Include string arrays and numeric arrays

**Acceptance Criteria:**
- ✅ DemonstrateArrays() method exists
- ✅ String array declared and initialized
- ✅ Array elements accessed by index
- ✅ .Length property used
- ✅ For loop iterates through array
- ✅ Foreach loop iterates through array
- ✅ Multi-dimensional array example included

---

## 4. Non-Functional Requirements

### NFR-1: Code Quality

**Priority**: Critical  
**Status**: ✅ Met

**Requirements:**
- NFR-1.1: All code must follow C# naming conventions
- NFR-1.2: All public methods must have XML documentation
- NFR-1.3: Code must include educational inline comments
- NFR-1.4: Variable and method names must be descriptive
- NFR-1.5: Code must be properly indented and formatted

**Acceptance Criteria:**
- ✅ PascalCase for methods and classes
- ✅ camelCase for local variables
- ✅ XML summary tags on all public methods
- ✅ Comments explain concepts, not just code
- ✅ Consistent 4-space indentation

---

### NFR-2: Educational Value

**Priority**: Critical  
**Status**: ✅ Met

**Requirements:**
- NFR-2.1: Code must be beginner-friendly
- NFR-2.2: Examples must be practical and relatable
- NFR-2.3: Output must be clear and well-formatted
- NFR-2.4: Demonstrations must progress from simple to complex
- NFR-2.5: Code must avoid advanced C# features
- NFR-2.6: Documentation must explain concepts thoroughly

**Acceptance Criteria:**
- ✅ No complex language features (LINQ, async, etc.)
- ✅ Examples use familiar concepts (age, names, scores)
- ✅ Section headers clearly separate demonstrations
- ✅ Key-Takeaways.md provides detailed explanations
- ✅ QUICKSTART.md guides beginners through setup

---

### NFR-3: Platform Compatibility

**Priority**: High  
**Status**: ✅ Met

**Requirements:**
- NFR-3.1: Must target .NET 10.0 LTS
- NFR-3.2: Must be compatible with .NET 8.0 and 9.0
- NFR-3.3: Must run on Windows, macOS, and Linux
- NFR-3.4: Must use only .NET base class libraries
- NFR-3.5: No external dependencies or NuGet packages

**Acceptance Criteria:**
- ✅ Target framework: net10.0
- ✅ Verified on .NET 10.0.103 SDK
- ✅ Tested on macOS (primary development environment)
- ✅ Only System namespace used
- ✅ No external package references

---

### NFR-4: Performance

**Priority**: Medium  
**Status**: ✅ Met

**Requirements:**
- NFR-4.1: Program must run in under 5 seconds
- NFR-4.2: Memory usage must be minimal (<50 MB)
- NFR-4.3: No infinite loops or blocking operations
- NFR-4.4: User input feature must be optional (not block by default)

**Acceptance Criteria:**
- ✅ Program executes in under 1 second
- ✅ Memory usage well under 50 MB
- ✅ All loops have proper termination conditions
- ✅ DemonstrateUserInput() commented out by default

---

### NFR-5: Maintainability

**Priority**: High  
**Status**: ✅ Met

**Requirements:**
- NFR-5.1: Code structure must be modular (one concept per method)
- NFR-5.2: Methods must be independently testable
- NFR-5.3: Adding new demonstrations must be straightforward
- NFR-5.4: Documentation must be easy to update
- NFR-5.5: Project must follow ITEC323 repository guidelines

**Acceptance Criteria:**
- ✅ Each demonstration in separate method
- ✅ No interdependencies between demonstrations
- ✅ Clear pattern for adding new methods
- ✅ Documentation mirrors project structure
- ✅ Follows AGENTS.md guidelines

---

## 5. Technical Requirements

### TR-1: Development Environment

**Minimum Requirements:**
- .NET SDK 8.0 or higher (verified with 10.0.103)
- Any text editor or IDE (VS Code, Visual Studio, Rider)
- Terminal/command-line access
- Operating System: Windows 10+, macOS 10.15+, or Linux

**Recommended:**
- Visual Studio Code with C# Dev Kit extension
- .NET 10.0 SDK for latest features
- Git for version control

---

### TR-2: Build Configuration

**Project File Settings:**
```xml
<PropertyGroup>
  <OutputType>Exe</OutputType>
  <TargetFramework>net10.0</TargetFramework>
  <ImplicitUsings>disable</ImplicitUsings>
  <Nullable>enable</Nullable>
</PropertyGroup>
```

**Rationale:**
- OutputType=Exe: Console application
- TargetFramework=net10.0: Latest LTS version
- ImplicitUsings=disable: Explicit using statements for educational clarity
- Nullable=enable: Modern C# nullable reference types

---

### TR-3: Output Requirements

**Program Output Must:**
- Display clear section headers (=== Section Name ===)
- Show example values for each concept
- Format output for readability
- Use consistent spacing and alignment
- Avoid excessive blank lines

**Example Output Format:**
```
=== 1. C# Output ===
This is with WriteLine (adds new line)
This is with Write (no new line)All on same line

=== 2. Variables ===
age = 25
name = Alice
...
```

---

## 6. Constraints and Limitations

### 6.1 Technical Constraints
- Must use only .NET base class libraries (no third-party packages)
- Cannot use advanced C# features (async, LINQ, reflection)
- Must remain a console application (no GUI)
- User input must be optional (commented out by default)

### 6.2 Educational Constraints
- Must avoid concepts not yet taught in ITEC323
- Examples must be culturally neutral and inclusive
- Code must be accessible to beginners with no prior C# experience
- Must not assume knowledge of object-oriented programming

### 6.3 Time Constraints
- Project must be completable in one 2-hour lab session
- Students should be able to read and understand all code
- Modifications should be simple enough for beginners

---

## 7. Success Criteria

### 7.1 Project Completion Checklist

**Code:**
- ✅ All 17 functional requirements implemented
- ✅ Program compiles without errors or warnings
- ✅ Program runs and produces expected output
- ✅ All 15 demonstration methods work correctly
- ✅ Code follows C# naming conventions
- ✅ All public methods have XML documentation

**Documentation:**
- ✅ README.md: Project overview, learning objectives, prerequisites
- ✅ QUICKSTART.md: Build and run instructions with examples
- ✅ docs/Key-Takeaways.md: Comprehensive concept explanations
- ✅ FRD.md: This complete Functional Requirements Document

**Testing:**
- ✅ Program builds successfully with `dotnet build`
- ✅ Program runs successfully with `dotnet run`
- ✅ All 15 demonstrations execute without errors
- ✅ Output is clear, formatted, and educational
- ✅ Optional user input feature can be enabled

**Quality Assurance:**
- ✅ No compiler errors or warnings
- ✅ No runtime exceptions during normal execution
- ✅ Code is formatted consistently
- ✅ Comments are accurate and helpful
- ✅ Documentation matches implementation

---

### 7.2 Educational Success Metrics

**Student Learning:**
- Students can explain each of the 15 core C# concepts
- Students can modify the code to add their own examples
- Students can write simple C# programs using these concepts
- Students understand when to use each programming construct

**Code Comprehension:**
- Students can read and understand 100% of the code
- Students can trace program execution flow
- Students recognize C# syntax patterns
- Students appreciate the importance of code documentation

---

## 8. Verification and Validation

### 8.1 Verification Status

**Build Verification:**
- ✅ Project builds without errors: `dotnet build`
- ✅ No compiler warnings
- ✅ Target framework correctly set to net10.0
- ✅ Project file is valid and complete

**Runtime Verification:**
- ✅ Program runs without exceptions: `dotnet run`
- ✅ All 15 demonstrations execute successfully
- ✅ Output matches expected format
- ✅ Program completes in under 5 seconds

**Code Quality Verification:**
- ✅ All methods follow naming conventions
- ✅ XML documentation present on all public methods
- ✅ Inline comments explain concepts clearly
- ✅ Code is properly formatted and indented

**Documentation Verification:**
- ✅ All required files present and complete
- ✅ Build instructions tested and work correctly
- ✅ Code examples in docs match actual implementation
- ✅ No broken links or references

---

### 8.2 Validation Environment

**Tested On:**
- Operating System: macOS (primary)
- .NET SDK Version: 10.0.103
- IDE: Visual Studio Code
- Terminal: zsh

**Expected to Work On:**
- Windows 10/11 with .NET 10.0 SDK
- Linux distributions with .NET 10.0 SDK
- .NET SDK versions 8.0.x, 9.0.x, 10.0.x

---

## 9. Risk Assessment

### 9.1 Technical Risks

| Risk | Probability | Impact | Mitigation |
|------|------------|--------|------------|
| .NET SDK not installed | Medium | High | Clear prerequisites in documentation |
| User input causes blocking | Low | Medium | Feature commented out by default |
| Array index out of bounds | Very Low | Low | All array access uses .Length checks |
| Division by zero | Very Low | Low | No user-input calculations by default |

### 9.2 Educational Risks

| Risk | Probability | Impact | Mitigation |
|------|------------|--------|------------|
| Too complex for beginners | Low | High | Code reviewed for simplicity |
| Overwhelming amount of info | Medium | Medium | Clear sectioning and documentation |
| Students skip reading docs | Medium | Medium | Code includes extensive comments |
| Confusion about nullable | Low | Low | Comments explain string? syntax |

---

## 10. Future Enhancements

### Potential Additions (Out of Scope for v1.0)

**Additional Concepts:**
- Methods with parameters and return values
- Variable scope and lifetime
- Constant vs variable
- Enumeration types (enum)
- Struct types
- Nullable value types (int?, double?)

**Interactive Features:**
- Menu-driven interface to select demonstrations
- Interactive exercises after each concept
- Quiz mode to test understanding
- Save output to file

**Code Quality:**
- Unit tests for each demonstration
- Code analysis and style checking
- Performance benchmarks
- CI/CD pipeline

---

## 11. Glossary

| Term | Definition |
|------|------------|
| **Console Application** | A program that runs in a terminal/command-line interface |
| **Method** | A named block of code that performs a specific task |
| **Variable** | A named container that stores a value |
| **Data Type** | Specifies what kind of data a variable can hold |
| **Loop** | A programming construct that repeats code multiple times |
| **Casting** | Converting a value from one data type to another |
| **XML Documentation** | Special comments that provide IntelliSense information |
| **.NET SDK** | Software Development Kit for building .NET applications |
| **LTS** | Long-Term Support - a stable version with extended support |

---

## 12. References

### Documentation
- Microsoft C# Documentation: https://learn.microsoft.com/en-us/dotnet/csharp/
- .NET Documentation: https://learn.microsoft.com/en-us/dotnet/
- C# Language Specification: https://learn.microsoft.com/en-us/dotnet/csharp/language-reference/

### Project Documentation
- [README.md](README.md): Project overview and learning objectives
- [QUICKSTART.md](QUICKSTART.md): Build and run instructions
- [docs/Key-Takeaways.md](docs/Key-Takeaways.md): Detailed concept explanations

### Repository Guidelines
- [AGENTS.md](/AGENTS.md): AI agent contribution guidelines
- [docs/agents/code-generation.md](/docs/agents/code-generation.md): Code standards
- [docs/agents/documentation.md](/docs/agents/documentation.md): Documentation standards

---

## 13. Approval and Sign-Off

| Role | Name | Date | Status |
|------|------|------|--------|
| Project Lead | ITEC323 Course Team | March 2026 | ✅ Approved |
| Technical Review | AI Assistant | March 2026 | ✅ Complete |
| Quality Assurance | Build System | March 2026 | ✅ Verified |

---

## 14. Document History

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0 | March 2026 | ITEC323 Team | Initial creation and complete implementation |

