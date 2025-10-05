# Water Drop Game

## Overview

This is a simple JavaFX game built using the FXGL framework. The objective is to catch falling water droplets with a bucket controlled by the mouse. Players gain points for each droplet caught and lose points for each droplet missed.

**Game Features:**
- Mouse-controlled bucket movement
- Falling water droplets with physics
- Real-time scoring system
- Sound effects and background music
- Collision detection

## How to Build

To compile and run the game, use the following Maven commands:

```bash
# Clean and compile the project
mvn clean compile

# Run the game
mvn javafx:run
```

Alternatively, you can build a fat JAR and run it directly:

```bash
# Build the project with all dependencies
mvn clean package

# Run the executable JAR
java -jar target/waterdrop-1.0.jar
```

## How to Play the Game

1. **Start the game** - The game window will open with a purple bucket at the bottom
2. **Move the bucket** - Use your mouse to move the bucket left and right
3. **Catch droplets** - Blue water droplets will fall from the top of the screen
4. **Score points** - Catch droplets to gain +1 point, miss droplets to lose -1 point
5. **Watch your score** - Your current score is displayed in the top-left corner as "SCORE: X"

**Controls:**
- Mouse movement: Control bucket position
- No additional keys required

## About FXGL Library

FXGL (JavaFX Game Library) is a modern game development framework for Java that provides:

- **Entity-Component System**: Flexible game object management
- **Built-in Physics**: Collision detection and response
- **Audio Support**: Background music and sound effects
- **Scene Management**: Easy UI and game scene handling
- **Input Handling**: Mouse, keyboard, and gamepad support
- **Asset Management**: Automatic loading of textures, sounds, and music

FXGL simplifies JavaFX game development by providing high-level APIs for common game development tasks, making it perfect for educational projects and rapid prototyping.

## Exercises: How Students Can Improve This Game

Here are 10 exercises to enhance the Water Drop game and learn more about game development:

### 1. **High Score System**
- Implement a persistent high score that saves to a file
- Display the high score on screen
- Show a "New High Score!" message when beaten
- **Learning**: File I/O, data persistence, game state management

### 2. **Add Background Image**
- Replace the white background with a sky or rain scene image
- Ensure the score text remains visible
- **Learning**: Asset management, UI layering, image scaling

### 3. **Variable Water Drop Speed**
- Make droplets fall at different speeds (slow, medium, fast)
- Add visual indicators for different speed types
- **Learning**: Random number generation, entity properties, game balance

### 4. **Power-ups and Special Items**
- Add golden droplets worth more points
- Implement temporary speed boosts or score multipliers
- **Learning**: Game mechanics, conditional logic, visual feedback

### 5. **Lives System**
- Give players 3 lives instead of infinite play
- Show remaining lives on screen
- End game when all lives are lost
- **Learning**: Game state management, win/lose conditions

### 6. **Difficulty Progression**
- Increase droplet spawn rate over time
- Add more droplets as score increases
- Implement speed increases
- **Learning**: Game progression, dynamic difficulty adjustment

### 7. **Sound Effects Enhancement**
- Add different sounds for different events (catch, miss, power-up)
- Implement volume controls
- Add sound for game over
- **Learning**: Audio programming, user preferences

### 8. **Particle Effects**
- Add splash effects when droplets are caught
- Create ripple effects in the bucket
- Add rain particle effects in the background
- **Learning**: Visual effects, animation systems

### 9. **Menu System**
- Create a main menu with Start, Settings, and Quit options
- Add a pause menu during gameplay
- Implement game over screen with restart option
- **Learning**: Scene management, UI design, game flow

### 10. **Mobile/Tablet Support**
- Add touch controls for mobile devices
- Implement swipe gestures for bucket movement
- Optimize UI for different screen sizes
- **Learning**: Cross-platform development, input handling, responsive design

## Additional Challenge Ideas

- **Multiplayer Mode**: Two players competing for the highest score
- **Time Attack Mode**: Score as many points as possible in 60 seconds
- **Weather Effects**: Add wind that affects droplet trajectory
- **Bucket Upgrades**: Allow players to buy bigger buckets or special abilities
- **Leaderboard**: Online high score sharing system

## Technical Requirements

- Java 23 or higher
- Maven 3.6 or higher
- FXGL 21
- JavaFX 21.0.2

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/acu/waterdrop/
│   │       └── DropApp.java          # Main game class
│   └── resources/
│       └── assets/
│           ├── music/
│           │   └── bgm.mp3           # Background music
│           ├── sounds/
│           │   └── drop.wav          # Drop sound effect
│           └── textures/
│               ├── bucket.png        # Bucket sprite
│               └── droplet.png       # Water droplet sprite
```

## Getting Started with Exercises

1. **Start Simple**: Begin with visual improvements (background, colors)
2. **Add Mechanics**: Implement new game rules (lives, power-ups)
3. **Enhance UI**: Create menus and better user experience
4. **Advanced Features**: Add complex systems (save/load, networking)

Each exercise builds upon the previous ones, creating a comprehensive learning experience in game development with JavaFX and FXGL.