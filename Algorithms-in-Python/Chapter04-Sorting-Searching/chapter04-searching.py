# Python Searching Algorithms Tutorial for Beginners

# --- Linear Search ---
# Linear search checks each element in the list one by one until it finds the target value.

def linear_search(search_list, target):
    """Return the index of target in search_list, or -1 if not found."""
    for index, value in enumerate(search_list):
        if value == target:
            return index  # Found the target, return its index
    return -1  # Target not found

# Example usage of linear search
numbers = [4, 2, 7, 1, 9, 3]
target_number = 7
result = linear_search(numbers, target_number)
if result != -1:
    print(f"Linear Search: Found {target_number} at index {result}.")
else:
    print(f"Linear Search: {target_number} not found in the list.")

# --- Binary Search ---
# Binary search works only on sorted lists.
# It repeatedly divides the search interval in half.

def binary_search(sorted_list, target):
    """Return the index of target in sorted_list, or -1 if not found."""
    left = 0
    right = len(sorted_list) - 1
    while left <= right:
        mid = (left + right) // 2  # Find the middle index
        if sorted_list[mid] == target:
            return mid  # Found the target
        elif sorted_list[mid] < target:
            left = mid + 1  # Search the right half
        else:
            right = mid - 1  # Search the left half
    return -1  # Target not found

# Example usage of binary search
sorted_numbers = [1, 2, 3, 4, 7, 9]
target_number = 7
result = binary_search(sorted_numbers, target_number)
if result != -1:
    print(f"Binary Search: Found {target_number} at index {result}.")
else:
    print(f"Binary Search: {target_number} not found in the list.")

# Summary:
# - Linear search works on any list, but is slower for large lists.
# - Binary search is much faster, but only works on sorted lists.

