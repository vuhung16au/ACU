/**
 * Example1.java
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

public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic File Reading and Writing ===\n");
        
        // Create a sample file to work with
        createSampleFile();
        
        // Demonstrate reading the file
        readFileExample();
        
        // Demonstrate writing to a new file
        writeFileExample();
        
        System.out.println("\n=== Example 1 completed! ===");
    }
    
    /**
     * Creates a sample text file for demonstration.
     * Python equivalent: with open('sample.txt', 'w') as f: f.write(content)
     */
    private static void createSampleFile() {
        System.out.println("📝 Creating sample file: data/example1_sample.txt");
        
        try (FileWriter writer = new FileWriter("data/example1_sample.txt")) {
            // Write some sample content
            writer.write("Hello from Java!\n");
            writer.write("This is a sample file created by Example1.\n");
            writer.write("Line 3: Learning file I/O operations.\n");
            writer.write("Line 4: Understanding Java vs Python differences.\n");
            writer.write("Line 5: End of sample content.\n");
            
            System.out.println("✅ Sample file created successfully!");
            
        } catch (IOException e) {
            System.err.println("❌ Error creating sample file: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates basic file reading using FileReader.
     * Python equivalent: with open('file.txt', 'r') as f: content = f.read()
     */
    private static void readFileExample() {
        System.out.println("\n📖 Reading file: data/example1_sample.txt");
        
        try (FileReader reader = new FileReader("data/example1_sample.txt")) {
            // Read character by character (not efficient for large files)
            StringBuilder content = new StringBuilder();
            int character;
            
            while ((character = reader.read()) != -1) {
                content.append((char) character);
            }
            
            System.out.println("File content:");
            System.out.println("---");
            System.out.print(content.toString());
            System.out.println("---");
            System.out.println("✅ File read successfully!");
            
        } catch (IOException e) {
            System.err.println("❌ Error reading file: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates basic file writing using FileWriter.
     * Python equivalent: with open('output.txt', 'w') as f: f.write(content)
     */
    private static void writeFileExample() {
        System.out.println("\n✍️ Writing to file: data/example1_output.txt");
        
        try (FileWriter writer = new FileWriter("data/example1_output.txt")) {
            // Write some content to the file
            writer.write("Output file created by Example1\n");
            writer.write("Current timestamp: " + java.time.LocalDateTime.now() + "\n");
            writer.write("Java version: " + System.getProperty("java.version") + "\n");
            writer.write("Operating system: " + System.getProperty("os.name") + "\n");
            writer.write("User home directory: " + System.getProperty("user.home") + "\n");
            
            System.out.println("✅ Output file written successfully!");
            
        } catch (IOException e) {
            System.err.println("❌ Error writing file: " + e.getMessage());
        }
    }
} 