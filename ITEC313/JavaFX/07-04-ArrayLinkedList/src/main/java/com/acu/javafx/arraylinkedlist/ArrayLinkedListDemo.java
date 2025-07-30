package com.acu.javafx.arraylinkedlist;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.*;

public class ArrayLinkedListDemo extends Application {
    
    private TextArea outputArea;
    private List<Integer> arrayList;
    private LinkedList<Object> linkedList;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Array and LinkedList Demo");
        
        // Initialize data structures
        initializeDataStructures();
        
        // Create UI components
        VBox root = createUI();
        
        // Create scene
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Run the demo
        runDemo();
    }
    
    private void initializeDataStructures() {
        arrayList = new ArrayList<>();
        arrayList.add(1); // 1 is autoboxed to an Integer object
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(1);
        arrayList.add(4);
        arrayList.add(0, 10);
        arrayList.add(3, 30);
        
        linkedList = new LinkedList<>(arrayList);
        linkedList.add(1, "red");
        linkedList.removeLast();
        linkedList.addFirst("green");
    }
    
    private VBox createUI() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        
        // Title
        Label titleLabel = new Label("Array and LinkedList Demonstration");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button arrayListBtn = new Button("Show ArrayList");
        Button linkedListForwardBtn = new Button("LinkedList Forward");
        Button linkedListBackwardBtn = new Button("LinkedList Backward");
        Button runAllBtn = new Button("Run All Demo");
        
        arrayListBtn.setOnAction(e -> showArrayList());
        linkedListForwardBtn.setOnAction(e -> showLinkedListForward());
        linkedListBackwardBtn.setOnAction(e -> showLinkedListBackward());
        runAllBtn.setOnAction(e -> runDemo());
        
        buttonBox.getChildren().addAll(arrayListBtn, linkedListForwardBtn, 
                                     linkedListBackwardBtn, runAllBtn);
        
        // Output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(15);
        outputArea.setPrefColumnCount(80);
        outputArea.setFont(Font.font("Monospaced", 12));
        
        // Clear button
        Button clearBtn = new Button("Clear Output");
        clearBtn.setOnAction(e -> outputArea.clear());
        
        root.getChildren().addAll(titleLabel, buttonBox, outputArea, clearBtn);
        
        return root;
    }
    
    private void showArrayList() {
        outputArea.appendText("=== ArrayList Demo ===\n");
        outputArea.appendText("A list of integers in the arraylist:\n");
        outputArea.appendText(arrayList.toString() + "\n\n");
    }
    
    private void showLinkedListForward() {
        outputArea.appendText("=== LinkedList Forward Demo ===\n");
        outputArea.appendText("Display the linked list forward:\n");
        
        ListIterator<Object> listIterator = linkedList.listIterator();
        while (listIterator.hasNext()) {
            outputArea.appendText(listIterator.next() + " ");
        }
        outputArea.appendText("\n\n");
    }
    
    private void showLinkedListBackward() {
        outputArea.appendText("=== LinkedList Backward Demo ===\n");
        outputArea.appendText("Display the linked list backward:\n");
        
        ListIterator<Object> listIterator = linkedList.listIterator(linkedList.size());
        while (listIterator.hasPrevious()) {
            outputArea.appendText(listIterator.previous() + " ");
        }
        outputArea.appendText("\n\n");
    }
    
    private void runDemo() {
        outputArea.clear();
        outputArea.appendText("=== Complete Array and LinkedList Demo ===\n\n");
        
        // ArrayList demo
        outputArea.appendText("1. ArrayList Demo:\n");
        outputArea.appendText("A list of integers in the arraylist:\n");
        outputArea.appendText(arrayList.toString() + "\n\n");
        
        // LinkedList forward demo
        outputArea.appendText("2. LinkedList Forward Demo:\n");
        outputArea.appendText("Display the linked list forward:\n");
        ListIterator<Object> listIterator = linkedList.listIterator();
        while (listIterator.hasNext()) {
            outputArea.appendText(listIterator.next() + " ");
        }
        outputArea.appendText("\n\n");
        
        // LinkedList backward demo
        outputArea.appendText("3. LinkedList Backward Demo:\n");
        outputArea.appendText("Display the linked list backward:\n");
        listIterator = linkedList.listIterator(linkedList.size());
        while (listIterator.hasPrevious()) {
            outputArea.appendText(listIterator.previous() + " ");
        }
        outputArea.appendText("\n\n");
        
        // Additional information
        outputArea.appendText("=== Additional Information ===\n");
        outputArea.appendText("ArrayList size: " + arrayList.size() + "\n");
        outputArea.appendText("LinkedList size: " + linkedList.size() + "\n");
        outputArea.appendText("ArrayList contains '1': " + arrayList.contains(1) + "\n");
        outputArea.appendText("LinkedList contains 'red': " + linkedList.contains("red") + "\n");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 