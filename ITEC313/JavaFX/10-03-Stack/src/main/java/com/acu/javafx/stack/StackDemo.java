package com.acu.javafx.stack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * JavaFX application demonstrating Stack data structure
 * Based on the animation from https://liveexample.pearsoncmg.com/dsanimation/StackeBook.html
 */
public class StackDemo extends Application {
    
    private Stack stack = new Stack();
    private Canvas canvas;
    private GraphicsContext gc;
    private TextField valueField;
    private Label statusLabel;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stack Data Structure Demo");
        
        // Create main layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Create canvas for stack visualization
        canvas = new Canvas(400, 200);
        gc = canvas.getGraphicsContext2D();
        
        // Create controls
        HBox controlsBox = createControls();
        
        // Create status label
        statusLabel = new Label("Stack is empty");
        statusLabel.setFont(Font.font(14));
        statusLabel.setStyle("-fx-text-fill: #2E8B57;");
        
        // Add components to root
        root.getChildren().addAll(
            new Label("Stack Visualization"),
            canvas,
            controlsBox,
            statusLabel
        );
        
        // Create scene
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Initial draw
        drawStack();
    }
    
    /**
     * Creates the control panel with buttons and input field
     */
    private HBox createControls() {
        HBox controlsBox = new HBox(10);
        controlsBox.setAlignment(Pos.CENTER);
        controlsBox.setPadding(new Insets(10));
        
        // Input field
        Label valueLabel = new Label("Enter a value:");
        valueField = new TextField();
        valueField.setPrefWidth(100);
        valueField.setPromptText("Value");
        
        // Buttons
        Button pushButton = new Button("Push");
        pushButton.setOnAction(e -> push());
        
        Button popButton = new Button("Pop");
        popButton.setOnAction(e -> pop());
        
        Button peekButton = new Button("Peek");
        peekButton.setOnAction(e -> peek());
        
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> clear());
        
        // Add controls to box
        controlsBox.getChildren().addAll(
            valueLabel, valueField, pushButton, popButton, peekButton, clearButton
        );
        
        return controlsBox;
    }
    
    /**
     * Pushes a value onto the stack
     */
    private void push() {
        String value = valueField.getText().trim();
        if (value.isEmpty()) {
            showAlert("No value entered", "Please enter a value to push onto the stack.");
            return;
        }
        
        stack.push(value);
        valueField.clear();
        drawStack();
        updateStatus("Pushed: " + value);
    }
    
    /**
     * Pops a value from the stack
     */
    private void pop() {
        if (stack.isEmpty()) {
            showAlert("Stack is empty", "Cannot pop from an empty stack.");
            return;
        }
        
        Object value = stack.pop();
        drawStack();
        updateStatus("Popped: " + value);
    }
    
    /**
     * Peeks at the top value of the stack
     */
    private void peek() {
        if (stack.isEmpty()) {
            showAlert("Stack is empty", "Cannot peek at an empty stack.");
            return;
        }
        
        Object value = stack.peek();
        updateStatus("Top element: " + value);
    }
    
    /**
     * Clears the stack
     */
    private void clear() {
        stack = new Stack();
        drawStack();
        updateStatus("Stack cleared");
    }
    
    /**
     * Draws the stack visualization on the canvas
     */
    private void drawStack() {
        // Clear canvas
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        // Set drawing properties
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.setFont(Font.font("Arial", 14));
        
        if (stack.isEmpty()) {
            // Draw empty stack message
            gc.setFill(Color.GRAY);
            gc.fillText("Stack is empty", canvas.getWidth() / 2 - 50, canvas.getHeight() / 2);
            return;
        }
        
        // Draw stack elements
        int x = (int) (canvas.getWidth() / 2 - 30);
        int y = (int) (canvas.getHeight() - 30);
        int rectWidth = 60;
        int rectHeight = 25;
        
        // Get stack elements (we need to access the internal list for visualization)
        // Since we can't directly access the private list, we'll simulate the visualization
        // by creating a temporary stack and popping elements to get them in reverse order
        Stack tempStack = new Stack();
        java.util.ArrayList<Object> elements = new java.util.ArrayList<>();
        
        // Create a copy of the stack to get elements
        while (!stack.isEmpty()) {
            Object element = stack.pop();
            elements.add(0, element); // Add to beginning to maintain order
            tempStack.push(element);
        }
        
        // Restore the original stack
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
        
        // Draw elements from bottom to top
        for (int i = 0; i < elements.size(); i++) {
            Object value = elements.get(i);
            
            // Draw rectangle
            gc.setFill(Color.LIGHTBLUE);
            gc.fillRect(x, y, rectWidth, rectHeight);
            gc.setStroke(Color.BLUE);
            gc.strokeRect(x, y, rectWidth, rectHeight);
            
            // Draw value
            gc.setFill(Color.BLACK);
            gc.fillText(value.toString(), x + 5, y + 17);
            
            y -= rectHeight;
        }
        
        // Draw "Top" label and arrow
        if (elements.size() > 0) {
            gc.setFill(Color.RED);
            gc.fillText("Top", x - 85, y + rectHeight + 10);
            
            // Draw arrow
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeLine(x - 45, y + rectHeight + 10, x, y + rectHeight + 10);
            
            // Draw arrowhead
            double arrowLength = 10;
            double angle = Math.PI;
            double arrowAngle = Math.PI / 6;
            
            double x1 = x;
            double y1 = y + rectHeight + 10;
            double x2 = x1 + arrowLength * Math.cos(angle + arrowAngle);
            double y2 = y1 + arrowLength * Math.sin(angle + arrowAngle);
            double x3 = x1 + arrowLength * Math.cos(angle - arrowAngle);
            double y3 = y1 + arrowLength * Math.sin(angle - arrowAngle);
            
            gc.strokeLine(x1, y1, x2, y2);
            gc.strokeLine(x1, y1, x3, y3);
        }
    }
    
    /**
     * Updates the status label
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }
    
    /**
     * Shows an alert dialog
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        launch(args);
    }
} 