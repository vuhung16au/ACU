#!/usr/bin/env php
<?php

/**
 * Bubble Sort Implementation in PHP
 * 
 * This script implements bubble sort algorithm with proper error handling,
 * memory management, and execution time tracking.
 */

// Set memory limit to 1GB to handle large arrays
ini_set('memory_limit', '1G');

// Set maximum execution time to 300 seconds (5 minutes)
set_time_limit(300);

/**
 * Bubble Sort Implementation
 * 
 * @param array $arr The array to be sorted
 * @return array The sorted array
 * @throws Exception If input is invalid or memory limit is exceeded
 */
function bubbleSort(array $arr): array {
    $n = count($arr);
    if ($n <= 1) {
        return $arr;
    }

    // Bubble sort implementation
    for ($i = 0; $i < $n - 1; $i++) {
        $swapped = false;
        
        for ($j = 0; $j < $n - $i - 1; $j++) {
            if ($arr[$j] > $arr[$j + 1]) {
                // Swap elements
                $temp = $arr[$j];
                $arr[$j] = $arr[$j + 1];
                $arr[$j + 1] = $temp;
                $swapped = true;
            }
        }

        // If no swapping occurred, array is sorted
        if (!$swapped) {
            break;
        }
    }

    return $arr;
}

// Main execution block
if (php_sapi_name() === 'cli') {
    try {
        // Check command line arguments
        if ($argc < 2) {
            throw new Exception("Usage: php bubble_sort.php <input_file> [output_file]");
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
        $sorted = bubbleSort($numbers);
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
        fprintf(STDERR, "PHP Bubble Sort Results\n");
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