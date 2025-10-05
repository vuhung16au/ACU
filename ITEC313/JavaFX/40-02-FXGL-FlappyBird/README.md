# Flappy Bird Clone

## Overview

This is a JavaFX-based Flappy Bird clone built using the FXGL game engine. The game features a bird that players control by jumping through obstacles (walls) while avoiding collisions. The game includes background music, sound effects, and a scoring system.

**Key Features:**
- Smooth bird physics with gravity and jump mechanics
- Procedurally generated wall obstacles
- Background music and jump sound effects
- Real-time scoring system
- Color-changing background
- Collision detection and game over mechanics

## How to Build

To build and run the game, use the following Maven command:

```bash
mvn clean compile javafx:run
```

**Prerequisites:**
- Java 11 or higher
- Maven 3.6 or higher
- JavaFX runtime

## How to Play the Game

1. **Start the Game**: Run the Maven command above to launch the game
2. **Controls**: 
   - Press `SPACE` or use the virtual D-pad to make the bird jump
   - The bird will fall due to gravity - time your jumps carefully
3. **Objective**: Navigate the bird through the gaps between wall obstacles
4. **Scoring**: Your score increases automatically as you survive
5. **Game Over**: The game ends when the bird hits a wall or falls off the screen
6. **Restart**: The game automatically restarts when you die

## Student Exercises

Here are 10 exercises to practice JavaFX and game development skills, ordered from simple to intermediate:

### 1. Add "Start the Game" Button
**Difficulty: Easy**
- Add a start screen with a "Start Game" button
- Hide the button and begin gameplay when clicked
- **Hint**: Use `Button` component and `setOnAction()` method

### 2. Add "Pause the Game" Button
**Difficulty: Easy**
- Add a pause button to the game UI
- Pause the game loop when clicked
- **Hint**: Use `getGameController().pauseEngine()` method

### 3. Add "Resume the Game" Button
**Difficulty: Easy**
- Add a resume button that appears when the game is paused
- Resume the game loop when clicked
- **Hint**: Use `getGameController().resumeEngine()` method

### 4. Add "Restart the Game" Button
**Difficulty: Easy**
- Add a restart button to the game UI
- Reset the game state and start a new game
- **Hint**: Use `getGameController().startNewGame()` method

### 5. Add "Exit the Game" Button
**Difficulty: Easy**
- Add an exit button to the game UI
- Close the application when clicked
- **Hint**: Use `getGameController().exit()` method

### 6. Add "Sound On/Off" Button
**Difficulty: Easy**
- Add a toggle button for sound effects
- Control the jump sound effect playback
- **Hint**: Use a boolean variable to track sound state

### 7. Add "Music On/Off" Button
**Difficulty: Easy**
- Add a toggle button for background music
- Control the background music playback
- **Hint**: Use `getAudioPlayer().stopMusic()` and `getAudioPlayer().loopBGM()` methods

### 8. Add "Fullscreen" Button
**Difficulty: Easy**
- Add a fullscreen toggle button
- Switch between windowed and fullscreen mode
- **Hint**: Use `getGameController().getGameScene().getRoot().setScaleX/Y()` or window properties

### 9. Show High Score
**Difficulty: Intermediate**
- Track and display the highest score achieved
- Persist the high score between game sessions
- **Hint**: Use `getGameState().setValue()` and `getGameState().getValue()` methods

### 10. Add Game Over Screen
**Difficulty: Intermediate**
- Create a game over screen that shows when the bird dies
- Display final score and high score
- Include "Play Again" and "Exit" buttons
- **Hint**: Use `Text` components and `getGameController().startNewGame()` method

## Project Structure

```
src/main/java/com/acu/fxgl/flappy/
├── FlappyBirdApp.java          # Main game application
├── PlayerComponent.java        # Bird physics and controls
├── EntityType.java             # Game entity types
├── ColorChangingComponent.java # Background color animation
└── WallBuildingComponent.java  # Wall generation logic
```

## Dependencies

- **FXGL 21**: Game engine framework
- **JavaFX 21**: UI framework
- **Maven**: Build tool

## Learning Objectives

By completing these exercises, students will learn:
- JavaFX UI components and event handling
- Game state management
- Audio control in games
- User interface design
- Game loop control
- Data persistence