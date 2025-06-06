#!/usr/bin/env php
<?php

/**
 * Radix Sort Implementation in PHP
 * 
 * This script implements radix sort algorithm with proper error handling,
 * memory management, and execution time tracking.
 */

// Set memory limit to 1GB to handle large arrays
ini_set('memory_limit', '1G');

// Set maximum execution time to 300 seconds (5 minutes)
set_time_limit(300);

/**
 * Counting sort for a specific digit
 * 
 * @param array $arr The array to be sorted
 * @param int $exp The exponent (digit position)
 * @return array The sorted array for the current digit
 */
function countingSortForRadix(array $arr, int $exp): array {
    $n = count($arr);
    $output = array_fill(0, $n, 0);
    $count = array_fill(0, 10, 0);
    
    // Store count of occurrences in count[]
    for ($i = 0; $i < $n; $i++) {
        $index = (int)($arr[$i] / $exp) % 10;
        $count[$index]++;
    }
    
    // Change count[i] so that count[i] now contains actual
    // position of this digit in output[]
    for ($i = 1; $i < 10; $i++) {
        $count[$i] += $count[$i - 1];
    }
    
    // Build the output array
    for ($i = $n - 1; $i >= 0; $i--) {
        $index = (int)($arr[$i] / $exp) % 10;
        $output[$count[$index] - 1] = $arr[$i];
        $count[$index]--;
    }
    
    return $output;
}

/**
 * Radix Sort Implementation
 * 
 * @param array $arr The array to be sorted
 * @return array The sorted array
 * @throws Exception If input is invalid or memory limit is exceeded
 */
function radixSort(array $arr): array {
    $n = count($arr);
    if ($n === 0) {
        return $arr;
    }

    // Find the maximum number to know number of digits
    $max = max($arr);
    
    // Do counting sort for every digit
    $exp = 1;
    while ($max / $exp > 0) {
        $arr = countingSortForRadix($arr, $exp);
        $exp = (int)($exp * 10); // Ensure exp remains an integer
    }
    
    return $arr;
}

// Main execution block
if (php_sapi_name() === 'cli') {
    try {
        // Check command line arguments
        if ($argc < 2) {
            throw new Exception("Usage: php radix_sort.php <input_file> [output_file]");
        }

        // Read input file
        $input_file = $argv[1];
        if (!file_exists($input_file)) {
            throw new Exception("Input file not found: $input_file");
        }

        // Read numbers from file
        $numbers = array_map('intval', file($input_file, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES));
        
        // Sort the array
        $startTime = microtime(true);
        $sorted = radixSort($numbers);
        $endTime = microtime(true);
        
        // Calculate metrics
        $executionTime = ($endTime - $startTime); // in seconds
        $dataSize = count($numbers);
        $elementsPerSecond = $dataSize / $executionTime;
        
        // Verify if array is sorted correctly
        $isSorted = true;
        for ($i = 1; $i < count($sorted); $i++) {
            if ($sorted[$i] < $sorted[$i - 1]) {
                $isSorted = false;
                break;
            }
        }
        
        // Output metrics
        fprintf(STDERR, "PHP Radix Sort Results\n");
        fprintf(STDERR, "Data size: %d\n", $dataSize);
        fprintf(STDERR, "Execution time: %.6f seconds\n", $executionTime);
        fprintf(STDERR, "Elements per second: %.0f\n", $elementsPerSecond);
        fprintf(STDERR, "Sorted correctly: %s\n", $isSorted ? "true" : "false");
        fprintf(STDERR, "Memory usage: %.2f MB\n", memory_get_peak_usage(true) / 1024 / 1024);
        
    } catch (Exception $e) {
        fprintf(STDERR, "Error: %s\n", $e->getMessage());
        exit(1);
    }
} 