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
import java.util.*;

/**
 * Example 2: CSV Writing
 * 
 * This example demonstrates how to write data to CSV files in Java.
 * It shows different approaches to creating CSV content and writing to files.
 * 
 * Python equivalent:
 * with open('output.csv', 'w') as file:
 *     file.write("Name,Age,City\n")
 *     file.write("John,25,Sydney\n")
 *     file.write("Jane,30,Melbourne\n")
 */
public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: CSV Writing ===\n");
        
        // Example 1: Writing simple CSV data
        System.out.println("1. Writing simple CSV data:");
        writeSimpleCSV();
        
        // Example 2: Writing CSV from data structures
        System.out.println("\n2. Writing CSV from data structures:");
        writeCSVFromData();
        
        // Example 3: Writing CSV with proper escaping
        System.out.println("\n3. Writing CSV with proper escaping:");
        writeCSVWithEscaping();
        
        System.out.println("\n=== Example 2 completed ===");
    }
    
    /**
     * Demonstrates writing simple CSV data to a file
     * 
     * Python equivalent:
     * with open('simple.csv', 'w') as file:
     *     file.write("Name,Age,City\n")
     *     file.write("John,25,Sydney\n")
     */
    public static void writeSimpleCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/simple.csv"))) {
            System.out.println("Writing to data/simple.csv");
            
            // Write header
            writer.write("Name,Age,City\n");
            
            // Write data rows
            writer.write("John,25,Sydney\n");
            writer.write("Jane,30,Melbourne\n");
            writer.write("Bob,35,Brisbane\n");
            
            System.out.println("Successfully wrote simple CSV file");
            
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates writing CSV from data structures (arrays and lists)
     * 
     * Python equivalent:
     * data = [["John", "25", "Sydney"], ["Jane", "30", "Melbourne"]]
     * with open('data.csv', 'w') as file:
     *     for row in data:
     *         file.write(','.join(row) + '\n')
     */
    public static void writeCSVFromData() {
        // Sample data as 2D array (similar to Python's list of lists)
        String[][] data = {
            {"Name", "Age", "City", "Occupation"},  // Header
            {"John Doe", "25", "Sydney", "Engineer"},
            {"Jane Smith", "30", "Melbourne", "Designer"},
            {"Bob Johnson", "35", "Brisbane", "Manager"},
            {"Alice Brown", "28", "Perth", "Developer"}
        };
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/data.csv"))) {
            System.out.println("Writing to data/data.csv");
            
            // Write each row
            for (String[] row : data) {
                // Join array elements with commas (Python: ','.join(row))
                String csvLine = String.join(",", row);
                writer.write(csvLine + "\n");
            }
            
            System.out.println("Successfully wrote " + data.length + " rows");
            
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates writing CSV with proper handling of special characters
     * 
     * Python equivalent would use csv.writer with proper quoting
     */
    public static void writeCSVWithEscaping() {
        // Data that contains commas and quotes
        String[][] data = {
            {"Name", "Age", "City", "Description"},
            {"John Doe", "25", "Sydney, NSW", "Software engineer"},
            {"Jane Smith", "30", "Melbourne, VIC", "UI/UX designer"},
            {"Bob Johnson", "35", "Brisbane, QLD", "Project manager"},
            {"Alice Brown", "28", "Perth, WA", "Data scientist"}
        };
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/escaped.csv"))) {
            System.out.println("Writing to data/escaped.csv");
            
            for (String[] row : data) {
                StringBuilder csvLine = new StringBuilder();
                
                for (int i = 0; i < row.length; i++) {
                    String field = row[i];
                    
                    // Check if field contains comma, quote, or newline
                    if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
                        // Escape quotes by doubling them
                        field = field.replace("\"", "\"\"");
                        // Wrap in quotes
                        csvLine.append("\"").append(field).append("\"");
                    } else {
                        csvLine.append(field);
                    }
                    
                    // Add comma separator (except for last field)
                    if (i < row.length - 1) {
                        csvLine.append(",");
                    }
                }
                
                writer.write(csvLine.toString() + "\n");
            }
            
            System.out.println("Successfully wrote escaped CSV file");
            
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
} 