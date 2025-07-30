package com.acu.javafx.queue;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class QueueDemo extends Application {
    
    private GenericQueue<String> queue = new GenericQueue<>();
    private GenericStack<String> stack = new GenericStack<>();
    private MyPriorityQueue<Patient> priorityQueue = new MyPriorityQueue<>();
    
    private VBox queueDisplay;
    private VBox stackDisplay;
    private VBox priorityQueueDisplay;
    private TextArea outputArea;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Queue, Stack, and Priority Queue Demo");
        
        // Create main layout
        BorderPane root = new BorderPane();
        
        // Create top title
        Label titleLabel = new Label("Data Structures Demo");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setPadding(new Insets(20));
        
        // Create tab pane for different data structures
        TabPane tabPane = new TabPane();
        
        // Queue Tab
        Tab queueTab = new Tab("Queue", createQueueTab());
        queueTab.setClosable(false);
        
        // Stack Tab
        Tab stackTab = new Tab("Stack", createStackTab());
        stackTab.setClosable(false);
        
        // Priority Queue Tab
        Tab priorityQueueTab = new Tab("Priority Queue", createPriorityQueueTab());
        priorityQueueTab.setClosable(false);
        
        // Test Tab
        Tab testTab = new Tab("Run Tests", createTestTab());
        testTab.setClosable(false);
        
        tabPane.getTabs().addAll(queueTab, stackTab, priorityQueueTab, testTab);
        
        // Create output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(10);
        outputArea.setStyle("-fx-font-family: 'Monaco', 'Consolas', monospace; -fx-font-size: 12;");
        
        // Layout
        root.setTop(titleLabel);
        root.setCenter(tabPane);
        root.setBottom(outputArea);
        BorderPane.setMargin(outputArea, new Insets(10));
        
        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox createQueueTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("Generic Queue Implementation");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Queue display
        queueDisplay = new VBox(5);
        queueDisplay.setAlignment(Pos.CENTER);
        queueDisplay.setStyle("-fx-border-color: #3498db; -fx-border-width: 2; -fx-padding: 10;");
        queueDisplay.setMinHeight(200);
        
        Label queueLabel = new Label("Queue Elements:");
        queueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        // Controls
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        
        TextField inputField = new TextField();
        inputField.setPromptText("Enter element to enqueue");
        inputField.setPrefWidth(200);
        
        Button enqueueBtn = new Button("Enqueue");
        enqueueBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        enqueueBtn.setOnAction(e -> {
            String value = inputField.getText().trim();
            if (!value.isEmpty()) {
                queue.enqueue(value);
                updateQueueDisplay();
                inputField.clear();
                log("Enqueued: " + value);
            }
        });
        
        Button dequeueBtn = new Button("Dequeue");
        dequeueBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        dequeueBtn.setOnAction(e -> {
            if (queue.getSize() > 0) {
                String value = queue.dequeue();
                updateQueueDisplay();
                log("Dequeued: " + value);
            } else {
                log("Queue is empty!");
            }
        });
        
        Button clearBtn = new Button("Clear");
        clearBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
        clearBtn.setOnAction(e -> {
            queue = new GenericQueue<>();
            updateQueueDisplay();
            log("Queue cleared");
        });
        
        controls.getChildren().addAll(inputField, enqueueBtn, dequeueBtn, clearBtn);
        
        vbox.getChildren().addAll(title, queueLabel, queueDisplay, controls);
        return vbox;
    }
    
    private VBox createStackTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("Generic Stack Implementation");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Stack display
        stackDisplay = new VBox(5);
        stackDisplay.setAlignment(Pos.CENTER);
        stackDisplay.setStyle("-fx-border-color: #9b59b6; -fx-border-width: 2; -fx-padding: 10;");
        stackDisplay.setMinHeight(200);
        
        Label stackLabel = new Label("Stack Elements:");
        stackLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        // Controls
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        
        TextField inputField = new TextField();
        inputField.setPromptText("Enter element to push");
        inputField.setPrefWidth(200);
        
        Button pushBtn = new Button("Push");
        pushBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        pushBtn.setOnAction(e -> {
            String value = inputField.getText().trim();
            if (!value.isEmpty()) {
                stack.push(value);
                updateStackDisplay();
                inputField.clear();
                log("Pushed: " + value);
            }
        });
        
        Button popBtn = new Button("Pop");
        popBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        popBtn.setOnAction(e -> {
            if (!stack.isEmpty()) {
                String value = stack.pop();
                updateStackDisplay();
                log("Popped: " + value);
            } else {
                log("Stack is empty!");
            }
        });
        
        Button peekBtn = new Button("Peek");
        peekBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        peekBtn.setOnAction(e -> {
            if (!stack.isEmpty()) {
                String value = stack.peek();
                log("Peeked: " + value);
            } else {
                log("Stack is empty!");
            }
        });
        
        Button clearBtn = new Button("Clear");
        clearBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
        clearBtn.setOnAction(e -> {
            stack = new GenericStack<>();
            updateStackDisplay();
            log("Stack cleared");
        });
        
        controls.getChildren().addAll(inputField, pushBtn, popBtn, peekBtn, clearBtn);
        
        vbox.getChildren().addAll(title, stackLabel, stackDisplay, controls);
        return vbox;
    }
    
    private VBox createPriorityQueueTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("Priority Queue Implementation");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Priority Queue display
        priorityQueueDisplay = new VBox(5);
        priorityQueueDisplay.setAlignment(Pos.CENTER);
        priorityQueueDisplay.setStyle("-fx-border-color: #e67e22; -fx-border-width: 2; -fx-padding: 10;");
        priorityQueueDisplay.setMinHeight(200);
        
        Label priorityQueueLabel = new Label("Priority Queue Elements:");
        priorityQueueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        // Controls
        VBox controls = new VBox(10);
        controls.setAlignment(Pos.CENTER);
        
        HBox inputControls = new HBox(10);
        inputControls.setAlignment(Pos.CENTER);
        
        TextField nameField = new TextField();
        nameField.setPromptText("Patient name");
        nameField.setPrefWidth(150);
        
        TextField priorityField = new TextField();
        priorityField.setPromptText("Priority (1-10)");
        priorityField.setPrefWidth(100);
        
        Button enqueueBtn = new Button("Enqueue Patient");
        enqueueBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        enqueueBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String priorityStr = priorityField.getText().trim();
            
            if (!name.isEmpty() && !priorityStr.isEmpty()) {
                try {
                    int priority = Integer.parseInt(priorityStr);
                    Patient patient = new Patient(name, priority);
                    priorityQueue.enqueue(patient);
                    updatePriorityQueueDisplay();
                    nameField.clear();
                    priorityField.clear();
                    log("Enqueued patient: " + patient);
                } catch (NumberFormatException ex) {
                    log("Invalid priority value!");
                }
            }
        });
        
        inputControls.getChildren().addAll(nameField, priorityField, enqueueBtn);
        
        HBox actionControls = new HBox(10);
        actionControls.setAlignment(Pos.CENTER);
        
        Button dequeueBtn = new Button("Dequeue Patient");
        dequeueBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        dequeueBtn.setOnAction(e -> {
            if (priorityQueue.getSize() > 0) {
                Patient patient = priorityQueue.dequeue();
                updatePriorityQueueDisplay();
                log("Dequeued patient: " + patient);
            } else {
                log("Priority queue is empty!");
            }
        });
        
        Button clearBtn = new Button("Clear");
        clearBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
        clearBtn.setOnAction(e -> {
            priorityQueue = new MyPriorityQueue<>();
            updatePriorityQueueDisplay();
            log("Priority queue cleared");
        });
        
        actionControls.getChildren().addAll(dequeueBtn, clearBtn);
        controls.getChildren().addAll(inputControls, actionControls);
        
        vbox.getChildren().addAll(title, priorityQueueLabel, priorityQueueDisplay, controls);
        return vbox;
    }
    
    private VBox createTestTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("Run Predefined Tests");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Test buttons
        VBox testButtons = new VBox(10);
        testButtons.setAlignment(Pos.CENTER);
        
        Button testStackQueueBtn = new Button("Test Stack and Queue");
        testStackQueueBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14;");
        testStackQueueBtn.setPrefWidth(250);
        testStackQueueBtn.setOnAction(e -> runStackQueueTest());
        
        Button testPriorityQueueBtn = new Button("Test Priority Queue");
        testPriorityQueueBtn.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-size: 14;");
        testPriorityQueueBtn.setPrefWidth(250);
        testPriorityQueueBtn.setOnAction(e -> runPriorityQueueTest());
        
        Button clearOutputBtn = new Button("Clear Output");
        clearOutputBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white;");
        clearOutputBtn.setOnAction(e -> outputArea.clear());
        
        testButtons.getChildren().addAll(testStackQueueBtn, testPriorityQueueBtn, clearOutputBtn);
        
        vbox.getChildren().addAll(title, testButtons);
        return vbox;
    }
    
    private void updateQueueDisplay() {
        queueDisplay.getChildren().clear();
        
        if (queue.getSize() == 0) {
            Label emptyLabel = new Label("Queue is empty");
            emptyLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-style: italic;");
            queueDisplay.getChildren().add(emptyLabel);
            return;
        }
        
        // Create visual representation
        HBox elementsBox = new HBox(5);
        elementsBox.setAlignment(Pos.CENTER);
        
        // Convert queue to list for display (this is just for visualization)
        List<String> elements = new ArrayList<>();
        GenericQueue<String> tempQueue = new GenericQueue<>();
        
        while (queue.getSize() > 0) {
            String element = queue.dequeue();
            elements.add(element);
            tempQueue.enqueue(element);
        }
        
        // Restore the queue
        while (tempQueue.getSize() > 0) {
            queue.enqueue(tempQueue.dequeue());
        }
        
        // Display elements
        for (String element : elements) {
            Rectangle rect = new Rectangle(60, 40);
            rect.setFill(Color.LIGHTBLUE);
            rect.setStroke(Color.DARKBLUE);
            rect.setStrokeWidth(2);
            
            Text text = new Text(element);
            text.setFont(Font.font("Arial", 12));
            
            VBox elementBox = new VBox(5);
            elementBox.setAlignment(Pos.CENTER);
            elementBox.getChildren().addAll(rect, text);
            
            elementsBox.getChildren().add(elementBox);
        }
        
        queueDisplay.getChildren().add(elementsBox);
    }
    
    private void updateStackDisplay() {
        stackDisplay.getChildren().clear();
        
        if (stack.isEmpty()) {
            Label emptyLabel = new Label("Stack is empty");
            emptyLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-style: italic;");
            stackDisplay.getChildren().add(emptyLabel);
            return;
        }
        
        // Create visual representation
        VBox elementsBox = new VBox(2);
        elementsBox.setAlignment(Pos.CENTER);
        
        // Convert stack to list for display (this is just for visualization)
        List<String> elements = new ArrayList<>();
        GenericStack<String> tempStack = new GenericStack<>();
        
        while (!stack.isEmpty()) {
            String element = stack.pop();
            elements.add(element);
            tempStack.push(element);
        }
        
        // Restore the stack
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
        
        // Display elements (top to bottom)
        for (int i = elements.size() - 1; i >= 0; i--) {
            String element = elements.get(i);
            Rectangle rect = new Rectangle(80, 30);
            rect.setFill(Color.LIGHTGREEN);
            rect.setStroke(Color.DARKGREEN);
            rect.setStrokeWidth(2);
            
            Text text = new Text(element);
            text.setFont(Font.font("Arial", 10));
            
            VBox elementBox = new VBox(2);
            elementBox.setAlignment(Pos.CENTER);
            elementBox.getChildren().addAll(rect, text);
            
            elementsBox.getChildren().add(elementBox);
        }
        
        stackDisplay.getChildren().add(elementsBox);
    }
    
    private void updatePriorityQueueDisplay() {
        priorityQueueDisplay.getChildren().clear();
        
        if (priorityQueue.getSize() == 0) {
            Label emptyLabel = new Label("Priority Queue is empty");
            emptyLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-style: italic;");
            priorityQueueDisplay.getChildren().add(emptyLabel);
            return;
        }
        
        // Create visual representation
        VBox elementsBox = new VBox(5);
        elementsBox.setAlignment(Pos.CENTER);
        
        // Convert priority queue to list for display (this is just for visualization)
        List<Patient> patients = new ArrayList<>();
        MyPriorityQueue<Patient> tempQueue = new MyPriorityQueue<>();
        
        while (priorityQueue.getSize() > 0) {
            Patient patient = priorityQueue.dequeue();
            patients.add(patient);
            tempQueue.enqueue(patient);
        }
        
        // Restore the priority queue
        while (tempQueue.getSize() > 0) {
            priorityQueue.enqueue(tempQueue.dequeue());
        }
        
        // Display patients (highest priority first)
        for (Patient patient : patients) {
            Rectangle rect = new Rectangle(120, 40);
            rect.setFill(Color.ORANGE);
            rect.setStroke(Color.DARKORANGE);
            rect.setStrokeWidth(2);
            
            VBox patientBox = new VBox(2);
            patientBox.setAlignment(Pos.CENTER);
            
            Text nameText = new Text(patient.getName());
            nameText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            
            Text priorityText = new Text("Priority: " + patient.getPriority());
            priorityText.setFont(Font.font("Arial", 10));
            priorityText.setFill(Color.DARKRED);
            
            patientBox.getChildren().addAll(rect, nameText, priorityText);
            elementsBox.getChildren().add(patientBox);
        }
        
        priorityQueueDisplay.getChildren().add(elementsBox);
    }
    
    private void runStackQueueTest() {
        log("=== Running Stack and Queue Test ===");
        
        // Capture System.out
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(baos));
        
        try {
            TestStackQueue.main(new String[0]);
            String output = baos.toString();
            log(output);
        } finally {
            System.setOut(originalOut);
        }
        
        log("=== Test completed ===");
    }
    
    private void runPriorityQueueTest() {
        log("=== Running Priority Queue Test ===");
        
        // Capture System.out
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(baos));
        
        try {
            TestPriorityQueue.main(new String[0]);
            String output = baos.toString();
            log(output);
        } finally {
            System.setOut(originalOut);
        }
        
        log("=== Test completed ===");
    }
    
    private void log(String message) {
        Platform.runLater(() -> {
            outputArea.appendText(message + "\n");
            outputArea.setScrollTop(Double.MAX_VALUE);
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 