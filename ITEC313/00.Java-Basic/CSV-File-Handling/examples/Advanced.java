/**
 * Advanced.java
 * 
 * This program demonstrates advanced in Java:
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
 * Advanced: Advanced CSV Operations
 * 
 * This example demonstrates advanced CSV operations including:
 * - Data validation and cleaning
 * - Filtering and searching
 * - Data transformation
 * - Error handling for malformed data
 * 
 * Python equivalent would use pandas or similar libraries for advanced operations
 */
public class Advanced {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced: Advanced CSV Operations ===\n");
        
        // Example 1: Data validation and cleaning
        System.out.println("1. Data validation and cleaning:");
        validateAndCleanData();
        
        // Example 2: Filtering and searching
        System.out.println("\n2. Filtering and searching:");
        filterAndSearchData();
        
        // Example 3: Data transformation
        System.out.println("\n3. Data transformation:");
        transformData();
        
        // Example 4: Handling malformed CSV
        System.out.println("\n4. Handling malformed CSV:");
        handleMalformedCSV();
        
        System.out.println("\n=== Advanced example completed ===");
    }
    
    /**
     * Demonstrates data validation and cleaning operations
     * 
     * Python equivalent would use pandas for data cleaning
     */
    public static void validateAndCleanData() {
        // Sample data with various issues
        String[] csvLines = {
            "Name,Age,City,Salary",
            "John Doe,25,Sydney,75000",
            "Jane Smith,30,Melbourne,82000",
            "Bob Johnson,35,Brisbane,68000",
            "Alice Brown,28,Perth,72000",
            "Charlie Wilson,abc,Sydney,65000",  // Invalid age
            "Diana Davis,32,Melbourne,",        // Missing salary
            "Eve Johnson,29,Sydney,75000",
            "Frank Miller,45,Brisbane,95000",
            "Grace Lee,27,Melbourne,78000",
            "Henry Brown,33,Perth,82000"
        };
        
        System.out.println("Original data (with issues):");
        for (String line : csvLines) {
            System.out.println("  " + line);
        }
        
        System.out.println("\nValidating and cleaning data:");
        
        List<String> validRows = new ArrayList<>();
        List<String> invalidRows = new ArrayList<>();
        
        // Process each line (skip header)
        for (int i = 1; i < csvLines.length; i++) {
            String line = csvLines[i];
            String[] fields = line.split(",");
            
            boolean isValid = true;
            List<String> issues = new ArrayList<>();
            
            // Validate field count
            if (fields.length != 4) {
                isValid = false;
                issues.add("Incorrect number of fields");
            }
            
            // Validate name (should not be empty)
            if (fields.length > 0 && fields[0].trim().isEmpty()) {
                isValid = false;
                issues.add("Empty name");
            }
            
            // Validate age (should be a number between 0 and 150)
            if (fields.length > 1) {
                try {
                    int age = Integer.parseInt(fields[1].trim());
                    if (age < 0 || age > 150) {
                        isValid = false;
                        issues.add("Invalid age: " + age);
                    }
                } catch (NumberFormatException e) {
                    isValid = false;
                    issues.add("Age is not a number: " + fields[1]);
                }
            }
            
            // Validate city (should not be empty)
            if (fields.length > 2 && fields[2].trim().isEmpty()) {
                isValid = false;
                issues.add("Empty city");
            }
            
            // Validate salary (should be a positive number)
            if (fields.length > 3) {
                if (fields[3].trim().isEmpty()) {
                    isValid = false;
                    issues.add("Missing salary");
                } else {
                    try {
                        int salary = Integer.parseInt(fields[3].trim());
                        if (salary < 0) {
                            isValid = false;
                            issues.add("Negative salary: " + salary);
                        }
                    } catch (NumberFormatException e) {
                        isValid = false;
                        issues.add("Salary is not a number: " + fields[3]);
                    }
                }
            }
            
            if (isValid) {
                validRows.add(line);
                System.out.println("  ✓ Valid: " + line);
            } else {
                invalidRows.add(line);
                System.out.println("  ✗ Invalid: " + line);
                System.out.println("    Issues: " + String.join(", ", issues));
            }
        }
        
        System.out.println("\nSummary:");
        System.out.println("  Valid rows: " + validRows.size());
        System.out.println("  Invalid rows: " + invalidRows.size());
    }
    
    /**
     * Demonstrates filtering and searching operations on CSV data
     * 
     * Python equivalent would use pandas filtering
     */
    public static void filterAndSearchData() {
        // Sample data
        String[] csvLines = {
            "Name,Age,City,Salary",
            "John Doe,25,Sydney,75000",
            "Jane Smith,30,Melbourne,82000",
            "Bob Johnson,35,Brisbane,68000",
            "Alice Brown,28,Perth,72000",
            "Charlie Wilson,32,Sydney,65000",
            "Diana Davis,32,Melbourne,78000",
            "Eve Johnson,29,Sydney,75000",
            "Frank Miller,45,Brisbane,95000",
            "Grace Lee,27,Melbourne,78000",
            "Henry Brown,33,Perth,82000"
        };
        
        System.out.println("Original data:");
        for (String line : csvLines) {
            System.out.println("  " + line);
        }
        
        // Filter by city
        System.out.println("\nFiltering by city (Sydney):");
        List<String> sydneyEmployees = filterByCity(csvLines, "Sydney");
        for (String employee : sydneyEmployees) {
            System.out.println("  " + employee);
        }
        
        // Filter by age range
        System.out.println("\nFiltering by age range (25-35):");
        List<String> youngEmployees = filterByAgeRange(csvLines, 25, 35);
        for (String employee : youngEmployees) {
            System.out.println("  " + employee);
        }
        
        // Filter by salary threshold
        System.out.println("\nFiltering by salary (>75000):");
        List<String> highSalaryEmployees = filterBySalary(csvLines, 75000);
        for (String employee : highSalaryEmployees) {
            System.out.println("  " + employee);
        }
        
        // Search by name
        System.out.println("\nSearching by name (contains 'John'):");
        List<String> johnEmployees = searchByName(csvLines, "John");
        for (String employee : johnEmployees) {
            System.out.println("  " + employee);
        }
    }
    
    /**
     * Demonstrates data transformation operations
     * 
     * Python equivalent would use pandas for data transformation
     */
    public static void transformData() {
        // Sample data
        String[] csvLines = {
            "Name,Age,City,Salary",
            "John Doe,25,Sydney,75000",
            "Jane Smith,30,Melbourne,82000",
            "Bob Johnson,35,Brisbane,68000",
            "Alice Brown,28,Perth,72000",
            "Charlie Wilson,32,Sydney,65000"
        };
        
        System.out.println("Original data:");
        for (String line : csvLines) {
            System.out.println("  " + line);
        }
        
        // Transform: Add salary category
        System.out.println("\nTransforming: Adding salary category");
        List<String> transformedData = addSalaryCategory(csvLines);
        for (String line : transformedData) {
            System.out.println("  " + line);
        }
        
        // Transform: Calculate average salary by city
        System.out.println("\nTransforming: Average salary by city");
        Map<String, Double> avgSalaryByCity = calculateAverageSalaryByCity(csvLines);
        for (Map.Entry<String, Double> entry : avgSalaryByCity.entrySet()) {
            System.out.println("  " + entry.getKey() + ": $" + String.format("%.2f", entry.getValue()));
        }
        
        // Transform: Sort by salary (descending)
        System.out.println("\nTransforming: Sort by salary (descending)");
        List<String> sortedData = sortBySalary(csvLines);
        for (String line : sortedData) {
            System.out.println("  " + line);
        }
    }
    
    /**
     * Demonstrates handling malformed CSV data
     * 
     * Python equivalent would use pandas with error handling
     */
    public static void handleMalformedCSV() {
        // Sample malformed CSV data
        String[] malformedLines = {
            "Name,Age,City,Salary",
            "John Doe,25,Sydney,75000",
            "Jane Smith,30,\"Melbourne, VIC\",82000",  // Quoted field with comma
            "Bob Johnson,35,Brisbane,68000",
            "Alice Brown,28,Perth,72000",
            "Charlie Wilson,32,Sydney,65000",
            "Diana Davis,32,Melbourne,78000",
            "Eve Johnson,29,Sydney,75000",
            "Frank Miller,45,Brisbane,95000",
            "Grace Lee,27,Melbourne,78000",
            "Henry Brown,33,Perth,82000"
        };
        
        System.out.println("Malformed CSV data:");
        for (String line : malformedLines) {
            System.out.println("  " + line);
        }
        
        System.out.println("\nParsing with different methods:");
        
        // Method 1: Simple split (fails with quoted fields)
        System.out.println("\nMethod 1: Simple split (fails with quoted fields)");
        for (int i = 1; i < malformedLines.length; i++) {
            String line = malformedLines[i];
            String[] fields = line.split(",");
            System.out.println("  Line " + i + ": " + Arrays.toString(fields));
        }
        
        // Method 2: Proper CSV parsing
        System.out.println("\nMethod 2: Proper CSV parsing (handles quoted fields)");
        for (int i = 1; i < malformedLines.length; i++) {
            String line = malformedLines[i];
            List<String> fields = parseQuotedCSV(line);
            System.out.println("  Line " + i + ": " + fields);
        }
    }
    
    // Helper methods for filtering and searching
    
    private static List<String> filterByCity(String[] csvLines, String city) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i < csvLines.length; i++) {
            String[] fields = csvLines[i].split(",");
            if (fields.length > 2 && fields[2].trim().equalsIgnoreCase(city)) {
                result.add(csvLines[i]);
            }
        }
        return result;
    }
    
    private static List<String> filterByAgeRange(String[] csvLines, int minAge, int maxAge) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i < csvLines.length; i++) {
            String[] fields = csvLines[i].split(",");
            if (fields.length > 1) {
                try {
                    int age = Integer.parseInt(fields[1].trim());
                    if (age >= minAge && age <= maxAge) {
                        result.add(csvLines[i]);
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid age
                }
            }
        }
        return result;
    }
    
    private static List<String> filterBySalary(String[] csvLines, int minSalary) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i < csvLines.length; i++) {
            String[] fields = csvLines[i].split(",");
            if (fields.length > 3) {
                try {
                    int salary = Integer.parseInt(fields[3].trim());
                    if (salary > minSalary) {
                        result.add(csvLines[i]);
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid salary
                }
            }
        }
        return result;
    }
    
    private static List<String> searchByName(String[] csvLines, String searchTerm) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i < csvLines.length; i++) {
            String[] fields = csvLines[i].split(",");
            if (fields.length > 0 && fields[0].toLowerCase().contains(searchTerm.toLowerCase())) {
                result.add(csvLines[i]);
            }
        }
        return result;
    }
    
    // Helper methods for data transformation
    
    private static List<String> addSalaryCategory(String[] csvLines) {
        List<String> result = new ArrayList<>();
        result.add(csvLines[0] + ",Category"); // Add header
        
        for (int i = 1; i < csvLines.length; i++) {
            String[] fields = csvLines[i].split(",");
            if (fields.length > 3) {
                try {
                    int salary = Integer.parseInt(fields[3].trim());
                    String category;
                    if (salary < 70000) {
                        category = "Low";
                    } else if (salary < 80000) {
                        category = "Medium";
                    } else {
                        category = "High";
                    }
                    result.add(csvLines[i] + "," + category);
                } catch (NumberFormatException e) {
                    result.add(csvLines[i] + ",Unknown");
                }
            }
        }
        return result;
    }
    
    private static Map<String, Double> calculateAverageSalaryByCity(String[] csvLines) {
        Map<String, List<Integer>> salariesByCity = new HashMap<>();
        
        for (int i = 1; i < csvLines.length; i++) {
            String[] fields = csvLines[i].split(",");
            if (fields.length > 3) {
                try {
                    String city = fields[2].trim();
                    int salary = Integer.parseInt(fields[3].trim());
                    
                    salariesByCity.computeIfAbsent(city, k -> new ArrayList<>()).add(salary);
                } catch (NumberFormatException e) {
                    // Skip invalid salary
                }
            }
        }
        
        Map<String, Double> result = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : salariesByCity.entrySet()) {
            String city = entry.getKey();
            List<Integer> salaries = entry.getValue();
            
            double average = salaries.stream()
                    .mapToInt(Integer::intValue)
                    .average()
                    .orElse(0.0);
            
            result.put(city, average);
        }
        
        return result;
    }
    
    private static List<String> sortBySalary(String[] csvLines) {
        List<String> dataRows = new ArrayList<>();
        String header = csvLines[0];
        
        for (int i = 1; i < csvLines.length; i++) {
            dataRows.add(csvLines[i]);
        }
        
        // Sort by salary (descending)
        dataRows.sort((a, b) -> {
            String[] fieldsA = a.split(",");
            String[] fieldsB = b.split(",");
            
            try {
                int salaryA = Integer.parseInt(fieldsA[3].trim());
                int salaryB = Integer.parseInt(fieldsB[3].trim());
                return Integer.compare(salaryB, salaryA); // Descending
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                return 0;
            }
        });
        
        List<String> result = new ArrayList<>();
        result.add(header);
        result.addAll(dataRows);
        return result;
    }
    
    // Helper method for parsing quoted CSV
    private static List<String> parseQuotedCSV(String line) {
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
} 