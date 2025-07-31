# Selection Sort Visualizer - JavaFX Implementation

## Project Overview

This project is a JavaFX-based visual demonstration of the Selection Sort algorithm, inspired by the GeeksforGeeks JavaScript implementation. The application provides an interactive way to understand how the Selection Sort algorithm works through animated visualization.

## Original Reference

Based on: [Selection Sort Visualizer in JavaScript - GeeksforGeeks](https://www.geeksforgeeks.org/javascript/selection-sort-visualizer-in-javascript/)

The original JavaScript implementation provided the foundation, but this JavaFX version offers enhanced features including:
- Native desktop application experience
- Better performance and smoother animations
- Professional UI with comprehensive styling
- Cross-platform compatibility
- Educational documentation

## Original Requirements

Base on the GeeksforGeeks article Selection Sort Simulation (JavaScript) below:
https://www.geeksforgeeks.org/javascript/selection-sort-visualizer-in-javascript/

The source code for the JavaScript version of Selection Sort is available at:
- `/src-javascript/index.html`
- `/src-javascript/index.js`  
- `/src-javascript/style.css`

**Task**: Create a JavaFX application that implements the selection sort algorithm, similar to the JavaScript version provided in the GeeksforGeeks article.

## Implementation Summary

### What Was Created

✅ **Complete JavaFX Application** with the following components:
- `Launcher.java` - Application entry point
- `SelectionSortApp.java` - Main application with visualization logic
- `styles.css` - Professional styling
- Comprehensive documentation in `docs/` directory

✅ **Visual Features Implemented**:
- Array elements displayed as colored bars with values
- Color-coded algorithm states (current, comparing, minimum, sorted)
- Smooth animations with configurable speed
- Interactive controls for array generation and sorting

✅ **Enhanced User Experience**:
- Professional desktop application interface
- Real-time status updates
- Speed control slider
- Responsive button states
- Cross-platform compatibility

✅ **Educational Value**:
- Step-by-step algorithm visualization
- Clear demonstration of O(n²) complexity
- Shows in-place sorting characteristics
- Comprehensive documentation for learning

### Key Features

#### Visual Animation
- **Array Representation**: Elements displayed as colored bars with values
- **Color-Coded States**: Different colors indicate algorithm operations
  - Sky Blue: Unsorted elements
  - Dark Blue: Current element being processed
  - Red: Element being compared
  - Orange: Current minimum element
  - Light Green: Sorted elements

#### Interactive Controls
- **Generate New Array**: Creates random arrays for sorting
- **Start Sorting**: Initiates the visualization
- **Speed Control**: Adjustable animation speed (100ms - 1000ms)
- **Status Display**: Real-time algorithm status updates

### Technical Implementation

#### Architecture
- **MVC Pattern**: Clean separation of model, view, and controller logic
- **JavaFX Components**: Modern UI framework with responsive design
- **Animation System**: Smooth transitions using PauseTransition
- **State Management**: Proper handling of application and algorithm states

#### Key Classes
- **Launcher.java**: Application entry point
- **SelectionSortApp.java**: Main application with visualization logic
- **BarElement**: Inner class representing visual array elements

#### Animation Logic
The algorithm uses instance variables to maintain sorting state:
- `sortCurrentIndex`: Current position being filled
- `sortCompareIndex`: Current element being compared
- `sortMinIndex`: Index of current minimum element

This approach avoids lambda variable capture issues while maintaining clean animation flow.

### Development Challenges Solved

#### Lambda Variable Capture
**Problem**: Java lambda expressions require effectively final variables
**Solution**: Used instance variables for sorting state instead of method parameters

#### Cross-Platform Compatibility
**Problem**: JavaFX dependencies vary by platform
**Solution**: Maven profiles for macOS (Intel/ARM), Windows, and Linux

#### Animation Timing
**Problem**: Smooth, controllable animations
**Solution**: PauseTransition with configurable delays and proper state management

## Project Structure
```
08-04-SelectionSortNew/
├── src/main/java/com/acu/javafx/selectionsort/
│   ├── Launcher.java              # Application entry point
│   └── SelectionSortApp.java      # Main application logic
├── src/main/resources/
│   └── styles.css                 # Application styling
├── docs/                          # Comprehensive documentation
│   ├── ALGORITHM.md               # Algorithm explanation
│   ├── IMPLEMENTATION.md          # Technical details
│   └── USER_GUIDE.md              # Usage instructions
├── src-javascript/                # Original JavaScript reference
├── pom.xml                        # Maven configuration
├── run.sh                         # Unix execution script
├── run.bat                        # Windows execution script
├── run_direct.sh                  # Direct execution option
├── Prompt.md                      # This documentation
└── README.md                      # Project overview
```

## How to Run

### Quick Start
1. Ensure Java 11+ and Maven are installed
2. Run `./run.sh` (macOS/Linux) or `run.bat` (Windows)
3. Click "Generate New Array" to create test data
4. Click "Start Selection Sort" to begin visualization
5. Adjust speed slider for preferred animation pace

### Development Commands
```bash
# Compile the application
mvn clean compile

# Run the application
mvn javafx:run

# Package the application
mvn clean package
```

## Educational Applications

### Learning Objectives
Students can observe and understand:
- How Selection Sort finds minimum elements systematically
- The nested loop structure resulting in O(n²) complexity
- Why the algorithm requires n-1 passes to sort n elements
- The concept of in-place sorting with O(1) space complexity
- Algorithm stability issues through visual examples

### Teaching Benefits
- **Visual Learning**: Complex concepts made accessible through animation
- **Interactive Exploration**: Students control the learning pace
- **Immediate Feedback**: Real-time status and operation explanations
- **Comparative Analysis**: Easy to compare with other sorting methods

## Comparison with Original JavaScript Version

### JavaScript Version Advantages
- Web-based accessibility
- No installation required
- Simple deployment

### JavaFX Version Advantages
- **Performance**: Native application with better responsiveness
- **Functionality**: More robust error handling and state management
- **User Experience**: Professional desktop application interface
- **Maintainability**: Structured code with proper object-oriented design
- **Extensibility**: Easier to add new features and modifications
- **Cross-Platform**: Standalone application for any Java-supported system

## Conclusion

This JavaFX implementation successfully translates the educational value of the original JavaScript version into a more robust, professional desktop application. It demonstrates how algorithm visualization can be enhanced through proper software engineering practices while maintaining the core educational objectives.

The project serves as both a learning tool for understanding Selection Sort and a reference implementation for creating educational JavaFX applications with animation and user interaction.


```
# Selection Sort Simulation (JavaScript) 
ref. https://www.geeksforgeeks.org/javascript/selection-sort-visualizer-in-javascript/