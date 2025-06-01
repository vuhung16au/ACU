import java.io.*;
import java.util.*;

public class QuickSort {
    
    public static void sort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            
            // Recursively sort elements before and after partition
            sort(arr, low, pivotIndex - 1);
            sort(arr, pivotIndex + 1, high);
        }
    }
    
    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }
    
    private static int partition(int[] arr, int low, int high) {
        // Choose the rightmost element as pivot
        int pivot = arr[high];
        
        // Index of smaller element
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (arr[j] <= pivot) {
                i++;
                // Swap elements
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        // Place pivot in correct position
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
    
    public static int[] readNumbersFromFile(String filename) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    numbers.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {
                    // Skip non-integer lines
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + filename + " not found. Please run generate_data.py first.");
            System.exit(1);
        }
        
        return numbers.stream().mapToInt(Integer::intValue).toArray();
    }
    
    public static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        System.out.println("Java Quick Sort Performance Test");
        System.out.println("================================");
        
        try {
            // Get input and output filenames from command line arguments
            String inputFile = "datasets/random_list.txt";  // Fixed path to include datasets directory
            String outputFile = "results/results_java.txt"; // Fixed path to include results directory
            
            if (args.length > 0) {
                inputFile = args[0];
            }
            
            if (args.length > 1) {
                outputFile = args[1];
            }
            
            System.out.println("Using dataset: " + inputFile);
                
            // Read data from file
            System.out.println("Reading data from file...");
            int[] data = readNumbersFromFile(inputFile);
            System.out.println("Data size: " + data.length + " integers");
            
            // Create a copy for sorting (to preserve original)
            int[] dataCopy = Arrays.copyOf(data, data.length);
            
            // Measure sorting time
            System.out.println("Starting Quick Sort...");
            long startTime = System.nanoTime();
            sort(dataCopy);
            long endTime = System.nanoTime();
            
            double executionTime = (endTime - startTime) / 1_000_000_000.0; // Convert to seconds
            
            // Verify the array is sorted
            boolean sorted = isSorted(dataCopy);
            
            // Results
            System.out.println("Sorting completed: " + (sorted ? "SUCCESS" : "FAILED"));
            System.out.printf("Execution time: %.6f seconds%n", executionTime);
            System.out.printf("Elements per second: %.0f%n", data.length / executionTime);
            
            // Write results to the output file
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                writer.println("Java Quick Sort Results");
                writer.println("Data size: " + data.length);
                writer.printf("Execution time: %.6f seconds%n", executionTime);
                writer.printf("Elements per second: %.0f%n", data.length / executionTime);
                writer.println("Sorted correctly: " + sorted);
            }
            
            System.out.println("Results saved to " + outputFile);
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
    }
}