# 2048 Game - JavaFX Implementation

A JavaFX implementation of the popular 2048 puzzle game.

## Game Logic

The 2048 game is a sliding puzzle where players combine numbered tiles on a 4×4 grid to create a tile with the number 2048. The game mechanics include:

- **Tile Movement**: Use arrow keys (↑↓←→) or WASD keys to slide tiles in any direction
- **Tile Merging**: When two tiles with the same number touch, they merge into one tile with double the value
- **Score System**: Points are awarded for each merge (the value of the merged tile)
- **Win Condition**: Create a tile with the number 2048 to win
- **Game Over**: The game ends when the board is full and no moves are possible

## Key Features

- **Responsive Design**: Clean, modern JavaFX interface with smooth animations
- **Keyboard Controls**: Intuitive arrow key and WASD controls
- **Score Tracking**: Real-time score display and game status
- **New Game**: Easy restart functionality with the "NEW GAME" button
- **Help System**: Interactive help dialog with game instructions and tips

## Architecture

### Core Classes

- **`Game2048App`**: Main JavaFX application class that handles the UI and user interactions
- **`Board`**: Game logic engine that manages the 4×4 grid, tile movements, and merging
- **`Tile`**: Represents individual game tiles with values and positions
- **`GameGrid`**: Visual representation of the game board using JavaFX GridPane
- **`HelpDialog`**: Interactive help dialog with game instructions and controls

### Game Flow

1. **Initialization**: Board starts with 2 random tiles (value 2 or 4)
2. **Player Input**: Arrow keys or WASD keys trigger tile movements
3. **Movement Logic**: Tiles slide in the specified direction, merging when possible
4. **New Tile Generation**: After each move, a new tile (2 or 4) appears in a random empty space
5. **Win/Lose Detection**: Game checks for 2048 tile (win) or full board with no moves (lose)

## Controls

- **Arrow Keys**: ↑ (Up), ↓ (Down), ← (Left), → (Right)
- **WASD Keys**: W (Up), S (Down), A (Left), D (Right)
- **R Key**: Start a new game
- **ESC Key**: Exit the application
- **Shift + ?**: Show help dialog
- **NEW GAME Button**: Start a new game
- **? Button**: Show help dialog with game instructions

## Building and Running

### Prerequisites

- Java 24 or higher
- Maven 3.6 or higher
- JavaFX 24

### Build Commands

```bash
# Compile the project
mvn clean compile

# Run tests
mvn test

# Run the application
mvn javafx:run

# Create executable JAR
mvn clean package
```

### Running the Application

```bash
# Using Maven
mvn javafx:run

# Using the generated JAR
java -jar target/game2048-1.0.0.jar
```

## Project Structure

```
60-02-2048-game/
├── src/
│   ├── main/java/com/acu/javafx/game2048/
│   │   ├── Game2048App.java          # Main application
│   │   ├── Board.java               # Game logic
│   │   ├── Tile.java                # Tile representation
│   │   ├── GameGrid.java            # Visual grid
│   │   └── ACUColorScheme.java       # Color definitions
│   └── test/java/com/acu/javafx/game2048/
│       ├── TileTest.java            # Tile unit tests
│       ├── BoardTest.java           # Board unit tests
│       └── ACUColorSchemeTest.java  # Color scheme tests
├── docs/                            # Documentation
├── images/                          # Game assets
├── pom.xml                          # Maven configuration
└── README.md                        # This file
```

## Educational Value

This implementation demonstrates:

- **JavaFX UI Development**: Modern JavaFX application structure
- **Game Logic Design**: Clean separation of game logic and presentation
- **Object-Oriented Design**: Well-structured classes with clear responsibilities
- **Unit Testing**: Comprehensive test coverage for game logic
- **Maven Project Management**: Proper Maven configuration and build process

## Testing

The project includes comprehensive unit tests covering:

- Tile creation, movement, and merging
- Board initialization and game state management
- Game logic edge cases and win/lose conditions

Run tests with:
```bash
mvn test
```

