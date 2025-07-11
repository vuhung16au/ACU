/**
 * Example 2: Advanced File Processing with Buffered I/O
 * 
 * This example demonstrates more efficient file processing using buffered I/O
 * and line-by-line processing techniques.
 * 
 * Key Learning Points:
 * - Using BufferedReader and BufferedWriter for efficient I/O
 * - Line-by-line processing for large files
 * - File filtering and transformation
 * - Working with different file formats
 * 
 * Python Comparison:
 * - Java: BufferedReader vs Python: file object iteration
 * - Java: explicit buffering vs Python: automatic buffering
 * - Java: manual line processing vs Python: for line in file
 */

import java.io.*;
import java.util.*;

public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: Advanced File Processing ===\n");
        
        // Create sample data files
        createSampleDataFiles();
        
        // Demonstrate different processing techniques
        demonstrateLineByLineProcessing();
        demonstrateFileFiltering();
        demonstrateDataTransformation();
        demonstrateFileMerging();
        
        System.out.println("\n=== Example 2 completed! ===");
    }
    
    /**
     * Creates sample data files for demonstration.
     * Python equivalent: with open('file.txt', 'w') as f: f.writelines(lines)
     */
    private static void createSampleDataFiles() {
        System.out.println("📁 Creating sample data files...");
        
        // Sample data for different file types
        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace"};
        String[] cities = {"Sydney", "Melbourne", "Brisbane", "Perth", "Adelaide"};
        String[] products = {"Laptop", "Phone", "Tablet", "Monitor", "Keyboard"};
        
        try {
            // Create names file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/names.txt"))) {
                for (String name : names) {
                    writer.write(name + "\n");
                }
            }
            
            // Create cities file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/cities.txt"))) {
                for (String city : cities) {
                    writer.write(city + "\n");
                }
            }
            
            // Create products file with prices
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/products.csv"))) {
                writer.write("Product,Price,Category\n");
                writer.write("Laptop,1200,Electronics\n");
                writer.write("Phone,800,Electronics\n");
                writer.write("Tablet,500,Electronics\n");
                writer.write("Monitor,300,Electronics\n");
                writer.write("Keyboard,50,Accessories\n");
            }
            
            System.out.println("✅ Sample data files created successfully!");
            
        } catch (IOException e) {
            System.err.println("❌ Error creating sample files: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates line-by-line processing using BufferedReader.
     * Python equivalent: for line in open('file.txt'): process(line)
     */
    private static void demonstrateLineByLineProcessing() {
        System.out.println("\n📖 Line-by-line processing: data/names.txt");
        
        try (BufferedReader reader = new BufferedReader(new FileReader("data/names.txt"))) {
            String line;
            int lineNumber = 0;
            List<String> processedNames = new ArrayList<>();
            
            // Process each line individually
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String processedLine = line.trim().toUpperCase();
                processedNames.add(processedLine);
                
                System.out.printf("Line %d: '%s' -> '%s'%n", 
                                lineNumber, line, processedLine);
            }
            
            // Write processed names to a new file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/processed_names.txt"))) {
                for (String name : processedNames) {
                    writer.write(name + "\n");
                }
            }
            
            System.out.println("✅ Processed " + lineNumber + " lines and saved to processed_names.txt");
            
        } catch (IOException e) {
            System.err.println("❌ Error in line-by-line processing: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates file filtering (keeping only lines that match criteria).
     * Python equivalent: [line for line in open('file.txt') if condition(line)]
     */
    private static void demonstrateFileFiltering() {
        System.out.println("\n🔍 File filtering: data/cities.txt (cities starting with 'S' or 'M')");
        
        try (BufferedReader reader = new BufferedReader(new FileReader("data/cities.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("data/filtered_cities.txt"))) {
            
            String line;
            int totalLines = 0;
            int filteredLines = 0;
            
            while ((line = reader.readLine()) != null) {
                totalLines++;
                String trimmedLine = line.trim();
                
                // Filter: keep only cities starting with 'S' or 'M'
                if (trimmedLine.startsWith("S") || trimmedLine.startsWith("M")) {
                    writer.write(trimmedLine + "\n");
                    filteredLines++;
                    System.out.println("✅ Kept: " + trimmedLine);
                } else {
                    System.out.println("❌ Filtered out: " + trimmedLine);
                }
            }
            
            System.out.printf("✅ Filtering complete: %d total lines, %d kept%n", 
                            totalLines, filteredLines);
            
        } catch (IOException e) {
            System.err.println("❌ Error in file filtering: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates data transformation (converting CSV to formatted output).
     * Python equivalent: csv.reader() and custom formatting
     */
    private static void demonstrateDataTransformation() {
        System.out.println("\n🔄 Data transformation: data/products.csv -> formatted output");
        
        try (BufferedReader reader = new BufferedReader(new FileReader("data/products.csv"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("data/formatted_products.txt"))) {
            
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    // Skip header line
                    isFirstLine = false;
                    continue;
                }
                
                // Parse CSV line
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String product = parts[0];
                    String price = parts[1];
                    String category = parts[2];
                    
                    // Transform and format the data
                    String formattedLine = String.format("Product: %-10s | Price: $%-6s | Category: %s", 
                                                      product, price, category);
                    
                    writer.write(formattedLine + "\n");
                    System.out.println(formattedLine);
                }
            }
            
            System.out.println("✅ Data transformation completed!");
            
        } catch (IOException e) {
            System.err.println("❌ Error in data transformation: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates file merging (combining multiple files into one).
     * Python equivalent: concatenating file contents
     */
    private static void demonstrateFileMerging() {
        System.out.println("\n🔗 File merging: combining names.txt and cities.txt");
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/merged_data.txt"))) {
            
            // Write header
            writer.write("=== MERGED DATA ===\n\n");
            
            // Merge names
            writer.write("NAMES:\n");
            try (BufferedReader reader = new BufferedReader(new FileReader("data/names.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write("  - " + line.trim() + "\n");
                }
            }
            
            writer.write("\nCITIES:\n");
            // Merge cities
            try (BufferedReader reader = new BufferedReader(new FileReader("data/cities.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write("  - " + line.trim() + "\n");
                }
            }
            
            writer.write("\n=== END OF MERGED DATA ===\n");
            
            System.out.println("✅ Files merged successfully into merged_data.txt");
            
        } catch (IOException e) {
            System.err.println("❌ Error in file merging: " + e.getMessage());
        }
    }
} 