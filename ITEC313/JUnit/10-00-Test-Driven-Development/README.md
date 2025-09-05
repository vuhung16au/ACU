# Test-Driven Development (TDD) with Bubble Sort

## Project Overview

This project demonstrates **Test-Driven Development (TDD)** principles using a Bubble Sort algorithm implementation. Students will learn how to write tests first, then implement the data structure, following the TDD cycle: **Red → Green → Refactor**.

The project focuses on teaching students to:
- Write comprehensive tests before implementing functionality
- Understand requirements through test specifications
- Implement code to make tests pass
- Refactor code while maintaining test coverage
- Learn data structure concepts through testing

## What is TDD?

**Test-Driven Development (TDD)** is a software development approach where you write tests before writing the actual implementation code. The TDD cycle consists of three phases:

### 🔴 Red Phase
- Write a failing test that describes the desired functionality
- The test should fail because the implementation doesn't exist yet
- This helps clarify requirements and expected behavior

### 🟢 Green Phase
- Write the minimum code necessary to make the test pass
- Focus on making the test pass, not on perfect code
- This ensures the implementation meets the test requirements

### 🔵 Refactor Phase
- Improve the code quality while keeping all tests passing
- Remove duplication, improve readability, optimize performance
- Ensure the refactored code still passes all tests

## Why TDD?

### Benefits for Students:

1. **Clear Requirements**: Tests serve as executable specifications
2. **Better Design**: Writing tests first leads to more modular, testable code
3. **Confidence**: Comprehensive test coverage provides confidence in code changes
4. **Documentation**: Tests serve as living documentation of how code should behave
5. **Debugging**: Failing tests help identify issues quickly
6. **Learning**: Tests help understand data structure behavior and edge cases

### Benefits for Data Structure Learning:

1. **Understanding Requirements**: Tests clarify what each operation should do
2. **Edge Case Discovery**: Tests help identify boundary conditions
3. **Algorithm Validation**: Tests verify algorithm correctness
4. **Performance Awareness**: Tests can include performance expectations
5. **API Design**: Tests help design clean, intuitive interfaces

## The Implementation of TDD in This Project

### Project Structure

```
src/
├── main/java/com/acu/datastructure/bubblesort/
│   ├── BubbleSort.java          # Implementation (students write this)
│   └── Main.java               # Demo application
└── test/java/com/acu/datastructure/bubblesort/
    └── BubbleSortTest.java     # Test suite (students write this first)
```

### TDD Learning Approach

#### Phase 1: Understanding Requirements Through Tests
Students start by writing tests that describe the expected behavior of a Bubble Sort algorithm:

```java
@Test
@DisplayName("Should sort an unsorted array in ascending order")
void shouldSortUnsortedArray() {
    // Given
    int[] unsortedArray = {64, 34, 25, 12, 22, 11, 90};
    int[] expectedArray = {11, 12, 22, 25, 34, 64, 90};
    
    // When
    int[] result = bubbleSort.sort(unsortedArray);
    
    // Then
    assertArrayEquals(expectedArray, result);
}
```

#### Phase 2: Implementing to Make Tests Pass
Students then implement the Bubble Sort algorithm to make their tests pass:

```java
public int[] sort(int[] arr) {
    if (arr == null || arr.length <= 1) {
        return arr;
    }
    
    int n = arr.length;
    boolean swapped;
    
    for (int i = 0; i < n - 1; i++) {
        swapped = false;
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                // Swap elements
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break; // Early termination
    }
    
    return arr;
}
```

#### Phase 3: Refactoring and Enhancement
Students improve their implementation while maintaining test coverage:

- Add optimization (early termination)
- Handle edge cases (null, empty arrays)
- Add additional functionality (descending sort)
- Improve code readability

### Test Categories for Learning

#### 1. **Basic Functionality Tests**
- Sorting unsorted arrays
- Handling already sorted arrays
- Sorting arrays with duplicates

#### 2. **Edge Case Tests**
- Empty arrays
- Single element arrays
- Null arrays
- Arrays with negative numbers

#### 3. **Performance Tests**
- Large arrays
- Worst-case scenarios (reverse sorted)
- Best-case scenarios (already sorted)

#### 4. **Additional Feature Tests**
- Descending sort
- Array validation
- Sort verification

### TDD Workflow for Students

1. **Write a Test**: Start with the simplest test case
2. **Run the Test**: Verify it fails (Red phase)
3. **Write Implementation**: Minimal code to pass the test
4. **Run the Test**: Verify it passes (Green phase)
5. **Refactor**: Improve code while keeping tests green
6. **Repeat**: Add more test cases and functionality

### Learning Objectives

By completing this project, students will:

- **Understand TDD Principles**: Learn the Red-Green-Refactor cycle
- **Write Effective Tests**: Create comprehensive test suites
- **Implement Data Structures**: Build a working Bubble Sort algorithm
- **Handle Edge Cases**: Test boundary conditions and error scenarios
- **Refactor Code**: Improve implementation while maintaining functionality
- **Use JUnit 5**: Master modern testing framework features

## Conclusion

This TDD project provides students with hands-on experience in:

1. **Test-First Development**: Learning to think about requirements through tests
2. **Data Structure Implementation**: Understanding sorting algorithms through testing
3. **Quality Assurance**: Building confidence through comprehensive test coverage
4. **Professional Practices**: Adopting industry-standard development methodologies

The project demonstrates that TDD is not just a testing technique, but a design methodology that leads to:
- Better code quality
- Clearer requirements understanding
- More maintainable software
- Increased confidence in code changes

Students will learn that writing tests first helps them understand what they're building before they build it, leading to better implementations and deeper understanding of data structure concepts.

## Building and Running

### Prerequisites

- **Java 11** or higher
- **Maven 3.6** or higher

### Maven Commands

#### 1. Clean and Compile
```bash
mvn clean compile
```
- Removes previous build artifacts (`target/` directory)
- Compiles source code to `target/classes/`
- Compiles test code to `target/test-classes/`

#### 2. Run Tests Only
```bash
mvn test
```
- Compiles and runs all JUnit 5 tests
- Shows test results and coverage
- **Expected Output**: 20 tests passing (5 nested test classes)

#### 3. Clean, Compile, and Test (All-in-One)
```bash
mvn clean test
```
- **Recommended command** for development workflow
- Ensures clean build and runs all tests
- **Expected Output**: BUILD SUCCESS with 20 tests passing

#### 4. Run the Demo Application
```bash
mvn exec:java
```
- Compiles and runs the `Main` class
- Demonstrates TDD Bubble Sort with various test cases
- Shows before/after arrays for different scenarios

#### 5. Clean, Build, and Run Demo (Complete Workflow)
```bash
mvn clean compile test exec:java
```
- Performs complete build, test, and demo execution
- **Recommended for first-time setup**

### Expected Test Results

When you run `mvn test`, you should see output like:
```
[INFO] Running com.acu.datastructure.bubblesort.BubbleSortTest
[INFO] Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Demo Output

When you run `mvn exec:java`, you'll see:
```
=== Test-Driven Development: Bubble Sort Demo ===
This implementation was created following TDD principles:
1. Tests were written FIRST (Red phase)
2. Implementation was written to make tests pass (Green phase)
3. Code was refactored while keeping tests green (Refactor phase)

Test Case 1: Normal unsorted array
Original: [64, 34, 25, 12, 22, 11, 90]
Sorted:   [11, 12, 22, 25, 34, 64, 90]
Is sorted: true

Test Case 2: Already sorted array
Original: [1, 2, 3, 4, 5]
Sorted:   [1, 2, 3, 4, 5]
Is sorted: true

Test Case 3: Array with duplicates
Original: [3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5]
Sorted:   [1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9]
Is sorted: true

Test Case 4: Descending sort
Original: [5, 2, 8, 1, 9]
Descending: [9, 8, 5, 2, 1]

Test Case 5: Edge cases
Empty array: []
Sorted: []

Single element: [42]
Sorted: [42]

Negative numbers: [-3, -1, -4, -1, -5]
Sorted: [-5, -4, -3, -1, -1]

=== TDD Benefits Demonstrated ===
✓ All edge cases are handled
✓ Code is well-tested and reliable
✓ Implementation follows clear requirements
✓ Refactoring is safe with comprehensive tests

Run 'mvn test' to execute all TDD tests!
```

### Troubleshooting

#### Common Issues:

1. **Java Version Error**: Ensure you have Java 11+ installed
   ```bash
   java -version
   ```

2. **Maven Not Found**: Install Maven or use Maven wrapper
   ```bash
   mvn -version
   ```

3. **Compilation Errors**: Clean and rebuild
   ```bash
   mvn clean compile
   ```

4. **Test Failures**: Check that all implementation methods are complete
   ```bash
   mvn test
   ```

## Getting Started

1. **Clone the project**
2. **Run the complete workflow**: `mvn clean compile test exec:java`
3. **Write your first test** (it should fail)
4. **Implement the minimum code** to make it pass
5. **Refactor and improve** your implementation
6. **Add more test cases** and repeat the cycle

Remember: **Red → Green → Refactor** - this is the heart of Test-Driven Development!

---

*"The act of writing a unit test is more an act of design than of verification. It is also more an act of documentation than of verification. The act of writing a unit test closes a remarkable number of feedback loops, the least of which is the one pertaining to verification of function."* - Robert C. Martin
