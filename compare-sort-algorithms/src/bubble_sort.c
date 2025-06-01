#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include <string.h>

/**
 * Bubble Sort implementation.
 * Repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order.
 */
void bubbleSort(int arr[], int n) {
    for (int i = 0; i < n; i++) {
        // Flag to optimize if no swaps are made in a pass
        bool swapped = false;
        
        // Last i elements are already in place
        for (int j = 0; j < n - i - 1; j++) {
            // Compare adjacent elements
            if (arr[j] > arr[j + 1]) {
                // Swap arr[j] and arr[j+1]
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                swapped = true;
            }
        }
        
        // If no swapping occurred in this pass, array is sorted
        if (!swapped) {
            break;
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
    printf("C Bubble Sort Performance Test\n");
    printf("===============================\n");
    
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
    printf("Starting Bubble Sort...\n");
    clock_t start = clock();
    bubbleSort(dataCopy, size);
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
    
    fprintf(resultFile, "C Bubble Sort Results\n");
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
