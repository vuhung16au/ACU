#include <iostream>
#include <vector>
#include <fstream>
#include <chrono>
#include <iomanip>
#include <algorithm>

class InsertionSort {
public:
    static void sort(std::vector<int>& arr) {
        int n = arr.size();
        
        // Start from the second element (index 1)
        for (int i = 1; i < n; i++) {
            // Key element to be inserted in sorted array
            int key = arr[i];
            int j = i - 1;
            
            // Move elements greater than key to one position ahead
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            
            // Place key at its correct position
            arr[j + 1] = key;
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
    
    return numbers;
}

bool isSorted(const std::vector<int>& arr) {
    for (size_t i = 1; i < arr.size(); i++) {
        if (arr[i - 1] > arr[i]) {
            return false;
        }
    }
    return true;
}

int main(int argc, char* argv[]) {
    std::cout << "C++ Insertion Sort Performance Test" << std::endl;
    std::cout << "=================================" << std::endl;
    
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
    std::cout << "Starting Insertion Sort..." << std::endl;
    auto startTime = std::chrono::high_resolution_clock::now();
    InsertionSort::sort(dataCopy);
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
    resultFile << "C++ Insertion Sort Results" << std::endl;
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
