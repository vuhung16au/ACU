// filepath: /Users/vuhung/Desktop/compare-sort/src/quick_sort.c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <stdbool.h>

// Quick Sort function declarations
void quicksort(int arr[], int low, int high);
int partition(int arr[], int low, int high);

// Swap function
void swap(int* a, int* b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

// Quick Sort implementation
void quicksort(int arr[], int low, int high) {
    if (low < high) {
        // Partition the array and get the pivot index
        int pivot_index = partition(arr, low, high);
        
        // Recursively sort elements before and after partition
        quicksort(arr, low, pivot_index - 1);
        quicksort(arr, pivot_index + 1, high);
    }
}

// Lomuto partition scheme
int partition(int arr[], int low, int high) {
    // Choose the rightmost element as pivot
    int pivot = arr[high];
    
    // Index of smaller element
    int i = low - 1;
    
    for (int j = low; j < high; j++) {
        // If current element is smaller than or equal to pivot
        if (arr[j] <= pivot) {
            i++;
            swap(&arr[i], &arr[j]);
        }
    }
    
    // Place pivot in correct position
    swap(&arr[i + 1], &arr[high]);
    return i + 1;
}

// Read numbers from file
int* read_numbers_from_file(const char* filename, int* size) {
    FILE* file = fopen(filename, "r");
    if (file == NULL) {
        printf("Error: %s not found. Please run generate_data.py first.\n", filename);
        exit(1);
    }
    
    // Allocate initial memory
    int capacity = 1000;
    int* numbers = (int*)malloc(capacity * sizeof(int));
    if (numbers == NULL) {
        printf("Error: Memory allocation failed\n");
        fclose(file);
        exit(1);
    }
    
    // Read numbers from file
    int count = 0;
    int number;
    char line[20]; // Assuming numbers won't exceed 20 characters
    
    while (fgets(line, sizeof(line), file)) {
        // Resize array if needed
        if (count >= capacity) {
            capacity *= 2;
            int* temp = (int*)realloc(numbers, capacity * sizeof(int));
            if (temp == NULL) {
                printf("Error: Memory reallocation failed\n");
                free(numbers);
                fclose(file);
                exit(1);
            }
            numbers = temp;
        }
        
        number = atoi(line);
        numbers[count++] = number;
    }
    
    fclose(file);
    *size = count;
    return numbers;
}

// Check if array is sorted
bool is_sorted(int arr[], int size) {
    for (int i = 0; i < size - 1; i++) {
        if (arr[i] > arr[i + 1]) {
            return false;
        }
    }
    return true;
}

int main(int argc, char* argv[]) {
    printf("C Quick Sort Performance Test\n");
    printf("=============================\n");
    
    // Get input and output filenames from command line arguments
    const char* filename = argc > 1 ? argv[1] : "random_list.txt";
    const char* result_filename = argc > 2 ? argv[2] : "results_c.txt";
    
    printf("Using dataset: %s\n", filename);
    
    // Read data from file
    printf("Reading data from file...\n");
    int data_size;
    int* data = read_numbers_from_file(filename, &data_size);
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
    printf("Starting Quick Sort...\n");
    clock_t start_time = clock();
    quicksort(data_copy, 0, data_size - 1);
    clock_t end_time = clock();
    
    double execution_time = ((double)(end_time - start_time)) / CLOCKS_PER_SEC;
    
    // Verify the array is sorted
    bool sorted = is_sorted(data_copy, data_size);
    
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
    
    fprintf(result_file, "C Quick Sort Results\n");
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
