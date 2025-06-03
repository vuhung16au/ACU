# Chapter 8: Greedy Strategy

## Introduction
This chapter introduces greedy algorithms, which make the best choice at each step in hopes of finding the global optimum. You will learn how greedy strategies work through classic examples like activity selection and coin change.

## Algorithms Implemented

### 1. Activity Selection Problem (Greedy)
Selects the maximum number of non-overlapping activities based on their start and end times.
```python
def select_max_activities(activities):
    sorted_activities = sorted(activities, key=lambda x: x[1])
    selected = []
    last_end_time = 0
    for activity in sorted_activities:
        start, end = activity
        if start >= last_end_time:
            selected.append(activity)
            last_end_time = end
    return selected
```
**Step-by-step:**
1. Sort activities by end time.
2. Go through each activity.
3. If it starts after the last selected activity ends, select it.

### 2. Coin Change Problem (Greedy)
Finds the minimum number of coins needed for a given amount using the largest denominations first.
```python
def min_coins_greedy(coins, amount):
    coins.sort(reverse=True)
    count = 0
    remaining = amount
    for coin in coins:
        while remaining >= coin:
            remaining -= coin
            count += 1
    if remaining != 0:
        return -1
    return count
```
**Step-by-step:**
1. Sort coins from largest to smallest.
2. Use as many of the largest coin as possible.
3. Move to the next largest coin.
4. Repeat until the amount is reached or no coins fit.

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Analysis
- **Activity Selection:** $O(n \log n)$ (for sorting), $O(n)$ for selection
- **Coin Change (greedy):** $O(n)$ (where $n$ is the number of coin types)

#### Proof & Cases
- **Activity Selection:** Sorting dominates, so $O(n \log n)$
- **Coin Change:** Each coin type is checked at most once per amount

## Important Notes
- Greedy algorithms do not always give the optimal solution for every problem.
- For standard coin denominations, greedy works; for others, it may not.
- Greedy is fast and simple, but not always correct for all cases.

## Real-World Applications
- Scheduling (meetings, tasks)
- Making change in vending machines
- Network routing (shortest path in some cases)

## Ideas for Self-Practicing
- Try the greedy approach on coin sets where it fails (e.g., coins = [1, 3, 4], amount = 6).
- Write a greedy algorithm for the fractional knapsack problem.
- Modify activity selection to return the actual activities, not just the count.

## Further Readings & Connections
- [GeeksforGeeks: Greedy Algorithms](https://www.geeksforgeeks.org/greedy-algorithms/)
- Learn about dynamic programming (see Chapter 9) for problems where greedy fails.

---
**Key Terms:**
- **Greedy Algorithm:** Makes the best choice at each step.
- **Optimal Solution:** The best possible answer to a problem.
- **Local Optimum:** The best choice at a single step. 