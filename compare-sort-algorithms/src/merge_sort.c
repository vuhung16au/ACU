#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include <string.h>

/**
 * Merge Sort implementation.
 * A divide-and-conquer algorithm that divides the input array into two halves, 
 * recursively sorts them, and then merges the sorted halves.
 */

// Merge two sorted subarrays
void merge(int arr[], int left[], int left_size, int right[], int right_size) {
    int i = 0, j = 0, k = 0;
    
    // Merge the two sorted arrays into arr
    while (i < left_size && j < right_size) {
        if (left[i] <= right[j]) {
            arr[k++] = left[i++];
        } else {
            arr[k++] = right[j++];
        }
    }
    
    // Copy remaining elements if any
    while (i < left_size) {
        arr[k++] = left[i++];
    }
    
    while (j < right_size) {
        arr[k++] = right[j++];
    }
}

// Main merge sort function
void mergeSort(int arr[], int n) {
    if (n < 2) {
        return; // Base case: array of size 0 or 1 is already sorted
    }
    
    int mid = n / 2;
    
    // Create left and right subarrays
    int* left = (int*)malloc(mid * sizeof(int));
    int* right = (int*)malloc((n - mid) * sizeof(int));
    
    if (left == NULL || right == NULL) {
        printf("Memory allocation failed\n");
        if (left) free(left);
        if (right) free(right);
        exit(1);
    }
    
    // Copy data to subarrays
    for (int i = 0; i < mid; i++) {
        left[i] = arr[i];
    }
    for (int i = mid; i < n; i++) {
        right[i - mid] = arr[i];
    }
    
    // Sort subarrays
    mergeSort(left, mid);
    mergeSort(right, n - mid);
    
    // Merge sorted subarrays
    merge(arr, left, mid, right, n - mid);
    
    // Free memory
    free(left);
    free(right);
}

/**
 * Reads numbers from file and returns as array.
 */
int* readNumbersFromFile(const char* filename, int* size) {
    FILE* file = fopen(filename, "r");
    if (file == NULL) {
        printf("Error: %s not found. Please run generate_data.py first.\n", filename);
        exit(1);
    }
    
    // Count the number of lines in the file
    int count = 0;
    char line[20]; // Assuming numbers won't exceed 20 characters
    
    while (fgets(line, sizeof(line), file)) {
        count++;
    }
    
    // Rewind file to beginning
    rewind(file);
    
    // Allocate memory for the numbers
    int* numbers = (int*)malloc(count * sizeof(int));
    if (numbers == NULL) {
        printf("Error: Memory allocation failed.\n");
        fclose(file);
        exit(1);
    }
    
    // Read numbers from file into array
    int i = 0;
    while (i < count && fgets(line, sizeof(line), file)) {
        numbers[i++] = atoi(line);
    }
    
    fclose(file);
    *size = count;
    return numbers;
}

/**
 * Checks if array is sorted.
 */
bool isSorted(int arr[], int size) {
    for (int i = 0; i < size - 1; i++) {
        if (arr[i] > arr[i + 1]) {
            return false;
        }
    }
    return true;
}

int main(int argc, char* argv[]) {
    printf("C Merge Sort Performance Test\n");
    printf("=============================\n");
    
    // Get input and output filenames from command line arguments
    const char* filename = argc > 1 ? argv[1] : "random_list.txt";
    const char* result_filename = argc > 2 ? argv[2] : "results_c.txt";
    
    printf("Using dataset: %s\n", filename);
    
    // Read data from file
    printf("Reading data from file...\n");
    int data_size;
    int* data = readNumbersFromFile(filename, &data_size);
    printf("Data size: %d integers\n", data_size);
    
    // Create a copy for sorting (to preserve original)
    int* data_copy = (int*)malloc(data_size * sizeof(int));
    if (data_copy == NULL) {
        printf("Error: Memory allocation failed for data copy\n");
        free(data);
        exit(1);
    }
    memcpy(data_copy, data, data_size * sizeof(int));
    
    // Measure sorting time
    printf("Starting Merge Sort...\n");
    clock_t start_time = clock();
    mergeSort(data_copy, data_size);
    clock_t end_time = clock();
    
    double execution_time = ((double)(end_time - start_time)) / CLOCKS_PER_SEC;
    
    // Verify the array is sorted
    bool sorted = isSorted(data_copy, data_size);
    
    // Results
    printf("Sorting completed: %s\n", sorted ? "SUCCESS" : "FAILED");
    printf("Execution time: %.6f seconds\n", execution_time);
    printf("Elements per second: %.0f\n", data_size / execution_time);
    
    // Save results to file
    FILE* result_file = fopen(result_filename, "w");
    if (result_file == NULL) {
        printf("Error: Could not create results file\n");
        free(data);
        free(data_copy);
        exit(1);
    }
    
    fprintf(result_file, "C Merge Sort Results\n");
    fprintf(result_file, "Data size: %d\n", data_size);
    fprintf(result_file, "Execution time: %.6f seconds\n", execution_time);
    fprintf(result_file, "Elements per second: %.0f\n", data_size / execution_time);
    fprintf(result_file, "Sorted correctly: %s\n", sorted ? "true" : "false");
    
    fclose(result_file);
    printf("Results saved to %s\n", result_filename);
    
    // Free memory
    free(data);
    free(data_copy);
    
    return 0;
}
