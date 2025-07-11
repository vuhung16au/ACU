#!/bin/bash

# Java Basic Projects Generator Script
# Course: ITEC313 - Advanced Programming Concepts

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${GREEN}Java Basic Projects Generator${NC}"
echo -e "${GREEN}===============================${NC}\n"

# Base directory
BASE_DIR="/Users/vuhung/00.Work/02.ACU/github/ITEC313/00.Java-Basic"

# Function to create project structure
create_project() {
    local project_name=$1
    local class_name=$2
    local description=$3
    
    echo -e "${BLUE}Creating project: ${project_name}${NC}"
    
    # Create main directory
    mkdir -p "${BASE_DIR}/${project_name}"
    
    # Create subdirectories
    mkdir -p "${BASE_DIR}/${project_name}/examples"
    mkdir -p "${BASE_DIR}/${project_name}/data"
    mkdir -p "${BASE_DIR}/${project_name}/tests"
    mkdir -p "${BASE_DIR}/${project_name}/docs"
    
    # Create basic Makefile
    cat > "${BASE_DIR}/${project_name}/Makefile" << EOF
# ${project_name} - Makefile
# Course: ITEC313 - Object-Oriented Programming

JAVAC = javac
JAVA = java
MAIN_CLASS = ${class_name}

.PHONY: all compile run clean help

all: compile

compile:
	\$(JAVAC) \$(MAIN_CLASS).java

run: compile
	\$(JAVA) \$(MAIN_CLASS)

clean:
	rm -f *.class examples/*.class tests/*.class

help:
	@echo "Available targets:"
	@echo "  compile - Compile the program"
	@echo "  run     - Compile and run the program"
	@echo "  clean   - Remove compiled files"
	@echo "  help    - Show this help"
EOF

    # Create basic README
    cat > "${BASE_DIR}/${project_name}/README.md" << EOF
# ${project_name}

${description}

## 📋 Overview

This program demonstrates key concepts in Java programming for the ITEC313 Object-Oriented Programming course.

## 📁 Files in this Directory

\`\`\`
${project_name}/
├── ${class_name}.java    # Main source code
├── Makefile              # Build automation
├── README.md             # This documentation
├── examples/             # Additional examples
├── data/                 # Sample data files
├── tests/                # Unit tests
└── docs/                 # Additional documentation
\`\`\`

## 🛠 Building and Running

\`\`\`bash
# Compile the program
make compile

# Run the program
make run

# Clean compiled files
make clean
\`\`\`

## 📚 Learning Objectives

This project teaches:
- ${description}
- Practical application of Java concepts
- Best practices in code organization
- Error handling and input validation

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
EOF

    # Create basic concept documentation
    cat > "${BASE_DIR}/${project_name}/docs/concepts.md" << EOF
# ${project_name} - Concepts

## Overview

${description}

## Key Concepts

### Main Learning Points

1. **Concept 1**: Description of key concept
2. **Concept 2**: Description of key concept
3. **Concept 3**: Description of key concept

### Best Practices

- Follow Java naming conventions
- Use appropriate data types
- Handle errors gracefully
- Write clear, commented code

### Common Pitfalls

- Avoid common mistakes
- Understand scope and lifetime
- Be aware of performance implications

## Further Reading

- Oracle Java Documentation
- Java Language Specification
- Best Practices Guides
EOF

    echo -e "${GREEN}✓ Created ${project_name}${NC}"
}

# Priority 1: Essential Java Basics
echo -e "${YELLOW}Priority 1: Essential Java Basics${NC}"
echo "=================================="

create_project "Variables-DataTypes" "VariablesDataTypes" "Primitive data types, wrapper classes, type conversion"
create_project "Input-Output" "InputOutput" "Scanner, BufferedReader, command-line arguments"
create_project "Operators-Expressions" "OperatorsExpressions" "All operator types, precedence, expressions"
create_project "Constants-Finals" "ConstantsFinals" "Final variables, naming conventions"
create_project "If-Else-Conditions" "IfElseConditions" "Conditional logic, nested conditions, ternary"
create_project "Switch-Statement" "SwitchStatement" "Switch cases, break, default, enhanced switch"
create_project "While-Loops" "WhileLoops" "While, do-while, infinite loops"
create_project "For-Loops" "ForLoops" "Traditional for, enhanced for, nested loops"
create_project "Loop-Control" "LoopControl" "Break, continue, labeled statements"
create_project "Methods-Basic" "MethodsBasic" "Method creation, parameters, return types"
create_project "Method-Overloading" "MethodOverloading" "Multiple methods with same name"
create_project "Recursion" "Recursion" "Recursive methods, base cases, call stack"
create_project "Variable-Scope" "VariableScope" "Local, instance, class scope"

echo ""

# Priority 2: Object-Oriented Fundamentals
echo -e "${YELLOW}Priority 2: Object-Oriented Fundamentals${NC}"
echo "========================================"

create_project "Classes-Objects" "ClassesObjects" "Class definition, object instantiation"
create_project "Constructors" "Constructors" "Default, parameterized, constructor chaining"
create_project "Instance-Variables" "InstanceVariables" "Fields, initialization, this keyword"
create_project "Static-Members" "StaticMembers" "Static variables, methods, blocks"
create_project "Encapsulation" "Encapsulation" "Private fields, getters/setters, data hiding"
create_project "Inheritance-Basic" "InheritanceBasic" "Extends, super keyword, method overriding"
create_project "Abstract-Classes" "AbstractClasses" "Abstract classes and methods"
create_project "Interfaces-Basic" "InterfacesBasic" "Interface implementation, default methods"
create_project "Polymorphism" "Polymorphism" "Runtime polymorphism, casting"

echo ""

# Priority 3: Data Structures & Collections
echo -e "${YELLOW}Priority 3: Data Structures & Collections${NC}"
echo "========================================"

create_project "Arrays-Basic" "ArraysBasic" "Array declaration, initialization, access"
create_project "Multidimensional-Arrays" "MultidimensionalArrays" "2D arrays, jagged arrays"
create_project "Array-Algorithms" "ArrayAlgorithms" "Sorting, searching, manipulation"
create_project "ArrayList-Basic" "ArrayListBasic" "Dynamic arrays, common operations"
create_project "LinkedList" "LinkedList" "Linked list operations, comparison with ArrayList"
create_project "HashMap-Basic" "HashMapBasic" "Key-value pairs, basic operations"
create_project "HashSet" "HashSet" "Unique elements, set operations"
create_project "Collections-Utility" "CollectionsUtility" "Collections class methods"

echo ""

# Priority 4: Error Handling & Debugging
echo -e "${YELLOW}Priority 4: Error Handling & Debugging${NC}"
echo "====================================="

create_project "Error-Exception-Handling" "ErrorExceptionHandling" "Try-catch, finally, exception hierarchy"
create_project "Custom-Exceptions" "CustomExceptions" "Creating custom exception classes"
create_project "Debugging-Techniques" "DebuggingTechniques" "Print debugging, IDE debugging"
create_project "Unit-Testing-Basic" "UnitTestingBasic" "JUnit basics, test methods"

echo ""

# Priority 5: File & I/O Operations
echo -e "${YELLOW}Priority 5: File & I/O Operations${NC}"
echo "==============================="

create_project "File-Handling" "FileHandling" "File I/O, FileReader, FileWriter"
create_project "Text-File-Processing" "TextFileProcessing" "Reading/writing text files"
create_project "CSV-File-Handling" "CSVFileHandling" "Working with CSV files"
create_project "Serialization-Basic" "SerializationBasic" "Object serialization concepts"

echo ""

# Priority 6: String Processing
echo -e "${YELLOW}Priority 6: String Processing${NC}"
echo "============================="

create_project "String-Manipulation" "StringManipulation" "String methods, immutability"
create_project "StringBuilder-StringBuffer" "StringBuilderStringBuffer" "Mutable strings, performance"
create_project "Regular-Expressions" "RegularExpressions" "Pattern matching, validation"
create_project "String-Algorithms" "StringAlgorithms" "Common string algorithms"

echo ""
echo -e "${GREEN}All project structures created successfully!${NC}"
echo -e "${GREEN}Total projects: 35${NC}"

# Create a master index
cat > "${BASE_DIR}/PROJECT_INDEX.md" << EOF
# Java Basic Projects - Index

This directory contains a comprehensive collection of Java programming projects organized by difficulty and learning progression.

## 📚 Project Organization

### Priority 1: Essential Java Basics (13 projects)
#### Core Syntax & Data Types
- [Variables-DataTypes](Variables-DataTypes/) - Primitive types, wrapper classes, type conversion
- [Input-Output](Input-Output/) - Scanner, BufferedReader, command-line arguments  
- [Operators-Expressions](Operators-Expressions/) - All operator types, precedence, expressions
- [Constants-Finals](Constants-Finals/) - Final variables, naming conventions

#### Control Structures
- [If-Else-Conditions](If-Else-Conditions/) - Conditional logic, nested conditions, ternary
- [Switch-Statement](Switch-Statement/) - Switch cases, break, default, enhanced switch
- [While-Loops](While-Loops/) - While, do-while, infinite loops
- [For-Loops](For-Loops/) - Traditional for, enhanced for, nested loops
- [Loop-Control](Loop-Control/) - Break, continue, labeled statements

#### Methods & Functions
- [Methods-Basic](Methods-Basic/) - Method creation, parameters, return types
- [Method-Overloading](Method-Overloading/) - Multiple methods with same name
- [Recursion](Recursion/) - Recursive methods, base cases, call stack
- [Variable-Scope](Variable-Scope/) - Local, instance, class scope

### Priority 2: Object-Oriented Fundamentals (9 projects)
#### Basic OOP
- [Classes-Objects](Classes-Objects/) - Class definition, object instantiation
- [Constructors](Constructors/) - Default, parameterized, constructor chaining
- [Instance-Variables](Instance-Variables/) - Fields, initialization, this keyword
- [Static-Members](Static-Members/) - Static variables, methods, blocks
- [Encapsulation](Encapsulation/) - Private fields, getters/setters, data hiding

#### Inheritance & Polymorphism
- [Inheritance-Basic](Inheritance-Basic/) - Extends, super keyword, method overriding
- [Abstract-Classes](Abstract-Classes/) - Abstract classes and methods
- [Interfaces-Basic](Interfaces-Basic/) - Interface implementation, default methods
- [Polymorphism](Polymorphism/) - Runtime polymorphism, casting

### Priority 3: Data Structures & Collections (8 projects)
#### Arrays
- [Arrays-Basic](Arrays-Basic/) - Array declaration, initialization, access
- [Multidimensional-Arrays](Multidimensional-Arrays/) - 2D arrays, jagged arrays
- [Array-Algorithms](Array-Algorithms/) - Sorting, searching, manipulation

#### Collections Framework
- [ArrayList-Basic](ArrayList-Basic/) - Dynamic arrays, common operations
- [LinkedList](LinkedList/) - Linked list operations, comparison with ArrayList
- [HashMap-Basic](HashMap-Basic/) - Key-value pairs, basic operations
- [HashSet](HashSet/) - Unique elements, set operations
- [Collections-Utility](Collections-Utility/) - Collections class methods

### Priority 4: Error Handling & Debugging (4 projects)
- [Error-Exception-Handling](Error-Exception-Handling/) - Try-catch, finally, exception hierarchy
- [Custom-Exceptions](Custom-Exceptions/) - Creating custom exception classes
- [Debugging-Techniques](Debugging-Techniques/) - Print debugging, IDE debugging
- [Unit-Testing-Basic](Unit-Testing-Basic/) - JUnit basics, test methods

### Priority 5: File & I/O Operations (4 projects)
- [File-Handling](File-Handling/) - File I/O, FileReader, FileWriter
- [Text-File-Processing](Text-File-Processing/) - Reading/writing text files
- [CSV-File-Handling](CSV-File-Handling/) - Working with CSV files
- [Serialization-Basic](Serialization-Basic/) - Object serialization concepts

### Priority 6: String Processing (4 projects)
- [String-Manipulation](String-Manipulation/) - String methods, immutability
- [StringBuilder-StringBuffer](StringBuilder-StringBuffer/) - Mutable strings, performance
- [Regular-Expressions](Regular-Expressions/) - Pattern matching, validation
- [String-Algorithms](String-Algorithms/) - Common string algorithms

## 🎯 Learning Path

1. **Start with Priority 1** - Essential syntax and basic programming concepts
2. **Move to Priority 2** - Object-oriented programming fundamentals
3. **Progress through Priority 3-6** - Advanced topics and practical applications

## 🛠 Usage

Each project contains:
- Complete source code with detailed comments
- Makefile for easy compilation and execution
- Comprehensive README with examples
- Additional example files
- Unit tests where applicable
- Detailed concept documentation

## 📝 Getting Started

\`\`\`bash
# Navigate to any project
cd Variables-DataTypes

# Compile and run
make run

# View help
make help
\`\`\`

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: Australian Catholic University (ACU)  
**Date**: July 11, 2025
EOF

echo -e "${GREEN}Master index created: PROJECT_INDEX.md${NC}"
echo -e "${YELLOW}Next steps:${NC}"
echo "1. Review the generated project structures"
echo "2. Customize the main Java files for each project"
echo "3. Add specific examples and test cases"
echo "4. Update documentation as needed"
