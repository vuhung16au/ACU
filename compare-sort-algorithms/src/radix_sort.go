package main

import (
	"bufio"
	"flag"
	"fmt"
	"log"
	"math"
	"os"
	"path/filepath"
	"strconv"
	"strings"
	"time"
)

// getMax finds the maximum value in the array
func getMax(arr []int) int {
	max := arr[0]
	for i := 1; i < len(arr); i++ {
		if arr[i] > max {
			max = arr[i]
		}
	}
	return max
}

// getMin finds the minimum value in the array
func getMin(arr []int) int {
	min := arr[0]
	for i := 1; i < len(arr); i++ {
		if arr[i] < min {
			min = arr[i]
		}
	}
	return min
}

// countingSortByDigit sorts the array based on the digit at the given position
func countingSortByDigit(arr []int, exp int, offset int) {
	n := len(arr)
	output := make([]int, n)
	count := make([]int, 10)

	// Store count of occurrences in count[]
	for i := 0; i < n; i++ {
		// Add offset to handle negative numbers
		adjustedValue := arr[i] + offset
		digit := (adjustedValue / exp) % 10
		count[digit]++
	}

	// Change count[i] so that count[i] now contains
	// actual position of this digit in output[]
	for i := 1; i < 10; i++ {
		count[i] += count[i-1]
	}

	// Build the output array
	for i := n - 1; i >= 0; i-- {
		adjustedValue := arr[i] + offset
		digit := (adjustedValue / exp) % 10
		output[count[digit]-1] = arr[i]
		count[digit]--
	}

	// Copy the output array to arr[]
	for i := 0; i < n; i++ {
		arr[i] = output[i]
	}
}

// radixSort implements the radix sort algorithm
func radixSort(arr []int) {
	if len(arr) <= 1 {
		return
	}

	// Find the minimum value for handling negative numbers
	min := getMin(arr)
	offset := 0
	if min < 0 {
		offset = -min
	}

	// Find the maximum value to know number of digits
	max := getMax(arr)
	if min < 0 {
		// When we have negative numbers, we need to consider the absolute values
		max = int(math.Max(float64(max), float64(abs(min))))
	}
	max += offset // Adjust max for the offset

	// Do counting sort for every digit
	for exp := 1; max/exp > 0; exp *= 10 {
		countingSortByDigit(arr, exp, offset)
	}

	// Remove the offset if any was applied
	if offset > 0 {
		for i := 0; i < len(arr); i++ {
			arr[i] -= 0 // No need to adjust since we're sorting in-place and maintaining relative order
		}
	}
}

// abs returns the absolute value of an integer
func abs(x int) int {
	if x < 0 {
		return -x
	}
	return x
}

// readNumbersFromFile reads integers from a file and returns them as a slice
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
			// Skip lines that cannot be converted to integers
			continue
		}

		numbers = append(numbers, num)
	}

	if err := scanner.Err(); err != nil {
		return nil, err
	}

	return numbers, nil
}

// isSorted checks if the array is sorted
func isSorted(arr []int) bool {
	for i := 0; i < len(arr)-1; i++ {
		if arr[i] > arr[i+1] {
			return false
		}
	}
	return true
}

func main() {
	fmt.Println("Go Radix Sort Performance Test")
	fmt.Println("==============================")

	// Parse command-line flags
	filePtr := flag.String("file", "random_list.txt", "Path to input file with numbers")
	outputPtr := flag.String("output", "results_go.txt", "Path to output file for results")
	flag.Parse()

	fmt.Printf("Using dataset: %s\n", *filePtr)

	// Read data from file
	fmt.Println("Reading data from file...")
	data, err := readNumbersFromFile(*filePtr)
	if err != nil {
		log.Fatalf("Error reading file: %v", err)
	}
	fmt.Printf("Data size: %d integers\n", len(data))

	// Create a copy for sorting (to preserve original)
	dataCopy := make([]int, len(data))
	copy(dataCopy, data)

	// Measure sorting time
	fmt.Println("Starting Radix Sort...")
	startTime := time.Now()
	radixSort(dataCopy)
	executionTime := time.Since(startTime).Seconds()

	// Verify the array is sorted
	sorted := isSorted(dataCopy)
	sortedStr := "SUCCESS"
	if !sorted {
		sortedStr = "FAILED"
	}

	// Results
	fmt.Printf("Sorting completed: %s\n", sortedStr)
	fmt.Printf("Execution time: %.6f seconds\n", executionTime)
	fmt.Printf("Elements per second: %.0f\n", float64(len(data))/executionTime)

	// Create output directory if it doesn't exist
	outputDir := filepath.Dir(*outputPtr)
	if outputDir != "." && outputDir != "" {
		err = os.MkdirAll(outputDir, 0755)
		if err != nil {
			log.Fatalf("Error creating output directory: %v", err)
		}
	}

	// Save results to file
	resultsFile, err := os.Create(*outputPtr)
	if err != nil {
		log.Fatalf("Error creating results file: %v", err)
	}
	defer resultsFile.Close()

	fmt.Fprintf(resultsFile, "Go Radix Sort Results\n")
	fmt.Fprintf(resultsFile, "Data size: %d\n", len(data))
	fmt.Fprintf(resultsFile, "Execution time: %.6f seconds\n", executionTime)
	fmt.Fprintf(resultsFile, "Elements per second: %.0f\n", float64(len(data))/executionTime)
	fmt.Fprintf(resultsFile, "Sorted correctly: %t\n", sorted)

	fmt.Printf("Results saved to %s\n", *outputPtr)
}
