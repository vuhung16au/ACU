# Multiplayer Tic-Tac-Toe Game

A multiplayer Tic-Tac-Toe game built with JavaFX that supports both network multiplayer and single-player (against computer) modes.

## Overview

This is a modern, interactive multiplayer Tic-Tac-Toe game that supports both network multiplayer and single-player modes:

- **Multiplayer Mode**: Play against another player over the network using socket programming (primary mode)
- **Single-Player Mode**: Play against an intelligent computer opponent (X vs O) (secondary mode)

The game features real-time network communication for multiplayer games and intelligent AI for single-player mode.

## Features

### 🎮 Game Features
- **Multiplayer Mode**: Play against another player over the network (primary feature)
- **Single-Player Mode**: Play against an intelligent computer opponent (secondary feature)
- **Real-time Network Communication**: Socket-based multiplayer with automatic synchronization
- **Multiple Game Sessions**: Server can handle multiple concurrent games
- **Smart AI**: Computer tries to win, blocks your winning moves, and makes strategic decisions
- **Visual Feedback**: Beautiful, modern UI with smooth animations
- **Game State Management**: Automatic win/tie detection
- **New Game**: Start fresh games with a single click
- **Responsive Design**: Works on different screen sizes

### 🎨 User Interface
- **Modern Design**: Clean, gradient background with card-style layout
- **Color-Coded Moves**: Your moves (X) in red, computer moves (O) in blue
- **Smooth Animations**: Hover effects and move animations
- **Status Updates**: Clear indication of whose turn it is and game results
- **Professional Styling**: Custom CSS with modern design principles

## Technical Specifications

### Development Environment
- **Java Version**: OpenJDK 24
- **JavaFX Version**: 21.0.2
- **Maven Version**: 3.9.x or later
- **Build System**: Maven with JavaFX plugin

### Cross-Platform Support
The game runs on:
- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64  
- **Linux**: x86_64 and ARM64

Platform detection is automatic through Maven profiles.

### Architecture
- **Launcher Pattern**: Separate launcher class to avoid JavaFX module issues
- **Event-Driven**: Comprehensive event handling for all interactions
- **Responsive Design**: Clean, modern UI with CSS styling
- **AI Implementation**: Simple but effective computer opponent
- **Network Architecture**: Client-server model with socket programming
- **Multi-threading**: Server handles multiple game sessions concurrently

## Getting Started

### Prerequisites
1. **Java 24**: Install OpenJDK 24 or later
2. **Maven**: Install Maven 3.9.x or later
3. **JavaFX**: Automatically downloaded by Maven

### Building and Running

#### Option 1: Multiplayer Mode (Primary)
```bash
# On macOS/Linux
./run-multiplayer.sh server    # Start server
./run-multiplayer.sh client    # Start client

# On Windows
run-multiplayer.bat server     # Start server
run-multiplayer.bat client     # Start client
```

#### Option 2: Single-Player Mode (Secondary)
```bash
# On macOS/Linux
./run.sh

# On Windows
run.bat
```

#### Option 2: Using Maven Commands
```bash
# Compile the project
mvn clean compile

# Run the application
mvn javafx:run
```

#### Option 3: Direct Java Execution
```bash
# On macOS/Linux (after compiling with Maven)
./run_direct.sh
```

### Quick Start Commands
```bash
# Clone or navigate to project directory
cd tictactoe-game

# Make scripts executable (macOS/Linux)
chmod +x run.sh run_direct.sh run-multiplayer.sh

# Multiplayer mode (primary)
./run-multiplayer.sh server    # Terminal 1
./run-multiplayer.sh client    # Terminal 2
./run-multiplayer.sh client    # Terminal 3

# Single-player mode (secondary)
./run.sh
```

## Project Structure
```
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── Launcher.java              # Application entry point
│   │   │   ├── TicTacToeApp.java          # Single-player application class (secondary)
│   │   │   ├── TicTacToeGame.java         # Single-player game logic and AI (secondary)
│   │   │   ├── TicTacToeConstants.java    # Multiplayer game constants (primary)
│   │   │   ├── TicTacToeServer.java       # Multiplayer server application (primary)
│   │   │   ├── TicTacToeClient.java       # Multiplayer client application (primary)
│   │   │   └── MultiPlayerLauncher.java   # Multiplayer launcher utility (primary)
│   │   └── resources/
│   │       └── styles.css                 # Modern UI styling
│   └── test/java/                         # Unit tests (future)
├── pom.xml                                # Maven configuration
├── run.sh                                 # Single-player Unix/Linux/macOS run script (secondary)
├── run.bat                                # Single-player Windows run script (secondary)
├── run_direct.sh                          # Direct Java execution
├── run-multiplayer.sh                     # Multiplayer Unix/Linux/macOS run script (primary)
├── run-multiplayer.bat                    # Multiplayer Windows run script (primary)
├── MULTIPLAYER_README.md                  # Detailed multiplayer documentation
└── README.md                              # This file
```

## How to Play

### 🎯 Game Objective
The goal is to be the first player to get three of your symbols in a row on the 3x3 grid. You can win by:
- **Horizontal**: Three symbols in the same row
- **Vertical**: Three symbols in the same column  
- **Diagonal**: Three symbols diagonally (top-left to bottom-right or top-right to bottom-left)

### 🌐 Multiplayer Mode (Primary)

#### Getting Started
1. **Start the Server**: Run `./run-multiplayer.sh server` (macOS/Linux) or `run-multiplayer.bat server` (Windows)
2. **Start First Client**: Run `./run-multiplayer.sh client` in a second terminal
3. **Start Second Client**: Run `./run-multiplayer.sh client` in a third terminal
4. **Game Interface**: Each client shows a 3x3 grid with connection status
5. **Player Roles**: Player 1 uses **X**, Player 2 uses **O**

### 🎮 Single-Player Mode (Secondary)

#### Getting Started
1. **Launch the Game**: Run `./run.sh` (macOS/Linux) or `run.bat` (Windows)
2. **Game Interface**: You'll see a 3x3 grid with a status message at the bottom
3. **Your Role**: You play as **X** (red color) and always go first

#### How to Play
1. **Your Turn**: Click any empty square to place your X
2. **Computer's Turn**: The computer automatically responds with an O (blue color)
3. **Continue**: Take turns until someone wins or the game is a tie
4. **New Game**: Click the "New Game" button to start over

#### Game States
- **"Your turn (X) - Click any square to start!"** - Ready to begin
- **"Computer is thinking..."** - Computer is making its move
- **"Congratulations! You win!"** - You've won the game
- **"Computer wins! Better luck next time!"** - Computer has won
- **"It's a tie!"** - No winner, all squares are filled

#### Strategy Tips
- **Take the center** if available - it gives you the most winning opportunities
- **Look for two-in-a-row** - either to win or to block the computer
- **Use corners strategically** - they can help create multiple winning paths

### 🌐 Multiplayer Mode

#### Setting Up a Game
1. **Start the Server**: In Terminal 1, run `./run-multiplayer.sh server`
   - Server window will open showing connection logs
   - Server listens on port 8000
2. **Start First Client**: In Terminal 2, run `./run-multiplayer.sh client`
   - Player 1 window opens, shows "Waiting for player 2 to join"
3. **Start Second Client**: In Terminal 3, run `./run-multiplayer.sh client`
   - Player 2 window opens, game automatically starts

#### Player Roles
- **Player 1**: Uses **X** symbol, goes first
- **Player 2**: Uses **O** symbol, goes second

#### How to Play
1. **Wait for Your Turn**: The status bar shows whose turn it is
2. **Make Your Move**: Click any empty square during your turn
3. **Watch Opponent**: See your opponent's move appear automatically
4. **Continue Playing**: Take turns until someone wins or it's a tie

#### Game States
- **"Waiting for player 2 to join"** - Player 1 waiting for opponent
- **"Player 2 has joined. I start first"** - Game is ready to begin
- **"Waiting for player 1 to move"** - Player 2 waiting for first move
- **"My turn"** - It's your turn to make a move
- **"Waiting for the other player to move"** - Opponent is thinking
- **"I won! (X)"** or **"I won! (O)"** - You've won the game
- **"Player 1 (X) has won!"** or **"Player 2 (O) has won!"** - Opponent won
- **"Game is over, no winner!"** - It's a tie

#### Network Features
- **Real-time Updates**: Moves appear instantly across both clients
- **Connection Status**: Status bar shows connection and game progress
- **Automatic Synchronization**: Game state stays in sync between players
- **Multiple Sessions**: Server can handle multiple games simultaneously

### 🎯 Controls

#### Multiplayer Mode (Primary)
- **🖱️ Click any empty square** - Make your move (only during your turn)
- **⏳ Wait for opponent** - When it's not your turn
- **📊 Status updates** - Monitor game progress and connection
- **❌ Close window** - Exit the game

#### Single-Player Mode (Secondary)
- **🖱️ Click any empty square** - Make your move
- **🔄 New Game button** - Start a fresh game
- **❌ Close window** - Exit the game

### 🧠 Computer AI Strategy (Single-Player)
The computer uses intelligent AI with this priority:
1. **🏆 Win if possible** - Takes winning moves when available
2. **🛡️ Block opponent** - Prevents you from winning on your next turn
3. **🎯 Take center** - Prioritizes the center square for strategic advantage
4. **🔲 Take corners** - Chooses corner positions when available
5. **📝 Fill remaining** - Takes any available space as a last resort

### 🏆 Winning Patterns
Look for these winning combinations:
```
X | X | X    X |   |      X |   |      X |   |   
---------    ---------    ---------    ---------
  |   |      X |   |        | X |        |   | X
  |   |      X |   |        |   | X      |   | X
```

### 🎲 Game Examples

#### Example 1: Quick Win
1. You place X in center
2. Computer places O in corner
3. You place X in opposite corner
4. Computer blocks with O
5. You place X in remaining corner - **You win!**

#### Example 2: Strategic Play
1. You place X in corner
2. Computer takes center
3. You place X in opposite corner
4. Computer blocks with O
5. You place X in remaining corner - **You win!**

### 🔧 Troubleshooting Game Issues

#### Single-Player Issues
- **Game not responding**: Try clicking the "New Game" button
- **Can't make moves**: Ensure you're clicking empty squares
- **Computer not moving**: Wait a moment, the AI needs time to think

#### Multiplayer Issues
- **Can't connect**: Make sure the server is running first
- **Connection lost**: Restart both client and server
- **Game stuck**: Close all windows and restart the session
- **Port issues**: Ensure port 8000 is not used by other applications

### 🎯 Pro Tips
- **Study the computer's moves** to learn better strategies
- **Practice in single-player** before trying multiplayer
- **Use the center position** - it's the most valuable square
- **Look ahead** - think about your opponent's next move
- **Don't rush** - take time to plan your strategy

### 📋 Quick Reference

#### Multiplayer Commands (Primary)
```bash
# Terminal 1: Start server
./run-multiplayer.sh server

# Terminal 2: Start first client
./run-multiplayer.sh client

# Terminal 3: Start second client
./run-multiplayer.sh client
```

#### Single-Player Commands (Secondary)
```bash
# macOS/Linux
./run.sh

# Windows
run.bat
```

#### Game Rules Summary
- **Objective**: Get three symbols in a row (horizontal, vertical, or diagonal)
- **Multiplayer**: Player 1 (X) vs Player 2 (O), Player 1 goes first
- **Single-Player**: You (X) vs Computer (O), you go first
- **Winning**: First to get three in a row wins
- **Tie**: All squares filled with no winner



## Game Interface

### 🎨 Visual Elements

#### Single-Player Interface
- **Game Title**: "Tic-Tac-Toe" at the top
- **Subtitle**: "Play against the computer!" 
- **Game Grid**: 3x3 grid of clickable squares
- **Status Bar**: Shows current game state and instructions
- **New Game Button**: Blue button to restart the game
- **Color Coding**: 
  - Your moves (X): Red color
  - Computer moves (O): Blue color
  - Empty squares: Light blue background

#### Multiplayer Interface
- **Player Title**: Shows "Player 1 with token 'X'" or "Player 2 with token 'O'"
- **Game Grid**: 3x3 grid with clickable cells
- **Status Bar**: Shows connection status and turn information
- **Visual Symbols**:
  - X: Cross symbol (two intersecting lines)
  - O: Circle symbol (ellipse)
  - Empty cells: Black border only

#### Server Interface
- **Log Window**: Scrollable text area showing connection logs
- **Connection Info**: Displays IP addresses and session numbers
- **Real-time Updates**: Shows when players join and games start

### 🎮 Game States Visual Guide

#### Single-Player States
```
🟦 Empty Square    🟥 Your X Move    🔵 Computer O Move
```

#### Multiplayer States
```
⬜ Empty Cell      ❌ Player X Move   ⭕ Player O Move
```

### 📱 Responsive Design
- **Window Size**: Automatically adjusts to content
- **Grid Scaling**: Cells resize with window changes
- **Font Scaling**: Text adapts to different screen sizes
- **Cross-Platform**: Consistent appearance on Windows, macOS, and Linux

## Game Screenshots

![Tic-Tac-Toe Game](images/05-tic-tac-toe.png)

*Note: The actual game interface may vary slightly from the screenshot above, as the multiplayer version has been enhanced with additional features.*

## Troubleshooting

### Common Issues

#### Build Errors
```bash
# Clean and rebuild
mvn clean compile

# Check Java version
java -version

# Check Maven version  
mvn -version
```

#### Platform Issues
- **macOS**: Ensure you have the correct architecture (Intel vs Apple Silicon)
- **Windows**: Run as Administrator if needed
- **Linux**: Install OpenJFX packages if using system Java

### Performance Tips
- **Memory**: Game uses minimal memory (~30MB)
- **CPU**: Efficient AI calculations with no unnecessary processing
- **Startup**: Initial load downloads JavaFX modules (one-time setup)

## Development

### Key Classes

#### Multiplayer Mode (Primary)
- **`TicTacToeServer`**: Server application that manages game sessions
- **`TicTacToeClient`**: Client application with JavaFX UI for players
- **`TicTacToeConstants`**: Interface defining game constants
- **`HandleASession`**: Inner class that handles individual game sessions
- **`Cell`**: Inner class representing individual game board cells

#### Single-Player Mode (Secondary)
- **`TicTacToeApp`**: Main application window and UI setup
- **`TicTacToeGame`**: Game logic, board management, and AI implementation
- **`Launcher`**: Application entry point for JavaFX compatibility

### AI Implementation
The computer AI is implemented in the `findBestMove()` method with the following priority:
1. Find winning moves
2. Block opponent's winning moves
3. Take strategic positions (center, corners)
4. Fill remaining spaces

### Styling
The game uses modern CSS with:
- Gradient backgrounds
- Card-style layouts
- Smooth animations
- Color-coded player moves
- Responsive design elements

---

**Enjoy playing Multiplayer Tic-Tac-Toe with friends over the network!** 🎮