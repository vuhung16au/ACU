# JavaFX Recursion Demonstrations

A comprehensive JavaFX application that demonstrates various recursion algorithms and concepts. This project showcases different recursive implementations including factorial, Fibonacci, sorting, searching, and classic problems like Tower of Hanoi.

## Features

The application demonstrates the following recursion algorithms:

1. **Compute Factorial** - Classic recursive factorial implementation
2. **Compute Fibonacci** - Recursive Fibonacci number calculation
3. **Recursive Selection Sort** - Sorting algorithm using recursion
4. **Recursive Binary Search** - Efficient search algorithm
5. **Directory Size** - Recursive file system traversal
6. **Tower of Hanoi** - Classic recursive puzzle solution
7. **Factorial (Tail Recursion)** - Optimized tail-recursive factorial

## Technical Specifications

### Development Environment

- **Target Platform**: Cross-platform (macOS, Windows, Linux)
- **Java Version**: OpenJDK 24
- **Maven Version**: 3.9.x or later
- **JavaFX Version**: 21

### Cross-Platform Compatibility

The project is buildable and runnable on:

- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64
- **Linux**: x86_64 and ARM64

## Project Structure

```
06-01-Recursion/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/acu/javafx/recursion/
│   │   │       ├── RecursionDemo.java          # Main JavaFX application
│   │   │       ├── ComputeFactorial.java       # Factorial implementation
│   │   │       ├── ComputeFibonacci.java       # Fibonacci implementation
│   │   │       ├── RecursiveSelectionSort.java # Selection sort
│   │   │       ├── RecursiveBinarySearch.java  # Binary search
│   │   │       ├── DirectorySize.java          # Directory size calculator
│   │   │       ├── TowerOfHanoi.java          # Tower of Hanoi solver
│   │   │       └── ComputeFactorialTailRecursion.java # Tail recursion
│   │   └── resources/
│   └── test/
│       └── java/
├── pom.xml                                # Maven build configuration
├── run.sh                                 # Unix/Linux/macOS execution script
├── run.bat                                # Windows execution script
├── README.md                              # This file
├── docs/
│   ├── architecture.md                    # Architecture documentation
│   └── concepts.md                        # Concepts and design decisions
└── .gitignore                            # Git ignore rules
```

## Quick Start

### Prerequisites

1. **Java 24** (OpenJDK or Oracle JDK)
2. **Maven 3.9+**
3. **Git** (for cloning)

### Installation and Running

#### Option 1: Using Build Scripts (Recommended)

**On macOS/Linux:**
```bash
chmod +x run.sh
./run.sh
```

**On Windows:**
```cmd
run.bat
```

#### Option 2: Manual Maven Commands

```bash
# Clone the repository (if not already done)
git clone <repository-url>
cd 06-01-Recursion

# Build the project
mvn clean compile

# Run the application
mvn javafx:run
```

#### Option 3: Direct Java Execution

```bash
# Build the project
mvn clean package

# Run the JAR file
java -jar target/recursion-demo-1.0.0.jar
```

## Usage

1. **Launch the Application**: Run the application using one of the methods above
2. **Select Algorithm**: Choose an algorithm from the dropdown menu
3. **Enter Input**: Provide appropriate input based on the selected algorithm:
   - **Factorial**: Enter a non-negative integer (e.g., 5)
   - **Fibonacci**: Enter an index (e.g., 10)
   - **Selection Sort**: Enter numbers separated by spaces (e.g., 5 2 8 1 9)
   - **Binary Search**: Enter a number to search (e.g., 7)
   - **Directory Size**: Enter a directory path (e.g., .)
   - **Tower of Hanoi**: Enter number of disks (e.g., 3)
4. **Run Algorithm**: Click "Run Algorithm" to see the results
5. **View Output**: Results are displayed in the output area

## Algorithm Details

### 1. Compute Factorial
- **Input**: Non-negative integer n
- **Output**: n! (factorial of n)
- **Recursive Formula**: n! = n × (n-1)!
- **Base Case**: 0! = 1

### 2. Compute Fibonacci
- **Input**: Index n
- **Output**: Fibonacci number at index n
- **Recursive Formula**: F(n) = F(n-1) + F(n-2)
- **Base Cases**: F(0) = 0, F(1) = 1

### 3. Recursive Selection Sort
- **Input**: Array of numbers
- **Output**: Sorted array
- **Algorithm**: Find minimum, swap to front, recursively sort remainder

### 4. Recursive Binary Search
- **Input**: Sorted array and search key
- **Output**: Index of key or insertion point
- **Algorithm**: Compare middle element, recursively search left or right half

### 5. Directory Size
- **Input**: Directory path
- **Output**: Total size in bytes
- **Algorithm**: Recursively sum file sizes in directory tree

### 6. Tower of Hanoi
- **Input**: Number of disks
- **Output**: Sequence of moves to solve puzzle
- **Algorithm**: Move n-1 disks to auxiliary, move nth disk, move n-1 disks to destination

### 7. Factorial (Tail Recursion)
- **Input**: Non-negative integer n
- **Output**: n! (factorial of n)
- **Optimization**: Tail-recursive implementation for better performance

## Build Configuration

### Maven Configuration

The project uses Maven with the following key features:

- **Java 24** compilation target
- **JavaFX 21** dependencies
- **Cross-platform** dependency management
- **Platform detection** for native libraries
- **Executable JAR** creation with Maven Shade plugin

### Platform-Specific Considerations

- **macOS**: Supports both Intel and Apple Silicon architectures
- **Windows**: Supports x86_64 and ARM64 architectures
- **Linux**: Supports x86_64 and ARM64 architectures

## Development

### Adding New Algorithms

1. Create a new Java class in `src/main/java/com/acu/javafx/recursion/`
2. Implement the algorithm with appropriate static methods
3. Add the algorithm to the `RecursionDemo.java` application
4. Update the documentation

### Testing

```bash
# Run tests
mvn test

# Run with coverage
mvn jacoco:prepare-agent test jacoco:report
```

## Troubleshooting

### Common Issues

1. **JavaFX not found**: Ensure JavaFX dependencies are properly configured
2. **Platform-specific issues**: Check that the correct platform dependencies are included
3. **Memory issues**: Increase JVM heap size if needed: `-Xmx2g`

### Platform-Specific Notes

- **macOS**: May require additional permissions for file system access
- **Windows**: Ensure proper PATH configuration for Java and Maven
- **Linux**: May need additional system libraries for JavaFX

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Original algorithm implementations from Pearson's Java Programming textbook
- JavaFX team for the excellent GUI framework
- Maven community for the build tools

## Version History

- **v1.0.0**: Initial release with all seven recursion algorithms
- Cross-platform support for macOS, Windows, and Linux
- JavaFX-based user interface
- Comprehensive documentation and build scripts 