#include <iostream>
#include <vector>
#include <fstream>
#include <chrono>
#include <iomanip>
#include <algorithm>

class MergeSort {
public:
    static void sort(std::vector<int>& arr) {
        // Temporary array for merging
        std::vector<int> temp(arr.size());
        
        // Call the recursive merge sort function
        mergeSort(arr, temp, 0, arr.size() - 1);
    }

private:
    static void mergeSort(std::vector<int>& arr, std::vector<int>& temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            // Sort first and second halves
            mergeSort(arr, temp, left, mid);
            mergeSort(arr, temp, mid + 1, right);
            
            // Merge the sorted halves
            merge(arr, temp, left, mid, right);
        }
    }
    
    static void merge(std::vector<int>& arr, std::vector<int>& temp, int left, int mid, int right) {
        // Copy data to temp arrays
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }
        
        int i = left;     // Initial index of first subarray
        int j = mid + 1;  // Initial index of second subarray
        int k = left;     // Initial index of merged subarray
        
        // Merge the temp arrays back into arr
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                arr[k] = temp[i];
                i++;
            } else {
                arr[k] = temp[j];
                j++;
            }
            k++;
        }
        
        // Copy the remaining elements of left subarray, if any
        while (i <= mid) {
            arr[k] = temp[i];
            i++;
            k++;
        }
        
        // Copy the remaining elements of right subarray, if any
        // Note: We don't need this loop as the elements are already in place
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
    std::cout << "C++ Merge Sort Performance Test" << std::endl;
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
    std::cout << "Starting Merge Sort..." << std::endl;
    auto startTime = std::chrono::high_resolution_clock::now();
    MergeSort::sort(dataCopy);
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
    resultFile << "C++ Merge Sort Results" << std::endl;
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
