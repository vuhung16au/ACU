# Linear Search JavaFX Demo - Project Summary

## Overview

This JavaFX application demonstrates the **Linear Search** algorithm with an interactive visual interface. The application provides a hands-on way to understand how linear search works by visualizing each step of the search process in real-time.

## Features

### 🎯 Core Functionality
- **Interactive Linear Search Visualization:** Step-by-step animation of the search process
- **Multiple Sample Arrays:** Pre-defined arrays to demonstrate different scenarios
- **Custom Input Support:** Users can input their own arrays and search values
- **Real-time Feedback:** Visual and textual feedback during search operations

### 🎨 Visual Elements
- **Array Visualization:** Each element displayed as a colored rectangle with value and index
- **Color-coded States:**
  - 🔵 **Blue:** Normal state (not yet checked)
  - 🟡 **Yellow:** Currently being checked
  - 🟢 **Green:** Found element
  - 🔴 **Red:** Not found (after checking all elements)

### 📊 Educational Features
- **Algorithm Complexity Display:** Shows O(n) time complexity and O(1) space complexity
- **Step-by-step Explanation:** Detailed text output of each search step
- **Multiple Test Cases:** Different array sizes and scenarios
- **Interactive Learning:** Hands-on experience with algorithm behavior

## Technical Specifications

### Development Environment
- **Java Version:** OpenJDK 24
- **JavaFX Version:** 21
- **Maven Version:** 3.9.x or later
- **Target Platform:** Cross-platform (macOS, Windows, Linux)

### Architecture
- **Pattern:** Model-View-Controller (MVC)
- **UI Framework:** JavaFX
- **Build Tool:** Maven
- **Package:** `com.acu.javafx.linearsearch`

## Project Structure

```
08-01-LinearSearch/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── acu/
│                   └── javafx/
│                       └── linearsearch/
│                           ├── LinearSearchApp.java    # Main JavaFX application
│                           └── Geeks.java              # Linear search algorithm
├── docs/
│   ├── concepts.md          # Main concepts and design decisions
│   └── architecture.md      # Detailed architecture documentation
├── pom.xml                  # Maven build configuration
├── run.sh                   # Unix/Linux/macOS execution script
├── run.bat                  # Windows execution script
├── .gitignore              # Git ignore rules
├── README.md               # Original lecture notes
└── PROJECT_SUMMARY.md      # This file
```

## How to Use

### Prerequisites
1. **Java 24 or later** installed on your system
2. **Maven 3.9.x or later** installed on your system

### Running the Application

#### Option 1: Using Build Scripts
**On macOS/Linux:**
```bash
./run.sh
```

**On Windows:**
```cmd
run.bat
```

#### Option 2: Using Maven Directly
```bash
mvn clean javafx:run
```

### Using the Application

1. **Start the Application:** Run one of the commands above
2. **Choose an Array:** Use the "Next Sample Array" button to cycle through different arrays
3. **Enter Search Value:** Type the number you want to search for in the "Search for" field
4. **Start Search:** Click "Start Search" to begin the visualization
5. **Watch the Animation:** Observe how the algorithm checks each element step by step
6. **View Results:** Check the results area for detailed output and complexity information
7. **Reset:** Use the "Reset" button to start over with the same array

## Sample Arrays Included

1. **Small Array:** `[3, 4, 1, 7, 5]` - Demonstrates finding an element
2. **Medium Array:** `[10, 20, 30, 40, 50, 60]` - Shows searching in larger array
3. **Sequential Array:** `[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]` - Demonstrates worst-case scenario
4. **High Value Array:** `[100, 200, 300, 400, 500]` - Shows searching with larger numbers

## Algorithm Details

### Linear Search Algorithm
```java
public static int search(int a[], int n, int x) {
    for (int i = 0; i < n; i++) {
        if (a[i] == x)
            return i;
    }
    return -1;  // Element not found
}
```

### Complexity Analysis
- **Time Complexity:** O(n) - Linear time
  - Best Case: O(1) when element is first
  - Average Case: O(n) when element is in middle
  - Worst Case: O(n) when element is last or not present
- **Space Complexity:** O(1) - Constant space

## Educational Value

This application serves as an excellent educational tool for:

1. **Algorithm Understanding:** Visual representation of linear search
2. **Complexity Analysis:** Real-time demonstration of O(n) time complexity
3. **Interactive Learning:** Hands-on experience with different search scenarios
4. **JavaFX Development:** Example of creating interactive applications
5. **Software Engineering:** Demonstration of proper project structure and documentation

## Key Features Demonstrated

### 1. JavaFX Development
- **Scene Graph Management:** Proper organization of UI components
- **Event Handling:** Button clicks and input validation
- **Animation System:** Using PauseTransition for step-by-step visualization
- **Layout Management:** VBox and HBox for responsive design

### 2. Algorithm Visualization
- **Step-by-step Animation:** Visual representation of algorithm execution
- **State Management:** Proper handling of search states
- **Color Coding:** Intuitive visual feedback
- **Real-time Updates:** Immediate feedback during search

### 3. User Experience
- **Intuitive Interface:** Clear sections and logical flow
- **Error Handling:** Proper validation and error messages
- **Responsive Design:** Adapts to different screen sizes
- **Educational Content:** Detailed explanations and complexity information

## Cross-Platform Support

The application is designed to work on:
- **macOS:** Intel (x86_64) and Apple Silicon (ARM64)
- **Windows:** x86_64 and ARM64
- **Linux:** x86_64 and ARM64

## Build and Deployment

### Development
```bash
mvn clean compile
```

### Testing
```bash
mvn test
```

### Package
```bash
mvn package
```

### Run
```bash
mvn javafx:run
```

## Contributing

This project demonstrates:
- Clean, well-documented code
- Proper separation of concerns
- Cross-platform compatibility
- Educational value
- Modern JavaFX development practices

## License

This project is part of the ITEC313 JavaFX course materials and follows the same licensing terms as the parent repository. 