# Chapter 8 Key Terms - Data Structures and Algorithms

## Overview

This document explains the key terms from Chapter 8 of the Data Structures and Algorithms course, providing definitions and practical Java examples for each concept.

---

## Algorithm Analysis Terms

### Average-Case Analysis
**Definition:** The analysis of an algorithm's performance when given typical or random input data, representing the expected behavior in real-world scenarios.

**Java Example:**
```java
public class AverageCaseExample {
    // Linear search average case: O(n/2) = O(n)
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // Found on average at position n/2
            }
        }
        return -1;
    }
}
```

### Best-Case Input
**Definition:** The input configuration that results in the minimum number of operations for an algorithm to complete.

**Java Example:**
```java
public class BestCaseExample {
    // Best case for linear search: target is first element
    public static int linearSearchBestCase(int[] arr, int target) {
        if (arr[0] == target) {
            return 0; // Found immediately - O(1)
        }
        return -1;
    }
}
```

### Big O Notation
**Definition:** A mathematical notation used to describe the upper bound of an algorithm's time or space complexity in the worst-case scenario.

**Java Example:**
```java
public class BigOExamples {
    // O(1) - Constant time
    public static int getFirst(int[] arr) {
        return arr[0];
    }
    
    // O(n) - Linear time
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
    
    // O(n²) - Quadratic time
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
```

### Constant Time
**Definition:** An algorithm that takes the same amount of time to execute regardless of input size, denoted as O(1).

**Java Example:**
```java
public class ConstantTimeExample {
    // O(1) - Always takes same time regardless of array size
    public static int getElement(int[] arr, int index) {
        return arr[index];
    }
    
    public static boolean isEmpty(int[] arr) {
        return arr.length == 0;
    }
}
```

### Exponential Time
**Definition:** An algorithm whose time complexity grows exponentially with input size, denoted as O(2^n) or worse.

**Java Example:**
```java
public class ExponentialTimeExample {
    // O(2^n) - Exponential time (inefficient Fibonacci)
    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
    
    // O(2^n) - Power set generation
    public static List<List<Integer>> generatePowerSet(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        int n = arr.length;
        
        for (int i = 0; i < (1 << n); i++) {
            List<Integer> subset = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    subset.add(arr[j]);
                }
            }
            result.add(subset);
        }
        return result;
    }
}
```

### Growth Rate
**Definition:** How an algorithm's time or space requirements increase as the input size grows, typically expressed using Big O notation.

**Java Example:**
```java
public class GrowthRateExample {
    // Different growth rates demonstrated
    
    // O(1) - Constant growth
    public static int constant(int n) {
        return 1;
    }
    
    // O(log n) - Logarithmic growth
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
    
    // O(n) - Linear growth
    public static int linearSum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }
}
```

### Logarithmic Time
**Definition:** An algorithm whose time complexity grows logarithmically with input size, denoted as O(log n).

**Java Example:**
```java
public class LogarithmicTimeExample {
    // O(log n) - Binary search
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    // O(log n) - Finding power of 2
    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
}
```

### Quadratic Time
**Definition:** An algorithm whose time complexity grows quadratically with input size, denoted as O(n²).

**Java Example:**
```java
public class QuadraticTimeExample {
    // O(n²) - Selection sort
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap elements
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }
    
    // O(n²) - Finding all pairs
    public static void printAllPairs(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                System.out.println("(" + arr[i] + ", " + arr[j] + ")");
            }
        }
    }
}
```

### Space Complexity
**Definition:** The amount of memory space required by an algorithm as a function of input size.

**Java Example:**
```java
public class SpaceComplexityExample {
    // O(1) space - constant space
    public static int sumArray(int[] arr) {
        int sum = 0; // Only one variable
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }
    
    // O(n) space - linear space
    public static int[] copyArray(int[] arr) {
        int[] copy = new int[arr.length]; // New array of size n
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }
    
    // O(n) space - recursive call stack
    public static int factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1); // n recursive calls
    }
}
```

### Time Complexity
**Definition:** The amount of time required by an algorithm to complete as a function of input size.

**Java Example:**
```java
public class TimeComplexityExample {
    // O(1) - Constant time
    public static int getFirst(int[] arr) {
        return arr[0];
    }
    
    // O(n) - Linear time
    public static int findMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
    
    // O(n log n) - Linearithmic time
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
}
```

### Worst-Case Input
**Definition:** The input configuration that results in the maximum number of operations for an algorithm to complete.

**Java Example:**
```java
public class WorstCaseExample {
    // Worst case for linear search: target is last element or not found
    public static int linearSearchWorstCase(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // Found at last position - O(n)
            }
        }
        return -1; // Not found - O(n)
    }
    
    // Worst case for quicksort: already sorted array
    public static void quickSortWorstCase(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);
            quickSortWorstCase(arr, low, pivot - 1);
            quickSortWorstCase(arr, pivot + 1, high);
        }
    }
}
```

---

## Algorithm Design Approaches

### Backtracking Approach
**Definition:** A systematic method for solving problems by trying partial solutions and abandoning them if they cannot lead to a complete solution.

**Java Example:**
```java
public class BacktrackingExample {
    // N-Queens problem using backtracking
    public static boolean solveNQueens(int[][] board, int col) {
        if (col >= board.length) {
            return true; // All queens placed
        }
        
        for (int row = 0; row < board.length; row++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 1; // Place queen
                
                if (solveNQueens(board, col + 1)) {
                    return true;
                }
                
                board[row][col] = 0; // Backtrack - remove queen
            }
        }
        return false;
    }
    
    private static boolean isSafe(int[][] board, int row, int col) {
        // Check row, column, and diagonals
        for (int i = 0; i < col; i++) {
            if (board[row][i] == 1) return false;
        }
        
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) return false;
        }
        
        for (int i = row, j = col; i < board.length && j >= 0; i++, j--) {
            if (board[i][j] == 1) return false;
        }
        
        return true;
    }
}
```

### Brute Force
**Definition:** A straightforward approach that tries all possible solutions to find the correct one, often inefficient but guaranteed to work.

**Java Example:**
```java
public class BruteForceExample {
    // Brute force string matching - O(n*m)
    public static int bruteForceSearch(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        
        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            if (j == m) {
                return i; // Pattern found at index i
            }
        }
        return -1; // Pattern not found
    }
    
    // Brute force maximum subarray - O(n²)
    public static int maxSubarraySum(int[] arr) {
        int maxSum = Integer.MIN_VALUE;
        
        for (int i = 0; i < arr.length; i++) {
            int currentSum = 0;
            for (int j = i; j < arr.length; j++) {
                currentSum += arr[j];
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        return maxSum;
    }
}
```

### Divide-and-Conquer Approach
**Definition:** A problem-solving paradigm that breaks a problem into smaller subproblems, solves them recursively, and combines the solutions.

**Java Example:**
```java
public class DivideAndConquerExample {
    // Merge sort - O(n log n)
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            // Divide: Sort left and right halves
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            
            // Conquer: Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }
    
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];
        
        // Copy data to temp arrays
        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }
        
        // Merge the temp arrays back
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }
        
        // Copy remaining elements
        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }
}
```

### Dynamic Programming Approach
**Definition:** A method for solving complex problems by breaking them down into simpler subproblems and storing the results to avoid redundant calculations.

**Java Example:**
```java
public class DynamicProgrammingExample {
    // Fibonacci with memoization - O(n)
    public static int fibonacciDP(int n) {
        int[] memo = new int[n + 1];
        return fibonacciHelper(n, memo);
    }
    
    private static int fibonacciHelper(int n, int[] memo) {
        if (n <= 1) {
            return n;
        }
        if (memo[n] != 0) {
            return memo[n]; // Return cached result
        }
        
        memo[n] = fibonacciHelper(n - 1, memo) + fibonacciHelper(n - 2, memo);
        return memo[n];
    }
    
    // Longest Common Subsequence - O(m*n)
    public static int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }
}
```

### Convex Hull
**Definition:** The smallest convex polygon that contains all the points in a given set. It's the boundary of the smallest convex set containing all the points.

**Java Example:**
```java
public class ConvexHullExample {
    // Graham Scan algorithm for convex hull - O(n log n)
    public static List<Point> findConvexHull(List<Point> points) {
        if (points.size() < 3) {
            throw new IllegalArgumentException("Need at least 3 points");
        }
        
        // Find bottom-most point
        Point bottom = findBottomPoint(points);
        
        // Sort by polar angle
        List<Point> sorted = sortByPolarAngle(points, bottom);
        
        // Apply Graham Scan
        Stack<Point> hull = new Stack<>();
        hull.push(sorted.get(0));
        hull.push(sorted.get(1));
        
        for (int i = 2; i < sorted.size(); i++) {
            Point current = sorted.get(i);
            
            while (hull.size() > 1 && 
                   crossProduct(hull.get(hull.size() - 2), hull.peek(), current) <= 0) {
                hull.pop();
            }
            hull.push(current);
        }
        
        return new ArrayList<>(hull);
    }
    
    private static double crossProduct(Point p1, Point p2, Point p3) {
        return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
    }
    
    // Point class for convex hull
    static class Point {
        double x, y;
        
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
```

---

## Summary

These key terms form the foundation of algorithm analysis and design. Understanding these concepts is crucial for:

- **Algorithm Selection**: Choosing the right approach for a given problem
- **Performance Analysis**: Predicting how algorithms will behave with different inputs
- **Optimization**: Identifying bottlenecks and improving efficiency
- **Problem Solving**: Applying appropriate design paradigms to complex problems

Each term represents a fundamental concept in computer science that, when combined, provides a comprehensive toolkit for analyzing and designing efficient algorithms.
