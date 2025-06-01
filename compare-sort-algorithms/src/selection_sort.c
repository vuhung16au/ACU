#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include <string.h>

/**
 * Selection Sort implementation.
 * Repeatedly finds the minimum element from the unsorted part and puts it at the beginning.
 */
void selectionSort(int arr[], int n) {
    for (int i = 0; i < n - 1; i++) {
        // Find the minimum element in the unsorted array
        int min_idx = i;
        for (int j = i + 1; j < n; j++) {
            if (arr[j] < arr[min_idx]) {
                min_idx = j;
            }
        }
        
        // Swap the found minimum element with the first element
        if (min_idx != i) {
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }
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
    char buffer[256];
    while (fgets(buffer, sizeof(buffer), file) != NULL) {
        count++;
    }
    
    // Allocate memory for the array
    int* numbers = (int*)malloc(count * sizeof(int));
    if (numbers == NULL) {
        printf("Memory allocation failed.\n");
        fclose(file);
        exit(1);
    }
    
    // Reset file pointer and read the numbers
    rewind(file);
    int i = 0;
    while (fgets(buffer, sizeof(buffer), file) != NULL) {
        numbers[i++] = atoi(buffer);
    }
    
    fclose(file);
    *size = count;
    return numbers;
}

/**
 * Verifies if the array is sorted.
 */
bool isSorted(int arr[], int n) {
    for (int i = 0; i < n - 1; i++) {
        if (arr[i] > arr[i + 1]) {
            return false;
        }
    }
    return true;
}

int main(int argc, char* argv[]) {
    printf("C Selection Sort Performance Test\n");
    printf("=================================\n");
    
    // Parse command line arguments
    const char* filename = "random_list.txt";
    const char* resultFilename = "results_c.txt";
    
    if (argc > 1) {
        filename = argv[1];
        printf("Using dataset: %s\n", filename);
    }
    
    if (argc > 2) {
        resultFilename = argv[2];
    }
    
    // Read data from file
    printf("Reading data from file...\n");
    int size;
    int* data = readNumbersFromFile(filename, &size);
    printf("Data size: %d integers\n", size);
    
    // Create a copy for sorting (to preserve original)
    int* dataCopy = (int*)malloc(size * sizeof(int));
    if (dataCopy == NULL) {
        printf("Memory allocation failed.\n");
        free(data);
        exit(1);
    }
    memcpy(dataCopy, data, size * sizeof(int));
    
    // Measure sorting time
    printf("Starting Selection Sort...\n");
    clock_t start = clock();
    selectionSort(dataCopy, size);
    clock_t end = clock();
    
    double executionTime = ((double)(end - start)) / CLOCKS_PER_SEC;
    
    // Verify the array is sorted
    bool sorted = isSorted(dataCopy, size);
    
    // Results
    printf("Sorting completed: %s\n", sorted ? "SUCCESS" : "FAILED");
    printf("Execution time: %.6f seconds\n", executionTime);
    printf("Elements per second: %.0f\n", size / executionTime);
    
    // Save results to file
    FILE* resultFile = fopen(resultFilename, "w");
    if (resultFile == NULL) {
        printf("Error: Could not open %s for writing.\n", resultFilename);
        free(data);
        free(dataCopy);
        exit(1);
    }
    
    fprintf(resultFile, "C Selection Sort Results\n");
    fprintf(resultFile, "Data size: %d\n", size);
    fprintf(resultFile, "Execution time: %.6f seconds\n", executionTime);
    fprintf(resultFile, "Elements per second: %.0f\n", size / executionTime);
    fprintf(resultFile, "Sorted correctly: %s\n", sorted ? "true" : "false");
    
    fclose(resultFile);
    printf("Results saved to %s\n", resultFilename);
    
    // Cleanup
    free(data);
    free(dataCopy);
    
    return 0;
}
