# Snake Game - JavaFX Implementation

## Overview

A modern Snake game implementation using JavaFX with ACU color schema. The game features smooth gameplay, intuitive controls, and a clean user interface designed for educational purposes.

## Game Logic

### Core Mechanics

The Snake game follows these fundamental rules:

1. **Snake Movement**: The snake moves continuously in one direction (up, down, left, right)
2. **Food Consumption**: When the snake's head touches food, it grows by one segment and the score increases
3. **Collision Detection**: The game ends if the snake hits walls or its own body
4. **Score System**: Points are awarded for each food item consumed (10 points per food)

### Key Components

#### Snake Class
- **Position Management**: Uses a list of Point2D objects to track snake segments
- **Movement Logic**: Implements direction-based movement with collision prevention
- **Growth Mechanism**: Adds new segments when food is consumed
- **State Management**: Tracks alive/dead state and current score

#### Food Class
- **Position Generation**: Creates random food positions that don't conflict with snake body
- **Collision Detection**: Provides methods to check if snake head reaches food
- **Grid Alignment**: Ensures food appears on the game grid (20x20 pixel cells)

#### GamePane Class
- **Game Loop**: Uses JavaFX Timeline for smooth 60 FPS gameplay
- **Input Handling**: Processes keyboard input for snake direction changes
- **Rendering**: Draws game elements using JavaFX Canvas with ACU color schema
- **State Management**: Handles game start, pause, reset, and game over states

### Technical Implementation

#### Movement System
```java
// Snake moves by adding new head position and removing tail
public void move(int gameWidth, int gameHeight) {
    Point2D head = body.get(0);
    int newX = (int) (head.getX() + direction.getDeltaX() * CELL_SIZE);
    int newY = (int) (head.getY() + direction.getDeltaY() * CELL_SIZE);
    
    // Check collisions and update position
    body.add(0, new Point2D(newX, newY));
}
```

#### Collision Detection
```java
// Wall collision
if (newX < 0 || newX >= gameWidth || newY < 0 || newY >= gameHeight) {
    isAlive = false;
}

// Self-collision
if (body.contains(newHead)) {
    isAlive = false;
}
```

#### Food Generation
```java
// Generate food position avoiding snake body
public Point2D generateNewPosition(List<Point2D> snakeBody) {
    Point2D newPosition;
    do {
        newPosition = generateRandomPosition();
    } while (snakeBody.contains(newPosition));
    return newPosition;
}
```

## Features

### Game Features
- **Smooth Movement**: 60 FPS gameplay with responsive controls
- **Score Tracking**: Real-time score display with high score persistence
- **Game States**: Start, pause, resume, and reset functionality
- **Collision Detection**: Wall and self-collision detection
- **Growth System**: Snake grows when eating food

### UI Features
- **ACU Color Schema**: Professional color scheme using ACU brand colors
- **Modern Design**: Clean, intuitive interface with proper spacing
- **Grid Visualization**: Vertical and horizontal grid lines for better gameplay visibility
- **How to Play Guide**: Built-in instructions displayed on the right side
- **Responsive Controls**: Keyboard and button controls
- **Visual Feedback**: Clear game state indicators and score display

### Technical Features
- **JavaFX Canvas**: Hardware-accelerated rendering
- **Timeline Animation**: Smooth game loop with configurable speed
- **Event Handling**: Comprehensive keyboard and mouse input
- **Memory Management**: Efficient object lifecycle management

## Controls

### Keyboard Controls
- **Arrow Keys** or **WASD**: Move snake in desired direction
- **SPACE**: Start new game, resume from pause, or pause current game
- **R**: Reset game

### Button Controls
- **Start Game**: Begin new game or resume from pause
- **Pause**: Pause/resume current game
- **Reset**: Reset to initial state

### Starting a New Game
You can start a new game using **either**:
- Press the **SPACE** key, OR
- Click the **Start Game** button

Both methods work independently - you don't need to use both!

## Game Rules

1. **Objective**: Eat as much food as possible to achieve the highest score
2. **Movement**: Snake moves continuously in the current direction
3. **Growth**: Snake grows by one segment each time it eats food
4. **Collision**: Game ends if snake hits walls or its own body
5. **Scoring**: 10 points awarded for each food item consumed
6. **Speed**: Game speed increases as score increases (every 50 points)

## Architecture

### Design Patterns
- **Model-View-Controller**: Separation of game logic, UI, and input handling
- **Observer Pattern**: Game state changes trigger UI updates
- **State Pattern**: Different game states (running, paused, game over)

### Class Hierarchy
```
SnakeGame (Application)
└── GamePane (BorderPane)
    ├── Snake (Game Logic)
    ├── Food (Game Logic)
    └── Canvas (Rendering)
```

### Data Flow
1. **Input** → Keyboard/Button events
2. **Processing** → Game logic updates (movement, collision, scoring)
3. **Rendering** → Canvas drawing with ACU colors
4. **Output** → Visual feedback and score updates

## Performance Considerations

### Optimization Techniques
- **Efficient Rendering**: Only redraw changed areas when possible
- **Memory Management**: Reuse objects and avoid unnecessary allocations
- **Collision Detection**: Optimized algorithms for wall and self-collision
- **Game Loop**: Fixed timestep for consistent gameplay

### Scalability
- **Configurable Grid**: Easy to change game area size
- **Modular Design**: Components can be easily extended or modified
- **Test Coverage**: Comprehensive unit tests for all game logic

## Educational Value

This implementation demonstrates:
- **JavaFX Graphics**: Canvas rendering and animation
- **Game Development**: Core game loop and state management
- **Object-Oriented Design**: Clean separation of concerns
- **Event Handling**: User input processing
- **Testing**: Unit testing for game logic
- **UI Design**: Modern interface with consistent styling

## Future Enhancements

Potential improvements include:
- **Power-ups**: Special food items with unique effects
- **Multiple Levels**: Increasing difficulty and speed
- **Sound Effects**: Audio feedback for actions
- **High Score Persistence**: Save high scores to file
- **Customization**: Player-selectable colors and themes
- **Multiplayer**: Two-player competitive mode
