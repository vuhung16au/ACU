/**
 * Merge Sort implementation in JavaScript with performance measurement.
 */

const fs = require('fs');
const path = require('path');

/**
 * Merge Sort implementation.
 * Divides the array into halves, sorts them, and then merges them.
 * 
 * @param {number[]} arr - The array to be sorted
 * @returns {number[]} - The sorted array
 */
function mergeSort(arr) {
    if (arr.length <= 1) {
        return arr;
    }
    
    // Split the array in half
    const middle = Math.floor(arr.length / 2);
    const left = arr.slice(0, middle);
    const right = arr.slice(middle);
    
    // Recursively sort both halves
    return merge(mergeSort(left), mergeSort(right));
}

/**
 * Merges two sorted arrays.
 * 
 * @param {number[]} left - The left array to merge
 * @param {number[]} right - The right array to merge
 * @returns {number[]} - The merged array
 */
function merge(left, right) {
    let result = [];
    let leftIndex = 0;
    let rightIndex = 0;
    
    // Compare elements from both arrays and merge in sorted order
    while (leftIndex < left.length && rightIndex < right.length) {
        if (left[leftIndex] <= right[rightIndex]) {
            result.push(left[leftIndex]);
            leftIndex++;
        } else {
            result.push(right[rightIndex]);
            rightIndex++;
        }
    }
    
    // Add remaining elements from left array (if any)
    while (leftIndex < left.length) {
        result.push(left[leftIndex]);
        leftIndex++;
    }
    
    // Add remaining elements from right array (if any)
    while (rightIndex < right.length) {
        result.push(right[rightIndex]);
        rightIndex++;
    }
    
    return result;
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
    console.log("JavaScript Merge Sort Performance Test");
    console.log("====================================");
    
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
    console.log("Starting Merge Sort...");
    const startTime = process.hrtime();
    const sortedData = mergeSort(dataCopy);
    const endTime = process.hrtime(startTime);
    
    // Calculate execution time in seconds
    const executionTime = endTime[0] + endTime[1] / 1e9;
    
    // Verify the array is sorted
    const sorted = isSorted(sortedData);
    
    // Results
    console.log(`Sorting completed: ${sorted ? 'SUCCESS' : 'FAILED'}`);
    console.log(`Execution time: ${executionTime.toFixed(6)} seconds`);
    console.log(`Elements per second: ${Math.floor(data.length / executionTime)}`);
    
    // Save results to file
    const results = `JavaScript Merge Sort Results\n` +
                    `Data size: ${data.length}\n` +
                    `Execution time: ${executionTime.toFixed(6)} seconds\n` +
                    `Elements per second: ${Math.floor(data.length / executionTime)}\n` +
                    `Sorted correctly: ${sorted}\n`;
    
    fs.writeFileSync(resultFilename, results);
    console.log(`Results saved to ${resultFilename}`);
}

// Run the main function
main();
