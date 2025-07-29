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
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Advanced {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced: Complex File Processing ===\n");
        
        // Create complex sample data
        createComplexSampleData();
        
        // Demonstrate advanced processing techniques
        demonstrateStreamProcessing();
        demonstrateBatchProcessing();
        demonstrateEncodingHandling();
        demonstrateFileStatistics();
        demonstrateErrorRecovery();
        
        System.out.println("\n=== Advanced examples completed! ===");
    }
    
    /**
     * Creates complex sample data files for advanced processing.
     * Python equivalent: creating multiple files with different content types
     */
    private static void createComplexSampleData() {
        System.out.println("📁 Creating complex sample data files...");
        
        try {
            // Create a large dataset file
            List<String> largeDataset = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                largeDataset.add(String.format("Record_%03d,Value_%d,Category_%d", i, i * 10, i % 5));
            }
            Files.write(Paths.get("data/large_dataset.csv"), largeDataset);
            
            // Create a log file with different log levels
            List<String> logEntries = Arrays.asList(
                "2024-01-15 10:30:15 INFO Application started",
                "2024-01-15 10:30:16 DEBUG Loading configuration",
                "2024-01-15 10:30:17 WARN Configuration file not found, using defaults",
                "2024-01-15 10:30:18 INFO Database connection established",
                "2024-01-15 10:30:19 ERROR Failed to load user preferences",
                "2024-01-15 10:30:20 INFO Processing completed successfully",
                "2024-01-15 10:30:21 DEBUG Memory usage: 45MB",
                "2024-01-15 10:30:22 INFO User session started",
                "2024-01-15 10:30:23 WARN High memory usage detected",
                "2024-01-15 10:30:24 ERROR Database connection timeout"
            );
            Files.write(Paths.get("data/application.log"), logEntries);
            
            // Create a configuration file with comments
            List<String> configLines = Arrays.asList(
                "# Application Configuration",
                "# Version: 2.1.0",
                "",
                "database.host=localhost",
                "database.port=5432",
                "database.name=myapp",
                "",
                "logging.level=INFO",
                "logging.file=app.log",
                "",
                "cache.enabled=true",
                "cache.size=1000",
                "",
                "# Feature flags",
                "feature.new_ui=true",
                "feature.analytics=false"
            );
            Files.write(Paths.get("data/config.properties"), configLines);
            
            System.out.println("✅ Complex sample data created successfully!");
            
        } catch (IOException e) {
            System.err.println("❌ Error creating complex sample data: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates stream-based file processing for large datasets.
     * Python equivalent: processing large files with generators
     */
    private static void demonstrateStreamProcessing() {
        System.out.println("\n🔄 Stream processing: data/large_dataset.csv");
        
        try (Stream<String> lines = Files.lines(Paths.get("data/large_dataset.csv"))) {
            
            // Process the stream to extract statistics
            List<String> processedData = lines
                .skip(1) // Skip header if present
                .filter(line -> !line.trim().isEmpty()) // Remove empty lines
                .map(line -> {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        return String.format("Processed: %s -> %s (%s)", 
                                          parts[0], parts[1], parts[2]);
                    }
                    return "Invalid line: " + line;
                })
                .collect(Collectors.toList());
            
            // Write processed data to output file
            Files.write(Paths.get("data/processed_dataset.txt"), processedData);
            
            System.out.println("✅ Stream processing completed! Processed " + processedData.size() + " records");
            
        } catch (IOException e) {
            System.err.println("❌ Error in stream processing: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates batch processing of multiple files.
     * Python equivalent: processing multiple files in a loop
     */
    private static void demonstrateBatchProcessing() {
        System.out.println("\n📦 Batch processing multiple files...");
        
        String[] filesToProcess = {"data/application.log", "data/config.properties"};
        
        for (String filePath : filesToProcess) {
            try {
                System.out.println("Processing: " + filePath);
                
                List<String> lines = Files.readAllLines(Paths.get(filePath));
                String outputPath = filePath.replace(".", "_processed.");
                
                // Process based on file type
                List<String> processedLines = new ArrayList<>();
                if (filePath.endsWith(".log")) {
                    // Process log file - extract error messages
                    processedLines = lines.stream()
                        .filter(line -> line.contains("ERROR"))
                        .map(line -> "ERROR FOUND: " + line)
                        .collect(Collectors.toList());
                } else if (filePath.endsWith(".properties")) {
                    // Process config file - extract non-comment lines
                    processedLines = lines.stream()
                        .filter(line -> !line.trim().startsWith("#") && !line.trim().isEmpty())
                        .map(line -> "CONFIG: " + line)
                        .collect(Collectors.toList());
                }
                
                Files.write(Paths.get(outputPath), processedLines);
                System.out.println("✅ Processed " + processedLines.size() + " lines -> " + outputPath);
                
            } catch (IOException e) {
                System.err.println("❌ Error processing " + filePath + ": " + e.getMessage());
            }
        }
    }
    
    /**
     * Demonstrates handling different character encodings.
     * Python equivalent: open(file, encoding='utf-8')
     */
    private static void demonstrateEncodingHandling() {
        System.out.println("\n🔤 Encoding handling demonstration...");
        
        try {
            // Create a file with specific encoding
            String content = "Hello World! 你好世界! Привет мир!";
            Files.write(Paths.get("data/multilingual.txt"), 
                      content.getBytes(StandardCharsets.UTF_8));
            
            // Read with explicit encoding
            String readContent = new String(
                Files.readAllBytes(Paths.get("data/multilingual.txt")), 
                StandardCharsets.UTF_8
            );
            
            System.out.println("Original: " + content);
            System.out.println("Read: " + readContent);
            System.out.println("✅ Encoding handled successfully!");
            
        } catch (IOException e) {
            System.err.println("❌ Error in encoding handling: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates file statistics and analysis.
     * Python equivalent: analyzing file content and structure
     */
    private static void demonstrateFileStatistics() {
        System.out.println("\n📊 File statistics analysis...");
        
        try {
            Path logFile = Paths.get("data/application.log");
            List<String> lines = Files.readAllLines(logFile);
            
            // Calculate statistics
            long totalLines = lines.size();
            long errorLines = lines.stream().filter(line -> line.contains("ERROR")).count();
            long warningLines = lines.stream().filter(line -> line.contains("WARN")).count();
            long infoLines = lines.stream().filter(line -> line.contains("INFO")).count();
            long debugLines = lines.stream().filter(line -> line.contains("DEBUG")).count();
            
            // Calculate average line length
            double avgLineLength = lines.stream()
                .mapToInt(String::length)
                .average()
                .orElse(0.0);
            
            // Write statistics to file
            List<String> stats = Arrays.asList(
                "=== FILE STATISTICS ===",
                "File: " + logFile.getFileName(),
                "Total lines: " + totalLines,
                "Error lines: " + errorLines,
                "Warning lines: " + warningLines,
                "Info lines: " + infoLines,
                "Debug lines: " + debugLines,
                "Average line length: " + String.format("%.2f", avgLineLength),
                "Error rate: " + String.format("%.1f%%", (errorLines * 100.0 / totalLines))
            );
            
            Files.write(Paths.get("data/file_statistics.txt"), stats);
            
            System.out.println("✅ File statistics calculated and saved!");
            
        } catch (IOException e) {
            System.err.println("❌ Error in file statistics: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates error recovery and robust file processing.
     * Python equivalent: try/except with fallback strategies
     */
    private static void demonstrateErrorRecovery() {
        System.out.println("\n🛡️ Error recovery demonstration...");
        
        // Try to process a non-existent file with recovery
        String[] filesToTry = {"data/nonexistent.txt", "data/application.log", "data/config.properties"};
        
        for (String filePath : filesToTry) {
            try {
                System.out.println("Attempting to process: " + filePath);
                
                List<String> lines = Files.readAllLines(Paths.get(filePath));
                System.out.println("✅ Successfully read " + lines.size() + " lines from " + filePath);
                
            } catch (NoSuchFileException e) {
                System.err.println("❌ File not found: " + filePath);
                System.out.println("🔄 Creating backup file...");
                
                try {
                    // Create a backup file with default content
                    List<String> backupContent = Arrays.asList(
                        "# Backup file created due to missing original",
                        "# Original file: " + filePath,
                        "# Created: " + new Date(),
                        "default.content=backup_value"
                    );
                    Files.write(Paths.get(filePath), backupContent);
                    System.out.println("✅ Backup file created: " + filePath);
                    
                } catch (IOException backupError) {
                    System.err.println("❌ Failed to create backup: " + backupError.getMessage());
                }
                
            } catch (IOException e) {
                System.err.println("❌ Error processing " + filePath + ": " + e.getMessage());
            }
        }
    }
} 