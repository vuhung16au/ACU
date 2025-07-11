import java.io.*;
import java.util.*;

/**
 * CSV-File-Handling - Main Program
 * 
 * This program demonstrates how to work with CSV files in Java.
 * It covers reading, writing, parsing, and manipulating CSV data.
 * 
 * Key differences from Python:
 * - Java uses BufferedReader/FileReader instead of Python's open()
 * - No built-in CSV module like Python's csv module
 * - Manual string splitting and parsing required
 * - More explicit exception handling
 * 
 * @author ITEC313 Student
 * @version 1.0
 */
public class CSVFileHandling {
    
    /**
     * Main method - Entry point of the program
     * Demonstrates various CSV file operations
     */
    public static void main(String[] args) {
        System.out.println("=== CSV File Handling in Java ===\n");
        
        // Example 1: Reading a CSV file
        System.out.println("1. Reading CSV File:");
        readCSVFile("data/sample.csv");
        
        // Example 2: Writing a CSV file
        System.out.println("\n2. Writing CSV File:");
        writeCSVFile("data/output.csv");
        
        // Example 3: Parsing CSV data
        System.out.println("\n3. Parsing CSV Data:");
        parseCSVData();
        
        // Example 4: Advanced CSV operations
        System.out.println("\n4. Advanced CSV Operations:");
        advancedCSVOperations();
        
        System.out.println("\n=== Program completed successfully ===");
    }
    
    /**
     * Reads and displays the contents of a CSV file
     * 
     * @param filename The path to the CSV file to read
     * 
     * Python equivalent: 
     * with open(filename, 'r') as file:
     *     for line in file:
     *         print(line.strip())
     */
    public static void readCSVFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            
            System.out.println("Reading file: " + filename);
            System.out.println("Content:");
            
            // Read file line by line (similar to Python's for line in file)
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                System.out.println("Line " + lineNumber + ": " + line);
                
                // Skip empty lines and comments
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }
                
                // Parse CSV line (split by comma)
                String[] fields = line.split(",");
                System.out.println("  Fields: " + Arrays.toString(fields));
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + filename);
            System.err.println("Python equivalent: FileNotFoundError");
        } catch (IOException e) {
            System.err.println("Error: Could not read file - " + e.getMessage());
            System.err.println("Python equivalent: IOError");
        }
    }
    
    /**
     * Writes data to a CSV file
     * 
     * @param filename The path to the CSV file to write
     * 
     * Python equivalent:
     * with open(filename, 'w') as file:
     *     file.write("header1,header2,header3\n")
     *     file.write("value1,value2,value3\n")
     */
    public static void writeCSVFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            System.out.println("Writing to file: " + filename);
            
            // Write header row
            writer.write("Name,Age,City,Occupation\n");
            
            // Write data rows
            String[][] data = {
                {"John Doe", "25", "Sydney", "Engineer"},
                {"Jane Smith", "30", "Melbourne", "Designer"},
                {"Bob Johnson", "35", "Brisbane", "Manager"},
                {"Alice Brown", "28", "Perth", "Developer"}
            };
            
            for (String[] row : data) {
                // Join array elements with commas (Python: ','.join(row))
                String csvLine = String.join(",", row);
                writer.write(csvLine + "\n");
            }
            
            System.out.println("Successfully wrote " + data.length + " rows to " + filename);
            
        } catch (IOException e) {
            System.err.println("Error: Could not write to file - " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates parsing CSV data with different scenarios
     * 
     * Python equivalent:
     * import csv
     * with open('file.csv', 'r') as file:
     *     reader = csv.reader(file)
     *     for row in reader:
     *         print(row)
     */
    public static void parseCSVData() {
        // Sample CSV data as string (simulating file content)
        String csvData = "Name,Age,City\nJohn,25,Sydney\nJane,30,Melbourne\nBob,35,Brisbane";
        
        System.out.println("Parsing CSV data:");
        System.out.println("Original data: " + csvData);
        
        // Split by lines first
        String[] lines = csvData.split("\n");
        
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            
            if (i == 0) {
                System.out.println("Header: " + line);
            } else {
                System.out.println("Row " + i + ": " + line);
                
                // Split by comma to get individual fields
                String[] fields = line.split(",");
                System.out.println("  Parsed fields: " + Arrays.toString(fields));
                
                // Access individual fields (Python: row[0], row[1], etc.)
                if (fields.length >= 3) {
                    String name = fields[0];
                    String age = fields[1];
                    String city = fields[2];
                    
                    System.out.println("    Name: " + name);
                    System.out.println("    Age: " + age);
                    System.out.println("    City: " + city);
                }
            }
        }
    }
    
    /**
     * Demonstrates advanced CSV operations including:
     * - Handling quoted fields
     * - Data validation
     * - Converting data types
     * 
     * Python equivalent would use csv module with proper quoting
     */
    public static void advancedCSVOperations() {
        System.out.println("Advanced CSV Operations:");
        
        // Sample data with quoted fields
        String[] csvLines = {
            "Name,Age,City,Salary",
            "\"John Doe\",25,\"Sydney, NSW\",75000",
            "\"Jane Smith\",30,\"Melbourne, VIC\",82000",
            "\"Bob Johnson\",35,\"Brisbane, QLD\",68000"
        };
        
        System.out.println("Processing CSV with quoted fields:");
        
        for (String line : csvLines) {
            System.out.println("Original line: " + line);
            
            // Simple parsing (doesn't handle quoted commas properly)
            String[] simpleFields = line.split(",");
            System.out.println("  Simple split: " + Arrays.toString(simpleFields));
            
            // Better parsing for quoted fields
            List<String> parsedFields = parseQuotedCSV(line);
            System.out.println("  Parsed fields: " + parsedFields);
        }
        
        // Data validation example
        System.out.println("\nData validation example:");
        validateCSVData(csvLines);
    }
    
    /**
     * Parses CSV line handling quoted fields
     * This is a simplified version - production code would use a proper CSV library
     * 
     * @param line The CSV line to parse
     * @return List of parsed fields
     */
    public static List<String> parseQuotedCSV(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                // End of field
                fields.add(currentField.toString().trim());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        
        // Add the last field
        fields.add(currentField.toString().trim());
        
        return fields;
    }
    
    /**
     * Validates CSV data for common issues
     * 
     * @param lines Array of CSV lines to validate
     */
    public static void validateCSVData(String[] lines) {
        if (lines.length == 0) {
            System.out.println("Error: Empty CSV data");
            return;
        }
        
        // Check header
        String header = lines[0];
        String[] headerFields = header.split(",");
        System.out.println("Header fields: " + Arrays.toString(headerFields));
        
        // Validate data rows
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] fields = line.split(",");
            
            System.out.println("Row " + i + " validation:");
            
            // Check field count
            if (fields.length != headerFields.length) {
                System.out.println("  Warning: Field count mismatch. Expected " + 
                                 headerFields.length + ", got " + fields.length);
            }
            
            // Check for empty fields
            for (int j = 0; j < fields.length; j++) {
                if (fields[j].trim().isEmpty()) {
                    System.out.println("  Warning: Empty field at position " + j);
                }
            }
            
            // Try to parse age as integer
            if (fields.length > 1) {
                try {
                    int age = Integer.parseInt(fields[1].trim());
                    if (age < 0 || age > 150) {
                        System.out.println("  Warning: Age value seems invalid: " + age);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("  Error: Age is not a valid number: " + fields[1]);
                }
            }
        }
    }
} 