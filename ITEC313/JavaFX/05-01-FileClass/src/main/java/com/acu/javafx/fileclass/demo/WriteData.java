package com.acu.javafx.fileclass.demo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * WriteData demonstrates writing data to a text file using PrintWriter.
 * This class shows how to write formatted output to a file.
 */
public class WriteData {
    
    /**
     * Writes sample data to a file using PrintWriter.
     * 
     * @param filename the name of the file to write to
     * @return a status message indicating success or failure
     */
    public static String writeDataToFile(String filename) {
        StringBuilder result = new StringBuilder();
        
        try {
            File file = new File(filename);
            
            if (file.exists()) {
                result.append("File already exists: ").append(filename);
                return result.toString();
            }

            // Create a file
            PrintWriter output = new PrintWriter(file);

            // Write formatted output to the file
            output.print("John T. Perez ");
            output.println(90);
            output.print("Jamal K. Johnson ");
            output.println(85);

            // Close the file
            output.close();
            
            result.append("Successfully wrote data to file: ").append(filename);
            
        } catch (IOException e) {
            result.append("Error writing to file: ").append(e.getMessage());
        }
        
        return result.toString();
    }
    
    /**
     * Writes sample data to the default scores.txt file.
     * 
     * @return a status message indicating success or failure
     */
    public static String writeDefaultData() {
        return writeDataToFile("scores.txt");
    }
} 