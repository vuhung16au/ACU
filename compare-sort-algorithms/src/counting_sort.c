#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include <string.h>
#include <limits.h>

/**
 * Counting Sort implementation.
 * A non-comparison based sorting algorithm that works by counting the number of objects
 * that have each distinct key value, and using that information to determine their positions.
 * Efficient for small ranges of integers.
 */
void countingSort(int arr[], int n) {
    // Find the maximum value in the array
    int max = arr[0];
    for (int i = 1; i < n; i++) {
        if (arr[i] > max) {
            max = arr[i];
        }
    }
    
    // Find the minimum value in the array
    int min = arr[0];
    for (int i = 1; i < n; i++) {
        if (arr[i] < min) {
            min = arr[i];
        }
    }
    
    int range = max - min + 1;
    
    // Create count and output arrays
    int* count = (int*)calloc(range, sizeof(int));
    int* output = (int*)malloc(n * sizeof(int));
    
    if (count == NULL || output == NULL) {
        printf("Memory allocation failed\n");
        if (count) free(count);
        if (output) free(output);
        exit(1);
    }
    
    // Store the count of each element
    for (int i = 0; i < n; i++) {
        count[arr[i] - min]++;
    }
    
    // Store the cumulative count
    for (int i = 1; i < range; i++) {
        count[i] += count[i - 1];
    }
    
    // Build the output array
    for (int i = n - 1; i >= 0; i--) {
        output[count[arr[i] - min] - 1] = arr[i];
        count[arr[i] - min]--;
    }
    
    // Copy the output array to the input array
    memcpy(arr, output, n * sizeof(int));
    
    free(count);
    free(output);
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
    printf("C Counting Sort Performance Test\n");
    printf("================================\n");
    
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
    printf("Starting Counting Sort...\n");
    clock_t start_time = clock();
    countingSort(data_copy, data_size);
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
    
    fprintf(result_file, "C Counting Sort Results\n");
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
