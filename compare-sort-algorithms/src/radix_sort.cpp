#include <iostream>
#include <vector>
#include <fstream>
#include <chrono>
#include <iomanip>
#include <algorithm>

class RadixSort {
private:
    // A function to get maximum value in arr[]
    static int getMax(const std::vector<int>& arr) {
        int max = arr[0];
        for (size_t i = 1; i < arr.size(); i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
    
    // A function to get minimum value in arr[] (for handling negatives)
    static int getMin(const std::vector<int>& arr) {
        int min = arr[0];
        for (size_t i = 1; i < arr.size(); i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }
    
    // A function to do counting sort based on the digit
    static void countSort(std::vector<int>& arr, int exp, int offset = 0) {
        std::vector<int> output(arr.size());
        int count[10] = {0};
        
        // Store count of occurrences in count[]
        for (size_t i = 0; i < arr.size(); i++) {
            int adjusted = arr[i] + offset;
            count[(adjusted / exp) % 10]++;
        }
        
        // Change count[i] so that count[i] contains actual
        // position of this digit in output[]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        // Build the output array
        for (int i = arr.size() - 1; i >= 0; i--) {
            int adjusted = arr[i] + offset;
            output[count[(adjusted / exp) % 10] - 1] = arr[i];
            count[(adjusted / exp) % 10]--;
        }
        
        // Copy the output array to arr[]
        for (size_t i = 0; i < arr.size(); i++) {
            arr[i] = output[i];
        }
    }
    
public:
    static void sort(std::vector<int>& arr) {
        if (arr.empty()) {
            return;
        }
        
        // To handle negative numbers, find the minimum value and offset all values to make them positive
        int min = getMin(arr);
        int offset = (min < 0) ? -min : 0; // If min is negative, offset by its absolute value
        
        // Find the maximum number to know number of digits
        int max = getMax(arr);
        max += offset; // Adjust max for the offset
        
        // Do counting sort for every digit
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(arr, exp, offset);
        }
        
        if (offset > 0) {
            // Remove the offset we added
            for (size_t i = 0; i < arr.size(); i++) {
                arr[i] -= 0; // No need to adjust since we're sorting in-place and maintaining relative order
            }
        }
    }
};

std::vector<int> readNumbersFromFile(const std::string& filename = "random_list.txt") {
    std::vector<int> numbers;
    std::ifstream file(filename);
    
    if (!file.is_open()) {
        std::cerr << "Error: " << filename << " not found. Please run generate_data.py first." << std::endl;
        exit(1);
    }
    
    int number;
    while (file >> number) {
        numbers.push_back(number);
    }
    
    file.close();
    return numbers;
}

bool isSorted(const std::vector<int>& arr) {
    for (size_t i = 0; i < arr.size() - 1; i++) {
        if (arr[i] > arr[i + 1]) {
            return false;
        }
    }
    return true;
}

int main(int argc, char* argv[]) {
    std::cout << "C++ Radix Sort Performance Test" << std::endl;
    std::cout << "===============================" << std::endl;
    
    // Get input and output filenames from command line arguments
    std::string filename = "random_list.txt";
    std::string resultFilename = "results_cpp.txt";
    
    if (argc > 1) {
        filename = argv[1];
        std::cout << "Using dataset: " << filename << std::endl;
    }
    
    if (argc > 2) {
        resultFilename = argv[2];
    }
    
    // Read data from file
    std::cout << "Reading data from file..." << std::endl;
    std::vector<int> data = readNumbersFromFile(filename);
    std::cout << "Data size: " << data.size() << " integers" << std::endl;
    
    // Create a copy for sorting (to preserve original)
    std::vector<int> dataCopy = data;
    
    // Measure sorting time
    std::cout << "Starting Radix Sort..." << std::endl;
    auto startTime = std::chrono::high_resolution_clock::now();
    RadixSort::sort(dataCopy);
    auto endTime = std::chrono::high_resolution_clock::now();
    
    auto duration = std::chrono::duration_cast<std::chrono::microseconds>(endTime - startTime);
    double executionTime = duration.count() / 1000000.0; // Convert to seconds
    
    // Verify the array is sorted
    bool sorted = isSorted(dataCopy);
    
    // Results
    std::cout << "Sorting completed: " << (sorted ? "SUCCESS" : "FAILED") << std::endl;
    std::cout << std::fixed << std::setprecision(6);
    std::cout << "Execution time: " << executionTime << " seconds" << std::endl;
    std::cout << std::fixed << std::setprecision(0);
    std::cout << "Elements per second: " << data.size() / executionTime << std::endl;
    
    // Output filename was already defined above from command line args
    
    std::ofstream resultFile(resultFilename);
    resultFile << "C++ Radix Sort Results" << std::endl;
    resultFile << "Data size: " << data.size() << std::endl;
    resultFile << std::fixed << std::setprecision(6);
    resultFile << "Execution time: " << executionTime << " seconds" << std::endl;
    resultFile << std::fixed << std::setprecision(0);
    resultFile << "Elements per second: " << data.size() / executionTime << std::endl;
    resultFile << "Sorted correctly: " << (sorted ? "true" : "false") << std::endl;
    resultFile.close();
    
    std::cout << "Results saved to " << resultFilename << std::endl;
    
    return 0;
}
