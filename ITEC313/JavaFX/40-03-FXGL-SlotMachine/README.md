# JavaFX Slot Machine Game

A modern slot machine game built with JavaFX and FXGL framework, featuring interactive gameplay and engaging visual effects.

## Overview

This project is a fully functional slot machine game developed using JavaFX and the FXGL game development framework. The game features:

- **5 spinning wheels** with different symbols
- **Interactive lever** to trigger spins
- **Money system** with starting balance of $500
- **Win detection** based on matching symbols
- **Real-time balance updates**
- **Smooth animations** and visual feedback

The game is built using the `com.acu.games.slotmachine` package structure and follows modern Java development practices with Maven build system.

## How to Build

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build and Run Commands

```bash
# Clean and compile the project
mvn clean compile

# Run the JavaFX application
mvn javafx:run

# Or run both commands together
mvn clean compile javafx:run
```

### Alternative Run Methods
```bash
# Run with specific JavaFX modules
mvn javafx:run -Djavafx.args="--add-modules javafx.controls,javafx.fxml"

# Package the application
mvn clean package
```

## How to Play the Game

1. **Start the Game**: Launch the application using `mvn javafx:run`
2. **Check Your Balance**: You start with $500 displayed at the top of the screen
3. **Spin the Wheels**: Click the lever on the right side to spin all 5 wheels
4. **Wait for Results**: Watch the wheels spin and stop automatically
5. **Check Winnings**: 
   - If you get 2 or more matching symbols, you win money
   - The more matching symbols, the higher the payout
   - If no matches, you lose $100
6. **Continue Playing**: Keep spinning to try your luck!

### Scoring System
- **2+ matching symbols**: Win money (formula: `highestMatch × highestMatch × 50`)
- **No matches**: Lose $100
- **Maximum payout**: 5 matching symbols = 5 × 5 × 50 = $1,250

## Student Exercises

Here are 10 progressive exercises to help students practice and enhance the slot machine game:

### **Beginner Level (1-3)**

#### 1. **Add Sound Effects**
- Add click sound when the lever is pressed
- Add spinning sound while wheels are rotating
- Add win/lose sound effects based on results
- **Implementation**: Use `MediaPlayer` and `AudioClip` from JavaFX

#### 2. **Display Spin Counter**
- Add a counter showing total number of spins
- Display it on the UI alongside the money display
- **Implementation**: Add a new game variable and update it in the `spin()` method

#### 3. **Add Pause Between Spins**
- Prevent rapid clicking of the lever
- Add a 2-second cooldown after each spin
- **Implementation**: Use a boolean flag and `Timeline` for delay

### **Easy Level (4-6)**

#### 4. **Bonus Rounds**
- Trigger a bonus round when getting 3+ matching symbols
- Add special visual effects (flashing lights, particle effects)
- Give extra spins or multiplier bonuses
- **Implementation**: Create a `BonusRoundComponent` and special UI elements

#### 5. **Autoplay Feature**
- Add an "Autoplay" button that spins automatically
- Allow setting number of auto-spins (5, 10, 25, 50)
- Add a stop button to cancel autoplay
- **Implementation**: Use `Timeline` with configurable repeat count

#### 6. **Save/Load Game State**
- Save current money balance to a file
- Load saved game when starting the application
- Add "New Game" option to reset balance
- **Implementation**: Use `Properties` class or JSON serialization

### **Intermediate Level (7-10)**

#### 7. **Gamble Feature**
- After winning, offer a gamble option (double or nothing)
- Show a card (red/black) guessing game
- Risk current winnings for double payout
- **Implementation**: Create a separate `GambleDialog` with card animation

#### 8. **Progressive Jackpot**
- Add a jackpot that increases with each spin
- Display jackpot amount on screen
- Award jackpot for 5 matching symbols
- **Implementation**: Add jackpot variable that increments by $10 each spin

#### 9. **Statistics Dashboard**
- Track total wins, losses, biggest win, total spins
- Show win percentage and average payout
- Add a statistics window accessible from main menu
- **Implementation**: Create a `StatisticsComponent` with data persistence

#### 10. **Themed Symbols and Animations**
- Replace basic symbols with themed graphics (fruits, gems, etc.)
- Add symbol-specific animations when winning
- Create different symbol sets (classic, modern, fantasy)
- **Implementation**: Create `SymbolType` enum and animated sprite components

### **Bonus Challenge (Advanced)**

#### 11. **Multiplayer Mode**
- Add network support for multiple players
- Show other players' balances and recent wins
- Implement leaderboards
- **Implementation**: Use JavaFX `WebSocket` or simple TCP sockets

#### 12. **Achievement System**
- Track milestones (first win, 10 wins in a row, etc.)
- Display achievement notifications
- Add achievement badges and rewards
- **Implementation**: Create `AchievementManager` with event-driven architecture

## Project Structure

```
src/main/java/com/acu/games/slotmachine/
├── SlotMachineApp.java          # Main application class
├── SlotMachineFactory.java     # Entity factory for game objects
├── SlotMachineType.java         # Entity type definitions
└── components/
    ├── LeverComponent.java     # Lever interaction logic
    └── WheelComponent.java     # Wheel spinning mechanics
```

## Dependencies

- **FXGL 21**: Game development framework
- **JavaFX 21**: UI framework
- **Maven**: Build and dependency management

## Contributing

1. Fork the repository
2. Create a feature branch
3. Implement your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is for educational purposes as part of the ITEC313 JavaFX course at ACU.
