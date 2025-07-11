/**
 * Example1.java - Basic File Reading
 * 
 * This example demonstrates fundamental file reading operations in Java.
 * Shows how to read text files line by line and character by character.
 * 
 * Key differences from Python:
 * - Java requires explicit resource management (close() calls)
 * - No context managers like Python's 'with' statement
 * - BufferedReader provides efficient line-by-line reading
 * 
 * @author ITEC313 - Object-Oriented Programming
 */

import java.io.*;

public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic File Reading ===\n");
        
        // Example 1.1: Reading file line by line
        readFileLineByLine();
        
        // Example 1.2: Reading file character by character
        readFileCharacterByCharacter();
        
        // Example 1.3: Reading entire file at once
        readEntireFile();
        
        System.out.println("=== Example 1 Complete ===\n");
    }
    
    /**
     * Demonstrates reading a file line by line using BufferedReader
     * In Python: for line in open('file.txt'): print(line)
     */
    public static void readFileLineByLine() {
        System.out.println("1.1 Reading file line by line:");
        System.out.println("-------------------------------");
        
        String filename = "data/input.txt";
        
        try {
            // Create FileReader and BufferedReader for efficient reading
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            int lineNumber = 0;
            
            // Read each line until end of file (null is returned)
            while ((line = bufferedReader.readLine()) != null) {
                lineNumber++;
                System.out.println("Line " + lineNumber + ": " + line);
                
                // Process the line (e.g., count words)
                String[] words = line.split("\\s+");
                System.out.println("  Words in this line: " + words.length);
            }
            
            System.out.println("Total lines read: " + lineNumber);
            
            // Always close resources in Java
            bufferedReader.close();
            fileReader.close();
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Demonstrates reading a file character by character
     * In Python: with open('file.txt', 'r') as f: char = f.read(1)
     */
    public static void readFileCharacterByCharacter() {
        System.out.println("1.2 Reading file character by character:");
        System.out.println("----------------------------------------");
        
        String filename = "data/input.txt";
        
        try {
            FileReader fileReader = new FileReader(filename);
            
            int character;
            int charCount = 0;
            StringBuilder content = new StringBuilder();
            
            // Read character by character (-1 indicates end of file)
            while ((character = fileReader.read()) != -1) {
                charCount++;
                char c = (char) character;
                content.append(c);
                
                // Print first 50 characters with their ASCII values
                if (charCount <= 50) {
                    System.out.println("Char " + charCount + ": '" + c + "' (ASCII: " + character + ")");
                }
            }
            
            System.out.println("Total characters read: " + charCount);
            System.out.println("First 100 characters: " + content.substring(0, Math.min(100, content.length())));
            
            fileReader.close();
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Demonstrates reading entire file content at once
     * In Python: content = open('file.txt').read()
     */
    public static void readEntireFile() {
        System.out.println("1.3 Reading entire file at once:");
        System.out.println("--------------------------------");
        
        String filename = "data/input.txt";
        
        try {
            // Method 1: Using FileReader with StringBuilder
            FileReader fileReader = new FileReader(filename);
            StringBuilder content = new StringBuilder();
            char[] buffer = new char[1024]; // Read in chunks of 1024 characters
            int bytesRead;
            
            while ((bytesRead = fileReader.read(buffer)) != -1) {
                content.append(buffer, 0, bytesRead);
            }
            
            System.out.println("File content:");
            System.out.println(content.toString());
            System.out.println("Total characters: " + content.length());
            
            fileReader.close();
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        System.out.println();
    }
} 