# Eight Queens JavaFX Application

A JavaFX application that demonstrates the solution to the classic Eight Queens puzzle. The application uses a backtracking algorithm to find a valid solution where eight queens are placed on an 8x8 chess board such that no queen can attack another queen.

## Project Overview

The Eight Queens puzzle is a classic constraint satisfaction problem. The goal is to place eight queens on an 8x8 chess board so that no two queens threaten each other. This means no two queens can share the same row, column, or diagonal.

## Features

- **Visual Chess Board**: 8x8 grid with clear borders
- **Queen Representation**: Gold queen symbols (♕) on the board
- **Backtracking Algorithm**: Efficient solution finding using recursive backtracking
- **Cross-Platform Support**: Runs on macOS, Windows, and Linux
- **Modern JavaFX UI**: Clean, responsive interface

## Technical Specifications

### Development Environment

- **Java Version**: OpenJDK 24
- **JavaFX Version**: 21
- **Maven Version**: 3.9.x or later
- **Target Platform**: Cross-platform (macOS, Windows, Linux)

### Architecture Support

- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64
- **Linux**: x86_64 and ARM64

## Project Structure

```
08-08-EightQueens/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/acu/javafx/eightqueens/
│       │       └── EightQueens.java          # Main application class
│       └── resources/
│           └── image/
│               └── queen.svg                  # Queen image (SVG format)
├── pom.xml                                    # Maven configuration
├── run.sh                                     # Unix/Linux/macOS execution script
├── run.bat                                    # Windows execution script
├── run_direct.sh                              # Direct Java execution script
└── README.md                                  # Project documentation
```

## Algorithm

The application uses a backtracking algorithm to solve the Eight Queens puzzle:

1. **Initialization**: Start with an empty 8x8 board
2. **Row-by-Row Placement**: Place queens one row at a time
3. **Constraint Checking**: For each placement, verify:
   - No queen in the same column
   - No queen in the same diagonal (both directions)
4. **Backtracking**: If no valid position is found in a row, backtrack to the previous row
5. **Solution Found**: When all 8 queens are placed successfully

### Key Methods

- `search()`: Main backtracking algorithm
- `findPosition(int k)`: Finds a valid position in row k
- `isValid(int row, int column)`: Checks if a position is valid

## Building and Running

### Prerequisites

- Java 24 (OpenJDK or Oracle JDK)
- Maven 3.9.x or later
- JavaFX 21

### Using Maven (Recommended)

#### Unix/Linux/macOS
```bash
chmod +x run.sh
./run.sh
```

#### Windows
```cmd
run.bat
```

### Direct Java Execution

#### Unix/Linux/macOS
```bash
chmod +x run_direct.sh
./run_direct.sh
```

### Manual Maven Commands

```bash
# Clean and compile
mvn clean compile

# Run the application
mvn javafx:run

# Package the application
mvn clean package

# Run the packaged JAR
java -jar target/eightqueens-1.0.0.jar
```

## Usage

1. Run the application using one of the provided scripts
2. The application will automatically find a solution to the Eight Queens puzzle
3. A window will display the 8x8 chess board with queens placed in valid positions
4. Each queen is represented by a gold queen symbol (♕)

## Solution Details

The application finds one valid solution to the Eight Queens puzzle. The solution ensures that:

- Each queen is in a different row
- Each queen is in a different column
- No two queens share the same diagonal

The backtracking algorithm guarantees finding a valid solution if one exists (which it does for the 8-queens problem).

## Cross-Platform Compatibility

### Platform Detection

The Maven configuration includes platform detection to automatically handle different architectures:

- **macOS**: Detects Apple Silicon (ARM64) vs Intel (x86_64)
- **Windows**: Supports both x86_64 and ARM64
- **Linux**: Supports both x86_64 and ARM64

### JavaFX Dependencies

The project uses JavaFX 21 with platform-specific dependencies managed by Maven's platform detection.

## Troubleshooting

### Common Issues

1. **JavaFX not found**: Ensure JavaFX 21 is installed and properly configured
2. **Maven not found**: Install Maven 3.9.x or later
3. **Java version issues**: Ensure Java 24 is installed and set as default

### Platform-Specific Notes

- **macOS**: JavaFX is included with recent JDK distributions
- **Windows**: May need to download JavaFX separately
- **Linux**: Install OpenJFX package: `sudo apt install openjfx` (Ubuntu/Debian)

## Development

### Adding Features

To extend the application:

1. **Multiple Solutions**: Modify the algorithm to find all 92 solutions
2. **Interactive Placement**: Allow users to place queens manually
3. **Animation**: Add visual backtracking animation
4. **Different Board Sizes**: Extend to N-queens problem

### Code Structure

The main class `EightQueens` extends `Application` and contains:

- **UI Components**: GridPane for chess board, Labels for cells
- **Algorithm Logic**: Backtracking implementation
- **Styling**: CSS styling for visual appearance

## License

This project is part of the ITEC313 JavaFX course materials.

## Contributing

This is an educational project. For questions or improvements, please refer to the course instructor.