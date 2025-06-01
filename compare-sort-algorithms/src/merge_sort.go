package main

import (
	"bufio"
	"flag"
	"fmt"
	"log"
	"os"
	"path/filepath"
	"strconv"
	"strings"
	"time"
)

// mergeSort implements the merge sort algorithm
func mergeSort(arr []int) []int {
	if len(arr) <= 1 {
		return arr
	}

	// Split the array in half
	mid := len(arr) / 2
	left := mergeSort(arr[:mid])
	right := mergeSort(arr[mid:])

	// Merge the two halves
	return merge(left, right)
}

// merge combines two sorted arrays
func merge(left, right []int) []int {
	result := make([]int, len(left)+len(right))
	i, j, k := 0, 0, 0

	// Compare elements from both arrays and merge in sorted order
	for i < len(left) && j < len(right) {
		if left[i] <= right[j] {
			result[k] = left[i]
			i++
		} else {
			result[k] = right[j]
			j++
		}
		k++
	}

	// Copy remaining elements from left array (if any)
	for i < len(left) {
		result[k] = left[i]
		i++
		k++
	}

	// Copy remaining elements from right array (if any)
	for j < len(right) {
		result[k] = right[j]
		j++
		k++
	}

	return result
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
	fmt.Println("Go Merge Sort Performance Test")
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
	fmt.Println("Starting Merge Sort...")
	startTime := time.Now()
	sortedData := mergeSort(dataCopy)
	executionTime := time.Since(startTime).Seconds()

	// Verify the array is sorted
	sorted := isSorted(sortedData)
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

	fmt.Fprintf(resultsFile, "Go Merge Sort Results\n")
	fmt.Fprintf(resultsFile, "Data size: %d\n", len(data))
	fmt.Fprintf(resultsFile, "Execution time: %.6f seconds\n", executionTime)
	fmt.Fprintf(resultsFile, "Elements per second: %.0f\n", float64(len(data))/executionTime)
	fmt.Fprintf(resultsFile, "Sorted correctly: %t\n", sorted)

	fmt.Printf("Results saved to %s\n", *outputPtr)
}
