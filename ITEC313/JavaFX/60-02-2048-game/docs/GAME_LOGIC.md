# Game Logic Documentation - 2048 Game

This document explains the core game logic and algorithms used in the 2048 JavaFX implementation.

## Overview

The 2048 game is a sliding puzzle where players combine numbered tiles on a 4×4 grid. The goal is to create a tile with the number 2048 by merging tiles with the same value.

## Core Components

### 1. Board Representation

The game board is represented as a 4×4 grid of `Tile` objects:

```java
private List<List<Tile>> tiles;  // 2D list representing the board
```

Each position can contain:
- An empty tile (value = 0)
- A numbered tile (value = 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048)

### 2. Tile Movement Algorithm

The movement algorithm follows these steps:

1. **Extract Non-Empty Tiles**: Remove all empty tiles from the movement direction
2. **Merge Adjacent Tiles**: Combine tiles with the same value
3. **Add Empty Tiles**: Fill remaining positions with empty tiles
4. **Update Board**: Place the processed tiles back on the board

#### Example: Moving Left

```
Before: [2][ ][2][4]
After:  [4][4][ ][ ]
```

**Step-by-step process:**
1. Extract: [2, 2, 4]
2. Merge: [4, 4] (first two 2s become 4)
3. Add empty: [4, 4, 0, 0]
4. Update board

### 3. Tile Merging Logic

When two tiles with the same value are adjacent in the movement direction:

```java
if (tiles.get(i).getValue() == tiles.get(i + 1).getValue()) {
    int mergedValue = tiles.get(i).merging();  // Double the value
    score += mergedValue;                       // Add to score
    tiles.remove(i + 1);                       // Remove duplicate
    emptyTiles++;                              // One less tile on board
}
```

**Merging Rules:**
- Only tiles with identical values can merge
- Merging doubles the tile value
- Each merge adds points to the score
- Merging creates an empty space

### 4. New Tile Generation

After each move, a new tile is added to a random empty position:

```java
private void generateNewRandomTile() {
    if (shouldGenerateNewTile && !isBoardFull()) {
        int value = Math.random() < 0.9 ? 2 : 4;  // 90% chance for 2, 10% for 4
        // Find random empty position and place tile
    }
}
```

**Generation Rules:**
- 90% chance for value 2
- 10% chance for value 4
- Only placed in empty positions
- Only one tile per move

### 5. Win/Lose Detection

#### Win Condition
```java
if (mergedValue == 2048) {
    gameOver = true;
    setGameStatus("WON");
}
```

#### Lose Condition
```java
if (isBoardFull() && !isMovePossible()) {
    setGameStatus("LOST");
}
```

**Lose Detection Algorithm:**
1. Check if board is full (no empty tiles)
2. Check if any adjacent tiles can merge
3. If both conditions are true, game is lost

### 6. Movement Directions

#### Up Movement
- Process each column from top to bottom
- Extract non-empty tiles from column
- Merge and add empty tiles at bottom

#### Down Movement
- Process each column from top to bottom
- Extract non-empty tiles from column
- Merge and add empty tiles at top

#### Left Movement
- Process each row from left to right
- Extract non-empty tiles from row
- Merge and add empty tiles at right

#### Right Movement
- Process each row from left to right
- Extract non-empty tiles from row
- Merge and add empty tiles at left

## Data Structures

### Tile Class
```java
public class Tile {
    private int value;    // Tile value (0 for empty)
    private int row;      // Current row position
    private int col;      // Current column position
}
```

### Board Class
```java
public class Board {
    private int score;                    // Current score
    private int emptyTiles;               // Number of empty positions
    private boolean gameOver;            // Win condition flag
    private String gameStatus;           // "WON", "LOST", or null
    private List<List<Tile>> tiles;      // 4×4 grid
}
```

## Algorithm Complexity

### Time Complexity
- **Move Operation**: O(n²) where n is the board size (4×4 = 16)
- **Merge Operation**: O(n) for each row/column
- **Overall**: O(n²) per move

### Space Complexity
- **Board Storage**: O(n²) for the 4×4 grid
- **Temporary Lists**: O(n) for processing rows/columns
- **Overall**: O(n²) space usage

## Game State Management

### Initial State
- Empty 4×4 board
- Two random tiles (value 2 or 4)
- Score = 0
- Game status = null

### During Gameplay
- Board updates after each move
- Score increases with each merge
- New tiles generated after valid moves
- Game status checked after each move

### End States
- **Win**: Tile with value 2048 created
- **Lose**: Board full with no possible merges
- **Continue**: Valid moves still available

## Edge Cases

### Empty Board
- Should not occur during normal gameplay
- Handled by checking `isBoardFull()`

### Full Board with Moves
- Game continues if merges are possible
- New tiles not generated when board is full

### Invalid Moves
- Moves that don't change the board state
- No new tiles generated for invalid moves
- Score remains unchanged

## Testing Strategy

### Unit Tests
- Tile creation and manipulation
- Board initialization and state
- Movement algorithms for each direction
- Merging logic and score calculation
- Win/lose condition detection

### Integration Tests
- Complete game flow from start to finish
- Keyboard input handling
- UI updates with game state changes
- Color scheme application

## Performance Considerations

### Optimization Techniques
- Efficient tile movement algorithms
- Minimal object creation during moves
- Lazy evaluation of game state
- Cached color calculations

### Memory Management
- Reuse of tile objects where possible
- Efficient list operations for merging
- Proper cleanup of temporary data structures

## Future Enhancements

### Possible Improvements
- Undo functionality
- High score persistence
- Different board sizes
- Animation effects
- Sound effects
- Multiplayer support

### Code Extensibility
- Modular design allows easy feature addition
- Clear separation of concerns
- Well-defined interfaces between components
- Comprehensive test coverage for safe refactoring
