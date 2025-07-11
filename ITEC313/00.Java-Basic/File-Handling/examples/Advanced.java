/**
 * Advanced.java - Advanced File Handling
 * 
 * This example demonstrates advanced file handling concepts in Java.
 * Includes file operations, directory handling, and complex error handling.
 * 
 * Key differences from Python:
 * - Java has separate classes for different file operations
 * - More explicit resource management required
 * - Different approach to directory operations
 * - More structured exception handling
 * 
 * @author ITEC313 - Object-Oriented Programming
 */

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Advanced {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced File Handling Examples ===\n");
        
        // Advanced 1: File and directory operations
        demonstrateFileOperations();
        
        // Advanced 2: Working with different file types
        demonstrateFileTypes();
        
        // Advanced 3: Complex error handling
        demonstrateComplexErrorHandling();
        
        // Advanced 4: File copying and moving
        demonstrateFileCopying();
        
        System.out.println("=== Advanced Examples Complete ===\n");
    }
    
    /**
     * Demonstrates various file and directory operations
     * In Python: os.path operations, os.listdir(), etc.
     */
    public static void demonstrateFileOperations() {
        System.out.println("Advanced 1: File and Directory Operations:");
        System.out.println("------------------------------------------");
        
        // Create a test directory
        String testDir = "data/test_directory";
        File directory = new File(testDir);
        
        try {
            // Create directory if it doesn't exist
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                System.out.println("Created directory: " + (created ? "success" : "failed"));
            } else {
                System.out.println("Directory already exists: " + testDir);
            }
            
            // Create some test files in the directory
            String[] testFiles = {"file1.txt", "file2.txt", "file3.txt"};
            for (String filename : testFiles) {
                File testFile = new File(testDir, filename);
                FileWriter writer = new FileWriter(testFile);
                writer.write("Content of " + filename + "\n");
                writer.write("Created at: " + new Date() + "\n");
                writer.close();
                System.out.println("Created: " + testFile.getPath());
            }
            
            // List directory contents
            System.out.println("\nDirectory contents of " + testDir + ":");
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    System.out.println("  - " + file.getName() + 
                                     " (" + file.length() + " bytes, " +
                                     (file.isDirectory() ? "dir" : "file") + ")");
                }
            }
            
            // Get file statistics
            System.out.println("\nFile statistics:");
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println("  " + file.getName() + ":");
                    System.out.println("    Size: " + file.length() + " bytes");
                    System.out.println("    Readable: " + file.canRead());
                    System.out.println("    Writable: " + file.canWrite());
                    System.out.println("    Last modified: " + new Date(file.lastModified()));
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error in file operations: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Demonstrates working with different file types and formats
     * In Python: Different file modes and encodings
     */
    public static void demonstrateFileTypes() {
        System.out.println("Advanced 2: Working with Different File Types:");
        System.out.println("----------------------------------------------");
        
        try {
            // Create a binary file (simulating image data)
            String binaryFile = "data/sample.bin";
            FileOutputStream binaryOut = new FileOutputStream(binaryFile);
            
            // Write some binary data
            byte[] data = {0x48, 0x65, 0x6C, 0x6C, 0x6F}; // "Hello" in ASCII
            binaryOut.write(data);
            binaryOut.write(0x0A); // New line
            binaryOut.write("World".getBytes());
            binaryOut.close();
            
            System.out.println("Created binary file: " + binaryFile);
            
            // Read binary file
            FileInputStream binaryIn = new FileInputStream(binaryFile);
            byte[] readData = new byte[100];
            int bytesRead = binaryIn.read(readData);
            binaryIn.close();
            
            System.out.println("Binary file content (hex):");
            for (int i = 0; i < bytesRead; i++) {
                System.out.printf("%02X ", readData[i]);
            }
            System.out.println();
            
            // Create a properties file
            String propsFile = "data/config.properties";
            Properties props = new Properties();
            props.setProperty("database.url", "localhost:3306");
            props.setProperty("database.user", "admin");
            props.setProperty("database.password", "secret");
            props.setProperty("app.version", "1.0.0");
            
            FileOutputStream propsOut = new FileOutputStream(propsFile);
            props.store(propsOut, "Application Configuration");
            propsOut.close();
            
            System.out.println("Created properties file: " + propsFile);
            
            // Read properties file
            Properties readProps = new Properties();
            FileInputStream propsIn = new FileInputStream(propsFile);
            readProps.load(propsIn);
            propsIn.close();
            
            System.out.println("Properties file content:");
            for (String key : readProps.stringPropertyNames()) {
                System.out.println("  " + key + " = " + readProps.getProperty(key));
            }
            
        } catch (IOException e) {
            System.err.println("Error with file types: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Demonstrates complex error handling for file operations
     * In Python: try/except with multiple exception types
     */
    public static void demonstrateComplexErrorHandling() {
        System.out.println("Advanced 3: Complex Error Handling:");
        System.out.println("-----------------------------------");
        
        String[] testFiles = {
            "data/existing_file.txt",
            "data/nonexistent_file.txt",
            "/root/protected_file.txt",
            "data/readonly_file.txt"
        };
        
        for (String filename : testFiles) {
            System.out.println("Testing file: " + filename);
            
            try {
                // Try to read the file
                File file = new File(filename);
                
                if (!file.exists()) {
                    throw new FileNotFoundException("File does not exist: " + filename);
                }
                
                if (!file.canRead()) {
                    throw new SecurityException("Cannot read file: " + filename);
                }
                
                // Try to read the file
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                
                String line = bufferedReader.readLine();
                System.out.println("  Successfully read: " + (line != null ? line.substring(0, Math.min(50, line.length())) : "empty file"));
                
                bufferedReader.close();
                reader.close();
                
            } catch (FileNotFoundException e) {
                System.err.println("  File not found: " + e.getMessage());
            } catch (SecurityException e) {
                System.err.println("  Security error: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("  IO error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("  Unexpected error: " + e.getMessage());
            }
        }
        
        // Demonstrate custom error handling
        System.out.println("\nCustom error handling example:");
        try {
            processFileWithCustomHandling("data/input.txt");
        } catch (CustomFileException e) {
            System.err.println("Custom error: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Custom method with specific error handling
     */
    public static void processFileWithCustomHandling(String filename) throws CustomFileException {
        File file = new File(filename);
        
        if (!file.exists()) {
            throw new CustomFileException("File not found: " + filename);
        }
        
        if (file.length() == 0) {
            throw new CustomFileException("File is empty: " + filename);
        }
        
        // Process the file
        System.out.println("  Processing file: " + filename);
    }
    
    /**
     * Demonstrates file copying and moving operations
     * In Python: shutil.copy(), shutil.move()
     */
    public static void demonstrateFileCopying() {
        System.out.println("Advanced 4: File Copying and Moving:");
        System.out.println("-------------------------------------");
        
        try {
            // Create a source file
            String sourceFile = "data/source.txt";
            FileWriter sourceWriter = new FileWriter(sourceFile);
            sourceWriter.write("This is the source file content.\n");
            sourceWriter.write("It will be copied to another location.\n");
            sourceWriter.close();
            
            System.out.println("Created source file: " + sourceFile);
            
            // Copy file using Files.copy (Java 7+)
            String destFile = "data/destination.txt";
            Path sourcePath = Paths.get(sourceFile);
            Path destPath = Paths.get(destFile);
            
            Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copied file to: " + destFile);
            
            // Verify copy
            String copiedContent = new String(Files.readAllBytes(destPath));
            System.out.println("Copied content: " + copiedContent.trim());
            
            // Move file to a new location
            String movedFile = "data/moved_file.txt";
            Path movedPath = Paths.get(movedFile);
            
            Files.move(destPath, movedPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved file to: " + movedFile);
            
            // Check if original destination is gone
            if (!Files.exists(destPath)) {
                System.out.println("Original destination file was moved successfully");
            }
            
            // Clean up
            Files.deleteIfExists(sourcePath);
            Files.deleteIfExists(movedPath);
            System.out.println("Cleaned up temporary files");
            
        } catch (IOException e) {
            System.err.println("Error in file copying: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Custom exception class for file handling
     */
    public static class CustomFileException extends Exception {
        public CustomFileException(String message) {
            super(message);
        }
    }
} 