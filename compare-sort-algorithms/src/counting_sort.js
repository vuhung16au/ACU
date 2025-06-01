/**
 * Counting Sort implementation in JavaScript with performance measurement.
 */

const fs = require('fs');
const path = require('path');

/**
 * Counting Sort implementation.
 * Determines the count of elements and places them in correct positions.
 * 
 * @param {number[]} arr - The array to be sorted
 * @returns {number[]} - The sorted array
 */
function countingSort(arr) {
    if (arr.length <= 1) {
        return arr;
    }
    
    // Find the minimum and maximum values
    let min = arr[0];
    let max = arr[0];
    
    for (let i = 1; i < arr.length; i++) {
        if (arr[i] < min) {
            min = arr[i];
        } else if (arr[i] > max) {
            max = arr[i];
        }
    }
    
    // Create counting array (with offset if min < 0)
    const range = max - min + 1;
    const count = new Array(range).fill(0);
    
    // Count occurrences of each element
    for (let i = 0; i < arr.length; i++) {
        count[arr[i] - min]++;
    }
    
    // Rebuild the sorted array
    let sortedIndex = 0;
    for (let i = 0; i < range; i++) {
        while (count[i] > 0) {
            arr[sortedIndex++] = i + min;
            count[i]--;
        }
    }
    
    return arr;
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
    console.log("JavaScript Counting Sort Performance Test");
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
    console.log("Starting Counting Sort...");
    const startTime = process.hrtime();
    countingSort(dataCopy);
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
    const results = `JavaScript Counting Sort Results\n` +
                    `Data size: ${data.length}\n` +
                    `Execution time: ${executionTime.toFixed(6)} seconds\n` +
                    `Elements per second: ${Math.floor(data.length / executionTime)}\n` +
                    `Sorted correctly: ${sorted}\n`;
    
    fs.writeFileSync(resultFilename, results);
    console.log(`Results saved to ${resultFilename}`);
}

// Run the main function
main();
