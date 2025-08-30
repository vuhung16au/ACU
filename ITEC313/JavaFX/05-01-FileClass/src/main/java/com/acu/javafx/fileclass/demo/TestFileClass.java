package com.acu.javafx.fileclass.demo;

import java.io.File;
import java.util.Date;

/**
 * TestFileClass demonstrates the use of the File class to get file properties.
 * This class shows how to check file existence, size, permissions, and other properties.
 */
public class TestFileClass {
    
    /**
     * Tests file properties for a given file path.
     * 
     * @param filePath the path to the file to test
     * @return a formatted string containing all file properties
     */
    public static String testFileProperties(String filePath) {
        StringBuilder result = new StringBuilder();
        
        try {
            File file = new File(filePath);
            
            result.append("File: ").append(filePath).append("\n");
            result.append("Does it exist? ").append(file.exists()).append("\n");
            result.append("The file has ").append(file.length()).append(" bytes\n");
            result.append("Can it be read? ").append(file.canRead()).append("\n");
            result.append("Can it be written? ").append(file.canWrite()).append("\n");
            result.append("Is it a directory? ").append(file.isDirectory()).append("\n");
            result.append("Is it a file? ").append(file.isFile()).append("\n");
            result.append("Is it absolute? ").append(file.isAbsolute()).append("\n");
            result.append("Is it hidden? ").append(file.isHidden()).append("\n");
            result.append("Absolute path is ").append(file.getAbsolutePath()).append("\n");
            result.append("Last modified on ").append(new Date(file.lastModified())).append("\n");
            
        } catch (Exception e) {
            result.append("Error testing file properties: ").append(e.getMessage());
        }
        
        return result.toString();
    }
    
    /**
     * Tests file properties for the default example file.
     * 
     * @return a formatted string containing all file properties
     */
    public static String testDefaultFile() {
        return testFileProperties("images/au.gif");
    }
} 