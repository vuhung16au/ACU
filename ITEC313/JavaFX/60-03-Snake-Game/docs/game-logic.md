# Snake Game Logic Documentation

## Core Game Mechanics

### Snake Movement
The snake moves in discrete steps on a grid-based system:
- **Grid Size**: 20x20 pixel cells
- **Movement**: One cell per game tick
- **Direction**: Four possible directions (up, down, left, right)
- **Prevention**: Cannot reverse direction (prevents accidental self-collision)

### Food System
- **Generation**: Random placement on grid
- **Collision**: Snake head must touch food to consume
- **Growth**: Snake grows by one segment per food consumed
- **Scoring**: 10 points per food item

### Collision Detection
1. **Wall Collision**: Snake hits game boundaries
2. **Self-Collision**: Snake head touches any body segment
3. **Game Over**: Any collision ends the game

## Implementation Details

### Snake Class
```java
public class Snake {
    private List<Point2D> body;           // Snake segments
    private Direction direction;           // Current movement direction
    private boolean isAlive;             // Game state
    private int score;                   // Current score
}
```

### Movement Algorithm
1. Calculate new head position based on current direction
2. Check for collisions (walls, self)
3. Add new head to body list
4. Remove tail segment (unless growing)

### Food Placement
1. Generate random grid coordinates
2. Check if position conflicts with snake body
3. If conflict, generate new position
4. Place food at valid position

## Game States

### Running State
- Snake moves continuously
- Input processing active
- Collision detection enabled
- Score tracking active

### Paused State
- Snake movement stopped
- Input processing limited
- Game state preserved
- Can resume or reset

### Game Over State
- Snake movement stopped
- Input processing for restart only
- Final score displayed
- High score updated

## Performance Considerations

### Rendering Optimization
- Only redraw changed areas
- Use efficient drawing primitives
- Minimize object creation in game loop

### Memory Management
- Reuse Point2D objects where possible
- Clear collections when resetting
- Avoid memory leaks in game loop

### Collision Detection
- Early exit on wall collision
- Efficient body segment checking
- Optimized position comparison
