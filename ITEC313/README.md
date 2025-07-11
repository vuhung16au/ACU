# ITEC313 - Object-Oriented Programming

This repository contains coursework and projects for **ITEC313 - Object-Oriented Programming** course. The repository is organized to provide a comprehensive learning path through Java programming fundamentals and object-oriented programming concepts.

## 📁 Repository Structure

```
ITEC313/
├── 00.Java-Basic/           # Basic Java programming examples
│   └── HelloWorld/         # Simple Hello World program
│       ├── HelloWorld.java # Java source code
│       ├── Makefile        # GNU Makefile for building
│       └── README.md       # This file
├── 00.Overview-Preparation/ # Course preparation materials
│   ├── 00.Setup-Java.md    # Java development environment setup
│   ├── 01.Java-IDE.md      # Integrated Development Environment guide
│   ├── 02.Java-Build-Tools.md # Build tools overview
│   └── HelloWorld.java     # Additional HelloWorld example
└── README.md               # Repository overview (this file)
```

## 🎯 Course Objectives

This repository covers the following key learning objectives:

1. **Java Fundamentals**
   - Basic syntax and structure
   - Variables, data types, and operators
   - Control structures (loops, conditionals)
   - Methods and parameters

2. **Object-Oriented Programming Concepts**
   - Classes and objects
   - Encapsulation
   - Inheritance
   - Polymorphism
   - Abstraction

3. **Advanced Topics**
   - Exception handling
   - File I/O operations
   - Collections framework
   - Design patterns

## 🚀 Getting Started

### Prerequisites

Before running the code in this repository, ensure you have the following installed:

- **Java Development Kit (JDK) 8 or higher**
  - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
- **GNU Make** (for using Makefiles)
  - On macOS: `xcode-select --install` or `brew install make`
  - On Linux: Usually pre-installed or `sudo apt-get install make`
  - On Windows: Install via [MSYS2](https://www.msys2.org/) or use Windows Subsystem for Linux

### Quick Start - HelloWorld Program

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd ITEC313/00.Java-Basic/HelloWorld
   ```

2. **Compile and run using Makefile:**
   ```bash
   # Compile the program
   make compile
   
   # Run the program
   make run
   
   # Clean compiled files
   make clean
   
   # View all available commands
   make help
   ```

3. **Alternative: Manual compilation:**
   ```bash
   # Compile
   javac HelloWorld.java
   
   # Run
   java HelloWorld
   ```

### Expected Output

When you run the HelloWorld program, you should see:

```
Hello, World!
Welcome to Java programming!
Course: ITEC313 - Object-Oriented Programming
Year: 2025
=====================================
This program demonstrates:
1. Basic Java class structure
2. Main method implementation
3. Console output using System.out.println()
4. String variables and concatenation
5. Basic commenting practices
```

## 📚 Learning Path

### Phase 1: Java Basics (Current)
- [x] Environment setup
- [x] First Java program (HelloWorld)
- [ ] Variables and data types
- [ ] Control structures
- [ ] Methods and functions

### Phase 2: Object-Oriented Fundamentals
- [ ] Classes and objects
- [ ] Constructors
- [ ] Instance variables and methods
- [ ] Encapsulation with getters/setters

### Phase 3: Advanced OOP Concepts
- [ ] Inheritance
- [ ] Method overriding
- [ ] Polymorphism
- [ ] Abstract classes and interfaces

### Phase 4: Advanced Topics
- [ ] Exception handling
- [ ] File operations
- [ ] Collections (ArrayList, HashMap, etc.)
- [ ] Design patterns

## 🛠 Development Environment

### Recommended IDEs

1. **IntelliJ IDEA** (Recommended)
   - Professional IDE with excellent Java support
   - Built-in debugging and profiling tools

2. **Eclipse**
   - Free, open-source IDE
   - Extensive plugin ecosystem

3. **Visual Studio Code**
   - Lightweight editor with Java extensions
   - Good for beginners

### Build Tools

- **Make**: Traditional build automation (used in this project)
- **Maven**: Dependency management and build automation
- **Gradle**: Modern build tool with flexible scripting

## 📖 Code Style and Conventions

This repository follows standard Java coding conventions:

- **Class names**: PascalCase (e.g., `HelloWorld`, `StudentRecord`)
- **Method names**: camelCase (e.g., `getName()`, `calculateAverage()`)
- **Variable names**: camelCase (e.g., `studentName`, `totalScore`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_SIZE`, `DEFAULT_VALUE`)
- **Packages**: lowercase (e.g., `com.example.utils`)

### Documentation Standards

- All classes should have Javadoc comments
- Public methods should be documented
- Complex algorithms should have inline comments
- Use meaningful variable and method names

## 🤝 Contributing

If you're a student in this course:

1. Fork the repository
2. Create a feature branch for your work
3. Follow the established code style
4. Include proper documentation and comments
5. Test your code before submitting
6. Create a pull request with a clear description

## 📝 Assignment Guidelines

### Code Submission

1. **File Organization**: Keep files in appropriate directories
2. **Naming Convention**: Use descriptive names for files and classes
3. **Documentation**: Include comprehensive comments and Javadoc
4. **Testing**: Ensure your code compiles and runs correctly
5. **Clean Code**: Remove unnecessary files before submission

### Makefile Usage

Each programming assignment should include a Makefile with the following targets:
- `compile`: Compile the Java source files
- `run`: Execute the main program
- `clean`: Remove compiled files
- `help`: Display usage information

## 🐛 Troubleshooting

### Common Issues

1. **"javac not found"**
   - Solution: Install JDK and ensure it's in your PATH

2. **"Class not found"**
   - Solution: Check classpath and ensure files are compiled

3. **"Make command not found"**
   - Solution: Install GNU Make or use manual compilation

4. **Permission denied**
   - Solution: Check file permissions with `ls -la`

### Getting Help

- Check the course documentation in `00.Overview-Preparation/`
- Review error messages carefully
- Use debugging techniques taught in class
- Ask questions during office hours or class discussions

## 📄 License

This repository contains educational material for ITEC313. The code is provided for learning purposes and should not be redistributed without permission.

## 📧 Contact

- **Course**: ITEC313 - Object-Oriented Programming
- **Institution**: Australian Catholic University (ACU)
- **Year**: 2025

---

*Happy coding! Remember: programming is learned by doing, so practice regularly and don't hesitate to experiment with the code.*
