import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CountingSort {
    
    /**
     * Counting sort implementation.
     * Determines the count of elements and places them in correct positions.
     */
    public static void sort(int[] arr) {
        if (arr.length == 0) {
            return;
        }
        
        // Find the minimum and maximum values
        int min = arr[0];
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            } else if (arr[i] > max) {
                max = arr[i];
            }
        }
        
        // Create counting array (with offset if min < 0)
        int range = max - min + 1;
        int[] count = new int[range];
        
        // Count occurrences of each element
        for (int i = 0; i < arr.length; i++) {
            count[arr[i] - min]++;
        }
        
        // Rebuild the sorted array
        int index = 0;
        for (int i = 0; i < range; i++) {
            while (count[i] > 0) {
                arr[index++] = i + min;
                count[i]--;
            }
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
        System.out.println("Java Counting Sort Performance Test");
        System.out.println("===================================");
        
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
        System.out.println("Starting Counting Sort...");
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
            writer.write("Java Counting Sort Results\n");
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
