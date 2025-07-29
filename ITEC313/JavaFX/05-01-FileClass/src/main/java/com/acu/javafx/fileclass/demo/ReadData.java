package com.acu.javafx.fileclass.demo;

import java.io.File;
import java.util.Scanner;

/**
 * ReadData demonstrates reading data from a text file using Scanner.
 * This class shows how to read formatted data from a file.
 */
public class ReadData {
    
    /**
     * Reads data from a file using Scanner.
     * 
     * @param filename the name of the file to read from
     * @return a formatted string containing the read data
     */
    public static String readDataFromFile(String filename) {
        StringBuilder result = new StringBuilder();
        
        try {
            // Create a File instance
            File file = new File(filename);
            
            if (!file.exists()) {
                result.append("File does not exist: ").append(filename);
                return result.toString();
            }

            // Create a Scanner for the file
            Scanner input = new Scanner(file);

            // Read data from a file
            while (input.hasNext()) {
                String firstName = input.next();
                String mi = input.next();
                String lastName = input.next();
                int score = input.nextInt();
                result.append(firstName).append(" ")
                      .append(mi).append(" ")
                      .append(lastName).append(" ")
                      .append(score).append("\n");
            }

            // Close the file
            input.close();
            
        } catch (Exception e) {
            result.append("Error reading from file: ").append(e.getMessage());
        }
        
        return result.toString();
    }
    
    /**
     * Reads data from the default scores.txt file.
     * 
     * @return a formatted string containing the read data
     */
    public static String readDefaultData() {
        return readDataFromFile("scores.txt");
    }
} 