# Knight's Tour as Hamiltonian Cycle – Algorithm Documentation

## Overview

The Knight's Tour can be reduced to a graph problem. Model the 8×8 chessboard as a graph with 64 vertices (squares). Connect two vertices with an edge if a knight can move between the corresponding squares. A closed Knight’s Tour is a **Hamiltonian cycle** in this graph.

## Problem Definition

### What is a Knight's Tour?

A Knight's Tour is a sequence of moves by a knight on a chessboard where:
- The knight visits every square exactly once
- The knight follows standard chess movement rules
- The tour can be either **open** (start and end are different) or **closed** (start and end squares are a knight move apart)

### Knight Movement Rules

A knight moves in an L-shape:
- 2 squares in one direction (horizontal or vertical)
- 1 square in the perpendicular direction
- From any position, there are up to 8 possible moves (fewer near edges)

```
Example moves from center position (3,3):
  . . . . . . . .
  . . . . . . . .
  . . . . . . . .
  . . . . . . . .
  . . . . . . . .
  . . . . . . . .
  . . . . . . . .
  . . . . . . . .
```

## Algorithm Implementation

### Backtracking Approach (Closed Tour)

Our implementation uses a **backtracking algorithm** that enforces the cycle constraint:

```java
private boolean solveRecursiveClosed(int row, int col, Position start,
                             boolean[][] visited, List<Position> path, int moveCount) {
    // Base case: all squares visited -> must be adjacent to start to close the cycle
    if (moveCount == 64) return isValidKnightMove(new Position(row, col), start);

    // Try all 8 possible moves
    for (int[] move : KNIGHT_MOVES) {
        int newRow = row + move[0];
        int newCol = col + move[1];

        if (isValidMove(newRow, newCol, visited)) {
            // Make move and recurse
            visited[newRow][newCol] = true;
            path.add(new Position(newRow, newCol));

            if (solveRecursiveClosed(newRow, newCol, start, visited, path, moveCount + 1)) {
                return true;
            }

            // Backtrack
            visited[newRow][newCol] = false;
            path.remove(path.size() - 1);
        }
    }
    return false;
}
```

### Algorithm Steps (Hamiltonian Cycle)

1. **Initialize**: Start with knight on chosen square (start vertex)
2. **Mark Visited**: Mark current square as visited
3. **Try Moves**: Attempt edges to unvisited neighbors
4. **Validate**: Move must be legal and lead to unvisited vertex
5. **Recurse**: If valid, make move and continue recursively
6. **Backtrack**: If no solution found, undo move and try next option
7. **Complete**: When all 64 vertices visited and last is adjacent to start, a closed tour is found

### Time Complexity

- **Worst Case**: O(8^64) - exponential time
- **Average Case**: Much better due to pruning
- **Space Complexity**: O(64) for recursion stack and visited array

## Alternative Algorithms

### Warnsdorff's Algorithm

A heuristic approach that chooses the move with the fewest onward moves. It can be adapted to prefer moves that keep the option to close the cycle in the final step.

### Neural/Metaheuristic Approaches

Modern approaches (neural networks, simulated annealing, genetic algorithms) can learn or search for tours; enforcing closure can be handled as a constraint or penalty.

## Implementation Details

### Data Structures

```java
// Knight moves (8 directions)
private static final int[][] KNIGHT_MOVES = {
    {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
    {1, -2}, {1, 2}, {2, -1}, {2, 1}
};

// Visited squares tracking
boolean[][] visited = new boolean[8][8];

// Current path
List<Position> path = new ArrayList<>();
```

### Move Validation

```java
private boolean isValidMove(int row, int col, boolean[][] visited) {
    return row >= 0 && row < 8 && 
           col >= 0 && col < 8 && 
           !visited[row][col];
}
```

### Position Class

```java
public static class Position {
    public final int row;
    public final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return row * 8 + col;
    }
}
```

## Performance Considerations

### Optimization Techniques

1. **Move Ordering**: Try moves in a specific order (e.g., Warnsdorff heuristic)
2. **Early Termination**: Stop if no solution possible
3. **Pruning**: Avoid moves that isolate unvisited islands
4. **Parallel Processing**: Use multiple threads for different starting positions

### Memory Management

- Use primitive arrays instead of collections where possible
- Minimize object creation in tight loops
- Consider iterative approach for very large boards

## Testing the Algorithm

### Test Cases

1. **Corner Positions**: Test starting from corners (0,0), (0,7), (7,0), (7,7)
2. **Edge Positions**: Test starting from edges
3. **Center Positions**: Test starting from center squares
4. **Closure Check**: Ensure the final position is a knight move from the start

### Validation Snippet

```java
List<Position> cycle = solver.solveClosedTourSync();
if (cycle != null) {
    assertEquals(cycle.get(0), cycle.get(cycle.size() - 1));
}
```

## Mathematical Properties

### Existence of Solutions

- **8x8 Board**: Solutions exist for most starting positions
- **Smaller Boards**: Some boards have no solutions
- **Larger Boards**: Solutions become more common

### Number of Solutions

- **8x8 Board**: Millions of possible tours
- **Closed Tours**: Fewer than open tours; this app searches for closed tours
- **Symmetric Solutions**: Many tours are rotations/reflections of others

## Applications

### Related Problems

- **Hamiltonian Path/Cycle**: Visit each vertex exactly once (cycle returns to start)
- **Traveling Salesman**: Visit all cities with minimum cost
- **N-Queens**: Place queens without attacking each other
- **Sudoku**: Fill grid with constraints

## Conclusion

Reducing the Knight’s Tour to a Hamiltonian cycle highlights the deep connection between chess puzzles and graph theory. The implementation illustrates backtracking search with a cycle constraint and provides an engaging visual demonstration in JavaFX.
