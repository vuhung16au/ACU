/**
 * Advanced.java
 * 
 * This program demonstrates advanced in Java:
 * - Core concepts and principles
 * - Implementation techniques
 * - Best practices and patterns
 * - Practical examples and usage
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.util.Arrays;

public class Advanced {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced Array Algorithms Example ===\n");
        
        // Sample data
        int[] data = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        System.out.println("Original data: " + Arrays.toString(data));
        
        // Demonstrate advanced sorting
        demonstrateAdvancedSorting(data.clone());
        
        // Demonstrate advanced searching
        demonstrateAdvancedSearching(data.clone());
        
        // Demonstrate array analysis
        demonstrateArrayAnalysis(data.clone());
        
        // Demonstrate memory-efficient algorithms
        demonstrateMemoryEfficientAlgorithms();
    }
    
    /**
     * Demonstrates advanced sorting algorithms
     */
    public static void demonstrateAdvancedSorting(int[] arr) {
        System.out.println("\n=== Advanced Sorting ===");
        
        // Merge Sort
        int[] mergeArray = arr.clone();
        System.out.println("Merge Sort:");
        System.out.println("Before: " + Arrays.toString(mergeArray));
        mergeSort(mergeArray, 0, mergeArray.length - 1);
        System.out.println("After:  " + Arrays.toString(mergeArray));
        
        // Heap Sort
        int[] heapArray = arr.clone();
        System.out.println("\nHeap Sort:");
        System.out.println("Before: " + Arrays.toString(heapArray));
        heapSort(heapArray);
        System.out.println("After:  " + Arrays.toString(heapArray));
        
        // Counting Sort (for small range integers)
        int[] countingArray = {4, 2, 1, 4, 1, 0, 3, 2, 4, 1};
        System.out.println("\nCounting Sort:");
        System.out.println("Before: " + Arrays.toString(countingArray));
        countingSort(countingArray, 5); // Range 0-4
        System.out.println("After:  " + Arrays.toString(countingArray));
    }
    
    /**
     * Demonstrates advanced searching algorithms
     */
    public static void demonstrateAdvancedSearching(int[] arr) {
        System.out.println("\n=== Advanced Searching ===");
        
        // Sort array first
        Arrays.sort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
        
        // Find first and last occurrence
        int target = 5;
        int first = findFirstOccurrence(arr, target);
        int last = findLastOccurrence(arr, target);
        System.out.println("First occurrence of " + target + ": " + first);
        System.out.println("Last occurrence of " + target + ": " + last);
        
        // Find k-th smallest element
        int k = 3;
        int kthSmallest = findKthSmallest(arr, k);
        System.out.println(k + "-th smallest element: " + kthSmallest);
        
        // Find missing number in sequence
        int[] sequence = {1, 2, 4, 5, 6, 7, 8, 9};
        int missing = findMissingNumber(sequence);
        System.out.println("Missing number in sequence: " + missing);
    }
    
    /**
     * Demonstrates array analysis techniques
     */
    public static void demonstrateArrayAnalysis(int[] arr) {
        System.out.println("\n=== Array Analysis ===");
        
        // Find subarray with maximum sum
        int[] maxSubarray = findMaxSubarray(arr);
        System.out.println("Maximum subarray: " + Arrays.toString(maxSubarray));
        
        // Find longest increasing subsequence
        int[] lis = findLongestIncreasingSubsequence(arr);
        System.out.println("Longest increasing subsequence: " + Arrays.toString(lis));
        
        // Find majority element
        int majority = findMajorityElement(arr);
        System.out.println("Majority element: " + majority);
        
        // Find equilibrium index
        int[] equilibriumArray = {1, 2, 3, 4, 6};
        int equilibrium = findEquilibriumIndex(equilibriumArray);
        System.out.println("Equilibrium index: " + equilibrium);
    }
    
    /**
     * Demonstrates memory-efficient algorithms
     */
    public static void demonstrateMemoryEfficientAlgorithms() {
        System.out.println("\n=== Memory Efficient Algorithms ===");
        
        // In-place array reversal
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("Original: " + Arrays.toString(arr));
        reverseInPlace(arr);
        System.out.println("Reversed: " + Arrays.toString(arr));
        
        // In-place array rotation
        int[] rotateArray = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("Original: " + Arrays.toString(rotateArray));
        rotateInPlace(rotateArray, 3);
        System.out.println("Rotated by 3: " + Arrays.toString(rotateArray));
    }
    
    // ========== ADVANCED SORTING ALGORITHMS ==========
    
    /**
     * Merge Sort - O(n log n) time complexity
     */
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
    
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        
        for (int i = 0; i < n1; i++) {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = arr[mid + 1 + j];
        }
        
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }
        
        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }
    
    /**
     * Heap Sort - O(n log n) time complexity
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;
        
        // Build heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        
        // Extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }
    
    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        
        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            heapify(arr, n, largest);
        }
    }
    
    /**
     * Counting Sort - O(n + k) time complexity
     */
    public static void countingSort(int[] arr, int range) {
        int[] count = new int[range];
        int[] output = new int[arr.length];
        
        // Count occurrences
        for (int num : arr) {
            count[num]++;
        }
        
        // Modify count array
        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output array
        for (int i = arr.length - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }
        
        // Copy back to original array
        for (int i = 0; i < arr.length; i++) {
            arr[i] = output[i];
        }
    }
    
    // ========== ADVANCED SEARCHING ALGORITHMS ==========
    
    /**
     * Find first occurrence of target in sorted array
     */
    public static int findFirstOccurrence(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                result = mid;
                right = mid - 1; // Continue searching left
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }
    
    /**
     * Find last occurrence of target in sorted array
     */
    public static int findLastOccurrence(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                result = mid;
                left = mid + 1; // Continue searching right
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }
    
    /**
     * Find k-th smallest element
     */
    public static int findKthSmallest(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Invalid k value");
        }
        return quickSelect(arr, 0, arr.length - 1, k - 1);
    }
    
    private static int quickSelect(int[] arr, int left, int right, int k) {
        if (left == right) {
            return arr[left];
        }
        
        int pivotIndex = partition(arr, left, right);
        
        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return quickSelect(arr, left, pivotIndex - 1, k);
        } else {
            return quickSelect(arr, pivotIndex + 1, right, k);
        }
    }
    
    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left - 1;
        
        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        int temp = arr[i + 1];
        arr[i + 1] = arr[right];
        arr[right] = temp;
        
        return i + 1;
    }
    
    /**
     * Find missing number in sequence
     */
    public static int findMissingNumber(int[] arr) {
        int n = arr.length + 1;
        int expectedSum = n * (n + 1) / 2;
        int actualSum = 0;
        
        for (int num : arr) {
            actualSum += num;
        }
        
        return expectedSum - actualSum;
    }
    
    // ========== ARRAY ANALYSIS ==========
    
    /**
     * Find subarray with maximum sum (Kadane's algorithm)
     */
    public static int[] findMaxSubarray(int[] arr) {
        int maxSoFar = arr[0];
        int maxEndingHere = arr[0];
        int start = 0, end = 0;
        int tempStart = 0;
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > maxEndingHere + arr[i]) {
                maxEndingHere = arr[i];
                tempStart = i;
            } else {
                maxEndingHere = maxEndingHere + arr[i];
            }
            
            if (maxEndingHere > maxSoFar) {
                maxSoFar = maxEndingHere;
                start = tempStart;
                end = i;
            }
        }
        
        int[] result = new int[end - start + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = arr[start + i];
        }
        return result;
    }
    
    /**
     * Find longest increasing subsequence
     */
    public static int[] findLongestIncreasingSubsequence(int[] arr) {
        int n = arr.length;
        int[] lis = new int[n];
        int[] prev = new int[n];
        
        for (int i = 0; i < n; i++) {
            lis[i] = 1;
            prev[i] = -1;
        }
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j] && lis[i] < lis[j] + 1) {
                    lis[i] = lis[j] + 1;
                    prev[i] = j;
                }
            }
        }
        
        int maxIndex = 0;
        for (int i = 1; i < n; i++) {
            if (lis[i] > lis[maxIndex]) {
                maxIndex = i;
            }
        }
        
        int[] result = new int[lis[maxIndex]];
        int index = lis[maxIndex] - 1;
        int current = maxIndex;
        
        while (current != -1) {
            result[index--] = arr[current];
            current = prev[current];
        }
        
        return result;
    }
    
    /**
     * Find majority element (Boyer-Moore algorithm)
     */
    public static int findMajorityElement(int[] arr) {
        int count = 1;
        int majority = arr[0];
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == majority) {
                count++;
            } else {
                count--;
                if (count == 0) {
                    majority = arr[i];
                    count = 1;
                }
            }
        }
        
        return majority;
    }
    
    /**
     * Find equilibrium index
     */
    public static int findEquilibriumIndex(int[] arr) {
        int totalSum = 0;
        for (int num : arr) {
            totalSum += num;
        }
        
        int leftSum = 0;
        for (int i = 0; i < arr.length; i++) {
            totalSum -= arr[i];
            if (leftSum == totalSum) {
                return i;
            }
            leftSum += arr[i];
        }
        
        return -1;
    }
    
    // ========== MEMORY EFFICIENT ALGORITHMS ==========
    
    /**
     * Reverse array in-place
     */
    public static void reverseInPlace(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
    
    /**
     * Rotate array in-place
     */
    public static void rotateInPlace(int[] arr, int k) {
        int n = arr.length;
        k = k % n;
        
        reverseInPlace(arr);
        reverseInPlace(arr, 0, k - 1);
        reverseInPlace(arr, k, n - 1);
    }
    
    private static void reverseInPlace(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
} 