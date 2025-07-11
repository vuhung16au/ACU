/**
 * FileHandling.java
 * 
 * This program demonstrates file handling in Java, including:
 * - Reading from text files
 * - Writing to text files
 * - File existence checks
 * - Error handling with try-catch blocks
 * - Working with different file types
 * 
 * Key differences from Python:
 * - Java uses explicit try-catch blocks for exception handling
 * - File operations require explicit resource management
 * - Java has separate classes for different file operations (FileReader, FileWriter, etc.)
 * - No context managers like Python's 'with' statement (prior to Java 7)
 * 
 * @author ITEC313 - Object-Oriented Programming
 * @version 1.0
 */

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileHandling {
    
    public static void main(String[] args) {
        System.out.println("=== Java File Handling Examples ===\n");
        
        // Example 1: Basic file reading
        demonstrateBasicFileReading();
        
        // Example 2: File writing
        demonstrateFileWriting();
        
        // Example 3: File existence and properties
        demonstrateFileProperties();
        
        // Example 4: Reading CSV files
        demonstrateCSVReading();
        
        // Example 5: Error handling
        demonstrateErrorHandling();
        
        System.out.println("\n=== File Handling Examples Complete ===");
    }
    
    /**
     * Demonstrates basic file reading using FileReader and BufferedReader
     * In Python: with open('file.txt', 'r') as f: content = f.read()
     */
    public static void demonstrateBasicFileReading() {
        System.out.println("1. Basic File Reading:");
        System.out.println("------------------------");
        
        String filename = "data/input.txt";
        
        try {
            // Method 1: Using FileReader (character by character)
            System.out.println("Reading file using FileReader:");
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            int lineCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                lineCount++;
                System.out.println("Line " + lineCount + ": " + line);
            }
            
            // Always close resources in Java (unlike Python's context managers)
            bufferedReader.close();
            fileReader.close();
            
            // Method 2: Reading entire file at once (Java 7+)
            System.out.println("\nReading entire file at once:");
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            System.out.println("Full content:\n" + content);
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Demonstrates file writing using FileWriter and BufferedWriter
     * In Python: with open('file.txt', 'w') as f: f.write(content)
     */
    public static void demonstrateFileWriting() {
        System.out.println("2. File Writing:");
        System.out.println("----------------");
        
        String filename = "data/output.txt";
        
        try {
            // Method 1: Using FileWriter
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            // Write different types of content
            bufferedWriter.write("Hello from Java File Handling!\n");
            bufferedWriter.write("This is line 2\n");
            bufferedWriter.write("This is line 3\n");
            
            // Write formatted content
            String formattedLine = String.format("Current time: %s\n", new Date());
            bufferedWriter.write(formattedLine);
            
            // Always flush and close
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
            
            System.out.println("Successfully wrote to: " + filename);
            
            // Read back to verify
            System.out.println("Verifying written content:");
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            System.out.println(content);
            
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Demonstrates checking file properties and existence
     * In Python: os.path.exists(), os.path.getsize(), etc.
     */
    public static void demonstrateFileProperties() {
        System.out.println("3. File Properties:");
        System.out.println("-------------------");
        
        String filename = "data/input.txt";
        File file = new File(filename);
        
        // Check if file exists
        if (file.exists()) {
            System.out.println("File exists: " + filename);
            System.out.println("File size: " + file.length() + " bytes");
            System.out.println("Is file: " + file.isFile());
            System.out.println("Is directory: " + file.isDirectory());
            System.out.println("Can read: " + file.canRead());
            System.out.println("Can write: " + file.canWrite());
            System.out.println("Last modified: " + new Date(file.lastModified()));
        } else {
            System.out.println("File does not exist: " + filename);
        }
        
        // Check directory properties
        File dataDir = new File("data");
        if (dataDir.exists() && dataDir.isDirectory()) {
            System.out.println("\nContents of data directory:");
            File[] files = dataDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    System.out.println("  - " + f.getName() + " (" + f.length() + " bytes)");
                }
            }
        }
        System.out.println();
    }
    
    /**
     * Demonstrates reading CSV files
     * In Python: import csv; reader = csv.reader(file)
     */
    public static void demonstrateCSVReading() {
        System.out.println("4. CSV File Reading:");
        System.out.println("---------------------");
        
        String csvFilename = "data/sample.csv";
        
        try {
            FileReader fileReader = new FileReader(csvFilename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            int rowCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                rowCount++;
                
                // Skip comment lines (lines starting with #)
                if (line.trim().startsWith("#")) {
                    System.out.println("Skipping comment line: " + line);
                    continue;
                }
                
                // Split by comma (simple CSV parsing)
                String[] columns = line.split(",");
                System.out.println("Row " + rowCount + ": " + Arrays.toString(columns));
                
                // Process each column
                for (int i = 0; i < columns.length; i++) {
                    System.out.println("  Column " + (i + 1) + ": " + columns[i].trim());
                }
            }
            
            bufferedReader.close();
            fileReader.close();
            
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Demonstrates proper error handling for file operations
     * In Python: try/except blocks
     */
    public static void demonstrateErrorHandling() {
        System.out.println("5. Error Handling:");
        System.out.println("------------------");
        
        // Try to read a non-existent file
        String nonExistentFile = "data/nonexistent.txt";
        
        try {
            FileReader fileReader = new FileReader(nonExistentFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line = bufferedReader.readLine();
            bufferedReader.close();
            fileReader.close();
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            System.out.println("This is expected behavior for demonstration.");
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
        }
        
        // Try to write to a read-only location
        String readOnlyFile = "/tmp/test_write_permission.txt";
        
        try {
            FileWriter fileWriter = new FileWriter(readOnlyFile);
            fileWriter.write("Test content");
            fileWriter.close();
            System.out.println("Successfully wrote to: " + readOnlyFile);
            
        } catch (IOException e) {
            System.err.println("Write permission error: " + e.getMessage());
            System.out.println("This might fail depending on system permissions.");
        }
        
        System.out.println();
    }
} 