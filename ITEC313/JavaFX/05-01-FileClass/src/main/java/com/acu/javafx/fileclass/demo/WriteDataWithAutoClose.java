package com.acu.javafx.fileclass.demo;

import java.io.File;
import java.io.PrintWriter;

/**
 * WriteDataWithAutoClose demonstrates writing data to a text file using try-with-resources.
 * This class shows how to automatically close resources using try-with-resources syntax.
 */
public class WriteDataWithAutoClose {
    
    /**
     * Writes sample data to a file using try-with-resources for automatic resource management.
     * 
     * @param filename the name of the file to write to
     * @return a status message indicating success or failure
     */
    public static String writeDataWithAutoClose(String filename) {
        StringBuilder result = new StringBuilder();
        
        try {
            File file = new File(filename);
            
            if (file.exists()) {
                result.append("File already exists: ").append(filename);
                return result.toString();
            }

            try (
                // Create a file - automatically closed when try block exits
                PrintWriter output = new PrintWriter(file);
            ) {
                // Write formatted output to the file
                output.print("John T. Perez ");
                output.println(90);
                output.print("Jamal K. Johnson ");
                output.println(85);
            }
            
            result.append("Successfully wrote data to file: ").append(filename);
            
        } catch (Exception e) {
            result.append("Error writing to file: ").append(e.getMessage());
        }
        
        return result.toString();
    }
    
    /**
     * Writes sample data to the default scores.txt file using auto-close.
     * 
     * @return a status message indicating success or failure
     */
    public static String writeDefaultDataWithAutoClose() {
        return writeDataWithAutoClose("scores.txt");
    }
} 