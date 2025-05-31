# Chapter 6: Divide and Conquer
# This tutorial introduces the divide and conquer paradigm for beginners with simple, well-commented examples.

# 1. What is Divide and Conquer?
# Divide and conquer is a problem-solving technique that breaks a problem into smaller subproblems, solves each subproblem, and then combines the results.

# 2. Example: Merge Sort
# Merge sort is a classic divide and conquer algorithm for sorting a list.

def merge_sort(numbers):
    """Sort a list using merge sort algorithm."""
    if len(numbers) <= 1:
        return numbers  # Base case: a list of 0 or 1 element is already sorted
    # Divide the list into two halves
    mid = len(numbers) // 2
    left_half = merge_sort(numbers[:mid])
    right_half = merge_sort(numbers[mid:])
    # Conquer: merge the sorted halves
    return merge(left_half, right_half)

def merge(left, right):
    """Merge two sorted lists into one sorted list."""
    merged = []
    left_index = 0
    right_index = 0
    # Compare elements from both lists and add the smallest to merged
    while left_index < len(left) and right_index < len(right):
        if left[left_index] < right[right_index]:
            merged.append(left[left_index])
            left_index += 1
        else:
            merged.append(right[right_index])
            right_index += 1
    # Add any remaining elements
    merged.extend(left[left_index:])
    merged.extend(right[right_index:])
    return merged

unsorted_list = [38, 27, 43, 3, 9, 82, 10]
print("Merge Sort: Before sorting:", unsorted_list)
sorted_list = merge_sort(unsorted_list)
print("Merge Sort: After sorting:", sorted_list)

# 3. Example: Quick Sort
# Quick sort is another divide and conquer algorithm for sorting a list.

def quick_sort(numbers):
    """Sort a list using quick sort algorithm."""
    if len(numbers) <= 1:
        return numbers  # Base case
    pivot = numbers[0]  # Choose the first element as pivot
    less_than_pivot = [x for x in numbers[1:] if x <= pivot]
    greater_than_pivot = [x for x in numbers[1:] if x > pivot]
    # Recursively sort the sublists and combine
    return quick_sort(less_than_pivot) + [pivot] + quick_sort(greater_than_pivot)

unsorted_list2 = [12, 7, 14, 9, 10, 11]
print("Quick Sort: Before sorting:", unsorted_list2)
sorted_list2 = quick_sort(unsorted_list2)
print("Quick Sort: After sorting:", sorted_list2)

# 4. Key Points About Divide and Conquer
# - Divide: Break the problem into smaller subproblems.
# - Conquer: Solve each subproblem recursively.
# - Combine: Merge the solutions of subproblems to get the final answer.
# - Commonly used in sorting, searching, and many other algorithms.

# End of Chapter 6: Divide and Conquer tutorial
