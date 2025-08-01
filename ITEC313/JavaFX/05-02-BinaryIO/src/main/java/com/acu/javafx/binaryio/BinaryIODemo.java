package com.acu.javafx.binaryio;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BinaryIODemo extends Application {
    private TextArea outputArea;
    private ExecutorService executor;
    
    @Override
    public void start(Stage primaryStage) {
        executor = Executors.newCachedThreadPool();
        
        // Create the main layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        
        // Create title
        Label titleLabel = new Label("Binary I/O Demonstration");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        // Create description
        Label descriptionLabel = new Label(
            "This application demonstrates various binary I/O operations in Java. " +
            "Click the buttons below to run different examples and see the results."
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");
        
        // Create buttons grid
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);
        
        // Create buttons for each demo
        Button testFileStreamBtn = createDemoButton("Test File Stream", this::runTestFileStream);
        Button testDataStreamBtn = createDemoButton("Test Data Stream", this::runTestDataStream);
        Button copyBtn = createDemoButton("Copy File", this::runCopy);
        Button testObjectOutputStreamBtn = createDemoButton("Test Object Output Stream", this::runTestObjectOutputStream);
        Button testObjectInputStreamBtn = createDemoButton("Test Object Input Stream", this::runTestObjectInputStream);
        Button testObjectStreamForArrayBtn = createDemoButton("Test Object Stream for Array", this::runTestObjectStreamForArray);
        Button testRandomAccessFileBtn = createDemoButton("Test Random Access File", this::runTestRandomAccessFile);
        Button clearBtn = createDemoButton("Clear Output", this::clearOutput);
        
        // Add buttons to grid
        buttonGrid.add(testFileStreamBtn, 0, 0);
        buttonGrid.add(testDataStreamBtn, 1, 0);
        buttonGrid.add(copyBtn, 0, 1);
        buttonGrid.add(testObjectOutputStreamBtn, 1, 1);
        buttonGrid.add(testObjectInputStreamBtn, 0, 2);
        buttonGrid.add(testObjectStreamForArrayBtn, 1, 2);
        buttonGrid.add(testRandomAccessFileBtn, 0, 3);
        buttonGrid.add(clearBtn, 1, 3);
        
        // Create output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(15);
        outputArea.setStyle("-fx-font-family: 'Monaco', 'Consolas', monospace; -fx-font-size: 12px;");
        
        // Create scroll pane for output
        ScrollPane scrollPane = new ScrollPane(outputArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefHeight(400);
        
        // Add all components to root
        root.getChildren().addAll(titleLabel, descriptionLabel, buttonGrid, scrollPane);
        
        // Create scene
        Scene scene = new Scene(root, 800, 700);
        primaryStage.setTitle("Binary I/O Demonstration");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Add initial message
        appendOutput("Binary I/O Demonstration Ready\n" +
                    "Click any button to run the corresponding example.\n" +
                    "Results will be displayed in this area.\n" +
                    "==========================================\n");
    }
    
    private Button createDemoButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setPrefHeight(40);
        button.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        button.setOnAction(e -> {
            button.setDisable(true);
            executor.submit(() -> {
                try {
                    action.run();
                } catch (Exception ex) {
                    appendOutput("Error: " + ex.getMessage() + "\n");
                } finally {
                    Platform.runLater(() -> button.setDisable(false));
                }
            });
        });
        return button;
    }
    
    private void appendOutput(String text) {
        Platform.runLater(() -> {
            outputArea.appendText(text);
            outputArea.setScrollTop(Double.MAX_VALUE);
        });
    }
    
    private void clearOutput() {
        Platform.runLater(() -> outputArea.clear());
    }
    
    private void runTestFileStream() {
        appendOutput("\n=== Test File Stream ===\n");
        try {
            // Create output stream and write data
            try (FileOutputStream output = new FileOutputStream("temp.dat")) {
                for (int i = 1; i <= 10; i++) {
                    output.write(i);
                }
                appendOutput("Written values 1-10 to temp.dat\n");
            }
            
            // Create input stream and read data
            try (FileInputStream input = new FileInputStream("temp.dat")) {
                appendOutput("Reading from temp.dat: ");
                int value;
                while ((value = input.read()) != -1) {
                    appendOutput(value + " ");
                }
                appendOutput("\n");
            }
        } catch (IOException e) {
            appendOutput("Error: " + e.getMessage() + "\n");
        }
    }
    
    private void runTestDataStream() {
        appendOutput("\n=== Test Data Stream ===\n");
        try {
            // Write data using DataOutputStream
            try (DataOutputStream output = new DataOutputStream(new FileOutputStream("temp.dat"))) {
                output.writeUTF("Liam");
                output.writeDouble(85.5);
                output.writeUTF("Susan");
                output.writeDouble(185.5);
                output.writeUTF("Chandra");
                output.writeDouble(105.25);
                appendOutput("Written student data to temp.dat\n");
            }
            
            // Read data using DataInputStream
            try (DataInputStream input = new DataInputStream(new FileInputStream("temp.dat"))) {
                appendOutput("Reading student data:\n");
                appendOutput(input.readUTF() + " " + input.readDouble() + "\n");
                appendOutput(input.readUTF() + " " + input.readDouble() + "\n");
                appendOutput(input.readUTF() + " " + input.readDouble() + "\n");
            }
        } catch (IOException e) {
            appendOutput("Error: " + e.getMessage() + "\n");
        }
    }
    
    private void runCopy() {
        appendOutput("\n=== Copy File ===\n");
        try {
            // Create a test file to copy
            try (FileOutputStream testOutput = new FileOutputStream("source.txt")) {
                testOutput.write("This is a test file for copying.".getBytes());
            }
            
            // Perform the copy operation
            try (BufferedInputStream input = new BufferedInputStream(new FileInputStream("source.txt"));
                 BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream("target.txt"))) {
                
                int r, numberOfBytesCopied = 0;
                while ((r = input.read()) != -1) {
                    output.write((byte)r);
                    numberOfBytesCopied++;
                }
                appendOutput(numberOfBytesCopied + " bytes copied from source.txt to target.txt\n");
            }
        } catch (IOException e) {
            appendOutput("Error: " + e.getMessage() + "\n");
        }
    }
    
    private void runTestObjectOutputStream() {
        appendOutput("\n=== Test Object Output Stream ===\n");
        try {
            try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("object.dat"))) {
                output.writeUTF("Jamal");
                output.writeDouble(85.5);
                output.writeObject(new java.util.Date());
                appendOutput("Written string, double, and Date object to object.dat\n");
            }
        } catch (IOException e) {
            appendOutput("Error: " + e.getMessage() + "\n");
        }
    }
    
    private void runTestObjectInputStream() {
        appendOutput("\n=== Test Object Input Stream ===\n");
        try {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("object.dat"))) {
                String name = input.readUTF();
                double score = input.readDouble();
                java.util.Date date = (java.util.Date)(input.readObject());
                
                appendOutput("Read from object.dat:\n");
                appendOutput("Name: " + name + "\n");
                appendOutput("Score: " + score + "\n");
                appendOutput("Date: " + date + "\n");
            }
        } catch (IOException | ClassNotFoundException e) {
            appendOutput("Error: " + e.getMessage() + "\n");
        }
    }
    
    private void runTestObjectStreamForArray() {
        appendOutput("\n=== Test Object Stream for Array ===\n");
        try {
            int[] numbers = {1, 2, 3, 4, 5};
            String[] strings = {"John", "Susan", "Kim"};
            
            // Write arrays
            try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("array.dat"))) {
                output.writeObject(numbers);
                output.writeObject(strings);
                appendOutput("Written arrays to array.dat\n");
            }
            
            // Read arrays
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("array.dat"))) {
                int[] newNumbers = (int[])(input.readObject());
                String[] newStrings = (String[])(input.readObject());
                
                appendOutput("Numbers array: ");
                for (int i = 0; i < newNumbers.length; i++) {
                    appendOutput(newNumbers[i] + " ");
                }
                appendOutput("\n");
                
                appendOutput("Strings array: ");
                for (int i = 0; i < newStrings.length; i++) {
                    appendOutput(newStrings[i] + " ");
                }
                appendOutput("\n");
            }
        } catch (IOException | ClassNotFoundException e) {
            appendOutput("Error: " + e.getMessage() + "\n");
        }
    }
    
    private void runTestRandomAccessFile() {
        appendOutput("\n=== Test Random Access File ===\n");
        try {
            try (RandomAccessFile inout = new RandomAccessFile("inout.dat", "rw")) {
                // Clear the file
                inout.setLength(0);
                
                // Write integers
                for (int i = 0; i < 200; i++) {
                    inout.writeInt(i);
                }
                appendOutput("Current file length is " + inout.length() + "\n");
                
                // Read first number
                inout.seek(0);
                appendOutput("The first number is " + inout.readInt() + "\n");
                
                // Read second number
                inout.seek(1 * 4);
                appendOutput("The second number is " + inout.readInt() + "\n");
                
                // Read tenth number
                inout.seek(9 * 4);
                appendOutput("The tenth number is " + inout.readInt() + "\n");
                
                // Modify eleventh number
                inout.writeInt(555);
                
                // Append a new number
                inout.seek(inout.length());
                inout.writeInt(999);
                
                appendOutput("The new length is " + inout.length() + "\n");
                
                // Read the modified eleventh number
                inout.seek(10 * 4);
                appendOutput("The eleventh number is " + inout.readInt() + "\n");
            }
        } catch (IOException e) {
            appendOutput("Error: " + e.getMessage() + "\n");
        }
    }
    
    @Override
    public void stop() {
        if (executor != null) {
            executor.shutdown();
        }
    }
    
    /**
     * Student class for demonstrating object serialization
     */
    public static class Student implements Serializable {
        private String name;
        private double score;
        
        public Student(String name, double score) {
            this.name = name;
            this.score = score;
        }
        
        public String getName() {
            return name;
        }
        
        public double getScore() {
            return score;
        }
        
        @Override
        public String toString() {
            return "Student{name='" + name + "', score=" + score + "}";
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 