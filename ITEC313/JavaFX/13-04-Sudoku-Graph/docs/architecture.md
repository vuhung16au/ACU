# Sudoku Solver Architecture Documentation - Graph Theory Implementation

## System Overview

The Sudoku Solver application follows a **Model-View-Controller (MVC)** architectural pattern enhanced with **Graph Theory** components, separating concerns between the user interface, business logic, graph algorithms, and data management. This design ensures maintainability, testability, and extensibility while demonstrating advanced computer science concepts.

## Architecture Components

### 1. Model Layer

#### SudokuSolverLogic Class (Traditional Backtracking)
- **Purpose**: Pure business logic for traditional Sudoku solving
- **Responsibilities**:
  - Implement backtracking algorithm
  - Validate Sudoku constraints
  - Provide basic solving functionality
- **Dependencies**: None (pure Java)
- **Testability**: High (no UI dependencies)

```java
public class SudokuSolverLogic {
    public boolean solveSudoku(int[][] grid);
    public boolean isValidPlacement(int[][] grid, int row, int col, int num);
    public boolean isValidSolution(int[][] grid);
    public boolean isValidInput(int[][] grid);
}
```

#### SudokuGraphSolver Class (Graph Theory Implementation)
- **Purpose**: Advanced Sudoku solving using graph theory algorithms
- **Responsibilities**:
  - Build constraint graph representation
  - Implement arc consistency algorithms
  - Apply heuristic search strategies
  - Provide graph-based solving functionality
- **Dependencies**: None (pure Java)
- **Testability**: High (no UI dependencies)

```java
public class SudokuGraphSolver {
    public boolean solveSudoku(int[][] grid);
    public boolean isValidSolution(int[][] grid);
    public Map<Cell, Set<Cell>> getConstraintGraph();
    public Map<Cell, Set<Integer>> getDomains();
    public int getConstraintCount();
    public int getCellDegree(Cell cell);
}
```

#### SudokuGraphVisualizer Class (Graph Visualization)
- **Purpose**: Visual representation of constraint graphs
- **Responsibilities**:
  - Draw constraint graphs on canvas
  - Show domain information
  - Provide interactive graph exploration
- **Dependencies**: JavaFX graphics
- **Testability**: Medium (requires JavaFX testing framework)

```java
public class SudokuGraphVisualizer {
    public static void drawConstraintGraph(Canvas canvas, 
                                         Map<Cell, Set<Cell>> constraintGraph,
                                         Map<Cell, Set<Integer>> domains,
                                         Cell highlightCell);
    public static void drawSimplifiedGraph(Canvas canvas, 
                                         Map<Cell, Set<Cell>> constraintGraph,
                                         Cell focusCell);
}
```

### 2. View Layer

#### SudokuSolver Class (JavaFX Application)
- **Purpose**: User interface and interaction handling
- **Responsibilities**:
  - Render Sudoku grid on Canvas
  - Handle user input events
  - Display status messages
  - Manage button interactions
- **Dependencies**: JavaFX framework
- **Testability**: Medium (requires JavaFX testing framework)

```java
public class SudokuSolver extends Application {
    private Canvas canvas;
    private GraphicsContext gc;
    private Button loadSampleButton;
    private Button solveButton;
    private Button clearButton;
    private Label statusLabel;
}
```

### 3. Controller Layer

#### Event Handling and State Management
- **Purpose**: Coordinate between Model and View
- **Responsibilities**:
  - Handle user interactions
  - Update model state
  - Refresh view display
  - Manage application state
- **Implementation**: Integrated into SudokuSolver class
- **Pattern**: Observer pattern for UI updates

## Design Patterns

### 1. Model-View-Controller (MVC)

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│      View       │    │   Controller    │    │      Model      │
│                 │    │                 │    │                 │
│  - Canvas       │◄──►│  - Event        │◄──►│  - Grid Data    │
│  - Buttons      │    │    Handlers     │    │  - Solver Logic │
│  - Labels       │    │  - State Mgmt   │    │  - Validation   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

**Benefits**:
- Separation of concerns
- Independent testing
- Easy maintenance
- Reusable components

### 2. Observer Pattern

The UI components observe changes in the model and update accordingly:

```java
// Model notifies view of changes
private void updateStatus(String message) {
    statusLabel.setText(message);
}

// View updates based on model state
private void drawGrid() {
    // Redraw based on current grid state
}
```

### 3. Strategy Pattern

Different solving algorithms can be implemented:

```java
public interface SudokuSolverStrategy {
    boolean solve(int[][] grid);
}

public class BacktrackingSolver implements SudokuSolverStrategy {
    // Implementation
}

public class DancingLinksSolver implements SudokuSolverStrategy {
    // Implementation
}
```

## Data Flow

### 1. User Input Flow

```
User Click → Event Handler → Model Update → View Refresh
     ↓              ↓              ↓           ↓
Canvas Click → handleCanvasClick → grid[row][col] = value → drawGrid()
```

### 2. Solving Flow

```
Solve Button → Validation → Solver Algorithm → Solution Display
     ↓              ↓              ↓              ↓
solvePuzzle() → isValidInput() → solveSudoku() → drawGrid()
```

### 3. Sample Loading Flow

```
Load Button → Copy Sample Data → Update Grid → Refresh Display
     ↓              ↓              ↓              ↓
loadSample() → samplePuzzle → grid = sample → drawGrid()
```

## Class Relationships

### UML Class Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    SudokuSolver                             │
│  - canvas: Canvas                                           │
│  - gc: GraphicsContext                                     │
│  - grid: int[][]                                           │
│  - solver: SudokuSolverLogic                               │
│  + start(Stage): void                                      │
│  + handleCanvasClick(MouseEvent): void                     │
│  + solvePuzzle(): void                                     │
│  + loadSamplePuzzle(): void                                │
│  + clearPuzzle(): void                                     │
│  + drawGrid(): void                                        │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                 SudokuSolverLogic                          │
│  + solveSudoku(int[][]): boolean                          │
│  + isValidPlacement(int[][], int, int, int): boolean      │
│  + isValidSolution(int[][]): boolean                      │
│  + isValidInput(int[][]): boolean                         │
│  + countEmptyCells(int[][]): int                          │
│  + copyGrid(int[][]): int[][]                             │
└─────────────────────────────────────────────────────────────┘
```

### Dependencies

```
SudokuSolver ──depends on──► SudokuSolverLogic
     │                              │
     ▼                              ▼
JavaFX Framework              Pure Java Logic
```

## State Management

### Application State

```java
public class SudokuSolver {
    // Grid state
    private int[][] grid = new int[9][9];
    private int[][] originalGrid = new int[9][9];
    private boolean[][] isOriginal = new boolean[9][9];
    
    // UI state
    private String statusMessage;
    private boolean isSolving;
    private boolean hasSolution;
}
```

### State Transitions

```
Initial State → Input State → Solving State → Solution State
     │              │              │              │
     ▼              ▼              ▼              ▼
Empty Grid → User Input → Algorithm → Display Result
```

## Error Handling

### Exception Hierarchy

```java
public class SudokuException extends Exception {
    public SudokuException(String message) {
        super(message);
    }
}

public class InvalidInputException extends SudokuException {
    public InvalidInputException(String message) {
        super("Invalid input: " + message);
    }
}

public class NoSolutionException extends SudokuException {
    public NoSolutionException() {
        super("No solution exists for this puzzle");
    }
}
```

### Error Handling Strategy

1. **Input Validation**: Check user input before processing
2. **Graceful Degradation**: Continue operation when possible
3. **User Feedback**: Clear error messages
4. **Recovery**: Provide ways to fix errors

```java
private void solvePuzzle() {
    try {
        if (!isValidInput()) {
            throw new InvalidInputException("Duplicate numbers detected");
        }
        
        if (!solveSudoku(grid)) {
            throw new NoSolutionException();
        }
        
        updateStatus("Solution found!");
    } catch (SudokuException e) {
        updateStatus("Error: " + e.getMessage());
    }
}
```

## Testing Architecture

### Test Structure

```
src/test/java/com/acu/javafx/sudoku/
├── SudokuSolverTest.java           # Unit tests for logic
├── SudokuSolverUITest.java        # UI integration tests
└── SudokuSolverPerformanceTest.java # Performance tests
```

### Test Categories

1. **Unit Tests**: Test individual methods in isolation
2. **Integration Tests**: Test component interactions
3. **UI Tests**: Test user interface functionality
4. **Performance Tests**: Test algorithm efficiency

### Test Coverage

```java
@Test
public void testSolveValidPuzzle() {
    // Arrange
    int[][] puzzle = createValidPuzzle();
    
    // Act
    boolean result = solver.solveSudoku(puzzle);
    
    // Assert
    assertTrue(result);
    assertTrue(solver.isValidSolution(puzzle));
}
```

## Performance Considerations

### Memory Management

```java
// Efficient grid copying
public int[][] copyGrid(int[][] grid) {
    int[][] copy = new int[9][9];
    for (int row = 0; row < 9; row++) {
        System.arraycopy(grid[row], 0, copy[row], 0, 9);
    }
    return copy;
}
```

### Algorithm Optimization

```java
// Early termination for invalid inputs
private boolean isValidInput() {
    // Check rows, columns, and boxes
    // Return false immediately on first violation
}
```

### UI Performance

```java
// Efficient canvas drawing
private void drawGrid() {
    gc.clearRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
    // Only redraw what's necessary
}
```

## Extensibility

### Adding New Features

1. **New Solving Algorithms**: Implement different strategies
2. **Puzzle Generation**: Add puzzle creation functionality
3. **Hint System**: Provide step-by-step guidance
4. **Statistics**: Track solving performance

### Plugin Architecture

```java
public interface SudokuSolverPlugin {
    String getName();
    boolean solve(int[][] grid);
    String getDescription();
}

public class PluginManager {
    private List<SudokuSolverPlugin> plugins;
    
    public void registerPlugin(SudokuSolverPlugin plugin);
    public List<SudokuSolverPlugin> getAvailablePlugins();
}
```

## Security Considerations

### Input Validation

```java
public boolean isValidInput(int[][] grid) {
    if (grid == null || grid.length != 9) {
        return false;
    }
    
    for (int row = 0; row < 9; row++) {
        if (grid[row] == null || grid[row].length != 9) {
            return false;
        }
        for (int col = 0; col < 9; col++) {
            if (grid[row][col] < 0 || grid[row][col] > 9) {
                return false;
            }
        }
    }
    return true;
}
```

### Resource Management

```java
// Proper resource cleanup
@Override
public void stop() {
    // Clean up resources
    gc = null;
    canvas = null;
}
```

## Conclusion

The Sudoku Solver architecture provides a solid foundation for:

1. **Maintainability**: Clear separation of concerns
2. **Testability**: Isolated components with comprehensive tests
3. **Extensibility**: Easy to add new features
4. **Performance**: Optimized algorithms and data structures
5. **User Experience**: Responsive and intuitive interface

This architecture demonstrates best practices in JavaFX application development and serves as a template for similar puzzle-solving applications.
