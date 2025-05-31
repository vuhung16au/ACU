## Python Sorting Algorithms Tutorial for Beginners

## Bubble Sort
# Bubble sort repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order.
def bubble_sort(arr):
    n = len(arr)
    for i in range(n):
        for j in range(0, n - i - 1):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]
    return arr

## Selection Sort
# Selection sort repeatedly finds the minimum element and moves it to the sorted part of the list.
def selection_sort(arr):
    n = len(arr)
    for i in range(n):
        min_idx = i
        for j in range(i + 1, n):
            if arr[j] < arr[min_idx]:
                min_idx = j
        arr[i], arr[min_idx] = arr[min_idx], arr[i]
    return arr

## Insertion Sort
# Insertion sort builds the sorted list one item at a time.
def insertion_sort(arr):
    for i in range(1, len(arr)):
        key = arr[i]
        j = i - 1
        while j >= 0 and arr[j] > key:
            arr[j + 1] = arr[j]
            j -= 1
        arr[j + 1] = key
    return arr

## Quick Sort
# Quick sort is a divide-and-conquer algorithm that partitions the list and sorts the partitions.
def quick_sort(arr):
    if len(arr) <= 1:
        return arr
    pivot = arr[len(arr) // 2]
    left = [x for x in arr if x < pivot]
    middle = [x for x in arr if x == pivot]
    right = [x for x in arr if x > pivot]
    return quick_sort(left) + middle + quick_sort(right)

## Counting Sort
# Counting sort works for non-negative integers and counts the occurrences of each value.
def counting_sort(arr):
    if not arr:
        return arr
    max_val = max(arr)
    count = [0] * (max_val + 1)
    for num in arr:
        count[num] += 1
    sorted_arr = []
    for num, freq in enumerate(count):
        sorted_arr.extend([num] * freq)
    return sorted_arr

## Radix Sort
# Radix sort sorts numbers digit by digit, starting from the least significant digit.
def radix_sort(arr):
    if not arr:
        return arr
    max_num = max(arr)
    exp = 1
    while max_num // exp > 0:
        arr = counting_sort_by_digit(arr, exp)
        exp *= 10
    return arr

def counting_sort_by_digit(arr, exp):
    n = len(arr)
    output = [0] * n
    count = [0] * 10
    for num in arr:
        index = (num // exp) % 10
        count[index] += 1
    for i in range(1, 10):
        count[i] += count[i - 1]
    for i in range(n - 1, -1, -1):
        index = (arr[i] // exp) % 10
        output[count[index] - 1] = arr[i]
        count[index] -= 1
    return output

## Merge Sort
# Merge sort is a divide-and-conquer algorithm that splits the list and merges sorted halves.
def merge_sort(arr):
    if len(arr) <= 1:
        return arr
    mid = len(arr) // 2
    left = merge_sort(arr[:mid])
    right = merge_sort(arr[mid:])
    return merge(left, right)

def merge(left, right):
    result = []
    i = j = 0
    while i < len(left) and j < len(right):
        if left[i] < right[j]:
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1
    result.extend(left[i:])
    result.extend(right[j:])
    return result

# --- Example Usage ---
unsorted_list = [5, 2, 9, 1, 5, 6]
print("Original list:", unsorted_list)
print("Bubble sort:", bubble_sort(unsorted_list.copy()))
print("Selection sort:", selection_sort(unsorted_list.copy()))
print("Insertion sort:", insertion_sort(unsorted_list.copy()))
print("Quick sort:", quick_sort(unsorted_list.copy()))
print("Counting sort:", counting_sort(unsorted_list.copy()))
print("Radix sort:", radix_sort(unsorted_list.copy()))
print("Merge sort:", merge_sort(unsorted_list.copy()))

# Summary:
# - Each sorting algorithm has its own strengths and weaknesses.
# - Try them out and see how they work on different lists!
