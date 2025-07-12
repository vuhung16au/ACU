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
*   **Best-case input:** Results in the shortest execution time.
*   **Worst-case input:** Results in the longest execution time. This is often preferred for analysis as it guarantees the algorithm will never be slower than this.
*   **Average-case analysis:** Attempts to determine the average time across all possible inputs of the same size, but is often difficult to perform.

**Analyzing Time Complexity in Code Structures:**
*   **Simple Loops:** A single loop iterating `n` times has a time complexity of **O(n)**.
*   **Nested Loops:**
    *   If outer loop is `n` and inner loop is `m`, it's **O(nm)**.
    *   If inner loop depends on outer loop (e.g., `i` times for `i=1 to n`), it's **O(n^2)**.
    *   If inner loop is a constant number of times (e.g., 20 times), it remains **O(n)**.
*   **Sequence:** Operations executed sequentially, where one's time complexity is greater than others, the overall complexity is determined by the largest one. For example, O(10) followed by O(n) results in **O(n)**.
*   **Selection (if/else):** The time complexity is determined by the test time plus the worst-case branch, e.g., an `if` condition taking O(n) and a loop in `else` taking O(n) results in **O(n)**.
*   **Constant Time (O(1)):** Operations whose time is not related to the input size, such as retrieving an element at a given array index.

**Algorithm Complexity Examples:**
*   **Linear Search:** **O(n)** in the worst case.
*   **Binary Search:** **O(logn)**, which is a logarithmic algorithm that grows slowly. Doubling input size only doubles the time.
*   **Selection Sort:** **O(n^2)**, a quadratic algorithm that grows quickly. Doubling input size quadruples the time.
*   **Recursive Fibonacci Numbers:** Exhibits **O(2^n)** time complexity, making it highly inefficient due to redundant computations of subproblems.
*   **Non-recursive Fibonacci Numbers (Dynamic Programming):** Achieves **O(n)** complexity, a significant improvement by solving each subproblem only once and storing results.
*   **Euclid's Algorithm for GCD:** Has a time complexity of **O(logn)**.
*   **Merge Sort:** Divides the array recursively and then merges sorted subarrays. Its time complexity is **O(nlogn)**.
*   **Quick Sort:** Selects a pivot to partition an array into two sub-arrays and recursively sorts them. In the worst-case, it is **O(n^2)**, but in the best and average cases, it's **O(nlogn)**.
*   **Heap Sort:** Utilizes a heap data structure (a complete binary tree where each node is greater than or equal to its children). The height of a heap with `n` elements is **O(logn)**. Although not explicitly stated as O(nlogn) in this lecture, it is implied by heap operations and general understanding of efficient sorting.
*   **Bucket Sort and Radix Sort:** These are specialized sorting algorithms that can perform better than O(nlogn) (the lower bound for comparison-based sorts) if keys are small integers, potentially reaching **O(n)**.

**Algorithm Design Paradigms:**
*   **Dynamic Programming:** Solves problems by breaking them into overlapping subproblems, solving each subproblem only once, and storing their results to avoid redundant computation. The non-recursive Fibonacci algorithm is a prime example.
*   **Divide-and-Conquer:** Divides a problem into non-overlapping subproblems, solves them recursively, and then combines their solutions. Many recursive problems follow this approach.
*   **Backtracking:** An incremental search approach that abandons a candidate solution as soon as it's determined to be invalid, then explores a new one.

**Recursion vs. Iteration:**
While recursion can be an alternative to loops, it often incurs **substantial overhead** as the system must allocate space for local variables and parameters with each recursive call, consuming memory and requiring extra time for management. However, recursion is beneficial for problems that are inherently recursive.

**Comparing Common Growth Functions:**
The lecture presents a hierarchy of common Big O complexities from most efficient to least efficient:
*   **O(1)** (Constant time)
*   **O(logn)** (Logarithmic time)
*   **O(n)** (Linear time)
*   **O(nlogn)** (Log-linear time)
*   **O(n^2)** (Quadratic time)
*   **O(n^3)** (Cubic time)
*   **O(2^n)** (Exponential time)

Sample code 

- http://www.cs.armstrong.edu/liang/animation/web/LinearSearch.htm -> dead link 
- http://www.cs.armstrong.edu/liang/animation/web/BinarySearch.html -> dead link
- http://www.cs.armstrong.edu/liang/animation/web/ClosestPair.html -> dead link


- https://liveexample.pearsoncmg.com/html/PerformanceTest.html
- https://liveexample.pearsoncmg.com/dsanimation/SelectionSortNew.html
- https://liveexample.pearsoncmg.com/html/ComputeFibonacci.html
- https://liveexample.pearsoncmg.com/html/ImprovedFibonacci.html
- https://liveexample.pearsoncmg.com/html/PrimeNumber.html
- https://liveexample.pearsoncmg.com/html/PrimeNumbers.html
- https://liveexample.pearsoncmg.com/html/EfficientPrimeNumbers.html
- https://liveexample.pearsoncmg.com/html/SieveOfEratosthenes.html
- https://liveexample.pearsoncmg.com/html/EightQueens.html