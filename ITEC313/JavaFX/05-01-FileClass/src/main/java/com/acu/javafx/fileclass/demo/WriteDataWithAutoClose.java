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
            
            // It is a good practice to check if the file exists
            if (file.exists()) {
                result.append("File already exists: ").append(filename);
                return result.toString();
            }

            // try-with-resources to automatically close the file
            // the resource `output` is automatically closed when the try block exits
            // make the exception handling safer
            try (
                PrintWriter output = new PrintWriter(file);
            ) {
                // Write formatted output to the file
                output.print("John T. Perez ");

                // print in a new line
                output.println(90);
                output.print("Jamal K. Johnson ");
                output.println(85);
            }
            
            result.append("Successfully wrote data to file: ").append(filename);

            // No need to close the file manually
            
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