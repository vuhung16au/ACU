# Chapter 9: Dynamic Programming

## Introduction
This chapter introduces dynamic programming (DP), a technique for solving problems by breaking them into smaller subproblems, solving each just once, and storing their solutions. You will learn how DP works through classic examples like Fibonacci and coin change.

## Algorithms Implemented

### 1. Fibonacci Sequence (Dynamic Programming)
Calculates the nth Fibonacci number using a bottom-up approach to avoid redundant work.
```python
def fibonacci_dp(n):
    if n == 0:
        return 0
    if n == 1:
        return 1
    fib_table = [0, 1]
    for i in range(2, n + 1):
        next_fib = fib_table[i - 1] + fib_table[i - 2]
        fib_table.append(next_fib)
    return fib_table[n]
```
**Step-by-step:**
1. Start with base cases (0 and 1).
2. Build up the table by adding the two previous numbers.
3. Return the nth Fibonacci number.

### 2. Coin Change Problem (Dynamic Programming)
Finds the minimum number of coins needed for a given amount using DP.
```python
def min_coins_dp(coins, amount):
    max_value = amount + 1
    dp = [max_value] * (amount + 1)
    dp[0] = 0
    for coin in coins:
        for x in range(coin, amount + 1):
            if dp[x - coin] + 1 < dp[x]:
                dp[x] = dp[x - coin] + 1
    return dp[amount] if dp[amount] != max_value else -1
```
**Step-by-step:**
1. Initialize a table with a value greater than any possible answer.
2. Set the base case (0 coins for amount 0).
3. For each coin, update the table for all amounts it can make.
4. Return the minimum coins needed for the target amount.

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Analysis
- **Fibonacci (DP):** $O(n)$ time, $O(n)$ space
- **Coin Change (DP):** $O(nm)$ time, $O(n)$ space ($n$ = amount, $m$ = number of coin types)

#### Proof & Cases
- **Fibonacci:** Each number is calculated once.
- **Coin Change:** Each amount is updated for each coin.

## Important Notes
- DP is powerful for problems with overlapping subproblems and optimal substructure.
- It is more efficient than naive recursion for many problems.
- Storing solutions (memoization or tabulation) avoids redundant work.

## Real-World Applications
- Shortest path algorithms (like Dijkstra's)
- Resource allocation and scheduling
- Text comparison (edit distance)

## Ideas for Self-Practicing
- Write a DP solution for the climbing stairs problem.
- Modify the coin change code to return the actual coins used.
- Try DP for the knapsack problem.

## Further Readings & Connections
- [GeeksforGeeks: Dynamic Programming](https://www.geeksforgeeks.org/dynamic-programming/)
- Learn about greedy algorithms (see Chapter 8) and recursion (see Chapter 5).

---
**Key Terms:**
- **Dynamic Programming:** Solving problems by storing solutions to subproblems.
- **Memoization:** Storing results of expensive function calls.
- **Tabulation:** Building up solutions from the bottom up. 