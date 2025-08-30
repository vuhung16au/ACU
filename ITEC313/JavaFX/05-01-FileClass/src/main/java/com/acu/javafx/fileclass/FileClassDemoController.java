package com.acu.javafx.fileclass;

import com.acu.javafx.fileclass.demo.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;

/**
 * Controller for the JavaFX File Class Demo application.
 * This class handles all UI interactions and demonstrates File class operations.
 */
public class FileClassDemoController {
    
    @FXML private TabPane tabPane;
    @FXML private TextArea outputArea;
    @FXML private TextField filePathField;
    @FXML private TextField sourceFileField;
    @FXML private TextField targetFileField;
    @FXML private TextField oldStringField;
    @FXML private TextField newStringField;
    
    /**
     * Initialize the controller and set up the UI.
     */
    @FXML
    public void initialize() {
        // Set default values
        filePathField.setText("images/au.gif");
        sourceFileField.setText("sample.txt");
        targetFileField.setText("replaced.txt");
        oldStringField.setText("text");
        newStringField.setText("content");
        
        // Clear output area
        outputArea.setText("Welcome to JavaFX File Class Demo!\n\n" +
                          "This application demonstrates various File class operations:\n" +
                          "1. TestFileClass - File properties and information\n" +
                          "2. WriteData - Writing data to files\n" +
                          "3. WriteDataWithAutoClose - Using try-with-resources\n" +
                          "4. ReadData - Reading data from files\n" +
                          "5. ReplaceText - Text replacement in files\n\n" +
                          "Select a tab to explore different File operations.\n");
    }
    
    /**
     * Demonstrates TestFileClass functionality.
     */
    @FXML
    private void testFileProperties() {
        String filePath = filePathField.getText().trim();
        if (filePath.isEmpty()) {
            filePath = "image/us.gif";
            filePathField.setText(filePath);
        }
        
        String result = TestFileClass.testFileProperties(filePath);
        outputArea.setText("=== TestFileClass Demo ===\n\n" + result);
    }
    
    /**
     * Demonstrates WriteData functionality.
     */
    @FXML
    private void writeData() {
        String result = WriteData.writeDefaultData();
        outputArea.setText("=== WriteData Demo ===\n\n" + result);
    }
    
    /**
     * Demonstrates WriteDataWithAutoClose functionality.
     */
    @FXML
    private void writeDataWithAutoClose() {
        String result = WriteDataWithAutoClose.writeDefaultDataWithAutoClose();
        outputArea.setText("=== WriteDataWithAutoClose Demo ===\n\n" + result);
    }
    
    /**
     * Demonstrates ReadData functionality.
     */
    @FXML
    private void readData() {
        String result = ReadData.readDefaultData();
        if (result.contains("File does not exist")) {
            result += "\n\nNote: You need to create the scores.txt file first using WriteData.";
        }
        outputArea.setText("=== ReadData Demo ===\n\n" + result);
    }
    
    /**
     * Demonstrates ReplaceText functionality.
     */
    @FXML
    private void replaceText() {
        String sourceFile = sourceFileField.getText().trim();
        String targetFile = targetFileField.getText().trim();
        String oldStr = oldStringField.getText().trim();
        String newStr = newStringField.getText().trim();
        
        if (sourceFile.isEmpty() || targetFile.isEmpty() || oldStr.isEmpty() || newStr.isEmpty()) {
            outputArea.setText("=== ReplaceText Demo ===\n\n" +
                              "Please fill in all fields: source file, target file, old string, and new string.");
            return;
        }
        
        String result = ReplaceText.replaceText(sourceFile, targetFile, oldStr, newStr);
        outputArea.setText("=== ReplaceText Demo ===\n\n" + result);
    }
    
    /**
     * Creates a sample file for text replacement demo.
     */
    @FXML
    private void createSampleFile() {
        String result = ReplaceText.createSampleFile("sample.txt");
        outputArea.setText("=== Create Sample File ===\n\n" + result);
    }
    
    /**
     * Demonstrates the complete text replacement workflow.
     */
    @FXML
    private void demonstrateReplaceText() {
        String result = ReplaceText.demonstrateReplaceText();
        outputArea.setText("=== Complete ReplaceText Demo ===\n\n" + result);
    }
    
    /**
     * Clears the output area.
     */
    @FXML
    private void clearOutput() {
        outputArea.clear();
    }
    
    /**
     * Shows help information.
     */
    @FXML
    private void showHelp() {
        String helpText = """
            === JavaFX File Class Demo Help ===
            
            This application demonstrates various File class operations:
            
            1. TestFileClass Tab:
               - Tests file properties like existence, size, permissions
               - Enter a file path and click "Test File Properties"
            
            2. WriteData Tab:
               - Demonstrates writing data to files using PrintWriter
               - Click "Write Data" to create scores.txt with sample data
            
            3. WriteDataWithAutoClose Tab:
               - Shows try-with-resources for automatic resource management
               - Click "Write Data with Auto-Close" to create scores.txt
            
            4. ReadData Tab:
               - Demonstrates reading data from files using Scanner
               - Click "Read Data" to read from scores.txt
               - Note: Create the file first using WriteData
            
            5. ReplaceText Tab:
               - Shows text replacement in files
               - Fill in source file, target file, old string, and new string
               - Click "Replace Text" to perform the replacement
               - Or use "Create Sample File" and "Demonstrate Replace Text"
            
            All operations show results in the output area below.
            """;
        
        outputArea.setText(helpText);
    }
} 