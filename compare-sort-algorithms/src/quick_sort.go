// filepath: /Users/vuhung/Desktop/compare-sort/src/quick_sort.go
package main

import (
	"bufio"
	"flag"
	"fmt"
	"os"
	"strconv"
	"strings"
	"time"
)

// quicksort performs the Quick Sort algorithm on an array
func quicksort(arr []int, low, high int) {
	if low < high {
		// Partition the array and get the pivot index
		pivotIndex := partition(arr, low, high)

		// Recursively sort elements before and after partition
		quicksort(arr, low, pivotIndex-1)
		quicksort(arr, pivotIndex+1, high)
	}
}

// partition implements the Lomuto partition scheme
func partition(arr []int, low, high int) int {
	// Choose the rightmost element as pivot
	pivot := arr[high]

	// Index of smaller element
	i := low - 1

	for j := low; j < high; j++ {
		// If current element is smaller than or equal to pivot
		if arr[j] <= pivot {
			i++
			arr[i], arr[j] = arr[j], arr[i]
		}
	}

	// Place pivot in correct position
	arr[i+1], arr[high] = arr[high], arr[i+1]
	return i + 1
}

// readNumbersFromFile reads integers from a file and returns as a slice
func readNumbersFromFile(filename string) ([]int, error) {
	file, err := os.Open(filename)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	var numbers []int
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := strings.TrimSpace(scanner.Text())
		if line == "" {
			continue
		}
		
		num, err := strconv.Atoi(line)
		if err != nil {
			// Skip non-integer lines
			continue
		}
		numbers = append(numbers, num)
	}

	if err := scanner.Err(); err != nil {
		return nil, err
	}
	return numbers, nil
}

// isSorted checks if an array is sorted in ascending order
func isSorted(arr []int) bool {
	for i := 0; i < len(arr)-1; i++ {
		if arr[i] > arr[i+1] {
			return false
		}
	}
	return true
}

func main() {
	fmt.Println("Go Quick Sort Performance Test")
	fmt.Println("===============================")

	// Parse command line flags
	inputFile := flag.String("file", "random_list.txt", "Path to input file")
	outputFile := flag.String("output", "results_go.txt", "Path to output results file")
	flag.Parse()

	fmt.Printf("Using dataset: %s\n", *inputFile)

	// Read data from file
	fmt.Println("Reading data from file...")
	data, err := readNumbersFromFile(*inputFile)
	if err != nil {
		fmt.Printf("Error: %s not found. Please run generate_data.py first.\n", *inputFile)
		os.Exit(1)
	}
	fmt.Printf("Data size: %d integers\n", len(data))

	// Create a copy for sorting (to preserve original)
	dataCopy := make([]int, len(data))
	copy(dataCopy, data)

	// Measure sorting time
	fmt.Println("Starting Quick Sort...")
	startTime := time.Now()
	quicksort(dataCopy, 0, len(dataCopy)-1)
	duration := time.Since(startTime)
	
	executionTime := float64(duration.Nanoseconds()) / 1000000000 // Convert to seconds

	// Verify the array is sorted
	sorted := isSorted(dataCopy)

	// Results
	fmt.Printf("Sorting completed: %s\n", map[bool]string{true: "SUCCESS", false: "FAILED"}[sorted])
	fmt.Printf("Execution time: %.6f seconds\n", executionTime)
	fmt.Printf("Elements per second: %.0f\n", float64(len(data))/executionTime)

	// Save results to file
	file, err := os.Create(*outputFile)
	if err != nil {
		fmt.Printf("Error creating results file: %v\n", err)
		os.Exit(1)
	}
	defer file.Close()

	fmt.Fprintf(file, "Go Quick Sort Results\n")
	fmt.Fprintf(file, "Data size: %d\n", len(data))
	fmt.Fprintf(file, "Execution time: %.6f seconds\n", executionTime)
	fmt.Fprintf(file, "Elements per second: %.0f\n", float64(len(data))/executionTime)
	fmt.Fprintf(file, "Sorted correctly: %v\n", sorted)

	fmt.Printf("Results saved to %s\n", *outputFile)
}
