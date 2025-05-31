# Chapter 4: Simple Sorting and Searching Algorithms
# This tutorial introduces basic sorting and searching algorithms for beginners.

# 1. Linear Search
# Linear search checks each element in the list until it finds the target value.
def linear_search(numbers, target):
    """Return the index of target in numbers, or -1 if not found."""
    for index, value in enumerate(numbers):
        if value == target:
            return index  # Found target
    return -1  # Not found

numbers_list = [5, 2, 9, 1, 7]
search_result = linear_search(numbers_list, 9)
if search_result != -1:
    print(f"Linear Search: 9 found at index {search_result}.")
else:
    print("Linear Search: 9 not found.")

# 2. Binary Search
# Binary search works on sorted lists. It repeatedly divides the list in half to find the target.
def binary_search(sorted_numbers, target):
    """Return the index of target in sorted_numbers, or -1 if not found."""
    left = 0
    right = len(sorted_numbers) - 1
    while left <= right:
        mid = (left + right) // 2
        if sorted_numbers[mid] == target:
            return mid
        elif sorted_numbers[mid] < target:
            left = mid + 1
        else:
            right = mid - 1
    return -1

sorted_list = [1, 2, 5, 7, 9]
binary_result = binary_search(sorted_list, 7)
if binary_result != -1:
    print(f"Binary Search: 7 found at index {binary_result}.")
else:
    print("Binary Search: 7 not found.")

# 3. Bubble Sort
# Bubble sort repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order.
def bubble_sort(numbers):
    """Sort the list using bubble sort and return the sorted list."""
    n = len(numbers)
    for i in range(n):
        for j in range(0, n - i - 1):
            if numbers[j] > numbers[j + 1]:
                # Swap if the element found is greater than the next element
                numbers[j], numbers[j + 1] = numbers[j + 1], numbers[j]
    return numbers

unsorted_list = [64, 34, 25, 12, 22, 11, 90]
print("Bubble Sort: Before sorting:", unsorted_list)
sorted_bubble = bubble_sort(unsorted_list.copy())
print("Bubble Sort: After sorting:", sorted_bubble)

# 4. Selection Sort
# Selection sort repeatedly finds the minimum element and moves it to the beginning.
def selection_sort(numbers):
    """Sort the list using selection sort and return the sorted list."""
    n = len(numbers)
    for i in range(n):
        min_index = i
        for j in range(i + 1, n):
            if numbers[j] < numbers[min_index]:
                min_index = j
        numbers[i], numbers[min_index] = numbers[min_index], numbers[i]
    return numbers

unsorted_list2 = [29, 10, 14, 37, 13]
print("Selection Sort: Before sorting:", unsorted_list2)
sorted_selection = selection_sort(unsorted_list2.copy())
print("Selection Sort: After sorting:", sorted_selection)

# 5. Insertion Sort
# Insertion sort builds the sorted list one item at a time.
def insertion_sort(numbers):
    """Sort the list using insertion sort and return the sorted list."""
    for i in range(1, len(numbers)):
        key = numbers[i]
        j = i - 1
        while j >= 0 and numbers[j] > key:
            numbers[j + 1] = numbers[j]
            j -= 1
        numbers[j + 1] = key
    return numbers

unsorted_list3 = [12, 11, 13, 5, 6]
print("Insertion Sort: Before sorting:", unsorted_list3)
sorted_insertion = insertion_sort(unsorted_list3.copy())
print("Insertion Sort: After sorting:", sorted_insertion)

# End of Chapter 4: Sorting and Searching Algorithms
