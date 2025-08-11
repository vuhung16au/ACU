# FlashText - JavaFX Multithreading Demo

## Overview

FlashText is a JavaFX application that demonstrates multithreading and parallel programming concepts using JavaFX animation. The application shows a text label that flashes between "Programming is fun" and "Welcome" using a background thread.

## Key Concepts Demonstrated

### 1. Multithreading in JavaFX
- **Background Thread**: A separate thread runs the animation logic
- **Platform.runLater()**: Ensures UI updates happen on the JavaFX Application Thread
- **Thread Safety**: Proper synchronization between background thread and UI thread

### 2. JavaFX Application Thread
- JavaFX UI components must be updated on the Application Thread
- `Platform.runLater()` schedules UI updates to run on the correct thread
- Prevents `IllegalStateException` when updating UI from background threads

### 3. Animation Using Threads
- Continuous animation loop using `while(true)`
- `Thread.sleep(200)` creates a 200ms delay between updates
- Text alternates between two states every 200ms

## Project Structure

```
32-02-FlashText/
├── src/main/java/com/acu/javafx/flashtext/
│   └── FlashText.java          # Main application class
├── docs/
│   ├── architecture.md         # Technical architecture details
│   └── concepts.md             # Multithreading concepts explanation
├── pom.xml                     # Maven configuration
├── README.md                   # This file
├── PROJECT_SUMMARY.md          # Project summary and learning outcomes
├── Prompt.md                   # Original project requirements
├── run.sh                      # Unix/Linux/macOS run script
├── run.bat                     # Windows run script
└── run-jar.sh                  # JAR execution script
```

## How It Works

### Core Animation Logic

```java
new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            while (true) {
                if (lblText.getText().trim().length() == 0)
                    text = "Welcome";
                else
                    text = "";

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lblText.setText(text);
                    }
                });

                Thread.sleep(200);
            }
        }
        catch (InterruptedException ex) {
        }
    }
}).start();
```

### Key Components

1. **Background Thread**: Created using `new Thread(Runnable)`
2. **Infinite Loop**: `while(true)` for continuous animation
3. **Text State Check**: Determines current text state
4. **Platform.runLater()**: Schedules UI update on Application Thread
5. **Thread.sleep()**: Creates animation timing

## Running the Application

### Prerequisites
- Java 24 or higher
- Maven 3.6 or higher
- JavaFX 21

### Quick Start

#### Unix/Linux/macOS
```bash
cd 32-02-FlashText
chmod +x run.sh
./run.sh
```

#### Windows
```cmd
cd 32-02-FlashText
run.bat
```

#### Run JAR File
```bash
cd 32-02-FlashText
chmod +x run-jar.sh
./run-jar.sh
```

### Manual Build and Run
```bash
# Build the project
mvn clean compile

# Run with JavaFX Maven plugin
mvn javafx:run

# Or build and run JAR
mvn clean package
java -jar target/flashtext-1.0.0.jar
```

## Expected Behavior

When you run the application, you should see:

1. A small window (200x50 pixels) titled "FlashText"
2. A label displaying "Programming is fun"
3. The text will flash/blink every 200ms, alternating between:
   - "Programming is fun"
   - "Welcome"
4. The animation continues indefinitely until you close the window

## Learning Objectives

### Multithreading Concepts
- **Thread Creation**: How to create and start background threads
- **Thread Communication**: Using `Platform.runLater()` for thread-safe UI updates
- **Thread Lifecycle**: Understanding thread execution and termination

### JavaFX Threading
- **Application Thread**: JavaFX's single-threaded UI model
- **Thread Safety**: Why UI updates must be on the Application Thread
- **Platform.runLater()**: The mechanism for cross-thread UI updates

### Animation Techniques
- **Timing Control**: Using `Thread.sleep()` for animation timing
- **State Management**: Managing animation state in background threads
- **Continuous Animation**: Creating infinite animation loops

## Technical Details

### Thread Safety
- UI components are not thread-safe
- All UI updates must be on the JavaFX Application Thread
- `Platform.runLater()` ensures thread safety

### Performance Considerations
- Background thread doesn't block UI responsiveness
- 200ms delay provides smooth visual animation
- Minimal CPU usage due to sleep intervals

### Error Handling
- `InterruptedException` is caught but not handled (for simplicity)
- Thread termination is not explicitly managed


## Extensions and Modifications

### Possible Enhancements
1. **Variable Speed**: Add controls to change animation speed
2. **Multiple Texts**: Cycle through more than two text states
3. **Color Changes**: Animate text color along with content
4. **User Controls**: Add buttons to start/stop animation
5. **Thread Management**: Proper thread shutdown on application close

### Code Modifications
- Change `Thread.sleep(200)` to adjust animation speed
- Modify the text strings in the if-else condition
- Add more complex state logic for different animation patterns

## Related Concepts

- **JavaFX Animation API**: Alternative to manual threading
- **Timeline**: JavaFX's built-in animation framework
- **Service**: JavaFX's background task framework
- **Task**: JavaFX's one-time background task framework

