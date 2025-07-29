package com.acu.javafx.binaryio;

import java.io.*;
import java.util.Scanner;

/**
 * CLI version of Binary I/O Demonstration
 * This application demonstrates various binary I/O operations in Java
 */
public class BinaryIOCLIDemo {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Binary I/O Demonstration (CLI Version) ===");
        System.out.println("This application demonstrates various binary I/O operations in Java.\n");
        
        while (true) {
            displayMenu();
            int choice = getMenuChoice();
            
            switch (choice) {
                case 1:
                    runTestFileStream();
                    break;
                case 2:
                    runTestDataStream();
                    break;
                case 3:
                    runCopy();
                    break;
                case 4:
                    runTestObjectOutputStream();
                    break;
                case 5:
                    runTestObjectInputStream();
                    break;
                case 6:
                    runTestObjectStreamForArray();
                    break;
                case 7:
                    runTestRandomAccessFile();
                    break;
                case 8:
                    runAllDemos();
                    break;
                case 9:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
            
            System.out.println("\nPress Enter to continue...");
            try {
                scanner.nextLine();
            } catch (Exception e) {
                // Handle case where input is not available (e.g., in automated testing)
                System.out.println("Continuing...");
            }
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n=== Available Demonstrations ===");
        System.out.println("1. Test File Stream");
        System.out.println("2. Test Data Stream");
        System.out.println("3. Copy File");
        System.out.println("4. Test Object Output Stream");
        System.out.println("5. Test Object Input Stream");
        System.out.println("6. Test Object Stream for Array");
        System.out.println("7. Test Random Access File");
        System.out.println("8. Run All Demonstrations");
        System.out.println("9. Exit");
        System.out.print("\nEnter your choice (1-9): ");
    }
    
    private static int getMenuChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number (1-9): ");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return choice;
    }
    
    private static void runTestFileStream() {
        System.out.println("\n=== Test File Stream ===");
        try {
            // Create output stream and write data
            try (FileOutputStream output = new FileOutputStream("temp.dat")) {
                for (int i = 1; i <= 10; i++) {
                    output.write(i);
                }
                System.out.println("✓ Written values 1-10 to temp.dat");
            }
            
            // Create input stream and read data
            try (FileInputStream input = new FileInputStream("temp.dat")) {
                System.out.print("✓ Reading from temp.dat: ");
                int value;
                while ((value = input.read()) != -1) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void runTestDataStream() {
        System.out.println("\n=== Test Data Stream ===");
        try {
            // Write data using DataOutputStream
            try (DataOutputStream output = new DataOutputStream(new FileOutputStream("temp.dat"))) {
                output.writeUTF("Liam");
                output.writeDouble(85.5);
                output.writeUTF("Susan");
                output.writeDouble(185.5);
                output.writeUTF("Chandra");
                output.writeDouble(105.25);
                System.out.println("✓ Written student data to temp.dat");
            }
            
            // Read data using DataInputStream
            try (DataInputStream input = new DataInputStream(new FileInputStream("temp.dat"))) {
                System.out.println("✓ Reading student data:");
                System.out.println("  " + input.readUTF() + " " + input.readDouble());
                System.out.println("  " + input.readUTF() + " " + input.readDouble());
                System.out.println("  " + input.readUTF() + " " + input.readDouble());
            }
        } catch (IOException e) {
            System.err.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void runCopy() {
        System.out.println("\n=== Copy File ===");
        try {
            // Create a test file to copy
            try (FileOutputStream testOutput = new FileOutputStream("source.txt")) {
                testOutput.write("This is a test file for copying.".getBytes());
                System.out.println("✓ Created source.txt");
            }
            
            // Perform the copy operation
            try (BufferedInputStream input = new BufferedInputStream(new FileInputStream("source.txt"));
                 BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream("target.txt"))) {
                
                int r, numberOfBytesCopied = 0;
                while ((r = input.read()) != -1) {
                    output.write((byte)r);
                    numberOfBytesCopied++;
                }
                System.out.println("✓ " + numberOfBytesCopied + " bytes copied from source.txt to target.txt");
            }
        } catch (IOException e) {
            System.err.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void runTestObjectOutputStream() {
        System.out.println("\n=== Test Object Output Stream ===");
        try {
            try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("object.dat"))) {
                output.writeUTF("Jamal");
                output.writeDouble(85.5);
                output.writeObject(new java.util.Date());
                System.out.println("✓ Written string, double, and Date object to object.dat");
            }
        } catch (IOException e) {
            System.err.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void runTestObjectInputStream() {
        System.out.println("\n=== Test Object Input Stream ===");
        try {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("object.dat"))) {
                String name = input.readUTF();
                double score = input.readDouble();
                java.util.Date date = (java.util.Date)(input.readObject());
                
                System.out.println("✓ Read from object.dat:");
                System.out.println("  Name: " + name);
                System.out.println("  Score: " + score);
                System.out.println("  Date: " + date);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void runTestObjectStreamForArray() {
        System.out.println("\n=== Test Object Stream for Array ===");
        try {
            int[] numbers = {1, 2, 3, 4, 5};
            String[] strings = {"John", "Susan", "Kim"};
            
            // Write arrays
            try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("array.dat"))) {
                output.writeObject(numbers);
                output.writeObject(strings);
                System.out.println("✓ Written arrays to array.dat");
            }
            
            // Read arrays
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("array.dat"))) {
                int[] newNumbers = (int[])(input.readObject());
                String[] newStrings = (String[])(input.readObject());
                
                System.out.print("✓ Numbers array: ");
                for (int i = 0; i < newNumbers.length; i++) {
                    System.out.print(newNumbers[i] + " ");
                }
                System.out.println();
                
                System.out.print("✓ Strings array: ");
                for (int i = 0; i < newStrings.length; i++) {
                    System.out.print(newStrings[i] + " ");
                }
                System.out.println();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void runTestRandomAccessFile() {
        System.out.println("\n=== Test Random Access File ===");
        try {
            try (RandomAccessFile inout = new RandomAccessFile("inout.dat", "rw")) {
                // Clear the file
                inout.setLength(0);
                
                // Write integers
                for (int i = 0; i < 200; i++) {
                    inout.writeInt(i);
                }
                System.out.println("✓ Current file length is " + inout.length());
                
                // Read first number
                inout.seek(0);
                System.out.println("✓ The first number is " + inout.readInt());
                
                // Read second number
                inout.seek(1 * 4);
                System.out.println("✓ The second number is " + inout.readInt());
                
                // Read tenth number
                inout.seek(9 * 4);
                System.out.println("✓ The tenth number is " + inout.readInt());
                
                // Modify eleventh number
                inout.writeInt(555);
                
                // Append a new number
                inout.seek(inout.length());
                inout.writeInt(999);
                
                System.out.println("✓ The new length is " + inout.length());
                
                // Read the modified eleventh number
                inout.seek(10 * 4);
                System.out.println("✓ The eleventh number is " + inout.readInt());
            }
        } catch (IOException e) {
            System.err.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void runAllDemos() {
        System.out.println("\n=== Running All Demonstrations ===");
        System.out.println("This will run all binary I/O demonstrations in sequence.\n");
        
        runTestFileStream();
        System.out.println();
        
        runTestDataStream();
        System.out.println();
        
        runCopy();
        System.out.println();
        
        runTestObjectOutputStream();
        System.out.println();
        
        runTestObjectInputStream();
        System.out.println();
        
        runTestObjectStreamForArray();
        System.out.println();
        
        runTestRandomAccessFile();
        
        System.out.println("\n✓ All demonstrations completed successfully!");
    }
} 