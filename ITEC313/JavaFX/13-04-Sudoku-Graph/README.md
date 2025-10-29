# Sudoku Solver - Graph Theory Implementation

A sophisticated Sudoku solver that demonstrates advanced computer science concepts through Graph Theory algorithms and Constraint Satisfaction Problem (CSP) techniques.

## Overview

This application transforms the traditional Sudoku puzzle into a constraint graph and uses sophisticated algorithms like arc consistency, heuristic search, and constraint propagation to find solutions efficiently. It serves as an educational tool for understanding Graph Theory, Constraint Satisfaction Problems, and advanced algorithm design.

## Features

### 🎯 Core Functionality
- **Interactive 9×9 Grid**: Click cells to cycle through numbers 1→2→3→4→5→6→7→8→9→0
- **Color-coded Numbers**: Each number (1-9) has a unique color for easy identification
- **Graph Theory Solving**: Advanced constraint satisfaction algorithms
- **Solution Validation**: Check if your puzzle is solved correctly
- **Sample Puzzles**: Load pre-configured Sudoku puzzles

### 🧠 Graph Theory Features
- **Constraint Graph Visualization**: See how cells are connected through Sudoku rules
- **Arc Consistency**: Domain reduction through constraint propagation
- **Heuristic Search**: Most Constrained Variable and Least Constraining Value strategies
- **Forward Checking**: Real-time constraint propagation during solving
- **Interactive Graph Explorer**: Visualize constraint relationships

### 🎨 User Interface
- **ACU Brand Colors**: Professional color scheme using university branding
- **Help System**: Comprehensive help and information dialogs
- **Algorithm Information**: Learn about the solving algorithms
- **Strategy Explanation**: Understand the solving strategy
- **About Dialog**: Application information and educational value

## Quick Start

### Prerequisites
- Java 24 or higher
- Maven 3.6 or higher
- JavaFX 24

### Running the Application

```bash
# Compile the project
mvn clean compile

# Run the application
mvn javafx:run

# Run tests
mvn test
```

### Using the Application

1. **Input Numbers**: Click any empty cell to cycle through numbers
2. **Load Sample**: Click "Load Sample" to load a pre-configured puzzle
3. **Solve**: Click "Solve (Backtracking)" to solve using Graph Theory algorithms
4. **Check Solution**: Click "Check Solution" to verify your puzzle
5. **Visualize Graph**: Click "Show Graph" to see the constraint graph
6. **Get Help**: Click "Help" for detailed usage instructions

## Graph Theory Concepts

### Constraint Satisfaction Problem (CSP)
- **Variables**: 81 cells in the 9×9 grid
- **Domains**: Each cell can contain values 1-9
- **Constraints**: Row, column, and 3×3 box constraints

### Constraint Graph
- **Vertices**: Each Sudoku cell represents a vertex
- **Edges**: Connect cells that share constraints
- **Graph Properties**: 81 vertices, ~810 edges, dense connectivity

### Algorithms
- **Arc Consistency**: Remove invalid values from domains
- **Most Constrained Variable**: Choose cell with fewest options
- **Least Constraining Value**: Choose value that eliminates fewest options
- **Forward Checking**: Update domains during search
- **Constraint Propagation**: Spread effects through the graph

## Architecture

### Model Layer
- **SudokuSolverLogic**: Traditional backtracking algorithm
- **SudokuGraphSolver**: Graph theory-based solving
- **SudokuGraphVisualizer**: Constraint graph visualization

### View Layer
- **SudokuSolver**: JavaFX user interface
- **Interactive Canvas**: Grid display and input handling
- **Information Dialogs**: Help, About, Algorithm, Strategy dialogs

### Controller Layer
- **Event Handling**: User interaction management
- **State Management**: Application state coordination
- **Graph Integration**: Constraint graph visualization

## Educational Value

This implementation demonstrates:
- **Graph Theory**: Practical application of graph concepts
- **Algorithm Design**: Heuristic search and constraint satisfaction
- **Problem Solving**: Breaking down complex problems
- **Visualization**: Making abstract concepts concrete
- **Software Architecture**: MVC pattern with separation of concerns

## Technical Details

### Complexity Analysis
- **Time Complexity**: O(9^(empty_cells)) worst case, much better with heuristics
- **Space Complexity**: O(81) for constraint graph, O(empty_cells) for recursion
- **Performance**: Significantly better than naive backtracking

### Graph Properties
- **Vertices**: 81 (one per cell)
- **Edges**: ~810 (each cell connects to ~20 others)
- **Connectivity**: Dense graph with high constraint density
- **Visualization**: Color-coded edges for different constraint types

## File Structure

```
13-04-Sudoku-Graph/
├── src/main/java/com/acu/javafx/sudoku/
│   ├── SudokuSolver.java              # Main JavaFX application
│   ├── SudokuSolverLogic.java         # Traditional backtracking solver
│   ├── SudokuGraphSolver.java         # Graph theory-based solver
│   └── SudokuGraphVisualizer.java     # Graph visualization utilities
├── src/test/java/com/acu/javafx/sudoku/
│   └── SudokuSolverTest.java          # Comprehensive test suite
├── docs/
│   ├── algorithm.md                   # Algorithm documentation
│   ├── architecture.md                # Architecture documentation
│   └── concepts.md                    # Graph theory concepts
├── images/
│   └── SudokuSolver.png              # Application screenshot
├── pom.xml                           # Maven configuration
└── README.md                         # This file
```

## Dependencies

- **JavaFX 24**: User interface framework
- **JUnit 5**: Testing framework
- **Maven**: Build and dependency management

## Development

### Building
```bash
mvn clean compile
```

### Testing
```bash
mvn test
```

### Running
```bash
mvn javafx:run
```

### Creating JAR
```bash
mvn clean package
```

---

**Built with ❤️ for Computer Science Education**

*Demonstrating Graph Theory, Constraint Satisfaction, and Advanced Algorithm Design*