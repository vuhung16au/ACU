/**
 * Radix Sort implementation in JavaScript with performance measurement.
 */

const fs = require('fs');
const path = require('path');

/**
 * Radix Sort implementation.
 * Sorts the array by processing individual digits.
 * 
 * @param {number[]} arr - The array to be sorted
 * @returns {number[]} - The sorted array
 */
function radixSort(arr) {
    if (arr.length <= 1) {
        return arr;
    }
    
    // Find the maximum number to know number of digits
    let max = Math.abs(arr[0]);
    let min = arr[0];
    
    for (let i = 1; i < arr.length; i++) {
        if (Math.abs(arr[i]) > max) {
            max = Math.abs(arr[i]);
        }
        if (arr[i] < min) {
            min = arr[i];
        }
    }
    
    // Calculate an offset to handle negative numbers
    let offset = 0;
    if (min < 0) {
        offset = Math.abs(min);
        // Apply offset to make all numbers non-negative
        for (let i = 0; i < arr.length; i++) {
            arr[i] += offset;
        }
        
        // Recalculate max after applying offset
        max = Math.max(max, Math.abs(min) + offset);
    }
    
    // Count number of digits in the maximum value
    const digits = Math.floor(Math.log10(max)) + 1;
    
    // Perform counting sort for each digit
    for (let digit = 0; digit < digits; digit++) {
        countingSortByDigit(arr, digit);
    }
    
    // Remove the offset if we applied one
    if (offset > 0) {
        for (let i = 0; i < arr.length; i++) {
            arr[i] -= offset;
        }
    }
    
    return arr;
}

/**
 * Counting sort implementation that sorts array based on the value of a specific digit.
 * 
 * @param {number[]} arr - The array to be sorted
 * @param {number} digit - The digit position to sort by (0 = ones, 1 = tens, etc.)
 */
function countingSortByDigit(arr, digit) {
    const n = arr.length;
    const output = new Array(n).fill(0);
    const count = new Array(10).fill(0);
    
    // Store count of occurrences in count[]
    for (let i = 0; i < n; i++) {
        const digitValue = getDigit(arr[i], digit);
        count[digitValue]++;
    }
    
    // Change count[i] so that count[i] now contains actual
    // position of this digit in output[]
    for (let i = 1; i < 10; i++) {
        count[i] += count[i - 1];
    }
    
    // Build the output array
    for (let i = n - 1; i >= 0; i--) {
        const digitValue = getDigit(arr[i], digit);
        output[count[digitValue] - 1] = arr[i];
        count[digitValue]--;
    }
    
    // Copy the output array to arr[]
    for (let i = 0; i < n; i++) {
        arr[i] = output[i];
    }
}

/**
 * Gets the value of a specific digit from a number.
 * 
 * @param {number} num - The number to get the digit from
 * @param {number} digit - The position of the digit (0 = ones, 1 = tens, etc.)
 * @returns {number} - The value of the digit
 */
function getDigit(num, digit) {
    return Math.floor(Math.abs(num) / Math.pow(10, digit)) % 10;
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
    console.log("JavaScript Radix Sort Performance Test");
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
    console.log("Starting Radix Sort...");
    const startTime = process.hrtime();
    radixSort(dataCopy);
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
    const results = `JavaScript Radix Sort Results\n` +
                    `Data size: ${data.length}\n` +
                    `Execution time: ${executionTime.toFixed(6)} seconds\n` +
                    `Elements per second: ${Math.floor(data.length / executionTime)}\n` +
                    `Sorted correctly: ${sorted}\n`;
    
    fs.writeFileSync(resultFilename, results);
    console.log(`Results saved to ${resultFilename}`);
}

// Run the main function
main();
