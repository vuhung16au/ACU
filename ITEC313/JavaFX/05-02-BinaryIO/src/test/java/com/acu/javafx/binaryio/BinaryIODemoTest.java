package com.acu.javafx.binaryio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Test class for Binary I/O Demo application
 * 
 * This class tests the core I/O functionality demonstrated in the application
 */
public class BinaryIODemoTest {

    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("binaryio-test");
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up temporary files
        if (tempDir != null && Files.exists(tempDir)) {
            Files.walk(tempDir)
                .sorted((a, b) -> b.compareTo(a)) // Delete files before directories
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        // Ignore cleanup errors
                    }
                });
        }
    }

    @Test
    void testFileInputStreamFileOutputStream() throws IOException {
        // Test basic byte I/O
        File testFile = tempDir.resolve("test.dat").toFile();
        
        // Write bytes
        try (FileOutputStream output = new FileOutputStream(testFile)) {
            for (int i = 1; i <= 10; i++) {
                output.write(i);
            }
        }
        
        // Verify file exists and has correct size
        assertTrue(testFile.exists());
        assertEquals(10, testFile.length());
        
        // Read bytes
        try (FileInputStream input = new FileInputStream(testFile)) {
            for (int i = 1; i <= 10; i++) {
                int value = input.read();
                assertEquals(i, value);
            }
            assertEquals(-1, input.read()); // End of file
        }
    }

    @Test
    void testDataInputStreamDataOutputStream() throws IOException {
        // Test primitive type I/O
        File testFile = tempDir.resolve("data.dat").toFile();
        
        // Write primitive types
        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(testFile))) {
            output.writeUTF("John");
            output.writeDouble(85.5);
            output.writeInt(25);
        }
        
        // Read primitive types
        try (DataInputStream input = new DataInputStream(new FileInputStream(testFile))) {
            String name = input.readUTF();
            double score = input.readDouble();
            int age = input.readInt();
            
            assertEquals("John", name);
            assertEquals(85.5, score, 0.001);
            assertEquals(25, age);
        }
    }

    @Test
    void testObjectSerialization() throws IOException, ClassNotFoundException {
        // Test object serialization
        File testFile = tempDir.resolve("object.dat").toFile();
        
        // Create and write object
        BinaryIODemo.Student student = new BinaryIODemo.Student("John", 85.5);
        
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(testFile))) {
            output.writeObject(student);
        }
        
        // Read object
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(testFile))) {
            BinaryIODemo.Student readStudent = (BinaryIODemo.Student) input.readObject();
            
            assertTrue(readStudent.toString().contains("John"));
            assertTrue(readStudent.toString().contains("85.5"));
        }
    }

    @Test
    void testRandomAccessFile() throws IOException {
        // Test random access file operations
        File testFile = tempDir.resolve("random.dat").toFile();
        
        // Write data
        try (RandomAccessFile raf = new RandomAccessFile(testFile, "rw")) {
            raf.writeDouble(4.5);
            raf.writeDouble(43.25);
            raf.writeDouble(3.2);
        }
        
        // Read at specific positions
        try (RandomAccessFile raf = new RandomAccessFile(testFile, "r")) {
            raf.seek(0);
            assertEquals(4.5, raf.readDouble(), 0.001);
            
            raf.seek(8);
            assertEquals(43.25, raf.readDouble(), 0.001);
            
            raf.seek(16);
            assertEquals(3.2, raf.readDouble(), 0.001);
        }
    }

    @Test
    void testFileCopy() throws IOException {
        // Test file copying
        File sourceFile = tempDir.resolve("source.dat").toFile();
        File targetFile = tempDir.resolve("target.dat").toFile();
        
        // Create source file
        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(sourceFile))) {
            for (int i = 0; i < 10; i++) {
                output.writeInt(i);
            }
        }
        
        // Copy file
        try (FileInputStream input = new FileInputStream(sourceFile);
             FileOutputStream output = new FileOutputStream(targetFile)) {
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }
        
        // Verify copy
        assertTrue(targetFile.exists());
        assertEquals(sourceFile.length(), targetFile.length());
        
        // Verify content
        try (DataInputStream sourceInput = new DataInputStream(new FileInputStream(sourceFile));
             DataInputStream targetInput = new DataInputStream(new FileInputStream(targetFile))) {
            
            for (int i = 0; i < 10; i++) {
                assertEquals(sourceInput.readInt(), targetInput.readInt());
            }
        }
    }

    @Test
    void testStudentClass() {
        // Test Student class functionality
        BinaryIODemo.Student student = new BinaryIODemo.Student("Alice", 92.5);
        
        String toString = student.toString();
        assertTrue(toString.contains("Alice"));
        assertTrue(toString.contains("92.5"));
        assertTrue(toString.contains("Student"));
    }

    @Test
    void testErrorHandling() {
        // Test error handling for non-existent file
        File nonExistentFile = tempDir.resolve("nonexistent.dat").toFile();
        
        assertThrows(FileNotFoundException.class, () -> {
            try (FileInputStream input = new FileInputStream(nonExistentFile)) {
                // This should throw FileNotFoundException
            }
        });
    }
} 