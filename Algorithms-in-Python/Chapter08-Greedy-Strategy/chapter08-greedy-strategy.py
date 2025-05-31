# Chapter 8: Greedy Strategy
# This tutorial introduces greedy algorithms for beginners with simple, well-commented examples.

# 1. What is a Greedy Algorithm?
# A greedy algorithm makes the best choice at each step, hoping to find the global optimum.

# 2. Example: Activity Selection Problem
# Given activities with start and end times, select the maximum number of non-overlapping activities.

def select_max_activities(activities):
    """Return the maximum set of non-overlapping activities using a greedy approach."""
    # Sort activities by their end time
    sorted_activities = sorted(activities, key=lambda x: x[1])
    selected = []
    last_end_time = 0
    for activity in sorted_activities:
        start, end = activity
        if start >= last_end_time:
            selected.append(activity)
            last_end_time = end
    return selected

# Each activity is represented as (start_time, end_time)
activities_list = [(1, 3), (2, 5), (4, 7), (1, 8), (5, 9), (8, 10)]
selected_activities = select_max_activities(activities_list)
print("Selected activities (start, end):", selected_activities)

# 3. Example: Coin Change Problem
# Given coins of different denominations and a total amount, find the minimum number of coins needed.
# The greedy approach works when coin denominations are standard (like 1, 5, 10, 25).

def min_coins_greedy(coins, amount):
    """Return the minimum number of coins needed for the amount using a greedy approach."""
    coins.sort(reverse=True)  # Use largest coins first
    count = 0
    remaining = amount
    for coin in coins:
        while remaining >= coin:
            remaining -= coin
            count += 1
            print(f"Used coin: {coin}, Remaining amount: {remaining}")
    if remaining != 0:
        print("Cannot make exact change with given coins.")
        return -1
    return count

coin_denominations = [25, 10, 5, 1]
total_amount = 63
print(f"Minimum coins needed for {total_amount}:", min_coins_greedy(coin_denominations, total_amount))

# 4. Key Points About Greedy Algorithms
# - Greedy algorithms make the locally optimal choice at each step.
# - They do not always produce the global optimum for every problem, but work well for many classic problems.
# - Examples: activity selection, coin change (with standard denominations), interval scheduling, etc.

# End of Chapter 8: Greedy Strategy tutorial
