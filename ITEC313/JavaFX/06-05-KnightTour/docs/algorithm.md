# Knight's Tour Algorithm Documentation

## Overview

The Knight's Tour is a classic problem in computer science and mathematics. It involves finding a sequence of moves by a knight on a chessboard such that the knight visits every square exactly once.

## Problem Definition

### What is a Knight's Tour?

A Knight's Tour is a sequence of moves by a knight on a chessboard where:
- The knight visits every square exactly once
- The knight follows standard chess movement rules
- The tour can be either **open** (start and end are different) or **closed** (start and end are adjacent)

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

### Backtracking Approach

Our implementation uses a **backtracking algorithm**:

```java
private boolean solveRecursive(int row, int col, boolean[][] visited, 
                             List<Position> path, int moveCount) {
    // Base case: all squares visited
    if (moveCount == 64) return true;
    
    // Try all 8 possible moves
    for (int[] move : KNIGHT_MOVES) {
        int newRow = row + move[0];
        int newCol = col + move[1];
        
        if (isValidMove(newRow, newCol, visited)) {
            // Make move and recurse
            visited[newRow][newCol] = true;
            path.add(new Position(newRow, newCol));
            
            if (solveRecursive(newRow, newCol, visited, path, moveCount + 1)) {
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

### Algorithm Steps

1. **Initialize**: Start with knight on chosen square
2. **Mark Visited**: Mark current square as visited
3. **Try Moves**: Attempt all 8 possible knight moves
4. **Validate**: Check if move is legal and square is unvisited
5. **Recurse**: If valid, make move and continue recursively
6. **Backtrack**: If no solution found, undo move and try next option
7. **Complete**: When all 64 squares visited, solution is found

### Time Complexity

- **Worst Case**: O(8^64) - exponential time
- **Average Case**: Much better due to pruning
- **Space Complexity**: O(64) for recursion stack and visited array

## Alternative Algorithms

### Warnsdorff's Algorithm

A heuristic approach that chooses the move with the fewest onward moves:

```java
private Position chooseBestMove(Position current, boolean[][] visited) {
    List<Position> moves = getPossibleMoves(current);
    Position bestMove = null;
    int minMoves = Integer.MAX_VALUE;
    
    for (Position move : moves) {
        if (!visited[move.row][move.col]) {
            int onwardMoves = countPossibleMoves(move, visited);
            if (onwardMoves < minMoves) {
                minMoves = onwardMoves;
                bestMove = move;
            }
        }
    }
    
    return bestMove;
}
```

### Neural Network Approach

Modern approaches use neural networks to learn patterns in valid tours.

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

1. **Move Ordering**: Try moves in a specific order (e.g., from center outward)
2. **Early Termination**: Stop if no solution possible
3. **Memoization**: Cache results for subproblems
4. **Parallel Processing**: Use multiple threads for different starting positions

### Memory Management

- Use primitive arrays instead of collections where possible
- Implement custom data structures for better cache locality
- Consider iterative approach for very large boards

## Testing the Algorithm

### Test Cases

1. **Corner Positions**: Test starting from corners (0,0), (0,7), (7,0), (7,7)
2. **Edge Positions**: Test starting from edges
3. **Center Positions**: Test starting from center squares
4. **Impossible Cases**: Verify algorithm handles unsolvable positions

### Validation

```java
@Test
void shouldFindValidTour() {
    ChessBoard board = new ChessBoard(8, 60);
    KnightTourSolver solver = new KnightTourSolver(board);
    
    board.placeKnight(0, 0);
    List<Position> solution = solver.findSolution();
    
    assertNotNull(solution);
    assertEquals(64, solution.size());
    assertTrue(isValidTour(solution));
}
```

## Mathematical Properties

### Existence of Solutions

- **8x8 Board**: Solutions exist for most starting positions
- **Smaller Boards**: Some boards have no solutions
- **Larger Boards**: Solutions become more common

### Number of Solutions

- **8x8 Board**: Millions of possible tours
- **Closed Tours**: Fewer than open tours
- **Symmetric Solutions**: Many tours are rotations/reflections of others

## Applications

### Real-World Uses

1. **Puzzle Games**: Chess puzzles and brain teasers
2. **Algorithm Testing**: Benchmark for backtracking algorithms
3. **Educational**: Teaching recursion and problem solving
4. **Research**: Graph theory and Hamiltonian path problems

### Related Problems

- **Hamiltonian Path**: Visit each vertex exactly once
- **Traveling Salesman**: Visit all cities with minimum cost
- **N-Queens**: Place queens without attacking each other
- **Sudoku**: Fill grid with constraints

## Conclusion

The Knight's Tour is an excellent example of:
- **Backtracking algorithms**
- **Recursive problem solving**
- **Constraint satisfaction**
- **Graph traversal**

Our implementation provides a solid foundation for understanding these concepts while offering an engaging visual demonstration of the algorithm in action.
