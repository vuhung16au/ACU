# Bin Packing Algorithms Documentation

## Overview

This document provides detailed explanations of the bin packing algorithms implemented in this application, including their strategies, complexity analysis, and theoretical guarantees.

## Problem Definition

The bin packing problem is a combinatorial optimization problem where:
- **Input**: A set of items with weights {w₁, w₂, ..., wₙ} and bin capacity C
- **Goal**: Pack all items into the minimum number of bins
- **Constraint**: The total weight of items in each bin cannot exceed C

## Algorithm Implementations

### 1. First Fit Algorithm

#### Strategy
The First Fit algorithm processes items in the order they are given and places each item into the first bin that has enough remaining capacity.

#### Pseudocode
```
1. Initialize empty list of bins
2. For each item i:
   a. For each existing bin j:
      - If bin j can fit item i, place item i in bin j and break
   b. If no bin can fit item i, create a new bin and place item i in it
3. Return the list of bins
```

#### Time Complexity
- **Time**: O(n × m) where n = number of items, m = number of bins
- **Space**: O(n + m)

#### Theoretical Guarantee
The First Fit algorithm uses at most 2 × OPT bins, where OPT is the optimal number of bins.

#### Advantages
- Simple to implement
- Fast execution
- Reasonable performance for most cases

#### Disadvantages
- May not produce optimal solutions
- Performance can degrade with certain item distributions

### 2. Best Fit Algorithm

#### Strategy
The Best Fit algorithm places each item into the bin that will have the least remaining capacity after placing the item. This minimizes wasted space.

#### Pseudocode
```
1. Initialize empty list of bins
2. For each item i:
   a. Find the bin with the smallest remaining capacity that can fit item i
   b. If such a bin exists, place item i in it
   c. Otherwise, create a new bin and place item i in it
3. Return the list of bins
```

#### Time Complexity
- **Time**: O(n × m) where n = number of items, m = number of bins
- **Space**: O(n + m)

#### Theoretical Guarantee
The Best Fit algorithm uses at most 1.7 × OPT bins.

#### Advantages
- Better space utilization than First Fit
- Often produces fewer bins than First Fit
- Still relatively simple to implement

#### Disadvantages
- Slightly more complex than First Fit
- Still not optimal in general

### 3. First Fit Decreasing Algorithm

#### Strategy
The First Fit Decreasing algorithm first sorts all items in decreasing order by weight, then applies the First Fit algorithm to the sorted items.

#### Pseudocode
```
1. Sort items in decreasing order by weight
2. Apply First Fit algorithm to the sorted items
3. Return the list of bins
```

#### Time Complexity
- **Time**: O(n log n + n × m) where n = number of items, m = number of bins
- **Space**: O(n + m)

#### Theoretical Guarantee
The First Fit Decreasing algorithm uses at most (11/9) × OPT + 6/9 bins.

#### Advantages
- Often produces the best results among the three algorithms
- Strong theoretical guarantee
- Good performance in practice

#### Disadvantages
- Requires sorting step (O(n log n))
- Slightly more complex implementation

## Complexity Analysis

### Time Complexity Comparison

| Algorithm | Best Case | Average Case | Worst Case |
|-----------|-----------|--------------|------------|
| First Fit | O(n) | O(n × m) | O(n × m) |
| Best Fit | O(n) | O(n × m) | O(n × m) |
| First Fit Decreasing | O(n log n) | O(n log n + n × m) | O(n log n + n × m) |

### Space Complexity

All algorithms have O(n + m) space complexity, where:
- n = number of items
- m = number of bins

## Approximation Ratios

| Algorithm | Approximation Ratio | Notes |
|-----------|-------------------|-------|
| First Fit | 2 × OPT | Worst case: 2 × OPT - 1 |
| Best Fit | 1.7 × OPT | Better than First Fit in practice |
| First Fit Decreasing | (11/9) × OPT + 6/9 | Best theoretical guarantee |

## Performance Characteristics

### When to Use Each Algorithm

#### First Fit
- **Best for**: Simple implementations, quick solutions
- **Use when**: Speed is more important than optimality
- **Avoid when**: Need near-optimal solutions

#### Best Fit
- **Best for**: Better space utilization than First Fit
- **Use when**: Want better results than First Fit with similar complexity
- **Avoid when**: Need optimal solutions

#### First Fit Decreasing
- **Best for**: Best practical performance
- **Use when**: Can afford the sorting overhead
- **Avoid when**: Items are already sorted or sorting is expensive

## Implementation Details

### Data Structures Used

1. **ArrayList<Bin>**: Dynamic list of bins
2. **ArrayList<Item>**: List of items to pack
3. **Bin class**: Represents a single bin with capacity and items
4. **Item class**: Represents a single item with weight

### Key Methods

#### Bin Class
- `addItem(Item item)`: Attempts to add an item to the bin
- `getRemainingCapacity()`: Returns remaining capacity
- `getCurrentWeight()`: Returns current weight

#### Algorithm Interface
- `solve(List<Item> items, int binCapacity)`: Main solving method
- `getAlgorithmName()`: Returns algorithm name
- `getTimeComplexity()`: Returns time complexity string

## Testing and Validation

### Test Cases

1. **Empty input**: No items
2. **Single item**: One item
3. **Perfect fit**: Items that exactly fill bins
4. **Imperfect fit**: Items that don't fill bins perfectly
5. **Large items**: Items too large for any bin
6. **Edge cases**: Boundary conditions

### Validation

- All items must be placed (unless too large for any bin)
- No bin should exceed its capacity
- All algorithms should produce valid solutions
- Performance should be within expected bounds

## Future Enhancements

### Potential Improvements

1. **Next Fit Algorithm**: Simpler than First Fit
2. **Worst Fit Algorithm**: Opposite of Best Fit
3. **Optimal Algorithm**: Exact solution using dynamic programming
4. **Genetic Algorithm**: Metaheuristic approach
5. **Simulated Annealing**: Another metaheuristic approach

### Advanced Features

1. **Multi-dimensional bin packing**: Items with multiple dimensions
2. **Online algorithms**: Items arrive one by one
3. **Variable bin sizes**: Different bin capacities
4. **Weighted items**: Items with different priorities
