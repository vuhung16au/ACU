/**
 * Advanced.java - Advanced I/O Techniques
 * 
 * This example demonstrates advanced input/output techniques including:
 * - Object serialization and deserialization
 * - Binary file I/O operations
 * - Advanced error handling and recovery
 * - Custom input validation
 * - Performance optimization techniques
 * 
 * Key differences from Python:
 * - Java serialization is more complex than Python's pickle
 * - Binary I/O requires explicit byte handling
 * - Exception handling is more structured in Java
 * - Resource management is more explicit
 * 
 * Course: ITEC313 - Object-Oriented Programming
 * Institution: Australian Catholic University (ACU)
 * Date: July 11, 2025
 */

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

public class Advanced {
    
    // Sample data class for serialization demonstration
    public static class Student implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private int age;
        private double gpa;
        private List<String> courses;
        
        public Student(String name, int age, double gpa) {
            this.name = name;
            this.age = age;
            this.gpa = gpa;
            this.courses = new ArrayList<>();
        }
        
        public void addCourse(String course) {
            courses.add(course);
        }
        
        @Override
        public String toString() {
            return String.format("Student{name='%s', age=%d, gpa=%.2f, courses=%s}", 
                               name, age, gpa, courses);
        }
        
        // Getters and setters
        public String getName() { return name; }
        public int getAge() { return age; }
        public double getGpa() { return gpa; }
        public List<String> getCourses() { return new ArrayList<>(courses); }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Advanced I/O Techniques ===\n");
        
        // Example 1: Object serialization
        demonstrateSerialization();
        
        // Example 2: Binary file operations
        demonstrateBinaryIO();
        
        // Example 3: Advanced error handling
        demonstrateAdvancedErrorHandling();
        
        // Example 4: Custom input validation
        demonstrateCustomValidation();
        
        // Example 5: Performance optimization
        demonstratePerformanceOptimization();
        
        System.out.println("\n=== Advanced Examples Complete ===");
    }
    
    /**
     * Demonstrate object serialization and deserialization
     * Shows how to save and load objects to/from files
     */
    public static void demonstrateSerialization() {
        System.out.println("1. OBJECT SERIALIZATION");
        System.out.println("=======================");
        
        // Create sample student objects
        Student student1 = new Student("Alice Johnson", 20, 3.85);
        student1.addCourse("Java Programming");
        student1.addCourse("Data Structures");
        
        Student student2 = new Student("Bob Smith", 22, 3.92);
        student2.addCourse("Algorithms");
        student2.addCourse("Database Systems");
        
        List<Student> students = Arrays.asList(student1, student2);
        
        // Serialize objects to file
        System.out.println("Serializing students to file...");
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/students.ser"))) {
            
            oos.writeObject(students);
            System.out.println("Students serialized successfully");
            
        } catch (IOException e) {
            System.err.println("Error serializing objects: " + e.getMessage());
        }
        
        // Deserialize objects from file
        System.out.println("\nDeserializing students from file...");
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("data/students.ser"))) {
            
            @SuppressWarnings("unchecked")
            List<Student> loadedStudents = (List<Student>) ois.readObject();
            
            System.out.println("Students deserialized successfully:");
            for (Student student : loadedStudents) {
                System.out.println("  " + student);
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deserializing objects: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrate binary file I/O operations
     * Shows how to read and write binary data
     */
    public static void demonstrateBinaryIO() {
        System.out.println("2. BINARY FILE I/O");
        System.out.println("===================");
        
        // Write binary data
        System.out.println("Writing binary data...");
        try (DataOutputStream dos = new DataOutputStream(
                new FileOutputStream("data/sample.bin"))) {
            
            // Write different data types
            dos.writeInt(42);
            dos.writeDouble(3.14159);
            dos.writeBoolean(true);
            dos.writeUTF("Hello, Binary World!");
            dos.writeLong(123456789L);
            
            System.out.println("Binary data written successfully");
            
        } catch (IOException e) {
            System.err.println("Error writing binary data: " + e.getMessage());
        }
        
        // Read binary data
        System.out.println("\nReading binary data...");
        try (DataInputStream dis = new DataInputStream(
                new FileInputStream("data/sample.bin"))) {
            
            // Read data in the same order it was written
            int intValue = dis.readInt();
            double doubleValue = dis.readDouble();
            boolean boolValue = dis.readBoolean();
            String stringValue = dis.readUTF();
            long longValue = dis.readLong();
            
            System.out.println("Binary data read successfully:");
            System.out.printf("  Integer: %d%n", intValue);
            System.out.printf("  Double: %.5f%n", doubleValue);
            System.out.printf("  Boolean: %b%n", boolValue);
            System.out.printf("  String: \"%s\"%n", stringValue);
            System.out.printf("  Long: %d%n", longValue);
            
        } catch (IOException e) {
            System.err.println("Error reading binary data: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrate advanced error handling techniques
     * Shows sophisticated error recovery and logging
     */
    public static void demonstrateAdvancedErrorHandling() {
        System.out.println("3. ADVANCED ERROR HANDLING");
        System.out.println("===========================");
        
        // Example 1: Retry mechanism
        System.out.println("--- Example 1: Retry mechanism ---");
        String filename = "data/input.txt";
        int maxRetries = 3;
        
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                String content = readFileWithTimeout(filename, 1000); // 1 second timeout
                System.out.printf("Successfully read file on attempt %d%n", attempt);
                System.out.printf("Content length: %d characters%n", content.length());
                break;
                
            } catch (IOException e) {
                System.err.printf("Attempt %d failed: %s%n", attempt, e.getMessage());
                if (attempt == maxRetries) {
                    System.err.println("All retry attempts failed");
                } else {
                    try {
                        Thread.sleep(1000); // Wait 1 second before retry
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
        
        // Example 2: Resource cleanup with try-with-resources
        System.out.println("\n--- Example 2: Resource cleanup ---");
        try (BufferedReader reader = new BufferedReader(new FileReader("data/input.txt"));
             PrintWriter writer = new PrintWriter(new FileWriter("data/processed.txt"))) {
            
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                String processedLine = processLine(line);
                writer.println(processedLine);
            }
            System.out.printf("Processed %d lines successfully%n", lineCount);
            
        } catch (IOException e) {
            System.err.println("Error during file processing: " + e.getMessage());
        }
        
        // Example 3: Custom exception handling
        System.out.println("\n--- Example 3: Custom exceptions ---");
        try {
            validateFileFormat("data/sample.csv");
            System.out.println("File format validation passed");
        } catch (InvalidFileFormatException e) {
            System.err.println("File format error: " + e.getMessage());
            System.err.println("Expected format: " + e.getExpectedFormat());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrate custom input validation techniques
     * Shows sophisticated validation patterns
     */
    public static void demonstrateCustomValidation() {
        System.out.println("4. CUSTOM INPUT VALIDATION");
        System.out.println("==========================");
        
        // Example 1: Email validation
        System.out.println("--- Example 1: Email validation ---");
        String[] testEmails = {
            "user@example.com",
            "invalid-email",
            "test@domain",
            "user.name@company.co.uk"
        };
        
        for (String email : testEmails) {
            boolean isValid = validateEmail(email);
            System.out.printf("Email '%s': %s%n", email, isValid ? "VALID" : "INVALID");
        }
        
        // Example 2: Phone number validation
        System.out.println("\n--- Example 2: Phone number validation ---");
        String[] testPhones = {
            "+1-555-123-4567",
            "555-123-4567",
            "123-456-7890",
            "invalid-phone"
        };
        
        for (String phone : testPhones) {
            boolean isValid = validatePhoneNumber(phone);
            System.out.printf("Phone '%s': %s%n", phone, isValid ? "VALID" : "INVALID");
        }
        
        // Example 3: Credit card validation (Luhn algorithm)
        System.out.println("\n--- Example 3: Credit card validation ---");
        String[] testCards = {
            "4532015112830366", // Valid Visa
            "4532015112830367", // Invalid
            "1234567890123456"  // Invalid
        };
        
        for (String card : testCards) {
            boolean isValid = validateCreditCard(card);
            System.out.printf("Card '%s': %s%n", card, isValid ? "VALID" : "INVALID");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrate performance optimization techniques
     * Shows efficient I/O patterns and buffering strategies
     */
    public static void demonstratePerformanceOptimization() {
        System.out.println("5. PERFORMANCE OPTIMIZATION");
        System.out.println("============================");
        
        // Example 1: Buffered I/O performance
        System.out.println("--- Example 1: Buffered vs unbuffered I/O ---");
        
        // Create a large test file
        createLargeTestFile("data/large_test.txt", 10000);
        
        // Time buffered reading
        long startTime = System.currentTimeMillis();
        readWithBuffering("data/large_test.txt");
        long bufferedTime = System.currentTimeMillis() - startTime;
        
        // Time unbuffered reading
        startTime = System.currentTimeMillis();
        readWithoutBuffering("data/large_test.txt");
        long unbufferedTime = System.currentTimeMillis() - startTime;
        
        System.out.printf("Buffered reading time: %d ms%n", bufferedTime);
        System.out.printf("Unbuffered reading time: %d ms%n", unbufferedTime);
        System.out.printf("Performance improvement: %.1fx%n", 
                         (double) unbufferedTime / bufferedTime);
        
        // Example 2: StringBuilder for string concatenation
        System.out.println("\n--- Example 2: StringBuilder performance ---");
        
        startTime = System.currentTimeMillis();
        String result1 = concatenateWithString(1000);
        long stringTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        String result2 = concatenateWithStringBuilder(1000);
        long builderTime = System.currentTimeMillis() - startTime;
        
        System.out.printf("String concatenation time: %d ms%n", stringTime);
        System.out.printf("StringBuilder time: %d ms%n", builderTime);
        System.out.printf("Performance improvement: %.1fx%n", 
                         (double) stringTime / builderTime);
        
        System.out.println();
    }
    
    // Helper methods for advanced examples
    
    private static String readFileWithTimeout(String filename, int timeoutMs) throws IOException {
        // Simulate file reading with potential timeout
        try {
            Thread.sleep(100); // Simulate I/O delay
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Operation interrupted");
        }
    }
    
    private static String processLine(String line) {
        // Simple line processing: trim and capitalize
        return line.trim().toUpperCase();
    }
    
    private static void validateFileFormat(String filename) throws InvalidFileFormatException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String firstLine = reader.readLine();
            if (firstLine == null || !firstLine.contains(",")) {
                throw new InvalidFileFormatException("File must contain comma-separated values", "CSV");
            }
        } catch (IOException e) {
            throw new InvalidFileFormatException("Cannot read file: " + e.getMessage(), "CSV");
        }
    }
    
    private static boolean validateEmail(String email) {
        // Simple email validation regex
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
    
    private static boolean validatePhoneNumber(String phone) {
        // Simple phone validation regex
        String phoneRegex = "^\\+?1?[-.\\s]?\\(?[0-9]{3}\\)?[-.\\s]?[0-9]{3}[-.\\s]?[0-9]{4}$";
        return phone.matches(phoneRegex);
    }
    
    private static boolean validateCreditCard(String cardNumber) {
        // Luhn algorithm implementation
        if (cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 19) {
            return false;
        }
        
        int sum = 0;
        boolean alternate = false;
        
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        
        return (sum % 10 == 0);
    }
    
    private static void createLargeTestFile(String filename, int lines) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int i = 0; i < lines; i++) {
                writer.printf("Line %d: This is a test line with some content.%n", i);
            }
        } catch (IOException e) {
            System.err.println("Error creating test file: " + e.getMessage());
        }
    }
    
    private static void readWithBuffering(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error reading with buffering: " + e.getMessage());
        }
    }
    
    private static void readWithoutBuffering(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            int character;
            int count = 0;
            while ((character = reader.read()) != -1) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error reading without buffering: " + e.getMessage());
        }
    }
    
    private static String concatenateWithString(int iterations) {
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += "iteration " + i + " ";
        }
        return result;
    }
    
    private static String concatenateWithStringBuilder(int iterations) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            result.append("iteration ").append(i).append(" ");
        }
        return result.toString();
    }
    
    // Custom exception class
    public static class InvalidFileFormatException extends Exception {
        private final String expectedFormat;
        
        public InvalidFileFormatException(String message, String expectedFormat) {
            super(message);
            this.expectedFormat = expectedFormat;
        }
        
        public String getExpectedFormat() {
            return expectedFormat;
        }
    }
} 