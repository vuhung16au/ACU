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
import java.util.*;

/**
 * Example 1: Basic CSV Reading
 * 
 * This example demonstrates the fundamental concepts of reading CSV files in Java.
 * It shows how to read a file line by line and parse comma-separated values.
 * 
 * Python equivalent:
 * with open('data.csv', 'r') as file:
 *     for line in file:
 *         fields = line.strip().split(',')
 *         print(fields)
 */
public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic CSV Reading ===\n");
        
        // Sample CSV data as a string (simulating a file)
        String csvData = "Name,Age,City\nJohn,25,Sydney\nJane,30,Melbourne\nBob,35,Brisbane";
        
        System.out.println("Original CSV data:");
        System.out.println(csvData);
        System.out.println();
        
        // Split the data into lines
        String[] lines = csvData.split("\n");
        
        System.out.println("Parsing CSV data:");
        
        // Process each line
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            
            if (i == 0) {
                // This is the header row
                System.out.println("Header row: " + line);
                String[] headers = line.split(",");
                System.out.println("Headers: " + Arrays.toString(headers));
            } else {
                // This is a data row
                System.out.println("Data row " + i + ": " + line);
                
                // Split the line by comma to get individual fields
                String[] fields = line.split(",");
                System.out.println("Fields: " + Arrays.toString(fields));
                
                // Access individual fields (similar to Python's row[0], row[1], etc.)
                if (fields.length >= 3) {
                    String name = fields[0];
                    String age = fields[1];
                    String city = fields[2];
                    
                    System.out.println("  Name: " + name);
                    System.out.println("  Age: " + age);
                    System.out.println("  City: " + city);
                }
            }
            System.out.println();
        }
        
        System.out.println("=== Example 1 completed ===");
    }
} 