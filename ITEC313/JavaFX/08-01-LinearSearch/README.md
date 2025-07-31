# Linear Search Algorithm Demo

A comprehensive JavaFX application demonstrating the linear search algorithm with interactive visualization and performance analysis.

## Overview

This application showcases the fundamental linear search algorithm with a complete interactive interface, including:

- **Interactive Search**: Real-time linear search visualization with step-by-step animation
- **Array Management**: Input custom arrays or use predefined sample arrays
- **Visual Feedback**: Highlighting of array elements during search process
- **Performance Metrics**: Display of search results, time complexity, and algorithm efficiency
- **Educational Content**: Comprehensive explanation of Big O notation and algorithm analysis

## Lecture 8 Objectives

Lecture 8, titled "**Developing Efficient Algorithms**," focuses on analyzing the efficiency of algorithms, primarily using **Big O notation**, and introduces various algorithm design paradigms.

The main **objectives** of this lecture are to:
*   Understand why measuring execution time for algorithms is problematic and learn a theoretical approach for analysis.
*   Grasp the concept of **growth rate** and the **Big O notation**.
*   Distinguish between **best, worst, and average-case scenarios** for algorithm performance.
*   Learn how to determine the Big O notation by ignoring multiplicative constants and non-dominating terms.
*   Analyze the time complexity of various programming constructs like loops and conditional statements.
*   Analyze the efficiency of specific algorithms, including linear search, binary search, selection sort, recursive and non-recursive Fibonacci, and Euclid's algorithm.
*   Introduce advanced algorithm design techniques such as **dynamic programming** and **divide-and-conquer**.

**Why Analyze Algorithms?**
Comparing algorithms by measuring their execution time is difficult due to concurrent tasks on a computer and dependency on specific input. For example, a linear search might be faster than binary search if the target element is the very first one in the list. To overcome these issues, a theoretical approach focuses on the **growth rate** of an algorithm's execution time as the input size increases, making comparisons independent of specific computers or input variations.

**Big O Notation**
The **Big O notation** is used to abbreviate for "order of magnitude" and describes how an algorithm's execution time increases with input size. For instance, the complexity of linear search is **O(n)** because its execution time is proportional to the size of the array (n). This notation focuses on the **growth rate**, allowing us to **ignore multiplicative constants** (e.g., O(n) is the same as O(n/2) or O(100n)). It also allows for **ignoring non-dominating terms** for large input sizes (e.g., O(n-1) becomes O(n) because as n grows, the '-1' becomes insignificant).

**Performance Cases (Best, Worst, Average)**
An algorithm's execution time can vary even for the same input size:

- **Best-case input:** Results in the shortest execution time.
- **Worst-case input:** Results in the longest execution time. This is often preferred for analysis as it guarantees the algorithm will never be slower than this.
- **Average-case analysis:** Attempts to determine the average time across all possible inputs of the same size, but is often difficult to perform.

**Analyzing Time Complexity in Code Structures:**

- **Simple Loops:** A single loop iterating `n` times has a time complexity of **O(n)**.
- **Nested Loops:**
  - If outer loop is `n` and inner loop is `m`, it's **O(nm)**.
  - If inner loop depends on outer loop (e.g., `i` times for `i=1 to n`), it's **O(n^2)**.
  - If inner loop is a constant number of times (e.g., 20 times), it remains **O(n)**.
- **Sequence:** Operations executed sequentially, where one's time complexity is greater than others, the overall complexity is determined by the largest one. For example, O(10) followed by O(n) results in **O(n)**.
- **Selection (if/else):** The time complexity is determined by the test time plus the worst-case branch, e.g., an `if` condition taking O(n) and a loop in `else` taking O(n) results in **O(n)**.
- **Constant Time (O(1)):** Operations whose time is not related to the input size, such as retrieving an element at a given array index.

**Algorithm Complexity Examples:**

- **Linear Search:** **O(n)** in the worst case.
- **Binary Search:** **O(logn)**, which is a logarithmic algorithm that grows slowly. Doubling input size only doubles the time.
- **Selection Sort:** **O(n^2)**, a quadratic algorithm that grows quickly. Doubling input size quadruples the time.
- **Recursive Fibonacci Numbers:** Exhibits **O(2^n)** time complexity, making it highly inefficient due to redundant computations of subproblems.
- **Non-recursive Fibonacci Numbers (Dynamic Programming):** Achieves **O(n)** complexity, a significant improvement by solving each subproblem only once and storing results.
- **Euclid's Algorithm for GCD:** Has a time complexity of **O(logn)**.
- **Merge Sort:** Divides the array recursively and then merges sorted subarrays. Its time complexity is **O(nlogn)**.
- **Quick Sort:** Selects a pivot to partition an array into two sub-arrays and recursively sorts them. In the worst-case, it is **O(n^2)**, but in the best and average cases, it's **O(nlogn)**.
- **Heap Sort:** Utilizes a heap data structure (a complete binary tree where each node is greater than or equal to its children). The height of a heap with `n` elements is **O(logn)**. Although not explicitly stated as O(nlogn) in this lecture, it is implied by heap operations and general understanding of efficient sorting.
- **Bucket Sort and Radix Sort:** These are specialized sorting algorithms that can perform better than O(nlogn) (the lower bound for comparison-based sorts) if keys are small integers, potentially reaching **O(n)**.

**Algorithm Design Paradigms:**

- **Dynamic Programming:** Solves problems by breaking them into overlapping subproblems, solving each subproblem only once, and storing their results to avoid redundant computation. The non-recursive Fibonacci algorithm is a prime example.
- **Divide-and-Conquer:** Divides a problem into non-overlapping subproblems, solves them recursively, and then combines their solutions. Many recursive problems follow this approach.
- **Backtracking:** An incremental search approach that abandons a candidate solution as soon as it's determined to be invalid, then explores a new one.

**Recursion vs. Iteration:**
While recursion can be an alternative to loops, it often incurs **substantial overhead** as the system must allocate space for local variables and parameters with each recursive call, consuming memory and requiring extra time for management. However, recursion is beneficial for problems that are inherently recursive.

**Comparing Common Growth Functions:**
The lecture presents a hierarchy of common Big O complexities from most efficient to least efficient:

- **O(1)** (Constant time)
- **O(logn)** (Logarithmic time)
- **O(n)** (Linear time)
- **O(nlogn)** (Log-linear time)
- **O(n^2)** (Quadratic time)
- **O(n^3)** (Cubic time)
- **O(2^n)** (Exponential time)

## Features

### 🔍 Interactive Linear Search
- **Step-by-step visualization**: Watch the algorithm search through array elements
- **Real-time highlighting**: Visual feedback showing current element being checked
- **Search results**: Clear indication of found/not found status with index position
- **Time complexity display**: Shows O(n) complexity with actual step count

### 📊 Array Management
- **Custom arrays**: Input your own comma-separated values
- **Sample arrays**: Pre-loaded examples for quick testing
- **Array visualization**: Visual representation of array elements as colored boxes
- **Dynamic updates**: Real-time array display updates

### 📈 Performance Analysis
- **Step counting**: Track exactly how many comparisons were made
- **Best/worst case scenarios**: Understand when linear search performs best and worst
- **Educational insights**: Learn why linear search is O(n) complexity

### 🎓 Educational Content
- **Algorithm explanation**: Built-in documentation of how linear search works
- **Big O notation**: Understanding time complexity concepts
- **Sample data**: Multiple test cases to explore different scenarios

## Technical Specifications

### Development Environment

- **Java Version**: OpenJDK 24 with preview features enabled
- **JavaFX Version**: 21.0.2
- **Maven Version**: 3.9.x or later
- **Build System**: Maven with JavaFX plugin

### Cross-Platform Support

The application is designed to run on:

- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64  
- **Linux**: x86_64 and ARM64

Platform detection is automatic through Maven profiles.

### Architecture

- **Clean JavaFX Design**: Modern UI with intuitive controls
- **Event-Driven**: Responsive interface with real-time updates
- **Algorithm Visualization**: Clear visual representation of search process
- **Educational Focus**: Designed for learning algorithm concepts
- **Modular Code**: Separate classes for search logic and UI components

## Getting Started

### Prerequisites

1. **Java 24**: Install OpenJDK 24 or later
2. **Maven**: Install Maven 3.9.x or later
3. **JavaFX**: Automatically downloaded by Maven

### Building and Running

#### Option 1: Using Make Commands (Recommended)

```bash
# JavaFX GUI Demo
make run

# Console-based Geeks Demo  
make compile-geeks && make run-geeks

# Clean build artifacts
make clean
```

#### Option 2: Using Maven Commands

```bash
# Compile the project
mvn clean compile

# Run the JavaFX application
mvn javafx:run

# Build fat JAR with dependencies
mvn clean package
```

#### Option 3: Using Shell Scripts

```bash
# On macOS/Linux
./run.sh

# On Windows
run.bat
```

### Quick Start Commands

```bash
# Clone or navigate to project directory
cd 08-01-LinearSearch

# Make scripts executable (macOS/Linux)
chmod +x run.sh

# Build and run JavaFX demo
make run
```

## Project Structure

```
├── src/
│   ├── main/
│   │   ├── java/com/acu/javafx/linearsearch/
│   │   │   ├── LinearSearchApp.java      # Main JavaFX application
│   │   │   └── Geeks.java               # Console-based implementation
│   │   └── resources/
│   │       └── styles.css               # Application styling (future)
│   └── test/java/                       # Unit tests (future)
├── docs/
│   └── algorithm-analysis.md            # Detailed algorithm documentation
├── images/
│   └── 08-01-LinearSearch.png          # Application screenshot
├── pom.xml                              # Maven configuration
├── Makefile                             # Make build commands
├── run.sh                               # Unix/Linux/macOS run script
├── run.bat                              # Windows run script
├── .gitignore                           # Git ignore rules
└── README.md                            # This file
```

## Usage Guide

### JavaFX Application

1. **Input Array**: 
   - Enter comma-separated integers in the "Array Input" field
   - Or click "Use Sample Array" to load predefined examples
   - Click "Set Array" to update the visualization

2. **Search Process**:
   - Enter the target value in the "Search Value" field
   - Click "Search" to start the linear search animation
   - Watch as elements are highlighted during the search process

3. **View Results**:
   - Search results appear in the text area below
   - Status label shows current operation state
   - Step count displays algorithm efficiency

4. **Reset and Retry**:
   - Click "Reset" to clear results and start over
   - Try different arrays and search values
   - Experiment with best-case (first element) and worst-case (last element) scenarios

### Console Application

Run the simple console version to see basic linear search functionality:

```bash
make compile-geeks && make run-geeks
```

This demonstrates the core algorithm without GUI visualization.

## Educational Examples

### Time Complexity Scenarios

**Best Case - O(1)**: Target element is the first in the array
```
Array: [7, 3, 1, 5, 9]
Search: 7
Result: Found at index 0 (1 comparison)
```

**Average Case - O(n/2)**: Target element is in the middle
```
Array: [7, 3, 1, 5, 9]
Search: 1  
Result: Found at index 2 (3 comparisons)
```

**Worst Case - O(n)**: Target element is last or not present
```
Array: [7, 3, 1, 5, 9]
Search: 9
Result: Found at index 4 (5 comparisons)

Search: 10
Result: Not found (5 comparisons)
```

## Troubleshooting

### Common Issues

#### Build Errors
```bash
# Clean and rebuild
make clean && make run

# Check Java version
java -version

# Check Maven version  
mvn -version
```

#### Platform Issues
- **macOS**: Ensure you have the correct architecture (Intel vs Apple Silicon)
- **Windows**: Run as Administrator if needed
- **Linux**: Install OpenJFX packages if using system Java

#### JavaFX Module Issues
```bash
# If you see module-related errors, try:
mvn clean compile
mvn javafx:run
```

### Performance Tips

- **Memory**: Application uses minimal memory (~30MB)
- **CPU**: Efficient algorithm with O(n) linear search
- **Startup**: Initial load downloads JavaFX modules (one-time setup)


## Screenshots

![Linear Search Demo](images/08-01-LinearSearch.png)

