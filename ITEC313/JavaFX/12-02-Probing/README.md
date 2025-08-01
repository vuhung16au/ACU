# JavaFX Probing Techniques Demonstration

This JavaFX application demonstrates three different hash table collision resolution techniques:
- **Linear Probing**
- **Quadratic Probing** 
- **Separate Chaining**

## Overview

The application provides an interactive interface to visualize and compare how different probing techniques handle hash table collisions. Users can add, remove, and search elements while observing how each technique manages the hash table structure.

## Features

### Interactive Operations
- **Add**: Insert new elements into all three hash tables
- **Remove**: Delete elements from all hash tables
- **Search**: Check if elements exist in the hash tables
- **Clear All**: Reset all hash tables to initial state

### Visualization
- Real-time display of hash table contents
- Load factor monitoring
- Capacity and size tracking
- Side-by-side comparison of all three techniques

### Configuration
- Adjustable load factor threshold
- Automatic rehashing when load factor is exceeded
- Configurable table capacity

## Technical Specifications

### Development Environment
- **Java Version**: OpenJDK 24
- **JavaFX Version**: 21
- **Maven Version**: 3.9.x or later
- **Target Platform**: Cross-platform (macOS, Windows, Linux)

### Project Structure
```
12-02-Probing/
├── src/main/java/com/acu/javafx/probing/
│   ├── LinkedList.java                    # Linked list implementation
│   ├── HashSetUsingLinearProbing.java    # Linear probing hash table
│   ├── HashSetUsingQuadraticProbing.java # Quadratic probing hash table
│   ├── MyHashSet.java                    # Separate chaining hash table
│   └── ProbingDemo.java                  # Main JavaFX application
├── pom.xml                               # Maven configuration
├── run.sh                                # Unix/Linux/macOS execution script
├── run.bat                               # Windows execution script
├── run_direct.sh                         # Direct Java execution script
└── README.md                             # This file
```

## Build and Run Instructions

### Prerequisites
- Java 24 or later
- Maven 3.9.x or later
- JavaFX 21

### Using Maven (Recommended)

#### Unix/Linux/macOS
```bash
chmod +x run.sh
./run.sh
```

#### Windows
```cmd
run.bat
```

### Manual Build and Run

#### Build
```bash
mvn clean compile
```

#### Run
```bash
mvn javafx:run
```

### Direct Java Execution (Unix/Linux/macOS)
```bash
chmod +x run_direct.sh
./run_direct.sh
```

## Hash Table Implementations

### Linear Probing (`HashSetUsingLinearProbing`)
- Uses linear sequence to resolve collisions
- Formula: `h(k, i) = (h(k) + i) mod m`
- Simple but can lead to clustering

### Quadratic Probing (`HashSetUsingQuadraticProbing`)
- Uses quadratic sequence to resolve collisions
- Formula: `h(k, i) = (h(k) + i²) mod m`
- Reduces clustering compared to linear probing

### Separate Chaining (`MyHashSet`)
- Uses linked lists to handle collisions
- Each bucket can hold multiple elements
- No clustering issues

## Usage Guide

1. **Launch the Application**: Run the appropriate script for your platform
2. **Add Elements**: Enter a number (0-99) and click "Add"
3. **Remove Elements**: Enter a number and click "Remove"
4. **Search Elements**: Enter a number and click "Search"
5. **Adjust Load Factor**: Enter a value (0-1) and click "Set"
6. **Clear All**: Click "Clear All" to reset all hash tables

## Visual Output

The application displays three panels showing:
- Current capacity and size
- Load factor and threshold
- Hash table contents
- Element lists

### Sample Output
```
Linear Probing Hash Table
========================
Capacity: 11
Size: 5
Load Factor: 0.45
Threshold: 0.75

Index | Value
------|-------
    0 | null
    1 | null
    2 | null
    3 | null
    4 | 4
    5 | 16
    6 | 28
    7 | 21
    8 | null
    9 | null
   10 | null

Elements: [4, 16, 28, 21, 44]
```

## Algorithm Details

### Hash Function
All implementations use the same hash function:
```java
public int hash(Integer hashCode) {
    return hashCode % this.capacity;
}
```

### Rehashing
When the load factor exceeds the threshold:
1. Double the capacity
2. Rehash all existing elements
3. Maintain the same collision resolution strategy

### Deletion Handling
- **Linear/Quadratic Probing**: Mark deleted positions with `Integer.MIN_VALUE`
- **Separate Chaining**: Remove elements from linked lists



## Screenshots

![Probing Demo](images/12-02-Probing.png)


