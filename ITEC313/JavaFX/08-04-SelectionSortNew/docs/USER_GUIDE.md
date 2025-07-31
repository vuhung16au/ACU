# User Guide - Selection Sort Visualizer

## Getting Started

### System Requirements
- Java 11 or higher
- Maven 3.6 or higher
- 4GB RAM minimum
- 1024x768 screen resolution or higher

### Installation and Setup

1. **Download/Clone the Project**
   ```bash
   # If using git
   git clone <repository-url>
   cd 08-04-SelectionSortNew
   ```

2. **Verify Prerequisites**
   ```bash
   # Check Java version
   java -version
   
   # Check Maven version
   mvn -version
   ```

3. **Run the Application**
   - **macOS/Linux**: `./run.sh`
   - **Windows**: Double-click `run.bat`
   - **Manual**: `mvn clean javafx:run`

## Application Interface

### Main Window Layout

The application window consists of several sections:

1. **Title Bar**: "Selection Sort Visualizer"
2. **Visualization Area**: Displays the array as colored bars
3. **Status Display**: Shows current operation status
4. **Speed Control**: Slider to adjust animation speed
5. **Control Buttons**: Generate array and start sorting

### Visual Elements

#### Array Bars
- **Height**: Represents the value of each array element
- **Color**: Indicates the current state of each element
- **Number**: Displays the actual value above each bar

#### Color Coding System
- **Sky Blue**: Default state (unsorted elements)
- **Dark Blue**: Current element being processed
- **Red**: Element currently being compared
- **Orange**: Current minimum element found
- **Light Green**: Elements in their final sorted position

## How to Use the Application

### Step 1: Generate an Array
1. Click the **"Generate New Array"** button
2. A new random array of 20 elements will be created
3. Each element will have a value between 5 and 100
4. All bars will appear in sky blue (default color)

### Step 2: Start Sorting
1. Click the **"Start Selection Sort"** button
2. The algorithm will begin with visual animation
3. Watch as the algorithm finds minimum elements and swaps them
4. Observe the color changes that indicate different operations

### Step 3: Control Animation Speed
1. Use the **speed slider** at the bottom of the window
2. Move left for faster animation (100ms delays)
3. Move right for slower animation (1000ms delays)
4. Default speed is 500ms per step

### Step 4: Observe the Process
- Watch the algorithm systematically find minimum elements
- Notice how the sorted portion (green) grows from left to right
- Observe the comparisons (red highlighting) in each pass
- See how the current minimum (orange) is tracked

## Understanding the Visualization

### What You're Seeing

#### Pass-by-Pass Breakdown
1. **Current Element**: Dark blue bar shows the position being filled
2. **Searching**: Red bars indicate elements being compared
3. **Minimum Found**: Orange bar highlights the smallest element found so far
4. **Swapping**: Quick color changes show when elements are swapped
5. **Sorted**: Green bars represent elements in their final positions

#### Algorithm Progress
- The algorithm works from left to right
- Each pass finds one minimum element
- The sorted portion grows by one element each pass
- After n-1 passes, the array is completely sorted

### Educational Insights

#### Time Complexity Observation
- Count the number of comparisons made
- Notice that comparisons decrease with each pass
- Total comparisons = n(n-1)/2, demonstrating O(n²) complexity

#### Space Complexity
- Observe that only the original array space is used
- No additional arrays or significant extra memory required
- Demonstrates O(1) space complexity

#### Stability Issues
- Watch for equal values that might change relative positions
- This demonstrates why selection sort is not stable

## Troubleshooting

### Common Issues and Solutions

#### Application Won't Start
**Problem**: Error messages about JavaFX modules
**Solution**: 
- Ensure Java 11+ is installed
- Use the provided run scripts instead of manual java commands
- Try the `run_direct.sh` script on macOS/Linux

#### Slow Performance
**Problem**: Animation is too slow or choppy
**Solution**:
- Reduce animation speed using the slider
- Close other applications to free system resources
- Ensure adequate RAM is available

#### Maven Errors
**Problem**: Maven build failures
**Solution**:
- Verify Maven installation: `mvn -version`
- Check internet connection for dependency downloads
- Clear Maven cache: `mvn clean`

#### Display Issues
**Problem**: UI elements appear incorrectly
**Solution**:
- Ensure screen resolution is at least 1024x768
- Check that graphics drivers are up to date
- Try resizing the application window

### Platform-Specific Notes

#### macOS
- Application works on both Intel and Apple Silicon Macs
- If you encounter permission issues, make scripts executable:
  ```bash
  chmod +x run.sh run_direct.sh
  ```

#### Windows
- Use the `.bat` files for proper execution
- Ensure Windows has Java in the system PATH
- Some antivirus software may flag Maven downloads

#### Linux
- Ensure JavaFX libraries are available
- Some distributions may require additional packages
- Use package manager to install missing dependencies

## Educational Activities

### Learning Exercises

#### Basic Understanding
1. Generate several arrays and observe the pattern
2. Count the number of swaps made in each pass
3. Notice which elements move the most during sorting

#### Advanced Analysis
1. Time different array sizes (modify ARRAY_SIZE in code)
2. Compare with other sorting algorithms
3. Analyze worst-case vs. best-case scenarios

#### Comparative Study
1. Note the differences from Bubble Sort visualization
2. Compare efficiency with other O(n²) algorithms
3. Understand when to use Selection Sort vs. alternatives

### Discussion Points

#### Algorithm Characteristics
- Why does Selection Sort always take O(n²) time?
- When might the minimal number of swaps be advantageous?
- How does this compare to real-world sorting needs?

#### Implementation Details
- Why is the algorithm not stable?
- How does in-place sorting affect memory usage?
- What are the practical limitations of this approach?

## Tips for Best Experience

### Optimal Usage
1. Start with default settings to understand the basic algorithm
2. Experiment with different speeds to see details clearly
3. Generate multiple arrays to see different scenarios
4. Focus on understanding each color transition

### Learning Strategy
1. First, watch the complete process without trying to predict
2. Then, try to predict which element will be selected next
3. Count operations to understand complexity
4. Compare with your manual sorting strategies

This visualizer is designed to enhance understanding of the Selection Sort algorithm through interactive visual learning. Take time to explore different aspects and ask questions about what you observe.
