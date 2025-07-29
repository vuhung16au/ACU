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
import java.util.Scanner;

public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: BufferedReader and File I/O ===\n");
        
        // Example 1: BufferedReader for console input
        demonstrateBufferedReader();
        
        // Example 2: Reading from a file
        demonstrateFileReading();
        
        // Example 3: Writing to a file
        demonstrateFileWriting();
        
        // Example 4: Processing CSV data
        demonstrateCSVProcessing();
        
        // Example 5: Error handling in I/O operations
        demonstrateErrorHandling();
        
        System.out.println("\n=== Example 2 Complete ===");
    }
    
    /**
     * Demonstrate BufferedReader for efficient console input
     * BufferedReader is more efficient than Scanner for reading large amounts of text
     */
    public static void demonstrateBufferedReader() {
        System.out.println("1. BUFFERED READER CONSOLE INPUT");
        System.out.println("================================");
        
        // In Python: input() or sys.stdin.readline()
        // In Java: BufferedReader with InputStreamReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            System.out.print("Enter a sentence (BufferedReader): ");
            String sentence = reader.readLine();
            
            if (sentence != null && !sentence.trim().isEmpty()) {
                System.out.println("\n--- Sentence Analysis ---");
                System.out.printf("Original: \"%s\"%n", sentence);
                System.out.printf("Length: %d characters%n", sentence.length());
                System.out.printf("Words: %d%n", sentence.trim().split("\\s+").length);
                System.out.printf("Uppercase: \"%s\"%n", sentence.toUpperCase());
                System.out.printf("Lowercase: \"%s\"%n", sentence.toLowerCase());
                
                // Count vowels and consonants
                int vowels = 0, consonants = 0;
                String vowelChars = "aeiouAEIOU";
                for (char c : sentence.toCharArray()) {
                    if (Character.isLetter(c)) {
                        if (vowelChars.indexOf(c) != -1) {
                            vowels++;
                        } else {
                            consonants++;
                        }
                    }
                }
                System.out.printf("Vowels: %d%n", vowels);
                System.out.printf("Consonants: %d%n", consonants);
                
                // Reverse the sentence
                StringBuilder reversed = new StringBuilder(sentence).reverse();
                System.out.printf("Reversed: \"%s\"%n", reversed.toString());
            } else {
                System.out.println("No sentence entered.");
            }
            
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Error closing reader: " + e.getMessage());
            }
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrate reading from a file
     * Shows different approaches to file reading
     */
    public static void demonstrateFileReading() {
        System.out.println("2. FILE READING");
        System.out.println("===============");
        
        // Read from the sample input file
        String filename = "data/input.txt";
        
        System.out.printf("Reading from file: %s%n", filename);
        
        // Method 1: BufferedReader for line-by-line reading
        System.out.println("\n--- Method 1: BufferedReader ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                System.out.printf("Line %d: \"%s\"%n", lineNumber, line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        // Method 2: Scanner for parsing file content
        System.out.println("\n--- Method 2: Scanner ---");
        try (Scanner scanner = new Scanner(new File(filename))) {
            int wordCount = 0;
            while (scanner.hasNext()) {
                String word = scanner.next();
                System.out.printf("Word %d: \"%s\"%n", ++wordCount, word);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        // Method 3: Reading entire file as string
        System.out.println("\n--- Method 3: Read entire file ---");
        try {
            String content = new String(java.nio.file.Files.readAllBytes(
                java.nio.file.Paths.get(filename)));
            System.out.printf("File content:%n%s%n", content);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrate writing to files
     * Shows different approaches to file writing
     */
    public static void demonstrateFileWriting() {
        System.out.println("3. FILE WRITING");
        System.out.println("===============");
        
        // Method 1: PrintWriter for formatted output
        System.out.println("\n--- Method 1: PrintWriter ---");
        try (PrintWriter writer = new PrintWriter(new FileWriter("data/output.txt"))) {
            writer.println("This is a sample output file");
            writer.println("Created by Example2.java");
            writer.printf("Current time: %s%n", new java.util.Date());
            writer.printf("Random number: %d%n", (int)(Math.random() * 100));
            System.out.println("File written using PrintWriter");
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
        
        // Method 2: BufferedWriter for efficient writing
        System.out.println("\n--- Method 2: BufferedWriter ---");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/buffered_output.txt"))) {
            writer.write("Line 1: Using BufferedWriter\n");
            writer.write("Line 2: More efficient for large files\n");
            writer.write("Line 3: Buffers data before writing\n");
            System.out.println("File written using BufferedWriter");
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
        
        // Method 3: FileWriter with append mode
        System.out.println("\n--- Method 3: Append to file ---");
        try (FileWriter writer = new FileWriter("data/append_output.txt", true)) {
            writer.write("This line is appended to the file\n");
            writer.write("Append mode preserves existing content\n");
            System.out.println("Content appended to file");
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrate CSV file processing
     * Shows how to read and parse CSV data
     */
    public static void demonstrateCSVProcessing() {
        System.out.println("4. CSV FILE PROCESSING");
        System.out.println("======================");
        
        String csvFile = "data/sample.csv";
        
        System.out.printf("Processing CSV file: %s%n", csvFile);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            int rowNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                rowNumber++;
                
                // Skip empty lines and comments
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }
                
                System.out.printf("Row %d: %s%n", rowNumber, line);
                
                // Parse CSV line (simple comma-separated)
                String[] fields = line.split(",");
                System.out.printf("  Fields: %d%n", fields.length);
                
                for (int i = 0; i < fields.length; i++) {
                    System.out.printf("    Field %d: \"%s\"%n", i, fields[i].trim());
                }
                
                // Process data based on row type
                if (rowNumber == 1) {
                    System.out.println("  (Header row)");
                } else {
                    System.out.println("  (Data row)");
                    // Here you could process the data fields
                }
                
                System.out.println();
            }
            
        } catch (IOException e) {
            System.err.println("Error processing CSV file: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrate error handling in I/O operations
     * Shows different types of I/O errors and how to handle them
     */
    public static void demonstrateErrorHandling() {
        System.out.println("5. ERROR HANDLING");
        System.out.println("=================");
        
        // Example 1: File not found
        System.out.println("\n--- Example 1: File not found ---");
        try (BufferedReader reader = new BufferedReader(new FileReader("nonexistent.txt"))) {
            String line = reader.readLine();
            System.out.println("This should not be reached");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            System.err.println("Suggestion: Check if the file exists");
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
        }
        
        // Example 2: Permission denied
        System.out.println("\n--- Example 2: Permission issues ---");
        try {
            File file = new File("/root/protected.txt");
            if (file.exists()) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("This should fail");
                }
            }
        } catch (SecurityException e) {
            System.err.println("Permission denied: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
        }
        
        // Example 3: Invalid data format
        System.out.println("\n--- Example 3: Data format errors ---");
        try (Scanner scanner = new Scanner(new File("data/input.txt"))) {
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    int number = scanner.nextInt();
                    System.out.printf("Valid integer: %d%n", number);
                } else {
                    String text = scanner.next();
                    System.out.printf("Text (not a number): %s%n", text);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        System.out.println();
    }
} 