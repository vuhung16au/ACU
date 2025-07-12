Lecture 6, titled "**Recursion**", introduces recursion as an alternative problem-solving technique in programming, particularly useful for problems that can be broken down into smaller, self-similar subproblems.

The lecture is motivated by problems like finding files within subdirectories or drawing complex fractal patterns like H-trees, which are intuitively solved using recursion.

The **key objectives** of this lecture are to enable students to:
*   Describe what a **recursive method** is and understand its **benefits**.
*   Develop recursive methods for **recursive mathematical functions**.
*   Explain how **recursive method calls are handled in a call stack**.
*   Solve problems using recursion.
*   Use an **overloaded helper method** to derive a recursive method.
*   Implement a **selection sort** and a **binary search** using recursion.
*   Get the **directory size** using recursion.
*   Solve the **Tower of Hanoi** problem using recursion.
*   Draw **fractals** using recursion.
*   Discover the relationship and difference between **recursion and iteration**.
*   Understand **tail-recursive methods** and their desirability.

**Core Concepts and Examples of Recursion**:

*   **Definition**: Recursion is an **alternative form of program control**, essentially repetition without a loop. A recursive method is one that calls itself.
*   **Characteristics of Recursion**:
    *   **Base Cases**: One or more base cases (the simplest case) are used to **stop the recursion**.
    *   **Reduction**: Every recursive call **reduces the original problem**, bringing it increasingly closer to a base case until it becomes that case.
    *   **Subproblems**: To solve a problem using recursion, you break it into subproblems. If a subproblem resembles the original problem but with a smaller size, you can apply the same recursive approach to solve it.

*   **Examples of Recursive Problem Solving**:
    *   **Factorial**: The factorial function `n! = n * (n-1)!` with `0! = 1` is a classic example. The lecture traces how `factorial(4)` unwinds through multiple recursive calls until it hits the base case `factorial(0)=1`, then calculates results back up the call stack. Each recursive call requires space on the **call stack** for its local variables and parameters.
    *   **Fibonacci Numbers**: The Fibonacci series is defined recursively as `fib(0) = 0`, `fib(1) = 1`, and `fib(index) = fib(index -1) + fib(index -2)` for `index >= 2`. The lecture illustrates tracing `fib(3)` and notes the **exponential time complexity** `O(2^n)` for the direct recursive implementation. A non-recursive (iterative) version significantly improves performance to `O(n)`. This highlights the concept of **dynamic programming**, where subproblems are solved once and results stored to avoid redundant computation.
    *   **Printing a Message `n` times**: A simple example illustrating how a problem can be broken down into printing once and then recursively printing `n-1` times, with `n==0` as the base case.
    *   **Palindrome Check**: Recursion can be used to check if a string is a palindrome by comparing its first and last characters and then recursively checking the substring in between.
    *   **Recursive Helper Methods**: For problems like palindrome checking, helper methods can be used to pass additional parameters (e.g., `low` and `high` indices) to avoid creating new objects (like substrings) in each recursive call, making it more efficient.
    *   **Recursive Selection Sort**: The algorithm works by finding the smallest number, swapping it to the first position, and then recursively sorting the remaining (smaller) list.
    *   **Recursive Binary Search**: This algorithm recursively searches for a key in sorted array by checking the middle element and then narrowing the search to either the left or right half.
    *   **Directory Size**: This problem is noted as difficult to solve without recursion, as calculating a directory's size involves summing the sizes of its files and recursively summing the sizes of its subdirectories.
    *   **Tower of Hanoi**: This classic problem involves moving `n` disks between three towers with specific rules. The solution is inherently recursive, decomposing into three subproblems: moving `n-1` disks from source to auxiliary, moving the `n`-th disk to the destination, and then moving `n-1` disks from auxiliary to destination.
    *   **GCD (Greatest Common Divisor)**: Euclid's algorithm for GCD (`gcd(m, n) = n` if `m % n == 0`, else `gcd(n, m % n)`) is presented as an efficient recursive solution with `O(log n)` time complexity.

**Recursion vs. Iteration and Efficiency**:
*   Recursion has **substantial overhead** because each method call requires the system to assign space for local variables and parameters on the call stack, consuming memory and time.
*   Despite the overhead, recursion is **good for solving problems that are inherently recursive**.
*   A **tail-recursive method** is one where there are no pending operations to be performed after a recursive call returns. This is often desirable because some compilers can optimize tail recursion to be as efficient as iteration, though Java's JVM does not typically perform this optimization.


Objectives

-  To describe what a recursive method is and the benefits of using recursion (§18.1).
-  To develop recursive methods for recursive mathematical functions (§§18.2–18.3).
-  To explain how recursive method calls are handled in a call stack (§§18.2–18.3).
-  To solve problems using recursion (§18.4).
-  To use an overloaded helper method to derive a recursive method (§18.5).
-  To implement a selection sort using recursion (§18.5.1).
-  To implement a binary search using recursion (§18.5.2).
-  To get the directory size using recursion (§18.6).
-  To solve the Tower of Hanoi problem using recursion (§18.7).
-  To draw fractals using recursion (§18.8).
-  To discover the relationship and difference between recursion and iteration (§18.9).
-  To know tail-recursive methods and why they are desirable (§18.10).

Sample Code

- https://liveexample.pearsoncmg.com/html/ComputeFactorial.html
- https://liveexample.pearsoncmg.com/html/ComputeFibonacci.html
- https://liveexample.pearsoncmg.com/html/RecursiveSelectionSort.html
- https://liveexample.pearsoncmg.com/html/RecursiveBinarySearch.html
- https://liveexample.pearsoncmg.com/html/DirectorySize.html
- https://liveexample.pearsoncmg.com/html/TowerOfHanoi.html
- https://liveexample.pearsoncmg.com/html/ComputeFactorial.html
- https://liveexample.pearsoncmg.com/html/ComputeFactorialTailRecursion.html