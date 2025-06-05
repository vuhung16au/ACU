#include <iostream>
#include <vector>
#include <fstream>
#include <chrono>
#include <iomanip>
#include <algorithm>
#include <random>

class QuickSort {
private:
    static const int INSERTION_SORT_THRESHOLD = 10;
    
    // Insertion sort for small subarrays
    static void insertionSort(std::vector<int>& arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= low && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    // Choose a good pivot using median-of-three
    static int choosePivot(std::vector<int>& arr, int low, int high) {
        int mid = low + (high - low) / 2;
        
        // Sort low, mid, high
        if (arr[low] > arr[mid])
            std::swap(arr[low], arr[mid]);
        if (arr[low] > arr[high])
            std::swap(arr[low], arr[high]);
        if (arr[mid] > arr[high])
            std::swap(arr[mid], arr[high]);
        
        // Place pivot at high-1
        std::swap(arr[mid], arr[high - 1]);
        return arr[high - 1];
    }
    
    // Three-way partition to handle duplicates efficiently
    static std::pair<int, int> partition(std::vector<int>& arr, int low, int high) {
        int pivot = choosePivot(arr, low, high);
        
        // Three-way partition
        int lt = low;      // Elements < pivot
        int gt = high - 1; // Elements > pivot
        int i = low;       // Current element
        
        while (i <= gt) {
            if (arr[i] < pivot) {
                std::swap(arr[lt++], arr[i++]);
            } else if (arr[i] > pivot) {
                std::swap(arr[i], arr[gt--]);
            } else {
                i++;
            }
        }
        
        return {lt, gt};
    }
    
    // Optimized quick sort implementation
    static void quickSort(std::vector<int>& arr, int low, int high) {
        while (high - low > INSERTION_SORT_THRESHOLD) {
            auto [lt, gt] = partition(arr, low, high);
            
            // Recursively sort smaller partition first
            if (lt - low < high - gt) {
                quickSort(arr, low, lt - 1);
                low = gt + 1;
            } else {
                quickSort(arr, gt + 1, high);
                high = lt - 1;
            }
        }
        
        // Use insertion sort for small subarrays
        insertionSort(arr, low, high);
    }

public:
    static void sort(std::vector<int>& arr) {
        if (arr.size() <= 1) return;
        quickSort(arr, 0, arr.size() - 1);
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
    std::cout << "C++ Quick Sort Performance Test" << std::endl;
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
    std::cout << "Starting Quick Sort..." << std::endl;
    auto startTime = std::chrono::high_resolution_clock::now();
    QuickSort::sort(dataCopy);
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
    resultFile << "C++ Quick Sort Results" << std::endl;
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

