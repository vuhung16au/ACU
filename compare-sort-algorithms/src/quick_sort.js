#!/usr/bin/env node
/**
 * Quick Sort implementation in JavaScript with performance measurement.
 */
const fs = require('fs');

class QuickSort {
    static sort(arr, low = 0, high = arr.length - 1) {
        if (low < high) {
            const pivotIndex = this.partition(arr, low, high);
            
            // Recursively sort elements before and after partition
            this.sort(arr, low, pivotIndex - 1);
            this.sort(arr, pivotIndex + 1, high);
        }
    }
    
    static partition(arr, low, high) {
        // Choose the rightmost element as pivot
        const pivot = arr[high];
        
        // Index of smaller element
        let i = low - 1;
        
        for (let j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (arr[j] <= pivot) {
                i++;
                // Swap elements
                [arr[i], arr[j]] = [arr[j], arr[i]];
            }
        }
        
        // Place pivot in correct position
        [arr[i + 1], arr[high]] = [arr[high], arr[i + 1]];
        return i + 1;
    }
}

function readNumbersFromFile(filename = 'random_list.txt') {
    try {
        const data = fs.readFileSync(filename, 'utf8');
        return data.trim().split('\n')
            .map(line => parseInt(line.trim()))
            .filter(num => !isNaN(num));
    } catch (error) {
        if (error.code === 'ENOENT') {
            console.error(`Error: ${filename} not found. Please run generate_data.py first.`);
            process.exit(1);
        }
        throw error;
    }
}

function isSorted(arr) {
    for (let i = 0; i < arr.length - 1; i++) {
        if (arr[i] > arr[i + 1]) {
            return false;
        }
    }
    return true;
}

function main() {
    console.log('JavaScript Quick Sort Performance Test');
    console.log('======================================');
    
    // Get input and output filenames from command line arguments
    const inputFile = process.argv[2] || 'random_list.txt';
    const outputFile = process.argv[3] || 'results_javascript.txt';
    
    console.log(`Using dataset: ${inputFile}`);
    
    // Read data from file
    console.log('Reading data from file...');
    const data = readNumbersFromFile(inputFile);
    console.log(`Data size: ${data.length} integers`);
    
    // Create a copy for sorting (to preserve original)
    const dataCopy = [...data];
    
    // Measure sorting time
    console.log('Starting Quick Sort...');
    const startTime = process.hrtime.bigint();
    QuickSort.sort(dataCopy);
    const endTime = process.hrtime.bigint();
    
    const executionTime = Number(endTime - startTime) / 1_000_000_000; // Convert to seconds
    
    // Verify the array is sorted
    const sorted = isSorted(dataCopy);
    
    // Results
    console.log(`Sorting completed: ${sorted ? 'SUCCESS' : 'FAILED'}`);
    console.log(`Execution time: ${executionTime.toFixed(6)} seconds`);
    console.log(`Elements per second: ${Math.round(data.length / executionTime)}`);
    
    // Save results to the specified output file
    const results = `JavaScript Quick Sort Results
Data size: ${data.length}
Execution time: ${executionTime.toFixed(6)} seconds
Elements per second: ${Math.round(data.length / executionTime)}
Sorted correctly: ${sorted}
`;
    
    fs.writeFileSync(outputFile, results);
    console.log(`Results saved to ${outputFile}`);
}

if (require.main === module) {
    main();
}
