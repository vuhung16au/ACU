import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RadixSort {
    
    /**
     * Radix sort implementation.
     * Sorts the array by processing individual digits.
     */
    public static void sort(int[] arr) {
        if (arr.length <= 1) {
            return;
        }
        
        // Find the maximum number to know number of digits
        int max = getMax(arr);
        int min = getMin(arr);
        
        // Calculate an offset to handle negative numbers
        int offset = 0;
        if (min < 0) {
            offset = -min;
            
            // Apply offset to make all numbers non-negative
            for (int i = 0; i < arr.length; i++) {
                arr[i] += offset;
            }
            
            // Recalculate max after applying offset
            max += offset;
        }
        
        // Do counting sort for every digit
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(arr, exp);
        }
        
        // Remove the offset if we applied one
        if (offset > 0) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] -= offset;
            }
        }
    }
    
    // A function to get maximum value in arr[]
    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
    
    // A function to get minimum value in arr[] for handling negative numbers
    private static int getMin(int[] arr) {
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }
    
    // A function to do counting sort according to the digit represented by exp
    private static void countSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];
        
        // Store count of occurrences in count[]
        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }
        
        // Change count[i] so that count[i] now contains actual
        // position of this digit in output[]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        // Build the output array
        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }
        
        // Copy the output array to arr[]
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }
    
    /**
     * Reads integers from a file.
     */
    private static int[] readNumbersFromFile(String filename) {
        List<Integer> numbers = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int num = Integer.parseInt(line.trim());
                    numbers.add(num);
                } catch (NumberFormatException e) {
                    // Skip non-integer lines
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            System.err.println(e.getMessage());
            System.exit(1);
        }
        
        // Convert List<Integer> to int[]
        int[] result = new int[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            result[i] = numbers.get(i);
        }
        
        return result;
    }
    
    /**
     * Verify that array is sorted.
     */
    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        System.out.println("Java Radix Sort Performance Test");
        System.out.println("================================");
        
        // Parse command-line arguments
        String filename = "random_list.txt";
        String resultFilename = "results_java.txt";
        
        if (args.length > 0) {
            filename = args[0];
            System.out.println("Using dataset: " + filename);
        }
        
        if (args.length > 1) {
            resultFilename = args[1];
        }
        
        // Read data from file
        System.out.println("Reading data from file...");
        int[] data = readNumbersFromFile(filename);
        System.out.println("Data size: " + data.length + " integers");
        
        // Create a copy for sorting (to preserve original)
        int[] dataCopy = new int[data.length];
        System.arraycopy(data, 0, dataCopy, 0, data.length);
        
        // Measure sorting time
        System.out.println("Starting Radix Sort...");
        long startTime = System.nanoTime();
        sort(dataCopy);
        long endTime = System.nanoTime();
        
        double executionTime = (endTime - startTime) / 1_000_000_000.0; // Convert to seconds
        
        // Verify the array is sorted
        boolean sorted = isSorted(dataCopy);
        
        // Results
        System.out.println("Sorting completed: " + (sorted ? "SUCCESS" : "FAILED"));
        System.out.printf("Execution time: %.6f seconds\n", executionTime);
        System.out.printf("Elements per second: %.0f\n", data.length / executionTime);
        
        // Save results to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFilename))) {
            writer.write("Java Radix Sort Results\n");
            writer.write("Data size: " + data.length + "\n");
            writer.write("Execution time: " + executionTime + " seconds\n");
            writer.write("Elements per second: " + (int)(data.length / executionTime) + "\n");
            writer.write("Sorted correctly: " + sorted + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + resultFilename);
            System.err.println(e.getMessage());
        }
        
        System.out.println("Results saved to " + resultFilename);
    }
}
