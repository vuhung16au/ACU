/**
 * Bubble Sort implementation in JavaScript with performance measurement.
 */

const fs = require('fs');
const path = require('path');

/**
 * Bubble Sort implementation.
 * Repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order.
 * 
 * @param {number[]} arr - The array to be sorted
 */
function bubbleSort(arr) {
    const n = arr.length;
    
    for (let i = 0; i < n; i++) {
        // Flag to optimize if no swaps are made in a pass
        let swapped = false;
        
        // Last i elements are already in place
        for (let j = 0; j < n - i - 1; j++) {
            // Compare adjacent elements
            if (arr[j] > arr[j + 1]) {
                // Swap arr[j] and arr[j+1]
                [arr[j], arr[j + 1]] = [arr[j + 1], arr[j]];
                swapped = true;
            }
        }
        
        // If no swapping occurred in this pass, array is sorted
        if (!swapped) {
            break;
        }
    }
}

/**
 * Reads numbers from file and returns as array.
 * 
 * @param {string} filename - The file to read from
 * @returns {number[]} Array of numbers
 */
function readNumbersFromFile(filename) {
    try {
        const data = fs.readFileSync(filename, 'utf8');
        return data.trim().split('\n').map(line => parseInt(line.trim(), 10)).filter(num => !isNaN(num));
    } catch (err) {
        console.error(`Error: ${filename} not found. Please run generate_data.py first.`);
        process.exit(1);
    }
}

/**
 * Verifies if the array is sorted.
 * 
 * @param {number[]} arr - The array to check
 * @returns {boolean} True if sorted, false otherwise
 */
function isSorted(arr) {
    for (let i = 0; i < arr.length - 1; i++) {
        if (arr[i] > arr[i + 1]) {
            return false;
        }
    }
    return true;
}

// Main function
function main() {
    console.log("JavaScript Bubble Sort Performance Test");
    console.log("=======================================");
    
    // Parse command line arguments
    const args = process.argv.slice(2);
    let filename = "random_list.txt";
    let resultFilename = "results_javascript.txt";
    
    if (args.length > 0) {
        filename = args[0];
        console.log(`Using dataset: ${filename}`);
    }
    
    if (args.length > 1) {
        resultFilename = args[1];
    }
    
    // Read data from file
    console.log("Reading data from file...");
    const data = readNumbersFromFile(filename);
    console.log(`Data size: ${data.length} integers`);
    
    // Create a copy for sorting (to preserve original)
    const dataCopy = [...data];
    
    // Measure sorting time
    console.log("Starting Bubble Sort...");
    const startTime = process.hrtime();
    bubbleSort(dataCopy);
    const endTime = process.hrtime(startTime);
    
    // Calculate execution time in seconds
    const executionTime = endTime[0] + endTime[1] / 1e9;
    
    // Verify the array is sorted
    const sorted = isSorted(dataCopy);
    
    // Results
    console.log(`Sorting completed: ${sorted ? 'SUCCESS' : 'FAILED'}`);
    console.log(`Execution time: ${executionTime.toFixed(6)} seconds`);
    console.log(`Elements per second: ${Math.floor(data.length / executionTime)}`);
    
    // Save results to file
    const results = `JavaScript Bubble Sort Results\n` +
                    `Data size: ${data.length}\n` +
                    `Execution time: ${executionTime.toFixed(6)} seconds\n` +
                    `Elements per second: ${Math.floor(data.length / executionTime)}\n` +
                    `Sorted correctly: ${sorted}\n`;
    
    fs.writeFileSync(resultFilename, results);
    console.log(`Results saved to ${resultFilename}`);
}

// Run the main function
main();
