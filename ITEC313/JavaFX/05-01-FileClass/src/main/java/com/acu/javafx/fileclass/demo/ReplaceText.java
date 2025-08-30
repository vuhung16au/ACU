package com.acu.javafx.fileclass.demo;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * ReplaceText demonstrates replacing text in a file using try-with-resources.
 * This class shows how to read from one file and write to another with text replacement.
 */
public class ReplaceText {
    
    /**
     * Replaces text in a source file and writes the result to a target file.
     * 
     * @param sourceFile the source file to read from
     * @param targetFile the target file to write to
     * @param oldStr the string to replace
     * @param newStr the string to replace with
     * @return a status message indicating success or failure
     */
    public static String replaceText(String sourceFile, String targetFile, String oldStr, String newStr) {
        StringBuilder result = new StringBuilder();
        
        try {
            // It is a good practice to check if the file exists
            File source = new File(sourceFile);
            if (!source.exists()) {
                result.append("Source file ").append(sourceFile).append(" does not exist");
                return result.toString();
            }

            // It is a good practice to check if the file exists
            File target = new File(targetFile);
            if (target.exists()) {
                result.append("Target file ").append(targetFile).append(" already exists");
                return result.toString();
            }

            // Handle the exception if the file does not exist
            try (
                // Create input and output files
                Scanner input = new Scanner(source);
                PrintWriter output = new PrintWriter(target);
            ) {        
                while (input.hasNext()) {
                    String s1 = input.nextLine();

                    // Replace the old string with the new string
                    String s2 = s1.replaceAll(oldStr, newStr);
                    output.println(s2);
                }
            }
            
            result.append("Successfully replaced text in ").append(sourceFile)
                  .append(" and wrote to ").append(targetFile);
            
        } catch (Exception e) {
            result.append("Error replacing text: ").append(e.getMessage());
        }
        
        return result.toString();
    }
    
    /**
     * Creates a sample file for testing text replacement.
     * 
     * @param filename the name of the file to create
     * @return a status message indicating success or failure
     */
    public static String createSampleFile(String filename) {
        StringBuilder result = new StringBuilder();
        
        // It is a good practice to check if the file exists
        try {
            File file = new File(filename);
            
            if (file.exists()) {
                result.append("File already exists: ").append(filename);
                return result.toString();
            }

            // Create a PrintWriter to write to the file
            try (PrintWriter output = new PrintWriter(file)) {
                output.println("This is a sample text file.");
                output.println("It contains some text to replace.");
                output.println("We will replace 'text' with 'content'.");
                output.println("This demonstrates text replacement functionality.");
            }
            
            result.append("Successfully created sample file: ").append(filename);
            
        } catch (Exception e) {
            result.append("Error creating sample file: ").append(e.getMessage());
        }
        
        return result.toString();
    }
    
    /**
     * Creates a default sample file and demonstrates text replacement.
     * 
     * @return a status message indicating success or failure
     */
    public static String demonstrateReplaceText() {
        String sampleFile = "sample.txt";
        String targetFile = "replaced.txt";
        
        // First create a sample file
        String createResult = createSampleFile(sampleFile);
        if (createResult.contains("Error") || createResult.contains("already exists")) {
            return createResult;
        }
        
        // Then perform text replacement
        return replaceText(sampleFile, targetFile, "text", "content");
    }
} 