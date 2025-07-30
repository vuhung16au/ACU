# JavaFX Sorting Algorithm Visualizer

A comprehensive JavaFX application that demonstrates various sorting algorithms with visual animations and interactive controls.

## Features

- **Multiple Sorting Algorithms**: Insertion Sort, Bubble Sort, Merge Sort, Quick Sort, Heap Sort, and Radix Sort
- **Visual Animation**: Real-time visualization of sorting process with animated bar charts
- **Interactive Controls**: Speed control, algorithm selection, and array generation
- **Cross-Platform**: Runs on macOS, Windows, and Linux
- **Modern UI**: Clean, responsive interface with intuitive controls

## Sorting Algorithms Implemented

### 1. Insertion Sort
- **Time Complexity**: O(n²)
- **Space Complexity**: O(1)
- **Stability**: Stable
- **Best for**: Small arrays, nearly sorted arrays

### 2. Bubble Sort
- **Time Complexity**: O(n²)
- **Space Complexity**: O(1)
- **Stability**: Stable
- **Best for**: Educational purposes, small arrays

### 3. Merge Sort
- **Time Complexity**: O(n log n)
- **Space Complexity**: O(n)
- **Stability**: Stable
- **Best for**: Large datasets, external sorting

### 4. Quick Sort
- **Time Complexity**: O(n log n) average, O(n²) worst case
- **Space Complexity**: O(log n)
- **Stability**: Unstable
- **Best for**: General-purpose sorting, in-place sorting

### 5. Heap Sort
- **Time Complexity**: O(n log n)
- **Space Complexity**: O(1)
- **Stability**: Unstable
- **Best for**: In-place sorting, priority queue implementation

### 6. Radix Sort
- **Time Complexity**: O(d(n+k)) where d is number of digits, k is base
- **Space Complexity**: O(n+k)
- **Stability**: Stable
- **Best for**: Integer sorting, fixed-length strings

## Technical Specifications

### Development Environment
- **Java Version**: OpenJDK 24
- **JavaFX Version**: 21
- **Maven Version**: 3.9.x or later
- **Target Platform**: Cross-platform (macOS, Windows, Linux)

### Architecture
- **Package**: `com.acu.javafx.sorting`
- **Main Class**: `SortingDemo`
- **Build System**: Maven with cross-platform profiles

## Installation and Setup

### Prerequisites
1. **Java**: OpenJDK 24 or later
2. **Maven**: 3.9.x or later
3. **Git**: For cloning the repository

### Quick Start

#### On macOS/Linux:
```bash
# Clone the repository
git clone <repository-url>
cd 09-01-Sorting

# Make script executable
chmod +x run.sh

# Run the application
./run.sh
```

#### On Windows:
```cmd
# Clone the repository
git clone <repository-url>
cd 09-01-Sorting

# Run the application
run.bat
```

#### Using Maven directly:
```bash
# Build the project
mvn clean compile

# Run the application
mvn javafx:run
```

#### Using Makefile:
```bash
# Build all Java files
make build

# Run individual sorting algorithms
make run_BubbleSort
make run_HeapSort
make run_InsertionSort
make run_MergeSort
make run_QuickSort
make run_RadixSort

# Run all CLI sorting algorithms in sequence
make run-all-cli

# Run the JavaFX visualizer
make run_SortingDemo

# Clean compiled files
make clean
```

## Usage

### Main Interface
1. **Algorithm Selection**: Choose from the dropdown menu
2. **Speed Control**: Adjust animation speed with the slider
3. **Sort Button**: Start the sorting animation
4. **Reset Button**: Restore the original array
5. **Generate New**: Create a new random array

### Controls
- **Algorithm Dropdown**: Select sorting algorithm
- **Speed Slider**: Control animation speed (0.1x to 2.0x)
- **Sort**: Begin sorting with selected algorithm
- **Reset**: Return to original unsorted array
- **Generate New**: Create new random array

### Visual Elements
- **Bar Chart**: Each bar represents an array element
- **Value Labels**: Show actual numeric values
- **Color Coding**: Bars change color during sorting
- **Status Updates**: Real-time progress information

## Project Structure

```
09-01-Sorting/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── acu/
│                   └── javafx/
│                       └── sorting/
│                           ├── SortingDemo.java      # Main JavaFX application
│                           ├── InsertionSort.java    # Insertion sort implementation
│                           ├── BubbleSort.java       # Bubble sort implementation
│                           ├── MergeSort.java        # Merge sort implementation
│                           ├── QuickSort.java        # Quick sort implementation
│                           ├── HeapSort.java         # Heap sort implementation
│                           ├── Heap.java             # Heap data structure
│                           └── RadixSort.java        # Radix sort implementation
├── pom.xml                 # Maven configuration
├── run.sh                  # Unix/Linux/macOS execution script
├── run.bat                 # Windows execution script
└── README.md              # This file
```

## Build Configuration

### Maven Profiles
The project includes platform-specific Maven profiles for automatic dependency management:

- **macOS**: Automatically detects and uses macOS-specific JavaFX modules
- **Windows**: Uses Windows-specific JavaFX modules
- **Linux**: Uses Linux-specific JavaFX modules

### Cross-Platform Compatibility
- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64
- **Linux**: x86_64 and ARM64

## Development

### Adding New Algorithms
1. Create a new Java class in the `com.acu.javafx.sorting` package
2. Implement the sorting algorithm with static methods
3. Add the algorithm to the `SortingDemo.java` switch statement
4. Update the algorithm selection dropdown

### Customizing the UI
- Modify `SortingDemo.java` for UI changes
- Update CSS styles for visual customization
- Add new controls in the `createControls()` method

### Performance Optimization
- The application uses JavaFX Platform.runLater() for thread-safe UI updates
- Animation timing is controlled by the Timeline API
- Memory management is handled automatically by JavaFX

## Troubleshooting

### Common Issues

#### Java Version Issues
```bash
# Check Java version
java -version

# If version is < 24, install OpenJDK 24+
# On macOS: brew install openjdk@24
# On Ubuntu: sudo apt install openjdk-24-jdk
# On Windows: Download from https://adoptium.net/
```

#### Maven Issues
```bash
# Check Maven version
mvn -version

# If Maven is not installed:
# On macOS: brew install maven
# On Ubuntu: sudo apt install maven
# On Windows: Download from https://maven.apache.org/
```

#### JavaFX Module Issues
```bash
# Clean and rebuild
mvn clean compile

# Run with explicit module path
mvn javafx:run -Djavafx.verbose=true
```

### Platform-Specific Notes

#### macOS
- Works natively on both Intel and Apple Silicon
- No additional configuration required
- Uses system JavaFX modules

#### Windows
- Requires JavaFX modules (handled by Maven profiles)
- May need Visual C++ Redistributable for older systems
- Tested on Windows 10/11

#### Linux
- May require additional font packages
- Uses system JavaFX modules
- Tested on Ubuntu 20.04+

## Contributing

1. Fork the repository
2. Create a feature branch
3. Implement your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Sorting algorithm implementations based on standard computer science literature
- JavaFX framework for the user interface
- Maven for build management and dependency resolution

## Version History

- **v1.0.0**: Initial release with 6 sorting algorithms
  - Insertion Sort, Bubble Sort, Merge Sort, Quick Sort, Heap Sort, Radix Sort
  - Visual animation system
  - Cross-platform compatibility
  - Interactive controls

## Support

For issues and questions:
1. Check the troubleshooting section
2. Review the project documentation
3. Create an issue on the repository
4. Contact the development team

---

**Note**: This application is designed for educational purposes and algorithm visualization. For production sorting, consider using the built-in `Arrays.sort()` method or specialized sorting libraries.