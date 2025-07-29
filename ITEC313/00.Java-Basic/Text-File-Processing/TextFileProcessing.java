/**
 * TextFileProcessing.java
 * 
 * This program demonstrates text file processing in Java:
 * - Reading text files
 * - Writing text files
 * - Text processing operations
 * - File format handling
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class TextFileProcessing {
    
    public static void main(String[] args) {
        System.out.println("=== Java Text File Processing Examples ===\n");
        
        // Create sample data files for demonstration
        createSampleFiles();
        
        // Demonstrate different file processing techniques
        demonstrateBasicFileReading();
        demonstrateAdvancedFileReading();
        demonstrateFileWriting();
        demonstrateFileAppending();
        demonstrateStreamProcessing();
        demonstrateFileCopying();
        
        System.out.println("\n=== All examples completed successfully! ===");
    }
    
    /**
     * Creates sample text files for demonstration purposes.
     * In Python, you might use: with open('file.txt', 'w') as f: f.write(content)
     */
    private static void createSampleFiles() {
        System.out.println("📁 Creating sample data files...");
        
        // Sample data for demonstration
        String[] sampleLines = {
            "Alice,25,Engineer",
            "Bob,30,Designer", 
            "Charlie,28,Developer",
            "Diana,32,Manager",
            "Eve,27,Analyst"
        };
        
        // Write sample data to files
        try {
            // Write to a simple text file
            Files.write(Paths.get("data/sample.txt"), Arrays.asList(sampleLines));
            
            // Write to a CSV file
            List<String> csvLines = new ArrayList<>();
            csvLines.add("Name,Age,Profession");
            csvLines.addAll(Arrays.asList(sampleLines));
            Files.write(Paths.get("data/employees.csv"), csvLines);
            
            // Write to a configuration file
            List<String> configLines = Arrays.asList(
                "# Application Configuration",
                "app.name=TextProcessor",
                "app.version=1.0",
                "max.file.size=1024",
                "encoding=UTF-8"
            );
            Files.write(Paths.get("data/config.txt"), configLines);
            
            System.out.println("✅ Sample files created successfully!");
            
        } catch (IOException e) {
            System.err.println("❌ Error creating sample files: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates basic file reading using BufferedReader.
     * Python equivalent: with open('file.txt', 'r') as f: content = f.read()
     */
    private static void demonstrateBasicFileReading() {
        System.out.println("\n📖 Basic File Reading Example:");
        System.out.println("Reading file: data/sample.txt");
        
        try (BufferedReader reader = new BufferedReader(new FileReader("data/sample.txt"))) {
            String line;
            int lineCount = 0;
            
            // Read file line by line (similar to Python's for line in file)
            while ((line = reader.readLine()) != null) {
                lineCount++;
                System.out.println("Line " + lineCount + ": " + line);
            }
            
            System.out.println("✅ Successfully read " + lineCount + " lines");
            
        } catch (IOException e) {
            System.err.println("❌ Error reading file: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates advanced file reading using Files utility class.
     * Python equivalent: lines = open('file.txt').readlines()
     */
    private static void demonstrateAdvancedFileReading() {
        System.out.println("\n📖 Advanced File Reading Example:");
        System.out.println("Reading file: data/employees.csv");
        
        try {
            // Read all lines at once (similar to Python's readlines())
            List<String> lines = Files.readAllLines(Paths.get("data/employees.csv"));
            
            System.out.println("CSV Data:");
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (i == 0) {
                    System.out.println("Header: " + line);
                } else {
                    // Parse CSV line (simple parsing)
                    String[] parts = line.split(",");
                    System.out.printf("Row %d: Name=%s, Age=%s, Job=%s%n", 
                                   i, parts[0], parts[1], parts[2]);
                }
            }
            
            System.out.println("✅ Successfully processed " + lines.size() + " lines");
            
        } catch (IOException e) {
            System.err.println("❌ Error reading file: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates file writing using BufferedWriter.
     * Python equivalent: with open('file.txt', 'w') as f: f.write(content)
     */
    private static void demonstrateFileWriting() {
        System.out.println("\n✍️ File Writing Example:");
        System.out.println("Writing to: data/output.txt");
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/output.txt"))) {
            // Write multiple lines
            writer.write("This is a sample output file.\n");
            writer.write("Created by Java TextFileProcessing program.\n");
            writer.write("Timestamp: " + new Date() + "\n");
            writer.write("Java version: " + System.getProperty("java.version") + "\n");
            
            System.out.println("✅ File written successfully!");
            
        } catch (IOException e) {
            System.err.println("❌ Error writing file: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates file appending (adding content to existing file).
     * Python equivalent: with open('file.txt', 'a') as f: f.write(content)
     */
    private static void demonstrateFileAppending() {
        System.out.println("\n➕ File Appending Example:");
        System.out.println("Appending to: data/output.txt");
        
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("data/output.txt", true))) { // true = append mode
            
            writer.write("\n--- Additional Content ---\n");
            writer.write("This content was appended to the existing file.\n");
            writer.write("Append timestamp: " + new Date() + "\n");
            
            System.out.println("✅ Content appended successfully!");
            
        } catch (IOException e) {
            System.err.println("❌ Error appending to file: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates stream-based file processing.
     * Python equivalent: lines = [line.strip() for line in open('file.txt') if line.strip()]
     */
    private static void demonstrateStreamProcessing() {
        System.out.println("\n🔄 Stream Processing Example:");
        System.out.println("Processing file: data/config.txt");
        
        try {
            // Read file and process with streams
            List<String> lines = Files.lines(Paths.get("data/config.txt"))
                .filter(line -> !line.trim().isEmpty()) // Skip empty lines
                .filter(line -> !line.trim().startsWith("#")) // Skip comments
                .map(line -> line.trim()) // Trim whitespace
                .collect(Collectors.toList());
            
            System.out.println("Configuration entries:");
            for (String line : lines) {
                if (line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    System.out.printf("  %s = %s%n", parts[0], parts[1]);
                }
            }
            
            System.out.println("✅ Stream processing completed!");
            
        } catch (IOException e) {
            System.err.println("❌ Error in stream processing: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates file copying using Files utility.
     * Python equivalent: shutil.copy('source.txt', 'destination.txt')
     */
    private static void demonstrateFileCopying() {
        System.out.println("\n📋 File Copying Example:");
        System.out.println("Copying data/sample.txt to data/sample_copy.txt");
        
        try {
            // Copy file using Files utility
            Files.copy(Paths.get("data/sample.txt"), 
                      Paths.get("data/sample_copy.txt"), 
                      StandardCopyOption.REPLACE_EXISTING);
            
            System.out.println("✅ File copied successfully!");
            
            // Verify copy by reading the copied file
            List<String> copiedLines = Files.readAllLines(Paths.get("data/sample_copy.txt"));
            System.out.println("Copied file contains " + copiedLines.size() + " lines");
            
        } catch (IOException e) {
            System.err.println("❌ Error copying file: " + e.getMessage());
        }
    }
} 