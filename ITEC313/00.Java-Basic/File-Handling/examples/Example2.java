/**
 * Example2.java
 * 
 * This program demonstrates example in Java:
 * - Core concepts and principles
 * - Implementation techniques
 * - Best practices and patterns
 * - Practical examples and usage
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.io.*;
import java.util.Date;

public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: File Writing Operations ===\n");
        
        // Example 2.1: Basic file writing
        basicFileWriting();
        
        // Example 2.2: Appending to files
        appendToFile();
        
        // Example 2.3: Writing formatted data
        writeFormattedData();
        
        // Example 2.4: Writing with buffering
        writeWithBuffering();
        
        System.out.println("=== Example 2 Complete ===\n");
    }
    
    /**
     * Demonstrates basic file writing using FileWriter
     * In Python: with open('file.txt', 'w') as f: f.write(content)
     */
    public static void basicFileWriting() {
        System.out.println("2.1 Basic file writing:");
        System.out.println("------------------------");
        
        String filename = "data/basic_output.txt";
        
        try {
            // Create FileWriter (overwrites existing file)
            FileWriter fileWriter = new FileWriter(filename);
            
            // Write simple text
            fileWriter.write("Hello, this is a test file!\n");
            fileWriter.write("This is the second line.\n");
            fileWriter.write("And this is the third line.\n");
            
            // Always flush and close
            fileWriter.flush();
            fileWriter.close();
            
            System.out.println("Successfully wrote to: " + filename);
            
            // Verify by reading back
            System.out.println("Verifying content:");
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("  " + line);
            }
            
            bufferedReader.close();
            fileReader.close();
            
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Demonstrates appending to existing files
     * In Python: with open('file.txt', 'a') as f: f.write(content)
     */
    public static void appendToFile() {
        System.out.println("2.2 Appending to file:");
        System.out.println("----------------------");
        
        String filename = "data/append_output.txt";
        
        try {
            // First, create a new file
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write("Original content\n");
            fileWriter.close();
            
            System.out.println("Created file with original content");
            
            // Now append to the file
            FileWriter appendWriter = new FileWriter(filename, true); // true = append mode
            appendWriter.write("This line was appended\n");
            appendWriter.write("Another appended line\n");
            appendWriter.write("Timestamp: " + new Date() + "\n");
            appendWriter.close();
            
            System.out.println("Appended additional content");
            
            // Read and display final content
            System.out.println("Final file content:");
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            int lineNumber = 0;
            while ((line = bufferedReader.readLine()) != null) {
                lineNumber++;
                System.out.println("  Line " + lineNumber + ": " + line);
            }
            
            bufferedReader.close();
            fileReader.close();
            
        } catch (IOException e) {
            System.err.println("Error with append operation: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Demonstrates writing formatted data to files
     * In Python: f.write(f"Formatted: {variable}")
     */
    public static void writeFormattedData() {
        System.out.println("2.3 Writing formatted data:");
        System.out.println("---------------------------");
        
        String filename = "data/formatted_output.txt";
        
        try {
            FileWriter fileWriter = new FileWriter(filename);
            
            // Write different types of formatted data
            String name = "Java";
            int version = 8;
            double pi = 3.14159;
            
            // Using String.format() (similar to Python's f-strings)
            fileWriter.write(String.format("Language: %s\n", name));
            fileWriter.write(String.format("Version: %d\n", version));
            fileWriter.write(String.format("Pi value: %.5f\n", pi));
            fileWriter.write(String.format("Combined: %s version %d\n", name, version));
            
            // Write a table-like structure
            fileWriter.write("\nData Table:\n");
            fileWriter.write("Name\tAge\tCity\n");
            fileWriter.write("John\t25\tNew York\n");
            fileWriter.write("Alice\t30\tLondon\n");
            fileWriter.write("Bob\t35\tParis\n");
            
            // Write current timestamp
            fileWriter.write(String.format("\nGenerated on: %s\n", new Date()));
            
            fileWriter.close();
            
            System.out.println("Successfully wrote formatted data to: " + filename);
            
            // Display the content
            System.out.println("Formatted content:");
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("  " + line);
            }
            
            bufferedReader.close();
            fileReader.close();
            
        } catch (IOException e) {
            System.err.println("Error writing formatted data: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Demonstrates writing with buffering for better performance
     * In Python: Buffering is automatic, but Java requires explicit BufferedWriter
     */
    public static void writeWithBuffering() {
        System.out.println("2.4 Writing with buffering:");
        System.out.println("---------------------------");
        
        String filename = "data/buffered_output.txt";
        
        try {
            // Use BufferedWriter for better performance
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            // Write multiple lines efficiently
            for (int i = 1; i <= 10; i++) {
                bufferedWriter.write("Line " + i + ": This is buffered writing\n");
            }
            
            // Write some special characters
            bufferedWriter.write("Special characters: á é í ó ú ñ\n");
            bufferedWriter.write("Unicode: \u0048\u0065\u006C\u006C\u006F\n"); // Hello
            
            // Always flush before closing
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
            
            System.out.println("Successfully wrote buffered content to: " + filename);
            
            // Verify the content
            System.out.println("Buffered content (first 5 lines):");
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            int count = 0;
            while ((line = bufferedReader.readLine()) != null && count < 5) {
                count++;
                System.out.println("  " + line);
            }
            
            if (count >= 5) {
                System.out.println("  ... (more lines)");
            }
            
            bufferedReader.close();
            fileReader.close();
            
        } catch (IOException e) {
            System.err.println("Error with buffered writing: " + e.getMessage());
        }
        System.out.println();
    }
} 