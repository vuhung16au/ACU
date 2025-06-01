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

// countingSort implements the counting sort algorithm
func countingSort(arr []int) {
	if len(arr) <= 1 {
		return
	}

	// Find the minimum and maximum values
	min, max := arr[0], arr[0]
	for i := 1; i < len(arr); i++ {
		if arr[i] < min {
			min = arr[i]
		} else if arr[i] > max {
			max = arr[i]
		}
	}

	// Create counting array (with offset if min < 0)
	range_ := max - min + 1
	count := make([]int, range_)

	// Count occurrences of each element
	for _, value := range arr {
		count[value-min]++
	}

	// Rebuild the sorted array
	index := 0
	for i, cnt := range count {
		value := i + min
		for j := 0; j < cnt; j++ {
			arr[index] = value
			index++
		}
	}
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
	fmt.Println("Go Counting Sort Performance Test")
	fmt.Println("=================================")

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
	fmt.Println("Starting Counting Sort...")
	startTime := time.Now()
	countingSort(dataCopy)
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

	fmt.Fprintf(resultsFile, "Go Counting Sort Results\n")
	fmt.Fprintf(resultsFile, "Data size: %d\n", len(data))
	fmt.Fprintf(resultsFile, "Execution time: %.6f seconds\n", executionTime)
	fmt.Fprintf(resultsFile, "Elements per second: %.0f\n", float64(len(data))/executionTime)
	fmt.Fprintf(resultsFile, "Sorted correctly: %t\n", sorted)

	fmt.Printf("Results saved to %s\n", *outputPtr)
}
