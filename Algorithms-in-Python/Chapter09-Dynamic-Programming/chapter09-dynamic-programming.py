## Chapter 9: Dynamic Programming
## This tutorial introduces dynamic programming for beginners with simple, well-commented examples.

## 1. What is Dynamic Programming?
# Dynamic programming (DP) is a technique for solving problems by breaking them into smaller subproblems,
# solving each subproblem just once, and storing their solutions (usually in a table) to avoid redundant work.

## 2. Example: Fibonacci Sequence (with DP)
## The naive recursive approach to Fibonacci is slow because it recomputes the same values many times.
## DP solves this by storing results in a list (bottom-up approach).

def fibonacci_dp(n):
    """Return the nth Fibonacci number using dynamic programming (bottom-up)."""
    if n == 0:
        return 0
    if n == 1:
        return 1
    fib_table = [0, 1]  # Store base cases
    for i in range(2, n + 1):
        next_fib = fib_table[i - 1] + fib_table[i - 2]
        fib_table.append(next_fib)
    return fib_table[n]

print("Fibonacci number at position 10:", fibonacci_dp(10))

## 3. Example: Coin Change Problem (Minimum Coins)
## Given coins of certain denominations and a total amount, find the minimum number of coins needed to make that amount.
## DP builds up the answer for all amounts from 1 to the target amount.

def min_coins_dp(coins, amount):
    """Return the minimum number of coins needed to make the amount using DP."""
    max_value = amount + 1  # A value greater than any possible answer
    dp = [max_value] * (amount + 1)
    dp[0] = 0  # Base case: 0 coins to make amount 0
    for coin in coins:
        for x in range(coin, amount + 1):
            if dp[x - coin] + 1 < dp[x]:
                dp[x] = dp[x - coin] + 1
    return dp[amount] if dp[amount] != max_value else -1

coin_denominations = [1, 3, 4]
target_amount = 6
print(f"Minimum coins needed for {target_amount}:", min_coins_dp(coin_denominations, target_amount))

## 4. Key Points About Dynamic Programming
## - DP is useful for problems with overlapping subproblems and optimal substructure.
## - Store solutions to subproblems to avoid redundant work.
## - Common applications: Fibonacci, coin change, knapsack, edit distance, etc.

## End of Chapter 9: Dynamic Programming tutorial
